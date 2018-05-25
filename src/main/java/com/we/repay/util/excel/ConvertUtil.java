package com.we.repay.util.excel;

public class ConvertUtil {

	/**
	 * 字符串转成Long,出错是返回-1
	 * 
	 * @param str
	 * @return
	 */
	public static long strToLong(String str, long defaultValue) {
		long result = defaultValue;
		try {
			result = Long.parseLong(str);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 字符串转成int 出错是返回-1
	 * 
	 * @param str
	 * @return
	 */
	public static Integer strToInteger(String str, Integer defaultValue) {
		Integer result = defaultValue;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultValue
	 *            默认值
	 * @return 转换后的字符串
	 */
	public static String strToStr(String str, String defaultValue) {
		String result = str;
		if (str == null||"".equals(str.trim())) {
			result = defaultValue;
		} else {
			result = str.trim();
		}
		return result;
	}

	public static Double strToDouble(String str, double defaultValue) {
		Double result = defaultValue;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {

		}
		return result;
	}
}