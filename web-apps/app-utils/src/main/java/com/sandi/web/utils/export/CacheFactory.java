package com.sandi.web.utils.export;

import com.sandi.web.utils.common.JedisUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by dizl on 2015/6/3.
 * 缓存类
 */
public class CacheFactory {
    private static final Log log = LogFactory.getLog(CacheFactory.class);
    private static final String LOCK_CACHE_MODULE_FLAG = "_LOCK_CACHE_MODULE_FLAG_";//缓存正在处理的模块
    private static final String LOCK_CACHE_FLAG = "_LOCK_CACHE_FLAG_";
    private static final String CFG_CACHE_ENTITY = "_CFG_CACHE_ENTITY_";
    private static final String MODULE_NAME_SPLIT_FLAG = ",";//缓存表中配置的模块分隔符
    private static final String ALL_MODULE = "ALL";//所有模块

    /**
     * web层通过 cacheName 获取 Redis 缓存
     * 仅供web层使用
     *
     * @param cacheName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRedisCache(String cacheName) throws Exception {
        Map<String, Object> resultMap = null;
        if (JedisUtils.existsObject(cacheName)) {
            resultMap = (Map<String, Object>) JedisUtils.getObject(cacheName);
        }
        return resultMap;
    }

    /**
     * web层通过 cacheName和key获取 Redis 缓存
     * <p/>
     * 仅供web层使用
     */
    public static Object getRedisCache(String cacheName, Object key) throws Exception  {
         Map<String,Object> resultMap = getRedisCache(cacheName);
            if(resultMap!=null){
                return resultMap.get(key);
            }
         return null;
    }

}
