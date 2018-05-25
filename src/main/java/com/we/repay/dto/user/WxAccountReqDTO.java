/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.dto.user;

import com.we.repay.dto.QueryReqDTO;

/**
 * @ClassName: WxAccountReqDTO
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月20日上午9:48:10
 * @history v2.0
 *
 */
@SuppressWarnings("serial")
public class WxAccountReqDTO extends QueryReqDTO {
	/**
     * 主键ID
     */
    private Long waId;

    /**
     * 云能用户ID
     */
    private Long ccUserId;

    /**
     * 云能用户名称
     */
    private String ccUserName;

    /**
     * 云能用户密码
     */
    private String ccUserPwd;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信小程序ID
     */
    private String xcxOpenId;

    /**
     * 公众号
     */
    private String mpOpenId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;

    /**
     * 用户个人资料填写的省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 用户头像
     */
    private String headimgurl;

    /**
     * 用户特权信息
     */
    private String privilege;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制） 
     */
    private String unionid;

    /**
     * 语言
     */
    private String language;

    /**
     * 预留字段1
     */
    private String attribute1;

    /**
     * 预留字段2
     */
    private String attribute2;

    /**
     * 预留字段3
     */
    private String attribute3;

    /**
     * 预留字段4
     */
    private String attribute4;

    /**
     * 预留字段5
     */
    private String attribute5;

    /**
     * 预留字段6
     */
    private String attribute6;

	public Long getWaId() {
		return waId;
	}

	public void setWaId(Long waId) {
		this.waId = waId;
	}

	public Long getCcUserId() {
		return ccUserId;
	}

	public void setCcUserId(Long ccUserId) {
		this.ccUserId = ccUserId;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getXcxOpenId() {
		return xcxOpenId;
	}

	public void setXcxOpenId(String xcxOpenId) {
		this.xcxOpenId = xcxOpenId;
	}

	public String getMpOpenId() {
		return mpOpenId;
	}

	public void setMpOpenId(String mpOpenId) {
		this.mpOpenId = mpOpenId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}
}
