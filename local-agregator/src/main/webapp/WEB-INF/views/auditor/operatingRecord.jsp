<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>操作日志</title>

</head>

<body>

<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/dashboard" class="grey"> <i class="icon-home home-icon"></i></a>
        </li>
        <li class="active">
           操作日志
        </li>
    </ul>
    <!--.breadcrumb-->

</div>

<div class="page-content">
    <div class="row-fluid">
        <div class="span12">

            <div class="box box-bordered">
                <div class="box-title no-margin-top"  >
                    <h4 class="inner"><i class="icon-group"></i> 操作日志</h4>
                </div>
                <div class="box-content" style="padding-top: 5px;">

                        <div id="table-funtion-bar" class="table-funtion-bar clear-both" style="margin-bottom:25px;"  >

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
                                <button class="btn no-border tooltips" id="refresh-function" data-original-title="刷新">
                                    <i class="icon-refresh"></i>
                                </button>
                                <button class="btn no-border tooltips" id="delete-function" style="display:none;" data-original-title="删除">
                                    <i class="icon-trash"></i>
                                </button>
                            </div>
                            <div class="input-append no-margin-bottom pull-right"  style="padding-right: 50px;">
                                <!--自定义搜索-->
                                <form id="searchForm" name="searchForm" class="no-margin no-padding">
                                    <span class="input-icon input-icon-right">
                                        <input class="input-medium" id="search" name="search" type="text" placeholder="操作人">
                                        <i class="icon-search blue" onclick="$('#searchForm').submit()" ></i>
                                    </span>
                                </form> <!--/#searchForm-->
                            </div>

                            <div class="prop-attrs display-none" >
                               <div class="attr">
                                   <form id="formRegionLevel1" class="no-margin">
                                   <div class="row-fluid form-inline">
                                       <div class="span4"> <label class="width-50px text-center">开始时间</label>
                                           <input id="startDate" name="filter_GTED_startDate" class="Wdate" type="text" placeholder="开始时间"
                                                  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                                       </div>
                                       <div class="span4"><label class="width-50px text-center">结束时间</label>
                                           <input id="endDate" name="filter_LTED_endDate" class="Wdate" type="text" placeholder="结束时间"
                                                  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2020-10-01'})"/>
                                       </div>
                                       <div class="span4">
                                           <label class="width-50px text-center">状态</label>
                                        <select name="filter_EQS_state">
                                            <option value="">全部</option>
                                            <option value="0">异常</option>
                                            <option value="1">正常</option>
                                        </select>
                                       </div>
                                   </div>
                                   </form>
                               </div>
                            </div>
                            <div class="prop-attrs display-none">
                                <div class="attr">
                                    <form id="formRegionLevel2" class="no-margin">
                                    <div class="row-fluid form-inline">
                                        <div class="span4"><label class="width-50px text-center"> IP </label>
                                            <input name="filter_LIKES_ip" type="text"  placeholder="IP地址">
                                        </div>
                                        <div class="span4"><label class="width-50px text-center">模块名称</label> <input name="filter_LIKES_module" type="text"   placeholder="模块名称"></div>
                                        <div class="span4 text-center">
                                            <button id="doMoreSearch" type="button" class="btn btn-primary btn-small">
                                                查询
                                            </button>
                                            <button type="button" class="btn  btn-small"
                                                    onclick="document.getElementById('formRegionLevel1').reset();document.getElementById('formRegionLevel2').reset();">
                                                重置
                                            </button>
                                        </div>
                                    </div>
                                   </form>
                                </div>
                            </div>

                             <div class="mb">
                                 <div class="attr-extra">
                                     <div class="inner-content"  id="moreSearch" ><a href="javascript:;">高级筛选</a>&nbsp;&nbsp;<i class="icon-angle-up"></i></div>
                                 </div>
                             </div>
                        </div> <!--/.table-funtion-bar-->

                        <table id="table-list"
                               class="table table-hover table-striped  table-nomargin table-bordered dataTable dataTable-nosort clear-both">
                        </table>
                    </div>

            </div>
            <!--/.box-->
        </div>
    </div>
</div>

