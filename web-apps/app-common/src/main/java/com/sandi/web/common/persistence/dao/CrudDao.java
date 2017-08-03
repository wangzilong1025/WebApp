package com.sandi.web.common.persistence.dao;

import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/5/8.
 * 普通表操作DAO
 */
public interface CrudDao<T, PK> extends BaseDao {
    /**
     * 根据主键查询数据
     *
     * @param id 主键id
     */
    public T findById(@Param("id") PK id) throws Exception;

    /**
     * 根据实体字段查询数据
     */
    public List<T> findByEntity(@Param("entity") T entity) throws Exception;

    /**
     * 根据实体查询数据，支持分页查询
     *
     * @param pageSize 每页数量
     * @entity 实体类
     * @pageNo 页码
     */
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 根据实体查询数据，按指定属性进行正序排列
     *
     * @param entity   实体类
     * @param rankAttr 排序属性
     */
    public List<T> findByEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，按指定属性进行排序
     *
     * @param entity       实体类
     * @param rankAttr     排序属性
     * @param rankAttrType 排序方式 ASC DESC，从Constants中获取
     */
    public List<T> findByEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     */
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param rankAttr 排序方式
     */
    public List<T> findByEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据量
     *
     * @param entity
     */
    public int findByEntityCount(@Param("entity") T entity) throws Exception;

    /**
     * 根据实体类部分字段模糊查询数据，只对varchar类型的数据进行模糊查询
     *
     * @param entity 实体类
     */
    public List<T> findLike(@Param("entity") T entity) throws Exception;

    /**
     * 根据实体查询数据，支持分页查询
     *
     * @param pageSize 每页数量
     * @entity 实体类
     * @pageNo 页码
     */
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 根据实体查询数据，按指定属性进行正序排列
     *
     * @param entity   实体类
     * @param rankAttr 排序属性
     */
    public List<T> findLike(@Param("entity") T entity, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，按指定属性进行排序
     *
     * @param entity       实体类
     * @param rankAttr     排序属性
     * @param rankAttrType 排序方式 ASC DESC，从Constants中获取
     */
    public List<T> findLike(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     */
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param rankAttr 排序方式
     * @param rankType
     */
    public List<T> findLike(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankType") String rankType);

    /**
     * 根据实体查询数据量
     *
     * @param entity
     */
    public int findLikeCount(@Param("entity") T entity) throws Exception;

    /**
     * 查询非该实体的数据
     */
    public List<T> findNotThisEntity(@Param("entity") T entity) throws Exception;

    /**
     * 根据实体查询数据，支持分页查询
     *
     * @param pageSize 每页数量
     * @entity 实体类
     * @pageNo 页码
     */
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 根据实体查询数据，按指定属性进行正序排列
     *
     * @param entity   实体类
     * @param rankAttr 排序属性
     */
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，按指定属性进行排序
     *
     * @param entity       实体类
     * @param rankAttr     排序属性
     * @param rankAttrType 排序方式 ASC DESC，从Constants中获取
     */
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     */
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param rankAttr 排序方式
     */
    public List<T> findNotThisEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据量
     *
     * @param entity
     */
    public int findNotThisEntityCount(@Param("entity") T entity) throws Exception;

    /**
     * 查询非该实体类似的数据
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity) throws Exception;

    /**
     * 根据实体查询数据，支持分页查询
     *
     * @param pageSize 每页数量
     * @entity 实体类
     * @pageNo 页码
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 根据实体查询数据，按指定属性进行正序排列
     *
     * @param entity   实体类
     * @param rankAttr 排序属性
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，按指定属性进行排序
     *
     * @param entity       实体类
     * @param rankAttr     排序属性
     * @param rankAttrType 排序方式 ASC DESC，从Constants中获取
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr);

    /**
     * 根据实体查询数据，支持分页、排序查询
     *
     * @param entity   实体类
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param rankAttr 排序方式
     */
    public List<T> findNotLikeEntity(@Param("entity") T entity, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("rankAttr") String rankAttr, @Param("rankAttrType") String rankAttrType);

    /**
     * 根据实体查询数据量
     *
     * @param entity
     */
    public int findNotLikeEntityCount(@Param("entity") T entity) throws Exception;

    /**
     * 保存数据
     *
     * @param entity 实体类
     */
    public int save(@Param("entity") T entity) throws Exception;

    /**
     * 保存数据
     *
     * @param entitys 实体类
     */
    public int save(@Param("entity") List<T> entitys) throws Exception;

    /**
     * 保存数据
     *
     * @param entitys 实体类
     */
    public int save(@Param("entity") T[] entitys) throws Exception;

    /**
     * 根据主键修改数据
     *
     * @param entity 实体类
     */
    public int updateById(@Param("entity") T entity) throws Exception;

    /**
     * 根据主键删除数据
     *
     * @param id 主键id
     */
    public int deleteById(@Param("id") PK id) throws Exception;

    /***
     * 根据实体类删除数据
     *
     * @param entity 实体类
     */
    public int deleteByEntity(@Param("entity") T entity) throws Exception;

    /**
     * 获取数据库当前时间
     *
     * @return
     * @throws Exception
     */
    public Timestamp getSysDate() throws Exception;

    /**
     * 获取sql查询返回实体
     *
     * @return
     * @throws Exception
     */
    public List<T> findBySql(@Param("sql") String sql, @Param("param") Map params) throws Exception;

}
