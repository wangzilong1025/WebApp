package com.sandi.web.common.staticdata;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.staticdata.cache.CfgStaticDataCache;
import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;
import com.sandi.web.common.staticdata.service.interfaces.ICfgStaticDataSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/4.
 * 静态数据处理类
 */
public class StaticDataFactory {
    private static final Logger log = Logger.getLogger(StaticDataFactory.class);
    private static Map<String, List<CfgStaticDataEntity>> cfgStaticDataCache = new HashMap<String, List<CfgStaticDataEntity>>();

    /**
     * 获取静态数据
     */
    public static List<CfgStaticDataEntity> getCfgStaticData(String codeType) throws Exception {
        //判断是否使用缓存，如果使用则从缓存中获取数据
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);

        List<CfgStaticDataEntity> cfgStaticDataEntityList = new ArrayList<CfgStaticDataEntity>();
        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            Object obj = CacheFactory.get(CfgStaticDataCache.CACHE_NAME, codeType);
            if (obj != null) {
                cfgStaticDataEntityList = (List<CfgStaticDataEntity>) obj;
            }
        }

        if (cfgStaticDataEntityList == null || cfgStaticDataEntityList.size() <= 0) {
            //如果没有使用缓存，则利用本地缓存进行获取
            if (cfgStaticDataCache.containsKey(codeType)) {
                cfgStaticDataEntityList = cfgStaticDataCache.get(codeType);
            } else {
                ICfgStaticDataSV cfgStaticDataSV = SpringContextHolder.getBean(ICfgStaticDataSV.class);
                cfgStaticDataEntityList = cfgStaticDataSV.getCfgStaticDataByCodeType(codeType);
                cfgStaticDataCache.put(codeType, cfgStaticDataEntityList);
            }
        }
        return cfgStaticDataEntityList;
    }

    public static CfgStaticDataEntity getCfgStaticDataByCon(String codeType, String codeValue) throws Exception {
        //判断是否使用缓存，如果使用则从缓存中获取数据
        String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);
        CfgStaticDataEntity cfgStaticDataEntity = new CfgStaticDataEntity();
        if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
            //修改成从reids中读取数据
            Object obj = CacheFactory.get(CfgStaticDataCache.CACHE_NAME, codeType);
            if (obj != null) {
                List<CfgStaticDataEntity> cfgStaticDataEntityList = (List<CfgStaticDataEntity>) obj;
                for (CfgStaticDataEntity temp : cfgStaticDataEntityList) {
                    if (StringUtils.equals(temp.getCodeValue(), codeValue)) {
                        cfgStaticDataEntity = temp;
                    }
                }
            }
        } else {
            //如果没有使用缓存，则查询数据库
            ICfgStaticDataSV cfgStaticDataSV = SpringContextHolder.getBean(ICfgStaticDataSV.class);
            cfgStaticDataEntity = cfgStaticDataSV.getCfgStaticDataByCon(codeType, codeValue);
        }
        return cfgStaticDataEntity;
    }
}