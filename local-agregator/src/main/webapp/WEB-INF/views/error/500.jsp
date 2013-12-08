<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@include file="/WEB-INF/included/resource.jsp" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
	<title>500 - 系统内部错误</title>
</head>

<body class="error-page">
<div class="wrapper">
    <div class="desc"><i class="icon-warning-sign"></i><span>500 - 系统内部错误</span></div>
    <div class="buttons">
        <a href="dashboard" class="btn btn-info btn-large btn-block"><i class="icon-home"></i>首页</a>
        <a href="javascript:;" onclick="window.history.go(-1);" class="btn btn-large btn-block"><i class="icon-arrow-left"></i>返回</a>
    </div>
</div>

</body>
</html>
