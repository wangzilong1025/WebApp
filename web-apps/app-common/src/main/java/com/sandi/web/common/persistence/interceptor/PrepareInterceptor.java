package com.sandi.web.common.persistence.interceptor;

import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.common.split.SplitTableFactory;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ReflectionsUtils;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by dizl on 2015/4/13.
 * 利用mybatis的拦截器进行分表
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
@Component
public class PrepareInterceptor extends BaseInterceptor {
    private static Logger log = Logger.getLogger(PrepareInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("SplitTableInterceptor intercept... ...");

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();
        Object params = statementHandler.getBoundSql().getParameterObject();

        sql = convert(sql, params);
        ReflectionsUtils.setFieldValue(statementHandler.getBoundSql(), "sql", sql);
        log.info("splitTable转化后的SQL语句：" + sql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        super.initProperties(properties);
    }

    private String convert(String sql, Object params) throws Exception {

        String resultSql = sql;
        if (resultSql.trim().toUpperCase().startsWith("SELECT")) {
            String str = StringUtils.replace(resultSql, " ", "");
            if (!StringUtils.startsWithIgnoreCase(str, "SELECTCOUNT")) {
                BaseEntity entity = (BaseEntity) SQLHelper.getBaseEntity(params);
                Page page = (Page) SQLHelper.getPage(params);
                if (entity != null || page != null) {
                    if (entity != null) {
                        resultSql = pageConvert(orderConvert(resultSql, entity), entity, null);
                    } else if (page != null) {
                        resultSql = pageConvert(resultSql, entity, page);
                    }
                }
            }
        }
        return splitTableConvert(resultSql, params);
    }

    private String orderConvert(String sql, BaseEntity entity) {
        if (entity.getRanks() != null && entity.getRanks().length > 0) {//进行排序
            StringBuffer sb = new StringBuffer();
            String simpleTableName = SQLHelper.getSimpleTableName(entity.getClass());
            for (int i = 0; i < entity.getRanks().length; i++) {
                String filedColumn = SQLHelper.getFieldColumn(entity.getClass(), entity.getRanks()[i].getAttrName());
                if (StringUtils.isNotEmpty(filedColumn)) {
                    if (sb.length() <= 0) {
                        sb.append(" ORDER BY ");
                    } else {
                        sb.append(",");
                    }
                    if (sql.toUpperCase().contains(" " + simpleTableName + "_" + filedColumn + " ")) {
                        sb.append(simpleTableName + "_" + filedColumn + " " + entity.getRanks()[i].getRankType());
                    } else {
                        sb.append(filedColumn + " " + entity.getRanks()[i].getRankType());
                    }
                }
            }
            return sql + sb.toString();
        }
        return sql;
    }

    private String pageConvert(String sql, BaseEntity entity, Page page) throws Exception {
        if (entity != null) {
            if (entity.getPage() != null && entity.getPage().getPageSize() > 0) {//分页查询
                return SQLHelper.generatePageSql(sql, entity.getPage(), DIALECT);
            }
        } else if (page != null && page.getPageSize() > 0) {
            return SQLHelper.generatePageSql(sql, page, DIALECT);
        }
        return sql;
    }

    private String splitTableConvert(String sql, Object params) throws Exception {
        String resultSql = sql;
        for (; resultSql.contains(CommConstants.SplitTable.splitTableStartFlag) && resultSql.contains(CommConstants.SplitTable.splitTableEndFlag); ) {
            int startIdx = resultSql.indexOf(CommConstants.SplitTable.splitTableStartFlag, 0) + CommConstants.SplitTable.splitTableStartFlag.length();
            int endIdx = resultSql.indexOf(CommConstants.SplitTable.splitTableEndFlag, startIdx);
            String tableName = resultSql.substring(startIdx, endIdx);
            String splitTableName = SplitTableFactory.getSplitTable(tableName, params);
            resultSql = StringUtils.replace(resultSql, CommConstants.SplitTable.splitTableStartFlag + tableName + CommConstants.SplitTable.splitTableEndFlag, splitTableName);
        }
        return resultSql;
    }
}