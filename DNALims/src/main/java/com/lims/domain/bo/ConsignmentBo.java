/**
 * ConsignmentBo.java
 *
 * 2013-7-11
 */
package com.lims.domain.bo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lims.domain.po.BoardSampleMap;
import com.lims.domain.po.Consignment;
import com.lims.domain.po.DictItem;

/**
 * @author lizhihua
 *
 */
public class ConsignmentBo extends Consignment implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String pcrSystem;

	protected String holeDiameter;

	protected String holeDiameterOther;

	protected List<DictItem> pcrSystemList;

	protected List<DictItem> sampleCarrierList;

	protected List<DictItem> holeDiameterList;

	protected String loopCount;

	protected List<DictItem> loopCountList;

	protected List<DictItem> examineInstrumentList;

	protected String boardNo;


	protected String organizationName;

	//取样人
	protected String extractor;

	protected String extractorDate;

	protected String pcrUser;

	protected String pcrDate;

	protected String pcrInstrument;

	protected String examineUser;

	protected String examineDate;

	protected String examineInstrument;

	protected String analysisDate;

	protected String analysisUser;

	protected String reviewUser;

	protected String reviewDate;

	protected String reagentNo;

	protected int sampleCountOfBoard;

	protected List<BoardSampleMap> boardSampleMapList;

	public String getPcrSystem() {
		return pcrSystem;
	}

	public void setPcrSystem(String pcrSystem) {
		this.pcrSystem = pcrSystem;
	}

	public String getHoleDiameter() {
		return holeDiameter;
	}

	public void setHoleDiameter(String holeDiameter) {
		this.holeDiameter = holeDiameter;
	}

	public String getHoleDiameterOther() {
		return holeDiameterOther;
	}

	public void setHoleDiameterOther(String holeDiameterOther) {
		this.holeDiameterOther = holeDiameterOther;
	}

	public List<DictItem> getPcrSystemList() {
		return pcrSystemList;
	}

	public void setPcrSystemList(List<DictItem> pcrSystemList) {
		this.pcrSystemList = pcrSystemList;
	}

	public List<DictItem> getSampleCarrierList() {
		return sampleCarrierList;
	}

	public void setSampleCarrierList(List<DictItem> sampleCarrierList) {
		this.sampleCarrierList = sampleCarrierList;
	}

	public List<DictItem> getHoleDiameterList() {
		return holeDiameterList;
	}

	public void setHoleDiameterList(List<DictItem> holeDiameterList) {
		this.holeDiameterList = holeDiameterList;
	}

	public String getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(String loopCount) {
		this.loopCount = loopCount;
	}

	public List<DictItem> getLoopCountList() {
		return loopCountList;
	}

	public void setLoopCountList(List<DictItem> loopCountList) {
		this.loopCountList = loopCountList;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getExtractor() {
		return extractor;
	}

	public void setExtractor(String extractor) {
		this.extractor = extractor;
	}

	public String getExtractorDate() {
		return extractorDate;
	}

	public void setExtractorDate(String extractorDate) {
		this.extractorDate = extractorDate;
	}

	public String getPcrUser() {
		return pcrUser;
	}

	public void setPcrUser(String pcrUser) {
		this.pcrUser = pcrUser;
	}

	public String getPcrDate() {
		return pcrDate;
	}

	public void setPcrDate(String pcrDate) {
		this.pcrDate = pcrDate;
	}

	public String getPcrInstrument() {
		return pcrInstrument;
	}

	public void setPcrInstrument(String pcrInstrument) {
		this.pcrInstrument = pcrInstrument;
	}

	public String getExamineUser() {
		return examineUser;
	}

	public void setExamineUser(String examineUser) {
		this.examineUser = examineUser;
	}

	public String getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(String examineDate) {
		this.examineDate = examineDate;
	}

	public String getExamineInstrument() {
		return examineInstrument;
	}

	public void setExamineInstrument(String examineInstrument) {
		this.examineInstrument = examineInstrument;
	}

	public String getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(String analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getAnalysisUser() {
		return analysisUser;
	}

	public void setAnalysisUser(String analysisUser) {
		this.analysisUser = analysisUser;
	}

	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReagentNo() {
		return reagentNo;
	}

	public void setReagentNo(String reagentNo) {
		this.reagentNo = reagentNo;
	}

	public List<BoardSampleMap> getBoardSampleMapList() {
		return boardSampleMapList;
	}

	public void setBoardSampleMapList(List<BoardSampleMap> boardSampleMapList) {
		this.boardSampleMapList = boardSampleMapList;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public List<DictItem> getExamineInstrumentList() {
		return examineInstrumentList;
	}

	public void setExamineInstrumentList(List<DictItem> examineInstrumentList) {
		this.examineInstrumentList = examineInstrumentList;
	}

	public int getSampleCountOfBoard() {
		return sampleCountOfBoard;
	}

	public void setSampleCountOfBoard(int sampleCountOfBoard) {
		this.sampleCountOfBoard = sampleCountOfBoard;
	}

}
