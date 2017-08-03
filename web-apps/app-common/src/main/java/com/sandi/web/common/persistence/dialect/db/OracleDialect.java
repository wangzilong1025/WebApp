/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect.db;

import com.sandi.web.common.his.HisEntityFactory;
import com.sandi.web.common.his.entity.CfgHisTableConfigEntity;
import com.sandi.web.common.id.IdGeneratorFactory;
import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.annotation.Entity;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.dialect.Dialect;
import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.common.split.SplitTableFactory;
import com.sandi.web.common.support.CommonSqlSessionTemplate;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ReflectionsUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Oracle方言的实现
 */
public class OracleDialect implements Dialect {
    private static final Logger log = Logger.getLogger(MySQLDialect.class);
    private static Map<String, String> sqlCache = new HashMap<String, String>();
    private static final String COLUMN_SPLIT = ",";


    @Override
    public String findById(Class<?> clazz, Object param) throws Exception {
        //是否已经在缓存中
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_findById";
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" SELECT ");
            StringBuilder tableSql = new StringBuilder();
            StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
            StringBuilder columnSql = new StringBuilder();

            Field idField = SQLHelper.getIdField(clazz);
            if (idField == null) {
                throw new Exception("no found primary key with @Id");
            }

            String simpleTableName = dealQuerySql(clazz, tableSql, columnSql);

