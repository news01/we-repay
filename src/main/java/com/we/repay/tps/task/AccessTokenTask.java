/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.task;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.we.repay.common.Constants;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.service.WXCustomMenu;
import com.we.repay.tps.util.HttpsClientUtil;





/**@ClassName: AccessTokenTask
 * @version 2.0
 * @Desc: 定时获取微信access_token的线程
 * @author tianzhongshan
 * @date 2017年7月11日下午4:37:03
 * @history v2.0
 */ 
public class AccessTokenTask implements Runnable{

	 // 记录日志
	private static Logger log = Logger.getLogger(AccessTokenTask.class);
	//设置自定义菜单是否执行获取
	private AtomicBoolean statusMenu = new AtomicBoolean(true);
	//设置jsapi_ticket是否执行获取
	private AtomicBoolean status = new AtomicBoolean(false);
	
	//access_token是公众号的全局唯一票据 有效期目前为2个小时
	public static String accessToken = null;
			
	@Override
	public void run() {
		
		Thread tJSAPI_TICKET = null;
		
		//定义新线程获取JSSDK使用权限签名jsapi_ticket
		Runnable runnable = new Runnable(){  
            public void run(){  
            	while(status.get()){
            		try{
            			JSONObject jsonJsapiTicket = GetJsapiTicket();
	            		String ticket = (String) jsonJsapiTicket.get("ticket");
	            		if(ticket!=null){
	            			TPSConstants.JSAPI_TICKET = ticket;
	            			Constants.INNOJSAPI_TICKET = ticket;
	            			status.compareAndSet(true,false);
	            			log.info("获取jsapi_ticket成功   jsapi_ticket:{"+ticket+"}");
	            		}else{
	            			// 如果获取access_token失败,60秒后重新获取
	            			log.error("{获取jsapi_ticket失败,60秒后重新获取}");
							Thread.sleep(60 * 1000);
	            		}
            		}catch(Exception e){
            			 try {
         	            	// 如果获取access_token失败,60秒后重新获取
            				 log.error("{获取jsapi_ticket失败,60秒后重新获取}", e);
         					Thread.sleep(60 * 1000);
         				  } catch (InterruptedException e1) {
         					e1.printStackTrace();
         				  }
            		}
            	}
            }
          };
          
		while(true){
			try{
				//默认设置不执行jsapi_ticket
				status.set(false);
				//获取公众号的全局唯一接口调用凭据access_token
				JSONObject jsonAccessToken = GetAccessToken();
				if(jsonAccessToken!=null){
					accessToken = (String) jsonAccessToken.get("access_token");
					log.info("获取token:"+accessToken);
					if(accessToken!=null){
						  Integer expiresIn = (Integer) jsonAccessToken.get("expires_in");
						  log.info("获取access_token成功，有效时长"+expiresIn+"秒   token:{"+accessToken+"}");

						  //启动新线程获取JSSDK使用权限签名jsapi_ticket
						  try{
							  status.set(true);
							  if(tJSAPI_TICKET==null||!tJSAPI_TICKET.isAlive()){
								  tJSAPI_TICKET = new Thread(runnable);
								  tJSAPI_TICKET.start();
							  }
						  }catch(Exception e){
							  
						  }
						  //设置自定义菜单
						  if(statusMenu.get()){
							  //自定义菜单
						        WXCustomMenu customMenu = new WXCustomMenu();
						        customMenu.createCustomMenu();
						        statusMenu.set(false);
						  }
						  
						  // 休眠7000秒
		                  Thread.sleep((expiresIn-200)*1000);
					}else{
						// 如果获取access_token失败,60秒后重新获取
						log.error("{获取access_token失败,60秒后重新获取}");
						Thread.sleep(60 * 1000);
					 }
				}else{
					// 如果获取access_token失败,60秒后重新获取
					log.error("{获取access_token失败,60秒后重新获取}");
					Thread.sleep(60 * 1000);
				 }
				  
			}catch(Exception e){
	             try {
	            	// 如果获取access_token失败,60秒后重新获取
	            	log.error("{获取access_token失败,60秒后重新获取}", e);
					Thread.sleep(60 * 1000);
				  } catch (InterruptedException e1) {
					e1.printStackTrace();
				  }
			}
			
			
			
		}
	}
	
	/**
	 * 描述：获取公众号的全局唯一接口调用凭据access_token
	 * @author tianzhongshan 
	 * @date 2017年8月11日下午2:46:45
	 * @return
	 */
	public JSONObject GetAccessToken(){
		  String requestUrl = MessageFormat.format(TPSConstants.GETACCESSTOKEN, 
													TPSConstants.APPID,
													TPSConstants.APPSECRET);
		  HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
		  JSONObject jsonobject = httpsClientUtil.sendGetRequestJson(requestUrl);
		return jsonobject;
	}
	
	/**
	 * 描述：JSSDK使用权限签名jsapi_ticket
	 * @author tianzhongshan 
	 * @date 2017年8月11日下午2:46:45
	 * @return
	 */
	public JSONObject GetJsapiTicket(){
		String requestUrl = MessageFormat.format(TPSConstants.GETJSAPITICKET, 
				accessToken);
		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
		JSONObject jsonobject = httpsClientUtil.sendGetRequestJson(requestUrl);
		return jsonobject;
	}
	
}
