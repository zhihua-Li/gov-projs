/**
 * CompareQueue.java
 *
 * 2013-7-13
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lizhihua
 *
 */
public class CompareQueue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public static final String TASK_STATUS_WAITING = "0";
	public static final String TASK_STATUS_RUNNINGG = "1";
	public static final String TASK_STATUS_SUCCESS = "2";
	public static final String TASK_STATUS_ERROR = "3";


	private String id;

	private String geneId;

	private String sampleId;

	private String genetypeInfo;

	private String taskStatus;

	private Date createDate;

	private String createUser;

	private Date updateDate;

	private String updateUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getGenetypeInfo() {
		return genetypeInfo;
	}

	public void setGenetypeInfo(String genetypeInfo) {
		this.genetypeInfo = genetypeInfo;
	}



}
