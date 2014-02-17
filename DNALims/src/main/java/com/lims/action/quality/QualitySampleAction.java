/**
 * QualitySampleAction.java
 *
 * 2013-6-20
 */
package com.lims.action.quality;

import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.QualityControlSample;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.PolluteRecordQuery;
import com.lims.domain.vo.PolluteRecordView;
import com.lims.domain.vo.QualityControlSampleView;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.service.dict.SysDictService;
import com.lims.service.gene.SampleSourceGeneService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.quality.QualityControlSampleService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class QualitySampleAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private QualityControlSample qualityControlSample;

	private QualityControlSampleView qualityControlSampleView;

	private List<QualityControlSampleView> qualityControlSampleList;

	private List<DictItem> genderList;

	private List<ConsignOrganization> organizationList;

	private List<String> alleleValue1;

	private List<String> alleleValue2;

	private List<String> alleleValue3;

	private PolluteRecordQuery polluteRecordQuery;

	private PolluteRecordView polluteRecordView;

	private List<PolluteRecordView> polluteRecordViewList;

	@Resource
	private QualityControlSampleService qualityControlSampleService;
	@Resource
	private SysDictService sysDictService;
	@Resource
	private ConsignOrganizationService consignOrganizationService;
	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private SampleSourceGeneService sampleSourceGeneService;

	private List<LocusInfo> allLocusInfoList;

	private List<String> sourceGenotypeInfoList;

	private List<String> matchGenotypeInfoList;

	private SampleSourceGeneView sampleSourceGeneView;



	public String queryQualitySample() throws Exception{
		if(qualityControlSample == null){
			qualityControlSample = new QualityControlSample();
		}else{
			trimQueryConditions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		qualityControlSample.setStartRowNum((pageIndex-1)*pageSize + 1);
		qualityControlSample.setEndRowNum(pageIndex*pageSize);

		recordCount = qualityControlSampleService.findQualityControlSampleListCount(qualityControlSample);
		qualityControlSampleList = qualityControlSampleService.findQualityControlSampleList(qualityControlSample);

		return SUCCESS;
	}

	public String into() throws Exception {
		genderList = sysDictService.findDictItemListByDictInfoType(DictInfo.GENDER);
		organizationList = consignOrganizationService.findAllConsignOrganizationList();

		if(OPERATE_TYPE_UPDATE.equals(operateType)){
			qualityControlSampleView = qualityControlSampleService.findQualityControlSampleById(qualityControlSample.getId());
		}else{
			qualityControlSampleView = new QualityControlSampleView();
		}

		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, null, qualityControlSampleView.getGenotypeInfo());
		qualityControlSampleView.setGeneInfoHtmlForPage(geneInfoHtml);

		return SUCCESS;
	}

	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		String genotypeInfoStr = GeneInfoTableConverter.convertGeneInfoToDbStr(allLocusInfoList, null,
				alleleValue1, alleleValue2, alleleValue3);
		qualityControlSampleView.setGenotypeInfo(genotypeInfoStr);

		if(OPERATE_TYPE_ADD.equals(operateType)){
			qualityControlSampleView.setCreateUser(sysUser.getId());

			qualityControlSampleService.insertQualityControlSample(qualityControlSampleView);
		}else{
			qualityControlSampleView.setUpdateUser(sysUser.getId());

			qualityControlSampleService.updateQualityControlSample(qualityControlSampleView);
		}

		return SUCCESS;
	}


	public String queryPolluteRecord() throws Exception{
		if(polluteRecordQuery == null){
			polluteRecordQuery = new PolluteRecordQuery();
		}else{
			trimPolluteRecordQueryConditions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		polluteRecordQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		polluteRecordQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = qualityControlSampleService.findPolluteRecordViewListCount(polluteRecordQuery);
		polluteRecordViewList = qualityControlSampleService.findPolluteRecordViewList(polluteRecordQuery);

		qualityControlSampleList = qualityControlSampleService.findQualityControlSampleList(new QualityControlSample());

		return SUCCESS;
	}

	/**
	 * 污染比中详情
	 * @return
	 * @throws Exception
	 */
	public String intoQualityRecordDetail() throws Exception {
		allLocusInfoList = locusInfoService.findAllLocusInfoList();
		polluteRecordView = qualityControlSampleService.findPolluteRecordViewList(polluteRecordQuery).get(0);

		qualityControlSampleView = qualityControlSampleService.findQualityControlSampleById(polluteRecordView.getQcpId());
		sampleSourceGeneView = sampleSourceGeneService.findSampleSourceGeneById(polluteRecordView.getSampleSourceGeneId());

		sourceGenotypeInfoList = ListUtil.stringArrayToList(qualityControlSampleView.getGenotypeInfo().split(";"));
		matchGenotypeInfoList = ListUtil.stringArrayToList(qualityControlSampleView.getGenotypeInfo().split(";"));

		return SUCCESS;
	}


	private void trimQueryConditions(){
		if(StringUtil.isNullOrEmpty(qualityControlSample.getFullName())){
			qualityControlSample.setFullName(null);
		}else{
			qualityControlSample.setFullName(qualityControlSample.getFullName().trim());
		}

		if(StringUtil.isNullOrEmpty(qualityControlSample.getSampleNo())){
			qualityControlSample.setSampleNo(null);
		}else{
			qualityControlSample.setSampleNo(qualityControlSample.getSampleNo().trim());
		}
	}

	private void trimPolluteRecordQueryConditions(){
		if(StringUtil.isNullOrEmpty(polluteRecordQuery.getQcpId())){
			polluteRecordQuery.setQcpId(null);
		}else{
			polluteRecordQuery.setQcpId(polluteRecordQuery.getQcpId().trim());
		}

		if(StringUtil.isNullOrEmpty(polluteRecordQuery.getQualitySampleNo())){
			polluteRecordQuery.setQualitySampleNo(null);
		}else{
			polluteRecordQuery.setQualitySampleNo(polluteRecordQuery.getQualitySampleNo().trim());
		}
	}


	public QualityControlSample getQualityControlSample() {
		return qualityControlSample;
	}


	public void setQualityControlSample(QualityControlSample qualityControlSample) {
		this.qualityControlSample = qualityControlSample;
	}


	public List<QualityControlSampleView> getQualityControlSampleList() {
		return qualityControlSampleList;
	}


	public void setQualityControlSampleList(
			List<QualityControlSampleView> qualityControlSampleList) {
		this.qualityControlSampleList = qualityControlSampleList;
	}

	public List<DictItem> getGenderList() {
		return genderList;
	}

	public void setGenderList(List<DictItem> genderList) {
		this.genderList = genderList;
	}

	public List<ConsignOrganization> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<ConsignOrganization> organizationList) {
		this.organizationList = organizationList;
	}

	public QualityControlSampleView getQualityControlSampleView() {
		return qualityControlSampleView;
	}

	public void setQualityControlSampleView(
			QualityControlSampleView qualityControlSampleView) {
		this.qualityControlSampleView = qualityControlSampleView;
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


	public List<PolluteRecordView> getPolluteRecordViewList() {
		return polluteRecordViewList;
	}

	public void setPolluteRecordViewList(
			List<PolluteRecordView> polluteRecordViewList) {
		this.polluteRecordViewList = polluteRecordViewList;
	}

	public PolluteRecordQuery getPolluteRecordQuery() {
		return polluteRecordQuery;
	}

	public void setPolluteRecordQuery(PolluteRecordQuery polluteRecordQuery) {
		this.polluteRecordQuery = polluteRecordQuery;
	}

	public List<LocusInfo> getAllLocusInfoList() {
		return allLocusInfoList;
	}

	public void setAllLocusInfoList(List<LocusInfo> allLocusInfoList) {
		this.allLocusInfoList = allLocusInfoList;
	}

	public List<String> getSourceGenotypeInfoList() {
		return sourceGenotypeInfoList;
	}

	public void setSourceGenotypeInfoList(List<String> sourceGenotypeInfoList) {
		this.sourceGenotypeInfoList = sourceGenotypeInfoList;
	}

	public List<String> getMatchGenotypeInfoList() {
		return matchGenotypeInfoList;
	}

	public void setMatchGenotypeInfoList(List<String> matchGenotypeInfoList) {
		this.matchGenotypeInfoList = matchGenotypeInfoList;
	}

	public PolluteRecordView getPolluteRecordView() {
		return polluteRecordView;
	}

	public void setPolluteRecordView(PolluteRecordView polluteRecordView) {
		this.polluteRecordView = polluteRecordView;
	}

	public List<String> getAlleleValue3() {
		return alleleValue3;
	}

	public void setAlleleValue3(List<String> alleleValue3) {
		this.alleleValue3 = alleleValue3;
	}

}
