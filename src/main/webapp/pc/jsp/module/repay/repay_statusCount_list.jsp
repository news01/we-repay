<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">	
<title>支付统计</title>
<%@include file="/pc/jsp/common/common.jsp"%>
<%@include file="/pc/jsp/common/datepicker.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/repay/repay_statusCount_list.css">

</head>
<body ng-app="repay_statusCount_list" ng-controller="contentHandleCtrl" ng-cloak style="overflow: hidden;">
	<div id="topMenu"><%@include file="/pc/jsp/common/top.jsp"%></div>
	<div id="downContent" style="border:0px red solid;height:100%; padding: 20px 20px; padding-top: 70px; margin-top: -50PX;">
		<div id="headerDiv" class="header">
			<div class="pull-left">
				<form id="" onsubmit="return false" class="query-form" style="">
				   <input id="year" name="year" type="text" placeholder="年份" >
				   
				   <select  style="width:130px;"  id="status" name="status"  >
				   	  <option value="-1">ALL</option>
			          <option value="0">未支付</option>
			          <option value="1">已支付</option>
			       </select>
				   <button id="searchBtn">筛选</button>
		   		   <button id="resetBtn">重置</button>
		   		   <button id="remindBtn">提醒</button>
				</form>
			</div>
		</div>
		<div class="content" style="height:100%;padding-top: 48px;margin-top: -48px;">
			<table id="gridTable" class="" style="height: 100%;" ></table>
		</div>
	</div>
</body>
<script type="text/javascript"  src="${basePath}/static/js/module/repay/repay_statusCount_list.js"></script>
<script type="text/javascript">

 if(IUSP){
	IUSP.start();
 }
	
</script>
</html>