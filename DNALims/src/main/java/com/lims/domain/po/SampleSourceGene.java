/**
 * SampleSourceGeneService.java
 *
 * 2013-7-9
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 * 样本原始基因信息
 */
public class SampleSourceGene extends BaseDomain implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String sampleId;

	protected String boardId;

	protected String geneType;

	protected String reagentKitId;

	protected String genotypeInfo;

	protected String reviewStatus;

	protected String reviewDesc;

	protected Date reviewDate;

	protected String reviewUser;

	protected String alignDbFlag;

	protected Date createDate;

	protected String createUser;

	protected Date updateDate;

	protected String updateUser;

	protected String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getGeneType() {
		return geneType;
	}

	public void setGeneType(String geneType) {
		this.geneType = geneType;
	}

	public String getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(String reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

	public String getGenotypeInfo() {
		return genotypeInfo;
	}

	public void setGenotypeInfo(String genotypeInfo) {
		this.genotypeInfo = genotypeInfo;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	public String getAlignDbFlag() {
		return alignDbFlag;
	}

	public void setAlignDbFlag(String alignDbFlag) {
		this.alignDbFlag = alignDbFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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

	public String getReviewDesc() {
		return reviewDesc;
	}

	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
	}
}
