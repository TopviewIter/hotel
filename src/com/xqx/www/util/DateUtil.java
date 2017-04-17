package com.xqx.www.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author xqx
 *
 */
public class DateUtil {

	private static final String pattern = "yyyy-MM-dd HH:mm:ss";

	private static final String shortPattern = "yyyy-MM-dd";

	public static String getLocalTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String getShortLocalTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(shortPattern);
		return format.format(date);
	}

	public static Date getLocalTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getShortLocalTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(shortPattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDate(int year, int month, int day) {

		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c.getTime();
	}

	/**
	 * 月份从1开始
	 * 
	 * @param month
	 * @return
	 */
	public static Date getFirstDay(int month) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 月份从1开始
	 * 
	 * @param month
	 * @return
	 */
	public static Date getLastDay(int month) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static double dayDis(Date start, Date end) {

		Calendar sc = Calendar.getInstance();
		sc.setTime(getShortLocalTime(getShortLocalTime(start)));
		long st = sc.getTimeInMillis();
		Calendar ec = Calendar.getInstance();
		ec.setTime(getShortLocalTime(getShortLocalTime(end)));
		long et = ec.getTimeInMillis();
		long bd = (et - st) / (1000 * 3600 * 24);

		double result = 0;
		int sh = sc.get(Calendar.HOUR_OF_DAY);
		int eh = ec.get(Calendar.HOUR_OF_DAY);

		if(bd <= 0) {
			if(sh < 6){
				result = 1;
			} else if (sh >= 6 && eh <= 18) {
				result = 0.5;
			} else if (eh - sh <= 5) {
				result = (eh - sh) / 24;
			} else {
				result = 1;
			}
		} else {
			if(eh <= 12) {
				result = bd;
			} else {
				result = bd + 1;
			}
		}
		
		return result;
	}

	public static void main(String[] args) throws ParseException {

		String start = "2017-02-02 06:00:00";
		String end = "2017-02-02 08:00:00";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dayDis(format.parse(start), format2.parse(end)));
	}

}
