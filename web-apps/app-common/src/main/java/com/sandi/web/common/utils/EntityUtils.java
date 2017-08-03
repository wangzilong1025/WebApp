package com.sandi.web.common.utils;

import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by hyf on 2017/3/22.
 */
public class EntityUtils {

    /**
     *将map中的值放到对应实体类中，如果实体类中不存在此字段的set方法，则不赋值
     * @param m 参数
     * @param c 实体类Class
     * @return 实体类对象实例
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object MapToEntity(Map m,Class c) throws Exception {
        Object obj=c.newInstance();//实例化对象
        try {
            for(Object key : m.keySet())
            {
                if(m.get(key) != null && !"".equals(m.get(key))){
                    Method method = getSetMethod(c, key.toString());
                    Class type = method.getParameterTypes()[0];
                    if(method != null){
                        method.invoke(obj, new Object[] { ConvertUtils.convert(m.get(key),type) });
                    }
                }
            }
        }catch (Exception e){
            obj = null;
            throw e;
        }
        return obj;
    }


    /**
     * 根据字段名获取set方法
     * @param objectClass 实体类Class
     * @param fieldName 字段名
     * @return
     */
    public static Method getSetMethod(Class objectClass, String fieldName) {
        Method method = null;
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        }catch (Exception e){

        }
        return method;
    }


}
