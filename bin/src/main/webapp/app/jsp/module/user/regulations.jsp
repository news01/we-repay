<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>章程</title>
<%@include file="/app/jsp/common/common.jsp"%>

<link rel="stylesheet" href="${basePath}/static/css/module/user/user_info.css">
<style type="text/css">
			.regulations{
				text-align: center;
				font-size: 1rem;
				font-weight: 900;
			}
			.smallTitle{
				text-align: center;
			}
			.smallRegulations{
				text-align: center;
				font-weight: 900 ;
			}
			.content{
				size: 0.5rem;
			}
			#img img{
				max-width:16rem; 
			}
			
		</style>
</head>
<body ng-app="user_info" ng-controller="contentHandleCtrl" ng-cloak>
	<div id="img">
		<img id="regulations_1"  src="${basePath }/static/images/module/regulations/regulations_1.png">
		<img id="regulations_2"  src="${basePath }/static/images/module/regulations/regulations_2.png">
		<img id="regulations_3"  src="${basePath }/static/images/module/regulations/regulations_3.png">
		<img id="regulations_4"  src="${basePath }/static/images/module/regulations/regulations_4.png">
	</div>
</body>

<script type="text/javascript" src="${basePath }/static/js/module/user/user_info.js"></script>

<script type="text/javascript">
	if(window.IUSP){
		IUSP.start();
	}
</script>
</html>