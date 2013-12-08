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
        <td class="table-lable">菜单名称：</td>
        <td >${menu.name}</td>
        <td class="table-lable">菜单代码：</td>
        <td >${menu.code}</td>
    </tr>
    <tr>
        <td class="table-lable">URL：</td>
        <td colspan="3">${menu.url}</td>
    </tr>
    <tr>
        <td class="table-lable">icon：</td>
        <td >  <i class="bigger-140 ${menu.icon}"></i> </td>
        <td class="table-lable">状态：</td>
        <td >  ${statusName} </td>
    </tr>
    <tr>
        <td class="table-lable">上级菜单：</td>
        <td colspan="3"> ${menu.parentName} </td>
    </tr>
    <tr>
        <td class="table-lable">创建人：</td>
        <td>${menu.createdBy}1</td>
        <td class="table-lable">创建时间：</td>
        <td><fmt:formatDate value="${menu.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
    </tr>
    <tr>
        <td class="table-lable">最后修改人：</td>
        <td>${menu.lastModifiedBy}</td>
        <td class="table-lable">最后修改时间：</td>
        <td><fmt:formatDate value="${menu.lastModifiedDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
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
                           关联的角色
                        </a>
                    </li>
                </ul>

                <div class="tab-content " style="padding:5px;">
                    <div id="profile3" class="tab-pane in active">
                        <div class="table-funtion-bar clear-both" style="margin:0 0 10px 0;padding: 2px 10px 2px;" >
                            <div class="btn-group" style="padding-top:4px;">
                                <button class="btn btn-small  no-border tooltips" id="refreshRole-function" data-original-title="刷新">
                                    <i class="icon-refresh"></i>
                                </button>
                            </div>
                            <div class="input-append no-margin-bottom pull-right">
                                <!--自定义搜索-->
                                <form id="roleSearchForm" name="roleSearchForm" class="no-margin no-padding">
                                    <span class="input-icon input-icon-right">
                                        <input class="input-medium" id="search" name="search" type="text" placeholder="姓名">
                                        <i class="icon-search blue" onclick="$('#roleSearchForm').submit()"></i>
                                    </span>
                                </form> <!--/#roleSearchForm-->
                            </div>
                        </div>
                        <table id="menuRelatedRoleTable"
                               class="table table-hover  table-nomargin table-bordered dataTable dataTable-nosort clear-both">
                        </table>
                    </div>
                </div>
            </div>
            <!--/.tabbable-->
        </td>
    </tr>

    </tbody>
</table>
<script>

    var oTable = $('#menuRelatedRoleTable');
    $(function () {
        /**全局:刷新关联角色table**/
        window.refreshRelatedRoleTable = function () {
            oTable.fnDraw();
        }
        /**
         *全局:解除菜单与角色关联关系,iframe回调函数
         * @param resp
         */
        window.removeRelatedCallback = function (resp) {
            var json = resp.attributes;
            lework.alert({content: json.message, type: json.type,
                timer:3000,
                onClose: null })
        };

        /**
         *全局:创建菜单与角色关联关系
         * @param resp
         */
        window.createRelateCallback = function (resp) {
            var json = resp.attributes;
            lework.alert({content: json.message, type: json.type,
                timer: 3000,
                onClose: null})
        };
        $('#refreshRole-function').click(refreshRelatedRoleTable) ;
        var menuId = '${menu.id}';
        oTable.dataTable({
            'aoColumns': [
                { 'mData': 'name', 'sTitle': '角色名称' },
                { 'mData': 'code', 'sTitle': '角色代码'}  ,
                { 'mData': 'type', 'sTitle': '类别'}  ,
                { 'mData': 'id', 'sTitle': '操作'}
            ],
            'aoColumnDefs': [
                {
                    'mRender': function (data, type, full) {
                        return  full['typeName'];
                    },
                    'aTargets': [2 ]
                },
                {
                    'mRender': function (data, type, full) {
                        var param = {
                            roleName: full.name,
                            roleId: full.id,
                            menuId: menuId
                        }
                        return ('<a href="javascript:;" class="removeRelatedRole" data-menu-id="{menuId}" data-role-id="{roleId}"' +
                                ' data-role-name="{roleName}" >解除</a>').format(param);
                    },
                    'aTargets': [3 ]
                },
                { bSortable: false,
                    aTargets: [3]
                },
                { 'sClass': '', 'aTargets': [3 ] }
            ],
            'sDom': 'rtip',
            sPaginationType: 'two_button',
            'iDisplayLength': 5,
            'bStateSave': false, /**state saving **/
            'bProcessing': true,
            'bServerSide': true,
            'fnServerData': lework.springDataJpaPageableAdapter,
            'sAjaxSource': '${ctx}/menu/getMenuRelatedRoleJson',
            'fnServerParams': function (aoData) {
                //附加请求参数
                aoData.pushArray({name: 'menuId', value: '${menu.id}'})
                aoData.pushArray($('#roleSearchForm').serializeArray());
            },
            'fnInitComplete': function () {     /**datatables ready**/
            },
            fnDrawCallback: function (oSettings) {
                //解除菜单角色关联关系
                oTable.find('.removeRelatedRole').confirmDelete({
                    text: '<span class="text-warning" >解除关联？</span>',
                    onDelete: function (ele) {
                        var param = $(this).data();
                        $.hiddenSubmit({
                            formAction: 'menu/removeRelatedRole',
                            data: [
                                {name: 'menuId', value: param.menuId } ,
                                {name: 'roleName', value: param.roleName } ,
                                {name: 'roleId', value: param.roleId }
                            ],
                            complete: function(response){
                                oTable.fnDraw();
                            }
                        })
                        return true;
                    }
                });
            }
        });//dataTables

        //搜索表单
        $('#roleSearchForm').submit(function (event) {
            event.preventDefault();
            oTable.fnDraw();
        });
    })  //dom ready


</script>
</body>
</html>
