/**
 * PcrInfoAction.java
 *
 * 2013-6-3
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
import com.lims.domain.po.PcrRecord;
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
 * 扩增检测Action
 *
 */
public class PcrInfoAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfoQuery boardInfoQuery;

	private BoardInfo boardInfo;

	private PcrRecord pcrRecord;

	private List<BoardInfoView> boardInfoList;

	private List<DictItem> pcrSystemList;

	private List<DictItem> pcrLoopCountList;

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

	/**
	 * 查询待扩增列表
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("boardNo");
			boardInfoQuery.setSortOrder("ASC");
		}else{
			boardInfoQuery = trimQueryOptions(boardInfoQuery);
		}

		boardInfoQuery.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);

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
	 * 进入pcr详情页面
	 * @return
	 * @throws Exception
	 */
	public String into() throws Exception {
		initDictInfo();
		sysUserList = sysUserService.findSysUserList(new SysUser());

		pcrRecord = new PcrRecord();
		pcrRecord.setBoardInfoId(boardInfo.getId());
		pcrRecord.setPcrDate(new Date(System.currentTimeMillis()));
		boardInfo.setExamineDate(new Date(System.currentTimeMillis()));

		//设置循环数的默认值
		for(DictItem di : pcrLoopCountList){
			if(di.getDictValue().equals("28")){
				pcrRecord.setLoopCount(di.getDictKey());
				break;
			}
		}

		return SUCCESS;
	}


	/**
	 * 提交PCR检测
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {

		SysUser sysUser = getLoginSysUser();
		pcrRecord.setCreateUser(sysUser.getId());

		BoardInfo bi = boardInfoService.findBoardInfoById(boardInfo.getId());
		examineService.addPcrRecord(bi, pcrRecord);

		return SUCCESS;
	}


	private void initDictInfo() throws Exception {
		pcrSystemList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_SYSTEM);
		pcrLoopCountList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_LOOP_COUNT);
		examineInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_INSTRUMENT);
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


	public List<BoardInfoView> getBoardInfoList() {
		return boardInfoList;
	}


	public void setBoardInfoList(List<BoardInfoView> boardInfoList) {
		this.boardInfoList = boardInfoList;
	}


	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}


	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
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


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}


	public PcrRecord getPcrRecord() {
		return pcrRecord;
	}


	public void setPcrRecord(PcrRecord pcrRecord) {
		this.pcrRecord = pcrRecord;
	}


	public BoardInfo getBoardInfo() {
		return boardInfo;
	}


	public void setBoardInfo(BoardInfo boardInfo) {
		this.boardInfo = boardInfo;
	}


	public ExamineService getExamineService() {
		return examineService;
	}


	public void setExamineService(ExamineService examineService) {
		this.examineService = examineService;
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


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
}
