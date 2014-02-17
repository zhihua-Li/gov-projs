/**
 * CodisBatchImportServiceImpl.java
 *
 * 2014-1-20
 */
package com.lims.service.codisbatchimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lims.codis.helper.FileStrInfo;
import com.lims.codis.helper.LocusValues;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.CodisSample;
import com.lims.domain.po.GeneInfo;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.vo.SampleInfoView;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.service.codissample.CodisSampleService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
@Service("codisBatchImportService")
public class CodisBatchImportServiceImpl extends LimsBaseServiceImpl implements
		CodisBatchImportService {

	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private CodisSampleService codisSampleService;


	@SuppressWarnings("unchecked")
	@Override
	public List<List<Map<String, Object>>> insertBatchImportCodisSample(String consignOrganizationId, String reagentKitId,
			List<List<Map<String, Object>>> allCodisFolderInfoList, String sysUserId) throws Exception {

		for(List<Map<String, Object>> singleCodisFolderList : allCodisFolderInfoList){
			for(Map<String, Object> codisFileMap : singleCodisFolderList){
				if(null != codisFileMap.get("successFlag")
						&& "0".equals(codisFileMap.get("successFlag"))){
					continue;
				}

				String codisFileName = (String) codisFileMap.get("codisFileName");
				List<FileStrInfo> fileStrInfoList = (List<FileStrInfo>) codisFileMap.get("sampleStrInfoList");
				String boardNo = codisFileName.substring(0, codisFileName.lastIndexOf('.'));

				try {
					List<Map<String, Object>> resultMapList = importSingleCodisFile(fileStrInfoList, consignOrganizationId, reagentKitId, boardNo, sysUserId);
					codisFileMap.put("successFlag", "1");
					codisFileMap.put("importResult", resultMapList);
				} catch(Exception e) {
					codisFileMap.put("successFlag", "0");
					codisFileMap.put("errorMessage", e.getMessage());
				}
			}
		}

		return allCodisFolderInfoList;
	}

	private List<Map<String, Object>> importSingleCodisFile(List<FileStrInfo> fileStrInfoList, String consignOrganizationId, String reagentKitId, String boardNo, String sysUserId) throws Exception {
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = null;

		SampleInfoView sampleInfo = null;
		CodisSample tempCodisSample = null;
		List<CodisSample> codisSampleList = new ArrayList<CodisSample>();
		List<GeneInfo> geneInfoList = new ArrayList<GeneInfo>();
		Date currentDate = new Date(System.currentTimeMillis());

		CodisSample cs = null;
		GeneInfo geneInfo = null;
		for(FileStrInfo fileStrInfo : fileStrInfoList){
			resultMap = new HashMap<String, Object>();
			String sampleNo = fileStrInfo.getSampleNo();

			resultMap.put("sampleNo", sampleNo);

			if(StringUtil.isNotEmpty(fileStrInfo.getMessage())){
				resultMap.put("flag", "0");
				resultMap.put("message", fileStrInfo.getMessage());
				resultMapList.add(resultMap);

				continue;
			}

			sampleInfo = sampleInfoService.findSampleInfoBySampleNo(sampleNo);
			tempCodisSample = codisSampleService.findCodisSampleBySampleNo(sampleNo);
			if(sampleInfo != null || tempCodisSample != null){
				String constainsSampleBoardNo = "";
				if(sampleInfo != null){
					List<BoardInfo> boardInfoList = dao.getSqlSession().selectList("findBoardInfoListBySampleId", sampleInfo.getId());
					if(!ListUtil.isEmptyList(boardInfoList)){
						for(BoardInfo bi : boardInfoList){
							constainsSampleBoardNo += bi.getBoardNo() + ", ";
						}
						
						constainsSampleBoardNo = constainsSampleBoardNo.substring(0, constainsSampleBoardNo.length()-1);
					}
					
				}
				
				if(tempCodisSample != null){
					if(!"".equals(constainsSampleBoardNo)){
						constainsSampleBoardNo += ",";
					}
					
					 constainsSampleBoardNo += tempCodisSample.getBoardInfoNo();
				}
				resultMap.put("flag", "0");
				resultMap.put("message", "样本编号重复！重复样本所在板号：" + constainsSampleBoardNo);
				resultMapList.add(resultMap);

				continue;
			}

			cs = new CodisSample();
			cs.setId(keyGeneratorService.getNextKey());
			cs.setSampleNo(sampleNo);
			cs.setBoardInfoNo(boardNo);
			cs.setConsignOrganizationId(consignOrganizationId);
			cs.setImportDate(currentDate);
			cs.setImportUser(sysUserId);
			cs.setReagentKit(reagentKitId);
			codisSampleList.add(cs);

			geneInfo = new GeneInfo();
			geneInfo.setId(keyGeneratorService.getNextKey());
			geneInfo.setSampleId(cs.getId());
			geneInfo.setGeneType(LimsConstants.GENE_TYPE_STR);
			geneInfo.setGenotypeInfo(convertGenotypeStr(fileStrInfo.getLocusValuesList()));
			geneInfo.setSampleType(GeneInfo.SAMPLE_TYPE_CODIS);
			geneInfo.setStoreUser(sysUserId);
			geneInfo.setStoreDate(currentDate);
			geneInfo.setReagentKit(reagentKitId);
			geneInfoList.add(geneInfo);

			resultMap.put("flag", "1");
			resultMap.put("message", "导入成功");
			resultMapList.add(resultMap);
		}

		if(codisSampleList.size() > 0){
			codisSampleService.insertImportCodisSample(codisSampleList, geneInfoList);
		}

		return resultMapList;
	}

	private String convertGenotypeStr(List<LocusValues> locusValuesList) throws Exception {
		String resultStr = "";

		List<LocusInfo> locusInfoList = locusInfoService.findAllLocusInfoList();
		for(LocusInfo locusInfo : locusInfoList){
			String locusInfoNameInDb = locusInfo.getGenoName();
			String locusInfoAliasInDb = locusInfo.getGenoAlias();

			boolean hasLocusInCodis = false;
			for(LocusValues lv : locusValuesList){
				String locusNameInCodis = lv.getLocusName();
				if(locusNameInCodis.equalsIgnoreCase(locusInfoNameInDb)
						|| (locusInfoAliasInDb != null
								&& locusInfoAliasInDb.toUpperCase().indexOf(locusNameInCodis.toUpperCase()) > -1)){
					resultStr += lv.getAlleleValue1() + "/" + lv.getAlleleValue2() + ";";
					hasLocusInCodis = true;
					break;
				}
			}

			if(!hasLocusInCodis){
				resultStr += "/;";
			}
		}

		resultStr = resultStr.substring(0, resultStr.length()-1);

		return resultStr;
	}

}
