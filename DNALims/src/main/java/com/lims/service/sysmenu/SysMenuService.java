/**
 * SysMenuService.java
 *
 * 2013-5-26
 */
package com.lims.service.sysmenu;

import java.util.List;

import com.lims.domain.po.SysMenu;
import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysRoleMenu;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 * 菜单service
 *
 */
public interface SysMenuService {


	/**
	 * 根据角色列表获取根菜单列表
	 * @param sysRoleList
	 * @return
	 * @throws ServiceException
	 */
	public List<SysMenu> findRootSysMenuListByRoles(List<SysRole> sysRoleList) throws ServiceException;

	/**
	 * 根据角色列表获取所有子菜单列表
	 * @param sysRoleList
	 * @return
	 * @throws ServiceException
	 */
	public List<SysMenu> findChildSysMenuListByRoles(List<SysRole> sysRoleList) throws ServiceException;

	/**
	 * 根据菜单ID获取菜单信息
	 * @param menuId 菜单ID
	 * @return
	 * @throws ServiceException
	 */
	public SysMenu findSysMenuById(String menuId) throws ServiceException;

	/**
	 * 根据父菜单的id，查找子菜单
	 * @param parentModuleId 父菜单
	 */
	public List<SysMenu> findAllSysMenuByParentId(String parentId) throws ServiceException;


	/**
	 * 根据角色ID获取角色菜单对应关系列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRoleMenu> findSysRoleMenuByRoleId(String roleId) throws ServiceException;
}
