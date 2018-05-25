/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util.model;

import java.util.ArrayList;
import java.util.List;

import com.we.repay.common.ErrorCode;
import com.we.repay.dto.QueryRespDTO;


/**
 * @ClassName: ResultVo
 * @version 1.0 
 * @Desc: 前台结果值对象；
 * @author huaping hu
 * @date 2016年7月20日上午9:04:46
 * @history v1.0
 *
 */
public class ResultVo {

	/**
	 * 错误代码
	 */
	private Integer errCode ;
	
	/**
	 * 错误消息
	 */
	private String errMsg ;
	
	/**
	 * 数据
	 */
	private Object result;
	
	/**
	 * 兼容表格
	 */
	private Integer total =0;
	/**
	 * 兼容表格
	 */
	private List<Object> rows = new ArrayList<Object>();

	/**
	 * 无参构造方法
	 */
	public ResultVo(){
		this.errCode = 0;
		this.errMsg = "success";
	}
	
	/**
	 * 有参构造方法
	 */
	public ResultVo(Integer errorCode, String errorMsg) {
		super();
		this.errCode = errorCode;
		this.errMsg = errorMsg;
	}

	/**
	 * 
	 * 描述：设置为无访问权限错误
	 * @author huaping hu 
	 * @date 2016年7月20日下午1:55:42
	 */
	public void setInvalidRigth(){
		this.errCode = ErrorCode.INVALID_RIGHT_CODE;
		this.errMsg = ErrorCode.INVALID_RIGHT_MSG;
	}
	
	/**
	 * 
	 * 描述：设置系统出错
	 * @author huaping hu 
	 * @date 2016年7月20日下午1:56:13
	 */
	public void setSystemError(){
		this.errCode = ErrorCode.SYS_CODE_MISTAKE;
		this.errMsg  =  ErrorCode.SYS_MSG_MISTAKE;
	}
	
	/**
	 * 描述：设置session失效出错
	 * @author tianzhongshan 
	 * @date 2017年7月27日下午3:54:20
	 */
	public void setSessionInvalid(){
		this.errCode = ErrorCode.SESSION_INVALID_CODE;
		this.errMsg  =  ErrorCode.SESSION_INVALID_MSG;
	}
	
	/**
	 * 描述：设置自定义问题
	 * @author huaping hu 
	 * @date 2016年7月21日上午9:18:22
	 * @param code 代码 ;如果为null则赋值为-1
	 * @param msg 消息
	 */
	public void setCustomError(Integer code,String msg){
		this.errCode = code==null?-1:code;
		this.errMsg  =  msg;
	}
	
	/**
	 * 
	 * 描述 获取默认ResusltVo 此对象是正确对象
	 * @author huaping hu 
	 * @date 2016年7月20日下午2:22:50
	 * @return
	 */
	public static ResultVo instance(){
		
		return new ResultVo();
	}
	
	/**
	 * 
	 * 描述：获取无访问权限错误
	 * @author huaping hu 
	 * @date 2016年7月20日下午1:55:42
	 */
	public static  ResultVo getInvalidRigth(){
		return new ResultVo(ErrorCode.INVALID_RIGHT_CODE, ErrorCode.INVALID_RIGHT_MSG);
	}
	
	/**
	 * 
	 * 描述：设置系统出错
	 * @author huaping hu 
	 * @date 2016年7月20日下午1:56:13
	 */
	public static ResultVo getSystemError(){
		return new ResultVo(ErrorCode.SYS_CODE_MISTAKE, ErrorCode.SYS_MSG_MISTAKE);
	}
	
	/**
	 * 描述：设置session失效出错
	 * @author tianzhongshan 
	 * @date 2017年7月27日下午3:55:08
	 * @return
	 */
	public static ResultVo getSessionInvalid(){
		return new ResultVo(ErrorCode.SESSION_INVALID_CODE, ErrorCode.SESSION_INVALID_MSG);
	}
	

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setGridData(QueryRespDTO queryRespDTO) {
		
		this.total = queryRespDTO.getTotal();
		this.rows = queryRespDTO.getRows();
	}
}
