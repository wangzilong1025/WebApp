package com.sandi.web.common.split;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.split.cache.SplitTableCache;
import com.sandi.web.common.split.entity.CfgTableSplitEntity;
import com.sandi.web.common.split.entity.CfgTableSplitMappingEntity;
import com.sandi.web.common.split.service.interfaces.ICfgTableSplitSV;
import com.sandi.web.common.split.service.interfaces.ISplitTableConvertSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.common.utils.ReflectionsUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by dizl on 2015/6/3.
 * 分表类
 */
public class SplitTableFactory {

    private static final Logger log = Logger.getLogger(SplitTableFactory.class);
    private static Map<String, ISplitTableConvertSV> convertCacheMap = new HashMap<String, ISplitTableConvertSV>();
    private static Map<String, CfgTableSplitEntity> cfgTableSplitEntityCache = new HashMap<String, CfgTableSplitEntity>();

    /**
     * 获取分表后的表名
     */
    public static String getSplitTable(String tableName, Object param) throws Exception {
        String tableNameExpr = tableName;
        CfgTableSplitEntity cfgTableSplitEntity = getCfgTableSplitEntity(tableName);
        if (cfgTableSplitEntity != null) {
            tableNameExpr = cfgTableSplitEntity.getTableNameExpr();
            if (tableNameExpr.indexOf("T[TABLE]") != -1) {
                tableNameExpr = StringUtils.replace(tableNameExpr, "T[TABLE]", cfgTableSplitEntity.getTableName());
            }
            List<CfgTableSplitMappingEntity> cfgTableSplitMappingEntityLists = cfgTableSplitEntity.getCfgTableSplitMappingEntitys();
            if (cfgTableSplitMappingEntityLists != null && cfgTableSplitMappingEntityLists.size() > 0) {
                for (CfgTableSplitMappingEntity cfgTableSplitMappingEntity : cfgTableSplitMappingEntityLists) {
                    String columnName = cfgTableSplitMappingEntity.getColumnName();
                    if (tableNameExpr.indexOf("C[" + columnName + "]") != -1) {
                        String columnValue = convert(getColumnValue(columnName, param, cfgTableSplitMappingEntity.getColumnConvertClass()), cfgTableSplitMappingEntity.getColumnConvertClass());
                        if (columnValue != null) {
                            tableNameExpr = StringUtils.replace(tableNameExpr, "C[" + columnName + "]", columnValue);
                        } else {
                            log.error("获取分表字段数据失败");
                            ExceptionUtil.throwBusinessException("获取分表字段数据失败...");
                        }
                    }
                }
            }
        }
        return tableNameExpr;
    }

    public static boolean isSplitTable(String tableName) throws Exception {
        CfgTableSplitEntity cfgTableSplitEntity = getCfgTableSplitEntity(tableName);
        if (cfgTableSplitEntity == null) {
            return false;
        }
        return true;
    }

