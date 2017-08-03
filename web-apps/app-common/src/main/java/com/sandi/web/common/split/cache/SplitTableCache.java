package com.sandi.web.common.split.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.split.entity.CfgTableSplitEntity;
import com.sandi.web.common.split.service.interfaces.ICfgTableSplitSV;
import com.sandi.web.utils.common.SpringContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/4.
 */
public class SplitTableCache extends DefaultCache {

    public static final String CACHE_NAME = "SplitTableCache";

    @Override
    protected Map getData() throws Exception {
        Map<String, CfgTableSplitEntity> map = new HashMap<String, CfgTableSplitEntity>();

        ICfgTableSplitSV cfgTableSplitSV = SpringContextHolder.getBean(ICfgTableSplitSV.class);
        List<CfgTableSplitEntity> cfgTableSplitEntityList = cfgTableSplitSV.getAllCfgTableSplits();
        if (cfgTableSplitEntityList != null && cfgTableSplitEntityList.size() > 0) {
            for (CfgTableSplitEntity cfgTableSplitEntity : cfgTableSplitEntityList) {
                map.put(cfgTableSplitEntity.getTableName(), cfgTableSplitEntity);
            }
        }
        return map;
    }
}
