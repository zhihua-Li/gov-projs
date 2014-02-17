/**
 * DateUtil.java
 *
 * 2013-6-16
 */
package com.lims.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizhihua
 *
 */
public class DateUtil {


	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}


	public static String currentDateStr(String fmt) {
		return new SimpleDateFormat(fmt).format(new Date(System.currentTimeMillis()));
	}

	public static String dateToString(Date date, String fmt) {
		return new SimpleDateFormat(fmt).format(date);
	}

	/**
	 * 自定义格式 字符串转换为日期型
	 * @param strDate
	 * @return 日期型时间
	 */
	public static Date stringToDate(String strDate, String fm) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(fm);
		try {
			date = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}


	/**
	 * 相差天数
	 * @param endDate		结束日期
	 * @param firstDate		开始日期
	 * @return
	 * @throws ParseException
	 */
	public static int subDay(String endDate, String firstDate)
			throws Exception {
		Date dt1 = stringToDate(endDate, "yyyy-MM-dd");
		Date dt2 = stringToDate(firstDate, "yyyy-MM-dd");
		long l = (dt1.getTime() - dt2.getTime()) / 1000 / 60 / 60 / 24;
		int result = Integer.parseInt(String.valueOf(l));
		return result;
	}

	public static String currentYear(){
		String currentDate = dateToString(new Date(), "yyyy-MM-dd");
		return currentDate.substring(0, 4);
	}

	public static String getYearStr(Date date){
		String strDate = dateToString(date, "yyyy-MM-dd");
		return strDate.substring(0, 4);
	}

	public static String currentMonth(){
		String currentDate = dateToString(new Date(), "yyyy-MM-dd");
		return currentDate.substring(5, 7);
	}

	public static String currentDay(){
		String currentDate = dateToString(new Date(), "yyyy-MM-dd");
		return currentDate.substring(8);
	}

	public static String getMonthStr(Date date){
		String strDate = dateToString(date, "yyyy-MM-dd");
		return strDate.substring(5, 7);
	}

	public static String getDayStr(Date date){
		String strDate = dateToString(date, "yyyy-MM-dd");
		return strDate.substring(8);
	}

//
//	public static void main(String[] args){
//		System.out.println(currentDateStr("yyyyMMddHHmmssSSS"));
//	}
}
