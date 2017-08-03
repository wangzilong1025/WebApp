/**
 * $Id: CfgBusiBaseUtil.java,v 1.0 2017/1/16 10:39 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.util;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.utils.api.osdi.ICfgBusiFSV;
import com.sandi.web.utils.http.entity.CfgBusiBase;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lijie
 * @version $Id: CfgBusiBaseUtil.java,v 1.1 2017/1/16 10:39 lijie Exp $
 * Created on 2017/1/16 10:39
 */
public class CfgBusiBaseUtil {

    private final static String _CFG_BUSI_BASE = "CfgBusiBaseCache_";
    /**
     * 服务配置
     */
    private static Map<String, CfgBusiBase> CFG_BUSI_BASE_MAP = new ConcurrentHashMap<String, CfgBusiBase>();
    private static final Logger logger = LoggerFactory.getLogger(CfgBusiBaseUtil.class);

    public static void setCfgBusiBaseMap(List<CfgBusiBase> cfgBusiBaseList) {
        synchronized (CFG_BUSI_BASE_MAP) {
            if (CFG_BUSI_BASE_MAP.size() == 0) {
                for (CfgBusiBase cfgBusiBase : cfgBusiBaseList) {
                    CFG_BUSI_BASE_MAP.put(cfgBusiBase.getBusiId(), cfgBusiBase);
                }
            }
        }
    }

    public static CfgBusiBase getCfgBusiBase(String busiId) {

        if (CFG_BUSI_BASE_MAP.size() == 0) {
            try {
                CfgSrvBase cfgSrvBase = new CfgSrvBase();
                cfgSrvBase.setSrvId("CfgBusi");
                cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgBusiFSV");
                ICfgBusiFSV cfgBusiFSV = (ICfgBusiFSV) DubboManage.getServer(cfgSrvBase);

                List<CfgBusiBase> cfgBusiBaseList = cfgBusiFSV.getAllBusi();
                CfgBusiBaseUtil.setCfgBusiBaseMap(cfgBusiBaseList);
            } catch (Exception e) {
                logger.warn("第一次获取服务配置数据异常!", e);
            }
        }

        CfgBusiBase retValue = CFG_BUSI_BASE_MAP.get(busiId);
        if (retValue == null) {
            synchronized (CFG_BUSI_BASE_MAP) {
                if (retValue == null) {
                    try {
                        CfgSrvBase cfgSrvBase = new CfgSrvBase();
                        cfgSrvBase.setSrvId("CfgBusi");
                        cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgBusiFSV");
                        ICfgBusiFSV cfgBusiFSV = (ICfgBusiFSV) DubboManage.getServer(cfgSrvBase);
                        CfgBusiBase busiBase = cfgBusiFSV.getBusi(busiId);
                        if (busiBase != null) {
                            CFG_BUSI_BASE_MAP.put(busiBase.getBusiId(), busiBase);
                        }
                        retValue = busiBase;
                    } catch (Exception e) {
                        retValue = null;
                        logger.error("获取业务配置数据异常!", e);
                    }
                }
            }
        }
        return retValue;
    }

}
