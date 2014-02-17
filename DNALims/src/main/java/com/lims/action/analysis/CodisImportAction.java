/**
 * CodisImportAction.java
 *
 * 2013-7-8
 */
package com.lims.action.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.codis.helper.CodisFileParser;
import com.lims.codis.helper.FileStrInfo;
import com.lims.codis.helper.LocusValues;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.ReagentKit;
import com.lims.domain.po.SampleSourceGene;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.service.gene.SampleSourceGeneService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.locus.ReagentKitService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * CODIS导入 Action
 *
 */
public class CodisImportAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
//	private final static String TARGET_FILE_TYPE_FILE = "0";				//文件
//	private final static String TARGET_FILE_TYPE_FOLDER = "1";				//文件夹
//
//	private String targetFileType;

	private File codisFile;

	private String codisFileFileName;

	private String codisPath;

	private String boardNo;
	private String sampleNo;

	private SampleSourceGeneView sampleSourceGeneView;

	private List<ReagentKit> reagentKitList;

	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private SampleSourceGeneService sampleSourceGeneService;
	@Resource
	private ReagentKitService reagentKitService;

	private List<Map<String, String>> importResultList;

	private Map<String, String> resultDetail;

	private String importSucceedFlag;

	private String reagentKitId;

	private Map<String, Object> jsonData;

	private List<Map<String, Object>> qualityCompareResultList;
	
	private String allLocusNameStr;
	private String qualitySampleGeneStr;
	private String matchSampleGeneStr;
	
	/**
	 * 进入CODIS导入页面
	 * @return
	 * @throws Exception
	 */
	public String intoCodisImport() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();

		return SUCCESS;
	}
	
	public String checkBoardExists() throws Exception {
		jsonData = new HashMap<String, Object>();
		
		BoardInfoQuery boardInfoQuery = new BoardInfoQuery();
		boardInfoQuery.setBoardNo(boardNo.trim());
		List<BoardInfoView> boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);
		
		if(ListUtil.isEmptyList(boardInfoList)){
			jsonData.put("boardExistsFlag", false);
			jsonData.put("examineFlag", false);
		}else{
			jsonData.put("boardExistsFlag", true);
			
			BoardInfoView boardInfo = boardInfoList.get(0);
			if(boardInfo != null){
				if(boardInfo.getExamineStatus() != null && 
						Integer.parseInt(boardInfo.getExamineStatus()) > Integer.parseInt(LimsConstants.EXAMINE_STATUS_EXTRACTED)){
					jsonData.put("examineFlag", true);
				}else{
					jsonData.put("examineFlag", false);
				}
			}
		}
		
		return SUCCESS;
	}


	/**
	 * 提交CODIS导入
	 * @return
	 * @throws Exception
	 */
	public String submitCodisImport() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();
		
		BoardInfoQuery boardInfoQuery = new BoardInfoQuery();
		boardInfoQuery.setBoardNo(boardNo.trim());
		List<BoardInfoView> boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		BoardInfoView boardInfo = null;
		if(boardInfoList != null && boardInfoList.size() > 0){
			boardInfo = boardInfoList.get(0);
		}
		
		//上样板的检验类型为质检时，将质检板中的样本与已导入的同编号样本的结果进行同型比对；首检、复检的板则直接导入
		if(LimsConstants.EXAMINE_TYPE_QUALITY.equals(boardInfo.getExamineType())){
			quailtyBoardCompare(boardInfo);
			
			return "qualityResult";
		}else{
			importSingleCodisFile(boardInfo);
			
			return "importResult";
		}

	}

	private void importSingleCodisFile(BoardInfoView boardInfo) throws Exception {
		SysUser sysUser = getLoginSysUser();
		importResultList = new ArrayList<Map<String, String>>();
		resultDetail = new HashMap<String, String>();
		resultDetail.put("fileName", codisFileFileName);

		List<FileStrInfo> fileStrInfoList = null;

		try {
			fileStrInfoList = CodisFileParser.getDataFromDat(this.codisFile);
			importSucceedFlag = "1";
		} catch(Exception e) {
			importSucceedFlag = "0";

			resultDetail.put("importedSampleCount", "0");
			resultDetail.put("importFailedSampleCount", "0");
			resultDetail.put("errorMessage", e.getMessage());

			return;
		}

		Map<String, String> importResult = null;
		String consignmentNo = boardInfo.getConsignmentNo();

		int importedSampleCount = 0;
		int importFailedSampleCount = 0;
		SampleInfoView sampleInfo = null;
		List<SampleSourceGene> sourceGeneList = new ArrayList<SampleSourceGene>();
		for(FileStrInfo fileStrInfo : fileStrInfoList){
			String sampleNo = fileStrInfo.getSampleNo();

			importResult = new HashMap<String, String>();
			importResult.put("consignmentNo", (consignmentNo == null ? "无" : consignmentNo));
			importResult.put("sampleNo", sampleNo);

			if(StringUtil.isNotEmpty(fileStrInfo.getMessage())){
				importResult.put("flag", "0");
				importResult.put("message", fileStrInfo.getMessage());
				importResultList.add(importResult);

				importFailedSampleCount++;
				continue;
			}

			sampleInfo = sampleInfoService.findSampleInfoBySampleNo(sampleNo);
			if(sampleInfo == null){
				importResult.put("flag", "0");
				importResult.put("message", "样本编号不存在");
				importResultList.add(importResult);

				importFailedSampleCount++;
				continue;
			}

			SampleSourceGene sourceGene = new SampleSourceGene();
			sourceGene.setSampleId(sampleInfo.getId());
			sourceGene.setBoardId(boardInfo.getId());
			sourceGene.setGeneType(LimsConstants.GENE_TYPE_STR);
			sourceGene.setReviewStatus(LimsConstants.REVIEW_STATUS_WAITING);
			sourceGene.setGenotypeInfo(convertGenotypeStr(fileStrInfo.getLocusValuesList()));
			sourceGene.setCreateUser(sysUser.getId());
			sourceGene.setAlignDbFlag(LimsConstants.FLAG_FALSE);
			sourceGene.setReagentKitId(reagentKitId);

			sourceGeneList.add(sourceGene);

			importResult.put("flag", "1");
			importResult.put("message", "导入成功");
			importResultList.add(importResult);

			importedSampleCount++;
		}

		if(sourceGeneList.size() > 0){
			sampleSourceGeneService.insertSampleSourceGeneList(sourceGeneList);

			boardInfo.setImportResultFlag(LimsConstants.FLAG_TRUE);
			boardInfo.setImportResultDate(new Date());
			boardInfoService.updateBoardInfo(boardInfo, sysUser);
		}

		resultDetail.put("importedSampleCount", String.valueOf(importedSampleCount));
		resultDetail.put("importFailedSampleCount", String.valueOf(importFailedSampleCount));
		
	}
	
	/**
	 * 质检比对
	 * @param boardInfo
	 * @throws Exception
	 */
	public void quailtyBoardCompare(BoardInfoView boardInfo) throws Exception {
		List<FileStrInfo> fileStrInfoList = null;
		resultDetail = new HashMap<String, String>();
		resultDetail.put("fileName", codisFileFileName);
		
		try {
			fileStrInfoList = CodisFileParser.getDataFromDat(this.codisFile);
			resultDetail.put("codisSampleCount", String.valueOf(fileStrInfoList.size()));
		} catch(Exception e) {
			resultDetail.put("codisSampleCount", "0");
			resultDetail.put("errorMessage", e.getMessage());
			return;
		}
		
		List<String> sampleNoList = new ArrayList<String>();
		for(FileStrInfo fsi : fileStrInfoList){
			sampleNoList.add(fsi.getSampleNo());
		}
		
		List<SampleSourceGeneView> sampleSourceGeneViewList = sampleSourceGeneService.findSampleSourceGeneBySampleNoList(sampleNoList);		
		qualityCompareResultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> qualityCompareMap = null;
		String sampleNo = null;
		String parseMessage = null;
		String genotypeInfo = null;
		for(FileStrInfo fileStrInfo : fileStrInfoList){
			qualityCompareMap = new HashMap<String, Object>();
			sampleNo = fileStrInfo.getSampleNo();
			qualityCompareMap.put("sampleNo", sampleNo);
			
			parseMessage = fileStrInfo.getMessage();
			if(StringUtil.isNotEmpty(parseMessage)){
				qualityCompareMap.put("compareFlag", "0");
				qualityCompareMap.put("hintMessage", parseMessage);
				
				qualityCompareResultList.add(qualityCompareMap);
				continue;
			}
			
			genotypeInfo = convertGenotypeStr(fileStrInfo.getLocusValuesList());
			qualityCompareMap.put("qualitySampleGeneStr", genotypeInfo);
			boolean hasSample = false;
			//判断基因型是否相同
			for(SampleSourceGeneView ssg : sampleSourceGeneViewList){
				if(sampleNo.equals(ssg.getSampleNo())){
					hasSample = true;
					
					qualityCompareMap.put("compareFlag", "1");
					qualityCompareMap.put("matchSampleNo", ssg.getSampleNo());
					qualityCompareMap.put("matchBoardNo", ssg.getBoardNo());
					qualityCompareMap.put("matchSampleGeneStr", ssg.getGenotypeInfo());
					if(genotypeInfo.equals(ssg.getGenotypeInfo())){
						qualityCompareMap.put("matchFlag", "1");
					}else{
						qualityCompareMap.put("matchFlag", "0");
					}
					
					qualityCompareResultList.add(qualityCompareMap);
					//这里不能break；因为同一个样本会存在多次结果
					//break;
				}
			}
			
			if(!hasSample){
				qualityCompareMap.put("compareFlag", "0");
				qualityCompareMap.put("hintMessage", "数据库不存在该样本的结果记录，无法比对！");
				qualityCompareResultList.add(qualityCompareMap);
			}
		}
	}
	
	public String viewQualityCompareGene() throws Exception {
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		
		allLocusNameStr = "";
		for(LocusInfo locusInfo : allLocusInfoList){
			allLocusNameStr += locusInfo.getGenoName() + ";";
		}
		allLocusNameStr = allLocusNameStr.substring(0, allLocusNameStr.length()-1);
		
		return SUCCESS;
	}

	public String viewImportedSampleGene() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();
		SampleSourceGeneQuery sgq = new SampleSourceGeneQuery();
		sgq.setBoardNo(boardNo);
		sgq.setSampleNo(sampleNo);

		List<SampleSourceGeneView> sampleSourceGeneViewList = sampleSourceGeneService.findSampleSourceGeneViewList(sgq);
		if(sampleSourceGeneViewList != null){
			sampleSourceGeneView = sampleSourceGeneViewList.get(0);

			ReagentKit rk = StringUtil.isNullOrEmpty(sampleSourceGeneView.getReagentKitId())
						? null
							: reagentKitService.findReagentKitById(sampleSourceGeneView.getReagentKitId());
			List<String> locusOrderOfReagent = null;
			List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
			if(rk != null) {
				locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
			}

			String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, locusOrderOfReagent, sampleSourceGeneView.getGenotypeInfo());
			sampleSourceGeneView.setGeneInfoHtmlForPage(geneInfoHtml);
		}else{
			sampleSourceGeneView = new SampleSourceGeneView();
		}

		return SUCCESS;
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
					resultStr += lv.getAlleleValue1() + "/" + lv.getAlleleValue2() + "/" + lv.getAlleleValue3() + ";";
					hasLocusInCodis = true;
					break;
				}
			}

			if(!hasLocusInCodis){
				resultStr += "//;";
			}
		}

		resultStr = resultStr.substring(0, resultStr.length()-1);

		return resultStr;
	}

	public File getCodisFile() {
		return codisFile;
	}


	public void setCodisFile(File codisFile) {
		this.codisFile = codisFile;
	}


	public String getCodisFileFileName() {
		return codisFileFileName;
	}


	public void setCodisFileFileName(String codisFileFileName) {
		this.codisFileFileName = codisFileFileName;
	}


	public String getCodisPath() {
		return codisPath;
	}


	public void setCodisPath(String codisPath) {
		this.codisPath = codisPath;
	}


	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}


	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
	}


	public List<Map<String, String>> getImportResultList() {
		return importResultList;
	}


	public void setImportResultList(List<Map<String, String>> importResultList) {
		this.importResultList = importResultList;
	}


	public Map<String, String> getResultDetail() {
		return resultDetail;
	}


	public void setResultDetail(Map<String, String> resultDetail) {
		this.resultDetail = resultDetail;
	}


	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}


	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}


	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}


	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
	}


	public SampleSourceGeneService getSampleSourceGeneService() {
		return sampleSourceGeneService;
	}


	public void setSampleSourceGeneService(
			SampleSourceGeneService sampleSourceGeneService) {
		this.sampleSourceGeneService = sampleSourceGeneService;
	}


	public String getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}


	public String getImportSucceedFlag() {
		return importSucceedFlag;
	}


	public void setImportSucceedFlag(String importSucceedFlag) {
		this.importSucceedFlag = importSucceedFlag;
	}


	public String getSampleNo() {
		return sampleNo;
	}


	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}


	public SampleSourceGeneView getSampleSourceGeneView() {
		return sampleSourceGeneView;
	}


	public void setSampleSourceGeneView(SampleSourceGeneView sampleSourceGeneView) {
		this.sampleSourceGeneView = sampleSourceGeneView;
	}


	public List<ReagentKit> getReagentKitList() {
		return reagentKitList;
	}


	public void setReagentKitList(List<ReagentKit> reagentKitList) {
		this.reagentKitList = reagentKitList;
	}


	public ReagentKitService getReagentKitService() {
		return reagentKitService;
	}


	public void setReagentKitService(ReagentKitService reagentKitService) {
		this.reagentKitService = reagentKitService;
	}


	public String getReagentKitId() {
		return reagentKitId;
	}


	public void setReagentKitId(String reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public List<Map<String, Object>> getQualityCompareResultList() {
		return qualityCompareResultList;
	}

	public void setQualityCompareResultList(
			List<Map<String, Object>> qualityCompareResultList) {
		this.qualityCompareResultList = qualityCompareResultList;
	}

	public String getQualitySampleGeneStr() {
		return qualitySampleGeneStr;
	}

	public void setQualitySampleGeneStr(String qualitySampleGeneStr) {
		this.qualitySampleGeneStr = qualitySampleGeneStr;
	}

	public String getMatchSampleGeneStr() {
		return matchSampleGeneStr;
	}

	public void setMatchSampleGeneStr(String matchSampleGeneStr) {
		this.matchSampleGeneStr = matchSampleGeneStr;
	}

	public String getAllLocusNameStr() {
		return allLocusNameStr;
	}

	public void setAllLocusNameStr(String allLocusNameStr) {
		this.allLocusNameStr = allLocusNameStr;
	}

}
