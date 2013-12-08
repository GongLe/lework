jQuery(function () {
    handle_side_menu();
    widget_boxes();
    nav_animate();
});
function nav_animate() {
    $('.ace-nav [class*="icon-animated-"]').closest("a").on("click", function () {
        var b = $(this).find('[class*="icon-animated-"]').eq(0);
        var a = b.attr("class").match(/icon\-animated\-([\d\w]+)/);
        b.removeClass(a[0]);
        $(this).off("click")
    });
}
function handle_side_menu() {
    $("#menu-toggler").on(ace.click_event, function () {
        $("#sidebar").toggleClass("display");
        $(this).toggleClass("display");
        return false
    });
    var hasMinMenu = $("#sidebar").hasClass("menu-min");
    $("#sidebar-collapse").on(ace.click_event, function () {
        $("#sidebar").toggleClass("menu-min");
        $(this).find('[class*="icon-"]:eq(0)').toggleClass("icon-double-angle-right");
        hasMinMenu = $("#sidebar").hasClass("menu-min");
        if (hasMinMenu) {
            $(".open > .submenu").removeClass("open")
        }
    });
    var a = "ontouchend" in document;
    $(".nav-list").on(ace.click_event, function (event) {
        var $link = $(event.target).closest("a");
        if (!$link || $link.length == 0) {
            return
        }
        if (!$link.hasClass("dropdown-toggle")) {
            if (hasMinMenu && ace.click_event == "tap" && $link.get(0).parentNode.parentNode == this) {
                var $menuText = $link.find(".menu-text").get(0);
                if (event.target != $menuText && !$.contains($menuText, event.target)) {
                    return false
                }
            }
            return
        }
        var d = $link.next().get(0);
        if (!$(d).is(":visible")) {
            var c = $(d.parentNode).closest("ul");
            if (hasMinMenu && c.hasClass("nav-list")) {
                return
            }
            c.find("> .open > .submenu").each(function () {
                if (this != d && !$(this.parentNode).hasClass("active")) {
                    $(this).slideUp(200).parent().removeClass("open")
                }
            })
        } else {
        }
        if (hasMinMenu && $(d.parentNode.parentNode).hasClass("nav-list")) {
            return false
        }
        $(d).slideToggle(200).parent().toggleClass("open");
        return false
    })
}

function widget_boxes() {
    $(".page-content").delegate(".widget-toolbar > [data-action]", "click", function (event) {
        event.preventDefault();
        var $this = $(this);
        var action = $this.data("action");
        var widgetBox = $this.closest(".widget-box");
        if (widgetBox.hasClass("ui-sortable-helper")) {
            return
        }
        if (action == "collapse") {
            var d = widgetBox.find(".widget-body");
            var i = $this.find("[class*=icon-]").eq(0);
            var e = i.attr("class").match(/icon\-(.*)\-(up|down)/);
            var b = "icon-" + e[1] + "-down";
            var f = "icon-" + e[1] + "-up";
            var h = d.find(".widget-body-inner");
            if (h.length == 0) {
                d = d.wrapInner('<div class="widget-body-inner"></div>').find(":first-child").eq(0)
            } else {
                d = h.eq(0)
            }
            var c = 300;
            var g = 200;
            if (widgetBox.hasClass("collapsed")) {
                if (i) {
                    i.addClass(f).removeClass(b)
                }
                widgetBox.removeClass("collapsed");
                d.slideUp(0, function () {
                    d.slideDown(c)
                })
            } else {
                if (i) {
                    i.addClass(b).removeClass(f)
                }
                d.slideUp(g, function () {
                    widgetBox.addClass("collapsed")
                })
            }
        } else {
            if (action == "close") {
                var n = parseInt($this.data("close-speed")) || 300;
                widgetBox.hide(n, function () {
                    widgetBox.remove()
                })
            } else {
                if (action == "reload") {
                    $this.blur();
                    var m = false;
                    if (widgetBox.css("position") == "static") {
                        m = true;
                        widgetBox.addClass("position-relative")
                    }
                    widgetBox.append('<div class="widget-box-layer"><i class="icon-spinner icon-spin icon-2x white"></i></div>');
                    setTimeout(function () {
                        widgetBox.find(".widget-box-layer").remove();
                        if (m) {
                            widgetBox.removeClass("position-relative")
                        }
                    }, parseInt(Math.random() * 1000 + 1000))
                } else {
                    if (action == "settings") {
                    }
                }
            }
        }
    })
}

