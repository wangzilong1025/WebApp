/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 类似hibernate的Dialect
 */
public interface Dialect {

    /**
     * 数据库本身是否支持分页当前的分页查询方式
     * 如果数据库不支持的话，则不进行数据库分页
     *
     * @return true：支持当前的分页查询方式
     */
    public boolean supportsLimit();

    /**
     * 将sql转换为分页SQL，分别调用分页sql
     *
     * @param sql    SQL语句
     * @param offset 开始条数
     * @param limit  每页显示多少纪录条数
     * @return 分页查询的sql
     */
    public String getLimitString(String sql, int offset, int limit) throws Exception;

    /**
     * 根据主键查询数据
     *
     * @param clazz
     * @param param
     */
    public String findById(Class<?> clazz, Object param) throws Exception;

    /**
     * 模糊查询
     *
     * @param clazz
     * @param param
     */
    public String findLike(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询数量
     */
    public String findLikeCount(Class<?> clazz, Object param) throws Exception;

    /**
     * 根据实体类进行查询
     *
     * @param clazz
     * @param param
     */

    public String findByEntity(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询数量
     */
    public String findByEntityCount(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询不是该实体的数据
     */
    public String findNotThisEntity(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询数量
     */
    public String findNotThisEntityCount(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询与该实体不类似的数据
     */
    public String findNotLikeEntity(Class<?> clazz, Object param) throws Exception;

    /**
     * 查询数量
     */
    public String findNotLikeEntityCount(Class<?> clazz, Object param) throws Exception;

    /**
     * 新增数据
     *
     * @param clazz
     * @param param
     */
    public String insert(Class<?> clazz, Object param) throws Exception;

    /**
     * 根据主键更新数据
     *
     * @param clazz
     * @param param
     */
    public String updateById(Class<?> clazz, Object param) throws Exception;

    /**
     * 根据主键删除数据
     *
     * @param clazz
     * @param param
     */
    public String deleteById(Class<?> clazz, Object param) throws Exception;

    /**
     * 根据实体类删除数据
     *
     * @param clazz
     * @param param
     */
    public String deleteByEntity(Class<?> clazz, Object param) throws Exception;

    /**
     * 处理历史表数据
     */
    public String insertUpbHis(Class<?> clazz, Object param) throws Exception;

    /**
     * 将数据写入到竣工表中
     */
    public String insertDelHis(Class<?> clazz, Object param) throws Exception;

}
