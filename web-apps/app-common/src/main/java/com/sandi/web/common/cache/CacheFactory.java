package com.sandi.web.common.cache;

import com.sandi.web.common.cache.entity.CfgCacheLoadEntity;
import com.sandi.web.common.cache.job.CacheLoadJob;
import com.sandi.web.common.cache.service.interfaces.ICfgCacheLoadSV;
import com.sandi.web.common.quartz.QuartzJobFactory;
import com.sandi.web.common.utils.CacheUtils;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import java.util.*;

/**
 * Created by dizl on 2015/6/3.
 * 缓存类
 */
public class CacheFactory {
    private static final Logger log = Logger.getLogger(CacheFactory.class);
    private static final String LOCK_CACHE_MODULE_FLAG = "_LOCK_CACHE_MODULE_FLAG_";//缓存正在处理的模块
    private static final String LOCK_CACHE_FLAG = "_LOCK_CACHE_FLAG_";
    private static final String CFG_CACHE_ENTITY = "_CFG_CACHE_ENTITY_";
    private static final String MODULE_NAME_SPLIT_FLAG = ",";//缓存表中配置的模块分隔符
    private static final String ALL_MODULE = "ALL";//所有模块
    private static final String sysModuleName = Global.getConfig(CommConstants.Config.MODULE_NAME);
    private static final String loadRedisCache = Global.getConfig(CommConstants.Config.LOAD_REDIS_CACHE);

