/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
 jQuery.extend(jQuery.validator.messages, {
        required: "必填字段",
		remote: "该值已存在",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入最大为 {0} 的值"),
		min: jQuery.validator.format("请输入最小为 {0} 的值")
});
/**
 * 兼容bootstrap2
 */
jQuery.extend(jQuery.validator.defaults ,{
    errorClass: 'error',
    validClass: 'success',
    errorElement: 'label',  // class='help-inline'
    highlight: function (element, errorClass, validClass) {
        var bs_group = $(element).closest('.control-group');
        if (bs_group) {
            bs_group.removeClass(validClass).addClass(errorClass);
        } else {
            //default style
            if (element.type === "radio") {
                this.findByName(element.name).addClass(errorClass).removeClass(validClass);
            } else {
                $(element).addClass(errorClass).removeClass(validClass);
            }
        }
    },
    unhighlight: function (element, errorClass, validClass) {
        var bs_group = $(element).closest('.control-group');
        if (bs_group) {
            $(element).addClass('valid')
                .closest('.control-group').removeClass(errorClass).addClass(validClass);
        } else {
            //default style
            if (element.type === "radio") {
                this.findByName(element.name).removeClass(errorClass).addClass(validClass);
            } else {
                $(element).removeClass(errorClass).addClass(validClass);
            }
        }
    }
})  ;

 /**
 ======================
 验证增强方法
 ======================
 **/
 
	// this one requires the value to be the same as the first parameter
	$.validator.methods.equal = function(value, element, param) {
		return value == param;
	};
	//要求值等于第一个参数param
	$.validator.methods.unequal = function(value, element, param) {
		return value != param;
	};

    /**用户账号验证(只能包括 _ 数字 字母) **/
    jQuery.validator.addMethod("account", function (value, element) {
        return this.optional(element) || (/^[\w]+$/.test(value) );
    }, "仅数字、字母、下划线");
     // 字符验证
    jQuery.validator.addMethod("normalChar", function (value, element) {
        return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
    }, "仅汉字、英文字母、数字、下划线");
    // 身份证号码验证
    jQuery.validator.addMethod("isIdCard", function (value, element) {
        return this.optional(element) || isIdCardNo(value);
    }, "请填写正确的身份证号码");
    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "手机号码格式错误");

    // 电话号码验证
    jQuery.validator.addMethod("isTel", function (value, element) {
        var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678
        return this.optional(element) || (tel.test(value));
    }, "电话号码格式错误.eg:010-12345678");

    // 联系电话(手机/电话皆可)验证
    jQuery.validator.addMethod("isPhone", function (value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return this.optional(element) || (tel.test(value) || mobile.test(value));

    }, "请填写正确的联系电话");

    // 邮政编码验证
    jQuery.validator.addMethod("isZipCode", function (value, element) {

        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请填写正确的邮政编码");
