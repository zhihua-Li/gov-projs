/**
 * ConsignOrganizationService.java
 *
 * 2013-6-2
 */
package com.lims.service.organization;

import java.util.List;

import com.lims.domain.po.ConsignOrganization;

/**
 * @author lizhihua
 *
 */
public interface ConsignOrganizationService {


	/**
	 * 获取委托单位列表
	 * @return
	 * @throws Exception
	 */
	public List<ConsignOrganization> findConsignOrganizationList(ConsignOrganization consignOrganization) throws Exception;


	/**
	 * 获取委托单位个数
	 * @return
	 * @throws Exception
	 */
	public int findConsignOrganizationListCount(ConsignOrganization consignOrganization) throws Exception;


	/**
	 * 获取所有委托单位列表
	 * @return
	 * @throws Exception
	 */
	public List<ConsignOrganization> findAllConsignOrganizationList() throws Exception;


	public ConsignOrganization findConsignOrganizationById(String id) throws Exception;


	public void addConsignOrganization(ConsignOrganization consignOrganization) throws Exception;

	public void updateConsignOrganization(ConsignOrganization consignOrganization) throws Exception;

	public void deleteConsignOrganizationById(String id) throws Exception;
}
