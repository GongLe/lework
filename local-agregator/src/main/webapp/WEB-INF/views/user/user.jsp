<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>
        <li class="active">
            用户管理
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">

            <div class="box box-bordered">
                <div class="box-title no-margin-top" style="border-bottom:1px dashed #c5d0dc;">
                   <%-- <h3 class="blue">用户管理</h3>--%>
                    <h4 class="inner"><i class="icon-user"></i>用户管理</h4>
                </div>
                <div class="box-content no-padding ">
                    <div class="pull-left" style="width:18%;min-height:600px;border-right:1px dashed  #c5d0dc;">
                        <%--<h5 class="header smaller lighter blue" style="margin:5px 10px;" >角色组</h5>--%>
                          <ul id="orgTree" style="padding:10px 10px 0 5px;" ></ul>
                    </div>
                    <div class="pull-left" style="width:78%; padding:0 0 5px 10px;" >
                        <div class="table-funtion-bar clear-both">
                            <div class="btn-group">
                                <button data-toggle="dropdown" class="btn no-border dropdown-toggle">
                                    <i id="checkIcon" class="icon-check-empty bigger-120"></i>
                                    <span class="caret"></span>
                                </button>

                                <ul class="dropdown-menu dropdown-default">
                                    <li id="selectedAll">
                                        <a href="javascript:;"  >全选</a>
                                    </li>
                                    <li id="cancelSelected" >
                                        <a href="javascript:;" >取消</a>
                                    </li>
                                </ul>
                            </div>
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
                             <%--   <button class="btn btn-danger no-border tooltips" id="role-function"  data-original-title="分配角色">
                                    <i class="icon-group"></i>
                                </button>--%>
                                <button class="btn btn-danger no-border tooltips" id="resetPassword-function" style="display:none;"   data-original-title="重置密码">
                                    <i class="icon-key"></i>
                                </button>
                            </div>
                            <div class="input-append no-margin-bottom pull-right">
                                <!--自定义搜索-->
                                <form id="searchForm" name="searchForm" class="no-margin no-padding">
                                        <span class="input-icon input-icon-right">
                                            <input class="input-medium" id="search" name="search" type="text" placeholder="用户名/姓名">
                                            <i class="icon-search blue" onclick="$('#searchForm').submit()" ></i>
                                        </span>
                                </form> <!--/#searchForm-->
                            </div>

                        </div><!--/.table-funtion-bar-->
                        <table id="table-list"
                               class="table table-hover  table-nomargin table-bordered dataTable dataTable-nosort clear-both">
                        </table>
                    </div><!--/.pull-left-->
                </div>
            </div>
            <!--/.box-->
        </div>
    </div>
</div>


