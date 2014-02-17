/**
 * ExamineService.java
 *
 * 2013-7-4
 */
package com.lims.service.examine;

import com.lims.domain.po.AnalysisRecord;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.ExamineRecord;
import com.lims.domain.po.PcrRecord;
import com.lims.domain.po.ReviewRecord;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface ExamineService {

	/**
	 * 保存扩增记录
	 * @param pcrRecord
	 */
	public void addPcrRecord(BoardInfo boardInfo, PcrRecord pcrRecord) throws ServiceException;


	/**
	 * 保存检测记录
	 * @param examineRecord
	 */
	public void addExaminRecord(BoardInfo boardInfo, ExamineRecord examineRecord) throws ServiceException;

	/**
	 * 保存分析记录
	 * @param analysisRecord
	 */
	public void addAnalysisRecord(AnalysisRecord analysisRecord) throws ServiceException;

	/**
	 * 保存复核记录
	 * @param reviewRecord
	 */
	public void addReviewRecord(ReviewRecord reviewRecord) throws ServiceException;

	/**
	 * 修改分析记录
	 * @param analysisRecord
	 */
	public void updateAnalysisRecord(AnalysisRecord analysisRecord) throws ServiceException;



	/**
	 * 根据上样板id获取分析记录
	 */
	public AnalysisRecord findAnalysisRecordByBoardId(String boardInfoId) throws ServiceException;

	/**
	 * 根据上样板id获取扩增记录
	 */
	public PcrRecord findPcrRecordByBoardInfoId(String boardInfoId) throws ServiceException;

	public ExamineRecord findExamineRecordByBoardInfoId(String boardInfoId) throws ServiceException;

	public ReviewRecord findReviewRecordByBoardInfoId(String boardInfoId) throws ServiceException;
}
