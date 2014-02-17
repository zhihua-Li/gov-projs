/**
 * ConsignmentService.java
 *
 * 2013-6-16
 */
package com.lims.service.consignment;

import java.util.List;

import com.lims.domain.po.Consignment;
import com.lims.domain.query.ConsignmentQuery;
import com.lims.domain.vo.ConsignmentView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface ConsignmentService {

	/**
	 *  保存委托信息
	 * @param consignment
	 * @return
	 * @throws ServiceException
	 */
	public Consignment addConsignmentInfo(Consignment consignment) throws ServiceException;

	public void updateConsignment(Consignment consignment) throws ServiceException;

	/**
	 * 根据id获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ConsignmentView findConsignmentViewById(String id) throws ServiceException;

	/**
	 * 根据id获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Consignment findConsignmentById(String id) throws ServiceException;

	/*
	 * 删除委托及委托相关的业务数据
	 */
	public void deleteConsignmentById(String id) throws ServiceException;

	/**
	 * 根据委托号获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Consignment findConsignmentByNo(String consignmentNo) throws ServiceException;

	/**
	 * 获取委托列表
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<ConsignmentView> findConsignmentList(ConsignmentQuery query) throws ServiceException;

	/**
	 * 获取委托列表个数
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public int findConsignmentListCount(ConsignmentQuery query) throws ServiceException;
}
