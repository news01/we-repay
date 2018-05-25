<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>登陆</title>
<%@include file="/pc/jsp/common/common.jsp"%>

<link rel="stylesheet"
	href="${basePath}/static/css/module/user/login.css">
<style type="text/css">
#myForm {
	margin-top: 30px;
	margin-bottom: 30px;
}

#div_img {
	background-color: #10c55b;
	margin-bottom: -80px;
}

#div_img img {
	height: 53px;
	width: 170px;
}

</style>

</head>
<body ng-app="login" ng-controller="contentHandleCtrl" ng-cloak
	style="height: 80%; overflow-y: hidden;background-color: #FAFAD2;">



	<div class="pages">
		<div class="container" id="login_page">

			<div class="row-fluid" id="div_img">

				<img src="${basePath}/static/images/common/ZHOUTIANBAIRUI.png" />

			</div>

			<form name="myForm" id="myForm" onsubmit="return false">
				<div class="row-fluid" id="login_input_div">
					<div class="login_li">
						<i class="login_icon_name"></i> <input type="text"
							placeholder="请输入用户名或者手机号码" required ng-model="param.loginName" />
					</div>
					<div class="login_li">
						<i class="login_icon_password"></i> <input type="password"
							placeholder="请输入密码" required ng-model="param.pwd" />
						<!-- 	<div id="forget_password">
								忘记密码？
							</div> -->
					</div>
				</div>
				<div class="login-error">{{loginErrorInfo}}</div>
				<button class="row-fluid" id="login_submit"
					ng-disabled="myForm.$invalid" ng-click="submitLogin(param)">登陆</button>
			</form>

			<!-- <div id="Jump_false_div"></div>
			<div id="Jump_div">
				没有账户？<span id="immediately_registered">立即注册</span>
			</div> -->
		</div>
	</div>

</body>

<script type="text/javascript"
	src="${basePath }/static/js/module/user/login.js"></script>

<script type="text/javascript">
	if (window.IUSP) {
		IUSP.start();
	}
</script>
</html>