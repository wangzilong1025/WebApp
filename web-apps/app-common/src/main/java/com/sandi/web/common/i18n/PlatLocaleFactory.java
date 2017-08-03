package com.sandi.web.common.i18n;

import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.i18n.entity.CfgI18nResourceEntity;
import com.sandi.web.common.i18n.service.interfaces.ICfgI18nResourceSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.config.Global;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dizl on 2015/6/3.
 * 国际化类
 */
public class PlatLocaleFactory {
    private static Map<String, CfgI18nResourceEntity> cfgI18nResourceEntityCache = new HashMap<String, CfgI18nResourceEntity>();

    public static String getResource(String key, Object[] args) {
        String value = getResource(key);

        if ((args != null) && (args.length > 0)) {
            for (int i = 0; i < args.length; ++i) {
                if (args[i] != null) {
                    value = StringUtils.replaceOnce(value, "{" + i + "}", args[i].toString());
                } else {
                    value = StringUtils.replaceOnce(value, "{" + i + "}", "<null>");
                }
            }
        }
        return value;
    }

    /**
     * 获取资源
     */
    public static String getResource(String key) {
        String rtn = null;
        try {
            //判断是否使用缓存，如果使用则从缓存中获取数据
            String isUseCache = Global.getConfig(CommConstants.Config.IS_USE_CACHE);
            CfgI18nResourceEntity cfgI18nResourceEntity = null;

            if (StringUtils.isNotEmpty(isUseCache) && (StringUtils.equalsIgnoreCase(isUseCache.trim(), "true") || StringUtils.equalsIgnoreCase(isUseCache.trim(), "y"))) {
                Object obj = CacheFactory.get(com.sandi.web.common.i18n.cache.I18nResourceCache.class, key);
                if (obj != null) {
                    cfgI18nResourceEntity = (CfgI18nResourceEntity) obj;
                    rtn = cfgI18nResourceEntity.getZhCn();
                }
            } else {
                if (cfgI18nResourceEntityCache.containsKey(key)) {
                    cfgI18nResourceEntity = cfgI18nResourceEntityCache.get(key);
                    rtn = cfgI18nResourceEntity.getZhCn();
                } else {
                    ICfgI18nResourceSV cfgI18nResourceSV = SpringContextHolder.getBean(ICfgI18nResourceSV.class);
                    cfgI18nResourceEntity = cfgI18nResourceSV.getCfgI18nResouce(key);
                    rtn = cfgI18nResourceEntity.getZhCn();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("key:" + key + ",resource found exception", ex);
        }

        if (rtn == null) {
            throw new RuntimeException("key:" + key + ",resource not found");
        }

        return rtn;
    }

    public static String getResource(String key, String arg1) {
        return getResource(key, new String[]{arg1});
    }

    public static String getResource(String key, String arg1, String arg2) {
        return getResource(key, new String[]{arg1, arg2});
    }

    public static String getResource(String key, String arg1, String arg2, String arg3) {
        return getResource(key, new String[]{arg1, arg2, arg3});
    }

}
