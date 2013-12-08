/**
 * lework alert 组件
 * google alert style @see http://www.bootcss.com/p/google-bootstrap/components.html#alerts
 * User: Gongle
 * Date: 13-11-23
 */

var lework = (function ($, lework) {
    var $container;
    var noop = function () {
    };
    var DEFAULTS = {
        onClose: noop,
        content: '', /**html or text **/
        timer: 6000, /** 6秒后关闭 **/
        id: null,
        width: '150px',
        type: '', /**info,success,danger,error**/
        parentEl: 'body' /**父容器,默认为body**/
    };



    function Alert(opt) {
        this.settings = $.extend({}, DEFAULTS, opt);
        this.init();
        this.create();
        this.autoColse();
    };
    Alert.prototype.init = function () {
        if (!$container) {
            //创建容器
            $(this.settings.parentEl)
                .append('<div class="lework-PopupPanel" id="lework-PopupPanel" ></div>');
            $container = $('#lework-PopupPanel')
        }
    };

    /**渲染html,附件事件
     * <pre>
     *     <div class="lework-alert" id="1386050958197">
     *         <div class="lework-alert-inner">hello</div>
     *         <button type="button" class="close" title="关闭">×</button>
     *         </div>
     *     </pre>
     */
    Alert.prototype.create = function () {
        var that = this;
        //create alert body
        this.$alert = $('<div />')
            /*.css('width', this.settings.width)*/
            .addClass('lework-alert' + (this.settings.type ? ' lework-alert-' + this.settings.type : ''))
            .prop({'id': (this.settings.id ? this.settings.id : (new Date()).getTime()) })
            .on('close.alert', function () {
                that.hide();
                if ($.isFunction(that.settings.onClose)) {
                    that.settings.onClose.apply(this);
                }
                that.destroy();
            }) .html(that.settings.content || '&nbsp;&nbsp;')
        this.$alert.append(' <button type="button" class="close" title="关闭" >×</button> ')
        //关闭按钮
        this.$alert.on('click', '.close', function (e) {
            e.preventDefault();
            $(this).parent().trigger('close.alert');
        })
        that.$alert.appendTo($container);
        $('<div class="clearfix" />').appendTo($container);
    };
    Alert.prototype.autoColse = function () {
        // debugger;
        //自动关闭
        var that = this;
        if (that.settings.timer) {
            setTimeout(function () {
                that.$alert.trigger('close.alert');
            }, that.settings.timer)
        }
    }
    Alert.prototype.hide = function () {
        this.$alert.hide();
    };
    Alert.prototype.destroy = function () {
        this.$alert.off('close.alert').remove();
        //   this.$alert = null;
    };
    Alert.prototype.update = function (content) {
        this.$alert.html(this.settings.content);
    }


    lework.alert = function (options) {

        return new Alert(options);
    }
    return lework;
})(jQuery, lework || {})
