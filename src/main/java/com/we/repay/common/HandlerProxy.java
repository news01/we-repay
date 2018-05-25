/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.we.repay.dto.QueryReqDTO;
import com.we.repay.po.user.WxAccount;
import com.we.repay.util.BeanCopyUtil;
import com.we.repay.util.WebParamUtils;
import com.we.repay.util.json.JSONUtil;
import com.we.repay.util.model.ResultVo;



/**
 * @ClassName: HandlerProxy
 * @version 1.0
 * @Desc: 前端handle代理（抽象）
 * @author John Gu
 * @date 2017年6月10日上午11:10:32
 * @history v1.0
 *
 */
public class HandlerProxy {

	private static Logger LOGGER = Logger.getLogger(HandlerProxy.class);

	/**
	 * 
	 * 描述：同步请求处理
	 * 
	 * @author John Gu
	 * @date 2017年6月10日下午6:12:44
	 * @param handler
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public static ModelAndView assemble(ControlHandler handler, HttpServletRequest request, HttpServletResponse response) {

		try {
			// 过滤检查
			return handler.handler(request, response,getUserInfo(request),null);
		} catch (Exception e) {
			LOGGER.error("系统发生错误", e);
			ModelAndView mav = new ModelAndView(Constants.BASE_PATH+"/jsp/module/exception/error.jsp");
			return mav;
		}
	}

	/**
	 * 
	 * 描述：异步请求处理
	 * 
	 * @author John Gu
	 * @date 2017年6月10日下午6:12:01
	 * @param handler
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public static void assembleAjax(ControlHandler handler,HttpServletRequest request, HttpServletResponse response) {
		
		ResultVo resultVo = ResultVo.instance();
		try {
			handler.handler(request, response, getUserInfo(request),resultVo);
		} catch (Exception e) {
			LOGGER.error("系统发生错误", e);
			resultVo.setSystemError();
		}
		outData(JSONUtil.toFormatJSON(resultVo),response);
	}

	/**
	 * 
	 * 描述：异步请求表格数据处理
	 * 
	 * @author John Gu
	 * @date 2017年6月10日下午6:11:05
	 * @param handler
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public static ModelAndView assembleAjaxGrid(ControlHandler handler,HttpServletRequest request, HttpServletResponse response,QueryReqDTO queryReqDTO) {
		
		ResultVo resultVo = ResultVo.instance();
		try {
			pageConditionWrap(request, queryReqDTO);
			handler.handler(request, response, getUserInfo(request),resultVo);
		} catch (Exception e) {
			LOGGER.error("系统发生错误", e);
			resultVo.setSystemError();
		}
		outData(JSONUtil.toFormatJSON(resultVo),response);
		
		return null;
	}

	
	private static void pageConditionWrap(HttpServletRequest request,QueryReqDTO queryReqDTO) {
		Integer page = WebParamUtils.getInteger("page", request);
        Integer pageSize = WebParamUtils.getInteger("rows", request);
        String sortFiled = request.getParameter("sort") ;
        String order = request.getParameter("order") ;
		
  		String queryParams = request.getParameter("queryParams");
		//搜索
		if(StringUtils.isNotEmpty(queryParams)){
			QueryReqDTO paraDTO =  JSONUtil.toBean(queryParams, queryReqDTO.getClass());
			BeanCopyUtil.copyBean(paraDTO, queryReqDTO);
		}
		
		queryReqDTO.setPageNumber(page);
		queryReqDTO.setPageSize(pageSize);
		
		//表格排序
		if(StringUtils.isNotEmpty(sortFiled)){
			
			queryReqDTO.setOrderField( sortFiled );//排序字段
			queryReqDTO.setOrderSequeue(order);//排序方式
		}
	}
	
	/**
	 * 
	 * 描述：同步请求表格数据处理
	 * 
	 * @author John Gu
	 * @date 2017年6月10日下午6:11:05
	 * @param handler
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public static ModelAndView assembleGrid(ControlHandler handler,HttpServletRequest request, HttpServletResponse response) {
		try {
			return handler.handler(request, response, getUserInfo(request),null);
		} catch (Exception e) {
			LOGGER.error("系统发生错误", e);
			ModelAndView mav = new ModelAndView(Constants.BASE_PATH+"/jsp/module/exception/error.jsp");
			return mav;
		}
	}
	
	
	/**
	 * 描述：request中获取用户信息
	 * @author tianzhongshan 
	 * @date 2017年8月11日下午1:45:20
	 * @param request
	 * @return
	 */
	private static WxAccount getUserInfo(HttpServletRequest request){
		// 基础数据User
		WxAccount user = (WxAccount) request.getSession().getAttribute(Constants.USER_AUTH_KEY);
		return user;
	}
	
	/**
	 * 
	 * 描述：输出数据
	 * @author Jack
	 * @date 2017年5月24日上午11:09:23
	 * @param data
	 * @param response
	 */
	private static void outData(String data, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			// 输出数据
			writer = response.getWriter();
			writer.print(data);
		} catch (IOException e) {
			LOGGER.error("输出数据出错", e);
			e.printStackTrace();
		} finally {
			// 关闭流
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
	
}
