/**
 * FileRecordService.java
 *
 * 2013-9-22
 */
package com.lims.service.fileupload;

import java.util.List;

import com.lims.domain.po.FileUploadRecord;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface FileRecordService {

	/**
	 * 获取文件记录个数
	 * @param fileUploadRecord
	 * @return
	 * @throws ServiceException
	 */
	public int findFileUploadRecordListCount(FileUploadRecord fileUploadRecord) throws ServiceException;

	/**
	 * 根据id获取文件记录
	 * @param fileUploadRecordId
	 * @return
	 * @throws ServiceException
	 */
	public FileUploadRecord findFileUploadRecordById(String fileUploadRecordId) throws ServiceException;

	public List<FileUploadRecord> findFileUploadRecordListByFileIdList(List<String> fileIdList) throws ServiceException;

	public void deleteFileUploadRecordByFileIdList(List<String> fileIdList) throws ServiceException;

	/**
	 * 获取文件记录列表
	 * @param fileUploadRecord
	 * @return
	 * @throws ServiceException
	 */
	public List<FileUploadRecord> findFileUploadRecordList(FileUploadRecord fileUploadRecord) throws ServiceException;

	/**
	 * 删除文件记录
	 * @param fileRecordId
	 * @throws ServiceException
	 */
	public void deleteFileUploadRecord(String fileRecordId) throws ServiceException;

}
