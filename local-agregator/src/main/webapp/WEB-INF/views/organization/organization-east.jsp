<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>

</head>

<body>
<%--右侧基本信息,关联信息ajax页面--%>
<table class="table table-condensed table-hover" style="min-width:300px;border:1px solid #e3e3e3">
    <thead>
    <tr style="background: none;">
        <th colspan="4"><i class="icon-pushpin blue"></i>&nbsp;&nbsp;基本信息</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="table-lable">组织名称：</td>
        <td >${entity.name}</td>
        <td class="table-lable">组织代码：</td>
        <td >${entity.code}</td>
    </tr>
    <tr>
        <td class="table-lable">组织简称：</td>
        <td > ${entity.shortName} </td>
        <td class="table-lable">上级组织：</td>
        <td > ${entity.parentName} </td>
    </tr>
    <tr>
        <td class="table-lable">类别：</td>
        <td > ${entity.typeName} </td>
        <td class="table-lable">状态：</td>
        <td >${entity.statusName}</td>
    </tr>
    <tr>
        <td class="table-lable">负责人：</td>
        <td>${entity.manager}1</td>
        <td class="table-lable">联系电话：</td>
        <td>${entity.phone}</td>
    </tr>

    </tbody>
</table>
<table class="table  table-condensed " style="min-width:300px;">

    <tbody>
    <tr>
        <td colspan="8" rowspan="5" class="no-border no-padding-left">
            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab3">

                    <li class="active">
                        <a data-toggle="tab" href="#profile3">
                          <%--  <i class="blue icon-user bigger-110"></i>--%>
                            所属用户
                        </a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="profile3" class="tab-pane in active">
                        <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>

                        <p>Raw denim you probably haven't heard of them jean shorts Austin.</p>
                    </div>
                </div>
            </div>
            <!--/.tabbable-->
        </td>
    </tr>

    </tbody>
</table>
<script>
    $(function () {

    })  //dom ready
</script>
</body>
</html>
