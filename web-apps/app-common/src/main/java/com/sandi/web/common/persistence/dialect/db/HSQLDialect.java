/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect.db;

import com.sandi.web.common.persistence.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Dialect for HSQLDB
 */
public class HSQLDialect implements Dialect {
    @Override
    public boolean supportsLimit() {
        return false;
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset),
                Integer.toString(limit));
    }

    @Override
    public String findById(Class<?> clazz, Object param) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String findLike(Class<?> clazz, Object param) throws Exception {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String findByEntity(Class<?> clazz, Object param) throws Exception {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String findNotThisEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String findNotLikeEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String insert(Class<?> clazz, Object param) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String updateById(Class<?> clazz, Object param) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String deleteById(Class<?> clazz, Object param) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    @Override
    public String deleteByEntity(Class<?> clazz, Object param) throws Exception {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
        boolean hasOffset = offset > 0;
        return
                new StringBuffer(sql.length() + 10)
                        .append(sql)
                        .insert(sql.toLowerCase().indexOf("select") + 6, hasOffset ? " limit " + offsetPlaceholder + " " + limitPlaceholder : " top " + limitPlaceholder)
                        .toString();
    }
    @Override
    public String insertUpbHis(Class<?> clazz,Object param) throws Exception{
        throw new UnsupportedOperationException("paged queries not supported");
    }
    @Override
    public String findLikeCount(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String findByEntityCount(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String findNotThisEntityCount(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String findNotLikeEntityCount(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    public String insertDelHis(Class<?> clazz,Object param) throws Exception{
        return null;
    }
}
