<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>支付记录</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/repay/my_repay_record.css">

</head>
<body ng-app="my_repay_record" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
		<div class="container">
			<div class="row-fluid record_container">
				<div class="row-fluid record_content" ng-repeat="repayRecord in repayRecordList">
					<div class="pull-left record_desc">
						<div class="row-fluid"><span class="span_font" ng-bind="repayRecord.attribute3==3?'微信支付-热心赞助':'微信支付-会费缴付'"></span></div>
						<div class="row-fluid"><span>{{repayRecord.showUpdateDtm}}</span></div>
					</div>
					<div class="pull-right record_amount">
						<div class="row-fluid"><span  class="span_font">{{repayRecord.amount}}</span></div>
					</div>
					
				</div>
				<div  class="more">
				 	<a class="" id="checkMore" ng-if="repayRecordMore" ng-click="fnRepayRecordList()">查看更多...</a>
				 	<span ng-if="!repayRecordMore" >没有更多信息</span> 
				</div>
				
			</div>
		
		</div>
	</div>

</body>

<script type="text/javascript" src="${basePath }/static/js/module/repay/my_repay_record.js"></script>

<script type="text/javascript">

	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>