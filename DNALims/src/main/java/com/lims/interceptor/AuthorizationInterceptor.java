/**
 * AuthorizationInterceptor.java
 *
 * 2013-5-25
 */
package com.lims.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.lims.action.BaseAction;
import com.lims.action.LoginAction;
import com.lims.action.system.HelpAction;
import com.lims.domain.po.SysUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author lizhihua
 *
 * 权限验证拦截器
 */
public class AuthorizationInterceptor implements Interceptor {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void destroy() {

	}


	@Override
	public void init() {

	}


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
        Action action = (Action) invocation.getAction();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires",0);

		if((action instanceof LoginAction)
				|| (action instanceof HelpAction)){
			//如果为登录动作，则不拦截
			result = invocation.invoke();
		} else {
			if(request.isRequestedSessionIdValid()){
				//验证是否登录
				SysUser sysUser = ((BaseAction) action).getLoginSysUser();
				if(sysUser == null){
					result = "login";
				}else{
					result = invocation.invoke();
				}
			}else{
				//session过期，返回登录页面
				result = "timeOut";
			}
		}

		return result;
	}

}
