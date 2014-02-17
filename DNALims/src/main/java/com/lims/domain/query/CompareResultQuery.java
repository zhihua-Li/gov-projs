/**
 * CompareResultQuery.java
 *
 * 2013-7-13
 */
package com.lims.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.lims.domain.po.CompareResult;

/**
 * @author lizhihua
 *
 */
public class CompareResultQuery extends CompareResult implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String sampleNo;

	private String instoreUser;

	private Date instoreDateStart;

	private Date instoreDateEnd;

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public String getInstoreUser() {
		return instoreUser;
	}

	public void setInstoreUser(String instoreUser) {
		this.instoreUser = instoreUser;
	}

	public Date getInstoreDateStart() {
		return instoreDateStart;
	}

	public void setInstoreDateStart(Date instoreDateStart) {
		this.instoreDateStart = instoreDateStart;
	}

	public Date getInstoreDateEnd() {
		return instoreDateEnd;
	}

	public void setInstoreDateEnd(Date instoreDateEnd) {
		this.instoreDateEnd = instoreDateEnd;
	}

}
