/**
 * ListUtil.java
 *
 * 2013-6-14
 */
package com.lims.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhihua
 *
 */
public class ListUtil{


	public static List<String> stringArrayToList(String[] strArr) {
		List<String> stringList = new ArrayList<String>(strArr.length);

		for(String str : strArr){
			stringList.add(str);
		}

		return stringList;
	}



	@SuppressWarnings("rawtypes")
	public static boolean isEmptyList(List list) {
		return list == null || list.size() == 0;
	}

}
