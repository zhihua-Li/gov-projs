/**
 * BatchImportCodisAction.java
 *
 * 2013-10-7
 */
package com.lims.action.comparison;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.codis.helper.CodisFileParser;
import com.lims.codis.helper.FileStrInfo;
import com.lims.codis.helper.LocusValues;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.CodisSample;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.GeneInfo;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.ReagentKit;
import com.lims.domain.po.SysUser;
import com.lims.domain.vo.GeneInfoView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.service.KeyGeneratorService;
import com.lims.service.codissample.CodisSampleService;
import com.lims.service.gene.GeneInfoService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.locus.ReagentKitService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.sample.SampleInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class BatchImportCodisAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//委托单位列表
	private List<ConsignOrganization> consignOrganizationList;

	private List<SysUser> sysUserList;

	private List<ReagentKit> reagentKitList;

    @Resource
	private ConsignOrganizationService consignOrganizationService;
	@Resource
	private ReagentKitService reagentKitService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private CodisSampleService codisSampleService;
	@Resource
	private KeyGeneratorService keyGeneratorService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private GeneInfoService geneInfoService;

	private File codisFile;

	private String codisFileFileName;

	private List<Map<String, String>> importResultList;

	private Map<String, String> resultDetail;

	private String importSucceedFlag;

	private String reagentKitId;

	private String consignOrganizationId;

	private CodisSample codisSample;

	private List<CodisSample> codisSampleList;

	private Map<String, Object> jsonData;

	private String checkedSampleNo;

	private GeneInfoView geneInfoView;

	private GeneInfo geneInfo;

	private List<String> alleleValue1;

	private List<String> alleleValue2;

	private List<String> alleleValue3;



	public String into() throws Exception {
		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
		reagentKitList = reagentKitService.finalAllReagentKitList();

		return SUCCESS;
	}


	public String submit() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();
		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();

		String consignOrganizationName = null;
		for(ConsignOrganization co : consignOrganizationList){
			if(consignOrganizationId.equals(co.getId())){
				consignOrganizationName = co.getOrganizationName();
				break;
			}
		}

		String boardNo = codisFileFileName.substring(0, codisFileFileName.lastIndexOf('.'));
		importSingleCodisFile(consignOrganizationName, boardNo);

		return SUCCESS;
	}

	/**
	 * 删除样本
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		jsonData = new HashMap<String, Object>();

		try {
			String[] checkedSampleNoArr = checkedSampleNo.split(",");
			codisSampleService.deleteSamplesBySampleNoArr(checkedSampleNoArr);

			jsonData.put("success", true);
		} catch (Exception e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());
		}


		return SUCCESS;
	}

	public String update() throws Exception {
		jsonData = new HashMap<String, Object>();

		try {
			codisSampleService.updateCodisSample(codisSample);

			jsonData.put("success", true);
		} catch (Exception e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());
		}


		return SUCCESS;
	}

	public String query() throws Exception {
		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
		sysUserList = sysUserService.findSysUserList(new SysUser());

		if(codisSample == null){
			codisSample = new CodisSample();

			codisSample.setSortColumn("importDate");
			codisSample.setSortOrder("DESC");
		}else{
			if(StringUtil.isNullOrEmpty(codisSample.getSampleNo())){
				codisSample.setSampleNo(null);
			}else{
				codisSample.setSampleNo(codisSample.getSampleNo().trim());
			}

			if(StringUtil.isNullOrEmpty(codisSample.getConsignOrganizationId())){
				codisSample.setConsignOrganizationId(null);
			}else{
				codisSample.setConsignOrganizationId(codisSample.getConsignOrganizationId().trim());
			}

			if(StringUtil.isNullOrEmpty(codisSample.getBoardInfoNo())){
				codisSample.setBoardInfoNo(null);
			}else{
				codisSample.setBoardInfoNo(codisSample.getBoardInfoNo().trim());
			}

			if(StringUtil.isNullOrEmpty(codisSample.getImportUser())){
				codisSample.setImportUser(null);
			}else{
				codisSample.setImportUser(codisSample.getImportUser().trim());
			}
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		codisSample.setStartRowNum((pageIndex-1)*pageSize + 1);
		codisSample.setEndRowNum(pageIndex*pageSize);

		recordCount = codisSampleService.findCodisSampleListCount(codisSample);
		codisSampleList = codisSampleService.findCodisSampleList(codisSample);

		return SUCCESS;
	}


	public String viewCodisSampleGene() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();
		geneInfo = codisSampleService.findCodisSampleGeneInfoBySampleId(codisSample.getId());
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();

		ReagentKit rk = StringUtil.isNullOrEmpty(geneInfo.getReagentKit())
					? null
						: reagentKitService.findReagentKitById(geneInfo.getReagentKit());
		List<String> locusOrderOfReagent = null;
		if(rk != null) {
			locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
		}

		String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, locusOrderOfReagent, geneInfo.getGenotypeInfo());
		geneInfo.setGeneInfoHtmlForPage(geneInfoHtml);

		return SUCCESS;
	}

	public String changeCodisSampleGeneReagentKit() throws Exception {
		geneInfo = codisSampleService.findCodisSampleGeneInfoById(geneInfo.getId());
		ReagentKit rk = StringUtil.isNullOrEmpty(geneInfo.getReagentKit())
							? null
								: reagentKitService.findReagentKitById(geneInfo.getReagentKit());
		List<String> locusOrderOfReagent = null;
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		if(rk != null) {
			locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
		}

		String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, locusOrderOfReagent, geneInfo.getGenotypeInfo());

		HttpServletResponse response = getServletResponse();
		PrintWriter pw = response.getWriter();
		pw.write(geneInfoHtml);
		pw.flush();

		return NONE;
	}

	public String saveUpdatedCodisSampleGene() throws Exception {
		SysUser sysUser = getLoginSysUser();
		ReagentKit rk = StringUtil.isNullOrEmpty(geneInfo.getReagentKit())
							? null
								: reagentKitService.findReagentKitById(geneInfo.getReagentKit());

		List<String> locusOrderOfReagent = null;
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		if(rk != null) {
			locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
		}

		String genotypeInfoStr = GeneInfoTableConverter.convertGeneInfoToDbStr(allLocusInfoList, locusOrderOfReagent,
										alleleValue1, alleleValue2, alleleValue3);
		geneInfo.setGenotypeInfo(genotypeInfoStr);

		geneInfo.setUpdateUser(sysUser.getId());
		geneInfoService.updateGeneInfo(geneInfo);

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);

		return SUCCESS;
	}

	private void importSingleCodisFile(String consignOrganizationName, String boardNo) throws Exception {
		SysUser sysUser = getLoginSysUser();
		importResultList = new ArrayList<Map<String, String>>();
		resultDetail = new HashMap<String, String>();
		resultDetail.put("fileName", codisFileFileName);

		List<FileStrInfo> fileStrInfoList = null;

		try {
			fileStrInfoList = CodisFileParser.getDataFromDat(this.codisFile);
		} catch(Exception e) {
			importSucceedFlag = "0";

			resultDetail.put("importedSampleCount", "0");
			resultDetail.put("importFailedSampleCount", "0");
			resultDetail.put("errorMessage", e.getMessage());

			return;
		}

		Map<String, String> importResult = null;
		importSucceedFlag = "1";
		int importFailedSampleCount = 0;
		SampleInfoView sampleInfo = null;
		List<CodisSample> codisSampleList = new ArrayList<CodisSample>();
		List<GeneInfo> geneInfoList = new ArrayList<GeneInfo>();
		Date currentDate = new Date(System.currentTimeMillis());

		CodisSample cs = null;
		GeneInfo geneInfo = null;
		for(FileStrInfo fileStrInfo : fileStrInfoList){
			String sampleNo = fileStrInfo.getSampleNo();

			importResult = new HashMap<String, String>();
			importResult.put("consignOrganizationName", consignOrganizationName);
			importResult.put("sampleNo", sampleNo);
			importResult.put("boardNo", boardNo);


			if(StringUtil.isNotEmpty(fileStrInfo.getMessage())){
				importResult.put("flag", "0");
				importResult.put("message", fileStrInfo.getMessage());
				importResultList.add(importResult);

				importFailedSampleCount++;
				continue;
			}

			sampleInfo = sampleInfoService.findSampleInfoBySampleNo(sampleNo);
			codisSample = codisSampleService.findCodisSampleBySampleNo(sampleNo);
			if(sampleInfo != null || codisSample != null){
				importResult.put("flag", "0");
				importResult.put("message", "样本编号重复，样本已导入！");
				importResultList.add(importResult);

				importFailedSampleCount++;
				sampleInfo = null;
				codisSample = null;

				continue;
			}

			cs = new CodisSample();
			cs.setId(keyGeneratorService.getNextKey());
			cs.setSampleNo(sampleNo);
			cs.setBoardInfoNo(boardNo);
			cs.setConsignOrganizationId(consignOrganizationId);
			cs.setImportDate(currentDate);
			cs.setImportUser(sysUser.getId());
			cs.setReagentKit(reagentKitId);
			codisSampleList.add(cs);

			geneInfo = new GeneInfo();
			geneInfo.setId(keyGeneratorService.getNextKey());
			geneInfo.setSampleId(cs.getId());
			geneInfo.setGeneType(LimsConstants.GENE_TYPE_STR);
			geneInfo.setGenotypeInfo(convertGenotypeStr(fileStrInfo.getLocusValuesList()));
			geneInfo.setSampleType(GeneInfo.SAMPLE_TYPE_CODIS);
			geneInfo.setStoreUser(sysUser.getId());
			geneInfo.setStoreDate(currentDate);
			geneInfo.setReagentKit(reagentKitId);

			geneInfoList.add(geneInfo);

			importResult.put("flag", "1");
			importResult.put("message", "导入成功");
			importResultList.add(importResult);
		}

		if(codisSampleList.size() > 0){
			codisSampleService.insertImportCodisSample(codisSampleList, geneInfoList);
		}

		resultDetail.put("importedSampleCount", String.valueOf(codisSampleList.size()));
		resultDetail.put("importFailedSampleCount", String.valueOf(importFailedSampleCount));

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


	public List<ConsignOrganization> getConsignOrganizationList() {
		return consignOrganizationList;
	}


	public void setConsignOrganizationList(
			List<ConsignOrganization> consignOrganizationList) {
		this.consignOrganizationList = consignOrganizationList;
	}


	public List<ReagentKit> getReagentKitList() {
		return reagentKitList;
	}


	public void setReagentKitList(List<ReagentKit> reagentKitList) {
		this.reagentKitList = reagentKitList;
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


	public String getImportSucceedFlag() {
		return importSucceedFlag;
	}


	public void setImportSucceedFlag(String importSucceedFlag) {
		this.importSucceedFlag = importSucceedFlag;
	}


	public String getReagentKitId() {
		return reagentKitId;
	}


	public void setReagentKitId(String reagentKitId) {
		this.reagentKitId = reagentKitId;
	}


	public String getConsignOrganizationId() {
		return consignOrganizationId;
	}


	public void setConsignOrganizationId(String consignOrganizationId) {
		this.consignOrganizationId = consignOrganizationId;
	}


	public CodisSample getCodisSample() {
		return codisSample;
	}


	public void setCodisSample(CodisSample codisSample) {
		this.codisSample = codisSample;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public List<CodisSample> getCodisSampleList() {
		return codisSampleList;
	}


	public void setCodisSampleList(List<CodisSample> codisSampleList) {
		this.codisSampleList = codisSampleList;
	}


	public List<SysUser> getSysUserList() {
		return sysUserList;
	}


	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}


	public String getCheckedSampleNo() {
		return checkedSampleNo;
	}


	public void setCheckedSampleNo(String checkedSampleNo) {
		this.checkedSampleNo = checkedSampleNo;
	}


	public GeneInfoView getGeneInfoView() {
		return geneInfoView;
	}


	public void setGeneInfoView(GeneInfoView geneInfoView) {
		this.geneInfoView = geneInfoView;
	}


	public GeneInfo getGeneInfo() {
		return geneInfo;
	}


	public void setGeneInfo(GeneInfo geneInfo) {
		this.geneInfo = geneInfo;
	}


	public List<String> getAlleleValue1() {
		return alleleValue1;
	}


	public void setAlleleValue1(List<String> alleleValue1) {
		this.alleleValue1 = alleleValue1;
	}


	public List<String> getAlleleValue2() {
		return alleleValue2;
	}


	public void setAlleleValue2(List<String> alleleValue2) {
		this.alleleValue2 = alleleValue2;
	}


	public List<String> getAlleleValue3() {
		return alleleValue3;
	}


	public void setAlleleValue3(List<String> alleleValue3) {
		this.alleleValue3 = alleleValue3;
	}
}
