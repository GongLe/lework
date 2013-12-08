/**
 * 把表单提交到iframe里面.
 * eg:  $('#inputForm').targetIframe()
 * 自动创建表弟那, 提交到iframe里面.
 * eg:   $.hiddenSubmit({
                    formAction: 'user/delete',
                    data: [  {name: 'deleteIds', value: ids } ] ,
                    complete : function(){  checkFunbarStatus(false); }
                })
 */
$(function () {
    var defaults = {
        iframeID: 'hidden_submit_iframe',       // Iframe ID.
        iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank',
        formID: 'hidden_submit_form',
        formAction: null,
        formMethod: 'POST',
        data: [],  //[{name:'parameter name',value:'parameter value' }]
        complete: null  // function (response) {  } // after response from the server has been received.

    };
    /**
     *把表单提交到iframe里面.
     * @returns {*}
     */
    $.fn.targetIframe = function () {
        prepareIframe(defaults)
        return this.each(function () {
            $(this).attr('target', defaults.iframeID)
        })
    }
    /**
     * prepare signle  iframe.
     * @param opt
     */
    function prepareIframe(opt) {
        // prepare   iframe.
        if (!$('#' + opt.iframeID).length) {
            $('body').append('<iframe id="' + opt.iframeID + '" name="' + opt.iframeID + '" src="' + opt.iframeSrc + '" style="display:none" ></iframe>');
        }
    }

    $.hiddenSubmit = function (options) {
        var iframe, form, $form  , extraInputs = [];
        options = $.extend({}, defaults, options);

        if (!$('#' + options.formID).length) {
            $form = $('<form/>');
            //setting form attr
        } else {
            //clear form
            $form = $('#' + options.formID);
            $form.html('');
        }
        //setting form
        $form.attr({
            id: options.formID,
            name: options.formID,
            method: options.formMethod,
            action: options.formAction,
            target: options.iframeID
        }).css('display', 'none')
            .appendTo('body');
        //setting form input
        //hide sitemesh parameter !!!
        options.data.push({name: '$SiteMesh', value: 'false'});

        for (var i = 0; i < options.data.length; i++) {
            $('<input type="hidden" name="' + options.data[i].name + '">').val(options.data[i].value)
                .appendTo($form);
        }

        prepareIframe(options)

        //on load
        iframe = $('#' + options.iframeID).load(function () {
            var response = iframe.contents()
            if ($.isFunction(options.complete))
                options.complete.apply($form[0], [response]);

            iframe.unbind('load');

        });
        //submit
        form = $form[0]
        try {
            form.submit();
        } catch (err) {
            // just in case form has element with name/id of 'submit'
            var submitFn = document.createElement('form').submit;
            submitFn.apply(form);
        }
    }


})