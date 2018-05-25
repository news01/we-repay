<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>为他人支付</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/repayforSomeone.css">

</head>
<body ng-app="repayforSomeone" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
	
		<div class="container">
			<!--内容-->
	    	<div id="change_name" class="child_page select_page">
	    		<div class="row-fluid title_div" id="user_title">
					<p class="pagetitle">为他人支付</p>
					<div  class="title_div_left">
						<a href="javascript:void(0);" onclick="IUSP.historyGo(window,-1);"><i class="icon_back_white"></i></a>
					</div>
				</div>
				<form id="editForm"  name="editForm">
				
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="realName" placeholder="用户姓名" name="realName" required ng-model="param.realName"/>
					</div>
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="mobile" placeholder="手机号" name="mobile" required ng-model="param.mobile"/>
					</div>
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="money" type="number" placeholder="缴费金额" name="money" required ng-model="param.total_fee"/>
					</div>
					<select name="address" id="address" class="row-fluid inno-paddingleft10 inno-paddingright10">
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
					
					<div class="update_name-error" >{{ErrorInfo}}</div>
					<div class="row-fluid">
						<button class="define" ng-disabled="editForm.realName.$error.required" ng-click="submitrepay($event,param)" >确定</button>
					</div>
				</form>
			</div>
	   	</div>
	</div>
</body>

<!-- <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script> -->
<script type="text/javascript" src="${basePath }/static/js/common/sha1.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/md5.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/wxconfig.js"></script>
<script type="text/javascript" src="${basePath }/static/js/module/repay/repayforSomeone.js"></script>

<script type="text/javascript">
	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>