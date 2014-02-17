/**
 * ConsignmentView.java
 *
 * 2013-8-3
 */
package com.lims.domain.vo;

import com.lims.domain.po.Consignment;

/**
 * @author lizhihua
 *
 */
public class ConsignmentView extends Consignment {


    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String organizationName;

    protected String createUserName;

    protected int sampleCount;

    protected int acceptedSampleCount;


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public int getAcceptedSampleCount() {
		return acceptedSampleCount;
	}

	public void setAcceptedSampleCount(int acceptedSampleCount) {
		this.acceptedSampleCount = acceptedSampleCount;
	}

}
