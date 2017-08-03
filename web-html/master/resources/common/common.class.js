/**
 * 类似于Prototype.js的Class类
 * 想要理解下面的内容可以看看这里
 * http://blog.buymeasoda.com/understanding-john-resigs-simple-javascript-i/
 */
(function () {
    var initializing = false;
    // just a quick & dirty way to check if "function decompilation" works.
    var fnTest = /xyz/.test(function () {
        xyz;
    }) ? /\b_super\b/ : /.*/;

    /**
     * 基础类,不做任何事情
     * @class
     * @constructor
     */
    this.Class = function () {
    };

    /**
     * 创建一个类,让新类继承至这个类
     * @param {Object} props
     * @return {Class}
     */
    Class.extend = function (props) {
        var _super = this.prototype;

        // 初始化父类(只是创建一个父类的实例, 没有调用构造函数)
        initializing = true;
        var prototype = new this();
        initializing = false;

        // 把属性复制到新类的prototype
        for (var name in props) {
            // 检查是不是在overwrite函数
            prototype[name] = typeof props[name] == "function" && typeof _super[name] == "function"
                && fnTest.test(props[name]) ? (function (name, fn) {
                return function () {
                    var tmp = this._super;

                    // 添加一个新的_super()方法,
                    this._super = _super[name];

                    // 这个方法只需要临时绑定,当执行完以后就不需要了
                    var ret = fn.apply(this, arguments);
                    this._super = tmp;

                    return ret;
                };
            })(name, props[name]) : props[name];
        }

        // 新类的构造函数
        function Class() {
            // 所有的初始化操作其实都在init方法中,如果没有正在初始化就不执行
            if (!initializing && this.init){
                this.init.apply(this, arguments);
            }
        }

        // 把我们创建的原型复制给新的类
        Class.prototype = prototype;

        // 指定构造函数为我们希望的函数
        Class.prototype.constructor = Class;

        // 让新类可以扩展
        Class.extend = arguments.callee;

        return Class;
    };
})();