<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改真实姓名</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/change_realName.css">

</head>
<body ng-app="change_realName" ng-controller="contentHandleCtrl" ng-cloak>
	<div class="pages">
	
		<div class="container">
			<!--内容-->
	    	<div id="change_name" class="child_page select_page">
	    		<div class="row-fluid title_div" id="user_title">
					<p class="pagetitle">真实姓名</p>
					<div  class="title_div_left">
						<a href="javascript:void(0);" onclick="IUSP.historyGo(window,-1);"><i class="icon_back_white"></i></a>
					</div>
				</div>
				<form id="editForm"  name="editForm">
					<div class="row-fluid inno-paddingleft10 inno-paddingright10">
						<input id="change_name_input" placeholder="真实姓名" name="realName" required ng-model="param.realName"/>
					</div>
					<div class="update_name-error" >{{error_tips}}</div>
					<div class="row-fluid">
						<button class="define" ng-disabled="editForm.realName.$error.required" ng-click="submitChangeName(param)" >确定修改</button>
					</div>
				</form>
			</div>
	   	</div>
	</div>
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/change_realName.js"></script>

<script type="text/javascript">
	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>