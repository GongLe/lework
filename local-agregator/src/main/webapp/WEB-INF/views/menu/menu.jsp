<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>菜单管理</title>
    <style>
        .datagrid-row-selected .icon-chevron-up,
        .datagrid-row-selected .icon-chevron-down{
            color: #fff;
        }
    </style>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>
        <li class="active">
            菜单管理
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">
            <div class="box">
                <div class="box-title no-margin-top" style="padding: 0">
                    <h3 class="no-margin-top">菜单管理</h3>
                </div>
                <div class="box-content no-padding ">
                    <div class="table-funtion-bar clear-both">
                        <div class="btn-group">
                            <button class="btn no-border tooltips" id="create-function" data-original-title="新增" >
           N                     <i class="icon-plus"></i>
                            </button>
                            <button class="btn no-border tooltips" id="refresh-function" data-original-title="刷新">
                                <i class="icon-refresh"></i>
                            </button>
                            <button class="btn no-border tooltips" id="update-function" data-original-title="编辑">
                                <i class="icon-edit"></i>
                            </button>
                            <button class="btn no-border tooltips" id="delete-function" style="display:none;" data-original-title="删除">
                                <i class="icon-trash"></i>
                            </button>

                        </div>
                        <button class="btn btn-danger no-border tooltips " id="addToRole-function" style="display:none;font-size:12px"
                                data-original-title="添加菜单到角色">
                            <i class="icon-group"></i> 添加菜单到角色
                        </button>
                    </div><!--/.table-funtion-bar-->

                    <div class="pull-left" id="menuTreeGridWrap">
                        <table id="menuTreeGrid" style="width:540px;height:500px;" ></table>
                    </div>

                    <div id="eastMenuRelated"  style="margin-left:526px;padding:0 20px 0 20px;">
                        加载中...
                    </div><!--/.eastMenuRelated-->
                </div>
            </div>  <!--/.box-->

        </div>
    </div> <!--/.row-fluid-->
</div>

<!--/.page-content-->
<script>
    var selectedMenuId;
