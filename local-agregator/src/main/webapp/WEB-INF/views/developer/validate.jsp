<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>jquery validate </title>
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
            jquery validate
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
                    <li class="active"><a href="#method">验证类型</a></li>
                    <li ><a href="#custom-method">自定义验证类型</a></li>
                </ul>
            </div><!--/.well -->
        </div><!--/.span3-->
        <div class="span8 pull-right" id="method" name="method">
           <a href="http://jqueryvalidation.org/documentation/" target="_blank">官方文档</a>
           <h3>验证方法</h3>
            <ul>
                <li><a  ><code>required</code> – Makes the element required.</a></li>
                <li><a  ><code>remote</code> – Requests a resource to check the element for validity.</a></li>
                <li><a  ><code>minlength</code> – Makes the element require a given minimum length.</a></li>
                <li><a href="/maxlength-method"><code>maxlength</code> – Makes the element require a given maxmimum length.</a></li>
                <li><a href="/rangelength-method"><code>rangelength</code> – Makes the element require a given value range.</a></li>
                <li><a href="/min-method"><code>min</code> – Makes the element require a given minimum.</a></li>
                <li><a href="/max-method"><code>max</code> – Makes the element require a given maximum.</a></li>
                <li><a href="/range-method"><code>range</code> – Makes the element require a given value range.</a></li>
                <li><a href="/email-method"><code>email</code> – Makes the element require a valid email</a></li>
                <li><a href="/url-method"><code>url</code> – Makes the element require a valid url</a></li>
                <li><a href="/date-method"><code>date</code> – Makes the element require a date.</a></li>
                <li><a href="/dateISO-method"><code>dateISO</code> – Makes the element require an ISO date.</a></li>
                <li><a href="/number-method"><code>number</code> – Makes the element require a decimal number.</a></li>
                <li><a href="/digits-method"><code>digits</code> – Makes the element require digits only.</a></li>
                <li><a href="/creditcard-method"><code>creditcard</code> – Makes the element require a credit card number.</a></li>
                <li><a href="/equalTo-method"><code>equalTo</code> – Requires the element to be the same as another one</a></li>
            </ul>
        </div> <!--/.span8-->
        <div class="span8 pull-right" id="custom-method" name="custom-method">
           <h3>自定义验证类型</h3>
            <ul>

            </ul>
        </div> <!--/.span8-->

    </div>
</div>
<!--/.page-content-->
<script>
    $(function () {

    })  //dom ready
</script>


</body>
</html>