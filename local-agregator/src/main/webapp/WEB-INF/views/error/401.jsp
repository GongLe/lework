<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/included/resource.jsp" %>
<%response.setStatus(200);%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
	<title>  401 - 权限不足 </title>
</head>

<body class="error-page">
<div class="wrapper">
    <div class="desc"><i class="icon-warning-sign"></i><span>401 - 权限不足 </span></div>
    <div class="buttons">
        <a href="dashboard" class="btn btn-info btn-large btn-block"><i class="icon-home"></i>首页</a>
        <a href="javascript:;" onclick="window.history.go(-1);" class="btn btn-large btn-block"><i class="icon-arrow-left"></i>返回</a>
    </div>
</div>
</html>
