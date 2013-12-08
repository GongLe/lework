/**
 * Global Utils Function
 **/


window.lework = (function (lework) {
    lework.hasConsole = window.console && window.console.log;
    /**
     * 简单封装浏览器console.log方法
     * @param info Object 日志信息
     */
    lework.log = function (info) {
        lework.hasConsole && window.console.log(info);
    };
    var hiddenSubmitDefault = {
        action: '',
        method: 'POST',
        target: '',
        param: [], //[{'name':'{name}','value':'{value}',]
        onSubmit: null,
        afterSubmit: null
    };
    /**
     * Create UUID
     * @returns {string}
     */
    lework.uuid = function () {
        // http://www.ietf.org/rfc/rfc4122.txt
        var s = [];
        var hexDigits = '0123456789abcdef';
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = '4';  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        //  s[8] = s[13] = s[18] = s[23] = '-';

        var uuid = s.join('');
        return uuid;
    }
    /**
     *
     * @returns {number}
     */
    lework.time = function () {
        return (new Date()).getTime();
    }
    // helper - does it support CSS3 transitions/animation
    lework.doesTransitions = function () {
        var b = document.body || document.documentElement;
        var s = b.style;
        var p = 'transition';
        if (typeof s[p] === 'string') {
            return true;
        }

        // Tests for vendor specific prop
        var v = ['Moz', 'Webkit', 'Khtml', 'O', 'ms'];
        p = p.charAt(0).toUpperCase() + p.substr(1);
        for (var i = 0; i < v.length; i++) {
            if (typeof s[v[i] + p] === 'string') {
                return true;
            }
        }
        return false;
    };
    return lework;
})(window.lework || {}); // closure
