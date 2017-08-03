/**
 * $Id: ContextLoaderListener.java,v 1.0 2015/11/26 14:02 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.listener;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import javax.servlet.ServletContextEvent;
import java.util.Properties;

/**
 * @author zhangrp
 * @version $Id: ContextLoaderListener.java,v 1.1 2015/11/26 14:02 zhangrp Exp $
 * Created on 2015/11/26 14:02
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            Resource resource = new ClassPathResource("/sysconfig.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            String configPath = props.getProperty("config.path", "/home/newesop/esop/config");
            System.setProperty("config.path", configPath);
            PropertyConfigurator.configure(configPath + "/log4j.properties");
        } catch (Exception e) {
            logger.error("load log4j config file error:", e);
        }
        super.contextInitialized(event);
    }

}
