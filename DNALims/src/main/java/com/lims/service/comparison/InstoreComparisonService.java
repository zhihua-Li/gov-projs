/**
 * InstoreComparisonService.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.List;

import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface InstoreComparisonService {


	/**
	 * 查询未入库样本列表
	 * @param sampleSourceGeneQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleSourceGeneView> findWaitingInstoreSampleList(SampleSourceGeneQuery sampleSourceGeneQuery)
			throws ServiceException;

	/**
	 * 查询未入库样本个数
	 * @param sampleSourceGeneQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findWaitingInstoreSampleListCount(SampleSourceGeneQuery sampleSourceGeneQuery)
			throws ServiceException;


	/**
	 *
	 * 入库
	 * @param sourceGeneList
	 * @throws ServiceException
	 */
	public void instoreSampleSourceGeneList(List<SampleSourceGeneView> sourceGeneList, SysUser sysUser) throws ServiceException;
}
