package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GeneInfo extends BaseDomain implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String SAMPLE_TYPE_NORMAL = "1";
	public static final String SAMPLE_TYPE_CODIS = "2";

	protected String geneInfoHtmlForPage;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.SAMPLE_ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String sampleId;

    protected String sampleType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.GENE_TYPE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String geneType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.REAGENT_KIT
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String reagentKit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.GENOTYPE_INFO
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String genotypeInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.STORE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected Date storeDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.STORE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String storeUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.MATCH_STATUS
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String matchStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.UPDATE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.UPDATE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column GENE_INFO.REMARK
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    protected String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.ID
     *
     * @return the value of GENE_INFO.ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.ID
     *
     * @param id the value for GENE_INFO.ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.SAMPLE_ID
     *
     * @return the value of GENE_INFO.SAMPLE_ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getSampleId() {
        return sampleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.SAMPLE_ID
     *
     * @param sampleId the value for GENE_INFO.SAMPLE_ID
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.GENE_TYPE
     *
     * @return the value of GENE_INFO.GENE_TYPE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getGeneType() {
        return geneType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.GENE_TYPE
     *
     * @param geneType the value for GENE_INFO.GENE_TYPE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.REAGENT_KIT
     *
     * @return the value of GENE_INFO.REAGENT_KIT
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getReagentKit() {
        return reagentKit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.REAGENT_KIT
     *
     * @param reagentKit the value for GENE_INFO.REAGENT_KIT
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setReagentKit(String reagentKit) {
        this.reagentKit = reagentKit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.GENOTYPE_INFO
     *
     * @return the value of GENE_INFO.GENOTYPE_INFO
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getGenotypeInfo() {
        return genotypeInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.GENOTYPE_INFO
     *
     * @param genotypeInfo the value for GENE_INFO.GENOTYPE_INFO
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setGenotypeInfo(String genotypeInfo) {
        this.genotypeInfo = genotypeInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.STORE_DATE
     *
     * @return the value of GENE_INFO.STORE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public Date getStoreDate() {
        return storeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.STORE_DATE
     *
     * @param storeDate the value for GENE_INFO.STORE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setStoreDate(Date storeDate) {
        this.storeDate = storeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.STORE_USER
     *
     * @return the value of GENE_INFO.STORE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getStoreUser() {
        return storeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.STORE_USER
     *
     * @param storeUser the value for GENE_INFO.STORE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setStoreUser(String storeUser) {
        this.storeUser = storeUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.MATCH_STATUS
     *
     * @return the value of GENE_INFO.MATCH_STATUS
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getMatchStatus() {
        return matchStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.MATCH_STATUS
     *
     * @param matchStatus the value for GENE_INFO.MATCH_STATUS
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.UPDATE_DATE
     *
     * @return the value of GENE_INFO.UPDATE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.UPDATE_DATE
     *
     * @param updateDate the value for GENE_INFO.UPDATE_DATE
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.UPDATE_USER
     *
     * @return the value of GENE_INFO.UPDATE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.UPDATE_USER
     *
     * @param updateUser the value for GENE_INFO.UPDATE_USER
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GENE_INFO.REMARK
     *
     * @return the value of GENE_INFO.REMARK
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GENE_INFO.REMARK
     *
     * @param remark the value for GENE_INFO.REMARK
     *
     * @mbggenerated Thu May 30 19:38:21 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getGeneInfoHtmlForPage() {
		return geneInfoHtmlForPage;
	}

	public void setGeneInfoHtmlForPage(String geneInfoHtmlForPage) {
		this.geneInfoHtmlForPage = geneInfoHtmlForPage;
	}
}