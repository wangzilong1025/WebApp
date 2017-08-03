/*
 * $Id: ResourceCollection.java,v 1.0 2015年7月24日 上午11:15:50 zhangrp Exp $
 *
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.util;

import com.sandi.web.utils.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrp
 * @version $Id: ResourceCollection.java v 1.0 Exp $
 *          Created on 2015年7月24日 上午11:15:50
 */
public class ResourceCollection {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCollection.class);

    private static List<String> classList = new ArrayList<String>();

    static void loaderClass() throws Exception {
        //从ClassLoader中查找
        if (classList.size() == 0) {

            String fsvPath = Global.getConfig("FVSPath");
            if (fsvPath == null) {
                fsvPath = ".*(jar)$";
            }

            URLClassLoader loader = (URLClassLoader) ResourceCollection.class.getClassLoader();
            URL[] us = loader.getURLs();
            for (URL u : us) {
                if (!u.getPath().endsWith(".jar") || u.getPath().matches(fsvPath)) {
                    logger.debug("" + u);
                    ClassUtil.getClassFromURL(u, classList);
                }
            }
        }
    }

    /**
     * 从资源池中，根据类名称，方法名称查找。
     *
     * @param className
     * @return String:  className
     * @throws Exception
     */
    public static String[] search(String className) throws Exception {
        loaderClass();
        List<String> ret = new ArrayList<String>();
        for (String s : classList) {
            if (s.indexOf(className) >= 0) {
                ret.add(s);
            }
        }
        if (ret.size() > 0) {
            return ret.toArray(new String[0]);
        }
        return null;
    }


}