            whereSql.append(" AND " + simpleTableName + "." + SQLHelper.getFieldColumn(idField) + " = #{id}");

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + " " + simpleTableName + tableSql)
                    .append(whereSql);
            resultSql = sql.toString();
            sqlCache.put(sqlCacheKey, resultSql);
        }
        return resultSql;
    }

    @Override
    public String findLike(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_findLike";
        StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
        //是否在缓存中
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" SELECT ");
            StringBuilder tableSql = new StringBuilder();
            StringBuilder columnSql = new StringBuilder();
            String simpleTableName = dealQuerySql(clazz, tableSql, columnSql);

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + " " + simpleTableName + tableSql);
            resultSql = sql.toString();

            sqlCache.put(sqlCacheKey, resultSql);
        }

        dealWhereSql(clazz, param, whereSql, "entity", true, true);

        dealRankAndPage(clazz, param);
        return resultSql + whereSql.toString();
    }

    @Override
    public String findByEntity(Class<?> clazz, Object param) throws Exception {
        //判断参数中，是否有startIdx,size参数，如果存在的进行分页查询
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_findLike";
        StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
        //是否在缓存中
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" SELECT ");
            StringBuilder tableSql = new StringBuilder();
            StringBuilder columnSql = new StringBuilder();
            String simpleTableName = dealQuerySql(clazz, tableSql, columnSql);

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + " " + simpleTableName + tableSql);
            resultSql = sql.toString();

            sqlCache.put(sqlCacheKey, resultSql);
        }
        dealWhereSql(clazz, param, whereSql, "entity", false, true);//处理where查询条件

        dealRankAndPage(clazz, param);

        return resultSql + whereSql.toString();
    }

    @Override
    public String findNotThisEntity(Class<?> clazz, Object param) throws Exception {
        //判断参数中，是否有startIdx,size参数，如果存在的进行分页查询
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_findNotThisEntity";
        StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
        //是否在缓存中
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" SELECT ");
            StringBuilder tableSql = new StringBuilder();
            StringBuilder columnSql = new StringBuilder();
            String simpleTableName = dealQuerySql(clazz, tableSql, columnSql);

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + " " + simpleTableName + tableSql);
            resultSql = sql.toString();

            sqlCache.put(sqlCacheKey, resultSql);
        }
        dealWhereSql(clazz, param, whereSql, "entity", false, false);//处理where查询条件
        dealRankAndPage(clazz, param);

        return resultSql + whereSql.toString();
    }

    @Override
    public String findNotLikeEntity(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_findNotLike";
        StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
        //是否在缓存中
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" SELECT ");
            StringBuilder tableSql = new StringBuilder();
            StringBuilder columnSql = new StringBuilder();
            String simpleTableName = dealQuerySql(clazz, tableSql, columnSql);

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + " " + simpleTableName + tableSql);
            resultSql = sql.toString();

            sqlCache.put(sqlCacheKey, resultSql);
        }

        dealWhereSql(clazz, param, whereSql, "entity", true, false);

        dealRankAndPage(clazz, param);
        return resultSql + whereSql.toString();
    }

    @Override
    public String insert(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_insert";
        boolean isBatchSave = false;

        Object obj = param;
        Object tempBaseEntity = SQLHelper.getBaseEntity(param);
        if (tempBaseEntity != null) {
            obj = tempBaseEntity;
        }
        if (obj instanceof List || obj instanceof Array) {//如果为批量保存
            sqlCacheKey = clazz.getName() + "_insertBatch";
            isBatchSave = true;
        }

        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            StringBuilder columnSql = new StringBuilder();
            StringBuilder paramSql = new StringBuilder();
            String entityPre = "entity";
            if (isBatchSave) {
                entityPre = "item";
            }

            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(SQLHelper.P_ID)) {
                        Id pid = (Id) field.getAnnotation(SQLHelper.P_ID);
                        if (pid.isAutoKey()) {
                            continue;
                        }
                    }
                    String fieldColumn = SQLHelper.getFieldColumn(field);
                    if (StringUtils.isNotEmpty(fieldColumn)) {
                        columnSql.append(fieldColumn + COLUMN_SPLIT);
                        if (isBatchSave) {
                            paramSql.append("#{" + entityPre + "." + field.getName() + "} AS " + fieldColumn + COLUMN_SPLIT);
                        } else {
                            paramSql.append("#{" + entityPre + "." + field.getName() + "}" + COLUMN_SPLIT);
                        }

                    }
                }
                sql.append(SQLHelper.getTableName(clazz))
                        .append("(" + columnSql.substring(0, columnSql.length() - 1) + ")");
                if (isBatchSave) {
                    sql.append(CommConstants.CrudDaoSql.FOR_EACH_SAVE_FLAG)
                            .append(" SELECT ")
                            .append(paramSql.substring(0, paramSql.length() - 1))
                            .append(" FROM DUAL");
                } else {
                    sql.append(" VALUES ");
                    sql.append("(" + paramSql.substring(0, paramSql.length() - 1) + ")");
                }
                resultSql = sql.toString();

                sqlCache.put(sqlCacheKey, resultSql);
            }
        }

        //处理主键，看对象中是否已为主键赋值
        Field idField = SQLHelper.getIdField(clazz);
        if (idField != null) {
            if (obj instanceof List) {
                List baseEntities = (List) obj;
                if (baseEntities != null && baseEntities.size() > 0) {
                    for (int i = 0; i < baseEntities.size(); i++) {
                        BaseEntity baseEntity = (BaseEntity) baseEntities.get(i);
                        Object idObj = ReflectionsUtils.getFieldValue(baseEntity, idField);
                        if (StringUtils.equalsIgnoreCase("string", idField.getType().getSimpleName()) && (idObj == null || StringUtils.isEmpty(String.valueOf(idObj)))) {
                            ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newStringId());
                        } else if (idObj instanceof Number && idObj==null) {
                            ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newId());
                        }
                    }
                }
            } else if (obj instanceof Array) {
                BaseEntity[] baseEntities = (BaseEntity[]) obj;
                if (baseEntities != null && baseEntities.length > 0) {
                    for (BaseEntity baseEntity : baseEntities) {
                        Object idObj = ReflectionsUtils.getFieldValue(baseEntity, idField);
                        if (StringUtils.equalsIgnoreCase("string", idField.getType().getSimpleName()) && (idObj == null || StringUtils.isEmpty(String.valueOf(idObj)))) {
                            ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newStringId());
                        } else if (idObj instanceof Number && idObj==null) {
                            ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newId());
                        }
                    }
                }
            } else if (obj instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) obj;
                Object idObj = ReflectionsUtils.getFieldValue(baseEntity, idField);
                if (StringUtils.equalsIgnoreCase("string", idField.getType().getSimpleName()) && (idObj == null || StringUtils.isEmpty(String.valueOf(idObj)))) {
                    ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newStringId());
                } else if (idObj instanceof Number && idObj==null) {
                    ReflectionsUtils.setFieldValue(baseEntity, idField, baseEntity.newId());
                }
            }
        }
        return resultSql;
    }

    @Override
    public String updateById(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_updateById";

        //判断是否设置了向历史表中插入数据，如果设置了则会写入到历史表中
        dealHisEntity(clazz, param, "UPB");
//        if(sqlCache.containsKey(sqlCacheKey)){
//            resultSql = sqlCache.get(sqlCacheKey);
//        }else{
        StringBuilder sql = new StringBuilder(" UPDATE ");
        StringBuilder setSql = new StringBuilder(" SET ");
        StringBuilder whereSql = new StringBuilder(" WHERE ");

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            boolean hasPId = false;
            for (Field field : fields) {
                if (!field.isAnnotationPresent(SQLHelper.P_ENTITY)) {
                    if (field.isAnnotationPresent(SQLHelper.P_ID)) {
                        hasPId = true;
                        String fieldColumn = SQLHelper.getFieldColumn(field);
                        whereSql.append(fieldColumn + " = #{entity." + field.getName() + "}");
                    } else {
                        Object objValue = ReflectionsUtils.getFieldValue(((MapperMethod.ParamMap) param).get("entity"), field);
                        if (objValue != null) {
                            String fieldColumn = SQLHelper.getFieldColumn(field);
                            setSql.append(fieldColumn + " = #{entity." + field.getName() + "}" + COLUMN_SPLIT);
                        }
                    }
                }
            }
            if (!hasPId) {
                throw new Exception("no found primary key with @Id");
            }
            String tableName = SQLHelper.getTableName(clazz);
            sql.append(tableName)
                    .append(setSql.substring(0, setSql.length() - 1))
                    .append(whereSql);
            resultSql = sql.toString();

            sqlCache.put(sqlCacheKey, resultSql);
        }
