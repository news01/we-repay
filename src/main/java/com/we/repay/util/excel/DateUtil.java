package com.we.repay.util.excel;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 日期工具类
 */
public class DateUtil {
	
	private static final  Logger logger = Logger.getLogger(DateUtil.class);
	
	/**
	 * 日期时间格式：yyyy-MM-dd HH:mm:ss
	 */
	public final static String YYYY_MM_DD_HH_MM_SS_1 = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期时间格式：yyyy/MM/dd HH:mm:ss
	 */
	public final static String YYYY_MM_DD_HH_MM_SS_2 = "yyyy/MM/dd HH:mm:ss";
	
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public final static String YYYY_MM_DD_1 = "yyyy-MM-dd";
	
	/**
	 * 日期格式：yyyy年MM月dd日
	 */
	public final static String YYYY_MM_DD_2 = "yyyy年MM月dd日";
	
	/**
	 * 日期格式：yyyyMMdd
	 */
	public final static String YYYY_MM_DD_3 = "yyyyMMdd";
	
	/**
	 * 日期格式：yyyy/MM/dd
	 */
	public final static String YYYY_MM_DD_4 = "yyyy/MM/dd";
	
	/**
	 * 日期格式：MM
	 */
	public final static String YYYY_MM_DD_5 = "MM";
	
	/**
	 * 日期格式：yyyy-MM
	 */
	public final static String YYYY_MM_DD_6 = "yyyy-MM";
	
	/**
	 * 时间格式：HH:mm:ss
	 */
	public final static String HH_MM_SS_1 = "HH:mm:ss";
	
	/**
	 * 时间格式：HH时mm分ss秒
	 */
	public final static String HH_MM_SS_2 = "HH时mm分ss秒";
	
	/**
	 * 时间格式：HHmmss
	 */
	public final static String HH_MM_SS_3 = "HHmmss";
	
	private final static Map<String,String> patternMap = new HashMap<String,String>();
	
	static{
		patternMap.put(YYYY_MM_DD_1,"\\d{4}-\\d{2}-\\d{2}");
		patternMap.put(YYYY_MM_DD_3,"\\d{4}\\d{2}\\d{2}");
		patternMap.put(YYYY_MM_DD_2,"\\d{4}年\\d{2}月\\d{2}日");
		patternMap.put(YYYY_MM_DD_4,"\\d{4}/\\d{2}/\\d{2}");
		patternMap.put(YYYY_MM_DD_HH_MM_SS_1,"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		patternMap.put(YYYY_MM_DD_HH_MM_SS_2,"\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}");
	}
	
