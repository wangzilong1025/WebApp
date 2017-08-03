/**
 * @author zhangrp
 * Created on 2015/12/31 9:36
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */

(function ($) {
    $.parser = {
        plugins: ['datebox', 'combobox', "tilecombobox", 'form', 'calendar', 'tree', 'button', 'address', 'datagrid'],
        parse: function () {
            var aa = [];
            for (var i = 0; i < $.parser.plugins.length; i++) {
                var name = $.parser.plugins[i];
                var r = $('.aiui-' + name);
                if (r.length > 0) {
                    $('.aiui-' + name).each(function () {
                        var that = {};
                        that.html = this;
                        that.name = name;
                        window.setTimeout(function () {
                            if ($(this.html)[this.name]) {
                                $(this.html)[this.name]();
                            }
                        }.bind(that), 1);
                    });
                }
            }
        },
        parseOptions: function (target, properties) {
            var t = $(target);
            var options = {};

            var s = $.trim(t.attr('data-options'));
            if (s) {
                if (s.substring(0, 1) != '{') {
                    s = '{' + s + '}';
                }
                options = (new Function('return ' + s))();
            }
            $.map(['width', 'height', 'left', 'top', 'minWidth', 'maxWidth', 'minHeight', 'maxHeight'], function (p) {
                var pv = $.trim(target.style[p] || '');
                if (pv) {
                    if (pv.indexOf('%') == -1) {
                        pv = parseInt(pv) || undefined;
                    }
                    options[p] = pv;
                }
            });

            if (properties) {
                var opts = {};
                for (var i = 0; i < properties.length; i++) {
                    var pp = properties[i];
                    if (typeof pp == 'string') {
                        opts[pp] = t.attr(pp);
                    } else {
                        for (var name in pp) {
                            var type = pp[name];
                            if (type == 'boolean') {
                                opts[name] = t.attr(name) ? (t.attr(name) == 'true' || t.attr(name) == name) : undefined;
                            } else if (type == 'number') {
                                opts[name] = t.attr(name) == '0' ? 0 : parseFloat(t.attr(name)) || undefined;
                            } else if (type == 'string') {
                                opts[name] = t.attr(name) ? t.attr(name) : undefined;
                            }
                        }
                    }
                }
                $.extend(options, opts);
            }
            return options;
        }
    };
    $(function () {
        $.parser.parse();
    });
})(jQuery);