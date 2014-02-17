/**
 * SampleInfoQuery.java
 *
 * 2013-6-18
 */
package com.lims.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.lims.domain.po.SampleInfo;

/**
 * @author lizhihua
 * 样本查询实体
 *
 */
public class SampleInfoQuery extends SampleInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String boardId;

	private String createUserName;

	private Date acceptDateStart;

	private Date acceptDateEnd;

	private String consignmentNo;


    protected String fuzzyFlag;

	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public String getBoardId() {
		return boardId;
	}


	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}


	public Date getAcceptDateStart() {
		return acceptDateStart;
	}


	public void setAcceptDateStart(Date acceptDateStart) {
		this.acceptDateStart = acceptDateStart;
	}


	public Date getAcceptDateEnd() {
		return acceptDateEnd;
	}


	public void setAcceptDateEnd(Date acceptDateEnd) {
		this.acceptDateEnd = acceptDateEnd;
	}


	public String getConsignmentNo() {
		return consignmentNo;
	}


	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}


	public String getFuzzyFlag() {
		return fuzzyFlag;
	}


	public void setFuzzyFlag(String fuzzyFlag) {
		this.fuzzyFlag = fuzzyFlag;
	}


}