    private static CfgTableSplitEntity getCfgTableSplitEntity(String tableName) throws Exception {
        //判断是否使用缓存，如果使用则从缓存中获取数据
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);
        CfgTableSplitEntity cfgTableSplitEntity = null;
        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            Object obj = CacheFactory.get(SplitTableCache.CACHE_NAME, tableName);
            if (obj != null) {
                cfgTableSplitEntity = (CfgTableSplitEntity) obj;
            }
        }
        if (cfgTableSplitEntity == null) {
            log.info("未使用缓存，根据表名从数据库中查询分表数据");
            //如果没有使用缓存，则利用本地缓存进行获取
            if (cfgTableSplitEntityCache.containsKey(tableName)) {
                cfgTableSplitEntity = cfgTableSplitEntityCache.get(tableName);
            } else {
                ICfgTableSplitSV cfgTableSplitSV = SpringContextHolder.getBean(ICfgTableSplitSV.class);
                cfgTableSplitEntity = cfgTableSplitSV.getCfgTableSplitByTableName(tableName.toUpperCase());
                cfgTableSplitEntityCache.put(tableName, cfgTableSplitEntity);
            }
        }
        return cfgTableSplitEntity;
    }

    private static Object getColumnValue(String columnName, Object param, String convertClassName) throws Exception {
        if (param != null) {
            if (param instanceof String) {
                return param;
            } else if (param instanceof Date) {
                return param;
            } else if (param instanceof Number) {
                return param;
            } else if (param instanceof Map) {
                Map map = (Map) param;
                Object resultObj = null;
                if (map.containsKey(columnName)) {
                    resultObj = map.get(columnName);
                } else if (map.containsKey("entity")) {
                    Object obj = map.get("entity");
                    return getColumnValue(columnName, obj, convertClassName);
                } else {
                    Set<String> keySet = map.keySet();
                    Iterator<String> iter = keySet.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        if (StringUtils.equalsIgnoreCase(columnName, key)) {
                            resultObj = map.get(key);
                        }
                    }
                }
                if (resultObj != null) {
                    return resultObj;
                } else {
                    ExceptionUtil.throwBusinessException("未找到" + columnName + "对应的配置数据");
                }
            } else if (param instanceof List) {
                List<BaseEntity> entitys = (List<BaseEntity>) param;
                Object resultObj = null;
                if (entitys != null && entitys.size() > 0) {
                    String convertStr = "";
                    for (BaseEntity entity : entitys) {
                        Object temp = getColumnValue(columnName, entity, convertClassName);
                        if (resultObj == null) {
                            resultObj = temp;
                            convertStr = convert(resultObj, convertClassName);
                        } else {  //如果为按年月日分表的话，直接不校验
                            if (StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYConvertSVImpl") ||
                                    StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYMMConvertSVImpl") ||
                                    StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYMMDDConvertSVImpl")) {
                                break;
                            } else if (!StringUtils.equalsIgnoreCase(convertStr, convert(temp, convertClassName))) {
                                ExceptionUtil.throwBusinessException("批量数据保存的时候，有数据不在相同分表中");
                            } else {
                                break;
                            }
                        }
                    }
                }
                if (resultObj != null) {
                    return resultObj;
                } else {
                    ExceptionUtil.throwBusinessException("未找到" + columnName + "对应的配置数据");
                }
            } else if (param instanceof Array) {
                BaseEntity[] entitys = (BaseEntity[]) param;
                Object resultObj = null;
                if (entitys != null && entitys.length > 0) {
                    String convertStr = "";
                    for (BaseEntity entity : entitys) {
                        Object temp = getColumnValue(columnName, entity, convertClassName);
                        if (resultObj == null) {
                            resultObj = temp;
                            convertStr = convert(resultObj, convertClassName);
                        } else {
                            //如果为按年月日分表的话，直接不校验
                            if (StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYConvertSVImpl") ||
                                    StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYMMConvertSVImpl") ||
                                    StringUtils.equals(convertClassName, "com.sandi.web.common.split.service.impl.YYYYMMDDConvertSVImpl")) {
                                break;
                            } else if (!StringUtils.equalsIgnoreCase(convertStr, convert(temp, convertClassName))) {
                                ExceptionUtil.throwBusinessException("批量数据保存的时候，有数据不在相同分表中");
                            } else {
                                break;
                            }
                        }
                    }
                }
                if (resultObj != null) {
                    return resultObj;
                } else {
                    ExceptionUtil.throwBusinessException("未找到" + columnName + "对应的配置数据");
                }
            } else {
                Field field = SQLHelper.getFieldByColumnName(param.getClass(), columnName);
                if (field != null) {
                    Object obj = ReflectionsUtils.getFieldValue(param, field);
                    return obj;
                } else {
                    ExceptionUtil.throwBusinessException("未找到" + columnName + "对应的字段");
                }
            }
        }
        return param;
    }

    private static String convert(Object param, String convertClassName) throws Exception {
        ISplitTableConvertSV convertSV;
        if (convertCacheMap.containsKey(convertClassName)) {
            convertSV = convertCacheMap.get(convertClassName);
        } else {
            convertSV = (ISplitTableConvertSV) Class.forName(convertClassName).newInstance();
            convertCacheMap.put(convertClassName, convertSV);
        }
        return convertSV.convert(param);
    }
}
