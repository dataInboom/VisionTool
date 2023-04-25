package com.pdmi.util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TimeFormatFactory {

	private static Map<String, SimpleDateFormat> formatMap = Collections
			.synchronizedMap(new HashMap<String, SimpleDateFormat>());

//	public static SimpleDateFormat get(String format) {
//		if (!formatMap.containsKey(format))
//			formatMap.put(format, new SimpleDateFormat(format));
//		return formatMap.get(format);
//	}
	
	public static SimpleDateFormat get(String format) {
		return new SimpleDateFormat(format);
	}
	
	public static SimpleDateFormat get(String format, Locale locale) {
		if (!formatMap.containsKey(format))
			formatMap.put(format, new SimpleDateFormat(format,locale));
		return formatMap.get(format);
	}

}
