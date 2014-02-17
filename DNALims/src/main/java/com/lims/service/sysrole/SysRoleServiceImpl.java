/**
 * SysRoleServiceImpl.java
 *
 * 2013-5-26
 */
package com.lims.service.sysrole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysRoleMenu;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 * 角色service
 */
@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl extends LimsBaseServiceImpl implements
		SysRoleService {

	/**
	 * 获取指定用户的角色列表
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findSysRoleBySysUserId(String userId) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysRoleMapper.findSysRoleByUserId", userId);
		} catch(Exception e) {
			log.error("invoke SysRoleService.findSysRoleBySysUserId error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取所有角色列表
	 *
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findAllSysRoles() throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysRoleMapper.findAllSysRoles");
		} catch(Exception e) {
			log.error("invoke SysRoleService.findAllSysRoles error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取角色列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SysRole> findSysRoleList(SysRole sysRole) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("SysRoleMapper.findSysRole", sysRole);
		} catch(Exception e) {
			log.error("invoke SysRoleService.findSysRoleList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取角色个数
	 * @return
	 * @throws ServiceException
	 */
	public int findSysRoleListCount(SysRole sysRole) throws ServiceException {
		try {
			return (Integer) (dao.getSqlSession().selectOne("SysRoleMapper.findSysRoleCount", sysRole));
		} catch(Exception e){
			log.error("invoke SysRoleService.findSysRoleListCount error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public SysRole findSysRoleById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("SysRoleMapper.findSysRoleById", id);
		} catch(Exception e) {
			log.error("invoke SysRoleService.findSysRoleById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 新增角色
	 * @param sysRole
	 * @param sysMenuIdList
	 * @throws ServiceException
	 */
	public void addSysRole(SysRole sysRole, List<String> sysMenuIdList) throws ServiceException {
		try {
			String id = keyGeneratorService.getNextKey();
			sysRole.setId(id);

			dao.getSqlSession().insert("SysRoleMapper.addSysRole", sysRole);

			List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>(sysMenuIdList.size());
			SysRoleMenu sur = null;
			for(String menuId : sysMenuIdList){
				sur = new SysRoleMenu();
				sur.setId(keyGeneratorService.getNextKey());
				sur.setMenuId(menuId);
				sur.setRoleId(id);

				sysRoleMenuList.add(sur);
			}

			dao.getSqlSession().insert("SysRoleMapper.addSysRoleMenu", sysRoleMenuList);
		} catch(Exception e) {
			log.error("invoke SysRoleService.addSysRole error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新角色
	 * @param sysRole
	 * @param sysMenuIdList
	 * @throws ServiceException
	 */
	public void updateSysRole(SysRole sysRole, List<String> sysMenuIdList) throws ServiceException {
		try {
			dao.getSqlSession().update("SysRoleMapper.updateSysRole", sysRole);

			dao.getSqlSession().delete("SysRoleMapper.deleteSysRoleMenuBySysRoleId", sysRole.getId());

			List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>(sysMenuIdList.size());
			SysRoleMenu sur = null;
			for(String menuId : sysMenuIdList){
				sur = new SysRoleMenu();
				sur.setId(keyGeneratorService.getNextKey());
				sur.setMenuId(menuId);
				sur.setRoleId(sysRole.getId());

				sysRoleMenuList.add(sur);
			}

			dao.getSqlSession().insert("SysRoleMapper.addSysRoleMenu", sysRoleMenuList);
		} catch(Exception e) {
			log.error("invoke SysRoleService.updateSysRole error!", e);
			throw new ServiceException(e);
		}
	}

}
