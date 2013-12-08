<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<body>
<div id="menuTreeGridContainer" style="max-height:500px;">
    <div class="table-funtion-bar clear-both" style="margin:5px 0 10px 0;padding: 4px 10px 4px;" id="menuBar"  >
        <div class="btn-group" style="padding-top:4px;">
            <button class="btn btn-small  no-border tooltips" id="refreshMenu-function" data-original-title="刷新" data-placement="bottom">
                <i class="icon-refresh"></i>
            </button>
        </div>
        <button class="btn btn-small btn-danger no-border tooltips" style="margin-top:4px;" id="saveMenu-function" data-original-title="保存" data-placement="bottom" >
            <i class="icon-save"></i>保存
        </button>
    </div> <!--/.table-funtion-bar-->
    <table id="menuTreeGrid" style="width:640px;height:450px;" ></table>
</div>
<script>
    $(function () {
        window.saveRelatedMenuCallback = function (resp) {
            var json = resp.attributes;
            lework.alert({content: json.message, type: json.type,
                timer: 1500,
                onClose: null })
        }
        var checkedIdsArr = ${checkedIds}, roleId = '${roleId}';
        var $menuTreeGrid = $('#menuTreeGrid');
        var hasCheck;
        var saveBtnTpl = ' <button  class="btn btn-minier btn-danger" id="saveMenu" title="保存"><i class="icon-save"></i>保存</button>';
        $menuTreeGrid.width($('#shouQuan').width() * 0.95);
        using(['treegrid'], function () {
            $menuTreeGrid.treegrid({
                url: 'menu/getTreeGrid',
                method: 'post',
                rownumbers: false,
                idField: 'id',
                treeField: 'name',
                columns: [
                    [
                        {field: 'id', title: '选择', width: 60, align: 'center', formatter: function (value, row, index) {
                            hasCheck = checkedIdsArr.indexOf(value) != -1 ? 'checked="checked"' : '';
                            return '<input type="checkbox" {0} name="checkedMenuId"  id="{1}" />'.format(hasCheck, value);
                        }},
                        {field: 'name', title: '菜单名称', width: 160},
                        {field: 'code', title: '菜单代码', width: 115, align: 'left'},
                        {field: 'url', title: 'URL', width: 270}
                    ]
                ],
                onClickRow: function (row) {
                },
                onSelect: function (node) {
                },
                onLoadSuccess: function () {
                    $('.tooltips').tooltip();
                    //修复 treegrid IE下border-right不可见bug.
                    var $wrap = $('.datagrid-wrap');
                    $wrap.width($wrap.width() - 2);
                    //默认选择根节点.
                    var root = $menuTreeGrid.treegrid('getRoot');

                }
            });

        }); //using
        $('#refreshMenu-function').on('click',function(){
            $menuTreeGrid.treegrid('reload');
        })
        $('#saveMenu-function').on('click',  function (e) {
            e.preventDefault();

            $('#menuTreeGridContainer').block();
            var selectedIdsJson = $('input[name="checkedMenuId"]:checked', '#menuTreeGridContainer')
                    .map(function () {
                        return {'name': 'checkedMenuId', 'value': this.id };
                    })
                    .get();
            selectedIdsJson.push({'name': 'roleId', value: roleId});
            //保存操作
            $.hiddenSubmit({
                formAction: 'roleControl/saveRelatedMenu',
                data: selectedIdsJson,
                complete: function () {
                    $('#menuTreeGridContainer').unblock()
                }
            })

        })
    }) //dom ready
</script>
</body>
</html>