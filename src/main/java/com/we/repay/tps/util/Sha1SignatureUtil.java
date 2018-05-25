/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.we.repay.util.StringUtil;



/**@ClassName: CheckSignatureUtil
 * @version 2.0
 * @Desc: 检验signature 工具类 
 * @author tianzhongshan
 * @date 2017年7月11日下午3:00:06
 * @history v2.0
 */ 
public class Sha1SignatureUtil {
	
	//日志
	public static Logger logger = Logger.getLogger(Sha1SignatureUtil.class);
	
	/**
	 * 描述：校验sha1加密
	 * @author tianzhongshan 
	 * @date 2017年8月11日上午11:33:14
	 * @param source  生成加密源数组
	 * @param ciphertext  待验证密文
	 * @return
	 */
	public static boolean checkSignature(String[] source,String ciphertext){
		boolean flag = false;
		if(!StringUtil.isEmpty(ciphertext)){
			String currCiphertext = sha1Encryption(source);
			logger.info("微信加密签名:"+ciphertext+"===待加密数组:"+JSONArray.fromObject(source).toString()+"生成加密签名后:"+currCiphertext);
			flag = ciphertext.toUpperCase().equals(currCiphertext);
		}
		return flag;
	}
	
	/**
	 * 描述：sha1加密
	 * @author tianzhongshan 
	 * @date 2017年8月11日上午11:33:14
	 * @param source  生成加密源数组
	 * @return 加密密文
	 */
	public static String sha1Encryption(String[] source){
		String ciphertext = "";
		if(source!=null && source.length>0){
			// 将参数字符串拼接成一个字符串进行sha1加密
			StringBuffer content = new StringBuffer();
			for (String temp : source) {
				content.append(temp);
			}
			 // 将参数字符串进行sha1加密
			ciphertext = getSha1(content.toString());
		}
		return ciphertext;
	}
	
	
	/**
	 * 描述：字符串 sha1 加密
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午3:16:12
	 * @param str 需加密字符串 
	 * @return 加密后字符串
	 */
	public static String getSha1(String str){
	   if (null == str || 0 == str.length()){
	        return null;
	    }
	   String byteToStr = "";
	   try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(str.getBytes("UTF-8"));
			byte[] digest = md.digest();
			byteToStr = byteToStr(digest);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteToStr;
	}
	

    /**
     * 描述：将字节数组转换为十六进制字符串
     * @author tianzhongshan 
     * @date 2017年7月11日下午3:16:30
     * @param byteArray 字节数组
     * @return 十六进制字符串
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (byte mByte : byteArray) {
    	    strDigest += byteToHexStr(mByte);
        }
        return strDigest;
    }


    /**
     * 描述：将字节转换为十六进制字符串
     * @author tianzhongshan 
     * @date 2017年7月11日下午3:16:46
     * @param mByte 字节
     * @return 十六进制字符串
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
	
}
