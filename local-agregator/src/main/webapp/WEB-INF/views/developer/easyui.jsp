<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>整合easyui兼容性</title>
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
            测试整合easyui兼容性
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">
            <h2>easyui merge</h2>

            <div style="padding:5px;">
                <a href="javascript:;" class="easyui-linkbutton btn" data-options="toggle:true"><i
                        class="icon-plus"></i> Add</a>
                <a href="javascript:;" class="easyui-linkbutton btn btn-danger" data-options="toggle:true"><i
                        class="icon-trash"></i> Remove</a>
                <a href="javascript:;" class="easyui-linkbutton btn btn-primary" data-options="toggle:true"><i
                        class="icon-save"></i> Save</a>
                <a href="javascript:;" class="easyui-linkbutton btn btn-info" data-options="toggle:true"> Text
                    Button</a>
            </div>
            <div style="margin:10px 0;">
                <a href="javascript:;" class="easyui-linkbutton  " id="confirm1">Confirm</a>
                <a href="javascript:;" class="easyui-linkbutton   btn-info btn-small" id="prompt1">Prompt</a>
            </div>
            <div class="span4">
                <h4>easyui-tree</h4>
                <ul class="easyui-tree"
                    data-options="url: '${ctx}/static/plugins/easyui/tree_data1.json',method:'get',animate:true,lines:true"  >
                </ul>


            </div>
            <div class="span6">
                <h4>1:combobox</h4>
                <select class="easyui-combobox" name="state" data-options="height:30,width:200" style="width:200px;">
                    <option value="MO">Missouri</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                </select>
                <input class="easyui-combotree" value="122"
                       data-options="url:'${ctx}/static/plugins/easyui/tree_data1.json',method:'get'" style="width:200px;">

                <p> <h4 class="red">2:easyui tooltip与bootstrap有冲突.不推荐使用.</h4>
                </p>
                <h4>3:tree grid</h4>
                <table title="" class="easyui-treegrid" style="width:500px;height:400px"
                               data-options="
                        url: '${ctx}/static/plugins/easyui/treegrid_data1.json',
                        method: 'get',
                        rownumbers: false ,
                        idField: 'id',
                        treeField: 'name'
                    ">
                            <thead>
                            <tr>
                                <th data-options="field:'name'" width="220">Name</th>
                                <th data-options="field:'size'" width="100" align="right">Size</th>
                                <th data-options="field:'date'" width="150">Modified Date</th>
                            </tr>
                            </thead>
                </table>
            </div><!--/.span6-->
            <div class="span6">
                <h2>Basic Dialog</h2>
                <div class="demo-info">
                    <div class="demo-tip icon-tip"></div>
                    <div>Click below button to open or close dialog.</div>
                </div>
                <div style="margin:10px 0;">
                    <a href="javascript:void(0)" class="easyui-linkbutton btn-info" onclick="$('#dlg').dialog('open')">Open</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton btn-danger" onclick="$('#dlg').dialog('close')">Close</a>
                </div>
                <div id="dlg" class="easyui-dialog" title="Basic Dialog" data-options="iconCls:'icon-save'" style="width:400px;height:200px;padding:10px">
                    The dialog content.
                </div>
            </div>
        </div>
    </div>
</div>
<!--/.page-content-->
<script>
    $(function () {
        //test easyui
        using(['menu', 'messager','tree','combobox','treegrid'], function () {
            $('#confirm1').click(function () {
                $.messager.confirm('My Title', 'Are you confirm this?', function (res) {
                    if (res) {
                        alert('confirmed: ' + res);
                    }
                });
            })
            $('#prompt1').click(function () {
                $.messager.prompt('My Title', 'Please type something', function (res) {
                    if (res) {
                        alert('you type: ' + res);
                    }
                });
            });


        })
    })  //dom ready


</script>


</body>
</html>