package com.we.repay.common;

/**
 * @ClassName: ErrorCode
 * @version 1.0
 * @Desc: 返回错误code
 * @author John Gu
 * @date 2016年4月5日下午1:43:24
 * @history v1.0
 */
public class ErrorCode {
	
	// ////////////////////////////////////////////////////////
	// 公共类常量
	// 范围：0-999
	// ////////////////////////////////////////////////////////
	 
	/**
	 * 正常状态[0]代码
	 */
	public static final Integer SYS_CODE_SUCCESS = 0;
	/**
	 * 正常状态[0]消息
	 */
	public static final String SYS_MSG_SUCCESS = "success";
	
	/**
	 * 系统报错[444]代码
	 */
	public static final Integer SYS_CODE_MISTAKE = 444;
	/**
	 * 系统报错[444]消息
	 */
	public static final String SYS_MSG_MISTAKE = "系统出现了问题，请重试！";
	/**
	 * 首次登录系统[410]代码
	 */
	public static final Integer SYS_CODE_FIRST_LOGIN = 410;
	/**
	 * 首次登录系统[410]消息
	 */
	public static final String SYS_MSG_FIRST_LOGIN = "首次登录系统！";
	
	/**
	 * 无权限访问[445]代码
	 */
	public static final Integer INVALID_RIGHT_CODE = 445;
	
	/**
	 *无权限访问[445]消息
	 */
	public static final String INVALID_RIGHT_MSG = "Sorry,您无权限操作!";
	
	/**
	 * 普通报错或者未知报错[446]代码
	 */
	public static final Integer SYS_ERROR_COMMON_CODE = 446;
	/**
	 * 普通报错或者未知报错[446]]消息
	 */
	public static final String SYS_ERROR_COMMON_MSG = "操作报错，请重试！";
	
	/**
	 * 重新鉴权[401]
	 */
	public static final Integer AFRESH_TOKEN_CODE = 401;
	/**
	 * 重新鉴权[401]消息
	 */
	public static final String  AFRESH_TOKEN_MSG = "令牌失效";

	/**
	 * 重新登录[404]
	 */
	public static final Integer AFRESH_LOGIN_CODE = 404;
	/**
	 * 重新登录[404]消息
	 */
	public static final String AFRESH_LOGIN_MSG = "重新登录";
	
	/**
	 * 会话实现[406]
	 */
	public static final Integer SESSION_INVALID_CODE = 406;
	/**
	 * 重新登录[406]消息
	 */
	public static final String SESSION_INVALID_MSG = "会话已失效，请重新登录！";
	
	/**
	 * 手机验证码不正确[501]
	 */
	public static final Integer VALID_MOBILE_CODE = 501;
	/**
	 * 手机验证码不正确[501]消息
	 */
	public static final String VALID_MOBILE_MSG = "手机验证码不正确";
	
	/**
	 *  强制退出[601]
	 */
	public static final Integer FORCED_OUT_CODE = 601;
	/**
	 *  强制退出[601]消息
	 */
	public static final String FORCED_OUT_MSG = "账号已被他人登录...";
	
	/**
	 *  强制登录[602]
	 */
	public static final Integer FORCED_LOGIN_CODE = 602;
	/**
	 *  强制登录[602]消息
	 */
	public static final String FORCED_LOGIN_MSG = "账号已在其它地方登录,是否强制进入！";
	

	// ////////////////////////////////////////////////////////
	// 用户企业类常量
	// 范围：1000-1999 
	// ////////////////////////////////////////////////////////

	/**
	 * 用户名不正确[1000]
	 */
	public static final Integer USER_CODE_1000 = 1000;

	/**
	 * 用户名不正确[1000]消息
	 */
	public static final String USER_MSG_1000 = "用户名不正确";

	/**
	 * 密码错误[1001]
	 */
	public static final Integer USER_CODE_1001 = 1001;

	/**
	 * 密码错误[1001]消息
	 */
	public static final String USER_MSG_1001 = "密码错误";

	/**
	 * 此用户已冻结[1002]
	 */
	public static final Integer USER_CODE_1002 = 1002;