$(function(){
    var $menuTreeGrid
    //表单提交后,iframe回调函数
    window.actionCallback = function (resp) {
        var json =  resp.attributes ;
        $.colorbox.close();
        lework.alert({content: json.message, type: json.type })
        $('#menuTreeGrid').treegrid('reload');
    };

    window.deleteCallback = function (resp) {
        var json =  resp.attributes ;
        lework.alert({content: json.message, type: json.type })
        $('#menuTreeGrid').treegrid('reload');
    };
    //调整序号iframe回调函数
    window.doSortNumCallback = function(resp){
        var json =  resp.attributes ;
        $.unblockUI();
        lework.alert({content: json.message, type: json.type  })
        $('#menuTreeGrid').treegrid('reload');
    }

    using(['treegrid'], function () {
        $menuTreeGrid = $('#menuTreeGrid');
        $menuTreeGrid.treegrid({
            url: 'menu/getTreeGrid',
            method: 'post',
            rownumbers: false,
            idField: 'id',
            treeField: 'name',
            columns: [
                [
                    {field: 'name', title: '菜单名称', width: 150},
                    /*   {field: 'code', title: '菜单代码', width: 115, align: 'left'},*/
                    {field: 'url', title: 'URL', width: 260} ,
                    {field: 'id', title: '序号', align: 'left', width: 100, formatter: function (value, row) {
                        var html = '&nbsp;&nbsp;&nbsp;&nbsp;';
                        if (row.levelIndex > 0) {
                            html += '<a href="javascript:;" class="sortNumAction up" data-id="{id}" title="上移序号">' +
                                    '<i class="icon-chevron-up"></i></a>';
                        } else {
                            html += '&nbsp;&nbsp;&nbsp;';
                        }
                        if (row.levelIndex < row.levelSize - 1) {
                            html += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" class="sortNumAction down" data-id="{id}"  title="下移序号">' +
                                    '<i class="icon-chevron-down"></i></a>';
                        }
                        return html.format(row);
                    }
                    }
                ]
            ],
            onClickRow: function (row) {
                checkFunbarStatus(true)
            },
            onDblClickRow: function (row) {
                //双击编辑
                $.colorbox({
                    href: 'menu/update?$SiteMesh=false&id=' + row.id,
                    adjustY: '40%',
                    width: '700px',
                    height: '415px', /**设置高度固定,弹出层load之后,有新的DOM加入,高度会增加,影响实际显示**/
                    overlayClose: false,
                    scrolling: false
                })
            },
            onSelect: function (node) {
                //加载选中节点east界面
                loadEast(node.id);
                checkFunbarStatus(true)
            },
            onLoadSuccess: function () {
                $('.tooltips').tooltip();
                //修复 treegrid IE下border-right不可见bug.
                var $wrap = $('.datagrid-wrap');
                $wrap.width($wrap.width() - 2);
                //默认选择根节点.
                var root = $menuTreeGrid.treegrid('getRoot');
                $menuTreeGrid.treegrid('select', root.id);
                //监听序号调整
                listenerSortAction();
            }
        });
    })
    //监听序号调整
    var listenerSortAction = function(){
        var id ;
        $('.sortNumAction' ,'#menuTreeGridWrap').on('click',function(event){
            event.preventDefault() ;
            if($(this).hasClass('up')){   //上移
                $.blockUI();
                id = $(this).data('id');
                $.hiddenSubmit({
                    formAction: 'menu/upSortNum',
                    data: [  {name: 'id', value: id } ]
                })

            } else if ($(this).hasClass('down')){  //下移
                $.blockUI();
                id = $(this).data('id');
                $.hiddenSubmit({
                    formAction: 'menu/downSortNum',
                    data: [  {name: 'id', value: id } ]
                })
            }
        })
    }  //listenerSortAction
    //新增
    $('#create-function').on('click',function () {
        $(this).colorbox({
            href :'menu/update?$SiteMesh=false' ,
            adjustY:'40%',
            width: '700px',
            height :'415px' , /**设置高度固定,弹出层load之后,有新的DOM加入,高度会增加,影响实际显示**/
            overlayClose: false,
            scrolling: false
        })
    });
    //刷新
    $('#refresh-function').on('click',function () {
        $('#menuTreeGrid').treegrid('reload');
        //重置function bar状态
        checkFunbarStatus(false);
    });
    //编辑
    $('#update-function').on('click',function () {
       var row =   $menuTreeGrid.treegrid('getSelected');
        //双击编辑
        $.colorbox({
            href: 'menu/update?$SiteMesh=false&id=' + row.id,
            adjustY: '40%',
            width: '700px',
            height: '415px', /**设置高度固定,弹出层load之后,有新的DOM加入,高度会增加,影响实际显示**/
            overlayClose: false,
            scrolling: false
        }) ;
    });

    //删除
    $('#delete-function').confirmDelete({text: '<span class="text-warning">确认删除？</span>',
        onDelete: function () {
            var row = $('#menuTreeGrid').treegrid('getSelected');
            $.hiddenSubmit({
                formAction: 'menu/delete',
                data: [  {name: 'deleteId', value: row.id } ],
                complete : function(){  checkFunbarStatus(false); }
            })
            return true;
        }
    });
    //添加菜单到角色
    $('#addToRole-function').on('click',function () {
        //当前选中的菜单
        var selectRow =  $menuTreeGrid.treegrid('getSelected');
        $(this).colorbox({
            href :'menu/addMenuToRole?$SiteMesh=false&menuId=' + selectRow.id  ,
            adjustY:'40%',
            width: '760px',
            overlayClose: false,
            scrolling: false,
            onClosed:function(){
                //关闭弹出层后,刷新菜单关联的角色 @see menu-east.jsp @178 line
                refreshRelatedRoleTable();
            }
        })
    });

    /**
     *根据选择的菜单节点,ajax加载对应基本信息,关联信息
     * @param menuId  菜单ID
     */
    function loadEast(menuId) {

        $('#eastMenuRelated').load('menu/eastMenuRelated?$SiteMesh=false', {'menuId': menuId, '_d': lework.time() })
    }

    //根据所选行,修改function bar状态.
    function checkFunbarStatus(hasSelected) {
        if (hasSelected == true) {
            $('#delete-function,#addToRole-function').show();
        } else {
            $('#delete-function,#addToRole-function').hide();
        }
    }
})
</script>

</body>
</html>