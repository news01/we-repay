/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.we.repay.common.IDGenerateStrategy;


/**
 * @ClassName: AutoSetGWTimeJob
 * @version 1.0 
 * @Desc: 定时获取MaxID
 * @author Jack
 * @date 2017年11月27日上午10:43:11
 * @history v2.0
 *
 */
public class UpdateCCUSerIdJob extends TimerTask{
	
	private Logger LOGGER = Logger.getLogger(UpdateCCUSerIdJob.class);

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		IDGenerateStrategy.instance().synIDCodeFromDB();
		long end = System.currentTimeMillis();
		LOGGER.info(">>>>>>>>>>>>>>>>定时执行耗时【"+(end - start)+"】毫秒！");
    }
	
}
