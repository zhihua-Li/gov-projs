/**
 * CompareResultAction.java
 *
 * 2013-7-13
 */
package com.lims.action.comparison;

import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.CompareResultQuery;
import com.lims.domain.vo.CompareResultView;
import com.lims.domain.vo.GeneInfoView;
import com.lims.service.comparison.CompareResultService;
import com.lims.service.gene.GeneInfoService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class CompareResultAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private CompareResultQuery compareResultQuery;

	private CompareResultView compareResult;

	private GeneInfoView sourceGeneInfoView;

	private GeneInfoView matchGeneInfoView;

	private List<CompareResultView> compareResultViewList;

	private List<SysUser> sysUserList;

	private List<LocusInfo> allLocusInfoList;

	private List<String> sourceGenotypeInfoList;

	private List<String> matchGenotypeInfoList;


	@Resource
	private CompareResultService compareResultService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private GeneInfoService geneInfoService;
	@Resource
	private LocusInfoService locusInfoService;

	private String allLocusNameStr;

	public String queryCompareResult() throws Exception {
		sysUserList = sysUserService.findSysUserList(new SysUser());

		if(compareResultQuery == null){
			compareResultQuery = new CompareResultQuery();
			compareResultQuery.setSortColumn("matchDate");
			compareResultQuery.setSortOrder("DESC");
		}else{
			trimQueryOptions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		compareResultQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		compareResultQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = compareResultService.findCompareResultListCount(compareResultQuery);
		compareResultViewList = compareResultService.findCompareResultList(compareResultQuery);

		return SUCCESS;
	}


	public String into() throws Exception {
		allLocusInfoList = locusInfoService.findAllLocusInfoList();
		compareResult = compareResultService.findCompareResultViewById(compareResult.getId());
		sourceGeneInfoView = geneInfoService.findGeneInfoViewBySampleId(compareResult.getSourceSampleId());
		matchGeneInfoView = geneInfoService.findGeneInfoViewBySampleId(compareResult.getMatchSampleId());

//		sourceGenotypeInfoList = ListUtil.stringArrayToList(sourceGeneInfoView.getGenotypeInfo().split(";"));
//		matchGenotypeInfoList = ListUtil.stringArrayToList(matchGeneInfoView.getGenotypeInfo().split(";"));

		allLocusNameStr = "";
		for(LocusInfo locusInfo : allLocusInfoList){
			allLocusNameStr += locusInfo.getGenoName() + ";";
		}
		allLocusNameStr = allLocusNameStr.substring(0, allLocusNameStr.length()-1);

		return SUCCESS;
	}


	private void trimQueryOptions() {

		if(StringUtil.isNullOrEmpty(compareResultQuery.getSampleNo())){
			compareResultQuery.setSampleNo(null);
		}else{
			compareResultQuery.setSampleNo(compareResultQuery.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(compareResultQuery.getInstoreUser())){
			compareResultQuery.setInstoreUser(null);
		}else{
			compareResultQuery.setInstoreUser(compareResultQuery.getInstoreUser().trim());
		}

	}


	public CompareResultQuery getCompareResultQuery() {
		return compareResultQuery;
	}


	public void setCompareResultQuery(CompareResultQuery compareResultQuery) {
		this.compareResultQuery = compareResultQuery;
	}


	public List<CompareResultView> getCompareResultViewList() {
		return compareResultViewList;
	}


	public void setCompareResultViewList(
			List<CompareResultView> compareResultViewList) {
		this.compareResultViewList = compareResultViewList;
	}


	public List<SysUser> getSysUserList() {
		return sysUserList;
	}


	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}


	public CompareResultService getCompareResultService() {
		return compareResultService;
	}


	public void setCompareResultService(CompareResultService compareResultService) {
		this.compareResultService = compareResultService;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public CompareResultView getCompareResult() {
		return compareResult;
	}


	public void setCompareResult(CompareResultView compareResult) {
		this.compareResult = compareResult;
	}


	public GeneInfoService getGeneInfoService() {
		return geneInfoService;
	}


	public void setGeneInfoService(GeneInfoService geneInfoService) {
		this.geneInfoService = geneInfoService;
	}


	public GeneInfoView getSourceGeneInfoView() {
		return sourceGeneInfoView;
	}


	public void setSourceGeneInfoView(GeneInfoView sourceGeneInfoView) {
		this.sourceGeneInfoView = sourceGeneInfoView;
	}


	public GeneInfoView getMatchGeneInfoView() {
		return matchGeneInfoView;
	}


	public void setMatchGeneInfoView(GeneInfoView matchGeneInfoView) {
		this.matchGeneInfoView = matchGeneInfoView;
	}


	public List<LocusInfo> getAllLocusInfoList() {
		return allLocusInfoList;
	}


	public void setAllLocusInfoList(List<LocusInfo> allLocusInfoList) {
		this.allLocusInfoList = allLocusInfoList;
	}


	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}


	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
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


	public String getAllLocusNameStr() {
		return allLocusNameStr;
	}


	public void setAllLocusNameStr(String allLocusNameStr) {
		this.allLocusNameStr = allLocusNameStr;
	}

}
