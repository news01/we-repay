/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.servlet;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.we.repay.task.UpdateCCUSerIdJob;
import com.we.repay.tps.task.AccessTokenTask;


/**@ClassName: InitServiceServlet
 * @version 2.0
 * @author tianzhongshan
 * @date 2017年7月11日下午4:39:07
 * @history v2.0
 */ 
public class InitServiceServlet extends HttpServlet {
	
	private final  Logger logger = Logger.getLogger(InitServiceServlet.class);

	private static final long serialVersionUID = 1L;
       
	@Override
	public void init() throws ServletException {
		
		super.init();
		try{
			// 启动定时获取access_token的线程
	        new Thread(new AccessTokenTask()).start();
		}catch(Exception e){
			logger.error(">>>>>>>>>>【定时获取微信access_token的线程发生异常：】",e);
		}
		
		try{
		    UpdateCCUSerIdJob asgt = new UpdateCCUSerIdJob();
			Timer timer = new Timer();
			long daySpan = 24 * 60 * 60 * 1000;//24 * 60 * 60 * 1000;//间隔时间
			Calendar startDate = Calendar.getInstance();
		    //设置开始执行的时间为 某年-某月-某月 00:00:00 23:30 查一次
		    startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 02, 00, 00);
			timer.schedule(asgt,startDate.getTime(),daySpan);
		}catch(Exception e){
			logger.error(">>>>>>>>>>【定时更新APP_ID线程发生异常：】",e);
		}
        
        
       /* try {
			IDGenerateStrategy.instance().initIDCodeFromDB();
		} catch (InterruptedException e) {
			logger.error(">>>>>>>>>>【初始化APP_ID线程发生异常：】",e);
		}*/
	}

}
