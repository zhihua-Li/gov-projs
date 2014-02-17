/**
 * SampleSourceGeneServiceImpl.java
 *
 * 2013-7-10
 */
package com.lims.service.gene;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.SampleSourceGene;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.DateUtil;

/**
 * @author lizhihua
 *
 */
@Service("sampleSourceGeneService")
@Transactional
public class SampleSourceGeneServiceImpl extends LimsBaseServiceImpl implements SampleSourceGeneService {

	/**
	 * 批量插入样本基因
	 * @param sourceGeneList
	 * @throws ServiceException
	 */
	public void insertSampleSourceGeneList(List<SampleSourceGene> sourceGeneList) throws ServiceException {
		try {
			for(SampleSourceGene ssg : sourceGeneList){
				ssg.setId(keyGeneratorService.getNextKey());
				ssg.setCreateDate(DateUtil.currentDate());
			}

			dao.getSqlSession().insert("SampleSourceGeneMapper.insertSampleSourceGeneList", sourceGeneList);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.insertSampleSourceGeneList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 插入样本基因
	 * @param sampleSourceGene
	 * @throws ServiceException
	 */
	public void insertSampleSourceGene(SampleSourceGene sampleSourceGene) throws ServiceException {
		try {
			sampleSourceGene.setId(keyGeneratorService.getNextKey());
			sampleSourceGene.setCreateDate(DateUtil.currentDate());

			dao.getSqlSession().insert("SampleSourceGeneMapper.insertSampleSourceGene", sampleSourceGene);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.insertSampleSourceGene error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改样本基因
	 * @param sampleSourceGene
	 * @throws ServiceException
	 */
	public void updateSampleSourceGene(SampleSourceGene sampleSourceGene) throws ServiceException {
		try {
			sampleSourceGene.setUpdateDate(DateUtil.currentDate());

			dao.getSqlSession().update("SampleSourceGeneMapper.updateSampleSourceGene", sampleSourceGene);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.updateSampleSourceGene error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 批量复核
	 * @throws ServiceException
	 */
	public void updateGeneReviewStatusBatch(List<String> geneIdList, String reviewStatus, String reviewDesc, SysUser sysUser) throws ServiceException {
		try {
			Date currentDate = DateUtil.currentDate();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reviewStatus", reviewStatus);
			if(reviewDesc != null){
				params.put("reviewDesc", reviewDesc);
			}
			params.put("reviewDate", currentDate);
			params.put("reviewUser", sysUser.getId());
			params.put("updateDate", currentDate);
			params.put("updateUser", sysUser.getId());
			params.put("geneIdList", geneIdList);

			dao.getSqlSession().update("SampleSourceGeneMapper.updateGeneReviewStatusBatch", params);

//			dao.getSqlSession().update("BoardInfoMapper.updateReviewStatusBatch", params);

		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.updateGeneReviewStatusBatch error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取SampleSourceGene列表
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneViewList(SampleSourceGeneQuery query)
		throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SampleSourceGeneMapper.findSampleSourceGeneViewList", query);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.findSampleSourceGeneViewList error!", e);
			throw new ServiceException(e);
		}
	}



	/**
	 * 获取SampleSourceGene个数
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleSourceGeneViewCount(SampleSourceGeneQuery query)
		throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleSourceGeneMapper.findSampleSourceGeneViewCount", query);

		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.findSampleSourceGeneViewCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 根据id获取样本基因信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SampleSourceGeneView findSampleSourceGeneById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SampleSourceGeneMapper.findSampleSourceGeneById", id);

		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.findSampleSourceGeneById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id列表获取基因列表
	 * @return
	 * @throws ServiceExcepton
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneByIdList(List<String> idList) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SampleSourceGeneMapper.findSampleSourceGeneByIdList", idList);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.findSampleSourceGeneByIdList error!", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据sampleNo列表获取基因列表
	 * @return
	 * @throws ServiceExcepton
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneBySampleNoList(List<String> sampleNoList) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SampleSourceGeneMapper.findSampleSourceGeneBySampleNoList", sampleNoList);
		} catch(Exception e) {
			log.error("invoke SampleSourceGeneService.findSampleSourceGeneBySampleNoList error!", e);
			throw new ServiceException(e);
		}
	}
}
