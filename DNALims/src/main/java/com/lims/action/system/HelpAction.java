/**
 * HelpAction.java
 *
 * 2014-1-12
 */
package com.lims.action.system;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.lims.action.BaseAction;

/**
 * @author lizhihua
 *
 */
public class HelpAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String ietype;

	private String filename;
	private String contentType;

	private InputStream inputStream;

	public String downloadIEBrower() throws Exception {
		contentType = "application/octet-stream;charset=UTF-8";

		if(ietype.equals("1")){
			filename = IE_BROWSER_XP_CHS_PATH.substring(IE_BROWSER_XP_CHS_PATH.lastIndexOf('/')+1);
			inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(IE_BROWSER_XP_CHS_PATH));
		}else if(ietype.equals("2")){
			filename = IE_BROWSER_VISTA_CHS_PATH.substring(IE_BROWSER_VISTA_CHS_PATH.lastIndexOf('/')+1);
			inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(IE_BROWSER_VISTA_CHS_PATH));
		}else if(ietype.equals("3")){
			filename = IE_BROWSER_XP_ENU_PATH.substring(IE_BROWSER_XP_ENU_PATH.lastIndexOf('/')+1);
			inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(IE_BROWSER_XP_ENU_PATH));
		}else if(ietype.equals("4")){
			filename = IE_BROWSER_VISTA_ENU_PATH.substring(IE_BROWSER_VISTA_ENU_PATH.lastIndexOf('/')+1);
			inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(IE_BROWSER_VISTA_ENU_PATH));
		}


		try{
			filename = URLEncoder.encode(filename,"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return SUCCESS;
	}



	public String downloadWinrar() throws Exception {
		contentType = "application/octet-stream;charset=UTF-8";
		filename = WINRAR_PATH.substring(WINRAR_PATH.lastIndexOf('/')+1);
		inputStream = new BufferedInputStream(this.getClass().getResourceAsStream(WINRAR_PATH));

		try{
			filename = URLEncoder.encode(filename,"UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getIetype() {
		return ietype;
	}

	public void setIetype(String ietype) {
		this.ietype = ietype;
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
