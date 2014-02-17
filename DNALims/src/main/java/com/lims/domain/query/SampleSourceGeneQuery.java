/**
 * SampleSourceGeneQuery.java
 *
 * 2013-7-10
 */
package com.lims.domain.query;

import java.util.Date;

import com.lims.domain.po.SampleSourceGene;

/**
 * @author lizhihua
 *
 */
public class SampleSourceGeneQuery extends SampleSourceGene {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String sampleName;

	protected String sampleNo;

	protected String boardNo;

	protected String consignmentNo;

	protected String organizationId;

	protected Date consignDateStart;

	protected Date consignDateEnd;

	protected Date reviewDateStart;

	protected Date reviewDateEnd;

	protected String examineType;

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getConsignDateStart() {
		return consignDateStart;
	}

	public void setConsignDateStart(Date consignDateStart) {
		this.consignDateStart = consignDateStart;
	}

	public Date getConsignDateEnd() {
		return consignDateEnd;
	}

	public void setConsignDateEnd(Date consignDateEnd) {
		this.consignDateEnd = consignDateEnd;
	}

	public String getExamineType() {
		return examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public Date getReviewDateStart() {
		return reviewDateStart;
	}

	public void setReviewDateStart(Date reviewDateStart) {
		this.reviewDateStart = reviewDateStart;
	}

	public Date getReviewDateEnd() {
		return reviewDateEnd;
	}

	public void setReviewDateEnd(Date reviewDateEnd) {
		this.reviewDateEnd = reviewDateEnd;
	}
}
