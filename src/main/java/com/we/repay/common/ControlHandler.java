/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.we.repay.po.user.WxAccount;
import com.we.repay.util.model.ResultVo;

/**
 * @ClassName: XXXControlHandler
 * @version 1.0 
 * @Desc: 前端控制业务处理handler
 * @author John Gu
 * @date 2017年6月10日上午11:14:36
 * @history v1.0
 *
 */
public interface ControlHandler {
	/**
	 * 
	 * 描述：业务handler
	 * @author John Gu 
	 * @date 2017年6月10日上午11:16:17
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	ModelAndView handler(HttpServletRequest request,HttpServletResponse response,WxAccount wxAccount,ResultVo resultVo) throws Exception;
	

}
