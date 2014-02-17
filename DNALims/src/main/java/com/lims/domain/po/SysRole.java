/**
 * SysRole.java
 *
 * 2013-05-24
 */
package com.lims.domain.po;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lizhihua
 *
 */
public class SysRole extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public final static String ROLE_TYPE_SUPER_MANAGER = "SUPER_MANAGER";
	public final static String ROLE_TYPE_DELETE_ROLE = "DELETE_MANAGE";

	private String id;

	private String roleType;

	private String roleName;

	private String roleDesc;

	protected String editableFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getEditableFlag() {
		return editableFlag;
	}

	public void setEditableFlag(String editableFlag) {
		this.editableFlag = editableFlag;
	}

}