<!--/.page-content-->
<script>
    var $orgTree =  $('#orgTree') ,
            oTable = $('#table-list');
    $(function () {
        //表单提交后,iframe回调函数
        window.actionCallback = function (resp) {
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content: resp.attributes.message, type: resp.attributes.type })
        };
        //重置密码回调
        window.resetPasswordCallback = function (resp) {
            $.colorbox.close();
            lework.alert({content: resp.attributes.message, type: resp.attributes.type })
        };
        window.deleteCallback = function (resp) {
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content:resp.attributes.message ,type: resp.attributes.type ,width:'250px'})
        };
        //自定义搜索表单
        $('#searchForm').submit(function(event){
            event.preventDefault() ;
            oTable.fnDraw();
        });
        //easy ui org tree
        using(['tree'], function () {
            $orgTree.tree({
                url: 'organization/getTree',
                method: 'get',
                checkbox: false,
                onLoadSuccess: function (node, data) {
                },
                onSelect : function (node) {
                    oTable.fnDraw();
                }
            });
        }) //using


        oTable.dataTable({
            'aoColumns': [
                { 'mData': 'name', 'sTitle': '姓名' },
                { 'mData': 'loginName', 'sTitle': '用户名'}  ,
                { 'mData': 'email', 'sTitle': 'Email'}  ,
                { 'mData': 'status', 'sTitle': '状态'}  ,
                { 'mData': 'createdBy', 'sTitle': '创建人'}  ,
                { 'mData': 'id', 'sTitle': '操作'}
            ],
            'aoColumnDefs': [
                {
                    'mRender': function (data, type, full) {
                        //  console.log(data)
                        if (data == 'enable') {
                            return   '<i class="icon-flag bigger-130 green" title="启用的"></i>';
                        }
                        return    '<i class="icon-flag bigger-130 red" title="禁用的"></i>';
                    },
                    'aTargets': [3 ]
                },
                {
                    'mRender': function (data, type, full) {
                      //  console.log(data)
                        return  $('#tableActionTpl').render({id: data});
                    },
                    'aTargets': [5 ]
                },
                { bSortable: false,
                    aTargets: [5]
                } ,
                //   { 'bVisible': false,  'aTargets': [ 1 ] },
                { 'sClass': 'center', 'aTargets': [3] }
            ],
            'sDom': 'rt<"table-footer clearfix"ip>',
            'bStateSave': false  , /**state saving **/
            'bProcessing': true ,
            'bServerSide': true,
            'fnServerData': lework.springDataJpaPageableAdapter,
            'sAjaxSource': '${ctx}/user/getDatatablesJson',
             'fnServerParams' :function(aoData ){
                 var orgNode = $.fn.tree && $('#orgTree').tree('getSelected');
                 if (orgNode)
                     aoData.push({ 'name': 'orgId', 'value': orgNode.id  });
                 aoData.pushArray($('#searchForm').serializeArray());
             },
            'fnInitComplete': function () {     /**datatables ready**/
                //lework.initDatatablesSearchHolder('用户名/姓名');
            } ,
            fnDrawCallback :function(oSettings ){
                //resert function bar
                checkFunbarStatus(false) ;
                // bootstrap-tooltip
                $('.tooltips').tooltip();
                $('.confirmDelete').confirmDelete({onDelete: function () {
                    var id = $(this).data('id') ;
                    $.hiddenSubmit({
                        formAction: 'user/delete',
                        data: [  {name: 'deleteId', value:  id } ],
                        complete : function(){  checkFunbarStatus(false); }
                    })
                    return true;
                 }
                });

            }
        });//dataTables

        //多行选择
        oTable.tableMutilDelete({
            afterSelect: function () {
                var size = oTable.find('tr.selected').size();
                checkFunbarStatus(size > 0);
            }
        });

        //重置密码
        $('#resetPassword-function').click(function () {
            var ids = [];
            oTable.find('tr.selected .confirmDelete').each(function () {
                ids.push($(this).data('id'))
            });
            $(this).colorbox({
                href: 'user/resetPassword?' + $.param({'$SiteMesh': false, 'userIds': ids.join(',')}),
                adjustY: '40%',
                width: '700px',
                overlayClose: false,
                scrolling: false
            })
        });
         //新建
        $('#create-function').click(function () {
            $(this).colorbox({
                href :'user/update?$SiteMesh=false' ,
                adjustY:'40%',
                width: '700px',
                overlayClose: false,
                scrolling: false
            })
        });
        //刷新
        $('#refresh-function').click(function () {
            oTable.fnDraw();
            //重置function bar状态
            checkFunbarStatus(false);
        });
        //双击进入修改页面
        oTable.on('dblclick','tbody>tr',function(event){
            event.preventDefault();
            $(this).find('.update').trigger('click');
        });

        //多行删除
        $('#delete-function').confirmDelete({text: '<span class="text-warning">确认删除多条记录？</span>',
            onDelete: function () {
                var ids = [];
                oTable.find('tr.selected .confirmDelete').each(function () {
                    ids.push($(this).data('id'))
                });
                ids = ids.join(',');
                $.hiddenSubmit({
                    formAction: 'user/delete',
                    data: [  {name: 'deleteIds', value: ids } ] ,
                    complete : function(){  checkFunbarStatus(false); }
                })
            return true;
        }
        });

        //取消选择
        $('#cancelSelected').click(function () {
            oTable.find('tbody>tr').removeClass('selected warning');
            checkFunbarStatus(false);
        });
        //全选行
        $('#selectedAll').click(function () {
            oTable.find('tbody>tr').addClass('selected warning')
            checkFunbarStatus(true);
        });

        //根据所选行,修改function bar状态.
        function checkFunbarStatus(hasSelected) {
            if (hasSelected == true) {
                $('#checkIcon').removeClass('icon-check-empty').addClass('icon-check')
                $('#delete-function,#resetPassword-function').show();
            } else {
                $('#checkIcon').removeClass('icon-check').addClass('icon-check-empty');
                $('#delete-function,#resetPassword-function').hide();
            }
        }

    })  //dom ready


</script>

<!-- ===============JsRender template ===================
    @see http://www.jsviews.com/#samples/jsr/converters
-->

<!--table action template-->
<script id="tableActionTpl" type="text/x-jsrender">
    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
        <a class="green tooltips view"  href="user/view?id={{:id}}&$SiteMesh=false"   data-original-title="查看"
           onclick="$(this).colorbox({adjustY:'40%',width:'900px',overlayClose:false,scrolling:true,scrolling:false });" >
            <i class="icon-zoom-in bigger-140 filterSelected"></i>
        </a>
        <a class="blue tooltips update" href="user/update?id={{:id}}&$SiteMesh=false" data-original-title="编辑"
           onclick="$(this).colorbox({ adjustY:'40%',width:'700px',overlayClose:false,scrolling:false });" >
            <i class="icon-edit bigger-140 filterSelected"></i>
        </a>
        <a class="red tooltips confirmDelete" href="javascript:;" data-id="{{:id}}"  data-original-title="删除">
            <i class="icon-trash bigger-140 filterSelected"></i>
        </a>
    </div>
</script>

</body>
</html>