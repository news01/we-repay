/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.we.repay.common.Constants;


/**@ClassName: XmlToJSONUtil
 * @version 2.0
 * @Desc: 读取微信自定义菜单XML文件
 * @author tianzhongshan
 * @date 2017年7月11日下午4:48:56
 * @history v2.0
 */ 
public class WXMenuXmlToJSON {
	
	//日志
	public static Logger logger = Logger.getLogger(WXMenuXmlToJSON.class);
	
	/**
	 * 描述：将xml文件转换成json字符串
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午4:49:04
	 * @param fileUrl classes下xml文件路径
	 * @return json字符串
	 */
	public static JSONObject xmlToJson(String fileUrl){
		URL realFileUrl = Thread.currentThread().getContextClassLoader().getResource(fileUrl);
		JSONObject json = new JSONObject();
		SAXReader saxReader = new SAXReader();
		try {
			Document read = saxReader.read(realFileUrl);
			Element rootElement = read.getRootElement();
			Map<String, Object> iteratorElement = iteratorElement(rootElement);
			json.put(rootElement.getName(), iteratorElement);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.equals(e);
		}
		return json;
	}
	
	/**
	 * 描述：迭代element元素节点
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午4:50:56
	 * @param element 元素节点
	 * @return 节点及子节点集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> iteratorElement(Element element){
		List<Element> elements = element.elements();//获取元素节点下的子节点集合
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		for (Element currElement : elements) {
			String currElementName = currElement.getName();
			List<Element> tempElement = currElement.elements();
			if(tempElement.size()>0){//当前节点存在子节点
				Map<String, Object> iteratorElement = iteratorElement(currElement);//递归取子节点的所有子节点
				if(jsonMap.containsKey(currElementName)){//集合中是否存在节点为key的值
					Object currElementVal = jsonMap.get(currElementName);
					Collection<Object> collection = null;
					if(currElementVal instanceof Collection){//节点的值已经是集合类型时
						collection = (Collection<Object>) currElementVal;
						collection.add(iteratorElement);
					}else{
						//节点的值不为集合时创建集合存储多个相同节点
						collection = new ArrayList<Object>();
						collection.add(jsonMap.get(currElement.getName()));
						collection.add(iteratorElement);
					}
					jsonMap.put(currElementName, collection);
				}else{
					if("button".equalsIgnoreCase(currElementName)||"sub_button".equalsIgnoreCase(currElementName)){
						Collection<Object> collection = new ArrayList<Object>();
						collection.add(iteratorElement);
						jsonMap.put(currElementName, collection);
					}else{
						jsonMap.put(currElementName, iteratorElement);
					}
				}
			}else{
				String textTrim = currElement.getTextTrim();
				if("url".equalsIgnoreCase(currElementName)){//为URL则拼接服务地址
					if(textTrim.indexOf("http://")==-1 && textTrim.indexOf("https://")==-1){
						textTrim = Constants.BASE_PATH+textTrim;
					}
				}
				jsonMap.put(currElementName, textTrim);
			}
		}
		return jsonMap;
	}
	
	
	public static void main(String[] args) {
//		JSONObject xmlToJson = XmlToJSONUtil.xmlToJson("other/wechat_service_menu.xml");
//		System.out.println(xmlToJson.get("menu").toString());
	}
}
