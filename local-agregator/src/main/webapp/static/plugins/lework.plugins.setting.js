/**
 * jquery 插件全局设置
 **/

$(function () {
    window.lework = (function (lework) {
        /**=================================
         * jquery DataTables Plugins 全局设置
         ===================================**/
        if ($.fn.dataTable) {
            /**
             * 扩充datatables方法,刷新,保持分页,排序信息.
             */
            $.fn.dataTableExt.oApi.fnStandingRedraw = function (oSettings) {
                if (oSettings.oFeatures.bServerSide === false) {
                    var before = oSettings._iDisplayStart;

                    oSettings.oApi._fnReDraw(oSettings);

                    // iDisplayStart has been reset to zero - so lets change it back
                    oSettings._iDisplayStart = before;
                    oSettings.oApi._fnCalculateEnd(oSettings);
                }

                // draw the 'current' page
                oSettings.oApi._fnDraw(oSettings);
            };

            /**
             *初始化Datatable全局搜索 placeholder属性
             * @param placeholder属性
             * @param tableFilterId table list filter 非必填
             */
            lework.initDatatablesSearchHolder = function (placeholderValue, tableFilterId) {
                $('input[type=text]', tableFilterId || '#table-list_filter').attr('placeholder', placeholderValue || '输入搜索')
                    .css({width: 180});
            }

            //适配Spring data jpa page 参数
            lework.springDataJpaPageableAdapter = function (sSource, aoData, fnCallback, oSettings) {


                //extract name/value pairs into a simpler map for use later
                var paramMap = {};
                for (var i = 0; i < aoData.length; i++) {
                    paramMap[aoData[i].name] = aoData[i].value;
                }


                //page calculations ,仅支持单列排序
                var pageSize = paramMap.iDisplayLength;
                var start = paramMap.iDisplayStart;
                var pageNum = (start == 0) ? 1 : (start / pageSize) + 1; // pageNum is 1 based

                // extract sort information
                var sortCol, sortDir, sortName;
                for (var i = 0; i < parseInt(paramMap['iColumns']); i++) {

                    if (paramMap['bSortable_' + i] == true && paramMap['iSortCol_0'] == i) {
                        sortCol = i;
                        sortName = paramMap['mDataProp_' + i];
                        sortDir = paramMap['sSortDir_0' ];
                        break;
                    }
                }

                //create new json structure for parameters for REST request
                // var restParams = [] ;
                var restParams = aoData;
          /*    @see http://docs.spring.io/spring-data/jpa/docs/1.4.2.RELEASE/reference/html/repositories.html
                restParams.push({'name': 'page.size', 'value': pageSize});
                restParams.push({'name': 'page.page', 'value': pageNum });
                restParams.push({'name': 'page.sort', 'value': sortName });
                restParams.push({'name': 'page.sort.dir', 'value': sortDir ? sortDir : 'asc' }); */

                sortName = (sortName + ',' + (sortDir ? sortDir : 'asc' ) ) ;
                restParams.push({'name': 'size', 'value': pageSize  });
                restParams.push({'name': 'page', 'value': pageNum -1});
                restParams.push({'name': 'sort', 'value': sortName });
                //finally, make the request
                oSettings.jqXHR = $.ajax({
                    'dataType': 'json',
                    'type': 'post',
                    'url': sSource,
                    'data': restParams,
                    'success': function (data) {
                        data.sEcho = paramMap.sEcho;
                        fnCallback(data);
                    }
                });
            };
            var _opt = {
                sPaginationType: 'full_numbers',
                oLanguage: {
                    sSearch: '<span>搜索:</span> ',
                    sInfo: '显示_START_到_END_条,共_TOTAL_条记录',
                    sInfoEmpty: '显示0到0条,共0条记录',
                    sLengthMenu: '每页_MENU_条',
                    sProcessing: '&nbsp;',
                    sEmptyTable: '<div class="alert no-margin" style="font-size:12px;padding:5px;">无可用数据</div>',
                    sZeroRecords: '<div class="alert no-margin" style="font-size:12px;padding:5px;">无记录数据/div>',
                    oPaginate: {  'sFirst': '首页', 'sPrevious': ' 上一页 ', 'sNext': ' 下一页 ', 'sLast': ' 尾页 ' }
                }
            };
            $.extend(true, $.fn.dataTable.defaults, _opt);

        }

        /**==================================
         * jquery pnotify 全局方法封装
         * ==================================**/
        if ($.pnotify) {
            $.pnotify.defaults.history = false;
            var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
            var stack_bar_top = {"dir1": "down", "dir2": "right", "push": "top", "spacing1": 0, "spacing2": 0};
            /**
             *页面信息提示
             * 依赖于jquery pnotiy插件c
             * @param title title
             * @param message notiry content
             * @param type  type :error,info,success,notice . default value :  notice
             */
            lework.notify = function (title, message, type) {
                var opts = {
                    title: title || '提示',
                    text: message || '',
                    shadow: false,
                //  addclass: "stack-topright",
                 //   addclass:"stack-bar-top" ,
                    labels: {redisplay: "重新显示", all: "所有", last: "最后", close: "关闭", stick: "播放/停止"},
                    animate_speed: 'fast' ,
                    top : '35px'
                //    stack: stack_bar_top
                };
                switch (type) {
                    case 'error':
                        opts.type = "error";
                        break;
                    case 'info':
                        opts.type = "info";
                        break;
                    case 'success':
                        opts.type = "success";
                        break;
                    //default  type: "notice"
                }
                $.pnotify(opts);
            };
        }

        return lework;
    })(window.lework || {}); // closure

}) //dom ready