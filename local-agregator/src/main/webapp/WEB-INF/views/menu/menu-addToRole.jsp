<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        .box-title {
            border-bottom: 1px dashed #c5d0dc!important;
        }

        .west {
            width: 28%;
            min-height: 300px;
            border-right: 1px dashed #c5d0dc;
        }

        .west h6 {
            margin: 5px 10px;
        }
        .middle {
            width: 70%;
            min-height: 300px;
            overflow: auto;
        }
        .middle h6 {
            margin: 5px 10px;
        }
    </style>
    <title>添加菜单到角色</title>
</head>

<body>

<div class="modal-content" >
    <form  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-30 error-inline" >
        <div class="modal-header" style="padding:1px 15px">
            <small class="grey">
                添加菜单到角色
            </small>
        </div>

        <div class="modal-body ">
            <div class="row-fluid ">
                <div class="span12"  >
                  <div class="box  box-bordered-no ">
                      <div class="box-title no-margin-top no-padding-top no-padding-left" >
                          <div class="alert no-margin-bottom">${menu.name}(${menu.code})</div>
                      </div>
                      <div class="box-content no-padding ">

                          <div class="pull-left west" >
                              <h6  class="smaller lighter blue">  角色组</h6>
                              <ul id="orgTree" style="padding:5px 10px 0 5px;" ></ul>
                          </div>
                          <div class="pull-left middle"  >
                              <h6  class="smaller lighter blue text-left">角色</h6>
                              <div id="guanLianRoleContent">加载中...</div>
                          </div>
                      </div>
                  </div>
                  <!--/.box-->
                </div>

            </div>
        </div><!--/.modal-body-->
        <div class="modal-footer">
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                关闭
            </button>
        </div>
    </form>
</div>
<script>
    var $orgTree =  $('#orgTree','#inputForm') ;
    $(function(){
        var loadOrgTree = function () {
            $orgTree.tree({
                url: 'organization/getTree',
                method: 'get',
                checkbox: false,
                onLoadSuccess: function (node, data) {
                    //默认选择根节点.
                  //  var root = $orgTree.tree('getRoot');
                   // $orgTree.tree('select', root.target);
                    loadCheckRoleItems('','${menu.id}')
                },
                onSelect : function (node) {
                    loadCheckRoleItems(node.id,'${menu.id}')
                }
            });
        }

        /**
         *根据角色组ID,菜单ID ajax加载对应角色选择页面
         * @param roleGroupId
         * @param menuId
         */
        function loadCheckRoleItems(roleGroupId, menuId) {
            $('#guanLianRoleContent').load('menu/relatedRole?$SiteMesh=false&' + $.param({
                _d: lework.time(),
                roleGroupId: roleGroupId,
                menuId: menuId
            }));
        }
        using(['tree'], function () {
            loadOrgTree();
        }) //using
    })  //dom ready
</script>
</body>
</html>
