<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="zh-CN"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="zh-CN"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="zh-CN"> <![endif]-->
<!--[if gt IE 8]><!--><html lang="zh-CN"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <%@ include file="/WEB-INF/included/resource.jsp" %>
    <le:stylesheet src="/static/assets/ace/css/login.css" />

</head>
<body class="login">
<div class="wrapper">
    <h1  style="color:#ffffff">lework</h1>
    <div class="login-body">
        <h2>账号登陆</h2>
        <form action="${ctx}/login" method="post" class="form-validate" >
            <c:if test="${not empty shiroLoginFailure }" >
                <div class="red">${shiroLoginFailure}</div>
            </c:if>

            <div class="control-group">
                <div class="email controls">
                    <input type="text" name="username" placeholder="用户名" class="input-block-level"  >
                </div>
            </div>
            <div class="control-group">
                <div class="pw controls">
                    <input type="password" name="password"  placeholder="密码" class="input-block-level"  >
                    <!--autocomplete="off"-->
                </div>
            </div>
            <c:if test="${not empty sessionScope.showCaptcha == true }" >
                <div class="control-group">
                    <div class="controls">
                        <input name="captcha"  autocomplete="off" placeholder="验证码"  style="width: 40%;" >
                        <img id="captchaImg" title="点击刷新验证码" src="login/getCaptcha" width="70" height="28" onclick="reloadValidateCode();"  />
                    </div>
                </div>
            </c:if>
            <div class="submit">
                <shiro:user>
                <p class="padding-10">
                    <strong>
                        <shiro:principal/>
                    </strong>检测到你已经登录，<a href="${ctx }/logout">注销</a> 或者
                    <a href="${ctx }/dashboard">进入系统</a>
                </p>
                </shiro:user>
                <shiro:guest>
                    <%--
                        	<select class="selection" name="rememberMe" id="filter_EQI_state" size="25">
			           		<option value="">
								无
			                </option>
			                <option value="25200">
								在一周内
			                </option>
			                <option value="2592000">
								在一个月内
			                </option>
			                <option value="31536000">
								在一个年内
			                </option>
			           </select>
			           --%>
                    <div class="remember">
                        <label for="rememberMe">记住密码&nbsp;&nbsp;</label><input type="checkbox" name="rememberMe" id="rememberMe"/>
                    </div>
                    <div class="pull-right">
                        <button type="submit" class="btn btn-primary" >登陆</button>
                    </div>
                </shiro:guest>

            </div>
        </form>
        <div class="forget">
            <a href="#"><span>忘记密码？</span></a>
        </div>
    </div>
</div>
<script>
    function reloadValidateCode() {
        $("#captchaImg").attr("src","login/getCaptcha?_=" + (new Date()).getTime()  );
    }
</script>
</body>
</html>
