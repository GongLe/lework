/**
 * 基于qtip2实现inline confirm box效果,
 * 依赖于jquery.qtip2插件
 * css  class qtip-confirmbox
 * @author Gongle
 * @date 2013年10月15日
 **/
(function ($) {
    var ConfirmDelete = function (ele, options) {
        this.options = options;
        this.$ele = $(ele);
        this.init();
    };
    // ConfirmBox PLUGIN DEFINITION
    // =======================
    ConfirmDelete.DEFAULTS = {
        onDelete: null,
        onCancel: null,
        text: '<span class="text-warning" >确认删除？</span>',
        position: {my: 'center center',
            at: 'center center',
            adjust: {
                x: -45
            }
        },
        cssClass: 'qtip-confirmbox',
        viewport: $(window)
    };
    var old = $.fn.confirmDelete;
    ConfirmDelete.prototype.init = function () {
        this.bindQtip2();
    }
    ConfirmDelete.prototype.hide = function () {
        this.$ele.qtip('hide');
    }
    ConfirmDelete.prototype.show = function () {
        this.$ele.qtip('show');
    }
    ConfirmDelete.prototype.bindQtip2 = function () {
        var that = this;
        this.$ele.qtip({
            overwrite: false,
            style: {
                name: 'light',
                classes: this.options.cssClass
            },
            show: {solo: true, ready: true},

            position: this.options.position,
            show: {
                event: 'click'
            },
            hide: {
                event: 'unfocus'
            },
            events: {
                render: function (event, api) {
                    //确认
                    $('.confirmDelete', api.elements.content).on('click', function (e) {
                        e.preventDefault();
                        var ret;
                        if ($.isFunction(that.options.onDelete)) {
                            ret = that.options.onDelete.apply(that.$ele[0] ,that.$ele[0]);
                        }
                        if (ret == true) {
                            that.hide();
                        }
                    });
                    //取消
                    $('.confirmCancel', api.elements.content).on('click', function (e) {
                        e.preventDefault();
                        if ($.isFunction(that.options.onCancel)) {
                            var ret = that.options.onCancel.apply(that.$ele[0] );
                            if (ret == true)
                                api.hide(false);
                        } else {
                            api.hide(false);
                        }
                    });
                }
            },
            content: {text: '<div style="min-width:160px;">' + that.options.text + '<button class="btn btn-danger btn-small no-border confirmDelete" >确认</button>&nbsp;' +
                ' <button class="btn btn-white btn-small no-border confirmCancel" >取消</button> </div> '},
            viewport: this.options.viewport
        });
    }

    $.fn.confirmDelete = function (option) {
        return this.each(function () {
            var $this = $(this)
            var data = $this.data('lework.confirmDelete')
            var options = $.extend({}, ConfirmDelete.DEFAULTS, typeof option == 'object' && option)
            if (!data) {
                $this.data('lework.confirmDelete', (data = new ConfirmDelete(this, options)));
            }

            if (typeof option == 'string') data[option].call(data)
        })
    }

    $.fn.confirmDelete.Constructor = ConfirmDelete;


    // ConfirmDelete NO CONFLICT
    // =================

    $.fn.confirmDelete.noConflict = function () {
        $.fn.confirmDelete = old
        return this
    }


})(window.jQuery)
