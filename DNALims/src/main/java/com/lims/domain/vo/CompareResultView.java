/**
 * CompareResultView.java
 *
 * 2013-7-13
 */
package com.lims.domain.vo;

import java.io.Serializable;
import java.util.Date;

import com.lims.domain.po.CompareResult;

/**
 * @author lizhihua
 *
 */
public class CompareResultView extends CompareResult implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    protected String sourceSampleNo;
    protected String sourceGeneInfo;
    protected String sourceConsignmentNo;
    protected String sourceConsignOrgName;
    protected String sourceSampleType;
    protected String sourceStoreUser;
    protected Date sourceStoreDate;

    protected String matchSampleNo;
    protected String matchGeneInfo;
    protected String matchConsignmentNo;
    protected String matchConsignOrgName;
    protected String matchSampleType;
    protected String matchStoreUser;
    protected Date matchStoreDate;

	public String getSourceSampleNo() {
		return sourceSampleNo;
	}

	public void setSourceSampleNo(String sourceSampleNo) {
		this.sourceSampleNo = sourceSampleNo;
	}

	public String getMatchSampleNo() {
		return matchSampleNo;
	}

	public void setMatchSampleNo(String matchSampleNo) {
		this.matchSampleNo = matchSampleNo;
	}

	public String getSourceGeneInfo() {
		return sourceGeneInfo;
	}

	public void setSourceGeneInfo(String sourceGeneInfo) {
		this.sourceGeneInfo = sourceGeneInfo;
	}

	public String getSourceConsignmentNo() {
		return sourceConsignmentNo;
	}

	public void setSourceConsignmentNo(String sourceConsignmentNo) {
		this.sourceConsignmentNo = sourceConsignmentNo;
	}

	public String getSourceConsignOrgName() {
		return sourceConsignOrgName;
	}

	public void setSourceConsignOrgName(String sourceConsignOrgName) {
		this.sourceConsignOrgName = sourceConsignOrgName;
	}

	public String getMatchGeneInfo() {
		return matchGeneInfo;
	}

	public void setMatchGeneInfo(String matchGeneInfo) {
		this.matchGeneInfo = matchGeneInfo;
	}

	public String getMatchConsignmentNo() {
		return matchConsignmentNo;
	}

	public void setMatchConsignmentNo(String matchConsignmentNo) {
		this.matchConsignmentNo = matchConsignmentNo;
	}

	public String getMatchConsignOrgName() {
		return matchConsignOrgName;
	}

	public void setMatchConsignOrgName(String matchConsignOrgName) {
		this.matchConsignOrgName = matchConsignOrgName;
	}

	public String getSourceSampleType() {
		return sourceSampleType;
	}

	public void setSourceSampleType(String sourceSampleType) {
		this.sourceSampleType = sourceSampleType;
	}

	public String getMatchSampleType() {
		return matchSampleType;
	}

	public void setMatchSampleType(String matchSampleType) {
		this.matchSampleType = matchSampleType;
	}

	public String getSourceStoreUser() {
		return sourceStoreUser;
	}

	public void setSourceStoreUser(String sourceStoreUser) {
		this.sourceStoreUser = sourceStoreUser;
	}

	public Date getSourceStoreDate() {
		return sourceStoreDate;
	}

	public void setSourceStoreDate(Date sourceStoreDate) {
		this.sourceStoreDate = sourceStoreDate;
	}

	public String getMatchStoreUser() {
		return matchStoreUser;
	}

	public void setMatchStoreUser(String matchStoreUser) {
		this.matchStoreUser = matchStoreUser;
	}

	public Date getMatchStoreDate() {
		return matchStoreDate;
	}

	public void setMatchStoreDate(Date matchStoreDate) {
		this.matchStoreDate = matchStoreDate;
	}

}
