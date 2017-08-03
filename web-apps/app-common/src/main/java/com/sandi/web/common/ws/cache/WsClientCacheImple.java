package com.sandi.web.common.ws.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.ws.entity.CfgWsClientEntity;
import com.sandi.web.common.ws.service.interfaces.IWsSV;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LIUQ on 2015/7/17.
 */
public class WsClientCacheImple extends DefaultCache {

    @Override
    protected Map getData() throws Exception {
        Map<String, CfgWsClientEntity> map = new HashMap<String, CfgWsClientEntity>();
        IWsSV wsSV = SpringContextHolder.getBean(IWsSV.class);
        CfgWsClientEntity[] cfgWsClientBeans = wsSV.getAllCfgWsClient();
        if (cfgWsClientBeans != null && cfgWsClientBeans.length > 0) {
            for (int i = 0; i < cfgWsClientBeans.length; i++)
                map.put(cfgWsClientBeans[i].getCfgWsClientCode(), cfgWsClientBeans[i]);
        }
        return map;
    }
}
