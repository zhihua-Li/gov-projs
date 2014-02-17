/**
 * QualityControlSampleService.java
 *
 * 2013-7-12
 */
package com.lims.service.quality;

import java.util.List;

import com.lims.domain.po.PolluteRecord;
import com.lims.domain.po.QualityControlSample;
import com.lims.domain.query.PolluteRecordQuery;
import com.lims.domain.vo.PolluteRecordView;
import com.lims.domain.vo.QualityControlSampleView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 * 质控人员Service
 */
public interface QualityControlSampleService {


	/**
	 * 查询质控人员列表
	 * @param qcs
	 * @return
	 * @throws ServiceException
	 */
	public List<QualityControlSampleView> findQualityControlSampleList(QualityControlSample qcs)
			throws ServiceException;


	/**
	 * 查询质控人员个数
	 * @param qcs
	 * @return
	 * @throws ServiceException
	 */
	public int findQualityControlSampleListCount(QualityControlSample qcs)
			throws ServiceException;


	/**
	 * 根据id获取质控人员信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public QualityControlSampleView findQualityControlSampleById(String id) throws ServiceException;

	/**
	 * 新增质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void insertQualityControlSample(QualityControlSampleView qualityControlSample) throws ServiceException;

	/**
	 * 更新质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void updateQualityControlSample(QualityControlSampleView qualityControlSample) throws ServiceException;

	/**
	 * 根据id删除质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void deleteQualityControlSample(String id) throws ServiceException;

	/**
	 * 获取污染记录列表
	 * @return
	 * @throws ServiceException
	 */
	public List<PolluteRecordView> findPolluteRecordViewList(PolluteRecordQuery polluteRecordQuery) throws ServiceException;

	/**
	 * 获取污染记录条数
	 * @return
	 * @throws ServiceException
	 */
	public int findPolluteRecordViewListCount(PolluteRecordQuery polluteRecordQuery) throws ServiceException;

	/**
	 * 添加污染记录
	 * @param pr
	 * @throws ServiceException
	 */
	public void addPolluteRecord(PolluteRecord pr) throws ServiceException;

}
