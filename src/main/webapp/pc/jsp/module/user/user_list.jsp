<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>用户列表</title>
<%@include file="/pc/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/user_list.css">

</head>
<body ng-app="user_list" ng-controller="contentHandleCtrl" ng-cloak
	style="overflow: hidden;">
	<div id="topMenu"><%@include file="/pc/jsp/common/top.jsp"%></div>
	<div id="downContent"
		style="border: 0px red solid; height: 100%; padding: 20px 20px; padding-top: 70px; margin-top: -50PX;">
		<div id="headerDiv" class="header">
			<div class="pull-left">
				<form id="" onsubmit="return false" class="query-form" style="">
					<input id="realName" name="realName" placeholder="真实姓名" /> 
					<input id="mobile" name="mobile" placeholder="手机号码" />
					<select  style="width:130px;"  id="attribute1" name="attribute1"  >
			          <option value="" selected="selected">ALL</option>
			          <option value="1">普通会员</option>
			          <option value="-1">终生会员</option>
			          <option value="0">关注非会员</option>
			        </select>
					<button id="searchBtn">筛选</button>
					<button id="resetBtn">重置</button>
					<button id="addBtn">添加</button>
					<button id="updateBtn">修改</button>
					<!-- <button id="delBtn">删除</button> -->
					<!--  <button id="updateBtn">修改</button> -->
				</form>
			</div>
		</div>
		<div class="content" style="height: 100%; padding-top: 48px; margin-top: -48px;">
			<table id="gridTable" class="" style="height: 100%;"></table>
		</div>
	</div>


</body>
<script type="text/javascript" src="${basePath}/static/js/module/user/user_list.js"></script>
<script type="text/javascript">
	if (IUSP) {
		IUSP.start();
	}
</script>
</html>