<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>提示</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/wx_authorize.css">

</head>
<body ng-app="wx_authorize" ng-controller="contentHandleCtrl" ng-cloak>
<div class="pages">
	<div class="container">
		<!--支付-->
		<div id="equipment_Details" class="pay_status_page" >
			<div class="row-fluid">
				<div class="row-fluid">
					<p class="pagetitle">提示</p>
				<!-- 	<p><i class="Failure_icon"></i></p> -->
					<p class="pay_status_text">自动登录失败，请重试<br/>或请取消公众号关注进行重新关注确认</p>
				</div>
				<button id="carryOut" ng-disabled="repeatedWxAuthorize" ng-click="fnWxAuthorize()">
					重试
				</button>
			</div>
		</div>
	</div>
</div>
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/wx_authorize.js"></script>

<script type="text/javascript">
	var page_redirectURL = '${redirectURL}';
	
	if (window.IUSP) {
		IUSP.start();
	}
</script>
</html>