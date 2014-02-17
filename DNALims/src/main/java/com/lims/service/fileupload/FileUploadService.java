/**
 * FileUploadService.java
 *
 * 2013-9-22
 */
package com.lims.service.fileupload;

/**
 * @author lizhihua
 *
 */
public interface FileUploadService {

	/*
	 * @param fileName 文件名称
	 * 		  fileData 文件二进制数据
	 * 		  savePath 文件存放位置
	 */
	public boolean saveUploadFile(String fileType, String fileName, byte[] fileData, String savePath, String uploadUser) throws Exception;

}
