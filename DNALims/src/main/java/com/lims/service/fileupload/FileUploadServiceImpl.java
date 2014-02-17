/**
 * FileUploadServiceImpl.java
 *
 * 2013-9-22
 */
package com.lims.service.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.FileUploadRecord;
import com.lims.service.KeyGeneratorService;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.ConfigUtil;

/**
 * @author lizhihua
 *
 */
@Service("fileUploadService")
@Transactional
public class FileUploadServiceImpl extends LimsBaseServiceImpl implements
		FileUploadService {


	private KeyGeneratorService keyGeneratorService;

	/*
	 * @param fileName 文件名称
	 * 		  fileData 文件二进制数据
	 * 		  savePath 文件存放位置
	 */
	public boolean saveUploadFile(String fileType, String fileName, byte[] fileData, String savePath, String uploadUser) throws Exception {
		DictItem dictItem = new DictItem();
		dictItem.setDictKey(fileType);
		dictItem.setDictInfoType(DictInfo.UPLOAD_FILE_TYPE);
		DictItem di = dao.getSqlSession().selectOne("findDictItem", dictItem);

		FileOutputStream fos = null;
		try {
			String rootPath = ConfigUtil.getProperty(ConfigUtil.FILEDATA_ROOTPATH_KEY);
			File outputFile = new File(rootPath + "\\" + di.getDictValue() + "\\" + savePath);
			if(!outputFile.exists()){
				outputFile.mkdirs();
			}

			outputFile = new File(rootPath + "\\" + di.getDictValue() + "\\"  + savePath + "\\" + fileName);
			fos = new FileOutputStream(outputFile);
			fos.write(fileData);
			fos.flush();

			FileUploadRecord fur = new FileUploadRecord();
			fur.setId(keyGeneratorService.getNextKey());
			fur.setFileFullPath(rootPath + "\\" + di.getDictValue() + "\\"  + savePath + "\\" + fileName);
			fur.setFileName(fileName);
			fur.setFileSize(fileData.length);
			fur.setUploadUser(uploadUser);
			fur.setUploadDate(new Date());
			fur.setFileType(fileType);

			dao.getSqlSession().insert("FileUploadRecordMapper.insertFileUploadRecord", fur);

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fos != null){
				fos.close();
			}
		}

		return true;
	}


	public KeyGeneratorService getKeyGeneratorService() {
		return keyGeneratorService;
	}

	public void setKeyGeneratorService(KeyGeneratorService keyGeneratorService) {
		this.keyGeneratorService = keyGeneratorService;
	}
}
