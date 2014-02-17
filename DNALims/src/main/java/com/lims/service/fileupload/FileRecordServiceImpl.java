/**
 * FileRecordServiceImpl.java
 *
 * 2013-9-22
 */
package com.lims.service.fileupload;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.FileUploadRecord;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("fileRecordService")
@Transactional
public class FileRecordServiceImpl extends LimsBaseServiceImpl implements
		FileRecordService {

	/**
	 * 获取文件记录个数
	 * @param fileUploadRecord
	 * @return
	 * @throws ServiceException
	 */
	public int findFileUploadRecordListCount(FileUploadRecord fileUploadRecord) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("FileUploadRecordMapper.findFileUploadRecordListCount", fileUploadRecord);
		} catch(Exception e) {
			log.error("invoke FileRecordService.findFileUploadRecordListCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取文件记录列表
	 * @param fileUploadRecord
	 * @return
	 * @throws ServiceException
	 */
	public List<FileUploadRecord> findFileUploadRecordList(FileUploadRecord fileUploadRecord) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("FileUploadRecordMapper.findFileUploadRecordList", fileUploadRecord);
		} catch(Exception e) {
			log.error("invoke FileRecordService.findFileUploadRecordList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 根据id获取文件记录
	 * @param fileUploadRecordId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public FileUploadRecord findFileUploadRecordById(String fileUploadRecordId)
			throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("FileUploadRecordMapper.findFileUploadRecordById", fileUploadRecordId);
		} catch(Exception e) {
			log.error("invoke FileRecordService.findFileUploadRecordById error!", e);
			throw new ServiceException(e);
		}
	}

	public List<FileUploadRecord> findFileUploadRecordListByFileIdList(List<String> fileIdList) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("FileUploadRecordMapper.findFileUploadRecordListByFileIdList", fileIdList);
		} catch(Exception e) {
			log.error("invoke FileRecordService.findFileUploadRecordListByFileIdList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 删除文件记录
	 * @param fileRecordId
	 * @throws ServiceException
	 */
	@Override
	public void deleteFileUploadRecord(String fileRecordId)
			throws ServiceException {
		try {
			FileUploadRecord fur = dao.getSqlSession().selectOne("FileUploadRecordMapper.findFileUploadRecordById", fileRecordId);

			dao.getSqlSession().selectOne("FileUploadRecordMapper.deleteFileUploadRecord", fileRecordId);

			File file = new File(fur.getFileFullPath());
			if(file.exists()){
				try {
					file.delete();
				} catch(Exception e){
					log.error("删除文件系统中的文件： " + fur.getFileFullPath() + "，删除操作失败！", e);
				}
			}
		} catch(Exception e) {
			log.error("invoke FileRecordService.deleteFileUploadRecord error!", e);
			throw new ServiceException(e);
		}
	}

	public void deleteFileUploadRecordByFileIdList(List<String> fileIdList) throws ServiceException {
		try {
			for(String fileRecordId : fileIdList){
				FileUploadRecord fur = dao.getSqlSession().selectOne("FileUploadRecordMapper.findFileUploadRecordById", fileRecordId);

				dao.getSqlSession().selectOne("FileUploadRecordMapper.deleteFileUploadRecord", fileRecordId);

				File file = new File(fur.getFileFullPath());
				if(file.exists()){
					try {
						file.delete();
					} catch(Exception e){
						log.error("删除文件系统中的文件： " + fur.getFileFullPath() + "，删除操作失败！", e);
					}
				}
			}
		} catch(Exception e) {
			log.error("invoke FileRecordService.deleteFileUploadRecordByFileIdList error!", e);
			throw new ServiceException(e);
		}
	}

}
