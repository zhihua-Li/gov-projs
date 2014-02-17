/**
 * SysUserService.java
 * 2013-05-23
 */
package com.lims.service.sysuser;

import java.util.List;

import com.lims.domain.po.SysUser;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 * 系统用户service
 *
 */
public interface SysUserService {

	/**
	 * 根据用户名、密码获取用户信息
	 * @param userName	用户名
	 * @param password	密码
	 * @return
	 * @throws Exception
	 */
	public SysUser findSysUserByUserNameAndPassword(String userName, String password) throws Exception;


	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	public SysUser findSysUserById(String id) throws Exception;

	/**
	 * 获取用户列表
	 *
	 * @param sysUser
	 * @return 符合条件的系统用户列表
	 * @throws ServiceException
	 */
	public List<SysUser> findSysUserList(SysUser sysUser) throws Exception;


	/**
	 * 获取用户个数
	 *
	 * @param sysUser
	 * @return 符合条件的系统用户个数
	 * @throws ServiceException
	 */
	public int findSysUserListCount(SysUser sysUser) throws Exception;

	/**
	 *
	 * 添加系统用户
	 *
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public String addSysUser(SysUser sysUser, List<String> roleIdList) throws Exception;


	/**
	 *
	 * 更新系统用户
	 *
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public boolean updateSysUser(SysUser sysUser, List<String> roleIdList) throws Exception;

	public void updateUserPassword(SysUser sysUser) throws Exception;


	/**
	 *
	 * 删除系统用户
	 *
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public boolean deleteSysUser(String id) throws Exception;


}
