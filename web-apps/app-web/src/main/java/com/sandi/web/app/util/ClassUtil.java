/*
 * $Id: ClassUtil.java,v 1.0 2015年7月24日 上午11:18:16 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author zhangrp
 * @version $Id: ClassUtil.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午11:18:16
 */
public class ClassUtil {
    public static String[] SelfMehtods = new String[]{
            "clone", "equals", "getClass", "hashCode", "notify", "notifyAll", "toString", "wait", "finalize"
    };
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
    static String[] jartypes = {"jar", "zip", "war", "ear"};

    /**
     * 从指定路径中获取类
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static void getClassFromURL(URL url, List<String> list) throws Exception {
        BufferedInputStream bis = null;
        JarInputStream jis = null;
        InputStream fis = null;
        try {
            if (isJar(url.getFile())) {
                bis = new BufferedInputStream(url.openStream());
                jis = new JarInputStream(bis);
                JarEntry jarEntry = null;
                while ((jarEntry = jis.getNextJarEntry()) != null) {
                    if (jarEntry.isDirectory()) {
                        continue;
                    }
                    if (isJar(jarEntry.getName())) {
                        getClassFromURL(url, list);
                    } else {
                        list.add(jarEntry.getName().replaceAll("/", "."));
                    }
                }
            } else {
                String fn = URLDecoder.decode(url.getFile());
                File f = new File(fn);
                cycleFiles(f, list, f.getPath());

            }
        } catch (IOException e) {
            logger.error(url.toString(), e);
        } catch (NullPointerException e) {
            logger.error(url.toString(), e);
        } finally {
            try {
                if (null != fis) fis.close();
            } catch (Exception e) {
            }
        }
    }

    private static boolean isJar(String file) {
        if (null != file) {
            String subfix = file.substring(file.lastIndexOf(".") + 1);
            for (String type : jartypes) {
                if (type.equals(subfix))
                    return true;
            }
        }
        return false;
    }

    private static long cycleFiles(File f, List<String> list, String head) throws IOException {
        File[] fs;
        long ret = 0;
        fs = f.listFiles();
        for (File c : fs) {
            if (c.isFile()) {
                String p = c.getName();
                if (p.endsWith("class")) {
                    list.add((c.getPath().substring(head.length() + 1)).replaceAll("\\\\", "."));
                }
            } else {
                ret += cycleFiles(c, list, head);
            }
        }
        return ret;
    }

    public static String createDecliare(String className, String methodName, String parametersClass) {
        if (null != parametersClass && !"".equals(parametersClass))
            return className + "#" + methodName + " " + parametersClass.trim();
        else
            return className + "#" + methodName;
    }

    /**
     * 获取类中自己定义的public方法
     *
     * @param c
     * @return
     */
    public static List<Method> getDeclareMethod(Class<?> c) {
        Method[] ms = c.getMethods();
        List<Method> li = new ArrayList<Method>();
        for (Method m : ms) {
            if (!isInStringArray(SelfMehtods, m.getName())) {
                m.getDeclaredAnnotations();
                if (Modifier.PUBLIC == m.getModifiers() || m.getModifiers() == 1025) {
                    li.add(m);
                }
            }
        }
        if (li.size() > 0)
            return li;
        return null;
    }


    private static boolean isInStringArray(String[] ss, String s) {
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].equals(s))
                return true;
        }
        return false;
    }

    /**
     * 获取某个类的方法
     * method (java.lang.String,xxxx)
     *
     * @return
     * @throws Exception
     */
    public static List<String> getMethodDesc(Class<?> c, String likeMethodName) throws Exception {
        List<Method> ms = ClassUtil.getDeclareMethod(c);
        List<String> li = new ArrayList<String>();
        for (Method m : ms) {
            if (null == likeMethodName || m.getName().toLowerCase().indexOf(likeMethodName.toLowerCase()) >= 0)
                li.add(m.getName() + " " + getMethodDesc(m));
        }
        if (li.size() > 0)
            return li;
        return null;
    }

    /**
     * (java.lang.String,long)
     * 获取方法的描述
     *
     * @param m
     * @return
     */
    public static String getMethodDesc(Method m) {
        Class<?>[] paramTypes = m.getParameterTypes();
        StringBuffer desc = new StringBuffer();
        if (paramTypes != null && paramTypes.length > 0) {
            desc.append((char) '(');
            int n = paramTypes.length;
            for (int i = 0; i < n; i++) {
                if (i > 0)
                    desc.append(",");
                toDescriptor(desc, paramTypes[i]);
            }
            desc.append((char) ')');
        }
        return desc.toString();
    }

    private static void toDescriptor(StringBuffer desc, Class<?> type) {
        if (type.isArray()) {
            desc.append((char) '[');
            try {
                toDescriptor(desc, type.getComponentType());
            } catch (Exception e) {
                String name = type.getName();
                logger.warn(name, e);
            }
        } else if (type.isPrimitive()) {
            desc.append(type.getName());
        } else {
            desc.append(type.getName());
        }
    }


    public static String class2String(Class<?>[] cs) {
        if (null != cs) {
            StringBuffer sb = new StringBuffer();
            for (Class<?> c : cs) {
                if (sb.length() == 0) {
                    toDescriptor(sb, c);
                } else {
                    sb.append(",");
                    toDescriptor(sb, c);
                }
            }
            return sb.toString();
        }
        return "";
    }
}
