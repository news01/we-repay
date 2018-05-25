/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.draw;

import java.util.List;

import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.draw.ModifyRandomDrawReqDTO;
import com.we.repay.dto.draw.QueryRandomDrawReqDTO;
import com.we.repay.dto.draw.RandomDrawReqDTO;
import com.we.repay.po.draw.RandomDraw;

/**
 * @ClassName: RandomDrawService
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年12月15日下午3:11:55
 * @history v2.0
 *
 */
public interface RandomDrawService {
	
	public int addRandomDraw(RandomDraw randomDraw);
	
	public int modifyRandomDrawById(RandomDraw randomDraw);
	
	public int deleteRandomDrawById(Long rdId);
	
	public RandomDraw queryRandomDrawById(Long rdId);
	
	public List<RandomDraw> queryRandomDrawList(RandomDraw randomDraw);
	
	public int modifyRandomDrawByIdList(ModifyRandomDrawReqDTO modifyRandomDrawReqDTO);
	
	public QueryRespDTO<RandomDraw> queryRandomDrawPage(RandomDrawReqDTO randomDrawReqDTO);
	
	public int queryAttribute1Max();
	
	public List<RandomDraw> queryRandomDrawByRdIdList(QueryRandomDrawReqDTO queryRandomDrawReqDTO);
}
