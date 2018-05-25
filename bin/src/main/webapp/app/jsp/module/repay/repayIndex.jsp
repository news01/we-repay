<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet"
	href="${basePath}/static/css/module/repay/repay.css">
<style type="text/css">
#parentDiv {
	height: 15rem;
	width: 15.122rem;
	margin-top: 6rem;
}

#parentDiv div {
	margin: 1rem auto;
}
</style>

</head>
<body ng-app="repayIndex" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
		<form id="editForm" name="editForm" onsubmit="return false;">

			<div class="container">
				<!--内容-->
				<div id="change_name" class="child_page select_page">
					<div class="row-fluid title_div" id="user_title">
						<p class="pagetitle">会员支付</p>
						<div class="title_div_left">
							<!-- <a href="javascript:void(0);"
								onclick="IUSP.historyGo(window,-1);"><i
								class="icon_back_white"></i></a> -->
						</div>
					</div>
				</div>
			</div>


			<div id="parentDiv">
				<div class="row-fluid" style="padding: 0rem 0.5416rem;">
					<button class="row-fluid zhifu"
						ng-click="payForSomeone(1)">自己支付</button>
				</div>
				<div class="row-fluid" style="padding: 0rem 0.5416rem;">
					<button class="row-fluid zhifu"
						ng-click="payForSomeone(2)">为他人支付</button>
				</div>
			</div>

		</form>
	</div>

</body>

<!-- <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script> -->
<script type="text/javascript" src="${basePath }/static/js/common/sha1.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/md5.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/wxconfig.js"></script>
<script type="text/javascript" src="${basePath }/static/js/module/repay/repayIndex.js"></script>

<script type="text/javascript">
	if (window.IUSP) {
		IUSP.start();
	}
</script>
</html>