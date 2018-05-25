/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.we.repay.controller.repay.RepayRecordController;

/**@ClassName: UpdateRepayRecordStatus
 * @version 1.0
 * @Desc: 定时更新支付记录状态
 * @author tianzhongshan
 * @date 2017年10月9日下午4:15:14
 * @history v1.0
 */ 
public class UpdateRepayRecordStatus {

	@Autowired
	private RepayRecordController repayRecordController;
	
	/**
	 * 描述：定时更新支付记录状态
	 * @author tianzhongshan 
	 * @date 2017年10月10日上午10:31:28
	 */
	public void start(){
		
		repayRecordController.UpdateRepayStatus();
		
	}
	
}
