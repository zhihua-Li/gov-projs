/**
 * GeneInfoView.java
 *
 * 2013-7-14
 */
package com.lims.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.lims.domain.po.GeneInfo;

/**
 * @author lizhihua
 *
 */
public class GeneInfoView extends GeneInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String sampleNo;

	protected String reagentKitName;

	protected List<String> genotypeInfoList;

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public List<String> getGenotypeInfoList() {
		return genotypeInfoList;
	}

	public void setGenotypeInfoList(List<String> genotypeInfoList) {
		this.genotypeInfoList = genotypeInfoList;
	}

}
