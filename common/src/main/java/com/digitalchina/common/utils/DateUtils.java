package com.digitalchina.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <日期工具, 自己要再加>
 * 
 * @author lihui
 * @since 2019年9月11日
 */
public class DateUtils {

	/**
	 * 获取持续时间
	 *
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	public static String continueTime(Date begin_date, Date end_date) {
		StringBuffer sb = new StringBuffer();
		long d = end_date.getTime() - begin_date.getTime();
		long day = d / (1000 * 60 * 60 * 24);
		long h = (d- day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
		long m = (d- day * 1000 * 60 * 60 * 24 -h * 1000 * 60 * 60) / (1000 * 60);
		sb.append(day).append("天").append(h).append("小时").append(m).append("分钟");
		return sb.toString();
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String date2Str(Date date, String formatte) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatte);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 获取当天0点0分0秒（00:00:00）
	 *
	 * @return
	 */
	public static String getTodayZeroStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date beginOfDate = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(beginOfDate);
	}

	/**
	 * 获取当天0点0分0秒（00:00:00）
	 *
	 * @return
	 */
	public static Date getTodayZero() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date beginOfDate = cal.getTime();
		return beginOfDate;
	}

}
