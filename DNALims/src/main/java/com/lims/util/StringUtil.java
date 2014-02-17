/**
 * StringUtil.java
 *
 * 2013-6-16
 */
package com.lims.util;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lizhihua
 *	字符串工具类
 */
public class StringUtil {


	public static boolean isNotEmpty(String src) {
		return src != null && !"".equals(src);
	}

	public static boolean isNullOrEmpty(String src) {
		return src == null || "".equals(src);
	}

	/**
	 * 用字府c给字符串str补足位数
	 * @param str
	 * @param c
	 * @param digitNum
	 * @return
	 */
	public static String getFullChar(String str, char c, int digitNum) {
		if(str!=null){
			for(int i=str.length();i<digitNum;i++){
				str = c + str;
			}
		}
		return str;
	}

	public static String arrayToStringIgnoreNull(String[] strArr, String separator) {
		StringBuilder sb = new StringBuilder(strArr[0]);
		for(int i = 1; i < strArr.length; i++){
			if(strArr[i] == null || "".equals(strArr[i])){
				continue;
			}

			sb.append(separator).append(strArr[i]);
		}

		return sb.toString();
	}

	public static String arrayToString(String[] strArr, String separator) {
		StringBuilder sb = new StringBuilder(strArr[0]);
		for(int i = 1; i < strArr.length; i++){
			if(strArr[i] == null || "".equals(strArr[i])){
				continue;
			}

			sb.append(separator).append(strArr[i]);
		}

		return sb.toString();
	}

	public static String[] stringToArray(String src, String separator){
		return src.split(separator);
	}


	public static List<String> splitGeneInfo(String geneInfo, String separator){
		List<String> result = new ArrayList<String>();

		int i = -1;
		while((i = geneInfo.indexOf(separator)) > -1){
			if(i == 0){
				result.add("");
			}else{
				result.add(geneInfo.substring(0, i));
			}

			if(i == geneInfo.length()-1){
				break;
			}else{
				geneInfo = geneInfo.substring(i+1);
			}
		}

		return result;
	}

	public static int findCharCountInString(String res, char ch){
		char[] charArr = res.toCharArray();

		int count = 0;
		for(char resChar : charArr){
			if(resChar == ch){
				count++;
			}
		}

		return count;
	}

}
