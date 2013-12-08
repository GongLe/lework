<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>

</head>

<body>

<div class="modal-content" >
    <form action="user/update"  method="post" id="inputForm" name="inputForm"
          class="no-margin  error-inline" >
        <div class="modal-header" style="padding:5px 20px 5px 20px;">
            <small class="grey">
                正在<c:if test="${entity.isNew == true}" >新建用户</c:if><c:if test="${entity.isNew ==false}" >编辑用户“${entity.loginName}”</c:if>
            </small>
        </div>

<div class="modal-body ">
    <div class="row-fluid ">
      <div class="span7 well" >

        <!--隐藏域-->
        <form:hidden path="entity.id" />

          <table class="table  table-condensed">
              <thead>
                  <tr style="background: none;">
                      <th colspan="4" >基本信息</th>
                  </tr>
              </thead>
              <tbody>
                  <tr>
                      <td>1</td>
                      <td colspan="3">
                          <input class="input-medium"   type="text" id="email" name="email"   value="${entity.email}" placeholder="输入email">
                      </td>
                  </tr>
                  <tr>
                      <td>1</td>
                      <td colspan="3">
                          <input  class="input-medium" type="text" id="email" name="email"   value="${entity.email}" placeholder="输入email">
                      </td>
                  </tr>
                   <tr>
                      <td>1</td>
                      <td colspan="3">
                          <input  class="input-medium" type="text" id="email" name="email"   value="${entity.email}" placeholder="输入email">
                      </td>
                  </tr>
              </tbody>
          </table>
       </div>
      <div class="span5 well"></div>
    </div><!--/.row-fluid -->
</div><!--/.modal-body-->
        <div class="modal-footer">
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                <i class="icon-remove"></i>
                关闭
            </button>                                                                                                      <%--仅修改时可见--%>
            <button type="submit" class="btn btn-small btn-primary" >
                <i class="icon-ok"></i>
                保存
            </button>
        </div>
    </form>
</div>
<script>
    $(function(){
        //from validater
        $('#inputForm').targetIframe().validate({rules: {
            name: {
                required: true,
                normalChar :true,
                maxlength: 50
            },
            loginName : {
                required: true ,
                maxlength: 50,
                account :true,
                remote: {
                    url: 'user/validateLoginName', //后台处理程序
                    type: 'post',               //数据发送方式
                    dataType: 'json',           //接受数据格式
                    data: {                     //要传递的数据
                      userId  : function() {
                            return $('#id').val() ;
                        }
                    }
                }
            },
            email : {
                required: true ,
                maxlength: 50,
                remote: {
                    url: 'user/validateEmail', //后台处理程序
                    type: 'post',               //数据发送方式
                    dataType: 'json',           //接受数据格式
                    data: {                     //要传递的数据
                      userId  : function() {
                            return $('#id').val() ;
                        }
                    }
                }
            },
            plainPassword :{
                maxlength: 16
            },
            status :{
                required:true
            },
            description :{
                maxlength : 200
            }
        }, messages: {
            loginName :{
                remote :'该用户名已被注册'
            }   ,
            email:{
                remote :'该Email已被注册'
            }
        }
       }); //end validate
    })
</script>
</body>
</html>
