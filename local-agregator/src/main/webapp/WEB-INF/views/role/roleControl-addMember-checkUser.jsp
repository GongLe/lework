<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="userCheckItems">
    <c:forEach items="${users}" var="user">
        <c:if test="${user.selected == true}">
            <div class="checkbuttonOk  panelcheck" data-role-id="${roleId}" data-user-id="${user.id}"  data-user-name="${user.name}" >
                <div data-id="${user.id}" class="checktext" title="${user.name}"> <i class="icon-user blue"></i>&nbsp;&nbsp;${user.name}
                </div>
                <div class="triangleOk"></div>
            </div>
        </c:if>
        <c:if test="${user.selected == false}">
            <div class="checkbuttonNo  panelcheck" data-role-id="${roleId}" data-user-id="${user.id}"  data-user-name="${user.name}">
                <div data-id="${user.id}" class="checktext" title="${user.name}"> <i class="icon-user blue"></i>&nbsp;&nbsp;${user.name}
                </div>
                <div class="triangleNo"></div>
            </div>
        </c:if>
    </c:forEach>
</div>
<script>

    $(function(){
        $('#userCheckItems').on('click', '.panelcheck', function () {
            if ($(this).hasClass('checkbuttonNo')) {   //选中
                $(this).removeClass('checkbuttonNo')
                        .addClass('checkbuttonOk')
                        .children('.triangleNo')
                        .removeClass('triangleNo')
                        .addClass('triangleOk');
                createRelateUser($(this).data());
            } else {                                    //取消选中
                $(this).removeClass('checkbuttonOk')
                        .addClass('checkbuttonNo')
                        .children('.triangleOk')
                        .removeClass('triangleOk')
                        .addClass('triangleNo');
                removeRelatedUser($(this).data());
            }
        });

        /**
         * 添加角色成员
         */
        function createRelateUser(param) {

            $.hiddenSubmit({
                formAction: 'roleControl/createRelateUser',
                data: [
                    {name: 'userId', value: param.userId } ,
                    {name: 'userName', value: param.userName } ,
                    {name: 'roleId', value: param.roleId }
                ],
                complete: null
            })
        }

        /**
         *解除角色成员
         */
        function removeRelatedUser(param) {
            $.hiddenSubmit({
                formAction: 'roleControl/removeRelatedUser',
                data: [
                    {name: 'userId', value: param.userId } ,
                    {name: 'userName', value: param.userName } ,
                    {name: 'roleId', value: param.roleId }
                ],
                complete: null
            })
        }

    });
</script>
</body>
</html>