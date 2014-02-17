/**
 * ConfigUtil.java
 *
 * 2013-6-17
 */
package com.lims.util;

import java.io.IOException;
import java.util.Properties;


/**
 * @author lizhihua
 *
 */
public class ConfigUtil {

	public static Properties props = new Properties();

	public static final String configFile = "/application.properties";

	public static final String DOCUMENT_GENERATE_DIR_KEY = "document.generateDir";

	public static final String FILEDATA_ROOTPATH_KEY = "datafile.rootPath";


	public static String getProperty(String key) {
		try{
			props = getProperties();
			return props.getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private static Properties getProperties() throws IOException{
		Properties  p = new Properties();
		p.load(ConfigUtil.class.getResourceAsStream(configFile));
		return p;
	}

}
