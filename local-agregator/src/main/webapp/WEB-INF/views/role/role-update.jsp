<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>

</head>

<body>

<div class="modal-content" >
    <form action="role/update"  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-40 error-inline" >
        <div class="modal-header" style="padding:5px 20px 5px 20px;">
            <h3>
                <small class="grey">
                 正在<c:if test="${entity.isNew == true}" >新建角色</c:if><c:if test="${entity.isNew ==false}" >编辑角色“${entity.name}”</c:if>
                </small>
            </h3>
        </div>

<div class="modal-body ">
    <div class="row-fluid ">
      <div class="span12"  >

        <!--隐藏域-->
        <form:hidden path="entity.id" />

        <div class="control-group">
            <label class="control-label " for="name">角色名称</label>
            <div class="controls">
                <input  class="input-xlarge" type="text" id="name" name="name"   value="${entity.name}"
                        autocomplete="off" placeholder="输入角色名称">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="code">角色代码</label>
            <div class="controls">
                <input class="input-xlarge" type="text" id="code" name="code" value="${entity.code}"
                       autocomplete="off" placeholder="输入角色代码">
            </div>
        </div>

          <div class="control-group">
              <label class="control-label" for="groupId">所属角色组</label>
              <div class="controls">
                 <input type="text" id="groupId" name="groupId"  value="${entity.groupId}"  style="width:284px;height:28px;" >
                  <div class="help-inline">
                      <a href="javascript:;" onclick="$('#groupId').combotree('clear');">清空</a>
                  </div>
              </div>
          </div>

          <div class="control-group">
              <label class="control-label" for="type">类别</label>
              <div class="controls">
                  <form:select  path="entity.type" cssClass="input-xlarge" cssStyle="width:284px"  >
                      <form:options  items="${typeList}"  itemValue="code" itemLabel="name"/>
                  </form:select>
              </div>
          </div>

        <div class="control-group">
            <label class="control-label" for="status">状态</label>
            <div class="controls">
                <form:select  path="entity.status" cssClass="input-xlarge" cssStyle="width:284px"  >
                    <form:options  items="${statusList}"  itemValue="code" itemLabel="name"/>
                </form:select>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label" for="description">描述</label>
            <div class="controls">
                <textarea class="input-xlarge" rows="3" id="description" name="description"   placeholder="输入描述信息" > ${entity.description}</textarea>
            </div>
        </div>
    </div>

    </div>
</div><!--/.modal-body-->
        <div class="modal-footer">
            <button id="submitBtn"  type="submit" class="btn btn-small btn-primary" >
                保存
            </button>
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                关闭
            </button>
        </div>
    </form>
</div>
<script>
    $(function(){
        var isNew = ${entity.isNew} ,
                selectedGroupId = '${roleGroupId}' ;
        //from validater
        $('#inputForm').targetIframe().validate({
            submitHandler: function (form) {
                $('#submitBtn').prop('disable', true).text('保存中....')
                form.submit();
            },
            rules: {
                name: {
                    required: true,
                    maxlength: 50
                },
                code: {
                    required: true,
                    account:true,
                    maxlength: 50,
                    remote: {
                        url: 'role/validateRoleCode', //后台处理程序
                        type: 'post',               //数据发送方式
                        dataType: 'json',           //接受数据格式
                        data: {                     //要传递的数据
                            roleId: function () {
                                return $('#id').val();
                            }
                        }
                    }
                },
                status: {
                    required: true
                },
                description: {
                    maxlength: 200
                }
            }, messages: {
                code: {
                    remote: '该值已被占用'
                }
            }
        }); //end validate


        setTimeout(function () {
            //easyui loader
            using(['tree','combotree'], function () {
                $('#groupId').combotree({
                    url: 'organization/getTree?',
                    method: 'get',
                    onSelect: function (node) {
                    },
                    onLoadSuccess: function () {
                        //新增时,默认选中父页面 角色组.
                       if(isNew && selectedGroupId){
                           $('#groupId').combotree('setValue',selectedGroupId)
                       }
                    }
                });

            }) //using
        }, 100);//timeout
    })  //dom ready
</script>
</body>
</html>
