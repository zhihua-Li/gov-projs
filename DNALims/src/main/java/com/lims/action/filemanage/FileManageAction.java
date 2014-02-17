/**
 * FileManageAction.java
 *
 * 2013-9-22
 */
package com.lims.action.filemanage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import com.lims.action.BaseAction;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.FileUploadRecord;
import com.lims.domain.po.SysUser;
import com.lims.service.dict.SysDictService;
import com.lims.service.fileupload.FileRecordService;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class FileManageAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private FileUploadRecord fileUploadRecordQuery;

	private List<FileUploadRecord> fileUploadRecordList;

	@Resource
	private FileRecordService fileRecordService;
	@Resource
	private SysDictService sysDictService;

	private String downloadFileName;
	private InputStream inputStream;

	private String checkedFileId;

	private String downloadFileSavePath;

	private Map<String, Object> jsonData;
	private List<DictItem> fileTypeList;


	public String query() throws Exception {
		if(fileUploadRecordQuery == null){
			fileUploadRecordQuery = new FileUploadRecord();

			fileUploadRecordQuery.setSortColumn("uploadDate");
			fileUploadRecordQuery.setSortOrder("DESC");
		}else{
			if(StringUtil.isNullOrEmpty(fileUploadRecordQuery.getFileName())){
				fileUploadRecordQuery.setFileName(null);
			}else{
				fileUploadRecordQuery.setFileName(fileUploadRecordQuery.getFileName().trim());
			}

			if(StringUtil.isNullOrEmpty(fileUploadRecordQuery.getUploadUser())){
				fileUploadRecordQuery.setUploadUser(null);
			}else{
				fileUploadRecordQuery.setUploadUser(fileUploadRecordQuery.getUploadUser().trim());
			}
		}

		fileTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.UPLOAD_FILE_TYPE);

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		fileUploadRecordQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		fileUploadRecordQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = fileRecordService.findFileUploadRecordListCount(fileUploadRecordQuery);
		fileUploadRecordList = fileRecordService.findFileUploadRecordList(fileUploadRecordQuery);

		return SUCCESS;
	}

	public String downUploadedFile() throws Exception {
		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();
		List<FileUploadRecord> fileList = fileRecordService.findFileUploadRecordListByFileIdList(ListUtil.stringArrayToList(checkedFileId.split(",")));

		String zipFileName = "下载-" + sysUser.getUserName() + "-" + DateUtil.currentDateStr("yyyyMMddHHmmssSSS") + ".zip";
		File zipFile = new File(DOWNLOAD_TMP_PATH);
		if(!zipFile.exists()){
			zipFile.mkdir();
		}

		zipFile = new File(DOWNLOAD_TMP_PATH + "\\" + zipFileName);
		if(!zipFile.exists()){
			zipFile.createNewFile();
		}

		BufferedInputStream bis = null;
//		BufferedOutputStream outputStream = null;
		ZipArchiveOutputStream zo = null;
		try {
			zo = new ZipArchiveOutputStream(new FileOutputStream(zipFile));
			zo.setUseZip64(Zip64Mode.AsNeeded);

			for(FileUploadRecord fur : fileList){
				try {
					ZipArchiveEntry entry = new ZipArchiveEntry(new File(fur.getFileFullPath()), fur.getFileName());
					zo.putArchiveEntry(entry);

					bis = new BufferedInputStream(new FileInputStream(fur.getFileFullPath()));
					byte[] buffer = new byte[1024];
					int len = -1;
					while((len = bis.read(buffer)) != -1){
						zo.write(buffer, 0, len);
					}

					zo.closeArchiveEntry();
				} catch(Exception e) {
					e.printStackTrace();
					jsonData.put("success", false);
					jsonData.put("errorMsg", e.getMessage());

					return SUCCESS;
				} finally {
					if(bis != null){
						bis.close();
					}
				}
			}

			zo.finish();
		} catch(Exception e) {
			e.printStackTrace();
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());

			return SUCCESS;
		} finally {
			if(zo != null){
				zo.close();
			}
		}

		inputStream = new BufferedInputStream(new FileInputStream(zipFile));
		try{
			downloadFileName = URLEncoder.encode(zipFileName,"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		jsonData.put("success", true);

		return SUCCESS;

//		inputStream = new BufferedInputStream(new FileInputStream(fur.getFileFullPath()));
//		try{
//			downloadFileName = URLEncoder.encode(fur.getFileName(),"UTF-8");
//		}catch(UnsupportedEncodingException e){
//			e.printStackTrace();
//		}
	}

//	public String downUploadedFile() throws Exception {
//		jsonData = new HashMap<String, Object>();
//		List<FileUploadRecord> fileList = fileRecordService.findFileUploadRecordListByFileIdList(ListUtil.stringArrayToList(checkedFileId.split(",")));
//
//		BufferedInputStream inputStream = null;
//		BufferedOutputStream outputStream = null;
//		for(FileUploadRecord fur : fileList){
//			try {
//				inputStream = new BufferedInputStream(new FileInputStream(fur.getFileFullPath()));
//				outputStream = new BufferedOutputStream(new FileOutputStream(new File(downloadFileSavePath + "\\" + fur.getFileName())));
//
//				byte[] buffer = new byte[1024];
//				int len = -1;
//				while((len = inputStream.read(buffer)) != -1){
//					outputStream.write(buffer, 0, len);
//				}
//
//				outputStream.flush();
//			} catch(Exception e) {
//				e.printStackTrace();
//				jsonData.put("success", false);
//				jsonData.put("errorMsg", e.getMessage());
//
//				return SUCCESS;
//			} finally {
//				if(inputStream != null){
//					inputStream.close();
//				}
//
//				if(outputStream != null){
//					outputStream.close();
//				}
//			}
//		}
//
//		jsonData.put("success", true);
//
//		return SUCCESS;
////		inputStream = new BufferedInputStream(new FileInputStream(fur.getFileFullPath()));
////		try{
////			downloadFileName = URLEncoder.encode(fur.getFileName(),"UTF-8");
////		}catch(UnsupportedEncodingException e){
////			e.printStackTrace();
////		}
//	}

	public String deleteFileRecord() throws Exception {
		jsonData = new HashMap<String, Object>();

		try {
			fileRecordService.deleteFileUploadRecordByFileIdList(ListUtil.stringArrayToList(checkedFileId.split(",")));
		} catch(Exception e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());
		}

		jsonData.put("success", true);

		return SUCCESS;
	}


	public FileUploadRecord getFileUploadRecordQuery() {
		return fileUploadRecordQuery;
	}


	public void setFileUploadRecordQuery(FileUploadRecord fileUploadRecordQuery) {
		this.fileUploadRecordQuery = fileUploadRecordQuery;
	}


	public FileRecordService getFileRecordService() {
		return fileRecordService;
	}


	public void setFileRecordService(FileRecordService fileRecordService) {
		this.fileRecordService = fileRecordService;
	}


	public List<FileUploadRecord> getFileUploadRecordList() {
		return fileUploadRecordList;
	}


	public void setFileUploadRecordList(List<FileUploadRecord> fileUploadRecordList) {
		this.fileUploadRecordList = fileUploadRecordList;
	}


	public String getDownloadFileName() {
		return downloadFileName;
	}


	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getCheckedFileId() {
		return checkedFileId;
	}


	public void setCheckedFileId(String checkedFileId) {
		this.checkedFileId = checkedFileId;
	}


	public String getDownloadFileSavePath() {
		return downloadFileSavePath;
	}


	public void setDownloadFileSavePath(String downloadFileSavePath) {
		this.downloadFileSavePath = downloadFileSavePath;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public List<DictItem> getFileTypeList() {
		return fileTypeList;
	}

	public void setFileTypeList(List<DictItem> fileTypeList) {
		this.fileTypeList = fileTypeList;
	}

}
