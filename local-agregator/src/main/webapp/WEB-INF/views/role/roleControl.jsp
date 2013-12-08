<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>角色权限控制</title>
    <style type="text/css">
        .box-title{border-bottom: 1px dashed #c5d0dc!important;}
        .west{ width:15%;min-height:550px;border-right:1px dashed  #c5d0dc;  }
        .west h5{  margin:5px 10px; }
        .middle{
            width:20%;min-height:550px;border-right:1px dashed  #c5d0dc;
            overflow: auto;}
        .middle h5{margin:5px 10px;}
        .east{width:63%; padding:0 0 5px 10px;}
        .east h5{margin:5px 10px;}
    </style>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>
        <li class="active">
            角色权限控制
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">

            <div class="box  box-bordered">
                <div class="box-title no-margin-top" >
                    <h4  ><i class="icon-group"></i> 角色权限控制</h4>
                </div>
                <div class="box-content no-padding ">

                    <div class="pull-left west" >
                          <h5  class="header smaller lighter blue">  角色组</h5>
                          <ul id="orgTree" style="padding:5px 10px 0 5px;" ></ul>
                    </div>
                    <div class="pull-left middle"  >
                        <h5  class="header smaller lighter blue ">角色</h5>
                        <ul id="roleTree" style="padding:5px 10px 0 5px;" ></ul>
                        <div id="alertNullRoleData" class="alert alert-warning" style="margin:0 8px;">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="icon-remove"></i>
                            </button>
                             暂无数据
                            <br>
                        </div><!--/.alert-->
                    </div>
                    <div class="pull-left east" id="shouQuan">
                            <h5  class="header smaller lighter blue "> 角色授权</h5>
                            <div class="alert alert-warning">
                                <button type="button" class="close" data-dismiss="alert">
                                    <i class="icon-remove"></i>
                                </button>
                                 暂无数据
                                <br>
                            </div><!--/.alert-->
                    </div>
                </div>
            </div>
            <!--/.box-->
        </div>
    </div>
</div>

<!--/.page-content-->
<script>
    var $orgTree =  $('#orgTree') ,$roleTree = $('#roleTree') ;
    $(function () {
        /**
        * 加载角色组
         * 加载队列:角色组 ==>  角色 ==> 角色授权页面
         */
        var loadOrgTree = function () {
            $orgTree.tree({
                url: 'organization/getTree',
                method: 'get',
                checkbox: false,
                onLoadSuccess: function (node, data) {
                    //默认选择根节点.
                    var root =  $('#orgTree').tree('getRoot');
                    $('#orgTree').tree('select', root.target);
                },
                onSelect : function (node) {
                    loadRoleTree(node.id);
                }
            });
        }
        /**
         *根据角色组()加载所属角色
         * @param groupId
         */
        var loadRoleTree = function (groupId) {
            $roleTree.tree({
                url: 'roleControl/getRoleTreeByGroupId?' + $.param({'groupId': groupId }),
                method: 'get',
                checkbox: false,
                onLoadSuccess: function (node, data) {
                    //默认选择根节点.
                    var root = $('#roleTree').tree('getRoot');
                    if (root) {
                        $('#alertNullRoleData').hide();
                        $('#roleTree').tree('select', root.target);
                    } else {
                        $('#alertNullRoleData').show();
                    }
                },
                onSelect: function (node) {
                    loadShouQuan(node.id);
                }
            });
        }
        /**
         * 根据角色ID加载角色授权页面
         * @param roleId
         */
        var loadShouQuan = function (roleId) {
            $('#shouQuan').load('roleControl/tabs?' + $.param({'roleId': roleId, '$SiteMesh': 'false' }));
        }

        using(['tree'], function () {
            loadOrgTree();
        }) //using

    })  //dom ready


</script>
</body>
</html>