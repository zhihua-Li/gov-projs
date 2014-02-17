/**
 * CodisSampleService.java
 *
 * 2013-10-8
 */
package com.lims.service.codissample;

import java.util.List;

import com.lims.domain.po.CodisSample;
import com.lims.domain.po.GeneInfo;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface CodisSampleService {

	/**
	 * 根据样本编号获取CODIS样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public CodisSample findCodisSampleBySampleNo(String sampleNo) throws ServiceException;


	/**
	 * 保存导入的codis中的样本及基因信息
	 * @throws ServiceException
	 */
	public void insertImportCodisSample(List<CodisSample> codisSampleList, List<GeneInfo> geneInfoList) throws ServiceException;


	/**
	 * 删除codis样本
	 * @param sampleNo
	 * @throws ServiceException
	 */
	public void deleteSampleBySampleNo(String sampleNo) throws ServiceException;

	/**
	 * 删除codis样本
	 * @param sampleNo
	 * @throws ServiceException
	 */
	public void deleteSamplesBySampleNoArr(String[] sampleNoArr) throws ServiceException;


	/**
	 * 修改codis样本
	 * @param codisSample
	 * @throws ServiceException
	 */
	public void updateCodisSample(CodisSample codisSample) throws ServiceException;


	/**
	 * 获取codis样本总数
	 * @param codisSample
	 * @return
	 * @throws ServiceException
	 */
	public int findCodisSampleListCount(CodisSample codisSample) throws ServiceException;

	/**
	 * 获取codis样本列表
	 * @param codisSample
	 * @return
	 * @throws ServiceException
	 */
	public List<CodisSample> findCodisSampleList(CodisSample codisSample) throws ServiceException;


	public GeneInfo findCodisSampleGeneInfoBySampleId(String sampleId) throws ServiceException;

	public GeneInfo findCodisSampleGeneInfoById(String sampleId) throws ServiceException;
}
