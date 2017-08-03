package com.sandi.web.utils.common;



//import org.codehaus.jackson.map.ObjectMapper;

//import com.fasterxml.jackson.databind.ObjectMapper;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public static ObjectMapper mapper = new ObjectMapper();

    /***
     * 将对象序列化为JSON文本  MAP,LIST,OBJECT都适用
     *
     * @param object
     * @return
     */
    public static String object2Json(Object object) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        return mapper.writeValueAsString(object);
    }

    /**
     * 将json字符串转化成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T json2Object(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    /**
     * 将object转换为对应的对象
     * @param object
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T changeObject(Object object, Class<T> clazz) throws IOException {
        return mapper.readValue(mapper.writeValueAsString(object), clazz);
    }


    /**
     * JSON字符串转化成Map对象具体需要转
     *  具体转换类型，参考Map<String,User> result = mapper.readValue(src, new TypeReference<Map<String,User>>()
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> json2Map(String jsonStr) throws IOException {
        return mapper.readValue(jsonStr, Map.class);
    }


    /**
     * JSON字符串转化成对象List
     *
     * @param jsonStr
     * @return
     */
    public static <T> List<T> json2List(String jsonStr) throws IOException {
        return mapper.readValue(jsonStr, List.class);

    }


    public static Object getValueInMap(Map<String, Object> map, String keyStr) {
        Object obj = map.get(keyStr);
        if (obj == null) {
            obj = map.get(keyStr.toUpperCase());
        }
        return obj;
    }

    /**
     * 将对象转化成Map对象
     *
     * @param obj
     * @return
     */
    public static <T> Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (obj != null) {
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Object entity = obj.getClass().getMethod(methodName, new Class[0]).invoke(obj, new Object[0]);
                    map.put(name, entity == null ? "" : entity);
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 将对象转化成属性全部转化为大写的Map对象
     *
     * @param obj
     * @return
     */
    public static <T> Map<String, Object> objectToUpperCaseMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (obj != null) {
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Object entity = obj.getClass().getMethod(methodName, new Class[0]).invoke(obj, new Object[0]);
                    map.put(name.toUpperCase(), entity == null ? "" : entity);
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 将POJO对象装换成String可以使用注解自定义成员属性名称的大小写
     * @param bean
     * @return
     * @throws IOException
     */
    public static String beanToJsonString(Object bean) throws IOException {
        if(null ==bean)
            return "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        String reqParamStr = mapper.writeValueAsString(bean);
        return reqParamStr;
    }

    /**
     * json字符串的格式化
     *
     * @param json
     * @param fillStringUnit
     * @return
     */
    public static String formatJson(String json, String fillStringUnit) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        int fixedLenth = 0;
        ArrayList tokenList = new ArrayList();
        {
            String jsonTemp = json;
            //预读取
            while (jsonTemp.length() > 0) {
                String token = getToken(jsonTemp);
                jsonTemp = jsonTemp.substring(token.length());
                token = token.trim();
                tokenList.add(token);
            }
        }

        for (int i = 0; i < tokenList.size(); i++) {
            String token = (String)tokenList.get(i);
            int length = token.getBytes().length;
            if (length > fixedLenth && i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
                fixedLenth = length;
            }
        }

        StringBuffer buf = new StringBuffer();
        int count = 0;
        for (int i = 0; i < tokenList.size(); i++) {

            String token = (String) tokenList.get(i);

            if (token.equals(",")) {
                buf.append(token);
                doFill(buf, count, fillStringUnit);
                continue;
            }
            if (token.equals(":")) {
                buf.append("").append(token).append("");
                continue;
            }
            if (token.equals("{")) {
                String nextToken = (String)tokenList.get(i + 1);
                if (nextToken.equals("}")) {
                    i++;
                    buf.append("{ }");
                } else {
                    count++;
                    buf.append(token);
                    doFill(buf, count, fillStringUnit);
                }
                continue;
            }
            if (token.equals("}")) {
                count--;
                doFill(buf, count, fillStringUnit);
                buf.append(token);
                continue;
            }
            if (token.equals("[")) {
                String nextToken = (String)tokenList.get(i + 1);
                if (nextToken.equals("]")) {
                    i++;
                    buf.append("[ ]");
                } else {
                    count++;
                    buf.append(token);
                    doFill(buf, count, fillStringUnit);
                }
                continue;
            }
            if (token.equals("]")) {
                count--;
                doFill(buf, count, fillStringUnit);
                buf.append(token);
                continue;
            }

            buf.append(token);
            //左对齐
            if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
                int fillLength = fixedLenth - token.getBytes().length;
                if (fillLength > 0) {
                    for(int j = 0; j < fillLength; j++) {
                        buf.append(" ");
                    }
                }
            }
        }
        return buf.toString();
    }

    private static String getToken(String json) {
        StringBuffer buf = new StringBuffer();
        boolean isInYinHao = false;
        while (json.length() > 0) {
            String token = json.substring(0, 1);
            json = json.substring(1);

            if (!isInYinHao &&
                    (token.equals(":") || token.equals("{") || token.equals("}")
                            || token.equals("[") || token.equals("]")
                            || token.equals(","))) {
                if (buf.toString().trim().length() == 0) {
                    buf.append(token);
                }

                break;
            }

            if (token.equals("\\")) {
                buf.append(token);
                buf.append(json.substring(0, 1));
                json = json.substring(1);
                continue;
            }
            if (token.equals("\"")) {
                buf.append(token);
                if (isInYinHao) {
                    break;
                } else {
                    isInYinHao = true;
                    continue;
                }
            }
            buf.append(token);
        }
        return buf.toString();
    }

    private static void doFill(StringBuffer buf, int count, String fillStringUnit) {
        buf.append("\n");
        for (int i = 0; i < count; i++) {
            buf.append(fillStringUnit);
        }
    }

}