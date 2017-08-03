/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect.db;

import com.sandi.web.common.persistence.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * Sybase数据库分页方言实现。
 * 还未实现
 */
public class SybaseDialect implements Dialect {

    public boolean supportsLimit() {
        return false;
    }


    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return null;
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


    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql               实际SQL语句
     * @param offset            分页开始纪录条数
     * @param offsetPlaceholder 分页开始纪录条数－占位符号
     * @param limit             分页每页显示纪录条数
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
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
