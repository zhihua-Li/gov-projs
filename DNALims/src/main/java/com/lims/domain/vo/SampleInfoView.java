/**
 * SampleInfoView.java
 *
 * 2013-7-5
 */
package com.lims.domain.vo;

import java.io.Serializable;
import java.util.Date;

import com.lims.domain.po.SampleInfo;

/**
 * @author lizhihua
 *
 */
public class SampleInfoView extends SampleInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String samplePosition;

	private String consignmentNo;

	private String boardId;

	private String boardNo;

	private String organizationId;

	private String organizationName;

	private String organizationSubName;

	private Date importDate;

	private String importUser;
	private String importUserName;

	private String createUserName;

	private String acceptUserName;

	private String examineTypeName;


	public String getSamplePosition() {
		return samplePosition;
	}

	public void setSamplePosition(String samplePosition) {
		this.samplePosition = samplePosition;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationSubName() {
		return organizationSubName;
	}

	public void setOrganizationSubName(String organizationSubName) {
		this.organizationSubName = organizationSubName;
	}

	public String getImportUserName() {
		return importUserName;
	}

	public void setImportUserName(String importUserName) {
		this.importUserName = importUserName;
	}

	public String getImportUser() {
		return importUser;
	}

	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getExamineTypeName() {
		return examineTypeName;
	}

	public void setExamineTypeName(String examineTypeName) {
		this.examineTypeName = examineTypeName;
	}

}
