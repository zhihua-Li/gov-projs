/**
 * SysUserRole.java
 *
 * 2013-5-24
 */
package com.lims.domain.po;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class SysUserRole extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String userId;

	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
