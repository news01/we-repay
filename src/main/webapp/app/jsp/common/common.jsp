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
	request.setAttribute("basePath", basePath+"/app");
	request.setAttribute("appid", Constants.INNOAPPID);
	request.setAttribute("jsapiTicket", Constants.INNOJSAPI_TICKET);
%>
<script type="text/javascript">
	window.contextPath = "${basePathReq}";
	window.appid = "${appid}";
	window.jsapiTicket = "${jsapiTicket}";
</script>

<link rel="stylesheet" href="${basePath}/static/plugin/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${basePath}/static/plugin/bootstrap/css/bootstrap-responsive.css">
<link rel="stylesheet" href="${basePath}/static/css/common/bootstrap_edit.css">

<link rel="stylesheet" href="${basePath}/static/plugin/easyui/themes/metro-blue/easyui.css"  />
<link rel="stylesheet" href="${basePath}/static/plugin/easyui/themes/icon.css"  />

<link rel="stylesheet" href="${basePath}/static/css/common/common.css">

<script src="${basePath}/static/plugin/jquery/js/jquery-1.10.2.min.js" type="text/javascript" ></script>

<script src="${basePath}/static/plugin/easyui/jquery.easyui.min.js" type="text/javascript" ></script>
<script src="${basePath}/static/plugin/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript" ></script>

<script src="${basePath}/static/plugin/angular/js/angular.min.js" type="text/javascript" ></script>

<script type="text/javascript" src="${basePath}/static/js/common/client_width_rem.js" ></script>

<script src="${basePath}/static/js/common/iusp.js"></script>
<script src="${basePath}/static/js/common/jweixin-1.2.0.js"></script>

