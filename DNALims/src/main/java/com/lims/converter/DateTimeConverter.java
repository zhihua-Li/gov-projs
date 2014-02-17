package com.lims.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsException;
import org.apache.struts2.util.StrutsTypeConverter;


/**
 * 日期时间类型转换
 */
public class DateTimeConverter extends StrutsTypeConverter {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (ArrayUtils.isEmpty(values) || StringUtils.isEmpty(values[0])) {
			return null;
		}
		Date result = null;
		try {
			String temp = (String) values[0];
			result = (Date) sdf.parseObject(temp);
		} catch (ParseException e) {
			throw new StrutsException("Could not parse date", e);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object o) {
		String result = null;
		if (o instanceof Date) {
			result = sdf.format(o);
		}
		return result;
	}
}