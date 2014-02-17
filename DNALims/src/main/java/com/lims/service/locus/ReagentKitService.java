/**
 * ReagentKitService.java
 *
 * 2013-7-9
 */
package com.lims.service.locus;

import java.util.List;

import com.lims.domain.po.ReagentKit;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 * 试剂盒Service
 */
public interface ReagentKitService {

	/**
	 * 根据id获取试剂盒信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ReagentKit findReagentKitById(String id) throws Exception;

	/**
	 * 查询试剂盒列表
	 * @param reagentKit
	 * @return
	 * @throws Exception
	 */
	public List<ReagentKit> findReagentKitList(ReagentKit reagentKit) throws Exception;

	/**
	 * 获取所有试剂盒列表
	 * @return
	 * @throws Exception
	 */
	public List<ReagentKit> finalAllReagentKitList() throws Exception;


	/**
	 * 新增试剂盒
	 * @param reagentKit
	 * @throws Exception
	 */
	public void insertReagentKit(ReagentKit reagentKit) throws Exception;

	/**
	 * 更新试剂盒列表
	 * @param reagentKit
	 * @throws Exception
	 */
	public void updateReagentKit(ReagentKit reagentKit) throws Exception;


	public void deleteReagentKitById(String id) throws Exception;

}
