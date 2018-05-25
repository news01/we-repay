/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.we.repay.util.WebParamUtils;
import com.we.repay.util.json.JSONUtil;
import com.we.repay.util.model.Page;


/**
 * @ClassName: BaseController
 * @version 2.0
 * @Desc: 公共的方法
 * @author Jack
 * @date 2017年5月24日上午10:57:55
 * @history v2.0
 *
 */
public class BaseController  extends MultiActionController{

	private final  Logger logger = Logger.getLogger(BaseController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return super.handleRequestInternal(request, response);
	}

    
    @SuppressWarnings("unchecked")
	protected Map<String,Object> getPageCondition(HttpServletRequest request) {
		
         /**
          * 分页及排序参数
          */
         Integer pageIndex = WebParamUtils.getInteger("page", request);
         Integer pageSize = WebParamUtils.getInteger("rows", request);
         String sortFiled = request.getParameter("sort") ;
         String order = request.getParameter("order") ;
         
         Page page = new Page(pageIndex,pageSize,sortFiled,order);
         
         Map<String,Object> condition = new HashMap<String, Object>();
         condition.put("page", page);
         
        String queryParams = WebParamUtils.getString("queryParams", request);
        
        if(StringUtils.isNotEmpty(queryParams)){
     	  
        	condition.putAll( JSONUtil.toBean(queryParams, Map.class));
        }
        return condition;
	}

    
    
	/**
	 * 获取request
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	
	/**
	 * 获取ip地址
	 * @return
	 */
	protected String getIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("http_remote_user_ip");
		String localIP = "127.0.0.1";
		//logger.info("-------------------http_remote_user_ip:" + ip);
		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
			//logger.info("-------------------x-forwarded-for:" + ip);
		}
		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			//logger.info("-------------------Proxy-Client-IP:" + ip);
		}
		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			//logger.info("-------------------WL-Proxy-Client-IP:" + ip);
		}
		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			//logger.info("-------------------RemoteAddr:" + ip);
		}
		return ip;
	}

	/**
	 * 获取请求属性封装为Map类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected HashMap<String, Object> getRequestMapSingle(HttpServletRequest request) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		for (Object o : map.keySet()) {
			String key = (String) o;
			conditions.put(key, ((String[]) map.get(key))[0]);
		}
		return conditions;
	}
	
	/**
	 * 描述：检查本地保存sessionId的cookie是否存在
	 * @author tianzhongshan 
	 * @date 2017年9月7日下午5:17:22
	 * @return 存在-- Cookie 不存在---null
	 */
	public static Cookie isSesessionIdExist(HttpServletRequest request){
		// 已经登录过用户
		Cookie cookies[] = request.getCookies();
		Cookie sessionCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie currCookie = cookies[i];
				if ("JSESSIONID".equals(currCookie.getName())) {
					sessionCookie = currCookie;
					break;
				}
			}
		}
		return sessionCookie;
	}
	
	
	/**
	 * 
	 * 描述：输出数据
	 * @author Jack
	 * @date 2017年5月24日上午11:12:08
	 * @param data
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	protected void outJSONData(Object data, HttpServletResponse response) {
		String jsonData = "";
		if (data instanceof List) {
			jsonData = JSONUtil.toFormatJSON((List) data);
		} else {
			jsonData = JSONUtil.toFormatJSON(data);
		}
		outData(jsonData, response);
	}

	/**
	 * 
	 * 描述：输出数据
	 * @author Jack
	 * @date 2017年5月24日上午11:09:23
	 * @param data
	 * @param response
	 */
	private void outData(String data, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			// 输出数据
			writer = response.getWriter();
			writer.print(data);
		} catch (IOException e) {
			logger.error("输出数据出错", e);
			e.printStackTrace();
		} finally {
			// 关闭流
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	
	/**
	 * 
	 * 描述：转化成表格数据
	 * @author huaping hu 
	 * @date 2017年5月31日上午11:18:20
	 * @param page
	 */
	@SuppressWarnings("rawtypes")
	protected String toGridData(Page page){
		
		//封装参数
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		if(page !=null){
			
			dataMap.put("total", page.getTotal());
			dataMap.put("rows", page.getData()==null ? new ArrayList() :page.getData());

		}
		return JSONUtil.toJSON(dataMap, JSONUtil.formatNullConfig());
	}
}
