/**
 * BaseAction.java
 *
 * 2013-5-25
 */
package com.lims.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.lims.domain.po.SysUser;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author lizhihua
 *
 * Action基类
 *
 */
public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware, ParameterAware {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	protected static final String SESSION_CURRENT_USER = "currentUser";
	protected static final String IS_SUPER_USER_ROLE = "isSuperUserRole";
	protected static final String HAS_DELETE_ROLE = "hasDeleteRole";

	protected static final String OPERATE_TYPE_ADD = "1";
	protected static final String OPERATE_TYPE_UPDATE = "2";
	protected static final String OPERATE_TYPE_DELETE = "3";
	protected static final String OPERATE_TYPE_VIEW = "4";


	protected static final String IE_BROWSER_XP_CHS_PATH = "/ie-browser/IE8-WindowsXP-x86-CHS.exe";
	protected static final String IE_BROWSER_VISTA_CHS_PATH = "/ie-browser/IE8-WindowsVista-x86-CHS.exe";

	protected static final String IE_BROWSER_XP_ENU_PATH = "/ie-browser/IE8-WindowsXP-x86-ENU.exe";
	protected static final String IE_BROWSER_VISTA_ENU_PATH = "/ie-browser/IE8-WindowsVista-x86-ENU.exe";

	protected static final String WINRAR_PATH = "/winrar/wrar501sc.exe";

	protected static final String FILEUPLOADER_PATH = "/fileuploader/FileUploader.rar";

	protected static final String DOWNLOAD_TMP_PATH = "D:\\download-temp";

	protected static int DEFAULT_PAGE_SIZE = 15;

	protected String errorMessage;

	private Map<String, Object> session;

	private Map<String, String[]> parameters;

	private HttpServletRequest request;

	private HttpServletResponse response;

	protected int pageIndex;		/**		页码		*/

	protected int recordCount;		/**		总条数		*/

	protected int totalPageCount;	/**		总页数		*/

	protected int pageSize;			/**		每页显示条数		*/

	protected String operateType;	/**		操作类型		*/


	protected String viewOnly;		/**	  只读标识	*/


	/**
	 * 系统试用期设置
	 */
	protected static final String EXPIRE_DATE_KEY = "EXPIRE_DATE";
	protected static final int FIRST_WARN_DAYS = 10;
	protected static final int SECOND_WARN_DAYS = 5;
	protected static final int LAST_WARN_DAYS = 3;

	protected String expireWarnMsg;

	protected int expiredDayCount;	//距离过期天数

	protected final static String ADMINISTRATOR = "administator";
	protected final static String ADMINISTRATOR_PSD = "123456";
	protected final static String ADMINISTRATOR_MD5_PSD = "Y10EFX3949SE59ESSY56Y057K20K883Y";


	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String[]> setParameters() {
		return this.parameters;
	}


	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSessionMap() {
		return this.session;
	}

	/**
     * 添加session对象
     * @param key
     * @param value
     */
	public void doPutSessionObject(String key, Object value) {
        this.getSessionMap().put(key, value);
    }
    /**
     * 获得sesion对象
     *
     * @param key 键
     * @return session的值
     */
    public Object doGetSessionObject(Object key) {
        return this.getSessionMap().get(key);
    }

    /**
     * 获得当前登录用户
     *
     * @return 当前登录用户
     */
    public SysUser getLoginSysUser() {
        return (SysUser) this.getSessionMap().get(SESSION_CURRENT_USER);
    }

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(String viewOnly) {
		this.viewOnly = viewOnly;
	}

	public String getExpireWarnMsg() {
		return expireWarnMsg;
	}

	public void setExpireWarnMsg(String expireWarnMsg) {
		this.expireWarnMsg = expireWarnMsg;
	}

	public int getExpiredDayCount() {
		return expiredDayCount;
	}

	public void setExpiredDayCount(int expiredDayCount) {
		this.expiredDayCount = expiredDayCount;
	}

}
