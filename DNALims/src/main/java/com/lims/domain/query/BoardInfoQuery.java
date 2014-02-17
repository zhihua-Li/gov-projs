/**
 * BoardInfoQuery.java
 *
 * 2013-7-4
 */
package com.lims.domain.query;

import java.util.Date;

import com.lims.domain.po.BoardInfo;

/**
 * @author lizhihua
 *
 */
public class BoardInfoQuery extends BoardInfo {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected Date importDateStart;
    protected Date importDateEnd;

    protected String sampleNo;

    protected String organizationCode;

    protected String organizationId;

    protected Date consignDateStart;
    protected Date consignDateEnd;

    protected Date examineDateStart;
    protected Date examineDateEnd;

    protected Date pcrDateStart;
    protected Date pcrDateEnd;

    protected Date analysisDateStart;
    protected Date analysisDateEnd;

    protected Date examineReviewDateStart;
    protected Date examineReviewDateEnd;

    protected String fuzzyFlag;

	public Date getImportDateStart() {
		return importDateStart;
	}
	public void setImportDateStart(Date importDateStart) {
		this.importDateStart = importDateStart;
	}
	public Date getImportDateEnd() {
		return importDateEnd;
	}
	public void setImportDateEnd(Date importDateEnd) {
		this.importDateEnd = importDateEnd;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public Date getExamineDateStart() {
		return examineDateStart;
	}
	public void setExamineDateStart(Date examineDateStart) {
		this.examineDateStart = examineDateStart;
	}
	public Date getExamineDateEnd() {
		return examineDateEnd;
	}
	public void setExamineDateEnd(Date examineDateEnd) {
		this.examineDateEnd = examineDateEnd;
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
	public Date getPcrDateStart() {
		return pcrDateStart;
	}
	public void setPcrDateStart(Date pcrDateStart) {
		this.pcrDateStart = pcrDateStart;
	}
	public Date getPcrDateEnd() {
		return pcrDateEnd;
	}
	public void setPcrDateEnd(Date pcrDateEnd) {
		this.pcrDateEnd = pcrDateEnd;
	}
	public Date getAnalysisDateStart() {
		return analysisDateStart;
	}
	public void setAnalysisDateStart(Date analysisDateStart) {
		this.analysisDateStart = analysisDateStart;
	}
	public Date getAnalysisDateEnd() {
		return analysisDateEnd;
	}
	public void setAnalysisDateEnd(Date analysisDateEnd) {
		this.analysisDateEnd = analysisDateEnd;
	}
	public Date getExamineReviewDateStart() {
		return examineReviewDateStart;
	}
	public void setExamineReviewDateStart(Date examineReviewDateStart) {
		this.examineReviewDateStart = examineReviewDateStart;
	}
	public Date getExamineReviewDateEnd() {
		return examineReviewDateEnd;
	}
	public void setExamineReviewDateEnd(Date examineReviewDateEnd) {
		this.examineReviewDateEnd = examineReviewDateEnd;
	}
	public String getFuzzyFlag() {
		return fuzzyFlag;
	}
	public void setFuzzyFlag(String fuzzyFlag) {
		this.fuzzyFlag = fuzzyFlag;
	}


}
