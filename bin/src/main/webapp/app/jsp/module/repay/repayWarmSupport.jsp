<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>热心赞助</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/repay/repay.css">

</head>
<body ng-app="repayWarmSupport" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
		<form id="editForm" name="editForm" onsubmit="return false;">
			<div class="row-fluid user_info_div">
				<div class="row-fluid">
					<div style="height: 2.7083rem; position: relative;">
						<div class="img_user"></div>
						<div class="info_user">
							<div class="row-fluid"
								style="width: 100%; height: 1.08334rem; margin-top: 0.5rem;">
								<div class="pull-left" style="font-size: 0.625rem; color: #333">
									热心赞助
								</div>
								<div class="pull-right" style="font-size: 0.5rem; color: #999">

								</div>
							</div>
						</div>
					</div>
					<div style="position: relative;">
						<input type="number" class="input_money" placeholder="缴付金额"
							step="0.01" name="total_fee" required ng-model="param.total_fee"
							ng-blur="inputblur($event)" ng-focus="inputFocus($event)" /> <span
							class="money_unit">￥</span>
					</div>
				</div>
			</div>
			<div id="remind">{{remind}}</div>
			<div class="row-fluid" style="padding: 0rem 0.5416rem;">
				<button class="row-fluid zhifu"
					ng-disabled="editForm.total_fee.$error.required"
					ng-click="submintRepayResq($event,param)">支付</button>
			</div>
		</form>
	</div>

</body>

<!-- <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script> -->
<script type="text/javascript" src="${basePath }/static/js/common/sha1.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/md5.js"></script>
<script type="text/javascript" src="${basePath }/static/js/common/wxconfig.js"></script>
<script type="text/javascript" src="${basePath }/static/js/module/repay/repayWarmSupport.js"></script>

<script type="text/javascript">

	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>