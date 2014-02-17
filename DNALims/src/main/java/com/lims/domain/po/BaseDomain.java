/**
 * BaseDomain.java
 *
 * 2013-5-30
 */
package com.lims.domain.po;

/**
 * @author lizhihua
 *
 * 实体基类
 *
 */
public class BaseDomain {

	protected int startRowNum;

	protected int endRowNum;

	protected String sortColumn;

	protected String sortOrder;

	public int getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
