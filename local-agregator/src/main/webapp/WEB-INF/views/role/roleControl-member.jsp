<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>角色成员</title>
</head>

<body>
<div class="table-funtion-bar clear-both" style="margin:4px 0 10px 0;padding: 4px 10px 4px;" id="memberBar"  >
    <div class="btn-group" style="padding-top:4px;">
        <button class="btn btn-small  no-border tooltips" id="refresh-function" data-original-title="刷新">
            <i class="icon-refresh"></i>
        </button>
    </div>
    <button class="btn btn-small btn-danger no-border tooltips" style="margin-top:4px;" id="addMember-function" data-original-title="添加成员" >
        <i class="icon-user"></i>添加成员
    </button>
    <div class="input-append no-margin-bottom pull-right">
        <!--自定义搜索-->
        <form id="userSearchForm" name="userSearchForm" class="no-margin no-padding">
            <span class="input-icon input-icon-right">
                <input class="input-medium" id="search" name="search" type="text" placeholder="姓名">
                <i class="icon-search blue" onclick="$('#userSearchForm').submit()" ></i>
            </span>
        </form> <!--/#userSearchForm-->
    </div>
</div> <!--/.table-funtion-bar-->
<table id="roleRelatedUserTable"
       class="table table-hover  table-nomargin table-bordered dataTable dataTable-nosort clear-both"  >
</table>
<script>
    var userTable = $('#roleRelatedUserTable');
    $(function () {
        /**全局:刷新关联角色table**/
        window.refreshRelatedUserTable = function () {
               userTable.fnDraw();
        }
        /**
         *全局:解除角色与用户关联关系
         * @param resp
         */
        window.removeRelatedCallback = function (resp) {
            var json = resp.attributes;
            lework.alert({content: json.message, type: json.type ,
                timer:1500,
                onClose: null })
        };

        /**
         *全局:创建角色与用户关联关系
         * @param resp
         */
        window.createRelateCallback = function (resp) {
            var json = resp.attributes;
            lework.alert({content: json.message, type: json.type ,
                timer: 1500,
                onClose: null})
        };

        $('#memberBar .tooltips').tooltip({
            placement: 'bottom'
        });

        var roleId = '${role.id}';
        userTable.dataTable({
            'aoColumns': [
                { 'mData': 'name', 'sTitle': '用户名' },
                { 'mData': 'loginName', 'sTitle': '登录名'}  ,
                { 'mData': 'status', 'sTitle': '状态'}  ,
                { 'mData': 'orgName', 'sTitle': '部门'}  ,
                { 'mData': 'id', 'sTitle': '操作'}
            ],
            'aoColumnDefs': [
                {
                    'mRender': function (data, type, full) {
                        if (data == 'enable') {
                            return   '<i class="icon-flag bigger-130 green" title="启用的"></i>';
                        }
                        return    '<i class="icon-flag bigger-130 red" title="禁用的"></i>';
                    },
                    'aTargets': [2 ]
                },
                {
                    'mRender': function (data, type, full) {
                        var param = {
                            userName: full.name,
                            userId: full.id,
                            roleId: roleId
                        }
                        return ('<a href="javascript:;" class="removeRelatedRole" data-user-id="{userId}" data-role-id="{roleId}"' +
                                ' data-user-name="{userName}" >解除</a>').format(param);
                    },
                    'aTargets': [4 ]
                },
                { bSortable: false,
                    aTargets: [4]
                },
                { 'sClass': '', 'aTargets': [3 ] }
            ],
            'sDom': 'rt<"table-footer clearfix"ip>',
            sPaginationType: 'two_button',
            'iDisplayLength': 5,
            'bStateSave': false, /**state saving **/
            'bProcessing': true,
            'bServerSide': true,
            'fnServerData': lework.springDataJpaPageableAdapter,
            'sAjaxSource': '${ctx}/roleControl/geRoleRelatedUserJson',
            'fnServerParams': function (aoData) {
                //附加请求参数
                aoData.pushArray({name: 'roleId', value: '${role.id}'})
                aoData.pushArray($('#userSearchForm').serializeArray());
            },
            'fnInitComplete': function () {     /**datatables ready**/
            },
            fnDrawCallback: function (oSettings) {
            }
        });//dataTables

        //搜索表单
        $('#userSearchForm').submit(function (event) {
            event.preventDefault();
            userTable.fnDraw();
        });

        //添加用户到角色
        $('#addMember-function').click(function () {

            $(this).colorbox({
                href: 'roleControl/addMember?' + $.param({'$SiteMesh': false, 'roleId': roleId}),
                adjustY: '40%',
                width: '760px',
                overlayClose: false,
                scrolling: false,
                onClosed: function () {
                    //关闭弹出层后,刷新角色关联的用户
                    refreshRelatedUserTable();
                }
            })
        })
        //刷新
        $('#refresh-function').click(function () {
            userTable.fnDraw();
        })

    })  //dom ready
</script>
</body>
</html>