package com.lims.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static final String GetMd5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'E', 'S', 'X', 'F', 'Y', 'K' };
		char str[] = null;
		byte strTemp[] = s.getBytes();
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		return new String(str);
	}


	public static void main(String args[]) {
		System.out.println(GetMd5("123456"));
	}

}