//        }
        return resultSql;
    }

    @Override
    public String deleteById(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_deleteById";

        //判断是否设置了向历史表中插入数据，如果设置了则会写入到历史表中
        dealHisEntity(clazz, param, "DEL");
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            Field idField = SQLHelper.getIdField(clazz);
            if (idField == null) {
                throw new Exception("no found primary key with @Id");
            }
            //查询hisEntity表中，看是否配置了挪历史表的记录
            String simpleTableName = SQLHelper.getSimpleTableName(clazz);
            String tableName = SQLHelper.getTableName(clazz);
            CfgHisTableConfigEntity hisTableConfigEntity = HisEntityFactory.getHisTableConfigEntity(simpleTableName);
            if (hisTableConfigEntity != null) {
                if (hisTableConfigEntity.getDeleteType() == 1) {//当删除的时候更新表状态
                    String columnName = hisTableConfigEntity.getColumnName();
                    String deleteFlag = hisTableConfigEntity.getDeleteFlag();
                    resultSql = "UPDATE " + tableName + " SET " + columnName + "='" + deleteFlag + "' WHERE " + SQLHelper.getFieldColumn(idField) + "=#{id}";
                }
            }
            if (StringUtils.isEmpty(resultSql)) {
                resultSql = "DELETE FROM " + tableName + " WHERE " + SQLHelper.getFieldColumn(idField) + "=#{id}";
            }

            sqlCache.put(sqlCacheKey, resultSql);
        }
        return resultSql;
    }

    @Override
    public String deleteByEntity(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_deleteByEntity";

        dealHisEntity(clazz, param, "DEL");
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            //查询hisEntity表中，看是否配置了挪历史表的记录
            String simpleTableName = SQLHelper.getSimpleTableName(clazz);
            String tableName = SQLHelper.getTableName(clazz);
            CfgHisTableConfigEntity hisTableConfigEntity = HisEntityFactory.getHisTableConfigEntity(simpleTableName);
            StringBuilder sb = new StringBuilder();
            if (hisTableConfigEntity != null && hisTableConfigEntity.getDeleteType() == 1) {
                String columnName = hisTableConfigEntity.getColumnName();
                String deleteFlag = hisTableConfigEntity.getDeleteFlag();
                sb.append("UPDATE " + tableName + " SET " + columnName + "='" + deleteFlag + " WHERE 1=1 ");
            } else {
                sb.append("DELETE FROM " + tableName + " WHERE 1=1 ");
            }
            resultSql = sb.toString();
            sqlCache.put(sqlCacheKey, resultSql);
        }
        boolean hasFieldValue = false;
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            Object baseEntity = param;
            Object tempBaseEntity = SQLHelper.getBaseEntity(param);
            if (tempBaseEntity != null) {
                baseEntity = tempBaseEntity;
            }
            for (Field field : fields) {
                Object objValue = ReflectionsUtils.getFieldValue(baseEntity, field);
                if (objValue != null) {
                    hasFieldValue = true;
                    resultSql += " AND " + SQLHelper.getFieldColumn(field) + "= #{entity." + field.getName() + "}";
                }
            }
        }
        if (!hasFieldValue) {
            throw new Exception("delete data not have any where cond...");
        }
        return resultSql;
    }

    @Override
    //TODO 可以进一步优化，根据字段写入历史表中，避免历史表和源表的字段顺序不一致问题，待以后再做
    public String insertUpbHis(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        StringBuffer whereSql = new StringBuffer();
        String cacheKey = clazz.getName() + "_insertHis";
        if (sqlCache.containsKey(cacheKey)) {
            resultSql = sqlCache.get(cacheKey);
        } else {
            resultSql = getHisSql(clazz, param, "UPB");
            sqlCache.put(cacheKey, resultSql);
        }
        Field idField = SQLHelper.getIdField(clazz);
        if (idField != null) {
            whereSql.append(" WHERE ");
            Object baseEntity = param;
            Object tempBaseEntity = SQLHelper.getBaseEntity(param);
            if (tempBaseEntity != null) {
                baseEntity = tempBaseEntity;
                Object objValue = ReflectionsUtils.getFieldValue(baseEntity, idField);
                if (objValue != null) {
                    whereSql.append(SQLHelper.getFieldColumn(idField) + "=#{entity." + idField.getName() + "}");
                } else {
                    throw new Exception("idFiled value is null");
                }
            }else{
                whereSql.append(SQLHelper.getFieldColumn(idField) + "=#{id}");
            }
        } else {
            throw new Exception("no found id field");
        }
        return resultSql + whereSql.toString();
    }

    public String insertDelHis(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        StringBuffer whereSql = new StringBuffer();
        String cacheKey = clazz.getName() + "_insertFinish";
        if (sqlCache.containsKey(cacheKey)) {
            resultSql = sqlCache.get(cacheKey);
        } else {
            resultSql = getHisSql(clazz, param, "DEL");
            sqlCache.put(cacheKey, resultSql);
        }
        Field idField = SQLHelper.getIdField(clazz);

        Object baseEntity = param;
        Object tempBaseEntity = SQLHelper.getBaseEntity(param);
        Object idValue = null;
        if (tempBaseEntity != null) {
            idValue = ReflectionsUtils.getFieldValue(tempBaseEntity, idField);
        }

        if (idField != null && idValue != null) {
            whereSql.append(" WHERE ");
            if (tempBaseEntity != null) {
                baseEntity = tempBaseEntity;
                Object objValue = ReflectionsUtils.getFieldValue(baseEntity, idField);
                if (objValue != null) {
                    whereSql.append(SQLHelper.getFieldColumn(idField) + "=#{entity." + idField.getName() + "}");
                } else {
                    throw new Exception("idFiled value is null");
                }
            }else{
                whereSql.append(SQLHelper.getFieldColumn(idField) + "=#{id}");
            }
        } else {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                whereSql.append(" WHERE 1=1 ");
                if (tempBaseEntity != null) {
                    baseEntity = tempBaseEntity;
                }
                for (Field field : fields) {
                    Object objValue = ReflectionsUtils.getFieldValue(baseEntity, field);
                    if (objValue != null) {
                        whereSql.append(" AND " + SQLHelper.getFieldColumn(field) + "=#{entity." + field.getName() + "}");
                    }
                }
            } else {
                throw new Exception("no found field");
            }
        }
        return resultSql + whereSql.toString();
    }

    private void dealHisEntity(Class<?> clazz, Object param, String hisType) throws Exception {
        //查询hisEntity表中，看是否配置了挪历史表的记录
        String simpleTableName = SQLHelper.getSimpleTableName(clazz);
        CfgHisTableConfigEntity hisTableConfigEntity = HisEntityFactory.getHisTableConfigEntity(simpleTableName);
        if (hisTableConfigEntity != null && hisTableConfigEntity.getDeleteType() == 2) {
            if (StringUtils.equals("UPB", hisType) && StringUtils.isNotEmpty(hisTableConfigEntity.getUpbHisTableName())) {//修改
                CommonSqlSessionTemplate commonSqlSessionTemplate = SpringContextHolder.getBean(CommonSqlSessionTemplate.class);
                Map map = new HashMap();
                map.put(CommConstants.CrudDaoSql.KEY_DAO_CLASS, clazz);
                map.put(CommConstants.CrudDaoSql.KEY_PARAMS, param);
                commonSqlSessionTemplate.insert("com.sandi.web.common.persistence.dao.CommonMethodMapper." + CommConstants.CrudDaoSql.INSERT_UPB_HIS, map);
            } else if (StringUtils.equals("DEL", hisType) && StringUtils.isNotEmpty(hisTableConfigEntity.getDelHisTableName())) {//删除
                CommonSqlSessionTemplate commonSqlSessionTemplate = SpringContextHolder.getBean(CommonSqlSessionTemplate.class);
                SqlSessionFactory sqlSessionFactory = commonSqlSessionTemplate.getSqlSessionFactory(commonSqlSessionTemplate.getPlatName(clazz.getName()));
                Map map = new HashMap();
                map.put(CommConstants.CrudDaoSql.KEY_DAO_CLASS, clazz);
                map.put(CommConstants.CrudDaoSql.KEY_PARAMS, param);
                commonSqlSessionTemplate.insert("com.sandi.web.common.persistence.dao.CommonMethodMapper." + CommConstants.CrudDaoSql.INSERT_DEL_HIS, map);
            }
        }
    }

    private String getHisSql(Class<?> clazz, Object param, String hisType) throws Exception {
        StringBuilder sql = new StringBuilder();
        StringBuilder columnSql = new StringBuilder();
        String simpleTableName = SQLHelper.getSimpleTableName(clazz);
        String tableName = SQLHelper.getTableName(clazz);
        CfgHisTableConfigEntity hisTableConfigEntity = HisEntityFactory.getHisTableConfigEntity(simpleTableName);
        if (hisTableConfigEntity != null) {
            String hisTableName = "";
            if (StringUtils.equals("UPB", hisType)) {//修改
                hisTableName = hisTableConfigEntity.getUpbHisTableName();
            } else if (StringUtils.equals("DEL", hisType)) {//删除
                hisTableName = hisTableConfigEntity.getDelHisTableName();
            }
            if (StringUtils.isEmpty(hisTableName)) {
                throw new Exception("not found his table config");
            }

            sql.append(" INSERT INTO " + SplitTableFactory.getSplitTable(hisTableName, param));

            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    String fieldColumn = SQLHelper.getFieldColumn(field);
                    if (StringUtils.isNotEmpty(fieldColumn)) {
                        columnSql.append(fieldColumn + COLUMN_SPLIT);
                    }
                }
            }
            sql.append("(" + columnSql + "HIS_DATE)");
            if (StringUtils.equalsIgnoreCase("Y", hisTableConfigEntity.getHisIdFlag())) {
                String newId = IdGeneratorFactory.newStringId(hisTableName);
                sql.append(" SELECT " + newId + ",").append(columnSql).append("sysdate FROM " + SplitTableFactory.getSplitTable(tableName, param) + " a");
            } else {
                sql.append(" SELECT ").append(columnSql).append("sysdate FROM " + SplitTableFactory.getSplitTable(tableName, param) + " a");
            }
        }
        return sql.toString();
    }

    /**
     * 部分sql不能对where条件进行缓存，因为where根据传入的参数可能会动态变化
     */
    private String dealQuerySql(Class clazz, StringBuilder tableSql, StringBuilder columnSql) throws Exception {
        String simpleTableName = SQLHelper.getSimpleTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            StringBuffer childTableSql = new StringBuffer();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(SQLHelper.P_ENTITY)) {
                    Entity pEntity = (Entity) field.getAnnotation(SQLHelper.P_ENTITY);
                    if (pEntity.autoLoad()) {
                        String childSimpleTableName = dealQuerySql(pEntity.clazz(), tableSql, columnSql);
                        String attr = pEntity.attr();
                        String relAttr = pEntity.relAttr();
                        if (StringUtils.isEmpty(pEntity.attr())) {
                            Field idField = SQLHelper.getIdField(fields);
                            if (idField != null) {
                                attr = idField.getName();
                            } else {
                                throw new Exception("no found primary key with @Id");
                            }
                        }
                        String childTableName = SQLHelper.getTableName(pEntity.clazz());
                        StringBuffer tempChildSql = new StringBuffer();
                        if (pEntity.cond() != null && pEntity.cond().length > 0) {
                            if (StringUtils.isEmpty(tempChildSql)) {
                                tempChildSql.append("(SELECT * FROM " + childTableName + " WHERE 1=1 ");
                            }
                            for (String str : pEntity.cond()) {
                                if (StringUtils.contains(str, "=")) {
                                    String temp[] = StringUtils.split(str, "=");
                                    if (temp != null && temp.length == 2) {
                                        String key = temp[0];
                                        String value = temp[1];
                                        tempChildSql.append(" AND " + SQLHelper.getFieldColumn(pEntity.clazz(), key) + " = " + value);
                                    }
                                } else if (StringUtils.contains(str, "<>")) {
                                    String temp[] = StringUtils.split(str, "<>");
                                    if (temp != null && temp.length == 2) {
                                        String key = temp[0];
                                        String value = temp[1];
                                        tempChildSql.append(" AND " + SQLHelper.getFieldColumn(pEntity.clazz(), key) + " <> " + value);
                                    }
                                } else if (StringUtils.contains(str, ">")) {
                                    String temp[] = StringUtils.split(str, ">");
                                    if (temp != null && temp.length == 2) {
                                        String key = temp[0];
                                        String value = temp[1];
                                        tempChildSql.append(" AND " + SQLHelper.getFieldColumn(pEntity.clazz(), key) + " > " + value);
                                    }
                                } else if (StringUtils.contains(str, "<")) {
                                    String temp[] = StringUtils.split(str, "<");
                                    if (temp != null && temp.length == 2) {
                                        String key = temp[0];
                                        String value = temp[1];
                                        tempChildSql.append(" AND " + SQLHelper.getFieldColumn(pEntity.clazz(), key) + " < " + value);
                                    }
                                } else if (StringUtils.containsIgnoreCase(str, "like")) {
                                    String temp[] = StringUtils.split(str, "like");
                                    if (temp != null && temp.length == 2) {
                                        String key = temp[0];
                                        String value = temp[1];
                                        tempChildSql.append(" AND " + SQLHelper.getFieldColumn(pEntity.clazz(), key) + " like " + value);
                                    }
                                }
                            }
                            tempChildSql.append(") " + childSimpleTableName);
                        } else {
                            tempChildSql.append(childTableName + " " + childSimpleTableName);
                        }
                        childTableSql.append(" LEFT JOIN " + tempChildSql.toString() + " ON " + simpleTableName + "." + SQLHelper.getFieldColumn(fields, attr) + "=" + childSimpleTableName + "." + SQLHelper.getFieldColumn(pEntity.clazz(), relAttr));
                    }
                } else {
                    String fieldColumn = SQLHelper.getFieldColumn(field);
                    if (StringUtils.isNotEmpty(fieldColumn)) {
                        columnSql.append(simpleTableName + "." + fieldColumn + " " + (SQLHelper.getSimpleClassName(clazz.getSimpleName()) + "_" + field.getName()).toLowerCase() + COLUMN_SPLIT);
                    }
                }
            }
            if (childTableSql != null && childTableSql.length() > 0) {
                tableSql.insert(0, childTableSql);
            }
        }
        return simpleTableName;
    }

    /**
     * 处理where条件
     */
    private void dealWhereSql(Class clazz, Object param, StringBuilder whereSql, String entityName, boolean likeFlag, boolean likeEntity) throws Exception {
        if (param != null) {
            String simpleTableName = SQLHelper.getSimpleTableName(clazz);
            Object baseEntity = param;
            Object tempBaseEntity = SQLHelper.getBaseEntity(param);

            if (tempBaseEntity != null) {
                baseEntity = tempBaseEntity;
            }
            //处理where条件
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldColumn = SQLHelper.getFieldColumn(field);
                    if (StringUtils.isNotEmpty(fieldColumn)) {
                        if (baseEntity instanceof List) {
                            dealListFieldColumn(baseEntity, field, simpleTableName, fieldColumn, entityName, whereSql, likeFlag, likeEntity);
                        } else if (baseEntity.getClass().isArray()) {
                            dealArrayFieldColumn(baseEntity, field, simpleTableName, fieldColumn, entityName, whereSql, likeFlag, likeEntity);
                        } else {
                            Object columnObj = ReflectionsUtils.getFieldValue(baseEntity, field);
                            if (columnObj != null) {
                                dealFiledColumn(baseEntity, columnObj, simpleTableName, field, fieldColumn, entityName, whereSql, likeFlag, likeEntity);
                            }
                        }
                    } else if (field.isAnnotationPresent(SQLHelper.P_ENTITY)) {
                        Entity entity = (Entity) field.getAnnotation(SQLHelper.P_ENTITY);
                        //如果为自动加载
                        if (entity.autoLoad()) {
                            Object columnObj = ReflectionsUtils.getFieldValue(baseEntity, field);
                            if (columnObj != null) {
                                dealWhereSql(entity.clazz(), columnObj, whereSql, entityName + "." + field.getName(), likeFlag, likeEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    private void dealFiledColumn(Object baseEntity, Object columnObj, String simpleTableName, Field field, String fieldColumn, String entityName, StringBuilder whereSql, boolean likeFlag, boolean likeEntity) {
        String fieldName = field.getName();
        if (columnObj instanceof String && StringUtils.isNotEmpty(String.valueOf(columnObj))) {
            if (likeEntity) {
                if (likeFlag) {
                    ReflectionsUtils.setFieldValue(baseEntity, field, "%" + columnObj + "%");
                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " like #{" + entityName + "." + fieldName + "}");
                } else {
                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " = #{" + entityName + "." + fieldName + "}");
                }
            } else {
                if (likeFlag) {
                    ReflectionsUtils.setFieldValue(baseEntity, field, "%" + columnObj + "%");
                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " not like #{" + entityName + "." + fieldName + "}");
                } else {
                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "." + fieldName + "}");
                }
            }
        } else if (columnObj instanceof Number && columnObj!=null) {
            if (likeEntity) {
                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + "= #{" + entityName + "." + fieldName + "}");
            } else {
                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "." + fieldName + "}");
            }
        } else if (columnObj instanceof Date) {
            if (likeEntity) {
                if (likeFlag) {
                    if (fieldName.toLowerCase().contains("valid") || fieldName.toLowerCase().contains("effective")) {
                        whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " <= #{" + entityName + "." + fieldName + "}");
                    } else if (fieldName.toLowerCase().contains("expire")) {
                        whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " >= #{" + entityName + "." + fieldName + "}");
                    } else {
                        whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " = #{" + entityName + "." + fieldName + "}");
                    }
                } else {
                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " = #{" + entityName + "." + fieldName + "}");
                }
            } else {
                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "." + fieldName + "}");
            }
        }
    }

    /**
     * 处理列
     *
     * @param baseEntity
     * @param field
     * @param simpleTableName
     * @param fieldColumn
     * @param entityName
     * @param whereSql
     * @param likeFlag        是否为Like
     * @param likeEntity      是否和实体类似
     */
    private void dealListFieldColumn(Object baseEntity, Field field, String simpleTableName, String fieldColumn, String entityName, StringBuilder whereSql, boolean likeFlag, boolean likeEntity) {
        List lists = (List) baseEntity;
        if (lists != null && lists.size() > 0) {
            String fieldName = field.getName();
            if (lists.size() == 1) {
                Object columnObj = ReflectionsUtils.getFieldValue(lists.get(0), field);
                if (columnObj != null) {
                    if (columnObj instanceof String && StringUtils.isNotEmpty(String.valueOf(columnObj))) {
                        if (likeEntity) {
                            if (likeFlag) {
                                ReflectionsUtils.setFieldValue(lists.get(0), field, "%" + columnObj + "%");
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " like #{" + entityName + "[0]." + fieldName + "}");
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " = #{" + entityName + "[0]." + fieldName + "}");
                            }
                        } else {
                            if (likeFlag) {
                                ReflectionsUtils.setFieldValue(lists.get(0), field, "%" + columnObj + "%");
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " not like #{" + entityName + "[0]." + fieldName + "}");
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "[0]." + fieldName + "}");
                            }
                        }
                    } else if (columnObj instanceof Number && columnObj!=null) {
                        if (likeEntity) {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + "= #{" + entityName + "[0]." + fieldName + "}");
                        } else {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + "!= #{" + entityName + "[0]." + fieldName + "}");
                        }
                    } else if (columnObj instanceof Date) {
                        if (likeEntity) {
                            if (likeFlag) {
                                if (fieldName.toLowerCase().contains("valid") || fieldName.toLowerCase().contains("effective")) {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " <=  #{" + entityName + "[0]." + fieldName + "}");
                                } else if (fieldName.toLowerCase().contains("expire")) {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " >=  #{" + entityName + "[0]." + fieldName + "}");
                                } else {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " =  #{" + entityName + "[0]." + fieldName + "}");
                                }
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " =  #{" + entityName + "[0]." + fieldName + "}");
                            }
                        } else {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "[0]." + fieldName + "}");
                        }
                    }
                }
            } else {
                StringBuilder listWhereSql = new StringBuilder();
                if (likeEntity) {
                    for (int i = 0; i < lists.size(); i++) {
                        Object columnObj = ReflectionsUtils.getFieldValue(lists.get(i), field);
                        if (columnObj != null) {
                            if ((columnObj instanceof String && StringUtils.isEmpty(columnObj.toString()))) {
                                continue;
                            }
                            if (listWhereSql.length() <= 0) {
                                listWhereSql.append(" AND " + simpleTableName + "." + fieldColumn + " IN (");
                            } else {
                                listWhereSql.append(",");
                            }
                            listWhereSql.append("#{" + entityName + "[" + i + "]." + fieldName + "}");
                        }
                    }
                    if (StringUtils.isNotEmpty(listWhereSql)) {
                        listWhereSql.append(")");
                        whereSql.append(listWhereSql);
                    }
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        Object columnObj = ReflectionsUtils.getFieldValue(lists.get(i), field);
                        if (columnObj != null) {
                            if ((columnObj instanceof String && StringUtils.isEmpty(columnObj.toString()))) {
                                continue;
                            }
                            if (listWhereSql.length() <= 0) {
                                listWhereSql.append(" AND " + simpleTableName + "." + fieldColumn + " NOT IN (");
                            } else {
                                listWhereSql.append(",");
                            }
                            listWhereSql.append("#{" + entityName + "[" + i + "]." + fieldName + "}");
                        }
                    }
                    if (StringUtils.isNotEmpty(listWhereSql)) {
                        listWhereSql.append(")");
                        whereSql.append(listWhereSql);
                    }
                }
            }
        }
    }

    /**
     * 处理列
     */
    private void dealArrayFieldColumn(Object baseEntity, Field field, String simpleTableName, String fieldColumn, String entityName, StringBuilder whereSql, boolean likeFlag, boolean likeEntity) {
        Object[] objects = (Object[]) baseEntity;
        if (objects != null && objects.length > 0) {
            String fieldName = field.getName();
            if (objects.length == 1) {
                Object columnObj = ReflectionsUtils.getFieldValue(objects[0], field);
                if (columnObj != null) {
                    if (columnObj instanceof String && StringUtils.isNotEmpty(String.valueOf(columnObj))) {
                        if (likeEntity) {
                            if (likeFlag) {
                                ReflectionsUtils.setFieldValue(objects[0], field, "%" + columnObj + "%");
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " like #{" + entityName + "[0]." + fieldName + "}");
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " = #{" + entityName + "[0]." + fieldName + "}");
                            }
                        } else {
                            if (likeFlag) {
                                ReflectionsUtils.setFieldValue(objects[0], field, "%" + columnObj + "%");
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " not like #{" + entityName + "[0]." + fieldName + "}");
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " != #{" + entityName + "[0]." + fieldName + "}");
                            }
                        }
                    } else if (columnObj instanceof Number && columnObj!=null) {
                        if (likeEntity) {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + "= #{" + entityName + "[0]." + fieldName + "}");
                        } else {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + "!= #{" + entityName + "[0]." + fieldName + "}");
                        }
                    } else if (columnObj instanceof Date) {
                        if (likeEntity) {
                            if (likeFlag) {
                                if (fieldName.toLowerCase().contains("valid") || fieldName.toLowerCase().contains("effective")) {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " <=  #{" + entityName + "[0]." + fieldName + "}");
                                } else if (fieldName.toLowerCase().contains("expire")) {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " >=  #{" + entityName + "[0]." + fieldName + "}");
                                } else {
                                    whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " =  #{" + entityName + "[0]." + fieldName + "}");
                                }
                            } else {
                                whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " =  #{" + entityName + "[0]." + fieldName + "}");
                            }
                        } else {
                            whereSql.append(" AND " + simpleTableName + "." + fieldColumn + " !=  #{" + entityName + "[0]." + fieldName + "}");
                        }
                    }
                }
            } else {
                StringBuilder listWhereSql = new StringBuilder();
                if (likeEntity) {
                    for (int i = 0; i < objects.length; i++) {
                        Object columnObj = ReflectionsUtils.getFieldValue(objects[i], field);
                        if (columnObj != null) {
                            if ((columnObj instanceof String && StringUtils.isEmpty(columnObj.toString()))) {
                                continue;
                            }
                            if (listWhereSql.length() <= 0) {
                                listWhereSql.append(" AND " + simpleTableName + "." + fieldColumn + " IN (");
                            } else {
                                listWhereSql.append(",");
                            }
                            listWhereSql.append("#{" + entityName + "[" + i + "]." + fieldName + "}");
                        }
                    }
                    if (StringUtils.isNotEmpty(listWhereSql)) {
                        listWhereSql.append(")");
                        whereSql.append(listWhereSql);
                    }
                } else {
                    for (int i = 0; i < objects.length; i++) {
                        Object columnObj = ReflectionsUtils.getFieldValue(objects[i], field);
                        if (columnObj != null) {
                            if ((columnObj instanceof String && StringUtils.isEmpty(columnObj.toString()))) {
                                continue;
                            }
                            if (listWhereSql.length() <= 0) {
                                listWhereSql.append(" AND " + simpleTableName + "." + fieldColumn + " NOT IN (");
                            } else {
                                listWhereSql.append(",");
                            }
                            listWhereSql.append("#{" + entityName + "[" + i + "]." + fieldName + "}");
                        }
                    }
                    if (StringUtils.isNotEmpty(listWhereSql)) {
                        listWhereSql.append(")");
                        whereSql.append(listWhereSql);
                    }
                }
            }
        }
    }

    /**
     * 进行分页、排序处理
     */
    private void dealRankAndPage(Class<?> clazz, Object param) throws Exception {
        if (param != null && param instanceof Map) {
            Map map = (Map) param;
            int pageNo = -1;
            int pageSize = -1;
            String rankAttr = "";
            String rankAttrType = "";
            if (map.containsKey("pageNo") && map.get("pageNo") != null) {
                pageNo = Integer.valueOf(String.valueOf(map.get("pageNo")));
            }
            if (map.containsKey("pageSize") && map.get("pageSize") != null) {
                pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
            }
            if (map.containsKey("rankAttr") && map.get("rankAttr") != null) {
                rankAttr = String.valueOf(map.get("rankAttr"));
            }
            if (map.containsKey("rankAttrType") && map.get("rankAttrType") != null) {
                rankAttrType = String.valueOf(map.get("rankAttrType"));
            }
            if (pageNo > 0 && pageSize > 0) {
                Page page = new Page(pageNo, pageSize);
                BaseEntity baseEntity = (BaseEntity) SQLHelper.getBaseEntity(param);
                baseEntity.setPage(page);
            }
            if (StringUtils.isNotEmpty(rankAttr)) {
                Rank rank = new Rank(rankAttr, rankAttrType);
                Rank[] ranks = new Rank[1];
                ranks[0] = rank;
                BaseEntity baseEntity = (BaseEntity) SQLHelper.getBaseEntity(param);
                baseEntity.setRanks(ranks);
            }
        }
    }

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql    实际SQL语句
     * @param offset 分页开始纪录条数
     * @param offset 分页开始纪录条数－占位符号
     * @param limit  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuilder pagingSelect = new StringBuilder();
        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(sql);
        int endString = offset + limit;
        pagingSelect.append(" ) row_) where rownum_ <= " + endString + " and rownum_ > ").append(offset);

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
    }

    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String findLikeCount(Class<?> clazz, Object param) throws Exception {
        return "SELECT COUNT(1) FROM (" + findLike(clazz, param) + ") count_tb";
    }

    @Override
    public String findByEntityCount(Class<?> clazz, Object param) throws Exception {
        return "SELECT COUNT(1) FROM (" + findByEntity(clazz, param) + ") count_tb";
    }

    @Override
    public String findNotThisEntityCount(Class<?> clazz, Object param) throws Exception {
        return "SELECT COUNT(1) FROM (" + findNotThisEntity(clazz, param) + ") count_tb";
    }

    @Override
    public String findNotLikeEntityCount(Class<?> clazz, Object param) throws Exception {
        return "SELECT COUNT(1) FROM (" + findNotLikeEntity(clazz, param) + ") count_tb";
    }
}
