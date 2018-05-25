<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>完善信息</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/complete_information.css">

</head>
<body ng-app="complete_information" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
	
		<div class="container">
			<!--内容-->
	    	<div id="change_name" class="child_page select_page">
	    		<div class="row-fluid title_div" id="user_title">
					<p class="pagetitle">完善注册信息</p>
					<div  class="title_div_left">
						<!-- <a href="javascript:void(0);"><i class="icon_back_white"></i></a> -->
					</div>
				</div>
				<form id="editForm"  name="editForm">
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="change_name_input" placeholder="姓名" name="realName" required ng-model="param.realName"/>
					</div>
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="change_phone_input" placeholder="手机" name="phone" required ng-model="param.phone"/>
						<!-- <button id="get_check_code" ng-disabled="editForm.telephone.$pristine" ng-click="regex($event,param,1)" >获取验证码</button> -->
					</div>
					<select id="change_sex_input" name="sex" class="easyui-validatebox">
						<option value="1">男</option>
						<option value="2">女</option>
					</select>
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="change_check_code"  name="validCode" placeholder="邮箱"  ng-model="param.email"/>
						<!-- validate-email message="邮箱格式不正确" -->
					</div>
					<select name="address" id="change_address_input" class="row-fluid inno-paddingleft10 inno-paddingright10">
					    <option value="0">请选择住址</option>
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
					<p>
						<label style="margin-bottom: 0px">阅读
							<a href="http://www.cloudcould.cc/we-repay/user/toRegulations.do" style="text-decoration: underline ">《洲瑞同乡联谊会章程》</a> 
					        <input type="checkbox" name="checkbox" id="checkbox" /> 我同意以上条款
						</label>
					</p><!-- 不是完整的11位手机号或者手机号不正确！ -->
					<div class="update_name-error" >{{error_tips}}</div>
					<div class="row-fluid" style="margin-top: -10px;">
						<button id="define" class="define" ng-click="regex($event,param,2)" >确定修改</button><!--  ng-disabled="editForm.realName.$error.required" -->
					</div>
				</form>
			</div>
	   	</div>
	</div>
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/complete_information.js"></script>

<script type="text/javascript">
	
	/* var wxOpenId = '${openId}'
	var sku_number = '${sku_number}'; */
	
	$('#define').attr("disabled", true);
	$("#checkbox").change(function() { 
		if($("#checkbox").is(':checked')){
			$('#define').attr("disabled", false);
		} else {
			$('#define').attr("disabled", true);
		}
	});

	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>