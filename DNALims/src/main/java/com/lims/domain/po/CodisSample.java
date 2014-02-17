/**
 * CodisSample.java
 *
 * 2013-10-8
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class CodisSample extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String sampleNo;
	private String consignOrganizationId;
	private String consignOrganizationName;
	private String boardInfoNo;
	private String reagentKit;
	private String reagentKitName;
	private Date importDate;
	private String importUser;
	private String importUserName;
	private String remark;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public String getConsignOrganizationId() {
		return consignOrganizationId;
	}
	public void setConsignOrganizationId(String consignOrganizationId) {
		this.consignOrganizationId = consignOrganizationId;
	}
	public String getBoardInfoNo() {
		return boardInfoNo;
	}
	public void setBoardInfoNo(String boardInfoNo) {
		this.boardInfoNo = boardInfoNo;
	}
	public String getReagentKit() {
		return reagentKit;
	}
	public void setReagentKit(String reagentKit) {
		this.reagentKit = reagentKit;
	}
	public String getReagentKitName() {
		return reagentKitName;
	}
	public void setReagentKitName(String reagentKitName) {
		this.reagentKitName = reagentKitName;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public String getImportUser() {
		return importUser;
	}
	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}
	public String getImportUserName() {
		return importUserName;
	}
	public void setImportUserName(String importUserName) {
		this.importUserName = importUserName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	public String getConsignOrganizationName() {
		return consignOrganizationName;
	}
	public void setConsignOrganizationName(String consignOrganizationName) {
		this.consignOrganizationName = consignOrganizationName;
	}
}
