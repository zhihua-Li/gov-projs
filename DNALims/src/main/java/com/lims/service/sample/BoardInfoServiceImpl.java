/**
 * BoardInfoServiceImpl.java
 *
 * 2013-6-18
 */
package com.lims.service.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.constants.LimsConstants;
import com.lims.constants.StaticSample;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.BoardSampleMap;
import com.lims.domain.po.SampleInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
@Service("boardInfoService")
@Transactional
public class BoardInfoServiceImpl extends LimsBaseServiceImpl implements
		BoardInfoService {

	/**
	 * 添加boardInfo
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public String addBoardInfo(BoardInfo boardInfo, String[] sampleNoArr, String[] positionNoArr, SysUser sysUser) throws ServiceException {
		try {
			String id = keyGeneratorService.getNextKey();
			boardInfo.setId(id);
			boardInfo.setBoardName(boardInfo.getBoardNo());
			boardInfo.setImportDate(DateUtil.currentDate());
			boardInfo.setImportUser(sysUser.getId());
			boardInfo.setReviewStatus(LimsConstants.REVIEW_STATUS_WAITING);
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);
			boardInfo.setPcrFlag(LimsConstants.FLAG_FALSE);
			boardInfo.setAnalysisFlag(LimsConstants.FLAG_FALSE);

			dao.getSqlSession().insert("BoardInfoMapper.insertBoardInfo", boardInfo);

//			List<SampleInfo> sampleInfoList = new ArrayList<SampleInfo>();
			List<BoardSampleMap> boardSampleMapList = new ArrayList<BoardSampleMap>();
			SampleInfoView si = null;
			BoardSampleMap bsm = null;
			for(int i = 0; i < positionNoArr.length; i++){
				if(StringUtil.isNotEmpty(sampleNoArr[i])){
					si = dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoBySampleNo", sampleNoArr[i]);
					if(si == null){
						continue;
					}

					bsm = new BoardSampleMap();
					bsm.setId(keyGeneratorService.getNextKey());
					bsm.setSampleId(si.getId());
					bsm.setBoardId(id);
					bsm.setSamplePosition(positionNoArr[i]);

					boardSampleMapList.add(bsm);
				}
			}

			dao.getSqlSession().insert("BoardInfoMapper.insertBatchBoardSampleMap", boardSampleMapList);

			return id;
		} catch(Exception e) {
			log.error("invoke BoardInfoService.addBoardInfo error!", e);
			throw new ServiceException(e);
		}
	}

	public void deleteCKSampleByBoardId(String boardId) throws ServiceException {
		try {

			HashMap<String, String> params = new HashMap<String,String>();
			params.put("boardId", boardId);
			params.put("samplePosition", StaticSample.STATIC_SAMPLE_CK_POSITION);

			dao.getSqlSession().delete("BoardSampleMapMapper.deleteBoardSampleMapByBoardIdAndPosition", params);

		} catch(Exception e) {
			log.error("invoke BoardInfoService.deleteCKSampleByBoardId error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 新增上样板
	 * @param boardInfo
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	public String addBoardInfo(BoardInfo boardInfo, SysUser sysUser) throws ServiceException {
		try {
			String id = keyGeneratorService.getNextKey();
			boardInfo.setId(id);
			boardInfo.setBoardName(boardInfo.getBoardNo());
			boardInfo.setImportDate(DateUtil.currentDate());
			boardInfo.setImportUser(sysUser.getId());
			boardInfo.setReviewStatus(LimsConstants.REVIEW_STATUS_WAITING);
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);
			boardInfo.setPcrFlag(LimsConstants.FLAG_FALSE);
			boardInfo.setAnalysisFlag(LimsConstants.FLAG_FALSE);

			dao.getSqlSession().insert("BoardInfoMapper.insertBoardInfo", boardInfo);

			return id;
		} catch(Exception e) {
			log.error("invoke BoardInfoService.addBoardInfo error!", e);
			throw new ServiceException(e);
		}
	}

	public void updateBoardInfo(BoardInfo boardInfo, String[] sampleNoArr, String[] positionNoArr, SysUser sysUser) throws ServiceException {
		try {
			boardInfo.setBoardName(boardInfo.getBoardNo());
			boardInfo.setUpdateDate(DateUtil.currentDate());
			boardInfo.setUpdateUser(sysUser.getId());
			boardInfo.setReviewStatus(LimsConstants.REVIEW_STATUS_WAITING);
			boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);
			boardInfo.setPcrFlag(LimsConstants.FLAG_FALSE);
			boardInfo.setAnalysisFlag(LimsConstants.FLAG_FALSE);

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);

			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardSampleMapByBoardId", boardInfo.getId());

			List<BoardSampleMap> boardSampleMapList = new ArrayList<BoardSampleMap>();
			SampleInfoView si = null;
			BoardSampleMap bsm = null;
			for(int i = 0; i < positionNoArr.length; i++){
				if(StringUtil.isNotEmpty(sampleNoArr[i])){
					si = dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoBySampleNo", sampleNoArr[i]);
					if(si == null){
						continue;
					}

					bsm = new BoardSampleMap();
					bsm.setId(keyGeneratorService.getNextKey());
					bsm.setSampleId(si.getId());
					bsm.setBoardId(boardInfo.getId());
					bsm.setSamplePosition(positionNoArr[i]);

					boardSampleMapList.add(bsm);
				}
			}

			dao.getSqlSession().insert("BoardInfoMapper.insertBatchBoardSampleMap", boardSampleMapList);

		} catch(Exception e) {
			log.error("invoke BoardInfoService.addBoardInfo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新上样板信息
	 * @param boardInfo
	 * @param sysUser
	 * @throws ServiceException
	 */
	public void updateBoardInfo(BoardInfo boardInfo, SysUser sysUser) throws ServiceException {
		try {
			boardInfo.setBoardName(boardInfo.getBoardNo());
			boardInfo.setUpdateDate(DateUtil.currentDate());
			boardInfo.setUpdateUser(sysUser.getId());

			dao.getSqlSession().update("BoardInfoMapper.updateBoardInfo", boardInfo);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.updateBoardInfo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新下载次数
	 */
	public void updateBoardDownloadCount(Map<String, Object> params) throws ServiceException {
		try {
			dao.getSqlSession().update("BoardInfoMapper.updateBoardDownloadCount", params);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.updateBoardDownloadCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 保存上样板与样本关系
	 * @param boardSampleMap
	 * @throws ServiceException
	 */
	public void addBoardSampleMap(BoardSampleMap boardSampleMap) throws ServiceException {
		try {
			boardSampleMap.setId(keyGeneratorService.getNextKey());

			dao.getSqlSession().update("BoardSampleMapMapper.addBoardSampleMap", boardSampleMap);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.addBoardSampleMap error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 在上样板上新增样本（打孔取样）
	 * @param boardSampleMap
	 * @throws ServiceException
	 */
	public void addSampleInBoard(SampleInfo sample, BoardSampleMap boardSampleMap) throws ServiceException {
		try {
			String sampleId = keyGeneratorService.getNextKey();
			sample.setId(sampleId);
			dao.getSqlSession().insert("SampleInfoMapper.insertSampleInfo", sample);

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("boardId", boardSampleMap.getBoardId());
			params.put("samplePosition", boardSampleMap.getSamplePosition());
			dao.getSqlSession().delete("deleteBoardSampleMapByBoardIdAndPosition", params);

			boardSampleMap.setId(keyGeneratorService.getNextKey());
			boardSampleMap.setSampleId(sampleId);
			dao.getSqlSession().insert("BoardSampleMapMapper.addBoardSampleMap", boardSampleMap);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.addBoardSampleMap error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id获取上样板信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public BoardInfo findBoardInfoById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("BoardInfoMapper.findBoardInfoById", id);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.findBoardInfoById error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 根据上样板id获取对应样本列表
	 * @return
	 * @throws ServiceException
	 */
	public List<BoardSampleMap> findBoardSampleListByBoardId(String boardId) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("BoardInfoMapper.findBoardSampleListByBoardId", boardId);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.findBoardSampleListByBoardId error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 根据boardInfo实体查询对应的上样板信息列表
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<BoardInfoView> findBoardInfoList(BoardInfoQuery boardInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("BoardInfoMapper.findBoardInfoList", boardInfoQuery);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.findBoardInfoList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据boardInfo实体查询对应的上样板信息个数
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public int findBoardInfoListCount(BoardInfoQuery boardInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("BoardInfoMapper.findBoardInfoListCount", boardInfoQuery);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.findBoardInfoListCount error!", e);
			throw new ServiceException(e);
		}
	}

	/*
	 * 删除上样板及相关数据
	 */
	public void deleteBoardInfoById(String id) throws ServiceException {
		try {
			//删除检验记录
			dao.getSqlSession().delete("PcrRecordMapper.deletePcrRecordByBoardId", id);
			dao.getSqlSession().delete("AnalysisRecordMapper.deleteAnalysisRecordByBoardId", id);

			//删除基因信息
			dao.getSqlSession().delete("SampleSourceGeneMapper.deleteSampleSourceGeneByBoardId", id);
			dao.getSqlSession().delete("GeneInfoMapper.deleteGeneInfoByBoardId", id);

			//删除上样板及样本信息
			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardSampleMapByBoardId", id);
			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardInfoById", id);
			//dao.getSqlSession().delete("SampleInfoMapper.deleteSampleInfoByBoardId", id);
		} catch(Exception e) {
			log.error("invoke BoardInfoService.deleteBoardInfoById error!", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 清除指定板孔上的样本（置空该位置，不删除样本）
	 * params:
	 * 		boardId,
	 * 		samplePosition
	 */
	public void deleteSampleOfBoardPosition(Map<String, String> params)  throws ServiceException {
		try {
			dao.getSqlSession().delete("BoardSampleMapMapper.deleteBoardSampleMapByBoardIdAndPosition", params);
		}catch(Exception e){
			log.error("invoke BoardInfoService.deleteSampleOfBoardPosition error!", e);
			throw new ServiceException(e);
		}
	}

}