	/**
	 * 此用户已冻结[1002]消息
	 */
	public static final String USER_MSG_1002 = "此用户已冻结";

	/**
	 * 此用户已存在[1003]
	 */
	public static final Integer USER_CODE_1003 = 1003;

	/**
	 * 此用户已存在[1003]消息
	 */
	public static final String USER_MSG_1003 = "此用户已存在";
	
	/**
	 * 没有权限登陆[1004]
	 */
	public static final Integer USER_CODE_1004 = 1004;

	/**
	 * 没有权限登陆[1004]消息
	 */
	public static final String USER_MSG_1004 = "没有权限登陆";
	
	/**
	 * 用户未审核[1005]
	 */
	public static final Integer USER_CODE_1005 = 1005;

	/**
	 * 用户未审核[1005]消息
	 */
	public static final String USER_MSG_1005 = "用户未审核";
	
	/**
	 * 用户审核未通过[1006]
	 */
	public static final Integer USER_CODE_1006 = 1006;

	/**
	 * 用户审核未通过[1006]消息
	 */
	public static final String USER_MSG_1006 = "用户审核未通过";
	
	/**
	 * 电话号码已存在[1007]
	 */
	public static final Integer USER_CODE_1007 = 1007;

	/**
	 * 电话号码已存在[1007]消息
	 */
	public static final String USER_MSG_1007 = "电话号码已存在";
	
	/**
	 * 用户邮箱已存在[1008]
	 */
	public static final Integer USER_CODE_1008 = 1008;

	/**
	 * 用户邮箱已存在[1008]消息
	 */
	public static final String USER_MSG_1008 = "用户邮箱已存在";
	
	/**
	 * 未查询到数据[1009]
	 */
	public static final Integer USER_CODE_1009 = 1009;
	
	/**
	 * 账号不存在[1000]
	 */
	public static final Integer USER_CODE_1010 = 1010;

	/**
	 * 账号不存在[1000]消息
	 */
	public static final String USER_MSG_1010 = "账号不存在";

	/**
	 * 未查询到数据[1009]消息
	 */
	public static final String USER_MSG_1009 = "未查询到数据";
	
	/**
	 * 个人账号无法在企业版登陆[1011]
	 */
	public static final Integer USER_CODE_1011 = 1011;
	
	/**
	 * 个人账号无法在企业版登陆[1011]消息
	 */
	public static final String USER_MSG_1011 = "个人账号无法在企业版登陆";
	
	/**
	 * 企业账号无法在个人版登陆[1012]
	 */
	public static final Integer USER_CODE_1012 = 1012;
	
	/**
	 * 企业账号无法在个人版登陆[1011]消息
	 */
	public static final String USER_MSG_1012 = "企业账号无法在个人版登陆";
	
	/**
	 * 设备info error [200]
	 */
	public static final Integer ERR_CODE_200 = 200;

	/**
	 * 设备info error [200]消息
	 */
	public static final String ERR_MSG_200 = "设备info error";

	/**
	 * 该用户已开通钱包无需再开[101]
	 */
	public static final Integer WALLET_102 = 102;

	// ////////////////////////////////////////////////////////
	// 设备类常量
	// 范围：2000-2999
	// ////////////////////////////////////////////////////////
	
	/**
	 * 此设备已被绑定[2000]
	 */
	public static final Integer DEV_CODE_2000 = 2000;
	
	/**
	 * 此设备已被绑定[2000]消息
	 */
	public static final String DEV_MSG_2000 = "此设备已被绑定";
	
	/**
	 * 此用户已拥有此设备[2001]
	 */
	public static final Integer DEV_CODE_2001 = 2001;
	
	/**
	 * 此用户已拥有此设备[2001]消息
	 */
	public static final String DEV_MSG_2001 = "此用户已拥有此设备";
	
	/**
	 * 设备编号不存在[2002]
	 */
	public static final Integer DEV_CODE_2002 = 2002;
	
	/**
	 * 设备编号不存在[2002]消息
	 */
	public static final String DEV_MSG_2002 = "设备编号不存在";
	
	
	/**
	 * 设备类型不存在[2003]
	 */
	public static final Integer DEV_CODE_2003 = 2003;
	