    static {
        try {
            loadAllCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据缓存名称和缓存类获取缓存信息
     */
    public static Object get(String cacheName, Object key) throws Exception {
        Map<String, Object> resultMap = get(cacheName);
        if (resultMap != null) {
            return resultMap.get(key);
        }
        return null;
    }

    /**
     * 根据缓存名称获取缓存数据
     */
    public static Map<String, Object> get(String cacheName) throws Exception {
        Map<String, Object> resultMap = null;
        Object cfgCache = CacheUtils.get(CFG_CACHE_ENTITY);
        if (cfgCache != null) {
            List<CfgCacheLoadEntity> cfgCacheLoadEntityList = (List<CfgCacheLoadEntity>) cfgCache;
            LOOP:
            for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                if (StringUtils.equalsIgnoreCase(cacheName, cfgCacheLoadEntity.getCacheName())) {
                    String moduleName = cfgCacheLoadEntity.getModuleName();
                    if (StringUtils.isNotEmpty(moduleName)) {
                        String[] moduleNames = StringUtils.split(moduleName, MODULE_NAME_SPLIT_FLAG);
                        for (String tempModule : moduleNames) {
                            //判断该模块能否获取数据
                            if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModule) || StringUtils.equalsIgnoreCase(sysModuleName, tempModule)) {
                                resultMap = getCacheValue(cfgCacheLoadEntity, null);
                                return resultMap;
                            }
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 根据缓存类获取缓存信息
     */
    public static Map<String, Object> get(Class clazz) throws Exception {
        String className = clazz.getName();
        Map<String, Object> resultMap = null;
        Object cfgCache = CacheUtils.get(CFG_CACHE_ENTITY);
        if (cfgCache != null) {
            List<CfgCacheLoadEntity> cfgCacheLoadEntityList = (List<CfgCacheLoadEntity>) cfgCache;
            LOOP:
            for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                if (StringUtils.equalsIgnoreCase(className, cfgCacheLoadEntity.getCacheImplClass())) {
                    String moduleName = cfgCacheLoadEntity.getModuleName();
                    if (StringUtils.isNotEmpty(moduleName)) {
                        String[] moduleNames = StringUtils.split(moduleName, MODULE_NAME_SPLIT_FLAG);
                        for (String tempModule : moduleNames) {
                            //判断该模块能否获取数据
                            if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModule) || StringUtils.equalsIgnoreCase(sysModuleName, tempModule)) {
                                resultMap = getCacheValue(cfgCacheLoadEntity, null);
                                return resultMap;
                            }
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 根据缓存类和键获取缓存信息
     */
    public static Object get(Class clazz, Object key) throws Exception {
        String className = clazz.getName();
        Map<String, Object> resultMap = null;
        Object cfgCache = CacheUtils.get(CFG_CACHE_ENTITY);
        if (cfgCache != null) {
            List<CfgCacheLoadEntity> cfgCacheLoadEntityList = (List<CfgCacheLoadEntity>) cfgCache;
            LOOP:
            for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                if (StringUtils.equalsIgnoreCase(className, cfgCacheLoadEntity.getCacheImplClass())) {
                    String moduleName = cfgCacheLoadEntity.getModuleName();
                    if (StringUtils.isNotEmpty(moduleName)) {
                        String[] moduleNames = StringUtils.split(moduleName, MODULE_NAME_SPLIT_FLAG);
                        for (String tempModule : moduleNames) {
                            //判断该模块能否获取数据
                            if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModule) || StringUtils.equalsIgnoreCase(sysModuleName, tempModule)) {
                                resultMap = getCacheValue(cfgCacheLoadEntity, key);
                                break LOOP;
                            }
                        }
                    }
                }
            }
        }
        if (resultMap != null) {
            return resultMap.get(key);
        }
        return null;
    }


    private static Map<String, Object> getCacheValue(CfgCacheLoadEntity cfgCacheLoadEntity, Object key) throws Exception {
        Map<String, Object> resultMap = null;
        if (cfgCacheLoadEntity != null) {
            String cacheName = cfgCacheLoadEntity.getCacheName();
            if (StringUtils.equalsIgnoreCase("2", cfgCacheLoadEntity.getSaveType())) {
                if (key != null) {
                    cacheName = cacheName + "_" + key;
                } else {
                    ExceptionUtil.throwBusinessException("数据存储类型为2的无法直接获取所有的缓存数据");
                }
                Object obj = JedisUtils.getObject(cacheName);
                resultMap = new HashMap<String, Object>();
                resultMap.put(key + "", obj);
            } else {
                if (cfgCacheLoadEntity.getCacheType() == 1) {//从redis中获取
                    if (JedisUtils.existsObject(cacheName)) {
                        resultMap = (Map) JedisUtils.getObject(cacheName);
                    }
                } else if (cfgCacheLoadEntity.getCacheType() == 2) {
                    Object obj = CacheUtils.get(cacheName);
                    if (obj != null && obj instanceof Map) {
                        resultMap = (Map<String, Object>) obj;
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 重新加载缓存
     */
    public static void reloadCache(String cacheName) throws Exception {
        Object cfgCache = CacheUtils.get(CFG_CACHE_ENTITY);
        if (cfgCache != null) {
            List<CfgCacheLoadEntity> cfgCacheLoadEntityList = (List<CfgCacheLoadEntity>) cfgCache;
            for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                if (StringUtils.equalsIgnoreCase(cacheName, cfgCacheLoadEntity.getCacheName())) {
                    List<String> redisLockModules = getRedisCacheModule(true);
                    List<String> ehcacheLockModules = getEhcacheModule(true);
                    try {
                        loadCache(cfgCacheLoadEntity, redisLockModules, ehcacheLockModules, true);
                    } catch (Exception e) {
                        log.error("load " + cfgCacheLoadEntity.getCacheName() + " for module " + sysModuleName + " error " + e.getMessage(),e);
                        e.printStackTrace();
                        throw e;
                    }
                    break;
                }
            }
        }
    }

    /**
     * 判断是否正在加载Redis缓存
     */
    public static boolean isLoadRedisCache() throws Exception {
        List<String> redisLockModules = JedisUtils.getList(LOCK_CACHE_MODULE_FLAG);
        boolean isLoad = false;
        if (redisLockModules != null && redisLockModules.size() > 0) {
            for (String tempModuleName : redisLockModules) {
                //如果该模块缓存正在加载
                if (StringUtils.equals(tempModuleName, sysModuleName)) {
                    isLoad = true;
                    break;
                }
            }
        }
        return isLoad;
    }

    /**
     * 判断是否正在加载ehCache缓存
     */
    public static boolean isLoadEhcache() throws Exception {
        List<String> ehcacheLockModules = null;
        boolean isLoad = false;
        Object obj = CacheUtils.get(LOCK_CACHE_MODULE_FLAG);
        if (obj != null) {
            ehcacheLockModules = (List<String>) obj;
            if (ehcacheLockModules.size() > 0) {
                for (String tempModuleName : ehcacheLockModules) {
                    if (StringUtils.equals(tempModuleName, sysModuleName)) {
                        isLoad = true;
                        break;
                    }
                }
            }
        }
        return isLoad;
    }

    /**
     * 加载缓存类
     */
    private static void loadAllCache() throws Exception {
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);

        log.info("is_user_cache...." + isUseCache);
        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            log.info("cache start load......");
            List<String> redisLockModules = getRedisCacheModule(false);
            List<String> ehcacheLockModules = getEhcacheModule(false);
            try {

                ICfgCacheLoadSV cfgCacheLoadSV = SpringContextHolder.getBean(ICfgCacheLoadSV.class);
                List<CfgCacheLoadEntity> cfgCacheLoadEntityList = cfgCacheLoadSV.getAllCfgCacheLoadEntity();
                if (cfgCacheLoadEntityList != null && cfgCacheLoadEntityList.size() > 0) {
                    for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                        try {
                            loadCache(cfgCacheLoadEntity, redisLockModules, ehcacheLockModules, false);
                        } catch (Exception e1) {
                            log.error("load " + cfgCacheLoadEntity.getCacheName() + " for module " + sysModuleName + " error " + e1.getMessage());
                            e1.printStackTrace();
                        }
                    }
                    CacheUtils.put(CFG_CACHE_ENTITY, cfgCacheLoadEntityList);
                }
            } catch (Exception e) {
                log.error("cfgCacheLoadEntity data load error ...." + e.getMessage());
                e.printStackTrace();
            } finally {
                if (redisLockModules != null && redisLockModules.size() > 0) {
                    if (redisLockModules.size() == 1) {
                        JedisUtils.del(LOCK_CACHE_MODULE_FLAG);
                    } else {
                        redisLockModules.remove(sysModuleName);
                        JedisUtils.setList(LOCK_CACHE_MODULE_FLAG, redisLockModules, 0);
                    }
                }
                if (ehcacheLockModules != null && ehcacheLockModules.size() > 0) {
                    if (ehcacheLockModules.size() == 1) {
                        CacheUtils.remove(LOCK_CACHE_MODULE_FLAG);
                    } else {
                        ehcacheLockModules.remove(sysModuleName);
                        CacheUtils.put(LOCK_CACHE_MODULE_FLAG, ehcacheLockModules);
                    }
                }
            }

            log.info("cache end load......");
        }
    }

    /**
     * 获取redis当前正在加载缓存的模块
     */
    private static List<String> getRedisCacheModule(boolean isReload) throws Exception {
        if (!isReload) {
            if (!(StringUtils.equalsIgnoreCase("true", loadRedisCache) || StringUtils.equalsIgnoreCase("y", loadRedisCache))) {
                return null;
            }
        }
        List<String> redisLockModules = null;
        int idx = 0;
        //判断是否已处于加载状态中
        while (JedisUtils.exists(LOCK_CACHE_FLAG) && idx < 500) {
            Thread.sleep(50);
            idx++;
        }

        JedisUtils.set(LOCK_CACHE_FLAG, "Y", 600);//加锁
        redisLockModules = JedisUtils.getList(LOCK_CACHE_MODULE_FLAG);
        if (redisLockModules != null && redisLockModules.size() > 0) {
            boolean hasModule = false;
            for (String tempModuleName : redisLockModules) {
                //如果该模块缓存未进行加载
                if (StringUtils.equals(tempModuleName, sysModuleName)) {
                    hasModule = true;
                }
            }
            if (!hasModule) {
                redisLockModules.add(sysModuleName);
            }
        } else {
            redisLockModules = new ArrayList<String>();
            redisLockModules.add(sysModuleName);
        }
        JedisUtils.setList(LOCK_CACHE_MODULE_FLAG, redisLockModules, 1200);
        JedisUtils.del(LOCK_CACHE_FLAG);//解锁

        return redisLockModules;
    }

    /**
     * 获取ehcache当前正在加载缓存的模块
     */
    private static List<String> getEhcacheModule(boolean isReload) throws Exception {
        List<String> ehcacheLockModules = null;
        int idx = 500;
        while (CacheUtils.get(LOCK_CACHE_FLAG) != null && idx < 500) {
            Thread.sleep(50);
            idx++;
        }

        CacheUtils.put(LOCK_CACHE_FLAG, "Y");
        Object obj = CacheUtils.get(LOCK_CACHE_MODULE_FLAG);
        if (obj != null) {
            ehcacheLockModules = (List<String>) obj;
            if (ehcacheLockModules.size() > 0) {
                boolean hasModule = false;
                for (String tempModuleName : ehcacheLockModules) {
                    if (StringUtils.equals(tempModuleName, sysModuleName)) {
                        hasModule = true;
                    }
                }
                if (!hasModule) {
                    ehcacheLockModules.add(sysModuleName);
                }
            }
        } else {
            ehcacheLockModules = new ArrayList<String>();
            ehcacheLockModules.add(sysModuleName);
        }
        CacheUtils.put(LOCK_CACHE_MODULE_FLAG, ehcacheLockModules);
        CacheUtils.remove(LOCK_CACHE_FLAG);

        return ehcacheLockModules;
    }

    private static void loadCache(CfgCacheLoadEntity cfgCacheLoadEntity, List<String> redisLockModules, List<String> ehcacheLockModules, boolean reload) throws Exception {
        String moduleName = cfgCacheLoadEntity.getModuleName();
        String cacheName = cfgCacheLoadEntity.getCacheName();//缓存名称
        int cacheType = cfgCacheLoadEntity.getCacheType();//数据存储方式  1-redis  2-encache
        String saveType = cfgCacheLoadEntity.getSaveType();//数据存储类型
        String cacheImplClass = cfgCacheLoadEntity.getCacheImplClass();//数据获取类
        String cronExpression = cfgCacheLoadEntity.getCronExpression();//数据刷新表达式

        //判断该缓存是否可以被该模块加载
        boolean canLoadCache = canLoadCache(moduleName, cacheType, redisLockModules, ehcacheLockModules, reload);
        if (canLoadCache) {
            if (StringUtils.isNotEmpty(cacheImplClass)) {
                DefaultCache cacheImpl = (DefaultCache) Class.forName(cacheImplClass).newInstance();
                Map<String, Object> map = cacheImpl.loadCache(cacheName);
                if (map != null && map.size() > 0) {
                    //放入到缓存中
                    if (StringUtils.equals("2", saveType)) {//如果每个key都单独存储
                        Set<String> keySet = map.keySet();
                        Iterator<String> iter = keySet.iterator();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            String cacheKey = cacheName + "_" + key;
                            if (cacheType == 1) {
                                if (JedisUtils.exists(cacheKey)) {
                                    JedisUtils.delObject(cacheKey);
                                }
                                JedisUtils.setObject(cacheKey, map.get(key), 0);
                            } else {
                                if (CacheUtils.get(cacheKey) != null) {
                                    CacheUtils.remove(cacheKey);
                                }
                                CacheUtils.put(cacheKey, map.get(key));
                            }
                        }
                    } else {
                        if (cacheType == 1) {
                            if (JedisUtils.existsObject(cacheName)) {
                                JedisUtils.delObject(cacheName);
                            }
                            JedisUtils.setObject(cacheName, map, 0);
                        } else if (cacheType == 2) {
                            if (CacheUtils.get(cacheName) != null) {
                                CacheUtils.remove(cacheName);
                            }
                            CacheUtils.put(cacheName, map);
                        }
                    }

                    //如果刷新表达式不为空，则放入定时任务中进行处理
                    if (StringUtils.isNotEmpty(cronExpression)) {
                        QuartzJobFactory.addJob(999999, CacheLoadJob.FLAG + cacheName, CacheLoadJob.FLAG + cacheName, cronExpression, CommConstants.State.STATE_ABNORMAL, CacheLoadJob.class);
                        if (!QuartzJobFactory.isSchedulerStarted()) {
                            QuartzJobFactory.startScheduler();
                        }
                    }
                }
            } else {
                ExceptionUtil.throwBusinessException("加载类未配置");
            }
        }
    }

    /**
     * 判断当前模块是否可加载该缓存
     */
    private static boolean canLoadCache(String moduleName, int cacheType, List<String> redisLockModules, List<String> ehcacheLockModules, boolean reload) throws Exception {
        boolean canLoad = false;
        if (!reload) {
            if (cacheType == 1 && !(StringUtils.equalsIgnoreCase("true", loadRedisCache) || StringUtils.equalsIgnoreCase("y", loadRedisCache))) {
                return false;
            }
        }

        String[] moduleNames = StringUtils.split(moduleName, MODULE_NAME_SPLIT_FLAG);
        if (moduleNames != null && moduleNames.length > 0) {
            for (String tempModuleName : moduleNames) {
                if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModuleName)) {//所有模块都可进行加载，如果当前有一个模块加载即可
                    if (cacheType == 1 && redisLockModules != null && redisLockModules.size() == 1) {
                        canLoad = true;
                    } else if (cacheType == 2 && ehcacheLockModules != null && ehcacheLockModules.size() == 1) {
                        canLoad = true;
                    }
                    break;
                } else {
                    //判断是否有当前模块配置
                    if (StringUtils.equalsIgnoreCase(sysModuleName, tempModuleName)) {
                        //判断当前是否已经有其他模块正在加载，如果存在则不进行重复加载
                        if (cacheType == 1) {
                            if (redisLockModules != null && redisLockModules.size() > 0) {
                                int idx = 0;
                                for (String temp : moduleNames) {
                                    for (String cacheModule : redisLockModules) {
                                        if (StringUtils.equalsIgnoreCase(cacheModule, temp)) {
                                            idx++;
                                        }
                                    }
                                }
                                if (idx == 1) {
                                    canLoad = true;
                                    break;
                                }
                            }
                        } else if (cacheType == 2) {
                            if (ehcacheLockModules != null && ehcacheLockModules.size() > 0) {
                                int idx = 0;
                                for (String temp : moduleNames) {
                                    for (String cacheModule : ehcacheLockModules) {
                                        if (StringUtils.equalsIgnoreCase(cacheModule, temp)) {
                                            idx++;
                                        }
                                    }
                                }
                                if (idx == 1) {
                                    canLoad = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return canLoad;
    }
    //add by wangjian13 2016/1/19
    public static boolean deleteFromCache(String cacheName,String cacheKey)throws Exception{
        boolean flag=false;
    	Object cfgCache = CacheUtils.get(CFG_CACHE_ENTITY);
        if (cfgCache != null) {
            List<CfgCacheLoadEntity> cfgCacheLoadEntityList = (List<CfgCacheLoadEntity>) cfgCache;
            LOOP:
            for (CfgCacheLoadEntity cfgCacheLoadEntity : cfgCacheLoadEntityList) {
                if (StringUtils.equalsIgnoreCase(cacheName, cfgCacheLoadEntity.getCacheName())) {
                    String moduleName = cfgCacheLoadEntity.getModuleName();
                    if (StringUtils.isNotEmpty(moduleName)) {
                        String[] moduleNames = StringUtils.split(moduleName, MODULE_NAME_SPLIT_FLAG);
                        for (String tempModule : moduleNames) {
                            //判断该模块能否删除数据
                            if (StringUtils.equalsIgnoreCase(ALL_MODULE, tempModule) || StringUtils.equalsIgnoreCase(sysModuleName, tempModule)) {
                                flag= deleteCacheValue(cfgCacheLoadEntity, cacheKey);
                                return flag;
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }
    public static boolean deleteCacheValue(CfgCacheLoadEntity cfgCacheLoadEntity, String key)throws Exception{
    	    String saveType=cfgCacheLoadEntity.getSaveType();
    	    if(saveType!=null&&"2".equals(saveType)){//如果每个key都是单独存储的
    	    	if(key!=null){
    	    		if(JedisUtils.existsObject(key)){
    	    		   JedisUtils.delObject(key);
    	    		   return true;
    	    		}
    	    	}else{
    	    		ExceptionUtil.throwBusinessException("数据存储类型为2的无法直接获取所有的缓存数据");
    	    		return false;
    	    	}
    	    }else{//不是每个key都是单独存储的
    	    	if(cfgCacheLoadEntity.getCacheType()==1){//从redis中删除
    	    		if(JedisUtils.existsObject(cfgCacheLoadEntity.getCacheName())){
    	    			Map<String,Object> map=(Map)JedisUtils.getObject(cfgCacheLoadEntity.getCacheName());
    	    		    JedisUtils.delObject(cfgCacheLoadEntity.getCacheName());
    	    		    map.remove(key);
    	    		    JedisUtils.setObject(cfgCacheLoadEntity.getCacheName(), map,0);
    	    		    return true;
    	    		}
    	    	}else{//使用cacheUtil删除
    	    		CacheUtils.remove(cfgCacheLoadEntity.getCacheName(), key);
    	    		return true;
    	    	}
    	    }
    	    return false;
    }
}
