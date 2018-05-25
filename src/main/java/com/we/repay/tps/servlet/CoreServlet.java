/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.we.repay.common.Constants;
import com.we.repay.controller.login.LoginController;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.dto.BaseMessageDTO;
import com.we.repay.tps.dto.TextMessageDTO;
import com.we.repay.tps.util.MessageUtil;
import com.we.repay.tps.util.Sha1SignatureUtil;



/**@ClassName: CoreServlet
 * @version 2.0
 * @Desc: 接收微信服务器传来信息
 * @author tianzhongshan
 * @date 2017年7月11日下午3:13:23
 * @history v2.0
 */ 
public class CoreServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//日志
	private final  Logger logger = Logger.getLogger(CoreServlet.class);
	
//	@Resource
//	private static WxAccountService wxAccountService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoreServlet() {
        super();
    }

    /**
	 * 描述：验证服务器地址的有效性，判断请求来自微信服务器
	 * @author tianzhongshan
	 * @date 2017年7月11日 15:14:26
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		
		//将token、timestamp、nonce三个参数进行字典序排序
		String[] source = new String[]{TPSConstants.TOKEN,timestamp,nonce};
		Arrays.sort(source);
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		boolean flag = Sha1SignatureUtil.checkSignature(source, signature);
		
		PrintWriter out = response.getWriter();
		if(flag){
			out.print(echostr);
			logger.info("验证服务器地址的有效性成功...");
		}else{
			logger.info("验证服务器地址的有效性失败,请检查...");
		}
		out.close();
		out = null;
	}

	/**
	 * 描述：处理微信服务器发来的消息
	 * @author tianzhongshan
	 * @date 2017年7月11日 15:14:26
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        logger.info("servlet_dopost");
        
        // 调用parseXml方法解析请求消息
        Map<String, String> requestMap = MessageUtil.parseXml(request);
        // 发送方帐号
        final String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        
        //返回消息对象
        BaseMessageDTO baseMessageDTO = null;

        //事件消息
        if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
        	String eventType = requestMap.get("Event");
        	//subscribe(订阅)
        	logger.info("事件类型："+eventType);
        	if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
        		
        		Runnable runnable = new Runnable(){  
                    @SuppressWarnings("unused")
					public void run(){  
                    	ServletContext servletContext = getServletContext();
                    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//                    	LoginController loginController = (LoginController)wac.getBean(LoginController.class);
                    	WxAccountService wxAccountService = (WxAccountService)wac.getBean(WxAccountService.class);
//                		loginController.addWxAccount(fromUserName);
                    	logger.info("调用关注");
                    	wxAccountService.registerAccount(fromUserName);
                    	

                    }
                  };
                //启动线程
            	new Thread(runnable).run();
            	// 回复文本消息
            	TextMessageDTO textMessage = new TextMessageDTO();
            	textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textMessage.setContent("欢迎关注...");
                //向上转型设置消息对象
                baseMessageDTO = textMessage;
        	}
        	
        	//取消订阅
        	if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
        		String key = Constants.USER_AUTH_KEY;
        		WxAccount wa = (WxAccount) request.getSession().getAttribute(key);
        		String waR = wa.toString();
        		
        		logger.info(">>>>>>>>>>>>>>>>>>>>>>>用户取消关注：" + waR);
//        		request.getSession().removeAttribute(Constants.USER_AUTH_KEY);
        		
        		/*if (userVo != null) {
					Integer result = userService.userLogoutDtm(userVo.getLulId());
					if (result > 0) {
						request.getSession().removeAttribute(Constants.USER_AUTH_KEY);
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>用户退出系统成功：" + userVo.getLoginName());
					}
				}*/
        		
        	}
        	
        }
        
        // 文本消息
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
        	// 回复文本消息
        	TextMessageDTO textMessage = new TextMessageDTO();
        	textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setContent("暂无图文消息...");
            //向上转型设置消息对象
            baseMessageDTO = textMessage;
        }
        
        //表示存在处理方式,设置公共参数
        if(baseMessageDTO!=null){
        	baseMessageDTO.setToUserName(fromUserName);
        	baseMessageDTO.setFromUserName(toUserName);
        	baseMessageDTO.setCreateTime(new Date().getTime());
        	// 将消息对象转换成xml
            String respXml = MessageUtil.messageToXml(baseMessageDTO);
            // 响应消息
            PrintWriter out = response.getWriter();
            try{
            	out.print(respXml);
            }finally{
            	if( out!=null){
            		out.close();
            	}
            }
        }
       
	}

}
