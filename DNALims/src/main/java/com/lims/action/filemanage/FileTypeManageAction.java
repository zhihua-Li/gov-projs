/**
 * FileTypeManageAction.java
 *
 * 2013-10-10
 */
package com.lims.action.filemanage;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.service.KeyGeneratorService;
import com.lims.service.dict.SysDictService;

/**
 * @author lizhihua
 *
 */
public class FileTypeManageAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private SysDictService sysDictService;
	@Resource
	private KeyGeneratorService keyGeneratorService;

	private List<DictItem> fileTypeList;

	private DictItem dictItem;

	private Map<String, Object> jsonData;

	private String downloadFileName;
	private InputStream inputStream;


	public String query() throws Exception {
		fileTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.UPLOAD_FILE_TYPE);

		return SUCCESS;
	}

	public String add() throws Exception {
		jsonData = new HashMap<String, Object>();

		fileTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.UPLOAD_FILE_TYPE);

		int temp = 0;
		for(DictItem di : fileTypeList){
			if(Integer.parseInt(di.getDictKey()) > temp){
				temp = Integer.parseInt(di.getDictKey());
			}
		}

		dictItem.setDictInfoType(DictInfo.UPLOAD_FILE_TYPE);
		dictItem.setDictKey(String.valueOf(temp+1));
		dictItem.setId(keyGeneratorService.getNextKey());
		dictItem.setDictSort(temp+1);
		sysDictService.addDictItem(dictItem);

		jsonData.put("success", true);

		return SUCCESS;
	}

	public String update() throws Exception {
		jsonData = new HashMap<String, Object>();

		sysDictService.updateDictItem(dictItem);

		jsonData.put("success", true);

		return SUCCESS;
	}

	public String intoDownloadFileUploader() throws Exception {

		return SUCCESS;
	}

	public String downloadFileUploader() throws Exception {
		downloadFileName = FILEUPLOADER_PATH.substring(FILEUPLOADER_PATH.lastIndexOf('/')+1);
		inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(FILEUPLOADER_PATH));

		try{
			downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}


		return SUCCESS;
	}


	public String delete() throws Exception {

		sysDictService.deleteDictItemById(dictItem.getId());

		return SUCCESS;
	}

	public List<DictItem> getFileTypeList() {
		return fileTypeList;
	}


	public void setFileTypeList(List<DictItem> fileTypeList) {
		this.fileTypeList = fileTypeList;
	}


	public DictItem getDictItem() {
		return dictItem;
	}


	public void setDictItem(DictItem dictItem) {
		this.dictItem = dictItem;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
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
}
