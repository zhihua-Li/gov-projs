/**
 * SampleInfoServiceImpl.java
 *
 * 2013-7-10
 */
package com.lims.service.sample;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.SampleInfo;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.domain.vo.SampleInfoView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("sampleInfoService")
@Transactional
public class SampleInfoServiceImpl extends LimsBaseServiceImpl implements
		SampleInfoService {


	/**
	 * 新增样本
	 * @param sampleInfo
	 * @throws ServiceException
	 */
	public String addSampleInfo(SampleInfo sampleInfo) throws ServiceException {
		try {
			String sampleId = keyGeneratorService.getNextKey();
			sampleInfo.setId(sampleId);

			dao.getSqlSession().insert("SampleInfoMapper.insertSampleInfo", sampleInfo);

			return sampleId;
		} catch(Exception e) {
			log.error("invoke SampleInfoService.addSampleInfo error!", e);
			throw new ServiceException(e);
		}
	}

	public void addBatchSampleInfos(List<SampleInfo> sampleInfoList) throws ServiceException {
		try {
			dao.getSqlSession().insert("SampleInfoMapper.insertBatchSampleInfo", sampleInfoList);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.addBatchSampleInfos error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新样本信息
	 * @param sampleInfo
	 * @return
	 * @throws ServiceException
	 */
	public void updateSampleInfo(SampleInfo sampleInfo) throws ServiceException {
		try {
			dao.getSqlSession().update("SampleInfoMapper.updateSampleInfo", sampleInfo);

		} catch(Exception e) {
			log.error("invoke SampleInfoService.updateSampleInfo error!", e);
			throw new ServiceException(e);
		}
	}

	public void updateSampleInfoBySelective(SampleInfo sampleInfo) throws ServiceException {
		try {
			dao.getSqlSession().update("SampleInfoMapper.updateSampleInfoBySelective", sampleInfo);

		} catch(Exception e) {
			log.error("invoke SampleInfoService.updateSampleInfoBySelective error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据样本条码号列表批量更新受理状态
	 * @param params
	 * @throws ServiceException
	 */
	public void updateAcceptStatusBatch(Map<String, Object> params) throws ServiceException {
		try {
			dao.getSqlSession().update("SampleInfoMapper.updateAcceptStatusBatch", params);

		} catch(Exception e) {
			log.error("invoke SampleInfoService.updateAcceptStatusBatch error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据委托id获取样本数目
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoCountByConsignmentId(String consignmentId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoCountByConsignmentId", consignmentId);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.addSampleInfo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询样本列表
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleInfoView> findSampleInfoViewList(SampleInfoQuery sampleInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SampleInfoMapper.findSampleInfoViewList", sampleInfoQuery);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoViewList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取样本个数
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoViewListCount(SampleInfoQuery sampleInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoViewListCount", sampleInfoQuery);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoViewListCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 查询样本列表
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleInfoView> findSampleInfoQueryList(SampleInfoQuery sampleInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SampleInfoMapper.findSampleInfoQueryList", sampleInfoQuery);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoQueryList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取样本个数
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoQueryListCount(SampleInfoQuery sampleInfoQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoQueryListCount", sampleInfoQuery);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoViewListCount error!", e);
			throw new ServiceException(e);
		}
	}

	public int selectSampleCountByBoardId(String boardId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.selectSampleCountByBoardId", boardId);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.selectSampleCountByBoardId error!", e);
			throw new ServiceException(e);
		}
	}



	/**
	 * 根据样本编号获取样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public SampleInfoView findSampleInfoBySampleNo(String sampleNo) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoBySampleNo", sampleNo);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoBySampleNo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据样本ID获取样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public SampleInfoView findSampleInfoById(String sampleId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleInfoMapper.findSampleInfoById", sampleId);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.findSampleInfoBySampleNo error!", e);
			throw new ServiceException(e);
		}
	}

	public void deleteSampleInfoById(String id) throws ServiceException {
		try {

			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardSampleMapBySampleId", id);

			//删除基因信息
			dao.getSqlSession().delete("SampleSourceGeneMapper.deleteSampleSourceGeneBySampleId", id);
			dao.getSqlSession().delete("GeneInfoMapper.deleteGeneInfoBySampleId", id);

			dao.getSqlSession().delete("SampleInfoMapper.deleteSampleInfoById", id);
		} catch(Exception e) {
			log.error("invoke SampleInfoService.deleteSampleInfoById error!", e);
			throw new ServiceException(e);
		}
	}

	public void deleteSampleInfoByIdList(List<String> sampleIdList) throws ServiceException {
		for(String id : sampleIdList){
			deleteSampleInfoById(id);
		}
	}

	/**
	 * 根据样本受理时间统计每个委托单位样本个数
	 * @param acceptDateStart
	 * @param acceptDateEnd
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> sampleStatistics(SampleInfoQuery sampleInfoQuery) throws ServiceException {
		try {
			List<Map<String, Object>> result = dao.getSqlSession().selectList("SampleInfoMapper.findSampleCountByAcceptDate", sampleInfoQuery);

			return result;
		} catch(Exception e) {
			log.error("invoke SampleInfoService.deleteSampleInfoById error!", e);
			throw new ServiceException(e);
		}
	}
}
