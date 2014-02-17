/**
 * LocusInfoService.java
 *
 * 2013-7-9
 */
package com.lims.service.locus;

import java.util.List;

import com.lims.domain.po.LocusInfo;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface LocusInfoService {

	/**
	 * 根据id获取基因座信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public LocusInfo findLocusInfoById(String id) throws ServiceException;

	/**
	 * 查询基因座列表
	 * @param locusInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<LocusInfo> findLocusInfoList(LocusInfo locusInfo) throws ServiceException;

	/**
	 * 获取所有基因座列表
	 * @return
	 * @throws ServiceException
	 */
	public List<LocusInfo> findAllLocusInfoList() throws ServiceException;


	/**
	 * 新增基因座
	 * @param locusInfo
	 * @throws ServiceException
	 */
	public LocusInfo insertLocusInfo(LocusInfo locusInfo) throws ServiceException;

	/**
	 * 更新基因座列表
	 * @param locusInfo
	 * @throws ServiceException
	 */
	public void updateLocusInfo(LocusInfo locusInfo) throws ServiceException;

}
