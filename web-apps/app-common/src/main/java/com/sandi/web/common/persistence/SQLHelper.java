package com.sandi.web.common.persistence;

import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.persistence.annotation.Entity;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.annotation.Table;
import com.sandi.web.common.persistence.dialect.Dialect;
import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.common.split.SplitTableFactory;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ReflectionsUtils;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by dizl on 2015/5/11.
 */
public class SQLHelper {
    public static final Class<? extends Annotation> P_TABLE = Table.class;
    public static final Class<? extends Annotation> P_COLUMN = Column.class;
    public static final Class<? extends Annotation> P_ID = Id.class;
    public static final Class<? extends Annotation> P_ENTITY = Entity.class;

    private static final String CRUD_RESULTMAP_ID = "CRUD_RESULTMAP_ID_";
    private static final Logger log = Logger.getLogger(SQLHelper.class);

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql     Mapper中的Sql语句
     * @param page    分页对象
     * @param dialect 方言类型
     * @return 分页SQL
     * entity.getClass(),sql,entity.getPage(),DIALECT,params
     */
    public static String generatePageSql(String sql, Page page, Dialect dialect) throws Exception {
        if (dialect.supportsLimit()) {
            return dialect.getLimitString(sql, page.getFirstResult(), page.getMaxResults());
        } else {
            return sql;
        }
    }

