/**
 * CompareResultServiceImpl.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.CompareQueue;
import com.lims.domain.po.CompareResult;
import com.lims.domain.query.CompareResultQuery;
import com.lims.domain.vo.CompareResultView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.ListUtil;

/**
 * @author lizhihua
 *
 */
@Service("compareResultService")
@Transactional
public class CompareResultServiceImpl extends LimsBaseServiceImpl implements
		CompareResultService {

	/**
	 * 根据id获取比中结果信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public CompareResultView findCompareResultViewById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CompareResultMapper.findCompareResultViewById", id);
		} catch(Exception e) {
			log.error("invoke CompareResultService.findCompareResultViewById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取比对结果列表
	 * @return
	 * @throws ServiceException
	 */
	public List<CompareResultView> findCompareResultList(CompareResultQuery compareResultQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("CompareResultMapper.findCompareResultList", compareResultQuery);
		} catch(Exception e) {
			log.error("invoke CompareResultService.findCompareResultList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取比对结果个数
	 * @return
	 * @throws ServiceException
	 */
	public int findCompareResultListCount(CompareResultQuery compareResultQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CompareResultMapper.findCompareResultListCount", compareResultQuery);
		} catch(Exception e) {
			log.error("invoke CompareResultService.findCompareResultListCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 保存比对结果
	 * @throws ServiceException
	 */
	public void insertCompareResult(List<CompareResult> compareResultList, CompareQueue comparQueue) throws ServiceException {
		try {
			if(!ListUtil.isEmptyList(compareResultList)){
				for(CompareResult cr : compareResultList){
					cr.setId(keyGeneratorService.getNextKey());
				}

				dao.getSqlSession().insert("CompareResultMapper.insertCompareResult", compareResultList);
			}

			dao.getSqlSession().update("CompareQueueMapper.updateCompareQueue", comparQueue);
		} catch(Exception e) {
			log.error("invoke CompareResultService.insertCompareResult error!", e);
			throw new ServiceException(e);
		}
	}

}
