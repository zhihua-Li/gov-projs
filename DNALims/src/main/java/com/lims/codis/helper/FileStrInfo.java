/**
 * FileStrInfo.java
 *
 * 2013-7-8
 */
package com.lims.codis.helper;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizhihua
 *
 */
public class FileStrInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String sampleNo;

	private String message;

	private List<LocusValues> locusValuesList;

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<LocusValues> getLocusValuesList() {
		return locusValuesList;
	}

	public void setLocusValuesList(List<LocusValues> locusValuesList) {
		this.locusValuesList = locusValuesList;
	}

}
