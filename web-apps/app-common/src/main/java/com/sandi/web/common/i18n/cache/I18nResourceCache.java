package com.sandi.web.common.i18n.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.i18n.entity.CfgI18nResourceEntity;
import com.sandi.web.common.i18n.service.interfaces.ICfgI18nResourceSV;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/10.
 */
public class I18nResourceCache extends DefaultCache {
    public static final String CACHE_NAME = "I18nResourceCache";

    @SuppressWarnings("unchecked")
    @Override
    protected Map getData() throws Exception {
        HashMap retMap = new HashMap();
        ICfgI18nResourceSV cfgI18nResourceSV = SpringContextHolder.getBean(ICfgI18nResourceSV.class);
        List<CfgI18nResourceEntity> cfgI18nResourceEntities = cfgI18nResourceSV.getAllCfgI18nResouce();
        for (CfgI18nResourceEntity entity : cfgI18nResourceEntities) {
            retMap.put(entity.getResKey(), entity);
        }
        return retMap;
    }
}
