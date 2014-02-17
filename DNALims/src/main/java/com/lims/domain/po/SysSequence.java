/**
 * SysSequence.java
 *
 * 2013-6-16
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class SysSequence extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//流水号
	private String sequenceNo;
	//类型
	private String sequenceType;
	//当前日期
	private String currentDate;

	//更改人
	private String updateUser;
    //修改时间
	private Date updateDate;

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public boolean equals(SysSequence s){
		return EqualsBuilder.reflectionEquals(this, s);
	}

	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(String sequenceType) {
		this.sequenceType = sequenceType;
	}
}
