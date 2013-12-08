<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>

<body>

<div class="modal-content" >
    <form action="user/resetPassword"  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-40 error-inline" >
        <div class="modal-header" style="padding:5px 20px 5px 20px;">
            <small class="grey">
                正在重置用户密码
            </small>
        </div>

<div class="modal-body">
    <div  id="userInputBody">
    <div class="row-fluid ">
      <div class="span12" >
          <div   class="alert alert-warning"  >
              <button type="button" class="close" data-dismiss="alert">
                  <i class="icon-remove"></i>
              </button>
              重置用户("${userNames}")密码
              <br>
          </div>
          <input type="hidden" name="$SiteMesh" value="false">
          <c:forEach items="${users}" var="user">
             <input type="hidden" name="userIds" value="${user.id}">
          </c:forEach>
          <div class="control-group">
              <label class="control-label " for="plainPassword">新密码</label>
              <div class="controls">
                  <div class="input-prepend">
                      <span class="add-on"><i class="icon-key"></i></span>
                      <input style="width: 245px;" type="password"  name="plainPassword" id="plainPassword"
                         placeholder="新密码"  >
                  </div>
              </div>
          </div>
          <div class="control-group">
              <label class="control-label " for="plainPassword">确认密码</label>
              <div class="controls">
                  <div class="input-prepend">
                      <span class="add-on"><i class="icon-key"></i></span>
                      <input style="width: 245px;" type="password"  name="plainPassword2" id="plainPassword2"
                         placeholder="确认密码"  >
                  </div>
              </div>
          </div>

    </div> <!--/.row-fluid-->
    </div><!--/#inputBody-->
</div><!--/.modal-body-->
        <div class="modal-footer">
            <button id="submitBtn" type="submit" class="btn btn-small btn-primary" >
                保存
            </button>
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                关闭
            </button>                                                                                                      <%--仅修改时可见--%>

        </div>
    </form>
</div>
<script>
    $(function(){

        //from validater
        $('#inputForm').targetIframe().validate({
            submitHandler: function (form) {
                $('#submitBtn').prop('disable', true).text('保存中....')
                form.submit();
            },
            rules: {

                plainPassword: {
                    required:true,
                    minlength: 6,
                    maxlength: 32
                },
                plainPassword2: {
                    required:true,
                    minlength: 6,
                    maxlength: 32,
                    equalTo:'#plainPassword'
                }
            }, messages: {
                plainPassword2:{
                    equalTo:'密码输入不一致'
                }
            }
        }); //end validate
    })
</script>
</body>
</html>
