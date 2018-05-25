/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.dto;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * @ClassName: QueryReqDTO
 * @version 1.0 
 * @Desc: 请求DTO对象
 * @author John Gu
 * @date 2017年6月12日下午5:52:21
 * @history v1.0
 *
 */
@SuppressWarnings("serial")
public class QueryReqDTO implements java.io.Serializable{
	private Integer pageNumber; // 当前页数
	private Integer pageSize = 10; // 分页条数
	private String orderField;// 排序字段
	private String orderSequeue = "asc";// 升序/降序
	
	private Integer start ;
	
	private List<Long> authUsers;

	public QueryReqDTO() {
	}

	public QueryReqDTO(int pageSize, String orderField) {
		this.pageSize = pageSize;
		this.orderField = orderField;
	}


	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderSequeue() {
		return orderSequeue;
	}

	public void setOrderSequeue(String orderSequeue) {
		this.orderSequeue = orderSequeue;
	}


	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		
		if (pageNumber == null) {
			return null;
		}
		if (pageNumber == 0) {
			pageNumber = 1;
		}
		this.start = (pageNumber - 1) * pageSize;
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String toJSONString(){
		return JSONObject.fromObject(this).toString();
	}

	public List<Long> getAuthUsers() {
		return authUsers;
	}

	public void setAuthUsers(List<Long> authUsers) {
		this.authUsers = authUsers;
	}

}
