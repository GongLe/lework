<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>
</head>

<body>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>

        <li class="active">
            账户信息
        </li>
    </ul>  <!--.breadcrumb-->

</div>

<div class="page-content">

    <div class="row-fluid">
        <div class="span10">
            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <li class="active">
                        <a data-toggle="tab" href="#baseInfo">
                            <i class="green icon-home bigger-110"></i>
                            基本信息
                        </a>
                    </li>

                    <li class="">
                        <a data-toggle="tab" href="#profile">
                            头像设置
                        </a>
                    </li>
                    <li class="">
                        <a data-toggle="tab" href="#profile">
                            联系信息
                        </a>
                    </li>

                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            Dropdown
                            <b class="caret"></b>
                        </a>

                        <ul class="dropdown-menu dropdown-info">
                            <li>
                                <a data-toggle="tab" href="#dropdown1">@fat</a>
                            </li>

                            <li>
                                <a data-toggle="tab" href="#dropdown2">@mdo</a>
                            </li>
                        </ul>
                    </li>
                </ul>

<div class="tab-content">
        <div id="baseInfo" class="tab-pane active">
            <div class="box">

                <div class="box-content ">
                    <form action="${ctx}/account/profile"  method="post" name="inputForm" id="inputForm"
                          class="form-horizontal">
                        <div class="control-group">
                            <label for="loginName" class="control-label">帐号</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-user"></i></span>
                                    <input class="input-xlarge"  type="text" id="loginName"  name="loginName"  value="${entity.loginName}" disabled="disabled" >
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="name" class="control-label">姓名</label>
                            <div class="controls">
                               <input  style="width:297px" type="text" placeholder="姓名"  name="name" id="name"   value="${entity.name}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="niceName" class="control-label">昵称</label>
                            <div class="controls">
                                    <input style="width:297px" type="text" placeholder="昵称"  name="niceName" id="niceName"  value="${entity.niceName}">
                            </div>
                        </div >
                        <div class="control-group">
                            <label for="email" class="control-label">Email</label>
                            <div class="controls">
                                    <input style="width:297px"  type="text" placeholder="电子邮箱"  name="email" id="email" value="${entity.email}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="plainPassword" class="control-label">修改密码</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-key"></i></span>
                                    <input class="input-xlarge" type="password" placeholder="输入新密码" name="plainPassword" id="plainPassword"    >
                                </div>
                            </div>
                        </div>
                        <div class="control-group" style="display:none;"  >
                            <label for="plainPassword2" class="control-label">确认新密码</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-key"></i></span>
                                    <input type="password" class="input-xlarge"  placeholder="确认新密码" name="plainPassword2" id="plainPassword2"   >
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="createdDate" class="control-label">创建时间</label>
                            <div class="controls">
                                <div class="input-append input-prepend">
                                    <span class="add-on"> <i class="icon-time"></i></span>
                                    <input class="input-xlarge"  type="text" name="createdDate" id="createdDate"     disabled="disabled"
                                           value="<fmt:formatDate value="${entity.createdDate}" pattern="yyyy-MM-dd HH:mm" />">
                                </div>
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <button class="btn btn-primary" type="submit">
                                <i class="icon-ok bigger-110"></i>
                                保存
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset">
                                <i class="icon-undo bigger-110"></i>
                                重置
                            </button>
                        </div>

                    </form>
                </div>
            </div>

        </div>

        <div id="profile" class="tab-pane">
            <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>
        </div>

    </div>
</div>  <!--/.tabbable-->



        </div> <!--/.span12-->
    </div> <!--/.row-fluid-->
</div><!--/.page-content-->
<%@include file="/WEB-INF/included/notify.jsp" %>
  <script>
      $(function () {
          //控制密码修改输入框
          $('#plainPassword').on('change.pwd', function () {
              $('#plainPassword2').parents('.control-group').show();
              $(this).off('change.pwd');
          });

          //from validater
          $('#inputForm').validate({rules: {
              name: {
                  required: true,
                  minlength: 1,
                  maxlength: 50
              },
              plainPassword: {
                  required: false,
                  minlength: 6,
                  maxlength: 32
              },
              plainPassword2: {
                  required: false,
                  minlength: 6,
                  maxlength: 32,
                  equalTo: "#plainPassword"
              } , email: {
                  required: true ,
                  email: true
              }
          }, messages: {

          }});

      });
  </script>
</body>
</html>