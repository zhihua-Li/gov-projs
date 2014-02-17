/**
 * ExamineRecordAction.java
 *
 * 2013-6-3
 */
package com.lims.action.examine;

import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.AnalysisRecord;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.PcrRecord;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.service.dict.SysDictService;
import com.lims.service.examine.ExamineService;
import com.lims.service.sample.BoardInfoService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 * 检验过程记录Action
 *
 */
public class ExamineRecordAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfo boardInfo;

	private BoardInfoQuery boardInfoQuery;

	private AnalysisRecord analysisRecord;

	private PcrRecord pcrRecord;

	private List<BoardInfoView> boardInfoList;

	private List<DictItem> pcrSystemList;

	private List<DictItem> pcrLoopCountList;

	private List<DictItem> analysisInstrumentList;
	private List<DictItem> examineInstrumentList;
	private List<DictItem> examineStatusList;


	@Resource
	private BoardInfoService boardInfoService;

	@Resource
	private ExamineService examineService;

	@Resource
	private SysDictService sysDictService;

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("examineDate");
			boardInfoQuery.setSortOrder("DESC");
		}else{
			trimQueryOptions();
		}
//		boardInfoQuery.setPcrFlag(LimsConstants.FLAG_TRUE);

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		examineStatusList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_STATUS);

//		for(BoardInfoView bv : boardInfoList){
//			if(LimsConstants.EXAMINE_STATUS_PCRED.equals(bv.getExamineStatus())){
//				bv.setExamineStatusName("已扩增");
//			}if(LimsConstants.EXAMINE_STATUS_EXAMINED.equals(bv.getExamineStatus())){
//				bv.setExamineStatusName("已检测");
//			}if(LimsConstants.EXAMINE_STATUS_ANALYSISED.equals(bv.getExamineStatus())){
//				bv.setExamineStatusName("已分析");
//			}if(LimsConstants.EXAMINE_STATUS_REVIEWED.equals(bv.getExamineStatus())){
//				bv.setExamineStatusName("已复核");
//			}
//		}

		return SUCCESS;
	}


	public String view() throws Exception {
		boardInfo = boardInfoService.findBoardInfoById(boardInfoQuery.getId());
		analysisRecord = examineService.findAnalysisRecordByBoardId(boardInfoQuery.getId());
		pcrRecord = examineService.findPcrRecordByBoardInfoId(boardInfoQuery.getId());

		pcrSystemList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_SYSTEM);
		pcrLoopCountList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_LOOP_COUNT);
		analysisInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.ANALYSIS_INSTRUMENT);
		examineInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_INSTRUMENT);

		return SUCCESS;
	}

	private void trimQueryOptions() {
		if(StringUtil.isNullOrEmpty(boardInfoQuery.getConsignmentNo())){
			boardInfoQuery.setConsignmentNo(null);
		}else{
			boardInfoQuery.setConsignmentNo(boardInfoQuery.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getSampleNo())){
			boardInfoQuery.setSampleNo(null);
		}else{
			boardInfoQuery.setSampleNo(boardInfoQuery.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getExamineUser())){
			boardInfoQuery.setExamineUser(null);
		}else{
			boardInfoQuery.setExamineUser(boardInfoQuery.getExamineUser().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getExamineStatus())){
			boardInfoQuery.setExamineStatus(null);
		}else{
			boardInfoQuery.setExamineStatus(boardInfoQuery.getExamineStatus().trim());
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

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getBoardNo())){
			boardInfoQuery.setBoardNo(null);
		}else{
			boardInfoQuery.setBoardNo(boardInfoQuery.getBoardNo().trim());
		}
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


	public ExamineService getExamineService() {
		return examineService;
	}


	public void setExamineService(ExamineService examineService) {
		this.examineService = examineService;
	}


	public AnalysisRecord getAnalysisRecord() {
		return analysisRecord;
	}


	public void setAnalysisRecord(AnalysisRecord analysisRecord) {
		this.analysisRecord = analysisRecord;
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


	public List<DictItem> getAnalysisInstrumentList() {
		return analysisInstrumentList;
	}


	public void setAnalysisInstrumentList(List<DictItem> analysisInstrumentList) {
		this.analysisInstrumentList = analysisInstrumentList;
	}


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}


	public List<DictItem> getExamineInstrumentList() {
		return examineInstrumentList;
	}


	public void setExamineInstrumentList(List<DictItem> examineInstrumentList) {
		this.examineInstrumentList = examineInstrumentList;
	}


	public List<DictItem> getExamineStatusList() {
		return examineStatusList;
	}


	public void setExamineStatusList(List<DictItem> examineStatusList) {
		this.examineStatusList = examineStatusList;
	}
}
