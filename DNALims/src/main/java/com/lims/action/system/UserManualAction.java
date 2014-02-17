/**
 * UserManualAction.java
 *
 * 2013-12-19
 */
package com.lims.action.system;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.struts2.ServletActionContext;

import com.lims.action.BaseAction;

/**
 * @author lizhihua
 *
 */
public class UserManualAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
	private String contentType;

	private InputStream inputStream;


	private static final String FILEPATH = "template/manual/User Manual.doc";

	/**
	 * 下载使用手册
	 * @return
	 * @throws Exception
	 */
	public String downloadManual() throws Exception {
		contentType = "application/octet-stream;charset=UTF-8";

			String realPath = ServletActionContext.getServletContext().getRealPath("/");

			inputStream = new BufferedInputStream(new FileInputStream(realPath + FILEPATH));



		try{
			filename = URLEncoder.encode("高通量DNA检验平台-使用手册.doc", "UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
