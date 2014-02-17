/**
 * ExamineRecord.java
 *
 * 2014-1-9
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class ExamineRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String boardId;

	protected String examineUserId;

	protected Date examineDate;

	protected String examineInstrumentNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getExamineUserId() {
		return examineUserId;
	}

	public void setExamineUserId(String examineUserId) {
		this.examineUserId = examineUserId;
	}

	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	public String getExamineInstrumentNo() {
		return examineInstrumentNo;
	}

	public void setExamineInstrumentNo(String examineInstrumentNo) {
		this.examineInstrumentNo = examineInstrumentNo;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
