/**
 * SysMenuServiceImpl.java
 *
 * 2013-5-26
 */
package com.lims.service.sysmenu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.SysMenu;
import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysRoleMenu;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 * 菜单service
 *
 */
@Service("sysMenuService")
@Transactional
public class SysMenuServiceImpl extends LimsBaseServiceImpl implements
		SysMenuService {

	/**
	 * 根据角色列表获取根菜单列表
	 * @param sysRoleList
	 * @return
	 * @throws ServiceException
	 */
	public List<SysMenu> findRootSysMenuListByRoles(List<SysRole> sysRoleList) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysMenuMapper.findRootSysMenuListByRoleIds", sysRoleList);
		} catch(Exception e) {
			log.error("invoke SysMenuService.findRootSysMenuListByRoles error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据角色列表获取所有子菜单列表
	 * @param sysRoleList
	 * @return
	 * @throws ServiceException
	 */
	public List<SysMenu> findChildSysMenuListByRoles(List<SysRole> sysRoleList) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysMenuMapper.findChildSysMenuListByRoleIds", sysRoleList);
		} catch(Exception e) {
			log.error("invoke SysMenuService.findChildSysMenuListByRoles error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据菜单ID获取菜单信息
	 * @param menuId 菜单ID
	 * @return
	 * @throws ServiceException
	 */
	public SysMenu findSysMenuById(String menuId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SysMenuMapper.findSysMenuById", menuId);
		} catch(Exception e) {
			log.error("invoke SysMenuService.findSysMenuById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据父菜单的id，查找子菜单
	 * @param parentModuleId 父菜单
	 */
	public List<SysMenu> findAllSysMenuByParentId(String parentId) throws ServiceException {
		try {
			List<SysMenu> result = new ArrayList<SysMenu>();
			getSubModule(parentId,result);
			return result;
		} catch(Exception e) {
			log.error("invoke SysMenuService.findAllSysMenuByParentId error!", e);
			throw new ServiceException(e);
		}
	}

	private void getSubModule(String parentModuleId, List<SysMenu> moduleList) {
		List<SysMenu> subModuleList = this.dao.getSqlSession().selectList("SysMenuMapper.findSysMenuByParentId", parentModuleId);
		if(subModuleList == null || subModuleList.size() < 1) {
			return;
		} else {
			for(SysMenu sysModule : subModuleList) {
				moduleList.add(sysModule);
				List<SysMenu> nextSubModuleList = this.dao.getSqlSession().selectList("SysMenuMapper.findSysMenuByParentId", sysModule.getId());
				if(nextSubModuleList == null || nextSubModuleList.size() < 1) {
					continue;
				} else {
					getSubModule(sysModule.getId(),moduleList);
				}
			}
		}
	}

	/**
	 * 根据角色ID获取角色菜单对应关系列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRoleMenu> findSysRoleMenuByRoleId(String roleId) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysMenuMapper.findSysRoleMenuByRoleId", roleId);
		} catch(Exception e) {
			log.error("invoke SysMenuService.findSysRoleMenuByRoleId error!", e);
			throw new ServiceException(e);
		}
	}

}