<!--/.page-content-->
<!--javascript Date增强版-->
<script src="${ctx}/static/assets/js/xdate.js" ></script>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" ></script>
<script>

    var oTable = $('#table-list');
    $(function () {

        //表单提交后,iframe回调函数
        window.actionCallback = function (resp) {
            var json = resp.attributes;
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content: json.message, type: json.type })
        };
        window.deleteCallback = function (resp) {
            var json = resp.attributes;
            $.colorbox.close();
            oTable.fnDraw();
            lework.alert({content: json.message, type: json.type })
        };
        //高级搜索
        $('#moreSearch').on('click', function () {
            $('#table-funtion-bar .display-none').slideToggle()
            var $icon = $(this).children('i');
            $icon.toggleClass('icon-angle-down')
        })
      //搜索表单
      $('#searchForm').submit(function(event){
          event.preventDefault() ;
          oTable.fnDraw();
      });
        //高级筛选
        $('#doMoreSearch').on('click',function(){
            oTable.fnDraw();
        })
        oTable.dataTable({
            'aoColumns': [
                { 'mData': 'module', 'sTitle': '模块' },
                { 'mData': 'function', 'sTitle': '动作'}  ,
                { 'mData': 'operatingTarget', 'sTitle': '操作记录'}  ,
                { 'mData': 'startDate', 'sTitle': '操作时间' },
                { 'mData': 'processingTime', 'sTitle': '耗时'}  ,
                { 'mData': 'username', 'sTitle': '操作人'}  ,
                { 'mData': 'ip', 'sTitle': 'IP地址'}  ,
                { 'mData': 'id', 'sTitle': '操作'}
            ],
            'aoColumnDefs': [
                {
                    'mRender': function (startDate, type, full) {

                        return  (new XDate(startDate) ).toString('yyyy/MM/dd HH:mm:ss');
                    },
                    'aTargets': [3 ]
                },
                {
                    'mRender': function (processingTime, type, full) {

                        return   (processingTime / 1000 % 60) > 1.0 ? ((processingTime / 1000 % 60) + '  秒') : (   processingTime  + '  毫秒');
                    },
                    'aTargets': [4]
                },
                {
                    'mRender': function (data, type, full) {
                        return  $('#tableActionTpl').render({id: data});
                    },
                    'aTargets': [7 ]
                },
                { bSortable: false,
                    aTargets: [7]
                }
                //   { 'bVisible': false,  'aTargets': [ 1 ] },
              //     { 'sClass': 'center', 'aTargets': [ 3 ] }
            ],
            'sDom': 'rt<"table-footer clearfix"ip>',
            "aaSorting": [ [ 3, "desc" ] ],   //默认排序
            'bStateSave': false  , /**state saving **/
            'bProcessing': true ,
            'bServerSide': true,
            'fnServerData': lework.springDataJpaPageableAdapter,
            'sAjaxSource': '${ctx}/operatingRecord/getDatatablesJson',
            'fnServerParams' :function(aoData ){
                //自定义参数
                aoData.pushArray($('#formRegionLevel1').serializeArray());
                aoData.pushArray($('#formRegionLevel2').serializeArray());
                aoData.pushArray($('#searchForm').serializeArray());
            },
            'fnInitComplete': function () {     /**datatables ready**/
            } ,
            fnDrawCallback :function(oSettings ){
                //resert function bar
                checkFunbarStatus(false) ;
                // bootstrap-tooltip
                $('.tooltips').tooltip();

            }
        });//dataTables

        //多行选择
        oTable.tableMutilDelete({
            afterSelect: function () {
                var size = oTable.find('tr.selected').size();
                checkFunbarStatus(size > 0);
            }
        });
        //双击进入详情页面
        oTable.on('dblclick','tbody>tr',function(event){
            event.preventDefault();
            $(this).find('.view').trigger('click');
        });

        //刷新
        $('#refresh-function').on('click',function () {
            oTable.fnDraw();

        });

        //取消选择
        $('#cancelSelected').on('click',function () {
            oTable.find('tbody>tr').removeClass('selected warning');
            checkFunbarStatus(false);
        });
        //全选行
        $('#selectedAll').on('click',function () {
            oTable.find('tbody>tr').addClass('selected warning')
            checkFunbarStatus(true);
        });

        //根据所选行,修改function bar状态.
        function checkFunbarStatus(hasSelected) {
            if (hasSelected == true) {
                $('#checkIcon').removeClass('icon-check-empty').addClass('icon-check')
              //  $('#delete-function').show();
            } else {
                $('#checkIcon').removeClass('icon-check').addClass('icon-check-empty');
             //   $('#delete-function').hide();
            }
        }


    })  //dom ready


</script>

<!-- ===============JsRender template ===================
    @see http://www.jsviews.com/#samples/jsr/converters
-->

<!--table action template-->
<script id="tableActionTpl" type="text/x-jsrender">
    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
        <a class="green tooltips view"  href="operatingRecord/view?id={{:id}}&$SiteMesh=false"   data-original-title="查看"
           onclick="$(this).colorbox({adjustY:'40%',width:'650px',overlayClose:false,scrolling:true,scrolling:false });" >
            <i class="icon-zoom-in bigger-140 filterSelected"></i>
        </a>

    </div>
</script>

</body>
</html>