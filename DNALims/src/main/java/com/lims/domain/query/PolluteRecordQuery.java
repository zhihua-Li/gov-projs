/**
 * PolluteRecordQuery.java
 *
 * 2013-7-14
 */
package com.lims.domain.query;

import java.io.Serializable;

import com.lims.domain.po.PolluteRecord;

/**
 * @author lizhihua
 *
 */
public class PolluteRecordQuery extends PolluteRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String qualitySampleName;

	protected String qualitySampleNo;

	protected String qualityGenotypeInfo;

	protected String matchSampleNo;

	protected String matchGenotypeInfo;

	public String getQualitySampleName() {
		return qualitySampleName;
	}

	public void setQualitySampleName(String qualitySampleName) {
		this.qualitySampleName = qualitySampleName;
	}

	public String getQualitySampleNo() {
		return qualitySampleNo;
	}

	public void setQualitySampleNo(String qualitySampleNo) {
		this.qualitySampleNo = qualitySampleNo;
	}

	public String getQualityGenotypeInfo() {
		return qualityGenotypeInfo;
	}

	public void setQualityGenotypeInfo(String qualityGenotypeInfo) {
		this.qualityGenotypeInfo = qualityGenotypeInfo;
	}

	public String getMatchSampleNo() {
		return matchSampleNo;
	}

	public void setMatchSampleNo(String matchSampleNo) {
		this.matchSampleNo = matchSampleNo;
	}

	public String getMatchGenotypeInfo() {
		return matchGenotypeInfo;
	}

	public void setMatchGenotypeInfo(String matchGenotypeInfo) {
		this.matchGenotypeInfo = matchGenotypeInfo;
	}
}
