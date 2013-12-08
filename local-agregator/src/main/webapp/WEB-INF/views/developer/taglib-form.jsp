<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP tag lib demo </title>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-home home-icon"></i>
            <a href="${ctx}/dashboard">home</a>
        </li>
        <li><a href="${ctx}/developer">开发者中心</a></li>
        <li class="active">
            JSP tag lib demo
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span4">
            <div class="well sidebar-nav">
                <ul class="nav nav-list">
                    <li class="nav-header">Spring Form Taglib</li>
                    <li class="active"><a href="#form">The form tag</a></li>
                    <li  ><a href="#input">The input tag</a></li>
                </ul>
            </div><!--/.well -->
        </div><!--/.span3-->
        <div class="span8" id="form" name="form">
            <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/view.html" target="_blank">spring taglib官方文档</a>
           <h3>The form tag</h3>
            <code>
                &lt;form:form commandName="user"&gt; \n
                     &lt;form:input path="loginName" /&gt; \n
                 &lt;/form:form&gt;
            </code>
            <h3>bootstrap checkboxes</h3>
            <code>
               &lt;form:bscheckboxes path="roleList" items="\${allRoles}" itemLabel="name" itemValue="id" /&gt;
            </code>
            <h3>bootstrap radiobuttons</h3>
            <code>
                &lt;form:bsradiobuttons path="status" items="\${allStatus}" labelCssClass="inline"/&gt;
            </code>
        </div> <!--/.span9-->
    </div>
</div>
<!--/.page-content-->
<script>
    $(function () {

    })  //dom ready
</script>


</body>
</html>