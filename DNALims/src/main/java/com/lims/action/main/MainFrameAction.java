/**
 * MainFrameAction.java
 *
 * 2013-5-26
 */
package com.lims.action.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.SysMenu;
import com.lims.domain.po.SysUser;
import com.lims.exception.ServiceException;
import com.lims.service.sysmenu.SysMenuService;

/**
 * @author lizhihua
 *
 */
public class MainFrameAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String menuId;						/**		菜单ID	*/

	private SysMenu defaultSysMenu;

	private List<SysMenu> childSysMenuList;				/**		子菜单列表	*/
	private List<SysMenu> rootSysMenuList;				/**		根菜单列表	*/

	@Resource
	private SysMenuService sysMenuService;

	/**
	 * 进入首页头部	index.jsp
	 * @return
	 * @throws Exception
	 */
	public String intoMainFrame() throws Exception {
		try {
			//获取当前登录用户
			SysUser sysUser = (SysUser) doGetSessionObject(BaseAction.SESSION_CURRENT_USER);
			rootSysMenuList = sysMenuService.findRootSysMenuListByRoles(sysUser.getSysRoleList());
			defaultSysMenu = rootSysMenuList.get(0);
		} catch(ServiceException se) {
			throw se;
		}

		return SUCCESS;
	}


	/**
	 * 进入首页主体页面	mainFrameContent.jsp
	 * @return
	 * @throws Exception
	 */
	public String intoFrameContent() throws Exception {

		SysMenu parentMenu = sysMenuService.findSysMenuById(menuId);
		if(parentMenu == null){
			throw new Exception("初始化用户页面失败，原因：找不到父菜单！");
		}

		//获取当前登录用户
		SysUser sysUser = (SysUser) doGetSessionObject(BaseAction.SESSION_CURRENT_USER);
		//获取所有授权子菜单
		List<SysMenu> tempSysMenuList = sysMenuService.findChildSysMenuListByRoles(sysUser.getSysRoleList());

		childSysMenuList = new ArrayList<SysMenu>();
		for(SysMenu sm : tempSysMenuList){
			if(sm.getParentMenuId().equals(parentMenu.getId())){
				childSysMenuList.add(sm);
			}
		}

		defaultSysMenu = childSysMenuList.get(0);

		return SUCCESS;
	}

	public SysMenuService getSysMenuService() {
		return sysMenuService;
	}


	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}


	public List<SysMenu> getRootSysMenuList() {
		return rootSysMenuList;
	}


	public void setRootSysMenuList(List<SysMenu> rootSysMenuList) {
		this.rootSysMenuList = rootSysMenuList;
	}


	public SysMenu getDefaultSysMenu() {
		return defaultSysMenu;
	}


	public void setDefaultSysMenu(SysMenu defaultSysMenu) {
		this.defaultSysMenu = defaultSysMenu;
	}


	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public List<SysMenu> getChildSysMenuList() {
		return childSysMenuList;
	}


	public void setChildSysMenuList(List<SysMenu> childSysMenuList) {
		this.childSysMenuList = childSysMenuList;
	}
}
