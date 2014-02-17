/**
 * SampleAcceptAction.java
 *
 * 2013-6-2
 */
package com.lims.action.sample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.lims.action.BaseAction;
import com.lims.action.helper.SampleExcelParser;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.Consignment;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.SampleInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.domain.vo.ConsignmentView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.exception.ServiceException;
import com.lims.service.AcceptionRecordGenerator;
import com.lims.service.KeyGeneratorService;
import com.lims.service.consignment.ConsignmentService;
import com.lims.service.dict.SysDictService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.sample.SampleInfoService;
import com.lims.util.ConfigUtil;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 样本受理Action
 *
 */
public class SampleAcceptAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//委托单位列表
	private List<ConsignOrganization> consignOrganizationList;

	private List<DictItem> consignModeList;			/** 送检方式 */
	private List<DictItem> sampleTypeList;			/** 样本类型 */
	private List<DictItem> checkContentList;		/** 检验内容 */
	private List<DictItem> sampleCarrierList;		/** 样本载体 */

	private Consignment consignment;

    private String[] consignmentSampleTypeStr;

    private String[] consignmentSampleCountStr;

//    private List<String> scanedSample;

    private SampleInfoQuery sampleInfoQuery;

    private SampleInfo sampleInfo;

    private List<SampleInfoView> sampleInfoList;

    @Resource
	private SysDictService sysDictService;
    @Resource
	private ConsignOrganizationService consignOrganizationService;
    @Resource
	private ConsignmentService consignmentService;
    @Resource
    private SampleInfoService sampleInfoService;
    @Resource
    private KeyGeneratorService keyGeneratorService;

	private Map<String, Object> jsonData;

	private String acceptFileName;
	private InputStream inputStream;

	private File sampleExcelFile;

	private String sampleExcelFileFileName;

	private List<Map<String, String>> importFailureList;

	private List<SampleInfo> importSucceedList;

	private Map<String, Object> resultDetail;


	private String barcodePrefix;

	private int serialNoLength;

	private String barcodeValStart;

	private String barcodeValEnd;

	public String into() throws Exception {
		initDictInfo();

		if(consignment == null){
			consignment = new Consignment();
			consignment.setConsignDate(new Date());
		}

		return SUCCESS;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		boolean isSucceed = true;
		String errorMsg = "";
		jsonData = new HashMap<String, Object>();

//		consignment.setConsignSampleType(StringUtil.arrayToStringIgnoreNull(consignmentSampleTypeStr, ","));
//		consignment.setConsignSampleCount(StringUtil.arrayToStringIgnoreNull(consignmentSampleCountStr, ","));
		consignment.setCreateDate(DateUtil.currentDate());
		consignment.setCreateUser(getLoginSysUser().getId());
		try {
//			consignment = consignmentService.addConsignmentInfo(consignment, scanedSample);
			consignment.setConsignmentNo(consignment.getConsignmentNo().toUpperCase());
			Consignment tempConsignment = consignmentService.findConsignmentByNo(consignment.getConsignmentNo());
			if(tempConsignment != null){
				jsonData.put("success", false);
				jsonData.put("errorMsg", "委托编号重复，请重新输入！");
				return SUCCESS;
			}

			consignment = consignmentService.addConsignmentInfo(consignment);
			jsonData.put("consignmentNo", consignment.getConsignmentNo());
			jsonData.put("consignmentId", consignment.getId());
		} catch(ServiceException se) {
			isSucceed = false;
			errorMsg = "保存委托信息失败， 原因： 数据库异常！";

			jsonData.put("errorMsg", errorMsg);
		}

		jsonData.put("success", isSucceed);

		return SUCCESS;
	}


	/**
	 * 进入样本扫描页面
	 * @return
	 * @throws Exception
	 */
	public String intoScaneSamples() throws Exception {
		sampleInfoQuery.setAcceptStatus(LimsConstants.FLAG_TRUE);
		sampleInfoQuery.setStartRowNum(1);
		sampleInfoQuery.setEndRowNum(20);
		sampleInfoList = sampleInfoService.findSampleInfoViewList(sampleInfoQuery);
		recordCount = sampleInfoService.findSampleInfoViewListCount(sampleInfoQuery);

		return SUCCESS;
	}


	/**
	 * 保存扫描样本
	 * @return
	 * @throws Exception
	 */
	public String saveScanedSample() throws Exception {
		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();

		SampleInfoView tempSample = sampleInfoService.findSampleInfoBySampleNo(sampleInfo.getSampleNo().trim());
		if(tempSample == null){
			Date currentDate = DateUtil.currentDate();
			sampleInfo.setSampleName(sampleInfo.getSampleNo());
			sampleInfo.setCreateUser(sysUser.getId());
			sampleInfo.setCreateDate(currentDate);
			sampleInfo.setAcceptStatus(LimsConstants.FLAG_TRUE);
			sampleInfo.setAcceptDate(currentDate);
			sampleInfo.setAcceptUser(sysUser.getId());

			String sampleId = sampleInfoService.addSampleInfo(sampleInfo);

			jsonData.put("success", true);
			jsonData.put("hasRepeat", false);
			jsonData.put("id", sampleId);
			jsonData.put("sampleNo", sampleInfo.getSampleNo());
			jsonData.put("acceptDate", DateUtil.dateToString(currentDate, "yyyy-MM-dd HH:mm:ss"));
			jsonData.put("acceptUserName", sysUser.getFullName());
			jsonData.put("sampleCnt", sampleInfoService.findSampleInfoCountByConsignmentId(sampleInfo.getConsignmentId()));
		}else{
			if(LimsConstants.FLAG_FALSE.equals(tempSample.getAcceptStatus())){
				tempSample.setAcceptStatus(LimsConstants.FLAG_TRUE);
				tempSample.setAcceptUser(sysUser.getId());
				Date updateDate = new Date(System.currentTimeMillis());
				tempSample.setAcceptDate(updateDate);
				tempSample.setUpdateDate(updateDate);
				tempSample.setUpdateUser(sysUser.getId());
				BeanUtils.copyProperties(tempSample, sampleInfo);
				sampleInfoService.updateSampleInfo(sampleInfo);

				jsonData.put("success", true);
				jsonData.put("id", tempSample.getId());
				jsonData.put("sampleNo", tempSample.getSampleNo());
				jsonData.put("acceptDate", DateUtil.dateToString(tempSample.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
				jsonData.put("acceptUserName", tempSample.getCreateUserName());
				jsonData.put("sampleCnt", sampleInfoService.findSampleInfoCountByConsignmentId(tempSample.getConsignmentId()));
			}else{
				jsonData.put("success", false);
				jsonData.put("hasRepeat", true);
			}
		}

		return SUCCESS;
	}

	/**
	 * 进入批量受理样本页面
	 * @return
	 * @throws Exception
	 */
	public String intoBatchAcceptSamples() throws Exception {

		return SUCCESS;
	}

	public String submitSampleBatchAccept() throws Exception {

		int barcodeValStartIntVal = Integer.parseInt(barcodeValStart);
		int barcodeValEndIntVal = Integer.parseInt(barcodeValEnd);

		List<SampleInfo> tobeAddSampleInfoList = new ArrayList<SampleInfo>();
		List<String> tobeUpdateSampleNoList = new ArrayList<String>();
		SampleInfo si = null;
		SampleInfo tempSample = null;
		Date currentDate = new Date(System.currentTimeMillis());
		SysUser su = getLoginSysUser();
		for(int i = barcodeValStartIntVal; i <= barcodeValEndIntVal; i++){

			String sampleNo = barcodePrefix + StringUtil.getFullChar(String.valueOf(i), '0', this.serialNoLength);

			tempSample = sampleInfoService.findSampleInfoBySampleNo(sampleNo);
			if(tempSample == null){
				si = new SampleInfo();
				si.setId(keyGeneratorService.getNextKey());
				si.setAcceptDate(currentDate);
				si.setAcceptStatus(SampleInfo.ACCEPT_STATUS_ACCEPTED);
				si.setAcceptUser(su.getId());
				si.setConsignmentId(consignment.getId());
				si.setCreateDate(currentDate);
				si.setCreateUser(su.getId());
				si.setSampleNo(sampleNo);
				si.setSampleName(sampleNo);

				tobeAddSampleInfoList.add(si);
			}else{
				//未受理的将状态修改为已受理
				if(LimsConstants.FLAG_FALSE.equals(tempSample.getAcceptStatus())){
					tobeUpdateSampleNoList.add(sampleNo);
				}
			}
		}

		if(tobeAddSampleInfoList.size() > 0){
			sampleInfoService.addBatchSampleInfos(tobeAddSampleInfoList);
		}

		if(tobeUpdateSampleNoList.size() > 0){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("acceptStatus", LimsConstants.FLAG_TRUE);
			params.put("acceptUser", su.getId());

			Date updateDate = new Date(System.currentTimeMillis());
			params.put("acceptDate", updateDate);
			params.put("updateDate", updateDate);
			params.put("updateUser", su.getId());
			params.put("sampleNoList", tobeUpdateSampleNoList);

			sampleInfoService.updateAcceptStatusBatch(params);
		}


		return SUCCESS;
	}



	public String updateSampleNo() throws Exception {
		SysUser sysUser = getLoginSysUser();

		sampleInfo.setUpdateDate(new Date());
		sampleInfo.setUpdateUser(sysUser.getId());
		sampleInfoService.updateSampleInfoBySelective(sampleInfo);

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);

		return SUCCESS;
	}

	public String deleteScanedSampleNo() throws Exception {
		sampleInfoService.deleteSampleInfoById(sampleInfo.getId());

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);

		return SUCCESS;
	}

	//导入样本excel文件
	public String intoSampleExcelImoprt() throws Exception {

		return SUCCESS;
	}

	public String submitSampleExcelImport() throws Exception {
		SysUser sysUser = getLoginSysUser();

		resultDetail = new HashMap<String, Object>();
		resultDetail.put("fileName", sampleExcelFileFileName);

		List<String> sampleNoList = null;

		try {
			sampleNoList = SampleExcelParser.parseSampleExcel(sampleExcelFile);
			if(!ListUtil.isEmptyList(sampleNoList)){
				resultDetail.put("sampleCountInExcel", sampleNoList.size());
			}else{
				resultDetail.put("success", true);
				resultDetail.put("sampleCountInExcel", 0);
				resultDetail.put("importedSampleCount", "0");
				resultDetail.put("importFailedSampleCount", "0");

				return SUCCESS;
			}
		} catch(Exception e) {
			resultDetail.put("success", false);
			resultDetail.put("errorMessage", e.getMessage());

			return SUCCESS;
		}

		importFailureList = new ArrayList<Map<String, String>>();
		importSucceedList = new ArrayList<SampleInfo>();

		SampleInfo si = null;
		Date currentDate = new Date(System.currentTimeMillis());
		List<SampleInfo> tobeSavedSamples = new ArrayList<SampleInfo>();
		List<String> toBeUpdateSampleList = new ArrayList<String>();
		for(String sampleNoStr : sampleNoList){
			si = sampleInfoService.findSampleInfoBySampleNo(sampleNoStr);
			if(si != null){
				if(LimsConstants.FLAG_FALSE.equals(si.getAcceptStatus())){
					toBeUpdateSampleList.add(si.getSampleNo());
				}

				importSucceedList.add(si);
			}else{
				si = new SampleInfo();
				si.setId(keyGeneratorService.getNextKey());
				si.setConsignmentId(consignment.getId());
				si.setSampleNo(sampleNoStr);
				si.setSampleName(sampleNoStr);
				si.setAcceptDate(currentDate);
				si.setAcceptStatus(LimsConstants.FLAG_TRUE);
				si.setAcceptUser(sysUser.getId());
				si.setCreateDate(currentDate);
				si.setCreateUser(sysUser.getId());

				tobeSavedSamples.add(si);
				importSucceedList.add(si);
			}
		}

		if(!ListUtil.isEmptyList(tobeSavedSamples)){
			sampleInfoService.addBatchSampleInfos(tobeSavedSamples);
		}

		if(!ListUtil.isEmptyList(toBeUpdateSampleList)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("acceptStatus", LimsConstants.FLAG_TRUE);
			params.put("acceptUser", sysUser.getId());

			Date updateDate = new Date(System.currentTimeMillis());
			params.put("acceptDate", updateDate);
			params.put("updateDate", updateDate);
			params.put("updateUser", sysUser.getId());
			params.put("sampleNoList", toBeUpdateSampleList);

			sampleInfoService.updateAcceptStatusBatch(params);
		}

		resultDetail.put("success", true);
		resultDetail.put("importedSampleCount", importSucceedList.size());
		resultDetail.put("importFailedSampleCount", importFailureList.size());

		return SUCCESS;
	}


	//生成受理表
	public String generate() throws Exception {
		ConsignmentView consignmentView = consignmentService.findConsignmentViewById(consignment.getId());
		String templatePath = ServletActionContext.getServletContext().getRealPath("/template/acceptiontemplate");
	    String generatePath = ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY);

	    String templateFileName = "acceptionTemplate.xml";
	    String newFilename = "(" + consignmentView.getConsignmentNo() + ")委托受理表.doc";

	  //编码格式
		String encode = "UTF-8";

		//目标完整路径
		String targetFileName = generatePath + System.getProperty("file.separator") + "委托受理表";
		File targetFile = new File(targetFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}

		targetFileName = targetFileName + System.getProperty("file.separator") + newFilename;

	    AcceptionRecordGenerator arg = new AcceptionRecordGenerator(templatePath);

        //-----------------------公用信息 end-------------------------//
		//-----------------------------生成鉴定书 begin------------------------------//
		boolean isSuccess = arg.builderAcceptionDoc(consignmentView, templateFileName, targetFileName, encode);
		if(isSuccess){
			inputStream = new BufferedInputStream(new FileInputStream(targetFileName));
			try{
				acceptFileName = URLEncoder.encode(newFilename,"UTF-8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}

		return SUCCESS;
	}

	private void initDictInfo() throws Exception {
		consignModeList = sysDictService.findDictItemListByDictInfoType(DictInfo.CONSIGN_MODE);
		sampleTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.SAMPLE_TYPE);
		checkContentList = sysDictService.findDictItemListByDictInfoType(DictInfo.CHECK_CONTENT);
		sampleCarrierList = sysDictService.findDictItemListByDictInfoType(DictInfo.SAMPLE_CARRIER);

		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
	}

	public List<ConsignOrganization> getConsignOrganizationList() {
		return consignOrganizationList;
	}

	public void setConsignOrganizationList(
			List<ConsignOrganization> consignOrganizationList) {
		this.consignOrganizationList = consignOrganizationList;
	}

	public Consignment getConsignment() {
		return consignment;
	}

	public void setConsignment(Consignment consignment) {
		this.consignment = consignment;
	}

	public SysDictService getSysDictService() {
		return sysDictService;
	}

	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

	public List<DictItem> getConsignModeList() {
		return consignModeList;
	}

	public void setConsignModeList(List<DictItem> consignModeList) {
		this.consignModeList = consignModeList;
	}

	public List<DictItem> getSampleTypeList() {
		return sampleTypeList;
	}

	public void setSampleTypeList(List<DictItem> sampleTypeList) {
		this.sampleTypeList = sampleTypeList;
	}

	public List<DictItem> getCheckContentList() {
		return checkContentList;
	}

	public void setCheckContentList(List<DictItem> checkContentList) {
		this.checkContentList = checkContentList;
	}

	public List<DictItem> getSampleCarrierList() {
		return sampleCarrierList;
	}

	public void setSampleCarrierList(List<DictItem> sampleCarrierList) {
		this.sampleCarrierList = sampleCarrierList;
	}

	public ConsignOrganizationService getConsignOrganizationService() {
		return consignOrganizationService;
	}

	public void setConsignOrganizationService(
			ConsignOrganizationService consignOrganizationService) {
		this.consignOrganizationService = consignOrganizationService;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public ConsignmentService getConsignmentService() {
		return consignmentService;
	}

	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}

	public String[] getConsignmentSampleTypeStr() {
		return consignmentSampleTypeStr;
	}

	public void setConsignmentSampleTypeStr(String[] consignmentSampleTypeStr) {
		this.consignmentSampleTypeStr = consignmentSampleTypeStr;
	}

	public String[] getConsignmentSampleCountStr() {
		return consignmentSampleCountStr;
	}

	public void setConsignmentSampleCountStr(String[] consignmentSampleCountStr) {
		this.consignmentSampleCountStr = consignmentSampleCountStr;
	}

	public String getAcceptFileName() {
		return acceptFileName;
	}

	public void setAcceptFileName(String acceptFileName) {
		this.acceptFileName = acceptFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

//	public List<String> getScanedSample() {
//		return scanedSample;
//	}
//
//	public void setScanedSample(List<String> scanedSample) {
//		this.scanedSample = scanedSample;
//	}

	public List<SampleInfoView> getSampleInfoList() {
		return sampleInfoList;
	}

	public void setSampleInfoList(List<SampleInfoView> sampleInfoList) {
		this.sampleInfoList = sampleInfoList;
	}

	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}

	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}

	public SampleInfoQuery getSampleInfoQuery() {
		return sampleInfoQuery;
	}

	public void setSampleInfoQuery(SampleInfoQuery sampleInfoQuery) {
		this.sampleInfoQuery = sampleInfoQuery;
	}

	public SampleInfo getSampleInfo() {
		return sampleInfo;
	}

	public void setSampleInfo(SampleInfo sampleInfo) {
		this.sampleInfo = sampleInfo;
	}

	public File getSampleExcelFile() {
		return sampleExcelFile;
	}

	public void setSampleExcelFile(File sampleExcelFile) {
		this.sampleExcelFile = sampleExcelFile;
	}

	public String getSampleExcelFileFileName() {
		return sampleExcelFileFileName;
	}

	public void setSampleExcelFileFileName(String sampleExcelFileFileName) {
		this.sampleExcelFileFileName = sampleExcelFileFileName;
	}

	public Map<String, Object> getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(Map<String, Object> resultDetail) {
		this.resultDetail = resultDetail;
	}

	public List<Map<String, String>> getImportFailureList() {
		return importFailureList;
	}

	public void setImportFailureList(List<Map<String, String>> importFailureList) {
		this.importFailureList = importFailureList;
	}
	public KeyGeneratorService getKeyGeneratorService() {
		return keyGeneratorService;
	}

	public void setKeyGeneratorService(KeyGeneratorService keyGeneratorService) {
		this.keyGeneratorService = keyGeneratorService;
	}

	public void setImportSucceedList(List<SampleInfo> importSucceedList) {
		this.importSucceedList = importSucceedList;
	}

	public List<SampleInfo> getImportSucceedList() {
		return importSucceedList;
	}

	public String getBarcodePrefix() {
		return barcodePrefix;
	}

	public void setBarcodePrefix(String barcodePrefix) {
		this.barcodePrefix = barcodePrefix;
	}

	public int getSerialNoLength() {
		return serialNoLength;
	}

	public void setSerialNoLength(int serialNoLength) {
		this.serialNoLength = serialNoLength;
	}

	public String getBarcodeValStart() {
		return barcodeValStart;
	}

	public void setBarcodeValStart(String barcodeValStart) {
		this.barcodeValStart = barcodeValStart;
	}

	public String getBarcodeValEnd() {
		return barcodeValEnd;
	}

	public void setBarcodeValEnd(String barcodeValEnd) {
		this.barcodeValEnd = barcodeValEnd;
	}

}
