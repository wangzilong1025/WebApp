/**
 * Created by dizl on 2016/5/25.
 */
var comm = {};
(function(){

    var dealParam = function (options) {
        var busiCode = options.busiCode;
        var param = options.param;
        var moduleName = comm.lang.isEmpty(options.moduleName) ? MODULE_NAME : options.moduleName;
        param = {"requestInfo": {busiCode: busiCode, moduleName: moduleName, busiParams: param}, "pubInfo": {}};
        return param;
    };

    var ajax = {

        paramWrap: function (options) {
            return {data: JSON.stringify(dealParam(options))};
        },
        /**
         * ajaxEntity是对ajax进行封装，以便使用常用后台服务调用
         * @method ajaxEntity
         * @param {String} busiCode 服务编号
         * @param {String} moduleName 模块名称
         * @param {Object} param 请求参数
         * @param {Function} [optional,default=undefined] GET请求成功回调函数
         * @param {Boolean} sync 是否异步
         * */
        ajaxEntity:function(options){
            options.url = SERVER_URL;
            options.type = "POST";
            options.dataType ="json";
            options.param = dealParam(options);;
            this.ajax(options);
        },

        /**
         * GetJson是对ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
         * @method GetJson
         * @param {String} url HTTP(GET)请求地址
         * @param {Object} param json对象参数
         * @param {Function} callback [optional,default=undefined] GET请求成功回调函数
         */
        getJson : function (options) {
            this.ajax({
                url : options.url,
                type : 'GET',
                param : options.param,
                dataType : "json",
                callbakc : options.callback
            });
        },
        /**
         * PostJsonAsync是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型, 采用同步阻塞的方式调用ajax
         * @method PostJsonAsync
         * @param {String} url HTTP(POST)请求地址
         * @param {Object} param json对象参数
         * @param {Function} callback [optional,default=undefined] POST请求成功回调函数
         */
        postJsonSync : function (options) {
            this.ajax({
                url : options.url,
                type : 'POST',
                param : options.param,
                dataType : "json",
                callback : options.callback,
                sync : true
            });
        },
        /**
         * PostJson是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
         * @method PostJson
         * @param {String} url HTTP(POST)请求地址
         * @param {Object} param json对象参数
         * @param {Function} callback [optional,default=undefined] POST请求成功回调函数
         */
        postJson : function (options) {
            this.ajax({
                url : options.url,
                type :  'POST',
                param : options.param,
                dataType : "json",
                callback : options.callback
            });
        },
        /**
         * loadHtml是对Ajax load的封装,为载入远程 HTML 文件代码并插入至 DOM 中
         *
         * @method loadHtml
         * @param {Object} target Dom对象
         * @param {String} url HTML 网页网址
         * @param {Function} callback [optional,default=undefined] 载入成功时回调函数
         */
        loadHtml:function (options) {
            var target = options.target;
            var url = options.url;
            var param = options.param;
            var callback = options.callback;
            $(target).load(url, param, function(response, status, xhr){
                callback = callback ? callback : function(){};
                status=="success" ? callback(true) : callback(false);
            });
        },
        /**
         * GetHtml是对ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
         * @method GetHtml
         * @param {String} url HTTP(GET)请求地址
         * @param {Object} param json对象参数
         * @param {Function} callback [optional,default=undefined] GET请求成功回调函数
         */
        postHtml : function (options) {
            /* start UEStory */
            this.ajax({
                url : options.url,
                type : 'POST',
                param : options.param,
                dataType : "html",
                callback : options.callback
            });
        },
        /**
         * 通过url获取页面的body部分
         * @param url 页面url地址
         * @param async 是否异步
         * @param callback 回调函数
         * @returns {string} 返回值
         */
        getRemoteHtmlBody : function (url, async, callback) {
            if (async == undefined) {
                async = true;
            }
            if (async == false || async == "false") {
                async = false;
            } else {
                async = true;
            }
            var html = "";
            $.ajax({
                type: "GET",
                cache: false,
                url: url,
                async: async,
                dataType: "html",
                success: function (data) {
                    var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
                    var matches = pattern.exec(data);
                    if (matches) {
                        html = matches[1];
                        if (callback) {
                            callback(html);
                        }
                    }
                }
            });
            return html;
        },
        /**
         * 基于jQuery ajax的封装，可配置化
         * @method ajax
         * @param {String} url HTTP(POST/GET)请求地址
         * @param {String} type POST/GET
         * @param {Object} param json参数命令和数据
         * @param {String} dataType 返回的数据类型
         * @param {Function} callback [optional,default=undefined]
         *                  请求成功回调函数,返回数据data和isSuc
         */
        ajax : function (options) {
            var url = options.url;
            var type = options.type || "POST";
            var param = options.param;
            var dataType = options.dataType || "";
            var callback = options.callback || function(){};
            var sync = options.sync!=null && options.sync==false ? false : true;
            var mask = options.mask!=null && options.mask==false ? false : true;
            var loadDialog;
            if(mask) {
                loadDialog = comm.dialog.load("正在处理...");
            }

            if(type.toLowerCase()=="get"){
                var temp = [];
                for(var key in param){
                    if(param.hasOwnProperty(key)){
                        temp.push(key + '='+ param[key]);
                    }
                }
                param = temp.join("&");
            }

            //对请求参数进行加密
            var data = "";
            if(IS_CIPHER && IS_CIPHER==true){
                if (comm.storage.SessionStorage.get("CLIENT_CIPHER") == undefined || comm.storage.SessionStorage.get("CLIENT_UNIQUE_ID") == undefined) {
                    //发送秘钥
                    var CLIENT_CIPHER = "xxxxxxxx".replace(/[x]/g, function (c) {
                        var r = Math.random() * 16 | 0;
                        return r.toString(16);
                    });
                    //看cookie中是否有该值
                    var CLIENT_UNIQUE_ID = comm.cookie.get("ESOP_SESSION_ID");
                    if(comm.lang.isEmpty(CLIENT_UNIQUE_ID)){
                        CLIENT_UNIQUE_ID = comm.date.dateTime2Long()+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx".replace(/[x]/g, function (c) {
                                var r = Math.random() * 16 | 0;
                                return r.toString(16);
                            });
                    }
                    var publicKey =
                        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwwr7dt78MvYYKMkEXqHusBFYt"+
                        "UHZSjJLzdzm0qF9zIUWulQaKC4zLdPaubRDF/YSoHJsAWvjDodcAmdq16ifP6wnp"+
                        "E/V03SpBD8SzoIb9dS4zzyJCFSPr2nl7neNtO+i/mS9hD0Hj4ARnslQnZq0gQcHn"+
                        "3z8hQtLCPlGGHlcAtwIDAQAB";
                    comm.storage.SessionStorage.set("CLIENT_CIPHER", CLIENT_CIPHER);//数据加密key
                    comm.storage.SessionStorage.set("CLIENT_UNIQUE_ID", CLIENT_UNIQUE_ID);//唯一标识
                    data = data + "CLIENT_UNIQUE_ID" + "=" + CLIENT_UNIQUE_ID + "&";
                    data = data + "CLIENT_CIPHER" + "=" + comm.crypto.encryptByRSA(CLIENT_CIPHER,publicKey) + "&";
                } else {
                    data = data + "CLIENT_UNIQUE_ID" + "=" + comm.storage.SessionStorage.get("CLIENT_UNIQUE_ID") + "&";
                }
                var value = comm.crypto.encryptByDES(JSON.stringify(param), comm.storage.SessionStorage.get("CLIENT_CIPHER"));
                data = data + "data=" + value;
            }else{
                data = "data="+JSON.stringify(param);
            }

            var thiz = this;
            $.ajax({
                url : url,
                type : type,
                data : data,
                cache: false,
                async: sync,
                dataType:"text",
                timeout : 30000,//超时,默认超时30000ms,
                success : function (resp) {
                    if(!resp) {
                        return;
                    }
                    if(IS_CIPHER && IS_CIPHER==true){
                        resp = comm.crypto.decryptByDES(resp, comm.storage.SessionStorage.get("CLIENT_CIPHER"));
                    }

                    if(dataType == "html"){
                        callback(resp,true);
                        return;
                    }else {
                        resp = JSON.parse(resp);
                        var code = resp.errorInfo.code;
                        var msg = resp.errorInfo.message;
                        var isSucc = true;
                        if (code == "0003") {//系统异常
                            comm.dialog.notification({
                                content:msg,
                                maskClickClosed:true,
                                type:comm.dialog.type.error,
                                title:'系统异常'
                            });
                            return;
                        }else if(code=="0002"){//用户未登陆，跳转到登录页面
                            openLoginPage();
                            return;
                        }else if(code=="0001"){//业务异常
                            isSucc = false;
                        }else if(code=="0000"){//调用正常
                            isSucc = true;
                        }
                        if (callback) {
                            callback(resp.data || {}, isSucc,msg);
                        }
                    }
                },
                error : function (errData) {
                    comm.dialog.notification({
                        content:errData,
                        maskClickClosed:true,
                        type:comm.dialog.type.error,
                        title:'系统异常'
                    });
                },
                complete:function(){
                    if(mask) {
                        comm.dialog.unload(loadDialog);
                    }
                }
            });

        }
    };

    var browser = {
        /**
         * 获取URL地址栏参数值
         *
         * @method getParameter
         * @param {String} name 参数名
         * @param {String} url [optional,default=当前URL]URL地址
         * @return {String} 参数值
         */
        getParameter : function(name, url) {
            var paramStr = url || window.location.search;
            if (paramStr.length == 0) {
                return null;
            }
            if (paramStr.charAt(0) != "?") {
                return null;
            }
            paramStr = unescape(paramStr).substring(1);
            if (paramStr.length == 0) {
                return null;
            }
            var params = paramStr.split('&');
            for ( var i = 0; i < params.length; i++) {
                var parts = params[i].split('=', 2);
                if (parts[0] == name) {
                    if (parts.length < 2 || typeof (parts[1]) === "undefined"
                        || parts[1] == "undefined" || parts[1] == "null")
                        return '';
                    return parts[1];
                }
            }
            return null;
        },
        /**
         * 将 uri 的查询字符串参数映射成对象
         *
         * @method mapQuery
         * @param {String} uri 要映射的 uri
         * @return {Object} 按照 uri 映射成的对象
         *
         * @example
         *  var queryObj = Rose.browser.mapQuery("http://www.10086.com/?bb=4765078&style=blue");
         *  // queryObj 则得到一个{bb:"4765078", style:"blue"}的对象。
         *
         */
        mapQuery :function(uri){
            var i,
                key,
                value,
                uri = uri && uri.split('#')[0] || window.location.search, //remove hash
                index = uri.indexOf("?"),
                pieces = uri.substring(index + 1).split("&"),
                params = {};
            if(index === -1){//如果连?号都没有,直接返回,不再进行处理.
                return params;
            }
            for(i=0; i<pieces.length; i++){
                try{
                    index = pieces[i].indexOf("=");
                    key = pieces[i].substring(0,index);
                    value = pieces[i].substring(index+1);
                    if(!(params[key] = unescape(value))){
                        throw new Error("uri has wrong query string when run mapQuery.");
                    }
                }
                catch(e){
                    //Rose.log("错误：[" + e.name + "] "+e.message+", " + e.fileName+", 行号:"+e.lineNumber+"; stack:"+typeof e.stack, 2);
                }
            }
            return params;
        },
        /**
         * 转到上一页（缓存页）
         *
         * @method goPrevPage
         * @return Rose.browser
         */
        goPrevPage : function() {
            history.go(-1);
            return this;
        },
        /**
         * 转到下一页
         *
         * @method goNextPage
         * @return Rose.browser
         */
        goNextPage : function() {
            history.go(1);
            return this;
        },
        /**
         * 转到当前页(刷新页面)
         *
         * @method refreshPage
         * @return Rose.browser
         */
        refreshPage : function() {
            history.go(0);
            return this;
        },
        /**
         * 获取域名或主机IP
         *
         * @method getHost
         * @return {String}
         */
        getHost : function() {
            return location.host.split(':')[0];
        },
        /**
         * Firefox需要手动开启dom.allow_scripts_to_close_windows<br>
         * about:config -> dom.allow_scripts_to_close_windows = true。
         *
         * @method closeWin
         */
        closeWin : function() {
            window.opener = null;
            window.open('', '_self');
            window.close();
        },
        /**
         * 设置主页
         *
         * @method setHomepage
         * @param {String} url 设置的URL
         * @return Rose.browser
         */
        setHomepage : function(url) {
            url = (url ? url : location.href);
            if (document.all) {
                document.body.style.behavior = 'url(#default#homepage)';
                document.body.setHomePage(url);
            } else if (window.sidebar) {
                if (window.netscape) {
                    try {
                        window.netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                    } catch (e) {
                        alert('此操作被浏览器拒绝！请在地址栏输入"about:config"并回车然后将[signed.applets.codebase_principal_support]的值设置为true');
                    }
                }
                try {
                    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                    prefs.setCharPref('browser.startup.homepage', url);
                } catch (e) {
                    alert('设置失败');
                }
            } else {
                alert('请用Ctrl+D将地址添加到收藏夹');
            }
            return this;
        },
        /**
         * 获取浏览器语言代码,如:'zh-CN'
         *
         * @method getLang
         * @return {String} 语言代码
         */
        getLang: function () {
            var nav = window.navigator;
            return (nav.language || nav.userLanguage);
        },

        /**
         * 刷新当前页面
         * */
        reload:function(url){
            if(url!=null && url.length>0){
                window.location.href = url;
            }else {
                window.location.reload(true);
            }
        }
    }

    var cookie = {
        /**
         * 设置一个cookie
         * @method set
         * @param {String} name cookie名称
         * @param {String} value cookie值
         * @param {String} path 所在路径
         * @param {Number} expires 存活时间，单位:小时
         * @param {String} domain 所在域名
         * @return {Boolean} 是否成功
         */
        set : function(name, value, expires, path, domain) {
            var str = name + "=" + encodeURIComponent(value);
            if (expires != null || expires != '') {
                if (expires == 0) {expires = 100*365*24*60;}
                var exp = new Date();
                exp.setTime(exp.getTime() + expires*60*1000);
                str += "; expires=" + exp.toGMTString();
            }
            if (path) {str += "; path=" + path;}
            if (domain) {str += "; domain=" + domain;}
            document.cookie = str;
        },
        /**
         * 获取指定名称的cookie值
         * @method get
         * @param {String} name cookie名称
         * @return {String} 获取到的cookie值
         */
        get : function(name) {
            var v = document.cookie.match('(?:^|;)\\s*' + name + '=([^;]*)');
            return v ? decodeURIComponent(v[1]) : null;
        },
        /**
         * 删除指定cookie,复写为过期
         * @method remove
         * @param {String} name cookie名称
         * @param {String} path 所在路径
         * @param {String} domain 所在域
         */
        remove : function(name, path, domain) {
            document.cookie = name + "=" +
                ((path) ? "; path=" + path : "") +
                ((domain) ? "; domain=" + domain : "") +
                "; expires=Thu, 01-Jan-70 00:00:01 GMT";
        }
    }

    var date = {
        /**
         * 将小于10数字前加0
         *
         * @method _zeroCompletion
         * @param {Number} time 时间
         * @return {String}
         */
        _zeroCompletion : function(time) {
            if (time < 10) {
                return '0' + time;
            }
            return time + '';
        },
        /**
         * 将秒转换为时间hh:mm:ss格式
         *
         * @method secs2Time
         * @param {Number} secs 秒
         * @return {String} 格式化时间字符串'00:00:00'
         */
        secs2Time : function(secs) {
            if (secs < 0) {
                secs = 0;
            }
            secs = parseInt(secs, 10);
            var hours = Math.floor(secs / 3600), mins = Math
                .floor((secs % 3600) / 60), sec = secs % 3600 % 60;
            return this._zeroCompletion(hours) + ':' + this._zeroCompletion(mins)
                + ':' + this._zeroCompletion(sec);
        },
        /**
         * 格式化日期时间字符串
         *
         * @method dateTime2str
         * @param {Date} dt 日期对象
         * @param {String} fmt 格式化字符串，如：'yyyy-MM-dd hh:mm:ss'
         * @return {String} 格式化后的日期时间字符串
         */
        dateTime2str : function(dt, fmt) {
            var z = {
                M : dt.getMonth() + 1,
                d : dt.getDate(),
                h : dt.getHours(),
                m : dt.getMinutes(),
                s : dt.getSeconds()
            };
            fmt = fmt.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
                return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1)))
                    .slice(-2);
            });
            return fmt.replace(/(y+)/g, function(v) {
                return dt.getFullYear().toString().slice(-v.length);
            });
        },
        /**
         * 根据日期时间格式获取获取当前日期时间
         *
         * @method dateTimeWrapper
         * @param {String} fmt 日期时间格式，如："yyyy-MM-dd hh:mm:ss";
         * @return {String} 格式化后的日期时间字符串
         */
        dateTimeWrapper : function(fmt) {
            if (arguments[0])
                fmt = arguments[0];
            return this.dateTime2str(new Date(), fmt);
        },
        /**
         * 获取当前日期时间
         *
         * @method getDatetime
         * @param {String} fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
         * @return {String} 格式化后的日期时间字符串
         */
        getDatetime : function(fmt) {
            return this.dateTimeWrapper(fmt || 'yyyy-MM-dd hh:mm:ss');
        },
        /**
         * 获取当前日期时间+毫秒
         *
         * @method getDatetimes
         * @param {String} fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
         * @return {String} 格式化后的日期时间字符串
         */
        getDatetimes : function(fmt) {
            var dt = new Date();
            return this.dateTime2str(dt, fmt || 'yyyy-MM-dd hh:mm:ss') + '.'
                + dt.getMilliseconds();
        },
        /**
         * 获取当前日期（年-月-日）
         *
         * @method getDate
         * @param {String} fmt [optional,default='yyyy-MM-dd'] 日期格式。
         * @return {String} 格式化后的日期字符串
         */
        getDate : function(fmt) {
            return this.dateTimeWrapper(fmt || 'yyyy-MM-dd');
        },
        /**
         * 获取当前时间（时:分:秒）
         *
         * @method getTime
         * @param {String} fmt [optional,default='hh:mm:ss'] 日期格式。
         * @return {String} 格式化后的时间字符串
         */
        getTime : function(fmt) {
            return this.dateTimeWrapper(fmt || 'hh:mm:ss');
        },
        /**
         * 将标准日期时间格式转换为长整形格式
         * @param {String} datetime 为空或 yyyy-MM-dd hh:mm:ss 格式时间
         * @returns {number}
         */
        dateTime2Long : function(datetime){
            if(datetime && typeof datetime === "string"){
                return new Date(datetime.replace(/\-/g,'/')).getTime();
            }
            return new Date().getTime();
        }
    }

    var dom = {
        /**
         * 点击隐藏(失去焦点后,点击元素以外区域后,元素隐藏)
         *
         * @method clickHide
         * @param {Object} obj 主对象
         * @example  Rose.dom.clickHide($('#test'))
         */
        clickHide : function(obj) {
            var $this = $(obj);
            var mouseInsideTag = false;

            $this.show();

            $this.hover(function(){
                mouseInsideTag=true;
            }, function(){
                mouseInsideTag=false;
            });

            $("html,body").mouseup(function(){
                if(!mouseInsideTag) $this.hide();
            });
        },
        /**
         * 自动页面滚动至某元素
         *
         * @method autoScroll
         * @param {All} obj 主体
         * @param {Object} obj 主对象
         * @param {Number} time 页面滚动至某元素所需时间
         * @example Rose.dom.autoScroll($('#goTop'),100)
         */
        autoScroll : function(obj, time) {
            var $this = $(obj);
            if(!time) time = 500;
            var $top = $this.offset().top;
            $('html,body').animate({
                scrollTop:$top
            },time);
        },
        /**
         * 表单中的默认字符,点击后隐藏这些字符
         *
         * @method defaultChars
         * @param {All} obj 主体
         * @example &lt;input type="text" defaultchars="搜索关键字 比如：(Rose come back)"&gt;
         */
        defaultChars : function(obj) {
            var $this = $(obj);
            if($this.val()) return;
            var $defaultchars = $this.attr('defaultchars');
            $this.val($defaultchars);
            $this.focusin(function(){
                if ($this.val() == $defaultchars) $this.val('');
            }).focusout(function(){
                if ($this.val() == '') $this.val($defaultchars);
            });
        },
        /**
         * 获取焦点时的样式
         *
         * @method focusClass
         * @param {Object} obj 主对象
         * @param {String} styleclass 样式名称
         * @example Rose.dom.focusClass($('#username'),"highRed");
         */
        focusClass : function(obj, styleclass) {
            var $this = $(obj);
            $this.focus(function(){
                $this.toggleClass(styleclass);
            }).blur(function(){
                $this.toggleClass(styleclass);
            });
        },
        /**
         * 滑至元素和移除元素时的样式切换
         *
         * @method hoverClass
         * @param {Object} obj 主对象
         * @param {String} styleclass 样式名称
         */
        hoverClass : function(obj, styleclass) {
            var $this = $(obj);
            $this.hover(function() {
                    $(this).toggleClass(styleclass);
                },
                function() {
                    $(this).toggleClass(styleclass);
                });
        },
        /**
         * 点击给元素添加样式，并移除已有元素的样式，提供多选参数
         *
         * @method clickClass
         * @param {Object} obj 主对象
         * @param {String} styleclass 样式名称
         * @param {boolean} mult [optional,default=false] 是否为多选，默认为单选
         */
        clickClass : function(obj, styleclass, mult){
            var $this = $(obj);
            if (mult == undefined){var mult = false;}
            $this.click(function(){
                if(!mult){$this.removeClass(styleclass);}
                $(this).addClass(styleclass);
            })
        }
    }

    var lang = {
        /**
         * 是否为数组
         *
         * @method isArray
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isArray : function(obj) {
            return Object.prototype.toString.apply(obj) === '[object Array]';
        },
        /**
         * 是否为空
         *
         * @method isEmpty
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isEmpty : function(obj) {
            return obj === null || typeof obj === 'undefined' || obj === '' || (typeof obj==='Array');
        },
        /**
         * 是否为数值
         *
         * @method isNumber
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isNumber : function(obj) {
            return typeof(obj) === 'number';
        },
        /**
         * 是否为字符
         *
         * @method isString
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isString : function(obj) {
            return typeof(obj) === 'string';
        },
        /**
         * 是否为布尔值
         *
         * @method isBoolean
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isBoolean : function(obj) {
            return typeof(obj) === 'boolean';
        },
        /**
         * 是否为对象
         *
         * @method isObject
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isObject : function(obj) {
            return typeof(obj) === 'object';
        },
        /**
         * 是否为函数
         *
         * @method isFunction
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isFunction : function(obj) {
            return typeof(obj) === 'function';
        },
        /**
         * 是否为 undefined
         *
         * @method isUndefined
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isUndefined : function(obj) {
            return o === undefined;
        },
        /**
         * 是否为 null
         *
         * @method isNull
         * @param {All} obj 主体
         * @return {Boolean} true/false
         */
        isNull : function(obj) {
            return o === null;
        },

        /**
         * 判断对象是否为纯整形数字或整形数字字符串 011=9(011 表示8进制)
         *
         * @method isInteger
         * @param {Number/String} obj 输入数字或字符串
         * @return {Boolean}
         */
        isInteger: function (obj) {
            if (obj != parseInt(obj, 10)) {
                return false;
            }
            return true;
        },
        /**
         * 将"undefined"和null转换为空串
         *
         * @method obj2Empty
         * @param {Object} obj 输入对象
         * @return {Object}
         */
        obj2Empty: function (obj) {
            if (typeof obj == "undefined" || obj == null) {
                return '';
            }
            return obj;
        },
        /**
         * 判断是否含有'.'号
         * @method hasDot
         * @param {String} str 输入字符串
         * @returns {Boolean}
         */
        hasDot: function (str) {
            if (typeof str != 'string') {
                return false;
            }
            if (str.indexOf('.') != -1) {
                return true;
            }
            return false;
        },
        /**
         * html转义
         *
         * @method htmlFilter
         * @param {String} content
         * @param {Mixed} type 引号转义方式
         * 过滤掉全部html标签(默认)
         * 1: 转义单引号&html标签
         * 2: 转义双引号&html标签
         * 3: 转义单双引号&html标签
         */
        htmlFilter: function(content,type){
            if (typeof type == 'undefined'){
                content = content.replace(/<\/?[^>]*>/g,''); //去除HTML tag
                content.value = content.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
                //content = content.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
            }
            if(type == 1 || type == 3){
                //单引号
                content = content.replace(/'/g, '&#039;');
            }
            if(type == 2 || type == 3){
                //多引号
                content = content.replace(/&/g, "&amp;").replace(/</g, '&lt;').replace(/>/g, '&gt;');
                content = content.replace(/"/g, '&quot');
            }
            return content;
        },
        /**
         * 转义引号
         *
         * @method htmlFilter
         * @param {String} content
         * @param {Mixed} quota_style 引号转义方式
         * 1: SINGLE <a href='qq'>q</a> --> <a href=\'qq\'>q</a>
         * 2: DOUBLE(默认) <a href="qq">q</a> --> <a href=\"qq\">q</a>
         */
        quote:function(content, quote_style){
            if(typeof quote_style == 'undefined'){
                quote_style = 2;
            }
            //单引号
            if(quote_style == 1){
                content = content.replace(/'/g, '\\\'');
            }
            else if(quote_style == 2){
                content = content.replace(/"/g, '\\"');
            }
            return content;
        }
    }
    var validate = {
        /**
         * 正则集
         *
         * @type {Object}
         * @namespace Rose.validate
         * @class regexp
         */
        regexp:{
            intege:"^-?[1-9]\\d*$",					//整数
            intege1:"^[1-9]\\d*$",					//正整数
            intege2:"^-[1-9]\\d*$",					//负整数
            num:"^([+-]?)\\d*\\.?\\d+$",			//数字
            num1:"^[1-9]\\d*|0$",					//正数（正整数 + 0）
            num2:"^-[1-9]\\d*|0$",					//负数（负整数 + 0）
            decmal:"^([+-]?)\\d*\\.\\d+$",			//浮点数
            decmal1:/^[0-9]*.?\d*$/,                //正浮点数
            decmal2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", //负浮点数
            decmal3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",  //浮点数
            decmal4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$", //非负浮点数（正浮点数 + 0）
            decmal5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$", //非正浮点数（负浮点数 + 0）
            email:/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, //邮件
            color:"^[a-fA-F0-9]{6}$",				//颜色
            url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",	//url
            chinese:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",					//仅中文
            ascii:"^[\\x00-\\xFF]+$",				//仅ACSII字符
            zipcode:"^\\d{6}$",						//邮编
            mobile:/^(13[0-9]|15[0-9]|14[7|5]|18[0-9])\d{8}$/,				//手机
            ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",	//ip地址
            picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",	//图片
            rar:"(.*)\\.(rar|zip|7zip|tgz)$",								//压缩文件
            date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",					//日期
            qq:"^[1-9]*[1-9][0-9]*$",				//QQ号码
            tel:"^(([0\\+]\\d{2,3}(-)?)?(0\\d{2,3})(-)?)?(\\d{7,8})(-(\\d{3,}))?$",	//电话号码的函数(包括验证国内区号,国际区号,分机号)
            name:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z]([\\s.]?[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z]+){1,}$", //真实姓名由汉字、英文字母、空格和点组成，不能以空格开头至少两个字
            addressname:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z]{1,}$",	//收货人
            username:"^[0-9a-zA-Z_\u0391-\uFFE5]{3,15}$",					//用来用户注册。匹配由数字、26个英文字母中文或者下划线组成的字符串 3-15个字符串之间
            letter:"^[A-Za-z]+$",					//字母
            letter_u:"^[A-Z]+$",					//大写字母
            letter_l:"^[a-z]+$",					//小写字母
            idcard:"^[1-9]([0-9]{14}|[0-9]{16}[0-9xX])$",	//身份证
            passwrd:"^[\\w-@#$%^&*]{6,20}$",         //密码保证6-20位的英文字母/数字/下划线/减号和@#$%^&*这些符号
            notempty:function(value){
                return !comm.lang.isEmpty(value);
            },
            empty:function(value){
                return comm.lang.isEmpty(value);
            }
        },
        /**
         * 格式校验方法
         *
         * @method checkReg
         * @param {String} type 正则表达式
         * @param {String} value 验证值
         */
        checkReg: function(_reg,value){
            var reg;
            if(typeof _reg == "string"){
                reg = new RegExp(_reg);
            }
            else if((typeof _reg) == "function"){
                return _reg(value);
            }
            else{
                reg = _reg;
                if(comm.lang.isEmpty(value)){
                    return true;
                }
            }
            return reg.test(value);
        },

        validate:function(target,_reg){
            var value = target.val();
            if(!comm.validate.checkReg(_reg,value)){//如果校验错误
                target.css("border-color","#E87467");
                target.prev(".input-icon").css("color","#E87467");
                target.unbind("change");
                target.bind("change",function(){//增加发生变更时触发事件
                    target.css("border-color","#ccd5db");
                    target.prev(".input-icon").css("color","#ccd5db");
                });
                return false;
            }else{
                target.unbind("change");
                return true;
            }
        },
        validateError:function(target) {
            target.css("border-color", "#E87467");
            target.prev(".input-icon").css("color", "#E87467");
            target.unbind("change");
            target.bind("change", function () {//增加发生变更时触发事件
                target.css("border-color", "#ccd5db");
                target.prev(".input-icon").css("color", "#ccd5db");
            });
        }
    }

    var event = {
        /**
         * 取消事件冒泡
         *
         * @method stopBubble
         * @param {Object} e 事件对象
         */
        stopBubble: function (e) {
            if (e && e.stopPropagation) {
                e.stopPropagation();
            } else {
                // ie
                window.event.cancelBubble = true;
            }
        },
        /**
         * 阻止浏览器默认行为
         *
         * @method stopDefault
         * @param {Object} e 事件对象
         * @return {Boolean}
         */
        preventDefault: function (e) {
            if (e && e.preventDefault) {
                e.preventDefault();
            } else {
                // ie
                window.event.returnValue = false;
            }
            return false;
        }
    }

    var dialog = {
        type:{
            info : "info",
            warn : "warn",
            success : "success",
            error : "error",
            question : "question"
        },
        tipPosition : {
            tc : ["top","center"],  //上中
            bc : ["bottom","center"],  //下中
            lc : ["left","center"],  //左中 
            rc : ["right","center"],  //右中
        },
        openWindows:[],
        /**
         * 页面信息提醒
         * @method notifycation 页面提醒
         * @param type 提醒类型
         * @param content 提醒内容
         * @param func 点击确定按钮的时候调用的方法
         * @param maskClickClosed 是否点击空白地方时候关闭弹框  true-关闭  false-不关闭
         * @param btnText 按钮名称
         * @param title 提示
         * @param close 关闭按钮
         * */
        notification:function(options){
            var content = options.content;
            var func = options.func || function(){};
            var maskClickClosed = options.maskClickClosed;
            var btnText = options.btnText || "确定";
            var title = options.title;
            var width = options.width || "330px";
            var height = options.height || "180px";
            var type = options.type;
            var iconClass = "";
            var close = options.close!=null && options.close==true?true:false;
            if(type == comm.dialog.type.warn){
                iconClass = "fa-warning pui-text-warning";
                maskClickClosed = maskClickClosed || false;
                title = title || "告警";
            }else if(type == comm.dialog.type.success){
                iconClass = "fa-check-circle pui-text-success";
                maskClickClosed = maskClickClosed || false;
                title = title || "成功";
            }else if(type == comm.dialog.type.error){
                iconClass = "fa-times-circle pui-text-error";
                maskClickClosed = maskClickClosed || false;
                title = title || "错误";
            }else if(type==comm.dialog.type.question){
                iconClass = "fa-question-circle pui-text-info";
                maskClickClosed = maskClickClosed || false;
                title = title || "疑问";
            }else{
                iconClass = "fa-info-circle pui-text-info";
                maskClickClosed = maskClickClosed!=null && maskClickClosed==false?false: true;
                title = title || "提示";
            }
            var dialog = $.dialog({
                type:"alert-app",
                maskOpacity : 0.2,
                maskClickClosed:maskClickClosed,
                width: width,
                height: height,
                content : "<div class='pui-center pui-text-center'><p class='pui-font-bold'><h5><i class='fa fa-2x "+iconClass+"' style='margin-right:15px;'></i>"+title+"</h5></p><p>"+content+"</p></div>",
                buttons : {
                    values:{
                        yes:btnText
                    }
                },
                yes : function(){
                    if(!comm.lang.isEmpty(func)){
                        if(comm.lang.isFunction(func)){
                            func.apply(this.target);
                        }
                    }
                }
            });
            dialog.toolbar(close);
        },
        /***
         * 确定对话框
         * @method confirm
         * @param content 提示内容
         * @param func 选择后执行的方法
         * @param title 显示标题，默认为请选择
         * @param okBtnText 确定按钮名称
         * @param cancelBtnText 取消按钮名称
         * */
        confirm:function(options){
            var content = options.content;
            var func = options.func || function(){};
            var title = options.title || "请选择";
            var okBtnText = options.okBtnText || "确定";
            var cancelBtnText = options.cancelBtnText || "取消";
            var width = options.width || "330px";
            var height = options.height || "180px";
            $.dialog({
                type : "confirm-app",
                maskOpacity : 0.2,
                maskClickClosed:false,
                toolbar : false,
                width: width,
                height: height,
                content : "<div class='pui-center pui-text-center'><p class='pui-font-bold'><h5><i class='fa fa-question-circle fa-2x pui-text-secondary' style='margin-right:15px;'></i>"+title+"</h5></p><p>"+content+"</p></div>",
                buttons:{
                  values:{
                      yes:okBtnText,
                      cancel:cancelBtnText
                  }
                },
                yes : function(){
                    func.apply(this.target,[true]);
                },
                cancel : function(){
                    func.apply(this.target,[false]);
                }
            });
        },
        /***
         * 弹出输入框
         * @method prompt
         * @param content 提示内容
         * @param func 输入后执行函数
         * @param isNotFullFlag 录入内容是否可为空
         * @param title 显示标题
         * @param okBtnText 确定按钮名称
         * @param cancelBtnText 取消按钮名称
         * @param width 对话框宽度
         * @param height 对话框高度
         * */
        prompt:function(options){
            var content = options.content;
            var func = options.func || function(){};
            var isNotNullFlag = options.isNotNullFlag || false;
            var title = options.title || "请输入";
            var okBtnText = options.okBtnText || "确定";
            var cancelBtnText = options.cancelBtnText || "取消";
            var width = options.width || "330px";
            var height = options.height || "220px";
            var password = options.password || false;
            var maskOpacity = options.maskOpacity || 0.2;

            var dialog = $.dialog({
                type : "prompt-app",
                maskOpacity : maskOpacity,
                maskClickClosed:false,
                width: width,
                height: height,
                content : "<div class='pui-center pui-text-center'><p class='pui-font-bold'><h5><i class='fa fa-edit fa-2x pui-text-secondary' style='margin-right:15px;'></i>"+title+"</h5></p><p>"+content+"</p></div>",
                toolbar : false,
                buttons:{
                    value:{
                        yes:okBtnText,
                        cancel:cancelBtnText
                    }
                },
                yes : function(){
                    var value = this.target.find('[pui-dialog-prompt-input]').val();
                    if(isNotNullFlag && isNotNullFlag==true){
                        if(comm.lang.isEmpty(value)){
                            this.target.find('[pui-dialog-prompt-input]').attr("placeholder","该值不能为空");
                            return;
                        }
                    }
                    this.close();
                    func.apply(this.target,[true,value]);
                },
                cancel : function(){
                    var value = this.target.find('[pui-dialog-prompt-input]').val();
                    this.close();
                    func.apply(this.target,[false,value]);
                }
            });
            if (password){
                var inputObj = dialog.target.find('[pui-dialog-prompt-input]');
                inputObj.attr("type", "password");
                inputObj.focus();
                inputObj.keydown(function(event){
                    if (event.keyCode == 13 || event.keyCode == 9){ //13 enter, 9 tab
                        dialog.yes();
                    };
                });
            }
        },
        /**
         * 页面加载，出现滚动条
         * */
        load:function(loadingText){
            if(comm.lang.isEmpty(loadingText)){
                loadingText = "loading...";
            }
            return $.dialog({
                type: "loading",
                toolbar: false,
                content:'<div class="pui-loading pui-loading-spinner pui-animation-rotate pui-animation-repeat pui-animation-reverse"><span class="fa fa-spinner fa-3x"></span></div><p>'+loadingText+'</p>',
                top: "center",
                maskOpacity: 0.2,
                maskClickClosed: false
            });
        },
        /**
         * 页面加载关闭
         * */
        unload:function(loadDialog){
            if(loadDialog==null || loadDialog==undefined){
                var loadDialogs = $(".pui-dialog[type='loading']");
                if(loadDialogs!=null && loadDialogs.length>0){
                    loadDialog = $.dialog.get(loadDialogs.attr("id"));
                }
            }
            if(loadDialog){
                loadDialog .close();
            }
        },
        /**
         * 页面遮罩
         * */
        mask:function(){
            var maskDiv = $("[div-pui-mask]");
            if(maskDiv==null || maskDiv == undefined || maskDiv.length<=0){
                maskDiv = $("<div class='pui-mask pui-mask-bg pui-mask-fixed pui-hide' div-pui-mask><div class='pui-mask pui-mask-container'><div class='pui-mask-content'></div></div></div>");
                $("body").append(maskDiv)
            }
            maskDiv.removeClass('pui-hide').fadeIn();
        },
        /**
         * 页面遮罩关闭
         * */
        unmask:function(){
            var maskDiv = $("[div-pui-mask]");
            if(maskDiv && maskDiv.length>0){
                maskDiv.fadeOut();
            }
        },
        /**
         * 内容提示
         * @param target 给哪个对象添加提示，Jquery对象
         * @param position 提示的位置，上中下
         * @param content 提示内容
         * @param srvId 调用服务获取提示内容，服务编号
         * @param param 调用服务获取提示内容，参数
         * @param type 提示的类型  error  success
         * */
        tip:function(options){
            var target = options.target;
            var position = options.position || comm.dialog.tipPosition.bc;
            var content = options.content || "";
            var srvId = options.srvId || '';
            var param = options.param || {};
            var type = options.type;
            var addClass = "pui-tooltip-bordered";
            if(type==comm.dialog.type.success){
                addClass = addClass + " pui-tooltip-success-light";
            }else if(type==comm.dialog.type.warn){
                addClass = addClass + " pui-tooltip-warning-light";
            }else if(type==comm.dialog.type.info){
                addClass = addClass + " pui-tooltip-info-light";
            }else if(type==comm.dialog.type.error){
                addClass = addClass + " pui-tooltip-error-light";
            }else if(type==comm.dialog.type.question){
                addClass = addClass + " pui-tooltip-primary-light";
            }else{
                addClass = addClass + " pui-tooltip-secondary-light";
            }

            var paramHandle = options.paramHandle||function(retJson,isSucc){
                    return JSON.stringify(retJson);
                };
            var callback = options.callback || function(){};
            var showDiv = options.showDiv || "";
            var cached = true;
            if(!comm.lang.isEmpty(srvId)){
                cached = false;
            }

            $(target).tooltip({
                position : position,
                auto : true,
                target : showDiv,
                cached : cached,
                addClass:addClass,
                showMode:"fade",
                content : function(self, tooltip, resetStyle) {
                    if(!comm.lang.isEmpty(srvId)){
                        tooltip.html('<div class="pui-inline-block pui-animation-rotate pui-animation-repeat pui-animation-reverse pui-text-white"><span class="fa fa-spinner fa-2x"></span></div> 正在加载内容，请稍后...');
                        resetStyle(tooltip);
                        comm.ajax.ajaxEntity({
                            srvId : srvId,
                            param : param,
                            mask:false,
                            callback:function(retJson,isSucc) {
                                tooltip.html(paramHandle(retJson,isSucc));
                                resetStyle(tooltip);
                            }
                        });
                    }else{
                        tooltip.html(content);
                        resetStyle(tooltip);
                    }
                },
                callback : function(self, tooltip) {
                    callback(self,tooltip);
                }
            });
        },
        /**
         * 打开图片
         * @param imgUrl 打开图片地址，如果为多个图片，则content为数组
         * @param width 宽度
         * @param height  高度
         * @param maskClickClosed 点击遮罩层是否关闭页面
         * @param close 是否显示关闭按钮，默认显示
         * */
        image:function(options){
            var imgUrl = options.imgUrl;
            var width = options.width || "";
            var height = options.height || "";
            var maskClickClosed = options.maskClickClosed || true;
            var close = options.close!=null && options.close==false?false:true;
            var dialog = $.dialog({
                type: "image",
                width :width,
                height:height,
                maskClickClosed:maskClickClosed,
                animated: false,
                padding: 20,
                content : imgUrl
            });
            dialog.toolbar(close);
        },
        /**
         * 打开窗口
         * @param title 标题
         * @param secondTitle 副标题
         * @param content 页面内容
         * @param url 打开页面链接，与content互斥
         * @param footerContent 脚本提示内容
         * @param width 宽度
         * @param height 高度
         * @param iconClass 图标样式
         * @param timeout 动画时长
         * @param maskClickClosed 点击遮罩关闭页面
         * @param callback 确认关闭页面时回调函数
         * @param toolbar {
         *   refresh 是否显示刷新按钮，默认false
         *   blank 是否打开新窗口  默认false
         *   max 是否显示最大化按钮 默认false
         *   min 是否显示最小化按钮  默认false
         *   close 是否显示关闭按钮  默认true
         * }
         * */
        window:function(options){
        	var title = options.title || "";
            var secondTitle = options.secondTitle || "";
            var content = options.content || "";
            var url = options.url;
            var footerContent = options.footerContent || "";
            var width = options.width;
            var height = options.height;
            var iconClass = options.iconClass || "fa fa-cube";
            var timeout = options.timeout;
            var maskClickClosed = options.maskClickClosed||false;
            var callback = options.callback||function(){};
			var param = {
                maskClickClosed:maskClickClosed,
                width:width,
                height:height
            };
            	
            if(!comm.lang.isEmpty(url)){
            	param.type = "iframe";
            	param.url = url;
                param.animated = false;
            }else{
            	param.type = "window";
            	param.content = content;
            }
            if(comm.lang.isEmpty(title) && comm.lang.isEmpty(secondTitle)){
            	param.header = {hasHeader:false};
            	param.style = {
            		border: "5px solid #ccc"
            	}
            }else{
            	param.title = title;
            	param.header = {
                    hasHeader:true,
            		subTitle:"<small class='pui-text-primary'>"+secondTitle+"</small>",
            		icon :"<i class='"+iconClass+"'></i>",
                    className : "font-green2 pui-unbordered"
            	}
                param.padding = "20";
            }
            if(!comm.lang.isEmpty(timeout)){
            	param.timeout = timeout;
            }
            param.dataReturn = options.dataReturn;
            var dialog = $.dialog(param);
            if(!comm.lang.isEmpty(footerContent)){
                dialog.footer("<small>"+footerContent+"</small>");
            }
            if(options.toolbar){
                dialog.toolbar({
                    refresh : options.toolbar.refresh || false,
                    blank : options.toolbar.blank || false,
                    max : options.toolbar.max || false,
                    min : options.toolbar.min || false,
                    close : options.toolbar.close!=null && options.toolbar.close==false?false:true
                });
            }
            comm.dialog.openWindows.push({
                id:"iframe-"+dialog.id,
                callback:callback,
                dialog:dialog
            })
        },
        /**
         * 关闭打开的窗口
         * @param id iframe的编号，默认不传
         * @param confirm 是否确认，如果确认的话则会调父页面的方法，否则不会调用
         * @param retVal 返回值
         * */
        unWindow:function(options){
            var id = options.id || '';
            var confirm = options.confirm && options.confirm == true ? true : false;
            var retVal = options.retVal || '';

            var openWindows = [];
            var iframes = []
            if (!(options.isClosed && options.isClosed == true)) {
                openWindows = parent.comm.dialog.openWindows;
                iframes = parent.document.getElementsByTagName("iframe");
            } else {
                openWindows = comm.dialog.openWindows;
                iframes = document.getElementsByTagName("iframe");
            }
            if (openWindows != null && openWindows.length > 0) {
                if (id == null || id == "") {
                    //根据target获取id
                    if (iframes != null && iframes.length > 0) {
                        for (var i = 0; i < iframes.length; i++) {
                            if (iframes[i].id && iframes[i].id.indexOf("iframe-pui-dialog") != -1) {
                                id = iframes[i].id;
                                break;
                            }
                        }
                    }
                }
                for (var i = 0; i < openWindows.length; i++) {
                    if (id == openWindows[i].id) {
                        var dialog = openWindows[i];
                        if (!(options.isClosed && options.isClosed == true)) {
                            dialog.dialog.close();//调用关闭方法
                            if (dialog.callback && confirm == true) {
                                dialog.callback(retVal);
                            }
                        } else {
                            if (dialog.callback && confirm == false) {
                                dialog.callback(retVal);
                            }
                        }
                        openWindows.splice(i, 1);
                        break;
                    }
                }
            }
        },
        /**
         * 通知公告
         * @type 类型
         * @timeout 多长时间后自动消失
         * @content 内容
         * */
        notice:function(options){
            var type = options.type;
            var timeout = options.timeout || 3000;
            var content = options.content||"";
            var position = options.position || "";

            var iconClass = "";
            if(type==comm.dialog.type.warn){
                iconClass = "pui-notice-icon-warning";
            }else if(type==comm.dialog.type.error){
                iconClass = "pui-notice-icon-error";
            }else if(type==comm.dialog.type.success){
                iconClass = "pui-notice-icon-success";
            }else if(type==comm.dialog.type.question){
                iconClass = "pui-notice-icon-question";
            }else{
                iconClass = "pui-notice-icon-info";
            }

            if(position=="center"){
                iconClass = iconClass + " pui-notice-center";
            }

            var article = $("<article class='pui-notice pui-notice-icon "+iconClass+"' style='z-index:999'>" +
                "<i class='pui-close pui-close-circle'></i><div class='pui-notice-content'><p>"+content+"</p></div>"+
            "</article>");
            var closeBtn =article.find(".pui-close").bind($.clickOrTouch(), function() {
                $(this).parent().css({minHeight: 0}).slideUp(500, function() {
                    $(this).remove();
                });
            });
            if(timeout!=null && timeout>0){
                timeout  = parseInt(timeout);
                var timer = setTimeout(function(){
                    closeBtn.trigger($.clickOrTouch());
                    clearTimeout(timer);
                }, timeout);
            }
            if(position=="center"){
                $("body").append(article);
            }else{
                var target = $(".pui-notice-position-br");
                if(target==null || target==undefined || target.length<=0){
                    target = $("<div class='pui-notice-popup pui-notice-position-br'></div>");
                    $("body").append(target);
                }
                target.append(article);
            }
        }
    }

    var tools = {
        /**
         * console.log方法(兼容IE)
         *
         * @method log
         * @param {All} text
         */
        log:function(text){
            window.console && console.log(text);
        },

        /**
         * 参数分别为：busiFrameId
         * 或者busiId，operateId
         * @param param1
         * @param param2
         */
        getDyncHtmlUrl: function (param1, param2) {
            var busiFrameId;
            var busiId;
            var operateId;
            if (param1 && param2) {
                busiId = "" + param1;
                operateId = "" + param2;
            } else if (param1 && param2 == undefined) {
                busiFrameId = "" + param1;
            } else {
                comm.dialog.notice({
                    type: comm.dialog.type.error,
                    position: "center",
                    content: "参数不能都为空！",
                    timeout: 800
                });
            }
            var url;
            comm.ajax.ajaxEntity({
                busiCode: busiCode.common.ICFGDYNCCOMMONFSV_GETREQUESTURL,
                mask: false,
                sync: false,
                param: {
                    busiFrameId: busiFrameId,
                    busiId: busiId,
                    operateId: operateId
                },
                callback: function (data, isSucc, msg) {
                    if (isSucc && data.template && data.template.templateUrl && data.busiFrameId) {
                        url = data.template.templateUrl + "?busiFrameId=" + data.busiFrameId;
                    }else{
                        comm.dialog.notification({
                            type: comm.dialog.type.error,
                            position: "center",
                            content: "调用失败！" + msg,
                            timeout: 800
                        });
                    }
                }
            });
            return url;
        },

        /**
         * 获取当前登录用户信息
         * **/
        getCurrUser:function(callback){
            //看session中是否有该用户信息
            if(comm.storage.SessionStorage.get("ESOP_SESSION_USER")!=null){
                callback(JSON.parse(comm.storage.SessionStorage.get("ESOP_SESSION_USER")));
                return;
            }
            //调用服务获取用户信息
            comm.ajax.ajax({
                url:busiCode.other.GET_CURR_USER,
                mask:false,
                sync:true,
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        comm.storage.SessionStorage.set("ESOP_SESSION_USER",JSON.stringify(data));
                        callback(data);
                    }else{
                        callback(null);
                    }
                }
            });
        },

        /**
         * 获取用户信息
         * **/
        getUser:function(options){
            //调用服务获取用户信息
            comm.ajax.ajax({
                url:busiCode.other.GET_USER,
                param:{
                    operatorCode: options.operatorCode
                },
                mask:false,
                sync:false,
                callback:function(data,isSucc,msg){
                    if(isSucc){
                        if (options.callback){
                            options.callback(data);
                        }
                    }
                }
            });
        },

        /**
         * 调用服务获取功能权限
         * **/
        getPrivs:function(options){//获取功能权限
            var operatorId = options.operatorId;
            var stationId = options.stationId;
            var groupId = options.groupId;
            var privId = options.privId;
            var sync = options.sync;
            var callback = options.callback;
            var retData = [];
            if(comm.lang.isEmpty(operatorId)){
                comm.tools.getCurrUser(function (userInfo) {
                    operatorId = userInfo.operatorId;
                });
            }
            if(comm.lang.isEmpty(stationId)) {
                comm.ajax.ajaxEntity({
                    busiCode: busiCode.sec.ISECFSV_GETBASESTATION,
                    param:{operatorId:operatorId},
                    mask: true,
                    callback: function (data, isSucc) {
                        if (isSucc) {
                            stationId = data;
                        }
                    }
                });
            }
            comm.ajax.ajaxEntity({
                busiCode:busiCode.sec.ISECFSV_GETPRIVSBYOPERATORID,
                moduleName:"common",
                param:{
                    operatorId:operatorId,
                    stationId:stationId,
                    groupId:groupId,
                    privId:privId
                },
                mask:false,
                sync:false,
                callback:function(data,isSucc,msg){
                    if(isSucc && data!=null && data.length>0){//接口调用成功
                        retData = data;
                    }
                    if(callback){
                        callback(retData);
                    }
                }
            });
            return retData;
        },
        /***
         * 获取当前URL的请求参数
         * */
        getRequest: function () {
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = {};
            var param = [];
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    param = strs[i].split("=");
                    try {
                        theRequest[param[0]] = decodeURI(param[1]);
                    } catch (e) {
                        comm.tools.log(e);
                    }
                }
            }
            return theRequest;
        },
        /**
         * 在当前url后面追加参数
         * */
        appendRequetParam:function(url,params){
            if(params!=null) {
                if (url.indexOf("?") == -1) {
                    url = url + "?";
                }
                for(var t in params){
                    url = url +"&"+t+"="+params[t];
                }
            }
            return url;
        },
        /**
         * 调用服务获取静态数据
         * **/
        getStaticData:function(options){//获取静态数据
            var codeType = options.codeType;
            var codeValue = options.codeValue;
            var parentCode = options.parentCode;//父节点
            var sync = options.sync;
            var callback = options.callback;
            var retData = [];
            if(comm.lang.isEmpty(codeType)){
                callback([]);
                return;
            }
            if(!comm.lang.isEmpty(codeValue)) {
                if (!comm.lang.isArray(codeValue)) {
                    codeValue = [codeValue];
                }
            }
            if(!comm.lang.isEmpty(parentCode)) {
                if (!comm.lang.isArray(parentCode)) {
                    parentCode = [parentCode];
                }
            }
            if(comm.storage.SessionStorage.get("STATIC_DATA_"+codeType)!=null){
                var staticData = JSON.parse(comm.storage.SessionStorage.get("STATIC_DATA_"+codeType));
                if(codeValue!=null && codeValue.length>0){
                    if(staticData!=null && staticData.length>0){
                        for(var i=0;i<staticData.length;i++) {
                            for (var j = 0; j < codeValue.length; j++) {
                                if (staticData[i].codeValue == codeValue[j]) {
                                    retData.push(staticData[i]);
                                }
                            }
                        }
                    }
                }else if(parentCode!=null && parentCode.length>0) {
                    if (staticData != null && staticData.length > 0) {
                        for (var i = 0; i < staticData.length; i++) {
                            for (var j = 0; j < parentCode.length; j++) {
                                if (staticData[i].parentCode == parentCode[j]) {
                                    retData.push(staticData[i]);
                                }
                            }
                        }
                    }
                }else {
                    retData = staticData;
                }
                if(callback){
                    callback(retData);
                }
            }else{
                comm.ajax.ajaxEntity({
                    busiCode:busiCode.common.ICFGSTATICDATAFSV_GETSTATICDATABYCODETYPE,
                    moduleName:"common",
                    param:{
                        codeType:codeType
                    },
                    mask:false,
                    sync:sync,
                    callback:function(staticData,isSucc,msg){
                        if(isSucc){//接口调用成功
                            staticData = staticData[codeType];
                            //将数据放在session中
                            comm.storage.SessionStorage.set("STATIC_DATA_"+codeType,JSON.stringify(staticData));
                            if(!comm.lang.isEmpty(codeValue)){
                                if(staticData!=null && staticData.length>0){
                                    for(var i=0;i<staticData.length;i++){
                                        for (var j = 0; j < codeValue.length; j++) {
                                            if (staticData[i].codeValue == codeValue[j]) {
                                                retData.push(staticData[i]);
                                            }
                                        }
                                    }
                                }
                            }else if(!comm.lang.isEmpty(parentCode)){
                                if(staticData!=null && staticData.length>0){
                                    for(var i=0;i<staticData.length;i++){
                                        for (var j = 0; j < parentCode.length; j++) {
                                            if (staticData[i].parentCode == parentCode[j]) {
                                                retData.push(staticData[i]);
                                            }
                                        }
                                    }
                                }
                            }else {
                                retData = staticData;
                            }
                        }else{
                            comm.dialog.notice({
                                type:comm.dialog.type.error,
                                position:"center",
                                content:msg,
                                timeout:800
                            });
                        }
                        if(callback){
                            callback(retData);
                        }
                    }
                })
            }
            return retData;
        },
        /**
         * 解析对象，并将它填充至指定对象中
         * @param type 用来表示该字段类型  date datetime staticdata static dynamic boolean string
         * @param prefix 字段展示时前缀
         * @param postfix 字段展示时后缀
         * @param defaultValue 默认值，如果为值为空，则默认展示该值
         * @param valueSplitFlag 值之间用什么符号分割
         * @param srvId 服务编号，只有type为dynamic才有效
         * @param schemaData 只有type为dynamic才有效
         * @param dataTextField
         * @param dataValueField
         * @param param 参数，如果type为staticdata，则表示静态数据，可以为json串或某个方法
         * **/
        setHtmlData:function(data){
            if(!comm.lang.isEmpty(data)){
                $.each(data, function (key, value) {
                    var target = $("#" + key);
                    var type = target.attr("type")?target.attr("type").toLowerCase():"string";
                    var prefix = target.attr("prefix")?target.attr("prefix"):"";
                    var postfix = target.attr("postfix")?target.attr("postfix"):"";
                    var defaultValue = target.attr("defaultValue");
                    var isDefaultValue = false;
                    if(comm.lang.isEmpty(value)){
                        if(!comm.lang.isEmpty(defaultValue)){
                            value = defaultValue;
                            isDefaultValue = true;
                        }else{
                            value = "";
                        }
                    }
                    var text = "";
                    switch(type){
                        case "date":  //日期
                            if(!comm.lang.isEmpty(value)) {
                                var date = new Date(value);
                                text = comm.date.dateTime2str(date, "yyyy-MM-dd");
                            }
                            break;
                        case "datetime"://日期时间
                            if(!comm.lang.isEmpty(value)) {
                                var date = new Date(value);
                                text = comm.date.dateTime2str(date, "yyyy-MM-dd hh:mm:ss");
                            }
                            break;
                        case "staticdata"://静态数据，固定数据
                            var valueSplitFlag = target.attr("valueSplitFlag");
                            var dataTextField = target.attr("dataTextField")?target.attr("dataTextField"):"text";
                            var dataValueField = target.attr("dataValueField")?target.attr("dataValueField"):"value";
                            var data = target.attr("param");
                            if(!comm.lang.isEmpty(value)) {
                                var paramValue = [value];
                                if (!comm.lang.isEmpty(valueSplitFlag)) {
                                    paramValue = value.split(valueSplitFlag);
                                } else {
                                    valueSplitFlag = "";
                                }
                                try {
                                    data = eval(data)(value);
                                }catch(e){
                                    data = eval(data) || {};
                                }
                                for (var i = 0; i < paramValue.length; i++) {
                                    if (comm.lang.isArray(data)) {
                                        for (var j = 0; j < data.length; j++) {
                                            if (data[j][dataValueField] == paramValue[i]) {
                                                if (!comm.lang.isEmpty(text)) {
                                                    text += valueSplitFlag;
                                                }
                                                text += data[j][dataTextField];
                                            }
                                        }
                                    } else {
                                        if (data[dataValueField] == value) {
                                            if (!comm.lang.isEmpty(text)) {
                                                text += valueSplitFlag;
                                            }
                                            text += data[dataTextField];
                                        }
                                    }
                                }
                            }
                            break;
                        case "static"://静态数据
                            var valueSplitFlag = target.attr("valueSplitFlag");
                            var codeType = target.attr("param");
                            if(!comm.lang.isEmpty(value)) {
                                var codeValue = [value];
                                if (!comm.lang.isEmpty(valueSplitFlag)) {
                                    codeValue = value.split(valueSplitFlag);
                                } else {
                                    valueSplitFlag = "";
                                }
                                comm.tools.getStaticData({
                                    codeType: codeType,
                                    codeValue: codeValue,
                                    sync: false,
                                    callback: function (retData) {
                                        if (!comm.lang.isEmpty(retData)) {
                                            for (var i = 0; i < retData.length; i++) {
                                                if (i > 0) {
                                                    text += valueSplitFlag;
                                                }
                                                text += retData[i].codeName;
                                            }
                                        }
                                    }
                                });
                            }
                            break;
                        case "dynamic"://动态数据
                            var valueSplitFlag = target.attr("valueSplitFlag");
                            var param = target.attr("param");
                            var srvId = target.attr("srvId");
                            var schemaData = target.attr("schemaData")?target.attr("schemaData"):"";
                            var dataTextField = target.attr("dataTextField")?target.attr("dataTextField"):"text";
                            var dataValueField = target.attr("dataValueField")?target.attr("dataValueField"):"value";
                            if(!comm.lang.isEmpty(value)) {
                                var paramValue = [value];
                                if (!comm.lang.isEmpty(param)) {
                                    try{
                                        param = eval(param)(value)
                                    }catch(e){
                                        param = param || "";
                                    }
                                }
                                if (!comm.lang.isEmpty(valueSplitFlag)) {
                                    paramValue = value.split(valueSplitFlag);
                                } else {
                                    valueSplitFlag = "";
                                }
                                comm.ajax.ajaxEntity({
                                    sync: false,
                                    param: param,
                                    busiCode: srvId,
                                    callback: function (data, isSucc, msg) {
                                        if (isSucc) {
                                            if (!comm.lang.isEmpty(schemaData)) {
                                                data = data[schemaData];
                                            }
                                            for (var i = 0; i < paramValue.length; i++) {
                                                if (comm.lang.isArray(data)) {
                                                    for (var j = 0; j < data.length; j++) {
                                                        if (data[j][dataValueField] == paramValue[i]) {
                                                            if (!comm.lang.isEmpty(text)) {
                                                                text += valueSplitFlag;
                                                            }
                                                            text += data[j][dataTextField];
                                                        }
                                                    }
                                                } else {
                                                    if (data[dataValueField] == value) {
                                                        if (!comm.lang.isEmpty(text)) {
                                                            text += valueSplitFlag;
                                                        }
                                                        text += data[dataTextField];
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            }

                            break;
                        case "file"://如果为文件
                            if(!comm.lang.isEmpty(value)) {
                                comm.ajax.ajaxEntity({
                                    busiCode: busiCode.common.IELECFSV_QUERYELECINS,
                                    sync: false,
                                    param: {
                                        fileInputId: value
                                    },
                                    callback: function (data, isSucc, msg) {
                                        if (isSucc && data) {
                                            for (var i = 0; i < data.length; i++) {
                                                var url = PROJECT_URL + "/elec/download?fileTypeId=" + data[i].fileTypeId + "&fileSaveName=" + data[i].fileSaveName + "&fileName=" + encodeURI(encodeURI(data[i].fileName));
                                                text += "<a href='"+url+"' style='text-decoration: underline;padding-right:15px;cursor:pointer;'>" + data[i].fileName + "</a>";
                                            }
                                            if (data.length == 0) {
                                                text += "无附件";
                                            }
                                        } else {
                                            comm.dialog.notification({
                                                title: "错误",
                                                type: comm.dialog.type.error,
                                                content: "获取数据异常"
                                            });
                                        }
                                    }
                                });
                            }
                            break;
                        case "boolean"://true/false
                            if(!comm.lang.isEmpty(value)) {
                                if (value == 0) {
                                    text = "false"
                                } else {
                                    text = "true"
                                }
                            }
                            break;
                        default:
                            text = value;
                            break;
                    }

                    target.attr("value",value);
                    target.attr("isDefault",isDefaultValue);
                    if(comm.lang.isEmpty(text)){
                        text = value;
                    }
                    target.html(prefix+text+postfix);
                });
            }
        }
    };

    var crypto = {
        encryptByDES:function(data,key){
            var keyHex = CryptoJS.enc.Utf8.parse(key);
            var encrypted = CryptoJS.DES.encrypt(data, keyHex, {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
            });
            return encodeURIComponent(encrypted.ciphertext.toString(CryptoJS.enc.Base64));
        },
        decryptByDES:function(data,key){
            var keyHex = CryptoJS.enc.Utf8.parse(key);
            var decrypted = CryptoJS.DES.decrypt(data, keyHex, {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
            });
            return decrypted.toString(CryptoJS.enc.Utf8);
        },
        encryptByRSA:function(data,publicKey){
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var encrypted = encrypt.encrypt(data);
            return encodeURIComponent(encrypted);
        },
        decryptByRSA:function(data,privatekey){
            var decrypt = new JSEncrypt();
            decrypt.setPrivateKey(privatekey);
            var uncrypted = decrypt.decrypt(data);
            return uncrypted;
        }
    }

    var stroge = {
        LocalStorage : {
            set: function(key, value) {
                window.localStorage.setItem(key, value);
            },
            get:function(key){
                return window.localStorage.getItem(key);
            },
            remove:function(key){
                window.localStorage.removeItem(key);
            },
            clear: function(){
                window.localStorage.clear();
            },
            update: function(key,value){
                window.localStorage.removeItem(key);
                window.localStorage.setItem(key, value);
            }
        },
        SessionStorage :{
            set: function (key, value) {
                window.sessionStorage.setItem(key, value);
            },
            get: function (key) {
                return window.sessionStorage.getItem(key);
            },
            remove: function (key) {
                window.sessionStorage.removeItem(key);
            },
            clear: function () {
                window.sessionStorage.clear();
            },
            update: function (key, value) {
                window.sessionStorage.removeItem(key);
                window.sessionStorage.setItem(key, value);
            }
        }
    }
    /**
     * 去掉字符串前面和最后的空格
     *
     * @method trim
     * @return {String}
     */
    String.prototype.trim = function() {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    };
    /**
     * 去掉字符串中所有的空格
     *
     * @method trimBlanks
     * @return {String}
     */
    String.prototype.trimBlanks = function() {
        return this.replace(/(\s*)/g, "");
    };
    /**
     * 去掉字符串前面(prefix)的空格blank
     *
     * @method trimPreBlank
     * @return {String}
     */
    String.prototype.trimPreBlank = function() {
        return this.replace(/(^\s*)/g, "");
    };
    /**
     * 去掉字符串后面(suffix)的空格blank
     *
     * @method trimSufBlank
     * @return {String}
     */
    String.prototype.trimSufBlank = function() {
        return this.replace(/(\s*$)/g, "");
    };
    /**
     * 去掉字符串中所有的character (<,>,&,")
     *
     * @method trimChars
     * @param {String/Number} character 字符
     * @param {Boolean} caseSensitive [optional,default=false] 是否区分大小写
     * @return {String}
     */
    String.prototype.trimChars = function(character, caseSensitive) {
        if (character === '') {
            character = ' ';
        }
        var i = 'i';
        if (caseSensitive) {
            i = '';
        }
        return this.replace(new RegExp("(" + character + ")", i + "g"), "");
    };
    /**
     * 去掉字符串最后面的','end//[逗号Comma]
     *
     * @method trimCom
     * @return {String}
     */
    String.prototype.trimCom = function() {
        return this.replace(/(\,$)/g, "");
    };
    /**
     * 去掉字符串中前面的'0'
     *
     * @method trimZero
     * @return {String}
     */
    String.prototype.trimPreZero = function() {
        return this.replace(/(^0*)/g, "");
    };

    /**
     * 计算字符串的长度（一个双字节字符按UTF-8长度计3(aaa)，ASCII字符计1）
     *
     * @method sizeUTF8
     * @return {String}
     */
    String.prototype.sizeUTF8 = function() {
        return this.replace(/[^\x00-\xff]/g, "aaa").length;
    };
    /**
     * 计算字符串的长度（一个双字节字符按DWORD长度计2(aa)，ASCII字符计1）
     *
     * @method sizeDW
     * @return {String}
     */
    String.prototype.sizeDW = function() {
        return this.replace(/[^\x00-\xff]/g, "aa").length;
    };
    /**
     * 清除前面和后面的换行符
     *
     * @method trimLines
     * @return {String}
     */
    String.prototype.trimLines = function() {
        return this.replace(/(^\n+)|(\n+$)/g, "");
    };
    /**
     * 将多个换行替换为一个
     *
     * @method rowSpan
     * @return {String}
     */
    String.prototype.rowSpan = function() {
        return this.replace(/(\n+)/g, "\n");
    };
    /**
     * 将\n替换为\r\n<br>
     * 在windows系统下，回车换行符号是"\r\n".但是在Linux等系统下是"\n"符号
     *
     * @method lr2crlf
     * @return {String}
     */
    String.prototype.lf2crlf = function() {
        return this.replace(/(\n+)/g, "\r\n");
    };
    /**
     * 格式化字符串,将{n},替换为对应的参数，如：'I {0}&{1} China.'.formatArgs('love','like'); 输出："I
     * love&like China."
     *
     * @method formatArgs
     * @param {String/Number} arguments
     * @return {String}
     */
    String.prototype.formatArgs = function() {
        var thiz = this;
        for ( var i = 0; i < arguments.length; i++) {
            var param = "\{" + i + "\}";
            thiz = thiz.replace(param, arguments[i]);
        }
        return thiz;
    };


    //日期转换类型
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "h+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };

    /**
     * 根据title判断某个tab是否存在
     * @param title
     * @returns {boolean}
     */
    kendo.ui.TabStrip.prototype.isExist = function(title){
        var that = this;
        var isExist = false;
        $(that.items()).each(function () {
            var title_ = $(this).find(".k-link").text();
            if (title.indexOf(">" + title_ + "<") >= 0 || title.indexOf(">" + title_) >= 0 || title.indexOf(title_ + "<") >= 0 || title == title_) {
                isExist = true;
            }
        });
        return isExist;
    };

    /**
     * 根据标题选中某个tab
     * @param title
     */
    kendo.ui.TabStrip.prototype.selectByTitle = function(title){
        var that = this;
        $(that.items()).each(function () {
            var title_ = $(this).find(".k-link").text();
            if (title.indexOf(">" + title_ + "<") >= 0 || title.indexOf(">" + title_) >= 0 || title.indexOf(title_ + "<") >= 0 || title == title_) {
                that.select($(this));
            }
        });
    };

    $.extend(comm,{
        ajax:ajax,
        browser:browser,
        cookie:cookie,
        date:date,
        dom:dom,
        lang:lang,
        validate:validate,
        event:event,
        dialog:dialog,
        tools:tools,
        crypto:crypto,
        storage:stroge
    });
})();

