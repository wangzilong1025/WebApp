package com.sandi.web.common.his.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.his.entity.CfgHisTableConfigEntity;
import com.sandi.web.common.his.service.interfaces.ICfgHisTableConfigSV;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/7/17.
 */
public class CfgHisTableConfigCache extends DefaultCache {
    public static final String CACHE_NAME = "CfgHisTableConfigCache";

    @Override
    protected Map getData() throws Exception {
        Map<String, CfgHisTableConfigEntity> map = new HashMap<String, CfgHisTableConfigEntity>();
        ICfgHisTableConfigSV cfgHisTableConfigSV = SpringContextHolder.getBean(ICfgHisTableConfigSV.class);
        List<CfgHisTableConfigEntity> cfgHisTableConfigEntities = cfgHisTableConfigSV.getAllEntity();
        if (cfgHisTableConfigEntities != null && cfgHisTableConfigEntities.size() > 0) {
            for (CfgHisTableConfigEntity cfgHisTableConfigEntity : cfgHisTableConfigEntities) {
                map.put(cfgHisTableConfigEntity.getTableName(), cfgHisTableConfigEntity);
            }
        }
        return map;
    }
}
