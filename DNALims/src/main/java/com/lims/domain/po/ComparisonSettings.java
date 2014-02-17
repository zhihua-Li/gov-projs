/**
 * ComparisonSettings.java
 *
 * 2013-7-13
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class ComparisonSettings implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String sameLowerLimitNum;
	private String defaultSameLowerLimitNum;

	private String diffUpperLimitNum;
	private String defaultUpperLimitNum;

	private Date updateDate;
	private String updateUser;

	private String remark;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSameLowerLimitNum() {
		return sameLowerLimitNum;
	}
	public void setSameLowerLimitNum(String sameLowerLimitNum) {
		this.sameLowerLimitNum = sameLowerLimitNum;
	}
	public String getDefaultSameLowerLimitNum() {
		return defaultSameLowerLimitNum;
	}
	public void setDefaultSameLowerLimitNum(String defaultSameLowerLimitNum) {
		this.defaultSameLowerLimitNum = defaultSameLowerLimitNum;
	}
	public String getDiffUpperLimitNum() {
		return diffUpperLimitNum;
	}
	public void setDiffUpperLimitNum(String diffUpperLimitNum) {
		this.diffUpperLimitNum = diffUpperLimitNum;
	}
	public String getDefaultUpperLimitNum() {
		return defaultUpperLimitNum;
	}
	public void setDefaultUpperLimitNum(String defaultUpperLimitNum) {
		this.defaultUpperLimitNum = defaultUpperLimitNum;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