	private static SimpleDateFormat getDateFormat(String pattern){
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getDateStr1(Date date){
		return getDateFormat(YYYY_MM_DD_1).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return yyyy年MM月dd日
	 */
	public static String getDateStr2(Date date){
		return getDateFormat(YYYY_MM_DD_2).format(date);
	}
	
	
	/**
	 * 当前时间yyyyMMddHHmmss
	 */
	public static String getCurrentDateString() {
		return date2String(new Date(), yyyyMMddHHmmss);
	}
	
	/**
	 * 日期按照指定格式转换为字符串
	 */
	public static String date2String(Date date, String formatStr) {
		return date2String(date, formatStr, Locale.getDefault());
	}
	
	private static String date2String(Date date, String formatStr, Locale locale) {
		try {
			Format format = new SimpleDateFormat(formatStr, locale);
			return format.format(date);
		} catch (Exception e) {
			//logger.error(e.getMessage());
		}
		return "";
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return yyyyMMdd
	 */
	public static String getDateStr3(Date date){
		return getDateFormat(YYYY_MM_DD_3).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return yyyyMMdd
	 */
	public static String getDateStr4(Date date){
		return getDateFormat(YYYY_MM_DD_4).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return MM
	 */
	public static String getDateStr5(Date date){
		return getDateFormat(YYYY_MM_DD_5).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期
	 * @param date
	 * @return yyyy-MM
	 */
	public static String getDateStr6(Date date){
		return getDateFormat(YYYY_MM_DD_6).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化时间
	 * @param date
	 * @return HH:mm:ss
	 */
	public static String getTimeStr1(Date date){
		return getDateFormat(HH_MM_SS_1).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化时间
	 * @param date
	 * @return HH时mm分ss秒
	 */
	public static String getTimeStr2(Date date){
		return getDateFormat(HH_MM_SS_2).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化时间
	 * @param date
	 * @return HHmmss
	 */
	public static String getTimeStr3(Date date){
		return getDateFormat(HH_MM_SS_3).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期时间
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeStr1(Date date){
		return getDateFormat(YYYY_MM_DD_HH_MM_SS_1).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期时间
	 * @param date
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static String getDateTimeStr2(Date date){
		return getDateFormat(YYYY_MM_DD_HH_MM_SS_2).format(date);
	}
	
	/**
	 * 
	 * 描述：格式化日期时间
	 * @param date
	 * @param format 格式
	 * @return
	 */
	public static String getDateTimeStr(Date date, String format){
		return getDateFormat(format).format(date);
	}
	
	/**
	 * 
	 * 描述：字符串转成日期时间
	 * 允许格式:
	 *          (1)yyyy-MM-dd  
	 *          (2)yyyy年MM月dd日   
	 *          (3)yyyyMMdd  
	 *          (4)yyyy/MM/dd  
	 *          (5)yyyy-MM-dd HH:mm:ss  
	 *          (6)yyyy/MM/dd HH:mm:ss  
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		try {
			Set<String> keySet = patternMap.keySet();
			Iterator<String> it = keySet.iterator();
			while(it.hasNext()){
				String key = it.next();
				Pattern p = Pattern.compile(patternMap.get(key));
				Matcher m = p.matcher(dateStr.trim());
				if(m.matches()){
					return getDateFormat(key).parse(dateStr.trim());
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：字符串转成日期时间
	 * @param dateStr
	 * @param format 格式
	 * @return
	 */
	public static Date strToDate(String dateStr, String format){
		try {
			return getDateFormat(format).parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：日期相加
	 * @param date
	 * @param type
	 * @param value
	 * @return
	 */
	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 描述：日期加上年数的时间
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date, int year) {
		return add(date, Calendar.YEAR, year);
	}
	
	/**
	 * 
	 * 描述：得到增加i个月后的时间，如加（5）或减（-5）
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMonth(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}
	
	/**
	 * 
	 * 描述：得到增加i天后的时间，如加（5）或减（-5）
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addDay(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return cal.getTime();
	}
	
	/**
	 * 
	 * 描述：得到增加i分钟后的时间，如加（5）或减（-5）
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMinute(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		return cal.getTime();
	}
	
	/**
	 * 
	 * 描述：计算两个时间之间的天数差
	 * @param earlyTime 较早的时间
	 * @param lateTime 较晚的时间
	 * @return 两者的天数差
	 * @throws ParseException
	 */
	public static int minusDates(Date earlyTime, Date lateTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date earlyDate;
		Date lateDate;
		Double intervalDay = 0.00;
		earlyDate = df.parse(df.format(earlyTime));
		lateDate = df.parse(df.format(lateTime));
		Long earlyDateSeconds = earlyDate.getTime();
		Long lateDateSeconds = lateDate.getTime();
		intervalDay = (double) ((lateDateSeconds - earlyDateSeconds) / (60 * 60 * 24 * 1000));
		return (int) Math.floor(intervalDay);
	}
	
	/**
	 * 
	 * 描述：判断当前时间是否在时间date2之前
	 * @param date2
	 * @return
	 */
	public static boolean isDateBefore(Date date2) {
		Date date1 = new Date();
		return date1.before(date2);
	}
	
	/**
	 * 
	 * 描述：获取当前时间所在月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		 calendar.setTime(date);
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}
	
	
	/**
	 * 描述：判断时间是否在指定的两个时间之间（可指定时间格式）
	 * 
	 * @author sunjie
	 * @date 2015-11-9下午13:48:25
	 * @param start
	 * @param between
	 * @param end
	 * @param format
	 *            格式 类似yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean isDateBetween(String start, String between, String end, String format) {
		try {
			DateFormat df = new SimpleDateFormat(format);
			return isDateBetween(df.parse(start), df.parse(between), df.parse(end));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 描述：判断时间是否在指定的两个时间之间（可指定时间格式）
	 * 
	 * @author sunjie
	 * @date 2015-11-9下午13:48:25
	 * @param start
	 * @param between
	 * @param end
	 * @return
	 */
	public static boolean isDateBetween(Date start, Date between, Date end) {
		if ((start).before( (between)) && (between).before( (end))) {
			return true;
		}
		return false;
	}
	
	
    // 判断时间date1是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	
	/**
	 * 得到一个日期的年份
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到一个日期的月份
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到一个日期的Day
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到一个日期的小时
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 得到一个日期的分钟
	 */
	public static int getMinite(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 得到一个日期的秒
	 */
	public static int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}
	
}
