/**
 * $Id: CfgElecCache.java,v 1.0 16/9/12 上午10:49 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.elec.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.elec.entity.CfgElecEntity;
import com.sandi.web.common.elec.service.interfaces.ICfgElecSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.elec.entity.CfgElec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangruiping
 * @version $Id: CfgElecCache.java,v 1.1 16/9/12 上午10:49 zhangruiping Exp $
 *          Created on 16/9/12 上午10:49
 */
public class CfgElecCache extends DefaultCache {

    @Override
    protected Map getData() throws Exception {
        HashMap<String, CfgElec> retMap = new HashMap<String, CfgElec>();

        ICfgElecSV cfgElecSV = SpringContextHolder.getBean(ICfgElecSV.class);
        List<CfgElecEntity> list = cfgElecSV.queryCfgElecEntity(new CfgElecEntity());
        if (list != null && list.size() > 0) {
            for (CfgElecEntity cfgElecEntity : list) {
                CfgElec cfgElec = JsonUtil.json2Object(JsonUtil.object2Json(cfgElecEntity), CfgElec.class);
                retMap.put("" + cfgElec.getFileTypeId(), cfgElec);
            }
        }
        return retMap;
    }
}