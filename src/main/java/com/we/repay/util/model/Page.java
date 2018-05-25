/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Page
 * @version 1.0
 * @Desc: 分页公共类
 * @author huangsiping
 * @date 2016年3月14日上午11:39:01
 * @history v1.0
 *
 */
public class Page  {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1995679988901861684L;

	private Integer pageIndex; // 当前页数
	private Integer pageSize = 10; // 分页条数
	private String orderBy;// 排序字段
	
	private int start = 0;

	private int total = 0;
	
	@SuppressWarnings("rawtypes")
	private List data = new ArrayList();
	
	public Page() {

	}

	public Page(Integer pageSize, String orderBy) {
		this.pageSize = pageSize;
		this.orderBy = orderBy;
	}

	
	public Page(Integer pageIndex, Integer pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	
	
	public Page(Integer pageIndex, Integer pageSize, String orderBy,
			String orderSequeue) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.orderBy = orderBy;
	}

	@SuppressWarnings("rawtypes")
	public Page(int total, List data) {
		super();
		this.total = total;
		this.data = data;
	}

	
	@SuppressWarnings("rawtypes")
	public static Page instance(int total,List data){
		return new Page(total,data);
	}
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getStart() {
		if (pageIndex ==null || pageIndex == 0) {
			pageIndex = 1;
		}
		if(pageSize==null){
			this.start = -1;
		}else{
			this.start = (pageIndex - 1) * pageSize;
		}
		return this.start;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getData() {
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void setData(List data) {
		this.data = data;
	}
	
}
