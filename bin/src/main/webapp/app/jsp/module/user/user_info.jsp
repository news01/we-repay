<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>个人中心</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/user_info.css">

</head>
<body ng-app="user_info" ng-controller="contentHandleCtrl" ng-cloak>

	<div class="pages">
		<div class="container">
			<!--用户块-->
			<div id="user_page" class="pages select_page">
				<div class="row-fluid title_div" id="user_title">
					<p class="pagetitle">个人中心</p>
					<!-- <div class="logout" id="logout" ng-click="openModal('#logout_info')">退出</div> -->
				</div>
				<div class="row-fluid" id="user_info">
					<!-- <div class="div_li">
						<div class="info_left">
							<i class="user_icon_name"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">昵称</div>
							<div class="pull-right">
								{{userInfo.nickname}} <i class="right_details"></i>
							</div>
						</div>
					</div> -->
					<div class="div_li"
						ng-if="userInfo.ccUserId != '' && userInfo.ccUserId != null">
						<div class="info_left">
							<i class="user_icon_name"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">编号</div>
							<div class="pull-right">
								{{userInfo.attribute3}} <i class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="div_li">
						<div class="info_left">
							<i class="user_icon_name"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">姓名</div>
							<div class="pull-right" ng-click="fnToChangeRealName()">
								{{userInfo.realName}} <i class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="div_li">
						<div class="info_left">
							<i class="user_icon_sex"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">性别</div>
							<div class="pull-right">
								{{userInfo.sex==1?'男':(userInfo.sex==2?'女':"未知")}} <i
									class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="div_li">
						<div class="info_left">
							<i class="user_icon_phone"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">手机</div>
							<div class="pull-right" ng-click="fnToChangemobile()">
								{{userInfo.mobile}} <i class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="div_li">
						<div class="info_left">
							<i class="user_icon_email"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">邮箱</div>
							<div class="pull-right">
								{{userInfo.email}} <i class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="div_li">
						<div class="info_left">
							<i class="user_icon_address"></i>
						</div>
						<div class="info_right">
							<div class="pull-left">自然村</div>
							<div class="pull-right">
								<!-- {{userInfo.province}} --> {{userInfo.attribute2}} <i class="right_details"></i>
							</div>
						</div>
					</div>
					<div class="row-fluid"
						ng-if="userInfo.ccUserId == '' || userInfo.ccUserId == null">
						<button class="define" ng-click="jump()">会员注册</button>
						<!--  ng-disabled="editForm.realName.$error.required" -->
					</div>
				</div>

			</div>
		</div>
		<!--弹出块-->
		<div id="modal_module" class="child_page">
			<div class="opacity0_5"></div>
			<!--用户退出页面-->
			<div id="logout_info" class="modal_module_child">
				<div ng-click="fnLoginOut()">更换绑定</div>
				<div id="login_out" ng-click="fnLoginOut()">解绑账号</div>
				<div class="cancel" ng-click="closeModal()">取消</div>
			</div>
		</div>
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/user_info.js"></script>

<script type="text/javascript">
	if (window.IUSP) {
		IUSP.start();
	}
</script>
</html>