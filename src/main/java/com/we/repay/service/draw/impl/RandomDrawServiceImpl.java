/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.draw.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.repay.dao.draw.RandomDrawMapper;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.draw.ModifyRandomDrawReqDTO;
import com.we.repay.dto.draw.QueryRandomDrawReqDTO;
import com.we.repay.dto.draw.RandomDrawReqDTO;
import com.we.repay.po.draw.RandomDraw;
import com.we.repay.service.draw.RandomDrawService;

/**
 * @ClassName: RandomDrawServiceImpl
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年12月15日下午3:12:11
 * @history v2.0
 *
 */
@Service("randomDrawService")
public class RandomDrawServiceImpl implements RandomDrawService {

	@Autowired
	private RandomDrawMapper randomDrawMapper;

	@Override
	public int addRandomDraw(RandomDraw randomDraw) {
		randomDraw.setCrtDtm(new Date());
		return randomDrawMapper.insertSelective(randomDraw);
	}

	@Override
	public int modifyRandomDrawById(RandomDraw randomDraw) {
		randomDraw.setModifyDtm(new Date());
		return randomDrawMapper.updateByPrimaryKeySelective(randomDraw);
	}

	@Override
	public int deleteRandomDrawById(Long rdId) {
		return randomDrawMapper.deleteByPrimaryKey(rdId);
	}

	@Override
	public RandomDraw queryRandomDrawById(Long rdId) {
		return randomDrawMapper.selectByPrimaryKey(rdId);
	}

	@Override
	public List<RandomDraw> queryRandomDrawList(RandomDraw randomDraw) {
		return randomDrawMapper.selectListSelective(randomDraw);
	}

	@Override
	public int modifyRandomDrawByIdList(
			ModifyRandomDrawReqDTO modifyRandomDrawReqDTO) {
		modifyRandomDrawReqDTO.setModifyDtm(new Date());
		return randomDrawMapper.updateByIdList(modifyRandomDrawReqDTO);
	}

	@Override
	public QueryRespDTO<RandomDraw> queryRandomDrawPage(
			RandomDrawReqDTO randomDrawReqDTO) {
		QueryRespDTO<RandomDraw> result = new QueryRespDTO<RandomDraw>();
		int total = randomDrawMapper.selectPageCountSelective(randomDrawReqDTO);
		if(total > 0){
			List<RandomDraw> list = randomDrawMapper.selectPageSelective(randomDrawReqDTO);
			result.setRows(list);
			result.setTotal(total);
		}
		return result;
	}

	@Override
	public int queryAttribute1Max() {
		return randomDrawMapper.selectAttribute1Max();
	}

	@Override
	public List<RandomDraw> queryRandomDrawByRdIdList(
			QueryRandomDrawReqDTO queryRandomDrawReqDTO) {
		return randomDrawMapper.selectByRdIdList(queryRandomDrawReqDTO);
	}
	
}
