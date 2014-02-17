/**
 * CompareResultService.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.List;

import com.lims.domain.po.CompareQueue;
import com.lims.domain.po.CompareResult;
import com.lims.domain.query.CompareResultQuery;
import com.lims.domain.vo.CompareResultView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface CompareResultService {


	/**
	 * 根据id获取比中结果信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public CompareResultView findCompareResultViewById(String id) throws ServiceException;

	/**
	 * 获取比对结果列表
	 * @return
	 * @throws ServiceException
	 */
	public List<CompareResultView> findCompareResultList(CompareResultQuery compareResultQuery) throws ServiceException;


	/**
	 * 获取比对结果个数
	 * @return
	 * @throws ServiceException
	 */
	public int findCompareResultListCount(CompareResultQuery compareResultQuery) throws ServiceException;

	/**
	 * 保存比对结果
	 * @throws ServiceException
	 */
	public void insertCompareResult(List<CompareResult> compareResultList, CompareQueue comparQueue) throws ServiceException;

}
