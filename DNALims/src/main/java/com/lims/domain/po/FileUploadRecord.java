/**
 * FileUploadRecord.java
 *
 * 2013-9-22
 */
package com.lims.domain.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lizhihua
 *
 */
public class FileUploadRecord extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String fileFullPath;

	private String fileName;

	private long fileSize;

	private String uploadUser;

	private Date uploadDate;

	private Date uploadDateStart;

	private Date uploadDateEnd;

	private String fileType;

	private String fileTypeName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadDateStart() {
		return uploadDateStart;
	}

	public void setUploadDateStart(Date uploadDateStart) {
		this.uploadDateStart = uploadDateStart;
	}

	public Date getUploadDateEnd() {
		return uploadDateEnd;
	}

	public void setUploadDateEnd(Date uploadDateEnd) {
		this.uploadDateEnd = uploadDateEnd;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}
