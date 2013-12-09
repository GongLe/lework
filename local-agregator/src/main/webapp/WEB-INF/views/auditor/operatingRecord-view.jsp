<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head> </head>

<body>

<div class="modal-content" >
    <form action="role/update"  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-40 error-inline" >
        <div class="modal-header" style="padding:5px 20px 5px 20px;">
            <h3>
                <small class="grey">
                操作日志(${entity.username}:<fmt:formatDate value="${entity.startDate}" pattern="yyyy/MM/dd mm:ss" />)
                </small>
            </h3>
        </div>

<div class="modal-body" id="logInputBody">
    <div class="row-fluid ">
      <div class="span12"  >
          <table class="table table-bordered table-striped">
              <thead>
              <tr>
                  <th colspan="2" >(${entity.username}:<fmt:formatDate value="${entity.startDate}" pattern="yyyy/MM/dd mm:ss" />)</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                  <td> <code>操作人</code> </td>  <td> ${entity.username}</td>
              </tr>
              <tr>
                  <td> <code>IP</code> </td>  <td> ${entity.ip}</td>
              </tr>
              <tr>
                  <td> <code>操作目标</code> </td>  <td> ${entity.operatingTarget}</td>
              </tr>
              <tr class="<c:if test="${entity.state == 0}">error</c:if>" >
                  <td> <code>状态</code> </td>  <td><c:if test="${entity.state == 0}">异常</c:if> <c:if test="${entity.state == 1}">正常</c:if> </td>
              </tr>
              <tr>
                  <td> <code>模块名称</code> </td>  <td> ${entity.module}</td>
              </tr>
              <tr>
                  <td> <code>功能名称</code> </td>  <td> ${entity.function}</td>
              </tr>

              <tr>
                  <td> <code>耗时</code> </td>  <td> ${entity.processingTime}(毫秒)</td>
              </tr>
              <tr>
                  <td> <code>请求详情</code> </td>  <td> ${entity.remark}</td>
              </tr>

              </tbody>
          </table>
        </div>
    </div>
</div><!--/.modal-body-->
        <div class="modal-footer">

            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                关闭
            </button>
        </div>
    </form>
</div>
<script>
    $(function(){
        $('#logInputBody').slimscroll({
            height:'500px'
        }); //slimscroll
    })  //dom ready
</script>
</body>
</html>
