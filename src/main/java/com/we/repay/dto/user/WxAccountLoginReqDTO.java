/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.dto.user;


/**
 * @ClassName: WxAccountReqDTO
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月20日上午9:48:10
 * @history v2.0
 *
 */
public class WxAccountLoginReqDTO {

    /**
     * 云能用户名称
     */
    private String ccUserName;

    /**
     * 云能用户密码
     */
    private String ccUserPwd;

	public String getCcUserName() {
		return ccUserName;
	}

	public void setCcUserName(String ccUserName) {
		this.ccUserName = ccUserName;
	}

	public String getCcUserPwd() {
		return ccUserPwd;
	}

	public void setCcUserPwd(String ccUserPwd) {
		this.ccUserPwd = ccUserPwd;
	}

}
