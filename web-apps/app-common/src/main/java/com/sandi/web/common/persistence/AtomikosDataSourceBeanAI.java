/**
 * $Id: AtomikosDataSourceBeanAI.java,v 1.0 2016/8/24 9:39 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.persistence;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.sandi.web.utils.security.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

/**
 * @author zhangrp
 * @version $Id: AtomikosDataSourceBeanAI.java,v 1.1 2016/8/24 9:39 zhangrp Exp $
 *          Created on 2016/8/24 9:39
 */
public class AtomikosDataSourceBeanAI extends AtomikosDataSourceBean {

    protected static final Logger logger = LoggerFactory.getLogger(AtomikosDataSourceBeanAI.class);

    public void setXaProperties(Properties xaProperties) {
        String password = xaProperties.get("password") == null ? null : xaProperties.get("password").toString();
        try {
            if (password != null) {
                password = K.k_s(password);
            }
            xaProperties.setProperty("password", password);
        } catch (Exception e) {
            logger.error("解压密码出错！", e);
        }
        super.setXaProperties(xaProperties);
    }
}
