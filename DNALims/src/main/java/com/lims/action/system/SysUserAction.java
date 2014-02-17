/**
 * SysUserAction.java
 *
 * 2013-5-29
 */
package com.lims.action.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysUser;
import com.lims.exception.ServiceException;
import com.lims.service.sysrole.SysRoleService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.StringUtil;

/**
 *
 * 用户管理Action
 * @author lizhihua
 *
 */
public class SysUserAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SysUser sysUser;

	private List<SysUser> sysUserList;

	private List<SysRole> sysRoleList;

	private List<String> roleIdList;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysRoleService sysRoleService;

	private Map<String, Object> jsonData;


	/**
	 * 查询系统用户列表
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(sysUser == null){
			sysUser = new SysUser();
		}else{
			trimQueryConditions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		sysUser.setStartRowNum((pageIndex-1)*pageSize + 1);
		sysUser.setEndRowNum(pageIndex*pageSize);

		recordCount = sysUserService.findSysUserListCount(sysUser);
		sysUserList = sysUserService.findSysUserList(sysUser);

		return SUCCESS;
	}

	/**
	 * 编辑系统用户
	 * @return
	 * @throws Exception
	 */
	public String editSysUser() throws Exception {
		if(this.operateType.equals(BaseAction.OPERATE_TYPE_UPDATE)) {
			sysUser = sysUserService.findSysUserById(sysUser.getId());

			List<SysRole> sysRoleList = sysRoleService.findSysRoleBySysUserId(sysUser.getId());
			roleIdList = new ArrayList<String>(sysRoleList.size());
			for(SysRole sr : sysRoleList) {
				roleIdList.add(sr.getId());
			}
//			sysUser.setSysRoleList(sysRoleList);
		}

		//获取所有角色列表
		sysRoleList = sysRoleService.findAllSysRoles();

		return SUCCESS;
	}

	/**
	 * 提交变更
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		SysUser currentSysUser = getLoginSysUser();
		boolean isSucceed = true;
		String errorMsg = "";

		if(currentSysUser == null){
			isSucceed = false;
			errorMsg = "登录超时，请重新登录！";
		}

		if(BaseAction.OPERATE_TYPE_ADD.equals(this.operateType)) {
			try {
				sysUser.setCreateUser(currentSysUser.getId());
				sysUser.setCreateDatetime(new Date());

				sysUserService.addSysUser(sysUser, roleIdList);
			} catch (ServiceException se) {
				isSucceed = false;
				errorMsg = "账户添加失败， 原因： 数据库异常！";
			}
		} else if(BaseAction.OPERATE_TYPE_UPDATE.equals(this.operateType)) {
			try {
				sysUser.setUpdateUser(currentSysUser.getId());
				sysUser.setUpdateDatetime(new Date());
				sysUserService.updateSysUser(sysUser, roleIdList);
			} catch (ServiceException se) {
				isSucceed = false;
				errorMsg = "账户更新失败， 原因： 数据库异常！";
			}
		}

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", isSucceed);
		jsonData.put("errorMsg", errorMsg);

		return SUCCESS;
	}

	/**
	 * 删除用户
	 * @return
	 * @throws Exception
	 */
	public String deleteSysUser() throws Exception {
		sysUserService.deleteSysUser(sysUser.getId());

		return SUCCESS;
	}


	public String intoUpdatePassword() throws Exception {
		return SUCCESS;
	}

	private void trimQueryConditions(){
		if(StringUtil.isNullOrEmpty(sysUser.getUserName())){
			sysUser.setUserName(null);
		}else{
			sysUser.setUserName(sysUser.getUserName().trim());
		}

		if(StringUtil.isNullOrEmpty(sysUser.getFullName())){
			sysUser.setFullName(null);
		}else{
			sysUser.setFullName(sysUser.getFullName().trim());
		}

		if(StringUtil.isNullOrEmpty(sysUser.getIdCardNo())){
			sysUser.setIdCardNo(null);
		}else{
			sysUser.setIdCardNo(sysUser.getIdCardNo().trim());
		}
	}

	public SysUser getSysUser() {
		return sysUser;
	}


	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}


	public List<SysUser> getSysUserList() {
		return sysUserList;
	}


	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
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

	public List<String> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public List<SysRole> getSysRoleList() {
		return sysRoleList;
	}

	public void setSysRoleList(List<SysRole> sysRoleList) {
		this.sysRoleList = sysRoleList;
	}
}
