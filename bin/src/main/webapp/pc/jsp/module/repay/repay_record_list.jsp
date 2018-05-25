<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">	
<title>支付列表</title>
<%@include file="/pc/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/repay/repay_record_list.css">

</head>
<body ng-app="repay_record_list" ng-controller="contentHandleCtrl" ng-cloak style="overflow: hidden;">
	<div id="topMenu"><%@include file="/pc/jsp/common/top.jsp"%></div>
	<div id="downContent" style="border:0px red solid;height:100%; padding: 20px 20px; padding-top: 70px; margin-top: -50PX;">
		<div id="headerDiv" class="header">
			<div class="pull-left">
				<form id="" onsubmit="return false" class="query-form" style="">
		           <input id="payMan" name="payMan" placeholder="支付人" />
		           <select  style="width:130px;"  id="status" name="status"  >
			          <option value="0">待支付</option>
			          <option value="1" selected="selected">交易成功</option>
			          <option value="2">交易失败</option>
			          <option value="3">转入退款</option>
			          <option value="4">已关闭</option>
			          <option value="5">已撤销</option>
			          <option value="6">用户支付中</option>
			       </select>
			       <select  style="width:130px;"  id="amount" name="amount">
			          <option value="">金额</option>
			          <option value="1">300+</option>
			          <option value="2">0-300</option>
			       </select>
				   <button id="searchBtn">筛选</button>
		   		   <button id="resetBtn">重置</button>
				</form>
			</div>
		</div>
		<div class="content" style="height: 100%;padding-top: 48px;margin-top: -48px;">
			<table id="gridTable" class="" style="height: 100%;" ></table>
		</div>
	</div>
</body>
<script type="text/javascript"  src="${basePath}/static/js/module/repay/repay_record_list.js"></script>
<script type="text/javascript">

 if(IUSP){
	IUSP.start();
 }
	
</script>
</html>