package com.we.repay.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @description(描述):
 * @author(创建人): zhangyong
 * @date(创建日期): 2012-3-19
 * @version(版本): v1.0
 * @company(公司): heshidai.com
 * @copyright(c): heshidai.com All rights reserved.
 * @history(修改历史):
 * 
 */
public class DateUtil {

	//private static Logger logger = Logger.getLogger(DateUtil.class);

	private static final long ONE_DAY_INTERVAL = 1000 * 3600 * 24;

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	public static final String HHMMSS = "HH:mm:ss";

	public static final String yyyyMMdd = "yyyyMMdd";

	/**
	 * 获得当然日期，返回Date类型
	 */
	public static Date getCurDate() {
		return new Date();
	}

	/**
	 * 当前日期YYYYMMDD
	 */
	public static String getCurrentDate() {
		return date2String(new Date(), YYYYMMDD);
	}

	/**
	 * 当前时间
	 */
	public static String getCurrentDatetime() {
		return date2String(new Date(), YYYYMMDDHHMMSS);
	}

	/**
	 * 当前时间
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

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date string2Date(String strDate, String formatStr) {
		return string2Date(strDate, formatStr, Locale.getDefault());
	}

	/**
	 * 比较StartDate和EndDate, startDate和endDate必须是凌晨的0:00:00:000.
	 */
	public static int dateDiff(Date startDate, Date endDate) {
		assert (startDate.getTime() % ONE_DAY_INTERVAL == 0 && endDate.getTime() % ONE_DAY_INTERVAL == 0);
		long interval = endDate.getTime() - startDate.getTime();
		return (int) (interval / ONE_DAY_INTERVAL);
	}

	/**
	 * 得到增加i天后的时间，如加（5）或减（-5）
	 */
	public static Date addDay(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return cal.getTime();
	}

	/**
	 * 得到增加i个月后的时间，如加（5）或减（-5）
	 */
	public static Date addMonth(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}

	/**
	 * 得到增加i分钟后的时间，如加（5）或减（-5）
	 */
	public static Date addMin(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		return cal.getTime();
	}

	/**
	 * 得到之前的几天
	 */
	public static String getDateBefore(int before) {
		return date2String(new Date(System.currentTimeMillis() - ONE_DAY_INTERVAL * before), YYYYMMDD);
	}

	/**
	 * 得到之前的几个月
	 */
	public static String getMonthBefore(int before) {
		Calendar gr = Calendar.getInstance();
		int year = Integer.parseInt(DateUtil.getCurrentDate().substring(0, 4));
		int month = Integer.parseInt(DateUtil.getCurrentDate().substring(5, 7));
		int day = Integer.parseInt(DateUtil.getCurrentDate().substring(8, 10));
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
		gr.set(year, month - before - 1, day);
		return sdf.format(gr.getTime());
	}

	/**
	 * 得到指定日期的前几天
	 */
	public static Date getDateBefore(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 得到之后几天
	 */
	public static String getDateAfter(String dt, Long days) {
		Date dt_time;
		dt_time = string2Date(dt, YYYYMMDD);
		return date2String(new Date(dt_time.getTime() + ONE_DAY_INTERVAL * days), YYYYMMDD);
	}

	/**
	 * 得到某天的DayOfWeek: 星期一为1...星期天为7;
	 */
	public static int getDayOfWeek(Date theDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDay);
		return ((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7) + 1;
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

	/**
	 * 得到输入日期是该年第几周
	 */
	public static int getWeekOfYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.WEEK_OF_YEAR);
	}

	private static Date string2Date(String strDate, String formatStr, Locale locale) {
		Date date = null;
		try {
			// date = new SimpleDateFormat(formatStr, locale).parse(strDate);
			date = new SimpleDateFormat(formatStr).parse(strDate);
		} catch (ParseException e) {
			//logger.error(e.getMessage());
		}
		return date;
	}

	public static String date2Str(Date date, String format) {
		SimpleDateFormat tempFormat = new SimpleDateFormat(format);
		if (date == null) {
			return "";
		}
		return tempFormat.format(date);
	}

