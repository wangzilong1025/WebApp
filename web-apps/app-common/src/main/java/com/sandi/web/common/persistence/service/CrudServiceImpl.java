/**
 * $Id: CrudServiceImpl.java,v 1.0 2016/8/30 12:52 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.persistence.service;

import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author lijie
 * @version $Id: CrudServiceImpl.java,v 1.1 2016/8/30 12:52 lijie Exp $
 * Created on 2016/8/30 12:52
 */
public abstract class CrudServiceImpl<T, PK> implements CrudService<T, PK> {
    public abstract CrudDao getDao();

    @Override
    public T findById(@Param("id") PK id) throws Exception {
        return (T) getDao().findById(id);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity) throws Exception {
        return (List<T>) getDao().findByEntity(entity);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
        return (List<T>) getDao().findByEntity(entity, pageNo, pageSize);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr) {
        return (List<T>) getDao().findByEntity(entity, rankAttr);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return (List<T>) getDao().findByEntity(entity, rankAttr, rankAttrType);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr) {
        return (List<T>) getDao().findByEntity(entity, pageNo, pageSize, rankAttr);
    }

    @Override
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return (List<T>) getDao().findByEntity(entity, pageNo, pageSize, rankAttr, rankAttrType);
    }

    @Override
    public int findByEntityCount(@Param("entity") T entity) throws Exception {
        return getDao().findByEntityCount(entity);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity) throws Exception {
        return getDao().findLike(entity);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
        return getDao().findLike(entity,pageNo, pageSize);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity, @Param("rankAttr") String rankAttr) {
        return getDao().findLike(entity, rankAttr);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return getDao().findLike(entity, rankAttr, rankAttrType);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr) {
        return getDao().findLike(entity, pageNo, pageSize, rankAttr);
    }

    @Override
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankType") String rankType) {
        return getDao().findLike(entity, pageNo, pageSize, rankAttr, rankType);
    }

    @Override
    public int findLikeCount(@Param("entity") T entity) throws Exception {
        return getDao().findLikeCount(entity);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity) throws Exception {
        return getDao().findNotThisEntity(entity);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
        return getDao().findNotThisEntity(entity, pageNo, pageSize);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr) {
        return getDao().findNotThisEntity(entity, rankAttr);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return getDao().findNotThisEntity(entity, rankAttr, rankAttrType);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr) {
        return getDao().findNotThisEntity(entity, pageNo, pageSize, rankAttr);
    }

    @Override
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return getDao().findNotThisEntity(entity, pageNo, pageSize, rankAttr, rankAttrType);
    }

    @Override
    public int findNotThisEntityCount(@Param("entity") T entity) throws Exception {
        return getDao().findNotThisEntityCount(entity);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity) throws Exception {
        return getDao().findNotLikeEntity(entity);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
        return getDao().findNotLikeEntity(entity, pageNo, pageSize);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr) {
        return getDao().findNotLikeEntity(entity, rankAttr);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return getDao().findNotLikeEntity(entity, rankAttr, rankAttrType);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr) {
        return getDao().findNotLikeEntity(entity,pageNo, pageSize, rankAttr);
    }

    @Override
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType) {
        return getDao().findNotLikeEntity(entity,pageNo, pageSize, rankAttr, rankAttrType);
    }

    @Override
    public int findNotLikeEntityCount(@Param("entity") T entity) throws Exception {
        return getDao().findNotLikeEntityCount(entity);
    }

    @Override
    public int save(@Param("entity") T entity) throws Exception {
        return getDao().save(entity);
    }

    @Override
    public int save(@Param("entity") List<T> entitys) throws Exception {
        return getDao().save(entitys);
    }

    @Override
    public int save(@Param("entity") T[] entitys) throws Exception {
        return getDao().save(entitys);
    }

    @Override
    public int updateById(@Param("entity") T entity) throws Exception {
        return getDao().updateById(entity);
    }

    @Override
    public int deleteById(@Param("id") PK id) throws Exception {
        return getDao().deleteById(id);
    }

    @Override
    public int deleteByEntity(@Param("entity") T entity) throws Exception {
        return getDao().deleteByEntity(entity);
    }

    @Override
    public Timestamp getSysDate() throws Exception {
        return getDao().getSysDate();
    }

    @Override
    public List<T> findBySql(@Param("sql") String sql, @Param("param") Map params) throws Exception {
        return getDao().findBySql(sql, params);
    }
}
