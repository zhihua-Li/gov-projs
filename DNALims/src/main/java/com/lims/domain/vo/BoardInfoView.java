/**
 * BoardInfoView.java
 *
 * 2013-7-7
 */
package com.lims.domain.vo;

import java.util.Date;

import com.lims.domain.po.BoardInfo;

/**
 * @author lizhihua
 *
 */
public class BoardInfoView extends BoardInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String organizationId;
	private String organizationName;

	private String organizationSubName;

	private Date consignDate;

	private String examineStatusName;

	private String examineTypeName;

	private String reviewStatusName;

	private String pcrUserName;
	private String examineUserName;
	private String analysisUserName;
	private String examineReviewUserName;

	protected String examineInstrumentName;

	protected Date pcrDate;

	protected Date analysisDate;

	protected Date examineReviewDate;

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

	public Date getConsignDate() {
		return consignDate;
	}

	public void setConsignDate(Date consignDate) {
		this.consignDate = consignDate;
	}

	public String getExamineStatusName() {
		return examineStatusName;
	}

	public void setExamineStatusName(String examineStatusName) {
		this.examineStatusName = examineStatusName;
	}

	public String getExamineTypeName() {
		return examineTypeName;
	}

	public void setExamineTypeName(String examineTypeName) {
		this.examineTypeName = examineTypeName;
	}

	public String getReviewStatusName() {
		return reviewStatusName;
	}

	public void setReviewStatusName(String reviewStatusName) {
		this.reviewStatusName = reviewStatusName;
	}

	public String getExamineInstrumentName() {
		return examineInstrumentName;
	}

	public void setExamineInstrumentName(String examineInstrumentName) {
		this.examineInstrumentName = examineInstrumentName;
	}

	public String getExamineUserName() {
		return examineUserName;
	}

	public void setExamineUserName(String examineUserName) {
		this.examineUserName = examineUserName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getPcrDate() {
		return pcrDate;
	}

	public void setPcrDate(Date pcrDate) {
		this.pcrDate = pcrDate;
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}



	public String getPcrUserName() {
		return pcrUserName;
	}

	public void setPcrUserName(String pcrUserName) {
		this.pcrUserName = pcrUserName;
	}

	public String getAnalysisUserName() {
		return analysisUserName;
	}

	public void setAnalysisUserName(String analysisUserName) {
		this.analysisUserName = analysisUserName;
	}

	public String getExamineReviewUserName() {
		return examineReviewUserName;
	}

	public void setExamineReviewUserName(String examineReviewUserName) {
		this.examineReviewUserName = examineReviewUserName;
	}

	public Date getExamineReviewDate() {
		return examineReviewDate;
	}

	public void setExamineReviewDate(Date examineReviewDate) {
		this.examineReviewDate = examineReviewDate;
	}


}
