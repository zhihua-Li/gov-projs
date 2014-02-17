package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QualityControlSample extends BaseDomain implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

    protected String fullName;

    protected String gender;

    protected String organizationId;

    protected String organizationSubName;

    protected String duty;

    protected String policeNo;

    protected String phonenum;

    protected String sampleNo;

    protected String idcardNo;

    protected String genotypeInfo;

    protected String otherGenotype;

    protected String remark;

    protected String createUser;
    protected Date createDate;
    protected String updateUser;
    protected Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }


    public String getGenotypeInfo() {
        return genotypeInfo;
    }


    public void setGenotypeInfo(String genotypeInfo) {
        this.genotypeInfo = genotypeInfo;
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

	public String getOtherGenotype() {
		return otherGenotype;
	}

	public void setOtherGenotype(String otherGenotype) {
		this.otherGenotype = otherGenotype;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationSubName() {
		return organizationSubName;
	}

	public void setOrganizationSubName(String organizationSubName) {
		this.organizationSubName = organizationSubName;
	}
}