	public static String date2Str(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(YYYYMMDD).format(date);
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
	 * 描述：判断时间是否在指定的两个时间之间（可指定时间格式）
	 * 
	 * @author jerry.deng
	 * @date 2014-9-9下午6:48:25
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
			//logger.error(e.getMessage(), e);
			return false;
		}
	}

	
	/**
	 * 描述：判断时间是否在指定的两个时间之间（可指定时间格式）
	 * 
	 * @author GARY.TANG
	 * @date 2014-9-9下午6:48:25
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
			//logger.error(e.getMessage(), e);
			return false;
		}
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			//logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	// 判断当前时间是否在时间date2之前
	public static boolean isDateBefore(Date date2) {
		Date date1 = new Date();
		return date1.before(date2);
	}

	// 时间date 和当前时间加上nDay天即date2(当前时间) date>date2返回false date<date2返回false
	public static boolean compareDate(Date date, int nDay) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(getCurDate());
		curr.add(Calendar.DAY_OF_WEEK, nDay);
		Date currDate = curr.getTime();
		return date.before(currDate);
	}

	public static boolean compareDate2(Date date, int nHour) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(getCurDate());
		curr.add(Calendar.HOUR_OF_DAY, nHour);
		Date currDate = curr.getTime();
		return date.before(currDate);
	}

	/**
	 * 获得指定日期的后n年的日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getForOffsetYear(Date currentDate, int nYear) {
		if (currentDate == null) {
			return null;
		}
		Calendar curr = Calendar.getInstance();
		curr.setTime(currentDate);
		curr.add(Calendar.YEAR, nYear);
		return curr.getTime();
	}

	/**
	 * 获得指定日期的后n月的日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getForOffsetMonth(Date currentDate, int nMonth) {
		if (currentDate == null) {
			return null;
		}
		Calendar curr = Calendar.getInstance();
		curr.setTime(currentDate);
		curr.add(Calendar.MONTH, nMonth);
		return curr.getTime();
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 * @throws ParseException
	 * @author lyl
	 * @creation date Apr 20, 2011
	 */
	public static long getTimestamp() {
		long rand = System.currentTimeMillis();
		return rand;
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 * @author lyl
	 * @creation date Apr 20, 2011
	 */
	public static Date getTimesDate(long time) {
		Date nowTime = new Date(time);
		return nowTime;
	}

	/**
	 * @MethodName: getMonthFirstDay
	 * @Param: UtilDate
	 * @Author: zhangyong
	 * @Date: 2013-3-22 下午07:14:34
	 * @Return:
	 * @Descb: 获取当月的第一天
	 * @Throws:
	 */
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Calendar f = (Calendar) cal.clone();
		f.clear();
		f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		String firstday = new SimpleDateFormat("yyyy-MM-dd").format(f.getTime());
		firstday = firstday + " 00:00:00";
		return firstday;

	}

	/**
	 * @MethodName: getMonthLastDay
	 * @Param: UtilDate
	 * @Author: zhangyong
	 * @Date: 2013-3-22 下午07:14:41
	 * @Return:
	 * @Descb: 获取当月的最后一天
	 * @Throws:
	 */
	public static String getMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		Calendar l = (Calendar) cal.clone();
		l.clear();
		l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		l.set(Calendar.MILLISECOND, -1);
		String lastday = new SimpleDateFormat("yyyy-MM-dd").format(l.getTime());
		lastday = lastday + " 23:59:59";
		return lastday;
	}

	/**
	 * 当前日期
	 */
	public static String getCurrentDay() {
		return date2String(new Date(), yyyyMMdd);
	}

	/**
	 * 当前日期
	 */
	public static String getCurrentDateTime() {
		return date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期加上天数的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddDay(Date date, int day) {
		return add(date, Calendar.DAY_OF_YEAR, day);
	}

	/**
	 * 日期加上月数的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date date, int month) {
		return add(date, Calendar.MONTH, month);
	}

	/**
	 * 日期加上年数的时间
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date, int year) {
		return add(date, Calendar.YEAR, year);
	}

	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	/**
	 * 计算剩余时间 (多少天多少时多少分)
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String remainDateToString(Date startDate, Date endDate) {
		StringBuilder result = new StringBuilder();
		long times = endDate.getTime() - startDate.getTime();
		if (times < -1) {
			result.append("过期");
		} else {
			long temp = 1000 * 60 * 60 * 24;
			// 天数
			long d = times / temp;

			// 小时数
			times %= temp;
			temp /= 24;
			long m = times / temp;
			// 分钟数
			times %= temp;
			temp /= 60;
			long s = times / temp;

			result.append(d);
			result.append("天");
			result.append(m);
			result.append("小时");
			result.append(s);
			result.append("分");
		}
		return result.toString();
	}

	/**
	 * 计算两个日期之间间隔的天数,不足一天的舍弃
	 * 
	 * @param newDate
	 *            最新时间
	 * @param orgDate
	 *            原来时间
	 * @return 间隔天数
	 */
	public static int minusDate(Date newDate, Date orgDate) {
		Long newDateSeconds = newDate.getTime();
		Long orgDateSeconds = orgDate.getTime();
		Double intervalDay = (double) ((newDateSeconds - orgDateSeconds) / (60 * 60 * 24 * 1000));
		return (int) Math.floor(intervalDay);
	}

	/**
	 * 计算指定时间与当前时间的天数差
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static int minusCurDate(Date targetTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date targetDate;
		Date currentDate;
		Double intervalDay = 0.00;
		targetDate = df.parse(df.format(targetTime));
		currentDate = df.parse(df.format(getCurDate()));
		Long currentDateSeconds = currentDate.getTime();
		Long targetDateSeconds = targetDate.getTime();
		intervalDay = (double) ((targetDateSeconds - currentDateSeconds) / (60 * 60 * 24 * 1000));
		return (int) Math.floor(intervalDay);
	}

	/**
	 * 计算两个时间之间的天数差
	 * 
	 * @param earlyTime
	 *            较早的时间
	 * @param lateTime
	 *            较晚的时间
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
	 * 计算两个时间之间的天数差
	 * 
	 * @param earlyTime
	 *            较早的时间
	 * @param lateTime
	 *            较晚的时间
	 * @return 两者的天数差
	 * @throws ParseException
	 */
	public static int minusDateString(String earlyTime, String lateTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date earlyDate;
		Date lateDate;
		Double intervalDay = 0.00;
		earlyDate = df.parse(earlyTime);
		lateDate = df.parse(lateTime);
		Long earlyDateSeconds = earlyDate.getTime();
		Long lateDateSeconds = lateDate.getTime();
		intervalDay = (double) ((lateDateSeconds - earlyDateSeconds) / (60 * 60 * 24 * 1000));
		return (int) Math.floor(intervalDay);
	}

	public static String formatter(String date, String formatter) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(formatter);
		return df.parse(date).toString();
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static int getMonths(Date date1, Date date2) throws Exception {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1)) {
				return 0;
			}
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH)) {
				flag = 1;
			}
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			} else {
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return iMonth;
	}

	/**
	 * 计算当前日期与指定日期之间相差的月数，天数
	 * 
	 * @param date
	 *            指定日期,一定要在当前日期之前
	 * @param isDayOrMonth
	 *            1-表示天标，2-表示月标,如果是天标，则不用计算月数，直接算天数
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Integer> getMonthAndDay(Date auditTime, Date repayDate, String isDayOrMonth) throws Exception {
		Map<String, Integer> result = new HashMap<String, Integer>();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		String date = sdf.format(auditTime);
		String now = sdf.format(repayDate);
		if ("1".equals(isDayOrMonth)) {
			int day = minusDateString(date, now);
			result.put("day", day);
		} else {
			int month = getMonths(sdf.parse(date), sdf.parse(now));

			Calendar date1 = Calendar.getInstance();
			date1.setTime(sdf.parse(date));
			date1.add(Calendar.MONTH, month);

			int day = minusDateString(sdf.format(date1.getTime()), now);
			result.put("month", month);
			result.put("day", day);
		}
		return result;
	}
	
	/**
	 * 计算两个时间之间的天数差(精确到秒的计算)
	 * 
	 * @param earlyTime
	 *            较早的时间
	 * @param lateTime
	 *            较晚的时间
	 * @return 两者的天数差
	 * @throws ParseException
	 */
	public static double minusDateSecond(String earlyTime, String lateTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date earlyDate;
		Date lateDate;
		Double intervalDay = 0.00;
		earlyDate = df.parse(earlyTime);
		lateDate = df.parse(lateTime);
		Long earlyDateSeconds = earlyDate.getTime();
		Long lateDateSeconds = lateDate.getTime();
		intervalDay = (double) ((lateDateSeconds - earlyDateSeconds) * 1.00 / (60 * 60 * 24 * 1000));
		return intervalDay;
	}
	
	/**
	 * 计算当前日期与指定日期之间相差的月数，天数
	 * @param date 指定日期,一定要在当前日期之前
	 * @param isDayOrMonth 1-表示天标，2-表示月标,如果是天标，则不用计算月数，直接算天数
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Integer> getMonthAndDay(String date,String isDayOrMonth) throws Exception{
		Map<String, Integer> result = new HashMap<String, Integer>();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		String now = sdf.format(new Date());
		if("1".equals(isDayOrMonth)){
			int day = minusDateString(date,now);
			 result.put("day", day);
		}else{
			int month = getMonths(sdf.parse(date),sdf.parse(now));
	        
	        Calendar date1 = Calendar.getInstance();
	        date1.setTime(sdf.parse(date));
	        date1.add(Calendar.MONTH, month);
	        
	        int day = minusDateString(sdf.format(date1.getTime()),now);
	        result.put("month", month);
	        result.put("day", day);
		}
		return result;
	}

	public static String formatDate(Date date, String formatter) {
		SimpleDateFormat df = new SimpleDateFormat(formatter);
		return df.format(date);
	}
	
	/**
	 * 
	 * 描述：获取当明日期yyyy-MM-dd
	 * @author John 
	 * @date 2015年7月2日上午11:44:44
	 * @return
	 */
	public static Date getCurrentAsDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		String now = sdf.format(new Date());
		Date nowDate=null;
		try {
			 nowDate=sdf.parse(now);
		} catch (ParseException e) {
		}
		return nowDate;
	}
	
	/**
	 * 描述：清空时间时分秒
	 * @author huaping hu 
	 * @date 2016年5月20日下午5:44:41
	 * @param date
	 * @return
	 */
	public static Date clearHHmmss(Date date){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
	}
	
	/**
	 * 描述：清空时间时分秒毫秒
	 * @author huaping hu 
	 * @date 2016年5月20日下午5:44:41
	 * @param date
	 * @return
	 */
	public static Date clearHHmmssFFF(Date date){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(clearHHmmssFFF(new Date()));
	}
}
