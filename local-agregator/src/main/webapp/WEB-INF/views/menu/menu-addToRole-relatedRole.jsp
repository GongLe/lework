<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>

<body>
<div id="roleItemsContainer">
  <c:forEach items="${roles}" var="role">
      <c:if test="${role.selected == true}">
          <div class="checkbuttonOk  panelcheck" data-menu-id="${menuId}" data-role-id="${role.id}"  data-role-name="${role.name}" >
              <div data-id="${role.id}" class="checktext" title="${role.name}"> <i class="icon-user blue"></i>&nbsp;&nbsp;${role.name}
              </div>
              <div class="triangleOk"></div>
          </div>
      </c:if>
      <c:if test="${role.selected == false}">
          <div class="checkbuttonNo  panelcheck" data-menu-id="${menuId}" data-role-id="${role.id}"  data-role-name="${role.name}">
              <div data-id="${role.id}" class="checktext" title="${role.name}"> <i class="icon-user blue"></i>&nbsp;&nbsp;${role.name}
              </div>
              <div class="triangleNo"></div>
          </div>
      </c:if>
  </c:forEach>
</div>
<script>

    $(function () {
        $('#roleItemsContainer').slimscroll({height: '450px'});

        $('#roleItemsContainer').on('click', '.panelcheck', function () {
            if ($(this).hasClass('checkbuttonNo')) {   //选中
                $(this).removeClass('checkbuttonNo')
                        .addClass('checkbuttonOk')
                        .children('.triangleNo')
                        .removeClass('triangleNo')
                        .addClass('triangleOk');

                createRoleRelateMenu($(this).data())
            } else {                                    //取消选中
                $(this).removeClass('checkbuttonOk')
                        .addClass('checkbuttonNo')
                        .children('.triangleOk')
                        .removeClass('triangleOk')
                        .addClass('triangleNo')

                removeRoleRelatedMenu($(this).data())
            }
        })  ;

        /**
         * 创建菜单与角色关联关系
         */
        function createRoleRelateMenu(param) {

            $.hiddenSubmit({
                formAction: 'menu/createRelateRole',
                data: [
                    {name: 'menuId', value: param.menuId } ,
                    {name: 'roleName', value: param.roleName } ,
                    {name: 'roleId', value: param.roleId }
                ],
                complete: null
            })
        }

        /**
         *解除菜单与角色关联关系
         */
        function removeRoleRelatedMenu(param) {
            $.hiddenSubmit({
                formAction: 'menu/removeRelatedRole',
                data: [
                    {name: 'menuId', value: param.menuId } ,
                    {name: 'roleName', value: param.roleName } ,
                    {name: 'roleId', value: param.roleId }
                ],
                complete: null
            })
        }
    })  //dom ready
</script>
</body>
</html>
