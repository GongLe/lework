/**
 * 扩充String原型,字符串模板格式化
 * <code>
 *     Array数据: '{0}'.format('hello world') ;
 *     json数据:'{name}'.format({'name':'hello world'});
 *     输出结果:hello world
 * </code>
 * @author Gongle
 * @date 2013年4月22日10:50:29
 */
if (!String.prototype.format) {
    String.prototype.format = function () {
        var args = arguments;
        if (Object.prototype.toString.call(args[0]) == '[object Object]') {
            var _jsonData = args[0];
            return this.replace(/{([^{}]+)}/gm, function (match, name) {
                return typeof _jsonData[name] != 'undefined'
                    ? _jsonData[name]
                    : match;
            });
        }
        return this.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match;
        });
    };
}
/***
 * Array添加indexOf方法.
 */
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (searchElement, fromIndex) {
        var i,
            pivot = (fromIndex) ? fromIndex : 0,
            length;

        if (!this) {
            throw new TypeError();
        }

        length = this.length;

        if (length === 0 || pivot >= length) {
            return -1;
        }

        if (pivot < 0) {
            pivot = length - Math.abs(pivot);
        }

        for (i = pivot; i < length; i++) {
            if (this[i] === searchElement) {
                return i;
            }
        }
        return -1;
    };
}
/**
 * concat 多个数组.
 * @param 数据
 */
Array.prototype.pushArray = function() {
    var toPush = this.concat.apply([], arguments);
    for (var i = 0, len = toPush.length; i < len; ++i) {
        this.push(toPush[i]);
    }
};