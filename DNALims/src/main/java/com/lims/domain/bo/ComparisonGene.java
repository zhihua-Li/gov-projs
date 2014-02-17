/**
 * ComparisonGene.java
 *
 * 2014-1-11
 */
package com.lims.domain.bo;

/**
 * @author lizhihua
 *
 */
public class ComparisonGene {
	private String id;

	private String sampleId;

	private String genotypeInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenotypeInfo() {
		return genotypeInfo;
	}

	public void setGenotypeInfo(String genotypeInfo) {
		this.genotypeInfo = genotypeInfo;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
}
