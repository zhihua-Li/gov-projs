/**
 * ReviewAction.java
 *
 * 2014-1-9
 */
package com.lims.action.examine;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.ReviewRecord;
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
 *
 */
public class ReviewAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfoQuery boardInfoQuery;

	private BoardInfo boardInfo;

	private ReviewRecord reviewRecord;

	private List<BoardInfoView> boardInfoList;

	private List<DictItem> pcrSystemList;

	private List<DictItem> pcrLoopCountList;

	private List<DictItem> examineInstrumentList;
	private List<ConsignOrganization> consignOrganizationList;


	private List<SysUser> operateUserList;

	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SysDictService sysDictService;
    @Resource
	private ConsignOrganizationService consignOrganizationService;
	@Resource
	private ExamineService examineService;
	@Resource
	private SysUserService sysUserService;


	public String query() throws Exception {
		initDictInfo();

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("boardNo");
			boardInfoQuery.setSortOrder("ASC");
		}else{
			boardInfoQuery = trimQueryOptions(boardInfoQuery);
		}

		boardInfoQuery.setExamineStatus(LimsConstants.EXAMINE_STATUS_ANALYSISED);

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
		operateUserList = sysUserService.findSysUserList(new SysUser());

		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());
		operateType = BaseAction.OPERATE_TYPE_ADD;
		reviewRecord = new ReviewRecord();
		reviewRecord.setBoardId(boardInfo.getId());
		reviewRecord.setReviewDate(new Date(System.currentTimeMillis()));

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
			if(null == reviewRecord.getReviewUserId()){
				reviewRecord.setReviewUserId(sysUser.getId());
			}

			examineService.addReviewRecord(reviewRecord);
		}

		return SUCCESS;
	}

	private void initDictInfo() throws Exception {
		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
		operateUserList = sysUserService.findSysUserList(new SysUser());
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


	public List<DictItem> getExamineInstrumentList() {
		return examineInstrumentList;
	}


	public void setExamineInstrumentList(List<DictItem> examineInstrumentList) {
		this.examineInstrumentList = examineInstrumentList;
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


	public ExamineService getExamineService() {
		return examineService;
	}


	public void setExamineService(ExamineService examineService) {
		this.examineService = examineService;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public ReviewRecord getReviewRecord() {
		return reviewRecord;
	}

	public void setReviewRecord(ReviewRecord reviewRecord) {
		this.reviewRecord = reviewRecord;
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

}
