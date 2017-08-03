/**
 * $Id: ExpressionUtil.java,v 1.0 2016/9/28 15:42 XiaoKe Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * @author XiaoKe
 * @version $Id: ExpressionUtil.java,v 1.1 2016/9/28 15:42 XiaoKe Exp $
 *          Created on 2016/9/28 15:42
 */
public class ExpressionUtil {
    /**
     * 通过脚本引擎获取表达式的值,作用类似于js中eval
     *
     * @param expression
     * @param map
     * @return
     * @throws ScriptException
     */
    public static Object getExpressionValue(String expression, Map<String, Object> map) throws ScriptException, Exception {
        if ("".equals(expression)) {
            throw new Exception("表达式不能为空");
        }
        expression=expression.trim();
        int startIndex = 0;
        int endIndex = 0;
        if (expression.contains("{") && expression.contains("}")) {
            for (int i = 0; i < expression.length(); i++) {
                if ((expression.charAt(i) + "").equals("{")) {
                    startIndex = i + 1;
                }
                if ((expression.charAt(i) + "").equals("}")) {
                    endIndex = i;
                    if (startIndex >= endIndex) {
                        throw new Exception("表达式有误，请检查");
                    }
                    String keyStr = expression.substring(startIndex, endIndex);
                    if (null != map && map.containsKey(keyStr)) {
                        expression = expression.replaceAll("{" + keyStr + "}", map.get(keyStr).toString());
                    } else {
                        throw new Exception("缺少参数");
                    }
                }
            }
        }
        if (expression.contains("{") || expression.contains("}")) {
            throw new Exception("表达式有误，请检查");
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("js");
        String str = expression;
        Object result = se.eval(str);
        return result;
    }
}