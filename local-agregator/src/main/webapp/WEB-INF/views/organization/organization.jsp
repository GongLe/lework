<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>组织机构</title>
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
            组织机构
        </li>
    </ul>   <!--.breadcrumb-->
</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">
            <div class="box">
                <div class="box-title no-margin-top">
                    <h3 class="no-margin-top">组织机构</h3>
                </div>
                <div class="box-content no-padding ">
                    <div class="table-funtion-bar clear-both">
                        <div class="btn-group">
                            <button class="btn no-border tooltips" id="create-function" data-original-title="新增" >
                                <i class="icon-plus"></i>
                            </button>
                            <button class="btn no-border tooltips" id="refresh-function" data-original-title="刷新">
                                <i class="icon-refresh"></i>
                            </button>
                            <button class="btn no-border tooltips" id="delete-function" style="display:none;" data-original-title="删除">
                                <i class="icon-trash"></i>
                            </button>
                        </div>
                    </div><!--/.table-funtion-bar-->

                    <div  id="orgTreeGridWrap">
                        <table id="orgTreeGrid" style="height:500px;" ></table>
                    </div>
                </div>
            </div>  <!--/.box-->
        </div>
    </div> <!--/.row-fluid-->
</div>  <!--/.page-content-->
<script>
  $(function(){
      //表单提交后,iframe回调函数
      window.actionCallback = function (resp) {
          var json = resp.attributes;
          $.colorbox.close();
          lework.alert({content: json.message, type: json.type })
          $('#orgTreeGrid').treegrid('reload');
      };

      window.deleteCallback = function (resp) {
          var json = resp.attributes;
          lework.alert({content: json.message, type: json.type })
          $('#orgTreeGrid').treegrid('reload');
      };
      //调整序号iframe回调函数
      window.doSortNumCallback = function (resp) {
          var json = resp.attributes;
          $.unblockUI();
          lework.alert({content: json.message, type: json.type })
          $('#orgTreeGrid').treegrid('reload');
      }
      var $orgTreeGrid = $('#orgTreeGrid');

      $orgTreeGrid.width($('#orgTreeGridWrap').width()*1) ;

      using(['treegrid'], function () {
        $orgTreeGrid.treegrid({
            url: 'organization/getTreeGrid',
            method: 'post',
            rownumbers: false,
            idField: 'id',
            treeField: 'name',
            columns: [
                [
                    {field: 'name', title: '组织名称', width: 300},
                    {field: 'code', title: '组织代码', width: $.browser.msie ? 200 : 210},
                    {field: 'typeName', title: '类别', width: 150},
                    {field: 'manager', title: '负责人', width: 150},
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
                    href: 'organization/update?$SiteMesh=false&id=' + row.id,
                    adjustY: '40%',
                    width: '700px',
                 //   height: '415px', /**设置高度固定,弹出层load之后,有新的DOM加入,高度会增加,影响实际显示**/
                    overlayClose: false,
                    scrolling: false
                })
            },
            onSelect: function (node) {
                //加载选中节点east界面
           //     loadEast(node.id);
            },
            onLoadSuccess: function () {
                $('.tooltips').tooltip();
                //修复 treegrid IE下border-right不可见bug.
                var $wrap = $('.datagrid-wrap');
                $wrap.width($wrap.width() - 2);
                //默认选择根节点.
                var root = $orgTreeGrid.treegrid('getRoot');
                if(!root)
                    return
                $orgTreeGrid.treegrid('select', root.id);
                //监听序号调整
                listenerSortAction();
                // bootstrap-tooltip
                $('.tooltips').tooltip();
                $('.confirmDelete').confirmDelete({onDelete: function () {
                    var id = $(this).data('id');
                    $.hiddenSubmit({
                        formAction: 'organization/delete',
                        data: [
                            {name: 'deleteId', value: id }
                        ],
                        complete: function () {
                            checkFunbarStatus(false);
                        }
                    })
                    return true;
                }
                });

            }
        });
    })
    //监听序号调整
    var listenerSortAction = function(){
        var id ;
        $('.sortNumAction' ,'#orgTreeGridWrap').click(function(event){
            event.preventDefault() ;
            if($(this).hasClass('up')){   //上移
                $.blockUI();
                id = $(this).data('id');
                $.hiddenSubmit({
                    formAction: 'organization/upSortNum',
                    data: [  {name: 'id', value: id } ]
                })

            } else if ($(this).hasClass('down')){  //下移
                $.blockUI();
                id = $(this).data('id');
                $.hiddenSubmit({
                    formAction: 'organization/downSortNum',
                    data: [  {name: 'id', value: id } ]
                })
            }
        })
    }  //listenerSortAction
    //新增
    $('#create-function').click(function () {
        $(this).colorbox({
            href :'organization/update?$SiteMesh=false' ,
            adjustY:'40%',
            width: '700px',
          //  height :'415px' , /**设置高度固定,弹出层load之后,有新的DOM加入,高度会增加,影响实际显示**/
            overlayClose: false,
            scrolling: false
        })
    });
    //刷新
    $('#refresh-function').click(function () {
        $('#orgTreeGrid').treegrid('reload');
        //重置function bar状态
        checkFunbarStatus(false);
    });

    //删除widget
    $('#delete-function').confirmDelete({text: '<span class="text-warning">确认删除？</span>',
        onDelete: function () {
            var row = $('#orgTreeGrid').treegrid('getSelected');
            $.hiddenSubmit({
                formAction: 'organization/delete',
                data: [  {name: 'deleteId', value: row.id } ],
                complete : function(){  checkFunbarStatus(false); }
            })
            return true;
        }
    });

      /**
       *根据选择的菜单节点,ajax加载对应基本信息,关联信息
       * @param orgId  菜单ID
       */
      function loadEast(orgId) {

          $('#eastOrgRelated').load('organization/eastOrgRelated?$SiteMesh=false',  {'orgId': orgId, '_d': lework.time() })
      }

    //根据所选行,修改function bar状态.
    function checkFunbarStatus(hasSelected) {
        if (hasSelected == true) {
            $('#delete-function').show();
        } else {
            $('#delete-function').hide();
        }
    }
    }) //dom ready
</script>

</body>
</html>