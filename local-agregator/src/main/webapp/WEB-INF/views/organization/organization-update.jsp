<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head> </head>
<body>

<div class="modal-content" >
    <form action="organization/update"  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-30 error-inline" >
        <div class="modal-header"  >
            <h3>
                正在<c:if test="${entity.isNew == true}" >新建组织</c:if><c:if test="${entity.isNew ==false}" >编辑组织“${entity.name}”</c:if>
            </h3>
        </div>

        <div class="modal-body" >
            <div  id="inputBody">
            <div class="row-fluid ">
                <div class="span12"  >

                    <!--隐藏域-->
                    <form:hidden path="entity.id"  />
                    <div class="control-group">
                        <label class="control-label" for="code">组织编码</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="code" name="code" value="${entity.code}" placeholder="输入组织代码" >
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="name">组织名称</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="name" name="name"   value="${entity.name}" placeholder="输入组织名称">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="name">组织简称</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="shortName" name="shortName"   value="${entity.shortName}"
                                   data-rule-required="false" placeholder="输入组织简称">
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="parentId">上级组织</label>
                        <div class="controls">
                            <input id="parentId" name="parentId"  style="width:284px;height:28px;" value="${entity.parentId}"   >
                            <div class="help-inline">
                                <a href="javascript:;" onclick="$('#parentId').combotree('clear');">清空</a>
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
                        <label class="control-label " for="name">负责人</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="manager" name="manager"   value="${entity.manager}"
                                   data-rule-required="false"    data-rule-maxlength="50" placeholder="输入负责人">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="name">联系电话</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="phone" name="phone"   value="${entity.phone}"
                                   data-rule-required="false"     data-rule-maxlength="50"  data-rule-isTel="true" placeholder="输入联系电话">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="innerPhone">内线</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="innerPhone" name="innerPhone"   value="${entity.innerPhone}"
                                   data-rule-required="false"    data-rule-maxlength="50" placeholder="输入内线电话">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="fax">传真</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="fax" name="fax"   value="${entity.fax}"
                                   data-rule-required="false"     data-rule-maxlength="50" placeholder="输入传真">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="fax">地址</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="address" name="address"   value="${entity.address}"
                                   data-rule-required="false"     data-rule-maxlength="100" placeholder="输入地址">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label " for="fax">网址</label>
                        <div class="controls">
                            <input autocomplete="off" class="input-xlarge" type="text" id="url" name="url"   value="${entity.url}"
                                   data-rule-required="false"    data-rule-maxlength="100" data-rule-url="true" placeholder="输入网址">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="description">描述</label>
                        <div class="controls">
                            <textarea id="description"  name="description" rows="3" cssClass="input-xlarge"
                                      data-rule-required="false"  data-rule-maxlength="100" >${entity.description}</textarea>
                        </div>
                    </div>
                </div>

            </div>
            </div><!--/#inputBody-->
        </div><!--/.modal-body-->
        <div class="modal-footer">
            <button id="submitBtn"  type="submit" class="btn btn-small btn-primary"   >
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
        $('#inputBody').slimscroll({
            height:'400px'
        }); //slimscroll

        //from validater
        $('#inputForm').targetIframe().validate({
            submitHandler: function (form) {
                $('#submitBtn').prop('disable',true).text('保存中....')
                form.submit() ;
            },
            rules: {
            name: {
                required: true,
                normalChar :true,
                maxlength: 50
            },
            code : {
                required: true ,
                maxlength: 50,
              //  account :true,
                remote: {
                    url: 'organization/validateOrgCode', //后台处理程序
                    type: 'post',               //数据发送方式
                    dataType: 'json',           //接受数据格式
                    data: {                     //要传递的数据
                        orgId  : function() {
                            return $('#id').val() ;
                        }
                    }
                }
            },
            status :{
                required:true
            }
        }, messages: {
            code:{
                remote :'该值已被占用'
            }
        }
        }); //end validate

        setTimeout(function () {
            using(['combotree'], function () {
                $('#parentId').combotree({
                    url: 'organization/getTree?ignoreNodeId=${entity.id}',
                    method: 'get',
                    onSelect: function (node) {
                        //  $('#parentId').val(node.id);
                    },
                    onLoadSuccess: function () {
                      //  $('.combo-text', '#inputForm').data('rule-required', false); //修复IE8 validate
                    }
                });
            }) //using
        }, 150);//timeout

    })  //dom ready
</script>
</body>
</html>
