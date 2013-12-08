<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="zh-CN"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="zh-CN"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="zh-CN"> <![endif]-->
<!--[if gt IE 8]><!--><html lang="zh-CN"><!--<![endif]-->
<head>
    <title><sitemesh:title/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <%@ include file="/WEB-INF/included/resource.jsp" %>

    <sitemesh:head/>
</head>

<body>
<%@ include file="/WEB-INF/included/navbar.jsp" %>
<div class="main-container container-fluid">
    <%@ include file="/WEB-INF/included/sidebar.jsp" %>
    <div class="main-content">
        <sitemesh:body></sitemesh:body>
    </div> <!--/.main-content-->
</div> <!--/.main-container-->
</body>
</html>