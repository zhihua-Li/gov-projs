/**
 * ExamineServiceImpl.java
 *
 * 2013-7-4
 */
package com.lims.service.examine;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.constants.LimsConstants;
import com.lims.domain.po.AnalysisRecord;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.ExamineRecord;
import com.lims.domain.po.PcrRecord;
import com.lims.domain.po.ReviewRecord;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("examineService")
@Transactional
public class ExamineServiceImpl extends LimsBaseServiceImpl implements
		ExamineService {
	/**
	 * 保存扩增记录
	 * @param pcrRecord
	 */
	public void addPcrRecord(BoardInfo boardInfo, PcrRecord pcrRecord) throws ServiceException {
		try {
			Date currentDate = new Date();
			String pcrRecordId = keyGeneratorService.getNextKey();
			pcrRecord.setId(pcrRecordId);
			pcrRecord.setCreateDate(currentDate);
			dao.getSqlSession().insert("PcrRecordMapper.insertPcrRecord", pcrRecord);

			boardInfo.setPcrFlag(LimsConstants.FLAG_TRUE);
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_PCRED);
			boardInfo.setUpdateDate(currentDate);
			boardInfo.setUpdateUser(pcrRecord.getCreateUser());

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);
		} catch(Exception e) {
			log.error("invoke ExamineService.addPcrRecord error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 保存检测记录
	 * @param examineRecord
	 */
	public void addExaminRecord(BoardInfo boardInfo, ExamineRecord examineRecord) throws ServiceException {
		try {
			Date currentDate = new Date();
			String examineRecordId = keyGeneratorService.getNextKey();
			examineRecord.setId(examineRecordId);

			dao.getSqlSession().insert("ExamineRecordMapper.insertExamineRecord", examineRecord);

			boardInfo.setExamineDate(examineRecord.getExamineDate());
			boardInfo.setExamineUser(examineRecord.getExamineUserId());
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXAMINED);
			boardInfo.setUpdateDate(currentDate);
			boardInfo.setUpdateUser(examineRecord.getExamineUserId());

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);
		} catch(Exception e) {
			log.error("invoke ExamineService.addExaminRecord error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 保存分析记录
	 * @param analysisRecord
	 */
	public void addAnalysisRecord(AnalysisRecord analysisRecord) throws ServiceException {
		try {
			Date currentDate = new Date();
			String analysisRecordId = keyGeneratorService.getNextKey();
			analysisRecord.setId(analysisRecordId);
			analysisRecord.setCreateDate(currentDate);

			dao.getSqlSession().insert("AnalysisRecordMapper.insertAnalysisRecord", analysisRecord);

			BoardInfo boardInfo = dao.getSqlSession().selectOne("BoardInfoMapper.findBoardInfoById", analysisRecord.getBoardInfoId());
			boardInfo.setAnalysisFlag(LimsConstants.FLAG_TRUE);
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_ANALYSISED);
			boardInfo.setUpdateDate(currentDate);
			boardInfo.setUpdateUser(analysisRecord.getCreateUser());

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);
		} catch(Exception e) {
			log.error("invoke ExamineService.addAnalysisRecord error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 保存复核记录
	 * @param reviewRecord
	 */
	public void addReviewRecord(ReviewRecord reviewRecord) throws ServiceException {
		try {
			Date currentDate = new Date();
			String reviewRecordId = keyGeneratorService.getNextKey();
			reviewRecord.setId(reviewRecordId);
			reviewRecord.setReviewDate(currentDate);

			dao.getSqlSession().insert("ReviewRecordMapper.insertReviewRecord", reviewRecord);

			BoardInfo boardInfo = dao.getSqlSession().selectOne("BoardInfoMapper.findBoardInfoById", reviewRecord.getBoardId());
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_REVIEWED);
			boardInfo.setUpdateDate(currentDate);
			boardInfo.setUpdateUser(reviewRecord.getReviewUserId());

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);
		} catch(Exception e) {
			log.error("invoke ExamineService.addAnalysisRecord error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改分析记录
	 * @param analysisRecord
	 */
	public void updateAnalysisRecord(AnalysisRecord analysisRecord) throws ServiceException {
		try {
			Date currentDate = new Date();
			analysisRecord.setUpdateDate(currentDate);

			dao.getSqlSession().insert("AnalysisRecordMapper.updateAnalysisRecord", analysisRecord);
		} catch(Exception e) {
			log.error("invoke ExamineService.updateAnalysisRecord error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据上样板id获取分析记录
	 */
	public AnalysisRecord findAnalysisRecordByBoardId(String boardInfoId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("AnalysisRecordMapper.findAnalysisRecordByBoardId", boardInfoId);
		} catch(Exception e) {
			log.error("invoke ExamineService.findAnalysisRecordByBoardId error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据上样板id获取扩增记录
	 */
	public PcrRecord findPcrRecordByBoardInfoId(String boardInfoId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("PcrRecordMapper.findPcrRecordByBoardInfoId", boardInfoId);
		} catch(Exception e) {
			log.error("invoke ExamineService.findPcrRecordByBoardInfoId error!", e);
			throw new ServiceException(e);
		}
	}

	public ExamineRecord findExamineRecordByBoardInfoId(String boardInfoId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ExamineRecordMapper.findExamineRecordByBoardId", boardInfoId);
		} catch(Exception e) {
			log.error("invoke ExamineService.findExamineRecordByBoardInfoId error!", e);
			throw new ServiceException(e);
		}
	}

	public ReviewRecord findReviewRecordByBoardInfoId(String boardInfoId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ReviewRecordMapper.findReviewRecordByBoardId", boardInfoId);
		} catch(Exception e) {
			log.error("invoke ExamineService.findReviewRecordByBoardInfoId error!", e);
			throw new ServiceException(e);
		}
	}

}
