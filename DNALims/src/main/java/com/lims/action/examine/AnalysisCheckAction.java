/**
 * AnalysisCheckAction.java
 *
 * 2013-6-3
 */
package com.lims.action.examine;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.AnalysisRecord;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.service.dict.SysDictService;
import com.lims.service.examine.ExamineService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 * 分析复核Action
 *
 */
public class AnalysisCheckAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfoQuery boardInfoQuery;

	private BoardInfo boardInfo;

	private AnalysisRecord analysisRecord;

	private List<BoardInfoView> boardInfoList;

	private List<DictItem> pcrSystemList;

	private List<DictItem> examineStatusList;

	private List<DictItem> pcrLoopCountList;

	private List<DictItem> analysisInstrumentList;

	private List<SysUser> operateUserList;

	private List<ConsignOrganization> consignOrganizationList;


	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SysDictService sysDictService;
    @Resource
	private ConsignOrganizationService consignOrganizationService;
    @Resource
    private SysUserService sysUserService;
	@Resource
	private ExamineService examineService;

	/**
	 * 查询待分析复核列表
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		initDictInfo();

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("boardNo");
			boardInfoQuery.setSortOrder("ASC");
		}else{
			boardInfoQuery = trimQueryOptions(boardInfoQuery);
		}

		boardInfoQuery.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXAMINED);

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		return SUCCESS;
	}


	/**
	 * 进入分析复核详情页面
	 * @return
	 * @throws Exception
	 */
	public String into() throws Exception {
		analysisInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.ANALYSIS_INSTRUMENT);
		operateUserList = sysUserService.findSysUserList(new SysUser());

		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());
		operateType = BaseAction.OPERATE_TYPE_ADD;

		analysisRecord = new AnalysisRecord();
		analysisRecord.setBoardInfoId(boardInfo.getId());
		analysisRecord.setAnalysisDate(new Date(System.currentTimeMillis()));


		return SUCCESS;
	}


	/**
	 * 提交分析复核
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();

		if(BaseAction.OPERATE_TYPE_ADD.equals(operateType)){
			if(null == analysisRecord.getCreateUser()){
				analysisRecord.setCreateUser(sysUser.getId());
			}

			examineService.addAnalysisRecord(analysisRecord);
		}else if(BaseAction.OPERATE_TYPE_UPDATE.equals(operateType)){
			analysisRecord.setUpdateUser(sysUser.getId());
			examineService.updateAnalysisRecord(analysisRecord);
		}

		return SUCCESS;
	}


	private BoardInfoQuery trimQueryOptions(BoardInfoQuery bi) {
		if(StringUtil.isNullOrEmpty(bi.getConsignmentNo())){
			bi.setConsignmentNo(null);
		}else{
			bi.setConsignmentNo(bi.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getSampleNo())){
			bi.setSampleNo(null);
		}else{
			bi.setSampleNo(bi.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getBoardNo())){
			bi.setBoardNo(null);
		}else{
			bi.setBoardNo(bi.getBoardNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getExamineStatus())){
			bi.setExamineStatus(null);
		}else{
			bi.setExamineStatus(bi.getExamineStatus().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getImportUser())){
			bi.setImportUser(null);
		}else{
			bi.setImportUser(bi.getImportUser().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getOrganizationCode())){
			bi.setOrganizationCode(null);
		}else{
			bi.setOrganizationCode(bi.getOrganizationCode().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getFuzzyFlag())){
			boardInfoQuery.setFuzzyFlag(null);
		}else{
			if("false".equals(boardInfoQuery.getFuzzyFlag())){
				boardInfoQuery.setFuzzyFlag(null);
			}else{
				boardInfoQuery.setFuzzyFlag(boardInfoQuery.getFuzzyFlag().trim());
			}
		}

		return bi;
	}

	private void initDictInfo() throws Exception {
		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
		operateUserList = sysUserService.findSysUserList(new SysUser());
		examineStatusList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_STATUS);
	}

	public BoardInfoQuery getBoardInfoQuery() {
		return boardInfoQuery;
	}


	public void setBoardInfoQuery(BoardInfoQuery boardInfoQuery) {
		this.boardInfoQuery = boardInfoQuery;
	}


	public BoardInfo getBoardInfo() {
		return boardInfo;
	}


	public void setBoardInfo(BoardInfo boardInfo) {
		this.boardInfo = boardInfo;
	}

	public List<BoardInfoView> getBoardInfoList() {
		return boardInfoList;
	}


	public void setBoardInfoList(List<BoardInfoView> boardInfoList) {
		this.boardInfoList = boardInfoList;
	}


	public List<DictItem> getPcrSystemList() {
		return pcrSystemList;
	}


	public void setPcrSystemList(List<DictItem> pcrSystemList) {
		this.pcrSystemList = pcrSystemList;
	}


	public List<DictItem> getPcrLoopCountList() {
		return pcrLoopCountList;
	}


	public void setPcrLoopCountList(List<DictItem> pcrLoopCountList) {
		this.pcrLoopCountList = pcrLoopCountList;
	}


	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}


	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
	}


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}


	public List<DictItem> getExamineStatusList() {
		return examineStatusList;
	}


	public void setExamineStatusList(List<DictItem> examineStatusList) {
		this.examineStatusList = examineStatusList;
	}


	public List<SysUser> getOperateUserList() {
		return operateUserList;
	}


	public void setOperateUserList(List<SysUser> operateUserList) {
		this.operateUserList = operateUserList;
	}


	public List<ConsignOrganization> getConsignOrganizationList() {
		return consignOrganizationList;
	}


	public void setConsignOrganizationList(
			List<ConsignOrganization> consignOrganizationList) {
		this.consignOrganizationList = consignOrganizationList;
	}


	public ConsignOrganizationService getConsignOrganizationService() {
		return consignOrganizationService;
	}


	public void setConsignOrganizationService(
			ConsignOrganizationService consignOrganizationService) {
		this.consignOrganizationService = consignOrganizationService;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public AnalysisRecord getAnalysisRecord() {
		return analysisRecord;
	}


	public void setAnalysisRecord(AnalysisRecord analysisRecord) {
		this.analysisRecord = analysisRecord;
	}


	public List<DictItem> getAnalysisInstrumentList() {
		return analysisInstrumentList;
	}


	public void setAnalysisInstrumentList(List<DictItem> analysisInstrumentList) {
		this.analysisInstrumentList = analysisInstrumentList;
	}


	public ExamineService getExamineService() {
		return examineService;
	}


	public void setExamineService(ExamineService examineService) {
		this.examineService = examineService;
	}
}
