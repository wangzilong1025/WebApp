package com.sandi.web.common.persistence.interceptor;

import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dialect.Dialect;
import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.StringUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by dizl on 2015/5/8.
 * 为DAO提供通用的方法
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class CrudInterceptor extends BaseInterceptor {
    private static Logger log = Logger.getLogger(CrudInterceptor.class);
    private static final Class<? extends Annotation> P_MYBATISDAO = Dao.class;
    private static Map<String, List<ResultMap>> resultMapCache = new HashMap<String, List<ResultMap>>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
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

    private void processIntercept(final Object[] queryArgs) throws Exception {
        final MappedStatement mappedStatement = (MappedStatement) queryArgs[0];//获取MyBatis定义的MappedStatement对象
        final Object parameter = queryArgs[1];//获取参数
        //对Mybatis的MapperMethod进行改造，将接口名称传过来
        if (parameter != null && parameter instanceof Map) {
            Map map = (Map) parameter;
            if (map.containsKey(CommConstants.CrudDaoSql.KEY_PARAMS)) {
                queryArgs[1] = map.get(CommConstants.CrudDaoSql.KEY_PARAMS);
            }
        }
        //获取操作Sql
        BoundSql boundSQL = mappedStatement.getBoundSql(queryArgs[1]);
        String mapperSQL = boundSQL.getSql();
        //edit xiaoke 2016年1月22日15:29:45
        String sql = "";
        //edit end
        //如果操作Sql中包含特殊标记$$GeneralCrud$$,说明这次查询为公共查询，则需要对Bean进行解析查询
        boolean interceptor = mapperSQL.contains(CommConstants.CrudDaoSql.CRUD_FLAG);
        if (interceptor) {
            if (parameter != null && parameter instanceof Map) {
                Map map = (Map) parameter;

                //edit  add by xiaoke 2016年1月22日15:07:31 根据sql查询的时候需要将传入的参数拉到@Param下面和传入的sql处于同一级
                if ((CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_BY_SQL).equals(mapperSQL)) {
                    Map paramsMap = (Map) map.get(CommConstants.CrudDaoSql.KEY_PARAMS);
                    Map param = (Map) paramsMap.get(CommConstants.CrudDaoSql.KEY_PARAM);
                    sql = paramsMap.get(CommConstants.CrudDaoSql.KEY_SQL).toString();
                    if (param != null) {
                        param.put(CommConstants.CrudDaoSql.KEY_SQL, sql);
                        queryArgs[1] = param;
                    }
                }
                //edit end  2016年1月22日15:07:35

                Class clazz = (Class) map.get(CommConstants.CrudDaoSql.KEY_DAO_CLASS);//Dao
                Class paramType = map.get(CommConstants.CrudDaoSql.KEY_PARAMS).getClass();

                if (clazz.isAnnotationPresent(P_MYBATISDAO)) {
                    Dao myBatisDao = (Dao) clazz.getAnnotation(P_MYBATISDAO);
                    String msId = "Crud." + clazz.getName() + mapperSQL.substring(mapperSQL.lastIndexOf("."), mapperSQL.length());
                    //获取新的Sql语句，然后重新进行封装
                    String newSql = getExecuSQL(myBatisDao.value(), msId, mapperSQL, queryArgs[1], DIALECT, mappedStatement, boundSQL);
                    log.info("Crud转化后的SQL语句：" + newSql);
                    //重新构建MyBatis的MapperStatement对象
                    queryArgs[0] = copyMappedStatement(mappedStatement, newSql, msId, mapperSQL, paramType, myBatisDao.value());
                } else {
                    if (clazz.getGenericSuperclass() == BaseEntity.class) {
                        String msId = "Crud." + clazz.getName() + mapperSQL.substring(mapperSQL.lastIndexOf("."), mapperSQL.length());
                        String newSql = getExecuSQL(clazz, msId, mapperSQL, queryArgs[1], DIALECT, mappedStatement, boundSQL);
                        log.info("Crud转化后的SQL语句：" + newSql);
                        queryArgs[0] = copyMappedStatement(mappedStatement, newSql, msId, mapperSQL, paramType, clazz);
                    } else {//edit by xiaoke  通过sql查询的处理
                        String msId = "Crud." + clazz.getName() + mapperSQL.substring(mapperSQL.lastIndexOf("."), mapperSQL.length()) + ".map";
                        String newSql = getExecuSQL(clazz, msId, mapperSQL, queryArgs[1], DIALECT, mappedStatement, boundSQL);
                        queryArgs[0] = copyMappedStatement(mappedStatement, newSql, msId, mapperSQL, paramType, null);
//                      throw new Exception(clazz.getName() + "缺少@MyBatisDao注解");
                        //edit end
                    }
                }
            }
        }
    }

    private MappedStatement copyMappedStatement(MappedStatement ms, String newSql, String msId, String mapperSQL, Class paramType, Class beanClazz) throws Exception {
        SqlSource sqlSource = ms.getSqlSource();
        SqlSource newSqlSource = sqlSource;
        //批量保存处理
        //看newSql中是否包含循环标记，如果包含循环标记则拆分成动态SqlSource
        if (newSql.contains(CommConstants.CrudDaoSql.FOR_EACH_SAVE_FLAG)) {
            String staticSql1 = newSql.substring(0, newSql.indexOf(CommConstants.CrudDaoSql.FOR_EACH_SAVE_FLAG));
            String staticSql2 = newSql.substring(newSql.indexOf(CommConstants.CrudDaoSql.FOR_EACH_SAVE_FLAG) + CommConstants.CrudDaoSql.FOR_EACH_SAVE_FLAG.length(), newSql.length());
            SqlNode staticSqlNode1 = new StaticTextSqlNode(staticSql1);
            SqlNode staticSqlNode2 = new StaticTextSqlNode(staticSql2);
            List<SqlNode> itemLists = new ArrayList<SqlNode>();
            itemLists.add(staticSqlNode2);
            SqlNode itemMixedSqlNode = new MixedSqlNode(itemLists);
            SqlNode forEachNode = new ForEachSqlNode(ms.getConfiguration(), itemMixedSqlNode, "entity", "index", "item", "(", ")", " UNION ALL ");

            List<SqlNode> sqlNodeList = new ArrayList<SqlNode>();
            sqlNodeList.add(staticSqlNode1);
            sqlNodeList.add(forEachNode);
            SqlNode mixedSqlNode = new MixedSqlNode(sqlNodeList);

            DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), mixedSqlNode);
            newSqlSource = dynamicSqlSource;
        } else if (sqlSource instanceof StaticSqlSource) {
            StaticSqlSource staticSqlSource = new StaticSqlSource(ms.getConfiguration(), newSql);
            newSqlSource = staticSqlSource;
        } else if (sqlSource instanceof RawSqlSource) {
            RawSqlSource rawSqlSource = new RawSqlSource(ms.getConfiguration(), newSql, paramType);
            newSqlSource = rawSqlSource;
        }
        List<ResultMap> resultMaps = ms.getResultMaps();
        if (!mapperSQL.endsWith("Count")) {
            if (resultMapCache.containsKey(msId)) {
                resultMaps = resultMapCache.get(msId);
            } else if (beanClazz == null) {
//                resultMaps = SQLHelper.getResultMap(ms.getConfiguration());
            } else {
                resultMaps = SQLHelper.getResultMap(ms.getConfiguration(), beanClazz);
                resultMapCache.put(msId, resultMaps);
            }
        }

        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(ms.getConfiguration(), msId, newSqlSource, ms.getSqlCommandType());
        mappedStatementBuilder.cache(ms.getCache());
        mappedStatementBuilder.databaseId(ms.getDatabaseId());
        mappedStatementBuilder.fetchSize(ms.getFetchSize());
        mappedStatementBuilder.keyColumn(StringUtils.connectStrArray(ms.getKeyColumns(), ","));
        mappedStatementBuilder.keyGenerator(ms.getKeyGenerator());
        mappedStatementBuilder.keyProperty(StringUtils.connectStrArray(ms.getKeyProperties(), ","));
        mappedStatementBuilder.lang(ms.getLang());
        mappedStatementBuilder.parameterMap(ms.getParameterMap());
        mappedStatementBuilder.resource(ms.getResource());
        mappedStatementBuilder.resulSets(StringUtils.connectStrArray(ms.getResulSets(), ","));
        mappedStatementBuilder.resultSetType(ms.getResultSetType());
        mappedStatementBuilder.statementType(ms.getStatementType());
        mappedStatementBuilder.timeout(ms.getTimeout());
        mappedStatementBuilder.resultMaps(resultMaps);
        return mappedStatementBuilder.build();
    }

    private String getExecuSQL(Class<?> clazz, String msId, String mapperDBsql, Object param, Dialect dialect, MappedStatement mappedStatement, BoundSql boundSql) throws Exception {
        String resultSql = null;
        //根据不同的数据库，调用不同的方言进行操作
        if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_BY_ID, mapperDBsql)) {//根据主键查询数据
            resultSql = dialect.findById(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_LIKE, mapperDBsql)) {//根据实体类模糊查询
            resultSql = dialect.findLike(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_LIKE_COUNT, mapperDBsql)) {//查询数量
            resultSql = dialect.findLikeCount(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_BY_ENTITY, mapperDBsql)) {//根据实体查询
            resultSql = dialect.findByEntity(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_BY_ENTITY_COUNT, mapperDBsql)) {
            resultSql = dialect.findByEntityCount(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_NOT_THIS_ENTITY, mapperDBsql)) {
            resultSql = dialect.findNotThisEntity(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_NOT_THIS_ENTITY_COUNT, mapperDBsql)) {
            resultSql = dialect.findNotThisEntityCount(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_NOT_LINK_ENTITY, mapperDBsql)) {
            resultSql = dialect.findNotLikeEntity(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_NOT_LINK_ENTITY_COUNT, mapperDBsql)) {
            resultSql = dialect.findNotLikeEntityCount(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.SAVE, mapperDBsql)) {//插入数据
            resultSql = dialect.insert(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.UPDATE_BY_ID, mapperDBsql)) {//根据主键更新数据
            resultSql = dialect.updateById(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.DELETE_BY_ID, mapperDBsql)) {//根据主键删除数据
            resultSql = dialect.deleteById(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.DELETE_BY_ENTITY, mapperDBsql)) {//根据实体类删除数据
            resultSql = dialect.deleteByEntity(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.INSERT_UPB_HIS, mapperDBsql)) {//修改时写入的表
            resultSql = dialect.insertUpbHis(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.INSERT_DEL_HIS, mapperDBsql)) {//删除时写入的表
            resultSql = dialect.insertDelHis(clazz, param);
        } else if (StringUtils.equals(CommConstants.CrudDaoSql.CRUD_FLAG + "." + CommConstants.CrudDaoSql.FIND_BY_SQL, mapperDBsql)) {//根据sql查询转化为实体
            Map map = (Map) param;
            resultSql = map.get(CommConstants.CrudDaoSql.KEY_SQL).toString();
        }
        return resultSql;
    }
}
