/**
 * SysRoleAction.java
 *
 * 2013-6-2
 */
package com.lims.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.SysMenu;
import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysRoleMenu;
import com.lims.service.sysmenu.SysMenuService;
import com.lims.service.sysrole.SysRoleService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 用户角色action
 *
 */
public class SysRoleAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SysRole sysRole;

	private List<SysRole> sysRoleList;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysMenuService sysMenuService;

	private Map<String, Object> jsonData;

	private List<Map<String, Object>> jsonSysMenuList;

	//页面勾选的菜单id列表
	private String sysMenuIdListStr;

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(sysRole == null){
			sysRole = new SysRole();
		}else{
			if(StringUtil.isNullOrEmpty(sysRole.getRoleName())){
				sysRole.setRoleName(null);
			}else{
				sysRole.setRoleName(sysRole.getRoleName().trim());
			}
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		sysRole.setStartRowNum((pageIndex-1)*pageSize + 1);
		sysRole.setEndRowNum(pageIndex*pageSize);

		recordCount = sysRoleService.findSysRoleListCount(sysRole);
		sysRoleList = sysRoleService.findSysRoleList(sysRole);

		return SUCCESS;
	}


	//进入新增页面
	public String add() throws Exception {
		operateType = OPERATE_TYPE_ADD;

		return SUCCESS;
	}


	//进入编辑页面
	public String edit() throws Exception {
		operateType = OPERATE_TYPE_UPDATE;

		sysRole = sysRoleService.findSysRoleById(sysRole.getId());

		return SUCCESS;
	}

	//删除角色
	public String delete() throws Exception {

		return SUCCESS;
	}

	//提交变更
	public String submit() throws Exception {
		if(OPERATE_TYPE_ADD.equals(operateType)){
			sysRoleService.addSysRole(sysRole, ListUtil.stringArrayToList(sysMenuIdListStr.split(",")));
		}else if(OPERATE_TYPE_UPDATE.equals(operateType)){
			sysRoleService.updateSysRole(sysRole, ListUtil.stringArrayToList(sysMenuIdListStr.split(",")));
		}

		return SUCCESS;
	}

	/**
	 * 获取菜单列表
	 * @return
	 */
	public String querySysMenus() throws Exception {
		List<SysMenu> sysMenuList = sysMenuService.findAllSysMenuByParentId(null);
		jsonSysMenuList = new ArrayList<Map<String, Object>>();
		if(OPERATE_TYPE_ADD.equals(operateType)){
			Map<String, Object> sysMenuObj = null;
			for(SysMenu sysMenu : sysMenuList){
				sysMenuObj = new HashMap<String, Object>();
				sysMenuObj.put("id", sysMenu.getId());
				sysMenuObj.put("pId", sysMenu.getParentMenuId());
				sysMenuObj.put("name", sysMenu.getMenuName());

//				if(sysMenu.getMenuLevel() != null && sysMenu.getMenuLevel().equals("0")) {
//					sysMenuObj.put("open", true);
//				}

				jsonSysMenuList.add(sysMenuObj);
			}
		}else if(OPERATE_TYPE_UPDATE.equals(operateType)){
			List<SysRoleMenu> existsSysRoleMenu = sysMenuService.findSysRoleMenuByRoleId(sysRole.getId());
			Map<String, Object> sysMenuObj = null;
			for(SysMenu sysMenu : sysMenuList){
				sysMenuObj = new HashMap<String, Object>();
				sysMenuObj.put("id", sysMenu.getId());
				sysMenuObj.put("pId", sysMenu.getParentMenuId());
				sysMenuObj.put("name", sysMenu.getMenuName());

//				if(sysMenu.getMenuLevel() != null && sysMenu.getMenuLevel().equals("0")) {
//					sysMenuObj.put("open", true);
//				}

				for(SysRoleMenu srm : existsSysRoleMenu){
					if(srm.getMenuId().equals(sysMenu.getId())){
						sysMenuObj.put("checked", true);
						break;
					}
				}

				jsonSysMenuList.add(sysMenuObj);
			}
		}

		return SUCCESS;
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public List<SysRole> getSysRoleList() {
		return sysRoleList;
	}

	public void setSysRoleList(List<SysRole> sysRoleList) {
		this.sysRoleList = sysRoleList;
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public SysMenuService getSysMenuService() {
		return sysMenuService;
	}


	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}


	public List<Map<String, Object>> getJsonSysMenuList() {
		return jsonSysMenuList;
	}


	public void setJsonSysMenuList(List<Map<String, Object>> jsonSysMenuList) {
		this.jsonSysMenuList = jsonSysMenuList;
	}


	public String getSysMenuIdListStr() {
		return sysMenuIdListStr;
	}


	public void setSysMenuIdListStr(String sysMenuIdListStr) {
		this.sysMenuIdListStr = sysMenuIdListStr;
	}
}
