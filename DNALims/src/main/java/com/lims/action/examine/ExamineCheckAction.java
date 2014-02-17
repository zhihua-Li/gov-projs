/**
 * ExamineCheckAction.java
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
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.ExamineRecord;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.service.dict.SysDictService;
import com.lims.service.examine.ExamineService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class ExamineCheckAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfoQuery boardInfoQuery;

	private BoardInfo boardInfo;

	private ExamineRecord examineRecord;

	private List<BoardInfoView> boardInfoList;

	private List<DictItem> pcrSystemList;

	private List<DictItem> pcrLoopCountList;

	/**
	 * 检测仪编号
	 */
	private List<DictItem> examineInstrumentList;

	private List<SysUser> sysUserList;

	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SysDictService sysDictService;
	@Resource
	private ExamineService examineService;
	@Resource
	private SysUserService sysUserService;

	public String query() throws Exception{
		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("boardNo");
			boardInfoQuery.setSortOrder("ASC");
		}else{
			boardInfoQuery = trimQueryOptions(boardInfoQuery);
		}

		boardInfoQuery.setExamineStatus(LimsConstants.EXAMINE_STATUS_PCRED);

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


	public String into() throws Exception {
		initDictInfo();
		sysUserList = sysUserService.findSysUserList(new SysUser());

		examineRecord = new ExamineRecord();
		examineRecord.setBoardId(boardInfo.getId());
		examineRecord.setExamineDate(new Date(System.currentTimeMillis()));
		boardInfo.setExamineDate(new Date(System.currentTimeMillis()));

		return SUCCESS;
	}

	private void initDictInfo() throws Exception {
		examineInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_INSTRUMENT);
	}

	/**
	 * 提交检测
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {

		BoardInfo bi = boardInfoService.findBoardInfoById(boardInfo.getId());
		bi.setExamineDate(boardInfo.getExamineDate());
		bi.setExamineUser(boardInfo.getExamineUser());
		bi.setExamineInstrumentNo(boardInfo.getExamineInstrumentNo());

		examineService.addExaminRecord(bi, examineRecord);

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

	public List<SysUser> getSysUserList() {
		return sysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
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


	public ExamineRecord getExamineRecord() {
		return examineRecord;
	}


	public void setExamineRecord(ExamineRecord examineRecord) {
		this.examineRecord = examineRecord;
	}
}
