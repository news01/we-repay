<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.we.repay.common.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	String path = request.getContextPath(); 
	String basePath = Constants.BASE_PATH;
	request.setAttribute("path", path);
	request.setAttribute("basePathReq", basePath);
	request.setAttribute("basePath", basePath+"/pc");
%>
<script type="text/javascript">
 	if(!window.contextPath){
 		window.contextPath = "${basePathReq}";
 	}
</script>

<link href="${basePath}/static/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath}/static/plugin/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${basePath}/static/plugin/easyui/themes/metro-blue/easyui.css"  />
<link rel="stylesheet" type="text/css" href="${basePath}/static/plugin/easyui/themes/icon.css"  />
<link href="${basePath}/static/css/common/common.css" rel="stylesheet">
<link href="${basePath}/static/css/common/header.css" rel="stylesheet" />

<script src="${basePath}/static/plugin/bootstrap/js/jquery-3.2.1.min.js" type="text/javascript" ></script>
<script src="${basePath}/static/plugin/bootstrap/js/bootstrap.min.js" type="text/javascript" ></script>
<script src="${basePath}/static/plugin/easyui/jquery.easyui.min.js" type="text/javascript" ></script>
<script src="${basePath}/static/plugin/angular/js/angular.min.js" type="text/javascript" ></script>
<script src="${basePath}/static/js/common/header.js"></script>
<script src="${basePath}/static/plugin/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript" ></script>
<script src="${basePath}/static/js/common/iusp.js"></script>