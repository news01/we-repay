<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>公告牌</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/billboard.css">

</head>
<body ng-app="billboard" ng-controller="contentHandleCtrl" ng-cloak>

	<div class="pages">
		<div class="container icon_blue_back">
			<div class="row-fluid content">
				<div class="row-fluid">
					<p>Hakka Federation MeiZhou（SZ） Membership Portal</p>
				</div>
				<div class="row-fluid">
					<p>深圳客家梅州会</p>
				</div>
				<div class="row-fluid margin_top6">
					<p>欢迎使用梅州客家深圳会客户端，请随时 关注我们的账号，我们会不定期发布会馆信息。</p>
				</div>
				<div class="row-fluid">
					<p>【QR CODE 】</p>
				</div>
			</div>
		</div>
	</div>
	
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/billboard.js"></script>

<script type="text/javascript">
	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>