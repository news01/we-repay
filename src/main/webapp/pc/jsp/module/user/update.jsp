<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/pc/jsp/common/common.jsp"%>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^1[2|3|4|5|6|7|8|9][0-9]{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    }, 
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: "只能输入汉字"
    }

});
</script>
<style type="text/css">
#street{
	width: 90%;
	height: 30px ;
	line-height: 30px;
	font-size: 14px;
	color: #999;
	padding-left:12px ;
	border: 1px solid #dbdbdb;
	margin-left: 12px;
}
#Membership_level{
	width: 90%;
	height: 30px ;
	line-height: 30px;
	font-size: 14px;
	color: #999;
	padding-left:12px ;
	margin-top: 17px;
	border: 1px solid #dbdbdb;
	margin-left: 12px;
}
</style>
</head>
<body>
	<div style="width: 100%;">
		<form class="edit-form" id="editForm" style="height: 300px;">
			<table>
				<input type="text" placeholder="用户昵称" id="account" name="nickname" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,10]',missingMessage:'用户名不能为空',invalidMessage:'用户名的长度为4-20位'" />
				<input type="hidden" id="userId" name="userId" />

				<input type="text" placeholder="真实姓名" class="easyui-validatebox" id="realName" name="realName"
					data-options="required:true,validType:'CHS',missingMessage:'姓名不能为空'" />
					
				<select id="sex" name="sex" class="easyui-validatebox" style="width: 90%;margin-left: 12px;margin-bottom: 16px;">
					<option value="1">男</option>
					<option value="2">女</option>
				</select>

				<input type="text" placeholder="电话" id="phone" name="phone" class="easyui-validatebox"
					data-options="required:true,validType:'mobile',missingMessage:'手机号码不能为空'" />

				<select name="address" id="street" class="easyui-validatebox">
					    <option value="TB">田背</option>
						<option value="XY">下营</option>
						<option value="CS">赤水</option>
						<option value="DK">大坑</option>
						<option value="NC">南村</option>
						<option value="ZA">嶂岸</option>
						<option value="HG">华光</option>
						<option value="BY">陂营</option>
						<option value="KP">葵坪</option>
						<option value="QT">其它</option>
				 </select>
				 
				 <select name="Membership_level" id="Membership_level" class="easyui-validatebox">
					    <option value="2">普通会员</option>
						<option value="1">终生会员</option>
				 </select>
					
			</table>
		</form>

		<div class="modal-footer">
			<button class="btn btn-primary" id="saveUpdateBtn">保存</button>
			<button id="modalCloseBtn" class="btn btn-default">关闭</button>
		</div>
	</div>

</body>
<script type="text/javascript" src="${basePath}/static/js/module/user/user_list.js"></script>
<script type="text/javascript">
	
	if(localStorage.info != null || localStorage.info != undefined){
		var info = JSON.parse(localStorage.info);
		
		$('#account').val(info.nickname);
		$('#realName').val(info.realName);
		$('#sex').val(info.sex);
		$('#phone').val(info.mobile);
		$('#email').val(info.email);
		$('#country').val(info.country);
		$('#province').val(info.province);
		$('#city').val(info.city);
		$('#userId').val(info.waId);
		
		var st = info.attribute2;
		var stVal = IUSP.GetAddressCH[st];
		$('#street').val(stVal);
		
		var a1 = info.attribute1;
		if(a1 != -1){
			a1 = 2;
		} else {
			a1 = 1;
		}
		$('#Membership_level').val(a1);
		
		localStorage.removeItem('info');
		
	}
	
	
	if (IUSP) {
		IUSP.start();
	}
	
	
</script>
</html>