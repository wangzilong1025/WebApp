/**
 * $Id: DubboManage.java,v 1.0 2015/7/16 14:30 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangrp
 * @version $Id: DubboManage.java,v 1.1 2015/7/16 14:30 zhangrp Exp $
 *          Created on 2015/7/16 14:30
 */
public class DubboManage {

    protected static final Logger logger = LoggerFactory.getLogger(DubboManage.class);

    //当前应用配置
    private static ApplicationConfig _APPLICATION = null;
    //连接注册中心配置
    private static RegistryConfig _REGISTRY = null;
    //远程服务
    private static Map<String, ReferenceConfig> _REFERENCE_CONFIG = new HashMap<String, ReferenceConfig>();

    private static synchronized void regist() {
        try {
            if (_APPLICATION == null || _APPLICATION == null) {
                Resource resource = new FileSystemResource(System.getProperty("config.path") + "/application.properties");
                Properties props = PropertiesLoaderUtils.loadProperties(resource);
                String application_name = props.getProperty("dubbo.application.name");
                String address = props.getProperty("dubbo.registry.address");
                String timeout = props.getProperty("dubbo.registry.timeout");

                _APPLICATION = new ApplicationConfig();
                _APPLICATION.setName(application_name);

                _REGISTRY = new RegistryConfig();
                _REGISTRY.setAddress(address);
                _REGISTRY.setTimeout(Integer.valueOf(timeout));
            }
        } catch (Exception e) {
            logger.error("", e);
            _APPLICATION = null;
            _REGISTRY = null;
        }
    }

    public static Object getServer(CfgSrvBase cfgOsdiSrvBase) throws Exception {
        if (_REFERENCE_CONFIG.get(cfgOsdiSrvBase.getSrvPackage()) != null) {
            Object object = _REFERENCE_CONFIG.get(cfgOsdiSrvBase.getSrvPackage()).get();
            if (object != null) {
                return object;
            } else {
                _REFERENCE_CONFIG.remove(cfgOsdiSrvBase.getSrvPackage());
            }
        }

        synchronized (_REFERENCE_CONFIG) {

            if (_APPLICATION == null || _REGISTRY == null) {
                regist();
            }
            if (_APPLICATION == null || _REGISTRY == null) {
                throw new Exception("无法注册到服务中心！");
            }

            //此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
            ReferenceConfig reference = new ReferenceConfig();
            reference.setApplication(_APPLICATION);
            reference.setRegistry(_REGISTRY);// 多个注册中心可以用setRegistries()
            reference.setInterface(Class.forName(cfgOsdiSrvBase.getSrvPackage()));
            reference.setRetries(0);
            reference.setTimeout(60000);
            _REFERENCE_CONFIG.put(cfgOsdiSrvBase.getSrvPackage(), reference);

            return reference.get();
        }
    }
}
