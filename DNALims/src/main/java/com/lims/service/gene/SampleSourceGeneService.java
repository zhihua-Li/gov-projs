/**
 * SampleSourceGeneService.java
 *
 * 2013-7-10
 */
package com.lims.service.gene;

import java.util.List;

import com.lims.domain.po.SampleSourceGene;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface SampleSourceGeneService {


	/**
	 * 批量插入样本基因
	 * @param sourceGeneList
	 * @throws ServiceException
	 */
	public void insertSampleSourceGeneList(List<SampleSourceGene> sourceGeneList) throws ServiceException;

	/**
	 * 插入样本基因
	 * @param sampleSourceGene
	 * @throws ServiceException
	 */
	public void insertSampleSourceGene(SampleSourceGene sampleSourceGene) throws ServiceException;


	/**
	 * 修改样本基因
	 * @param sampleSourceGene
	 * @throws ServiceException
	 */
	public void updateSampleSourceGene(SampleSourceGene sampleSourceGene) throws ServiceException;

	/**
	 * 批量复核
	 * @throws ServiceException
	 */
	public void updateGeneReviewStatusBatch(List<String> geneIdList, String reviewStatus, String reviewDesc, SysUser sysUser) throws ServiceException;


	/**
	 * 获取SampleSourceGene列表
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneViewList(SampleSourceGeneQuery query) throws ServiceException;


	/**
	 * 获取SampleSourceGene个数
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleSourceGeneViewCount(SampleSourceGeneQuery query) throws ServiceException;


	/**
	 * 根据id获取样本基因信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SampleSourceGeneView findSampleSourceGeneById(String id) throws ServiceException;

	/**
	 * 根据id列表获取基因列表
	 * @return
	 * @throws ServiceExcepton
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneByIdList(List<String> idList) throws ServiceException;
	
	/**
	 * 根据sampleNo列表获取基因列表
	 * @return
	 * @throws ServiceExcepton
	 */
	public List<SampleSourceGeneView> findSampleSourceGeneBySampleNoList(List<String> sampleNoList) throws ServiceException;

}
