/**
 * ComparisonAction.java
 *
 * 2013-6-20
 */
package com.lims.action.comparison;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.ComparisonSettings;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.PolluteRecord;
import com.lims.domain.po.QualityControlSample;
import com.lims.domain.po.ReagentKit;
import com.lims.domain.po.SampleSourceGene;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.QualityControlSampleView;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.service.comparison.ComparisonSettingsService;
import com.lims.service.comparison.InstoreComparisonService;
import com.lims.service.gene.SampleSourceGeneService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.locus.ReagentKitService;
import com.lims.service.quality.QualityControlSampleService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class ComparisonAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SampleSourceGeneQuery sampleSourceGeneQuery;

	private List<SampleSourceGeneView> sampleSourceGeneList;

	private List<SysUser> sysUserList;

	private List<String> checkedId;

	private String checkedSampleIds;

	private List<ReagentKit> reagentKitList;

	private SampleSourceGene sampleSourceGene;


	@Resource
	private InstoreComparisonService instoreComparisonService;
	@Resource
	private SampleSourceGeneService sampleSourceGeneService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ReagentKitService reagentKitService;
	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private QualityControlSampleService qualityControlSampleService;
	@Resource
	private ComparisonSettingsService comparisonSettingsService;

	private SampleSourceGeneView sampleSourceGeneView;

	private Map<String, Object> jsonData;


	public String queryWaitingInstoreComparison() throws Exception{
		sysUserList = sysUserService.findSysUserList(new SysUser());

		if(sampleSourceGeneQuery == null){
			sampleSourceGeneQuery = new SampleSourceGeneQuery();
			sampleSourceGeneQuery.setSortColumn("sampleNo");
			sampleSourceGeneQuery.setSortOrder("ASC");
		}else{
			trimQueryOptions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		sampleSourceGeneQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		sampleSourceGeneQuery.setEndRowNum(pageIndex*pageSize);
		sampleSourceGeneQuery.setReviewStatus(LimsConstants.REVIEW_STATUS_PASSED);

		recordCount = instoreComparisonService.findWaitingInstoreSampleListCount(sampleSourceGeneQuery);
		sampleSourceGeneList = instoreComparisonService.findWaitingInstoreSampleList(sampleSourceGeneQuery);
		for(SampleSourceGeneView ssg : sampleSourceGeneList){
			SampleSourceGeneQuery sg = new SampleSourceGeneQuery();
			sg.setSampleNo(ssg.getSampleNo());
			sg.setAlignDbFlag(LimsConstants.FLAG_TRUE);
			int iCount = instoreComparisonService.findWaitingInstoreSampleListCount(sg);
			ssg.setHasInstorGeneFlag(iCount > 0 ? "1" : "0");
		}

		return SUCCESS;
	}


	public String batchInstore() throws Exception {
		if(checkedId == null || checkedId.size() == 0){
			return SUCCESS;
		}

		SysUser sysUser = getLoginSysUser();
		List<SampleSourceGeneView> sourceGeneList = sampleSourceGeneService.findSampleSourceGeneByIdList(checkedId);
		instoreComparisonService.instoreSampleSourceGeneList(sourceGeneList, sysUser);

		return SUCCESS;
	}

	public String qualitySampleCompare() throws Exception {
		jsonData = new HashMap<String, Object>();

		List<QualityControlSampleView> qcsList = qualityControlSampleService.findQualityControlSampleList(new QualityControlSample());
		ComparisonSettings settings = comparisonSettingsService.findComparisonSettings();
		boolean hasPolluteSample = false;
		String pollutedSample = "";
		String[] checkedSampleId = checkedSampleIds.split(",");
		List<String> pollutedIdList = new ArrayList<String>();
		for(String sampleSourceGeneId : checkedSampleId){
			SampleSourceGeneView ssg = sampleSourceGeneService.findSampleSourceGeneById(sampleSourceGeneId);
			for(QualityControlSampleView qcsv : qcsList){
				if(compare(ssg, qcsv, settings)){
					hasPolluteSample = true;
					pollutedSample += ssg.getSampleNo() + ",";
					pollutedIdList.add(sampleSourceGeneId);
					break;
				}
			}
		}

		jsonData.put("hasPolluteSample", hasPolluteSample);
		if(hasPolluteSample){
			jsonData.put("pollutedSample", pollutedSample.substring(0, pollutedSample.length()-1));
			jsonData.put("pollutedIdList", pollutedIdList);
		}

		return SUCCESS;
	}

	private boolean compare(SampleSourceGene ssg, QualityControlSampleView qcsv, ComparisonSettings settings) {
		String genotypeInfoOfQueue = ssg.getGenotypeInfo();
		String targetGenotypeInfo = qcsv.getGenotypeInfo();

		if(StringUtil.isNullOrEmpty(genotypeInfoOfQueue)
				|| StringUtil.isNullOrEmpty(targetGenotypeInfo)){
			return false;
		}

		String[] genotypeInfoArrOfQueue = genotypeInfoOfQueue.split(";");
		String[] targetGenotypeInfoArr = targetGenotypeInfo.split(";");

		//性别基因不一样，直接返回false
		if(!genotypeInfoArrOfQueue[0].equals(targetGenotypeInfoArr[0])){
			return false;
		}

		int sameCount = 0;
		int diffCount = 0;
		for(int i = 1; i < genotypeInfoArrOfQueue.length && i < targetGenotypeInfoArr.length; i++){
			if("/".equals(genotypeInfoArrOfQueue[i])
					|| "/".equals(targetGenotypeInfoArr[i])){
				continue;
			}

			if(genotypeInfoArrOfQueue[i].equals(targetGenotypeInfoArr[i])){
				sameCount++;
			}else{
				diffCount++;
			}
		}

		if(sameCount >= Integer.parseInt(settings.getSameLowerLimitNum())
				&& diffCount <= Integer.parseInt(settings.getDiffUpperLimitNum())) {
			//添加污染记录
			Date currentDate = DateUtil.currentDate();
			PolluteRecord pr = new PolluteRecord();
			pr.setQcpId(qcsv.getId());
			pr.setMatchedSampleId(ssg.getSampleId());
			pr.setMatchDate(currentDate);
			pr.setDiffNum(diffCount);
			pr.setSameNum(sameCount);
			pr.setSampleSourceGeneId(ssg.getId());

			qualityControlSampleService.addPolluteRecord(pr);

			return true;
		} else {
			return false;
		}
	}

	public String into() throws Exception {
		operateType = OPERATE_TYPE_UPDATE;
		reagentKitList = reagentKitService.finalAllReagentKitList();
		sampleSourceGeneView = sampleSourceGeneService.findSampleSourceGeneById(sampleSourceGeneQuery.getId());

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

		return SUCCESS;
	}


	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();

		sampleSourceGeneView = sampleSourceGeneService.findSampleSourceGeneById(sampleSourceGeneView.getId());
		List<SampleSourceGeneView> sourceGeneList = new ArrayList<SampleSourceGeneView>();
		sourceGeneList.add(sampleSourceGeneView);
		instoreComparisonService.instoreSampleSourceGeneList(sourceGeneList, sysUser);

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);

		return SUCCESS;
	}


	private void trimQueryOptions() {
		if(StringUtil.isNullOrEmpty(sampleSourceGeneQuery.getConsignmentNo())){
			sampleSourceGeneQuery.setConsignmentNo(null);
		}else{
			sampleSourceGeneQuery.setConsignmentNo(sampleSourceGeneQuery.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(sampleSourceGeneQuery.getSampleNo())){
			sampleSourceGeneQuery.setSampleNo(null);
		}else{
			sampleSourceGeneQuery.setSampleNo(sampleSourceGeneQuery.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(sampleSourceGeneQuery.getReviewUser())){
			sampleSourceGeneQuery.setReviewUser(null);
		}else{
			sampleSourceGeneQuery.setReviewUser(sampleSourceGeneQuery.getReviewUser().trim());
		}

		if(StringUtil.isNullOrEmpty(sampleSourceGeneQuery.getAlignDbFlag())){
			sampleSourceGeneQuery.setAlignDbFlag(null);
		}else{
			sampleSourceGeneQuery.setAlignDbFlag(sampleSourceGeneQuery.getAlignDbFlag().trim());
		}

	}

	public SampleSourceGeneQuery getSampleSourceGeneQuery() {
		return sampleSourceGeneQuery;
	}


	public void setSampleSourceGeneQuery(SampleSourceGeneQuery sampleSourceGeneQuery) {
		this.sampleSourceGeneQuery = sampleSourceGeneQuery;
	}


	public List<SampleSourceGeneView> getSampleSourceGeneList() {
		return sampleSourceGeneList;
	}


	public void setSampleSourceGeneList(
			List<SampleSourceGeneView> sampleSourceGeneList) {
		this.sampleSourceGeneList = sampleSourceGeneList;
	}


	public InstoreComparisonService getInstoreComparisonService() {
		return instoreComparisonService;
	}


	public void setInstoreComparisonService(
			InstoreComparisonService instoreComparisonService) {
		this.instoreComparisonService = instoreComparisonService;
	}


	public List<SysUser> getSysUserList() {
		return sysUserList;
	}


	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public List<String> getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(List<String> checkedId) {
		this.checkedId = checkedId;
	}


	public SampleSourceGeneService getSampleSourceGeneService() {
		return sampleSourceGeneService;
	}


	public void setSampleSourceGeneService(
			SampleSourceGeneService sampleSourceGeneService) {
		this.sampleSourceGeneService = sampleSourceGeneService;
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


	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}


	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
	}


	public SampleSourceGeneView getSampleSourceGeneView() {
		return sampleSourceGeneView;
	}


	public void setSampleSourceGeneView(SampleSourceGeneView sampleSourceGeneView) {
		this.sampleSourceGeneView = sampleSourceGeneView;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public SampleSourceGene getSampleSourceGene() {
		return sampleSourceGene;
	}


	public void setSampleSourceGene(SampleSourceGene sampleSourceGene) {
		this.sampleSourceGene = sampleSourceGene;
	}


	public QualityControlSampleService getQualityControlSampleService() {
		return qualityControlSampleService;
	}


	public void setQualityControlSampleService(
			QualityControlSampleService qualityControlSampleService) {
		this.qualityControlSampleService = qualityControlSampleService;
	}


	public ComparisonSettingsService getComparisonSettingsService() {
		return comparisonSettingsService;
	}


	public void setComparisonSettingsService(
			ComparisonSettingsService comparisonSettingsService) {
		this.comparisonSettingsService = comparisonSettingsService;
	}


	public String getCheckedSampleIds() {
		return checkedSampleIds;
	}


	public void setCheckedSampleIds(String checkedSampleIds) {
		this.checkedSampleIds = checkedSampleIds;
	}

}
