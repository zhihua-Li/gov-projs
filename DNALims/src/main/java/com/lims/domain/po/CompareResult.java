package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CompareResult extends BaseDomain implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	protected String id;


    protected String sourceGeneId;


    protected String matchGeneId;


    protected String sourceSampleId;


    protected String matchSampleId;


    protected Date matchDate;


    protected int sameNum;


    protected int diffNum;


    protected String matchDesc;


    protected Date updateDate;


    protected String updateUser;


    protected String remark;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getSourceSampleId() {
        return sourceSampleId;
    }


    public void setSourceSampleId(String sourceSampleId) {
        this.sourceSampleId = sourceSampleId;
    }


    public String getMatchSampleId() {
        return matchSampleId;
    }


    public void setMatchSampleId(String matchSampleId) {
        this.matchSampleId = matchSampleId;
    }


    public Date getMatchDate() {
        return matchDate;
    }


    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }


    public int getSameNum() {
        return sameNum;
    }


    public void setSameNum(int sameNum) {
        this.sameNum = sameNum;
    }


    public int getDiffNum() {
        return diffNum;
    }


    public void setDiffNum(int diffNum) {
        this.diffNum = diffNum;
    }


    public String getMatchDesc() {
        return matchDesc;
    }


    public void setMatchDesc(String matchDesc) {
        this.matchDesc = matchDesc;
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

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


	public String getSourceGeneId() {
		return sourceGeneId;
	}


	public void setSourceGeneId(String sourceGeneId) {
		this.sourceGeneId = sourceGeneId;
	}


	public String getMatchGeneId() {
		return matchGeneId;
	}


	public void setMatchGeneId(String matchGeneId) {
		this.matchGeneId = matchGeneId;
	}
}