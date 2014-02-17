/**
 * SampleSourceGeneView.java
 *
 * 2013-7-10
 */
package com.lims.domain.vo;

import java.util.Date;

import com.lims.domain.po.SampleSourceGene;

/**
 * @author lizhihua
 *
 */
public class SampleSourceGeneView extends SampleSourceGene {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String consignmentNo;

	protected Date consignDate;

	protected String organizationName;

	protected String organizationSubName;

	protected String examineType;

	protected String examineTypeName;

	protected String sampleName;

	protected String sampleNo;

	protected String boardNo;

	protected String importDate;

	protected String reviewStatusName;

	protected String reviewUserName;

	protected Date acceptDate;

	protected String acceptStatus;

	protected String acceptUserName;

	protected String geneInfoHtmlForPage;

	private String hasInstorGeneFlag;

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

	public String getExamineType() {
		return examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}

	public String getExamineTypeName() {
		return examineTypeName;
	}

	public void setExamineTypeName(String examineTypeName) {
		this.examineTypeName = examineTypeName;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getReviewStatusName() {
		return reviewStatusName;
	}

	public void setReviewStatusName(String reviewStatusName) {
		this.reviewStatusName = reviewStatusName;
	}

	public String getGeneInfoHtmlForPage() {
		return geneInfoHtmlForPage;
	}

	public void setGeneInfoHtmlForPage(String geneInfoHtmlForPage) {
		this.geneInfoHtmlForPage = geneInfoHtmlForPage;
	}

	public Date getConsignDate() {
		return consignDate;
	}

	public void setConsignDate(Date consignDate) {
		this.consignDate = consignDate;
	}

	public String getReviewUserName() {
		return reviewUserName;
	}

	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String isHasInstorGeneFlag() {
		return hasInstorGeneFlag;
	}

	public void setHasInstorGeneFlag(String hasInstorGeneFlag) {
		this.hasInstorGeneFlag = hasInstorGeneFlag;
	}

}
