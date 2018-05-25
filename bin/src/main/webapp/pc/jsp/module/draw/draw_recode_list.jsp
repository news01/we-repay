<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>抽奖记录列表</title>
<%@include file="/pc/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/user_list.css">

</head>
<body ng-app="draw_recode_list" ng-controller="contentHandleCtrl" ng-cloak style="overflow: hidden;">
	<div id="topMenu"><%@include file="/pc/jsp/common/top.jsp"%></div>
	<div id="downContent"
		style="border: 0px red solid; height: 100%; padding: 20px 20px; padding-top: 70px; margin-top: -50PX;">
		<div id="headerDiv" class="header">
			<div class="pull-left">
				<form id="" onsubmit="return false" class="query-form" style="">
					<input id="name" name="name" placeholder="会员姓名" />
					<input id="numberCode" name="numberCode" placeholder="会员编号" />
					<select style="width: 130px;" id="prizeLevel" name="prizeLevel">
						<option value="" selected="selected">中奖等级</option>
						<option value="-2">未中奖</option>
						<option value="-1">未抽奖</option>
						<option value="0">特等奖</option>
						<option value="1">一等奖</option>
						<option value="2">二等奖</option>
						<option value="3">三等奖</option>
						<option value="4">四等奖</option>
						<option value="5">五等奖</option>
						<option value="6">六等奖</option>
					</select>
					<select style="width: 130px;" id="status" name="status">
						<option value="" selected="selected">抽奖资格</option>
						<option value="0">异常</option>
						<option value="1">正常</option>
					</select>
					<button id="searchBtn">筛选</button>
					<button id="resetBtn">重置</button>
					<button id="addBtn">同步</button>
					<button id="updateBtn">冻结</button>
				    <button id="drawPrize">抽奖</button>
				</form>
			</div>
		</div>
		<div class="content"
			style="height: 100%; padding-top: 48px; margin-top: -48px;">
			<table id="gridTable" class="" style="height: 100%;"></table>
		</div>
	</div>


</body>
<script type="text/javascript" src="${basePath}/static/js/module/draw/draw_recode_list.js"></script>
<script type="text/javascript">
	if (IUSP) {
		IUSP.start();
	}
</script>
</html>