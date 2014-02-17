/**
 * SysRoleService.java
 *
 * 2013-5-26
 */
package com.lims.service.sysrole;

import java.util.List;

import com.lims.domain.po.SysRole;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 * 角色service
 */
public interface SysRoleService {

	/**
	 * 获取指定用户的角色列表
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findSysRoleBySysUserId(String userId) throws ServiceException;


	/**
	 * 获取所有角色列表
	 *
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findAllSysRoles() throws ServiceException;


	/**
	 * 获取角色列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findSysRoleList(SysRole sysRole) throws ServiceException;


	/**
	 * 获取角色个数
	 * @return
	 * @throws ServiceException
	 */
	public int findSysRoleListCount(SysRole sysRole) throws ServiceException;


	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SysRole findSysRoleById(String id) throws ServiceException;

	/**
	 * 新增角色
	 * @param sysRole
	 * @param sysMenuIdList
	 * @throws ServiceException
	 */
	public void addSysRole(SysRole sysRole, List<String> sysMenuIdList) throws ServiceException;

	/**
	 * 更新角色
	 * @param sysRole
	 * @param sysMenuIdList
	 * @throws ServiceException
	 */
	public void updateSysRole(SysRole sysRole, List<String> sysMenuIdList) throws ServiceException;
}
