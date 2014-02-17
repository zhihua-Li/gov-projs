/**
 * SampleInfoService.java
 *
 * 2013-7-10
 */
package com.lims.service.sample;

import java.util.List;
import java.util.Map;

import com.lims.domain.po.SampleInfo;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.domain.vo.SampleInfoView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface SampleInfoService {


	/**
	 * 保存样本信息
	 * @param sampleInfo
	 * @return sampleId
	 * @throws ServiceException
	 */
	public String addSampleInfo(SampleInfo sampleInfo) throws ServiceException;

	public void addBatchSampleInfos(List<SampleInfo> sampleInfoList) throws ServiceException;

	/**
	 * 更新样本信息
	 * @param sampleInfo
	 * @return
	 * @throws ServiceException
	 */
	public void updateSampleInfo(SampleInfo sampleInfo) throws ServiceException;

	/**
	 * 根据委托id获取样本数目
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoCountByConsignmentId(String consignmentId) throws ServiceException;

	/**
	 * 查询样本列表
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleInfoView> findSampleInfoViewList(SampleInfoQuery sampleInfoQuery) throws ServiceException;

	/**
	 * 获取样本个数
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoViewListCount(SampleInfoQuery sampleInfoQuery) throws ServiceException;


	/**
	 * 查询样本列表
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleInfoView> findSampleInfoQueryList(SampleInfoQuery sampleInfoQuery) throws ServiceException;


	/**
	 * 获取样本个数
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findSampleInfoQueryListCount(SampleInfoQuery sampleInfoQuery) throws ServiceException;


	public int selectSampleCountByBoardId(String boardId) throws ServiceException;

	/**
	 * 根据样本编号获取样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public SampleInfoView findSampleInfoBySampleNo(String sampleNo) throws ServiceException;

	/**
	 * 根据样本ID获取样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public SampleInfoView findSampleInfoById(String sampleId) throws ServiceException;


	public void deleteSampleInfoById(String id) throws ServiceException;

	public void deleteSampleInfoByIdList(List<String> sampleIdList) throws ServiceException;

	public void updateSampleInfoBySelective(SampleInfo sampleInfo) throws ServiceException;

	/**
	 * 根据样本条码号列表批量更新受理状态
	 * @param params
	 * @throws ServiceException
	 */
	public void updateAcceptStatusBatch(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据样本受理时间统计每个委托单位样本个数
	 * @param sampleInfoQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> sampleStatistics(SampleInfoQuery sampleInfoQuery) throws ServiceException;


}
