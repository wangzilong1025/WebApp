/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect.db;

import com.sandi.web.common.persistence.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * A dialect compatible with the H2 database.
 */
public class H2Dialect implements Dialect {

    public boolean supportsLimit() {
        return false;
    }
    private String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return sql + ((offset > 0) ? " limit " + limitPlaceholder + " offset "
                + offsetPlaceholder : " limit " + limitPlaceholder);
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
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