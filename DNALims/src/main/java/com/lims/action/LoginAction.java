/**
 * LoginAction.java
 *
 * 2013-5-25
 */
package com.lims.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lims.domain.po.DictItem;
import com.lims.domain.po.SysRole;
import com.lims.domain.po.SysUser;
import com.lims.exception.ServiceException;
import com.lims.service.dict.SysDictService;
import com.lims.service.sysrole.SysRoleService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.Md5Util;
import com.lims.util.crypt.SimpleDateCrypt;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author lizhihua
 *
 */
public class LoginAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SysUser sysUser;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysDictService sysDictService;

	private Map<String, Object> jsonData;

	protected final Log log = LogFactory.getLog(getClass());

	private final static String LOGIN_ERROR = "loginError";


	/**
	 * 进入登录页面
	 * @return
	 */
	public String intoLogin() throws Exception {
		List<DictItem> dictItemList = sysDictService.findDictItemListByDictInfoType(EXPIRE_DATE_KEY);
		DictItem di = null;
		if(!ListUtil.isEmptyList(dictItemList)){
			di = dictItemList.get(0);
		}

		String currentDate = DateUtil.currentDateStr("yyyy-MM-dd");
		String cryptedDate = di.getDictValue();
		String expireDate = SimpleDateCrypt.decrypt(cryptedDate);	//对取得的日期解密
		expiredDayCount = DateUtil.subDay(expireDate, currentDate);
		if((expiredDayCount == FIRST_WARN_DAYS
				|| expiredDayCount == SECOND_WARN_DAYS
				|| expiredDayCount <= LAST_WARN_DAYS )
				&& expiredDayCount > 0){
			expireWarnMsg = "本系统试用期还有 " + expiredDayCount + " 天！";
		}else if(expiredDayCount <= 0){
			expireWarnMsg = "本系统试用期已结束，将暂时无法使用本系统！";
		}

		if(!StringUtils.isEmpty(getErrorMessage())){
			setErrorMessage(URLDecoder.decode(getErrorMessage(), "utf-8"));
		}

		return SUCCESS;
	}

	/**
	 * 提交登录
	 * @return
	 */
	public String login() throws Exception {
		SysUser loginSysUser = null;

		try {

			/**
			 * 获取角色列表
			 */
			List<SysRole> sysRoleList = null;

			if(ADMINISTRATOR.equals(sysUser.getUserName())){
				loginSysUser = sysUserService.findSysUserByUserNameAndPassword(
						ADMINISTRATOR, ADMINISTRATOR_PSD);

				sysRoleList = sysRoleService.findAllSysRoles();
			}else{
				/**
				 * 检查登录用户
				 */
				loginSysUser = sysUserService.findSysUserByUserNameAndPassword(
											sysUser.getUserName(), sysUser.getUserPassword());

				if(loginSysUser == null){
					errorMessage = "用户名或密码错误！";

					return LOGIN_ERROR;
				}

				if(SysUser.ACTIVE_FLAG_FALSE.equals(loginSysUser.getActiveFlag())){
					errorMessage = "该账户已禁用！";

					return LOGIN_ERROR;
				}

				sysRoleList = sysRoleService.findSysRoleBySysUserId(loginSysUser.getId());
			}
			loginSysUser.setSysRoleList(sysRoleList);

			boolean isSuperRole = false;
			boolean hasDeleteRole = false;
			for(SysRole sr : sysRoleList){
				if(SysRole.ROLE_TYPE_SUPER_MANAGER.equals(sr.getRoleType())){
					isSuperRole = true;
				}

				if(SysRole.ROLE_TYPE_DELETE_ROLE.equals(sr.getRoleType())){
					hasDeleteRole = true;
				}
			}

			/**
			 * 将当前用户放入session
			 */
			super.doPutSessionObject(SESSION_CURRENT_USER, loginSysUser);
			super.doPutSessionObject(IS_SUPER_USER_ROLE, isSuperRole);
			super.doPutSessionObject(HAS_DELETE_ROLE, hasDeleteRole);
		} catch(ServiceException se){
			throw se;
		}

		log.info("[用户登录] -- 登录人：" + loginSysUser.getFullName());

		return SUCCESS;
	}


	public String logout() throws Exception {
		if(getServletRequest().isRequestedSessionIdValid()){
			getSessionMap().remove(SESSION_CURRENT_USER);
			ActionContext.getContext().getSession().clear();
		}

		return SUCCESS;
	}

	public String submitUpdatePassword() throws Exception {
		SysUser oldUser = (SysUser) super.doGetSessionObject(SESSION_CURRENT_USER);
		jsonData = new HashMap<String, Object>();

		if(!oldUser.getUserPassword().equals(Md5Util.GetMd5(sysUser.getUserPassword().trim()))){
			jsonData.put("success", false);
			jsonData.put("oldPasswordError", true);
		}else{
			sysUser.setNewPassword(Md5Util.GetMd5(sysUser.getNewPassword()));
			sysUserService.updateUserPassword(sysUser);

			jsonData.put("success", true);
		}

		return SUCCESS;
	}



	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
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

	public SysDictService getSysDictService() {
		return sysDictService;
	}

	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

}
