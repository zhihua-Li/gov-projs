/**
 * QualityControlSampleView.java
 *
 * 2013-7-12
 */
package com.lims.domain.vo;

import com.lims.domain.po.QualityControlSample;

/**
 * @author lizhihua
 *
 */
public class QualityControlSampleView extends QualityControlSample {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	protected String genderName;

	protected String organizationName;

	protected String geneInfoHtmlForPage;

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getGeneInfoHtmlForPage() {
		return geneInfoHtmlForPage;
	}

	public void setGeneInfoHtmlForPage(String geneInfoHtmlForPage) {
		this.geneInfoHtmlForPage = geneInfoHtmlForPage;
	}
}
