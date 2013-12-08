<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>开发者文档 </title>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-home home-icon"></i>
            <a href="${ctx}/dashboard">home</a>
        </li>

        <li class="active">
            开发者中心
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span6">
            <ul class="nav nav-tabs nav-stacked">
                <li  ><a href="easyui">jquery easyui</a></li>
                <li  ><a href="taglib-form">jsp taglib::spring </a></li>
                <li  ><a href="validate">jquery validate </a></li>
            </ul>
        </div>
    </div>
</div>
<!--/.page-content-->
<script>
    $(function () {

    })  //dom ready
</script>


</body>
</html>