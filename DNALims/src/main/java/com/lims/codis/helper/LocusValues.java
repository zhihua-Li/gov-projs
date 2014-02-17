/**
 * LocusValues.java
 *
 * 2013-7-10
 */
package com.lims.codis.helper;

import java.io.Serializable;

/**
 * @author lizhihua
 *
 */
public class LocusValues implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String locusName;

	private String alleleValue1;

	private String alleleValue2;

	private String alleleValue3;

	public String getLocusName() {
		return locusName;
	}

	public void setLocusName(String locusName) {
		this.locusName = locusName;
	}

	public String getAlleleValue1() {
		return alleleValue1;
	}

	public void setAlleleValue1(String alleleValue1) {
		this.alleleValue1 = alleleValue1;
	}

	public String getAlleleValue2() {
		return alleleValue2;
	}

	public void setAlleleValue2(String alleleValue2) {
		this.alleleValue2 = alleleValue2;
	}

	public String getAlleleValue3() {
		return alleleValue3;
	}

	public void setAlleleValue3(String alleleValue3) {
		this.alleleValue3 = alleleValue3;
	}

}
