<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp"%>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<le:javascript src="/assets/js/html5shiv.js"/>
<![endif]-->
<!--bootstrap css-->
<le:stylesheet src="/static/plugins/bootstrap/2.3.2/css/non-icon/bootstrap.css"/>
<le:stylesheet src="/static/plugins/bootstrap/2.3.2/css/bootstrap-responsive.css"/>
<!-- google code prettify css-->
<le:stylesheet src="/static/plugins/google-code-prettify/prettify.css"/>
<!--font icons :: font-awesome-->
<le:stylesheet src="/static/plugins/font-awesome/css/font-awesome.css"/>
<!--[if IE 7]>
<le:stylesheet src="/static/plugins/font-awesome/css/font-awesome-ie7.min.css"/>
<![endif]-->


<!--utils-->
<le:stylesheet src="/static/assets/ace/css/ace-utils.css"/>
<le:stylesheet src="/static/assets/ace/css/ace-color.css"/>
<!--layout-->
<le:stylesheet src="/static/assets/ace/css/ace-layout.css"/>
<!--components-->
<le:stylesheet src="/static/assets/ace/css/ace-components.css"/>
<!--typography-->
<le:stylesheet src="/static/assets/ace/css/ace-typography.css"/>
<!--elements-->
<le:stylesheet src="/static/assets/ace/css/ace-elements.css"/>
<!--forms-->
<le:stylesheet src="/static/assets/ace/css/ace-forms.css"/>
<!--navbar-->
<le:stylesheet src="/static/assets/ace/css/ace-navbar.css"/>
<!--left-sidebar-->
<le:stylesheet src="/static/assets/ace/css/ace-left-sidebar.css"/>
<!--widgets-->
<le:stylesheet src="/static/assets/ace/css/ace-widget.css"/>
<!--table-->
<le:stylesheet src="/static/assets/ace/css/table.css"/>
<!--tree-->
<%--<le:stylesheet src="/static/assets/ace/css/ace-tree.css"/>
<!--gallery-->
<le:stylesheet src="/static/assets/ace/css/ace-gallery.css"/>   --%>
<!--lework custom component style-->
<le:stylesheet src="/static/assets/css/lework.component.css"/>

<!--[if lt IE 9]>
<le:stylesheet src="/static/assets/ace/css/ace-ie.min.css"/>
<![endif]-->

<!-- easyui css custom theme    -->
<le:stylesheet src="/static/plugins/easyui/1.3.2/themes/bootstrap/easyui.custom.css"/>

<le:javascript src="/static/plugins/lework.prototype.js" />
<!--jquery -->
<le:javascript src="/static/assets/js/jquery-1.10.2.js"/>
<le:javascript src="/static/assets/js/jquery-migrate-1.2.1.js"/>

<script>
    if ("ontouchend" in document) document.write("<script src=${ctx}/static/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");

    jQuery(function () {
        if (!("ace" in window)) {
            window.ace = {}
        }
        window.ace.click_event = $.fn.tap ? "tap" : "click";
    });
    if (!('lework'  in window)) {
        window.lework = {}  ;
    }
</script>
<!--bootstrap js-->
<le:javascript src="/static/plugins/bootstrap/2.3.2/js/bootstrap.js"/>
<!--ace js-->
<le:javascript src="/static/assets/ace/js/ace.min2.js"/>

<!-- bootstrap modal
<le:stylesheet src="/static/plugins/bootstrap-modal/css/bootstrap-modal.css"/>
<le:javascript src="/static/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"/>
<le:javascript src="/static/plugins/bootstrap-modal/js/bootstrap-modal.js"/>
-->
<!--jquery colorbox-->
<le:stylesheet src="/static/plugins/colorbox/colorbox.css"/>
<le:javascript src="/static/plugins/colorbox/jquery.colorbox.js"/>
<!-- pnotify-->
<le:stylesheet src="/static/plugins/pnotify/jquery.pnotify.default.css"/>
<le:javascript src="/static/plugins/pnotify/jquery.pnotify.js" />
<!-- jquery validation -->
<le:javascript src="/static/plugins/jquery-validation/1.11.1/jquery.validate.js" />
<le:javascript src="/static/plugins/jquery-validation/1.11.1/additional-methods.js" />
<le:javascript src="/static/plugins/jquery-validation/1.11.1/jquery_validate_message_cn_zh.js" />
<!--jquery datatables-->
<le:javascript src="/static/plugins/DataTables-1.9.4/media/js/jquery.dataTables.js" />
<!--jquery template JSRender-->
<le:javascript src="/static/plugins/JsRender/jsrender.min.js" />
<!--qtip2-->
<le:stylesheet src="/static/plugins/qtip2/jquery.qtip.css" />
<le:javascript src="/static/plugins/qtip2/jquery.qtip.js" />
<!--jquery.slimscroll-->
<le:javascript src="/static/plugins/slimscroll/jquery.slimscroll.js" />

<le:javascript src="/static/plugins/blockUI/jquery.blockUI.js" />

<!--jquery chonse-->
<le:javascript src="/static/plugins/chosen/chosen.jquery.js" />
<le:stylesheet src="/static/plugins/chosen/chosen.css"/>

<!--lework jquery plugins :: ConfirmDelete -->
<le:javascript src="/static/plugins/lework.jquery.confirmdelete.js" />
<!--lework jquery plugins :: TableMutilDelete -->
<le:javascript src="/static/plugins/lework.jquery.TableMutilDelete.js" />
<!--lework   plugins :: Alert -->
<le:stylesheet src="/static/plugins/lework-alert/lework.jquery.alert.css" />
<le:javascript src="/static/plugins/lework-alert/lework.jquery.alert.js" />
<!--插件全局设置 -->
<le:javascript src="/static/plugins/lework.plugins.setting.js" />
<le:javascript src="/static/plugins/lework.utils.js" />
<le:javascript src="/static/plugins/lework.jquery.hiddenSubmit.js" />

<!-- jquery easyui loader-->
<script src="${ctx}/static/plugins/easyui/1.3.2/easyloader.js" ></script>
<script>
    /**====================
     * jquery easyui
     * ====================**/
    if (easyloader) {
        easyloader.locale = "zh_CN"; // 本地化设置
        easyloader.theme = "bootstrap"; // 设置主题
        easyloader.css = false  ;
    }
    /**===============
        close ajax cache
       =================
    **/
    $.ajaxSetup ({
        cache: false
    });
</script>