	/**
	 * 设备类型不存在[2003]消息
	 */
	public static final String DEV_MSG_2003 = "设备类型不存在";
	
	/**
	 * 群组设置包含此设备,请先解除关系![2004]
	 */
	public static final Integer DEV_CODE_2004 = 2004;
	
	/**
	 * 群组设置包含此设备,请先解除关系![2004]消息
	 */
	public static final String DEV_MSG_2004 = "群组设置包含此设备,请先解除关系!";
	
	/**
	 * 组合设置包含此设备,请先解除关系![2005]
	 */
	public static final Integer DEV_CODE_2005 = 2005;
	
	/**
	 * 组合设置包含此设备,请先解除关系![2005]消息
	 */
	public static final String DEV_MSG_2005 = "组合设置包含此设备,请先解除关系!";
	
	/**
	 * 标签名称已存在[2006]
	 */
	public static final Integer DEV_CODE_2006 = 2006;
	
	/**
	 * 标签名称已存在[2006]消息
	 */
	public static final String DEV_MSG_2006 = "标签名称已存在";
	
	/**
	 * 企业设备无法绑定[2007]
	 */
	public static final Integer DEV_CODE_2007 = 2007;
	
	/**
	 * 企业设备无法绑定[2007]消息
	 */
	public static final String DEV_MSG_2007 = "企业设备无法绑定";
	
	/**
	 * 无权限修改[2008]
	 */
	public static final Integer DEV_CODE_2008 = 2008;
	
	/**
	 * 无权限修改[2008]消息
	 */
	public static final String DEV_MSG_2008 = "无权限修改";
	
	/**
	 * 个人设备无法分配给企业账号[2009]
	 */
	public static final Integer DEV_CODE_2009 = 2009;
	
	/**
	 * 个人设备无法分配给企业账号[2009]消息
	 */
	public static final String DEV_MSG_2009 = "个人设备无法分配给企业账号";
	
	/**
	 * 安装位置已存在[2010]
	 */
	public static final Integer DEV_CODE_2010 = 2010;
	
	/**
	 * 安装位置已存在[2010]消息
	 */
	public static final String DEV_MSG_2010 = "安装位置已存在";
	
	/**
	 * 安装位置已存在[2011]
	 */
	public static final Integer DEV_CODE_2011 = 2011;
	
	/**
	 * 安装位置已存在[2010]消息
	 */
	public static final String DEV_MSG_2011 = "打开摄像头失败";
	
	/**
	 * 无权限分配设备[2012]
	 */
	public static final Integer DEV_CODE_2012 = 2012;
	
	/**
	 * 无权限分配设备[2012]消息
	 */
	public static final String DEV_MSG_2012 = "无权限分配设备";
	
	/**
	 * 菜单名称已存在[2014]
	 */
	public static final Integer DEV_CODE_2014 = 2014;
	
	/**
	 * 菜单名称已存在[2014]消息
	 */
	public static final String DEV_MSG_2014 = "菜单名称已存在";
	
	/**
	 * 角色名称已存在[2015]
	 */
	public static final Integer DEV_CODE_2015 = 2015;
	
	/**
	 * 角色名称已存在[2015]消息
	 */
	public static final String DEV_MSG_2015 = "角色名称已存在";
	
	/**
	 * 场地名称已存在[2016]
	 */
	public static final Integer DEV_CODE_2016 = 2016;
	
	/**
	 * 场地名称已存在[2016]消息
	 */
	public static final String DEV_MSG_2016 = "场地名称已存在";
	
	// ////////////////////////////////////////////////////////
	// 事件类常量
	// 范围：3000-3999
	// ////////////////////////////////////////////////////////
	
	
	

	// ////////////////////////////////////////////////////////
	// 日志类常量
	// 范围：4000-4999
	// ////////////////////////////////////////////////////////
	
	
	
	

	// ////////////////////////////////////////////////////////
	// 设置类常量
	// 范围：5000-5999
	public static final int ERR_DEV_SET_CODE = 5000;
	public static final String ERR_DEV_SET_MSG ="设置异常";
	// ////////////////////////////////////////////////////////

}
