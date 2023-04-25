package com.pdmi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

	public static long timeStamp(Date date, SimpleDateFormat format) {
		String time = format.format(date);
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (long) (date.getTime() / 1000);
	}

	public static long timeStampMS(Date date) {
		return (long) (date.getTime());
	}

	public static Date time(String time, SimpleDateFormat format) {
		if (time == null)
			return null;
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String timeText(Date date, SimpleDateFormat format) {
		String time = format.format(date).toString();
		return time;
	}

	public static String timeGMT(Date date) {
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = TimeFormatFactory.get(
				"EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		String time = sdf.format(cd.getTime());
		return time;
	}

	public static int dateList(List<String> dateList, String start, String end,
			SimpleDateFormat format) {
		Calendar calendar = Calendar.getInstance();
		Date curDate = TimeUtil.time(start, format);
		Date endDate = TimeUtil.time(end, format);
		int count = 0;
		while (curDate.getTime() <= endDate.getTime()) {
			calendar.setTime(curDate);
			String dateStr = format.format(calendar.getTime());
			dateList.add(dateStr);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			curDate = calendar.getTime();
			count = count + 1;
		}
		return count;
	}

	public static String dateList(List<String> dateList, String start,
			String end, int limit, SimpleDateFormat format) {
		Calendar calendar = Calendar.getInstance();
		Date curDate = TimeUtil.time(start, format);
		Date endDate = TimeUtil.time(end, format);
		int count = 0;
		String nextDate = "";
		while (curDate.getTime() <= endDate.getTime() && count <= limit) {
			calendar.setTime(curDate);
			String dateStr = format.format(calendar.getTime());
			if (count == limit)
				nextDate = dateStr;
			else
				dateList.add(dateStr);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
			curDate = calendar.getTime();
			count = count + 1;
		}
		return nextDate;
	}

	public static String dateCal(String start, int limit,
			SimpleDateFormat format) {
		Calendar calendar = Calendar.getInstance();
		Date curDate = TimeUtil.time(start, format);
		calendar.setTime(curDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + limit);
		String dateStr = format.format(calendar.getTime());
		return dateStr;
	}
	
	public static Date stampToDate(long stamp, SimpleDateFormat format){
        Date date = new Date(stamp);
        return date;
	}
}
