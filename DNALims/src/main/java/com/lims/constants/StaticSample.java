/**
 * StaticSample.java
 *
 * 2013-10-11
 */
package com.lims.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhihua
 *
 */
public class StaticSample {

	public static String STATIC_SAMPLE_CK = "CK";
	public static String STATIC_SAMPLE_LADDER = "Ladder";
	public static String STATIC_SAMPLE_9947 = "9947";

	public static String STATIC_SAMPLE_CK_POSITION = "A01";

	public static List<String> staticSampleList(){
		List<String> list = new ArrayList<String>();
		list.add("CK");
		list.add("Ladder");
		list.add("9947");

		return list;
	}

}
