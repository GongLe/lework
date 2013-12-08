/**
 * 表格多行选择,模仿gmail
 * @author Gongle
 * @date 2013-11-4
 **/
(function ($) {
    var TableMutilDelete = function (ele, options) {
        this.options = options;
        this.$ele = $(ele);
        this.init();
        this.selectedClass = '.' +     this.options.selectedCssClass ;
    };
    // TableMutilDelete PLUGIN DEFINITION
    // =======================
    TableMutilDelete.DEFAULTS = {
        target : null, //
        targetElement:'tbody>tr:has(a)',
        selectedCssClass : 'selected warning',
        onSelect :null,
        beforeSelect: null,
        afterSelect :null ,
        filterSelector :'.filterSelected' , /**忽略此类元素事件**/
        trigger :  'click'
    };
    var old = $.fn.tableMutilDelete;

    TableMutilDelete.prototype.init = function () {
        var that = this , ret;
        $( this.$ele ).on(that.options.trigger , this.options.targetElement, function (event) {
            if($(event.target).is(that.options.filterSelector)){
                return;
            }
            if ($.isFunction(that.options.beforeSelect)) {
                if ((ret = that.options.beforeSelect.apply(this) ) == false) {
                    return ret;
                }
            }
            if ($.isFunction(that.options.onSelect)) {
                if ((ret = that.options.onSelect.apply(this)) == false) {
                    return ret;
                }
            }
            //toggleClass row
            $(this).toggleClass(that.options.selectedCssClass );
            if ($.isFunction(that.options.afterSelect)) {
                if ((ret = that.options.afterSelect.apply(this) ) == false) {
                    return ret;
                }
            }

        });
    }

/*
    //全选
    TableMutilDelete.prototype.selectedAll = function(){
        console.log(  $(this.$ele).find(this.options.targetElement)
            .filter(  this.selectedClass).size()
        )
        $(this.$ele).find(this.options.targetElement)
            .filter(  this.selectedClass  ).addClass( this.options.selectedCssClass)
    }  ;
    //取消全选
    TableMutilDelete.prototype.cancelSelected= function(){
        $(this.$ele).find(this.options.targetElement)
            .not( this.selectedClass )
            .removeClass(this.options.selectedCssClass)
    }
*/

    $.fn.tableMutilDelete = function (option) {
        return this.each(function () {
            var $this = $(this)
            var data = $this.data('lework.tableMutilDelete')
            var options = $.extend({}, TableMutilDelete.DEFAULTS, typeof option == 'object' && option)
            if (!data) {
                $this.data('lework.tableMutilDelete', (data = new TableMutilDelete(this, options)));
            }

            if (typeof option == 'string') data[option].call(data);
        })
    }

    $.fn.tableMutilDelete.Constructor = TableMutilDelete ;


    // TableMutilDelete NO CONFLICT
    // =================

    $.fn.tableMutilDelete.noConflict = function () {
        $.fn.tableMutilDelete  = old
        return this
    }


})(window.jQuery)
