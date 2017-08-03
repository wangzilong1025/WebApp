package com.sandi.web.common.id;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.id.cache.IdGeneratorCache;
import com.sandi.web.common.id.dao.IIdGeneratorDao;
import com.sandi.web.common.id.entity.IdGeneratorEntity;
import com.sandi.web.common.id.service.interfaces.IGeneratorConvert;
import com.sandi.web.common.id.service.interfaces.IIdGeneratorSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/4.
 */
public class IdGeneratorFactory {
    private static final Logger log = Logger.getLogger(IdGeneratorFactory.class);
    private static Map<String, IdGeneratorEntity> idGeneratorEntityCache = new HashMap<String, IdGeneratorEntity>();
    private static final String ID_CACHE_FLAG = "_ID_CACHE_FLAG_";
    private static final String ID_CACHE_LOAD_LOCK_KEY = "_ID_CACHE_LOAD_LOCK_KEY_";
    private static Object lock = new Object();

    public static long newId(String tableName) throws Exception {
        String newId = newStringId(tableName);
        if (StringUtils.isNotEmpty(newId)) {
            return Long.parseLong(newId);
        }
        return 0;
    }

    /**
     * 获取配置信息
     */
    public static String newStringId(String tableName) throws Exception {
        tableName = tableName.toUpperCase();
        String newId = null;
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);
        IdGeneratorEntity entity = null;
        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            Object obj = CacheFactory.get(IdGeneratorCache.CACHE_NAME, tableName);
            if (obj != null) {
                entity = (IdGeneratorEntity) obj;
                //启动线程加载newId
                List<IdGeneratorEntity> entitys = new ArrayList<IdGeneratorEntity>();
                entitys.add(entity);
                loadNewId(entitys);
            } else {
                ExceptionUtil.throwBusinessException("请检查cfg_id_generator表中是否配置" + tableName + "的数据");
            }
            newId = JedisUtils.listPop(ID_CACHE_FLAG + tableName);
        } else {
            log.info("未使用缓存，根据表名从数据库中查询分表数据");
            IIdGeneratorSV idGeneratorSV = SpringContextHolder.getBean(IIdGeneratorSV.class);
            if (idGeneratorEntityCache.containsKey(tableName)) {
                entity = idGeneratorEntityCache.get(tableName);
            } else {
                entity = idGeneratorSV.getIdGeneratorEntityByTableName(tableName);
                if (entity == null) {
                    ExceptionUtil.throwBusinessException("请检查cfg_id_generator表中是否配置" + tableName + "的数据");
                }
                idGeneratorEntityCache.put(tableName, entity);
            }
            long newValue;
            if (entity.getGeneratorType() == 1) {
                newValue = entity.getCurrValue() + entity.getStepBy();
            } else {
                //sequence生成
                IIdGeneratorDao idGeneratorDao = SpringContextHolder.getBean(IIdGeneratorDao.class);
                newValue = idGeneratorDao.getNewId(entity.getSequenceName());
            }
            if (newValue > entity.getMaxValue()) {
                if (StringUtils.equalsIgnoreCase(CommConstants.State.Y, entity.getCycleFlag())) {
                    newValue = entity.getMinValue();
                } else {
                    ExceptionUtil.throwBusinessException("主键已超过设置的最大值");
                }
            }
            newId = newValue + "";
            if (StringUtils.isNotEmpty(entity.getGeneratorClass())) {
                IGeneratorConvert convert = (IGeneratorConvert) Class.forName(entity.getGeneratorClass()).newInstance();
                newId = convert.convert(newValue);
            }
            entity.setCurrValue(newValue);
            idGeneratorSV.updateEntity(entity);//将最新之保存到数据库中
        }
        return newId;
    }

    /**
     * 启动一个线程加载新的id到缓存中
     */
    public static void loadNewId(final List<IdGeneratorEntity> entitys) throws Exception {
        if (entitys != null && entitys.size() > 0) {
            synchronized (lock) {
                try {
                    for (IdGeneratorEntity tempEntity : entitys) {
                        String jedisKey = ID_CACHE_FLAG + tempEntity.getTableName();
                        String jedisLockKey = ID_CACHE_LOAD_LOCK_KEY + tempEntity.getTableName();
                        List<String> newIdList = new ArrayList<String>();
                        //判断当前缓存中的数量是否已到阀值
                        long cacheListSize = JedisUtils.listSize(jedisKey);
                        //超过阀值则进行加载
                        if (cacheListSize <= 0 || cacheListSize < tempEntity.getCacheSize() / 4) {
                            //判断当前是否已经有程序进行处理了
                            System.out.println(JedisUtils.get(jedisLockKey));
                            if (!JedisUtils.exists(jedisLockKey)) {
                                //放入redis中，避免其他程序会处理
                                JedisUtils.set(jedisLockKey, "Y", 200);

                                IIdGeneratorSV idGeneratorSV = SpringContextHolder.getBean(IIdGeneratorSV.class);
                                IdGeneratorEntity entity = idGeneratorSV.getIdGeneratorEntityByTableName(tempEntity.getTableName());
                                if (entity.getGeneratorType() == 1) {//系统生成
                                    long currValue = entity.getCurrValue();
                                    for (int i = 0; i < (tempEntity.getCacheSize() - cacheListSize) * 0.8; i++) {
                                        currValue = currValue + entity.getStepBy();
                                        if (currValue > tempEntity.getMaxValue()) {
                                            if (StringUtils.equalsIgnoreCase(CommConstants.State.Y, tempEntity.getCycleFlag())) {
                                                currValue = entity.getMinValue();
                                            } else {
                                                ExceptionUtil.throwBusinessException("主键已超过设置的最大值");
                                            }
                                        }
                                        String newId = currValue + "";
                                        if (StringUtils.isNotEmpty(tempEntity.getGeneratorClass())) {
                                            IGeneratorConvert convert = (IGeneratorConvert) Class.forName(tempEntity.getGeneratorClass()).newInstance();
                                            newId = convert.convert(currValue);
                                        }
                                        newIdList.add(newId);
                                    }
                                    entity.setCurrValue(currValue);
                                    idGeneratorSV.updateEntity(entity);//将最新值保存到数据库中
                                } else {//sequence生成
                                    long currValue = entity.getCurrValue();
                                    IIdGeneratorDao idGeneratorDao = SpringContextHolder.getBean(IIdGeneratorDao.class);
                                    if (entity.getSequenceName() == null) {
                                        ExceptionUtil.throwBusinessException("序列名不能为空");
                                    }
                                    for (int i = 0; i < (tempEntity.getCacheSize() - cacheListSize) * 0.8; i++) {
                                        currValue = idGeneratorDao.getNewId(entity.getSequenceName());
                                        if (currValue > tempEntity.getMaxValue()) {
                                            if (StringUtils.equalsIgnoreCase(CommConstants.State.Y, tempEntity.getCycleFlag())) {
                                                currValue = entity.getMinValue();
                                            } else {
                                                ExceptionUtil.throwBusinessException("主键已超过设置的最大值");
                                            }
                                        }
                                        String newId = currValue + "";
                                        if (StringUtils.isNotEmpty(tempEntity.getGeneratorClass())) {
                                            IGeneratorConvert convert = (IGeneratorConvert) Class.forName(tempEntity.getGeneratorClass()).newInstance();
                                            newId = convert.convert(currValue);
                                        }
                                        newIdList.add(newId);
                                    }
                                    entity.setCurrValue(currValue);
                                    idGeneratorSV.updateEntity(entity);
                                }
                                JedisUtils.listAdd(jedisKey, newIdList.toArray(new String[0]));
                                JedisUtils.del(jedisLockKey);//处理完成后从redis中删除
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("error:主键加载错误" + e.getMessage(),e);
                }
            }
        }
    }
}
