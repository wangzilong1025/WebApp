/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.interceptor;

import com.sandi.web.common.persistence.dialect.Dialect;
import com.sandi.web.common.persistence.dialect.db.*;
import com.sandi.web.utils.config.Global;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Properties;

/**
 * Mybatis拦截器基类
 *
 * @author poplar.yfyang /
 * @version 2013-8-28
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {
    private static Logger log = Logger.getLogger(BaseInterceptor.class);
    private static final long serialVersionUID = 1L;
    protected Dialect DIALECT;

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式
     * <code>dialectClass</code>,自定义方言类。可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlPattern</code> 需要拦截的SQL ID
     *
     * @param p 属性
     */
    protected void initProperties(Properties p) {
        Dialect dialect = null;
        String dbType = Global.getConfig("jdbc.type");
        if ("db2".equals(dbType)) {
            dialect = new DB2Dialect();
        } else if ("derby".equals(dbType)) {
            dialect = new DerbyDialect();
        } else if ("h2".equals(dbType)) {
            dialect = new H2Dialect();
        } else if ("hsql".equals(dbType)) {
            dialect = new HSQLDialect();
        } else if ("mysql".equals(dbType)) {
            dialect = new MySQLDialect();
        } else if ("oracle".equals(dbType)) {
            dialect = new OracleDialect();
        } else if ("postgre".equals(dbType)) {
            dialect = new PostgreSQLDialect();
        } else if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
            dialect = new SQLServer2005Dialect();
        } else if ("sybase".equals(dbType)) {
            dialect = new SybaseDialect();
        }
        if (dialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        DIALECT = dialect;
    }
}
