/**
 * ConsignmentQuery.java
 *
 * 2013-8-3
 */
package com.lims.domain.query;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lims.domain.po.Consignment;

/**
 * @author lizhihua
 *
 */
public class ConsignmentQuery extends Consignment {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Date consignDateStart;

	private Date consignDateEnd;

	private Date createDateStart;

	private Date createDateEnd;

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

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
