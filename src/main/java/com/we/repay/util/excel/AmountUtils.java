package com.we.repay.util.excel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额计算工具类
 */
public class AmountUtils {
	
	//默认除法运算精度
	private static final int DEFAULT_DIV_SCALE = 16; 
	
	/**
	 * 
	 * 描述：将数字转成String
	 * @author lizg 
	 * @date 2015-9-25下午3:43:24
	 * @param obj类型为String,int,Integer,long,Long,float,Fload,double,Double,BigDecimal,
	 * 其他的直接返回空字符串 由于float与Float数值过大时会有精度问题，建议计算时候少用，用BigDecimal代替
	 * @return
	 */
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof String || obj == int.class || obj instanceof Integer || obj == long.class || obj instanceof Long || obj instanceof BigDecimal) {
			return String.valueOf(obj);
		} else if (obj == float.class || obj instanceof Float || obj == double.class || obj instanceof Double) {
			/**
			 * 由于浮点型数字太大时会用科学计数法表示， 所以要将科学计数法转成数字
			 */
			String str = String.valueOf(obj);
			if (str.indexOf("E") == -1 && str.indexOf("e") == -1) {
				// 非科学计数法，直接返回
				return str;
			} else {
				String[] strs = null;
				if (str.indexOf("E") != -1) {
					strs = str.split("E");
				} else if (str.indexOf("e") != -1) {
					strs = str.split("e");
				}
				if (strs == null || strs.length < 2) {
					return "";
				}
				BigDecimal b = new BigDecimal(strs[0]);
				b = b.movePointRight(ConvertUtil.strToInteger(strs[1], 0));
				return b.toString();
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * 描述：将Object转成BigDecimal
	 * @author lizg 
	 * @date 2015-9-25下午3:44:31
	 * @param obj
	 * @return
	 */
	public static BigDecimal objToBigDecimal(Object obj) {
		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		} else {
			return new BigDecimal(objToString(obj));
		}
	}
	
	/**
	 * 
	 * 描述：两个数值相加,保留两位小数点. 相加规则：如果数值小数点超过两位，直接截取两位小数，不做小数点进位处理,然后再相加 先转成BigDecimal
	 * ，然后右移两位小数点。 再取其整数值相加,转成BigDecimal，左移两位小数点
	 * @author lizg 
	 * @date 2015-9-25下午3:46:33
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static BigDecimal add(Object num1, Object num2) {
		/**
		 * 先将两个数值转成String，再new BigDecimal，以防精度丢失
		 */
		BigDecimal bd1 = objToBigDecimal(num1);
		BigDecimal bd2 = objToBigDecimal(num2);
		/**
		 * 右移小数点
		 */
		bd1 = bd1.movePointRight(2);
		bd2 = bd2.movePointRight(2);
		/**
		 * 各取整数值相加
		 */
		long resultLong = bd1.longValue() + bd2.longValue();
		/**
		 * 转成BigDecimal，左移小数点
		 */
		BigDecimal result = new BigDecimal(String.valueOf(resultLong));
		result = result.movePointLeft(2);
		return result;
	}
	
	/**
	 * 两个数值相减,前面一个减去后面一个： num1-num2 相减规则：如果数值小数点超过两位，直接截取两位小数，不做小数点进位处理,然后再相减
	 * 先转成BigDecimal ，然后右移两位小数点。 再取其整数值相加,转成BigDecimal，左移两位小数点
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static BigDecimal subtract(Object num1, Object num2) {
		/**
		 * 先将两个数值转成String，再new BigDecimal，以防精度丢失
		 */
		BigDecimal bd1 = objToBigDecimal(num1);
		BigDecimal bd2 = objToBigDecimal(num2);
		/**
		 * 右移小数点
		 */
		bd1 = bd1.movePointRight(2);
		bd2 = bd2.movePointRight(2);
		/**
		 * 各取整数值相加
		 */
		long resultLong = bd1.longValue() - bd2.longValue();
		/**
		 * 转成BigDecimal，左移小数点
		 */
		BigDecimal result = new BigDecimal(String.valueOf(resultLong));
		result = result.movePointLeft(2);
		return result;
	}

	/**
	 * 保留两位小数，不管小数点后的第三位是多少都舍去
	 * @param obj类型为String,int,Integer,long,Long,float,Fload,double,Double,BigDecimal，其他的直接返回0.00
	 * 如果为String，也必须是数值型，否则也只返回0.00
	 * @return
	 */
	public static BigDecimal numberFormat(Object obj){
		/**
		 * 先将两个数值转成String，再new BigDecimal，以防精度丢失
		 */
		BigDecimal bd = objToBigDecimal(obj);
		/**
		 * 右移小数点
		 */
		bd = bd.movePointRight(2);
		/**
		 * 取整数
		 */
		long resultLong = bd.longValue();
		/**
		 * 转成BigDecimal，左移小数点
		 */
		BigDecimal result = new BigDecimal(String.valueOf(resultLong));
		result = result.movePointLeft(2);
		return result;
	}
	
	/**
	 * 保留两位小数，不管小数点后的第三位是多少都舍去
	 * @param obj类型为String,int,Integer,long,Long,float,Fload,double,Double,BigDecimal，其他的直接返回0.00
	 * 如果为String，也必须是数值型，否则也只返回0.00
	 * @return
	 */
	public static BigDecimal numberFormat(Object obj,int length){
		/**
		 * 先将两个数值转成String，再new BigDecimal，以防精度丢失
		 */
		BigDecimal bd = objToBigDecimal(obj);
		/**
		 * 右移小数点
		 */
		bd = bd.movePointRight(length);
		/**
		 * 取整数
		 */
		long resultLong = bd.longValue();
		/**
		 * 转成BigDecimal，左移小数点
		 */
		BigDecimal result = new BigDecimal(String.valueOf(resultLong));
		result = result.movePointLeft(length);
		return result;
	}
	
	/**
	 * 四舍五入
	 * @param amt
	 * @return
	 */
	public static BigDecimal sswrFormat(BigDecimal amt) {
		DecimalFormat frm = new DecimalFormat("#0.00");
		return AmountUtils.objToBigDecimal(frm.format(amt));
	}
	
	/**
	 * 除法运算，b1/b2
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1,BigDecimal b2){
		return b1.divide(b2,DEFAULT_DIV_SCALE,BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 利率计算，
	 * @param yearRate 年利率，分百分比
	 * @param isDayThe 1：计算天利率，2：计算月利率
	 * @return
	 */
	public static BigDecimal rate(BigDecimal yearRate,int isDayThe){
		BigDecimal baseBig = null;
		if(isDayThe == 1){
			baseBig = new BigDecimal(360 * 100);
		}else if(isDayThe == 2){
			baseBig = new BigDecimal(12 * 100);
		}
		return divide(yearRate,baseBig);
	}
}
