package com.sandi.web.common.his;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.his.cache.CfgHisTableConfigCache;
import com.sandi.web.common.his.entity.CfgHisTableConfigEntity;
import com.sandi.web.common.his.service.interfaces.ICfgHisTableConfigSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dizl on 2015/6/24.
 */
public class HisEntityFactory {
    private static final Logger log = Logger.getLogger(HisEntityFactory.class);
    private static Map<String, CfgHisTableConfigEntity> cfgHisTableConfigEntityCache = new HashMap<String, CfgHisTableConfigEntity>();

    /**
     * 获取历史表配置数据
     */
    public static CfgHisTableConfigEntity getHisTableConfigEntity(String tableName) throws Exception {
        //判断是否使用缓存，如果使用则从缓存中获取数据
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);
        CfgHisTableConfigEntity cfgHisTableConfigEntity = null;

        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            Object obj = CacheFactory.get(CfgHisTableConfigCache.CACHE_NAME, tableName);
            if (obj != null) {
                cfgHisTableConfigEntity = (CfgHisTableConfigEntity) obj;
            }
        } else {
            //如果没有使用缓存，则利用本地缓存进行获取
            if (cfgHisTableConfigEntityCache.containsKey(tableName)) {
                cfgHisTableConfigEntity = cfgHisTableConfigEntityCache.get(tableName);
            } else {
                ICfgHisTableConfigSV cfgHisTableConfigSV = SpringContextHolder.getBean(ICfgHisTableConfigSV.class);
                cfgHisTableConfigEntity = cfgHisTableConfigSV.getByTableName(tableName);
                cfgHisTableConfigEntityCache.put(tableName, cfgHisTableConfigEntity);
            }
        }
        return cfgHisTableConfigEntity;
    }
}
