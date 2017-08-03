/**
 * Copyright &copy; 2012-2014 <a href="https://github.com//jeesite">JeeSite</a> All rights reserved.
 */
package com.sandi.web.common.persistence.dialect.db;

import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.annotation.Entity;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.dialect.Dialect;
import com.sandi.web.utils.common.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * DB2的分页数据库方言实现
 */
public class DB2Dialect implements Dialect {
    private static Map<String, String> sqlCache = new HashMap<String, String>();
    private static final String COLUMN_SPLIT = ",";

    @Override
    public boolean supportsLimit() {
        return true;
    }

    private static String getRowNumber(String sql) {
        StringBuilder rownumber = new StringBuilder(50)
                .append("rownumber() over(");

        int orderByIndex = sql.toLowerCase().indexOf("order by");

        if (orderByIndex > 0 && !hasDistinct(sql)) {
            rownumber.append(sql.substring(orderByIndex));
        }

        rownumber.append(") as rownumber_,");

        return rownumber.toString();
    }

    private static boolean hasDistinct(String sql) {
        return sql.toLowerCase().contains("select distinct");
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

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

            String simpleQuery = dealQuerySql(clazz, tableSql, columnSql);

            whereSql.append(" AND " + simpleQuery + "." + SQLHelper.getFieldColumn(idField) + " = #{id}");

            sql.append(columnSql.substring(0, columnSql.length() - 1))
                    .append(" FROM " + SQLHelper.getTableName(clazz) + tableSql)
                    .append(whereSql);
            resultSql = sql.toString();
            sqlCache.put(sqlCacheKey, resultSql);
        }
        return resultSql;
    }

    @Override
    //TODO
    public String findLike(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    //TODO
    public String findByEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    //TODO
    public String findNotThisEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    //TODO
    public String findNotLikeEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    @Override
    public String insert(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_insert";
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            StringBuilder columnSql = new StringBuilder();
            StringBuilder paramSql = new StringBuilder();

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
                        paramSql.append("#{entity." + field.getName() + "}" + COLUMN_SPLIT);
                    }
                }

                sql.append(SQLHelper.getSimpleTableName(clazz))
                        .append("(" + columnSql.substring(0, columnSql.length() - 1) + ")")
                        .append(" VALUES ")
                        .append("(" + paramSql.substring(0, paramSql.length() - 1) + ")");
                resultSql = sql.toString();

                sqlCache.put(sqlCacheKey, resultSql);
            }
        }
        return resultSql;
    }

    @Override
    public String updateById(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_updateById";
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" UPDATE ");
            StringBuilder setSql = new StringBuilder(" SET ");
            StringBuilder whereSql = new StringBuilder(" WHERE ");

            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                boolean hasPId = false;
                for (Field field : fields) {
                    if (field.isAnnotationPresent(SQLHelper.P_ID)) {
                        hasPId = true;
                        String fieldColumn = SQLHelper.getFieldColumn(field);
                        whereSql.append(fieldColumn + " = #{" + field.getName() + "}");
                    } else {
                        String fieldColumn = SQLHelper.getFieldColumn(field);
                        setSql.append(fieldColumn + " = #{ " + field.getName() + " }" + COLUMN_SPLIT);
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
        }
        return resultSql;
    }

    @Override
    public String deleteById(Class<?> clazz, Object param) throws Exception {
        String resultSql = "";
        String sqlCacheKey = clazz.getName() + "_deleteById";
        if (sqlCache.containsKey(sqlCacheKey)) {
            resultSql = sqlCache.get(sqlCacheKey);
        } else {
            StringBuilder sql = new StringBuilder(" DELETE FROM ");
            StringBuilder whereSql = new StringBuilder(" WHERE ");

            Field idField = SQLHelper.getIdField(clazz);
            if (idField != null) {
                String fieldColumn = SQLHelper.getFieldColumn(idField);
                whereSql.append(fieldColumn + " = #{" + idField.getName() + "}");

                String tableName = SQLHelper.getTableName(clazz);
                sql.append(tableName).append(whereSql);

                resultSql = sql.toString();
                sqlCache.put(sqlCacheKey, resultSql);
            } else {
                throw new Exception("no found primary key with @Id");
            }
        }
        return resultSql;
    }

    @Override
    //TODO
    public String deleteByEntity(Class<?> clazz, Object param) throws Exception {
        return null;
    }

    /**
     * 部分sql不能对where条件进行缓存，因为where根据传入的参数可能会动态变化
     */
    private static String dealQuerySql(Class clazz, StringBuilder tableSql, StringBuilder columnSql) throws Exception {
        String simpleTableName = SQLHelper.getSimpleTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
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
                        tableSql.append(" LEFT JOIN " + SQLHelper.getTableName(pEntity.clazz()) + " ON " + simpleTableName + "." + SQLHelper.getFieldColumn(fields, attr) + "=" + childSimpleTableName + "." + SQLHelper.getFieldColumn(pEntity.clazz(), relAttr));
                    }
                } else {
                    String fieldColumn = SQLHelper.getFieldColumn(field);
                    if (StringUtils.isNotEmpty(fieldColumn)) {
                        columnSql.append(simpleTableName + "." + fieldColumn + " AS " + (clazz.getSimpleName() + "_" + field.getName()).toLowerCase() + COLUMN_SPLIT);
                    }
                }
            }
        }
        return simpleTableName;
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
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
        int startOfSelect = sql.toLowerCase().indexOf("select");

        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100)
                .append(sql.substring(0, startOfSelect)) //add the comment
                .append("select * from ( select ") //nest the main query in an outer select
                .append(getRowNumber(sql)); //add the rownnumber bit into the outer query select list

        if (hasDistinct(sql)) {
            pagingSelect.append(" row_.* from ( ") //add another (inner) nested select
                    .append(sql.substring(startOfSelect)) //add the main query
                    .append(" ) as row_"); //close off the inner nested select
        } else {
            pagingSelect.append(sql.substring(startOfSelect + 6)); //add the main query
        }

        pagingSelect.append(" ) as temp_ where rownumber_ ");

        //add the restriction to the outer select
        if (offset > 0) {
//			int end = offset + limit;
            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            pagingSelect.append("between ").append(offsetPlaceholder)
                    .append("+1 and ").append(endString);
        } else {
            pagingSelect.append("<= ").append(limitPlaceholder);
        }

        return pagingSelect.toString();
    }

    @Override
    public String insertUpbHis(Class<?> clazz, Object param) throws Exception {
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

    /**
     * 将数据写入到竣工表中
     */
    public String insertDelHis(Class<?> clazz, Object param) throws Exception {
        return null;
    }
}