    /**
     * 查询数据
     *
     * @param sql             SQL语句
     * @param connection      数据库连接
     * @param mappedStatement mapped
     * @throws SQLException sql查询错误
     */
    public static int getCount(final String sql, final Connection connection, final MappedStatement mappedStatement, final Object parameterObject, final BoundSql boundSql) throws SQLException {
        final String countSql = "select count(1) from (" + sql + ") tmp_count";
        Connection conn = connection;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("COUNT SQL: " + StringUtils.replaceEach(countSql, new String[]{"\n", "\t"}, new String[]{" ", " "}));
            }
            if (conn == null) {
                conn = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
                conn.setAutoCommit(false);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++" + conn);
            }
            ps = conn.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), parameterObject);
            SQLHelper.setParameters(ps, mappedStatement, countBS, parameterObject);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            conn.commit();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps              表示预编译的 SQL 语句的对象。
     * @param mappedStatement MappedStatement
     * @param boundSql        SQL
     * @param parameterObject 参数对象
     * @throws SQLException 数据库异常
     */
    @SuppressWarnings("unchecked")
    public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null :
                    configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    public static List<ResultMap> getResultMap(Configuration configuration, Class beanClazz) {
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = dealResultMap(configuration, beanClazz, resultMaps);
        resultMaps.add(resultMap);
        return resultMaps;
    }

    private static ResultMap dealResultMap(Configuration configuration, Class beanClazz, List<ResultMap> resultMaps) {
        String resultMapId = CRUD_RESULTMAP_ID + beanClazz.getName();
        ResultMap resultMap = null;
        if (configuration.hasResultMap(resultMapId)) {
            resultMap = configuration.getResultMap(resultMapId);
        } else {
            List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
            Field[] fields = beanClazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    ReflectionsUtils.makeAccessible(field);
                    String fieldName = field.getName();
                    Class javaType = field.getType();
                    /**
                     * 如果fieldName为jacocoData跳过。jacoco代码覆盖率分析
                     */
                    if (fieldName.equals("$jacocoData")) {
                        continue;
                    }
                    if (field.isAnnotationPresent(P_COLUMN)) {
                        Column pColumn = (Column) field.getAnnotation(P_COLUMN);
                        if (!pColumn.isColumn()) {
                            continue;
                        }
                    }
                    if (field.isAnnotationPresent(P_ENTITY)) {
                        Entity pBean = (Entity) field.getAnnotation(P_ENTITY);
                        Class childBeanClazz = pBean.clazz();
                        ResultMap childResultMap = dealResultMap(configuration, childBeanClazz, resultMaps);
                        if (childResultMap != null) {
                            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, fieldName, (getSimpleClassName(childBeanClazz.getSimpleName()) + "_" + fieldName).toLowerCase(), javaType);
                            builder.nestedResultMapId(childResultMap.getId());
                            builder.lazy(true);
                            resultMappings.add(builder.build());
                        }
                    } else {
                        List<ResultFlag> listResultFlag = new ArrayList<ResultFlag>();
                        if (field.isAnnotationPresent(P_ID)) {
                            listResultFlag = new ArrayList<ResultFlag>();
                            listResultFlag.add(ResultFlag.ID);
                        }
                        ResultMapping.Builder builder = new ResultMapping.Builder(configuration, fieldName, (getSimpleClassName(beanClazz.getSimpleName()) + "_" + fieldName).toLowerCase(), javaType);
                        builder.flags(listResultFlag);
                        resultMappings.add(builder.build());
                    }
                }
            }
            ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, resultMapId, beanClazz, resultMappings);
            resultMap = resultMapBuilder.build();

            configuration.addResultMap(resultMap);
        }
        return resultMap;
    }

    public static String getSimpleTableName(Class clazz) {
        String tableName = clazz.getSimpleName();
        if (clazz.isAnnotationPresent((P_TABLE))) {
            Table platTable = (Table) clazz.getAnnotation(P_TABLE);
            if (StringUtils.isNotEmpty(platTable.value())) {
                tableName = platTable.value()
                        .replace(CommConstants.SplitTable.splitTableStartFlag, "")
                        .replace(CommConstants.SplitTable.splitTableEndFlag, "");
            } else {
                if (tableName.endsWith("Entity")) {
                    tableName = tableName.substring(0, tableName.length() - 6);
                } else if (tableName.endsWith("Bean")) {
                    tableName = tableName.substring(0, tableName.length() - 4);
                }
                tableName = StringUtils.camelToUnderline(tableName);
            }
        } else {
            if (tableName.endsWith("Entity")) {
                tableName = tableName.substring(0, tableName.length() - 6);
            } else if (tableName.endsWith("Bean")) {
                tableName = tableName.substring(0, tableName.length() - 4);
            }
            tableName = StringUtils.camelToUnderline(tableName);
        }
        return tableName.toUpperCase();
    }

    /**
     * 如果分表的话，会返回分表标识
     */
    public static String getTableName(Class clazz) throws Exception {
        String tableName = getSimpleTableName(clazz);
        boolean hasConfTable = false;//是否设置了表名
        if (clazz.isAnnotationPresent((P_TABLE))) {
            Table platTable = (Table) clazz.getAnnotation(P_TABLE);
            if (StringUtils.isNotEmpty(platTable.value())) {
                tableName = platTable.value();
                hasConfTable = true;
            }
        }

        if (!hasConfTable && SplitTableFactory.isSplitTable(tableName)) {
            tableName = CommConstants.SplitTable.splitTableStartFlag + tableName + CommConstants.SplitTable.splitTableEndFlag;
        }
        return tableName;
    }

    public static String getFieldColumn(Field field) {
        if (field.isAnnotationPresent(P_ENTITY)) {
            return null;
        }
        String columnName = field.getName();
        if (field.isAnnotationPresent(P_COLUMN)) {
            Column platColumn = (Column) field.getAnnotation(P_COLUMN);
            if (platColumn.isColumn()) {
                if (StringUtils.isNotEmpty(platColumn.value())) {
                    columnName = platColumn.value();
                } else {
                    columnName = StringUtils.camelToUnderline(columnName);
                }
            } else {
                columnName = null;
            }
        } else {
            columnName = StringUtils.camelToUnderline(columnName);
        }
        if (columnName != null) {
            columnName = columnName.toUpperCase();
        }
        /**
         * 如果columnName为JACOCO_DATA跳过。jacoco代码覆盖率分析
         */
        if (columnName != null && columnName.equalsIgnoreCase("$JACOCO_DATA")) {
            return null;
        }
        return columnName;
    }

    public static String getFieldColumn(Field[] fields, String fieldName) {
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                if (StringUtils.equals(fieldName, field.getName())) {
                    return getFieldColumn(field);
                }
            }
        }
        return null;
    }

    public static String getFieldColumn(Class clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        return getFieldColumn(fields, fieldName);
    }

    public static Field getFieldByColumnName(Class clazz, String columnName) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                if (StringUtils.equalsIgnoreCase(columnName, getFieldColumn(field))) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Field getIdField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return getIdField(fields);
    }

    public static Field getIdField(Field[] fields) {
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                //判断是否设置为主键标识
                if (field.isAnnotationPresent(P_ID)) {
                    return field;
                }
            }
        }
        return null;
    }


    public static Object getBaseEntity(Object param) throws Exception {
        Object baseEntity = null;
        if (param instanceof Map) {
            Map map = (Map) param;
            if (map.containsKey("entity")) {
                baseEntity = map.get("entity");
            } else {
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    Object obj = map.get(iter.next());
                    if (obj != null) {
                        baseEntity = getBaseEntity(obj);
                        if (baseEntity != null) {
                            break;
                        }
                    }
                }
            }
        } else if (param instanceof BaseEntity) {
            baseEntity = (BaseEntity) param;
        }
        return baseEntity;
    }

    public static Object getPage(Object param) throws Exception {
        Object page = null;
        if (param instanceof Map) {
            Map map = (Map) param;
            if (map.containsKey("page")) {
                page = map.get("page");
            } else {
                Iterator iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    Object obj = map.get(iter.next());
                    if (obj != null) {
                        page = getPage(obj);
                        if (page != null) {
                            break;
                        }
                    }
                }
            }
        } else if (param instanceof Page) {
            page = (Page) param;
        }
        return page;
    }

    public static String getSimpleClassName(String simpleName) {
        if (simpleName.endsWith("Entity")) {
            simpleName = simpleName.substring(0, simpleName.length() - 6);
        } else if (simpleName.endsWith("Bean")) {
            simpleName = simpleName.substring(0, simpleName.length() - 4);
        }

        if (simpleName.length() > 10) {
            for (int i = simpleName.length() - 1; i < simpleName.length(); i--) {
                char c = simpleName.charAt(i);
                if (Character.isUpperCase(c)) {
                    simpleName = simpleName.substring(i, simpleName.length());
                    break;
                }
            }
            if (simpleName.length() > 10) {
                simpleName = StringUtils.abbr(simpleName, 10);
            }
        }
        return simpleName;
    }

    public static String getJdbcType(String fieldClazzName) {
        if (StringUtils.equalsIgnoreCase("string", fieldClazzName)) {
            return "VARCHAR";
        } else if (StringUtils.equalsIgnoreCase("boolean", fieldClazzName)) {
            return "BOOLEAN";
        } else if (StringUtils.equalsIgnoreCase("byte", fieldClazzName)) {
            return "TINYINT";
        } else if (StringUtils.equalsIgnoreCase("short", fieldClazzName)) {
            return "SMALLINT";
        } else if (StringUtils.equalsIgnoreCase("integer", fieldClazzName)) {
            return "INTEGER";
        } else if (StringUtils.equalsIgnoreCase("int", fieldClazzName)) {
            return "INTEGER";
        } else if (StringUtils.equalsIgnoreCase("long", fieldClazzName)) {
            return "BIGINT";
        } else if (StringUtils.equalsIgnoreCase("float", fieldClazzName)) {
            return "REAL";
        } else if (StringUtils.equalsIgnoreCase("double", fieldClazzName)) {
            return "DOUBLE";
        } else if (StringUtils.equalsIgnoreCase("date", fieldClazzName)) {
            return "DATE";
        } else if (StringUtils.equalsIgnoreCase("time", fieldClazzName)) {
            return "TIME";
        } else if (StringUtils.equalsIgnoreCase("timestamp", fieldClazzName)) {
            return "TIMESTAMP";
        } else if (StringUtils.equalsIgnoreCase("clob", fieldClazzName)) {
            return "CLOB";
        } else if (StringUtils.equalsIgnoreCase("blob", fieldClazzName)) {
            return "BLOB";
        } else if (StringUtils.equalsIgnoreCase("bigDecimal", fieldClazzName)) {
            return "NUMERIC";
        } else if (StringUtils.equalsIgnoreCase("byte[]", fieldClazzName)) {
            return "BINARY";
        }
        return "VARCHAR";
    }
}
