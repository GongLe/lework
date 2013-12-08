<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>角色权限</title>
</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>
        <li class="active">
            角色权限
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">

            <div class="box box-bordered">
                <div class="box-title no-margin-top"  >
                    <h4 class="inner"><i class="icon-group"></i> 角色权限</h4>
                </div>
                <div class="box-content no-padding ">


                        <div class="table-funtion-bar clear-both"  >

                            <div class="btn-group">
                                <button data-toggle="dropdown"  class="btn no-border dropdown-toggle">
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
                            </div>
                            <div class="input-append no-margin-bottom pull-right">
                                <!--自定义搜索-->
                                <form id="searchForm" name="searchForm" class="no-margin no-padding">
                                    <span class="input-icon input-icon-right">
                                        <input class="input-medium" id="search" name="search" type="text" placeholder="名称/代码">
                                        <i class="icon-search blue" onclick="$('#searchForm').submit()" ></i>
                                    </span>
                                </form> <!--/#searchForm-->
                            </div>

                        </div> <!--/.table-funtion-bar-->

                        <table id="table-list"
                               class="table table-hover  table-nomargin table-bordered dataTable dataTable-nosort clear-both">
                        </table>

            </div>
            <!--/.box-->
        </div>
    </div>
</div>

<!--/.page-content-->
<script>
    var oTable = $('#table-list');
    $(function () {
        //表单提交后,iframe回调函数
        window.actionCallback = function (resp) {
            var json =  resp.attributes ;
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content: json.message, type: json.type })
        };
        window.deleteCallback = function (resp) {
            var json =  resp.attributes ;
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content:json.message ,type: json.type })
        };
      //搜索表单
      $('#searchForm').submit(function(event){
          event.preventDefault() ;
          oTable.fnDraw();
      });

    })  //dom ready


</script>

<!-- ===============JsRender template ===================
    @see http://www.jsviews.com/#samples/jsr/converters
-->

<!--table action template-->
<script id="tableActionTpl" type="text/x-jsrender">
    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
        <a class="green tooltips view"  href="role/view?id={{:id}}&$SiteMesh=false"   data-original-title="查看"
           onclick="$(this).colorbox({adjustY:'40%',width:'650px',overlayClose:false,scrolling:true,scrolling:false });" >
            <i class="icon-zoom-in bigger-140 filterSelected"></i>
        </a>
        <a class="blue tooltips update" href="role/update?id={{:id}}&$SiteMesh=false" data-original-title="编辑"
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