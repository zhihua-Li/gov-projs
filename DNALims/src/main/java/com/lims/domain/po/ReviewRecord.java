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
public class ReviewRecord implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String boardId;

	protected String reviewUserId;

	protected Date reviewDate;

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


	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
}
