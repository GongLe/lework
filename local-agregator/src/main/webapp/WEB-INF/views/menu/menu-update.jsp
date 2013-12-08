<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>

</head>

<body>

<div class="modal-content" >
    <form action="menu/update"  method="post" id="inputForm" name="inputForm"
          class="no-margin form-horizontal offset-30 error-inline" >
        <div class="modal-header" style="padding:5px 20px 5px 20px;">
            <h3>
                <small class="grey">
                正在<c:if test="${entity.isNew == true}" >新建菜单</c:if><c:if test="${entity.isNew ==false}" >编辑菜单“${entity.name}”</c:if>
                </small>
            </h3>
        </div>

        <div class="modal-body ">
            <div class="row-fluid ">
                <div class="span12"  >

                    <!--隐藏域-->
                    <input type="hidden" name="$SiteMesh" value="false">
                    <form:hidden path="entity.id"  />
                    <input type="hidden" name="$SiteMesh" value="false">
                    <div class="control-group">
                        <label class="control-label " for="name">菜单名称</label>
                        <div class="controls">
                            <input autocomplete="off"  class="input-xlarge" type="text" id="name" name="name"   value="${entity.name}" placeholder="输入菜单名称">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="code">菜单代码</label>
                        <div class="controls">
                            <input  autocomplete="off" class="input-xlarge" type="text" id="code" name="code" value="${entity.code}" placeholder="输入菜单代码">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="parentId">上级菜单</label>
                        <div class="controls">
                            <input id="parentId" name="parentId"  style="width:284px;height:28px;" value="${entity.parentId}"   >
                            <div class="help-inline">
                                <a href="javascript:;" onclick="$('#parentId').combotree('clear');">清空</a>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="url">URL</label>
                        <div class="controls">
                            <input class="input-xlarge" type="text" id="url" name="url" value="${entity.url}" placeholder="输入菜单URL">
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
                        <label class="control-label" for="icon">icon</label>
                        <div class="controls">
                            <input class="input-xlarge" type="text" id="icon" name="icon" value="${entity.icon}"
                                   placeholder="输入菜单icon">
                            <div class="help-inline">
                                <a href="javascript:;" id="selectIcon" class="smaller-90" title="Icons"
                                   onclick="$(this).text($(this).text() == '选择' ? '关闭' : '选择' );">选择</a>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div><!--/.modal-body-->
        <div class="modal-footer">
            <button id="submitBtn" type="submit" class="btn btn-small btn-primary" >
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
        //页面icon弹出层回调
        window.selectIconCallback = function () {
            $('#icon', '#inputForm').val($(this).prop('class'));
        }
        //from validater
        $('#inputForm').targetIframe().validate({
            submitHandler: function (form) {
                $('#submitBtn').prop('disable',true).text('保存中....')
                 form.submit() ;
            },
            rules: {
                name: {
                    required: true,
                    normalChar: true,
                    maxlength: 50
                },
                code: {
                    required: true,
                    maxlength: 50,
                    account: true,
                    remote: {
                        url: 'menu/validateMenuCode', //后台处理程序
                        type: 'post',               //数据发送方式
                        dataType: 'json',           //接受数据格式
                        data: {                     //要传递的数据
                            menuId: function () {
                                return $('#id').val();
                            }
                        }
                    }
                },
                url: {
                    required: true,
                    maxlength: 200
                },
                status: {
                    required: true
                }
            }, messages: {
                code: {
                    remote: '该值已被占用'
                }
            }
        }); //end validate

        setTimeout(function () {
            using(['combotree'], function () {
                $('#parentId').combotree({
                    url: 'menu/getTree?ignoreNodeId=${entity.id}',
                    method: 'get',
                    onLoadSuccess: function () {
                      //  $('.combo-text', '#inputForm').data('rule-required', false); //修复IE8 validate
                    }
                });
            }) //using
        }, 100);//timeout

        $('#selectIcon').on('click.popover',function (e) {
            e.preventDefault();
            var $ele = $(this) ;
            $ele.off('click.popover') ;
            $.get('menu/fontawesome?$SiteMesh=false', function (html) {
                $ele.popover({
                    placement :'top' ,
                    html : true,
                    content: html
                }).popover('show');
            });
        });

    })  //dom ready
</script>
</body>
</html>
