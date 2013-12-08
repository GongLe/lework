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
                正在<c:if test="${entity.isNew == true}" >新建用户</c:if><c:if test="${entity.isNew ==false}" >查看用户“${entity.loginName}”</c:if>
            </small>
        </div>

<div class="modal-body">
    <div class="row-fluid " >
        <div class="span4">
            <table class="table  table-condensed table-bordered table-hover"   style="background-color:#f8f9ff" >
                <thead>
                <tr style="background: none;">
                    <th colspan="4" ><i class="icon-user"></i>&nbsp;&nbsp;基本信息</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>姓名：</td>
                    <td colspan="3">${entity.name}</td>
                </tr>
                <tr>
                    <td>用户名：</td>
                    <td colspan="3">${entity.loginName}</td>
                </tr>
                 <tr>
                    <td>所属机构：</td>
                    <td colspan="3">org tree</td>
                </tr>
                 <tr>
                    <td>Email：</td>
                    <td colspan="3">${entity.email}</td>
                </tr>
                <tr>
                    <td>联系电话：</td>
                    <td colspan="3">${entity.telphone}</td>
                </tr>
                 <tr>
                    <td>手机号码：</td>
                    <td colspan="3">${entity.mobile}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="span4 " >
            <table class="table  table-condensed table-bordered"   style="background-color:#f8f9ff" >
                <thead>
                <tr style="background: none;">
                    <th colspan="4" >所属角色</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="4" rowspan="5">
                        <div>角色tree</div>
                        <div>角色tree</div>
                        <div>角色tree</div>
                        <div>角色tree</div>
                        <div>角色tree</div>
                        <div>角色tree</div>
                        <div>角色tree</div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="span4" >
            <table class="table  table-condensed table-bordered"   style="background-color:#f8f9ff" >
                <thead>
                <tr style="background: none;">
                    <th colspan="4" >所属菜单</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colspan="4" rowspan="5">
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                        <div>菜单tree</div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div><!--/.row-fluid -->
</div><!--/.modal-body-->
        <div class="modal-footer">
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                <i class="icon-remove"></i>
                关闭
            </button>                                                                                                      <%--仅修改时可见--%>
        </div>
    </form>
</div>
<script>
    $(function(){

    })
</script>
</body>
</html>
