/**
 * $Id: CfgSrvBaseUtil.java,v 1.0 2016/8/18 16:04 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.util;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.utils.api.osdi.ICfgSrvFSV;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangrp
 * @version $Id: CfgSrvBaseUtil.java,v 1.1 2016/8/18 16:04 zhangrp Exp $
 *          Created on 2016/8/18 16:04
 */
public class CfgSrvBaseUtil {

    private final static String _CFG_SRV_BASE = "CfgSrvBaseCache_";
    /**
     * 服务配置
     */
    private static Map<String, CfgSrvBase> CFG_SRV_BASE_MAP = new ConcurrentHashMap<String, CfgSrvBase>();
    private static final Logger logger = LoggerFactory.getLogger(CfgSrvBaseUtil.class);

    public static void setCfgSrvBaseMap(List<CfgSrvBase> cfgSrvBaseList) {
        synchronized (CFG_SRV_BASE_MAP) {
            if (CFG_SRV_BASE_MAP.size() == 0) {
                for (CfgSrvBase cfgSrvBase : cfgSrvBaseList) {
                    CFG_SRV_BASE_MAP.put(cfgSrvBase.getSrvId(), cfgSrvBase);
                }
            }
        }
    }

    public static CfgSrvBase getCfgSrvBase(String serverId) {

        if (CFG_SRV_BASE_MAP.size() == 0) {
            try {
                CfgSrvBase cfgSrvBase = new CfgSrvBase();
                cfgSrvBase.setSrvId("CfgSrv");
                cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
                ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);

                List<CfgSrvBase> cfgSrvBaseList = cfgSrvFSV.getAllServer();
                CfgSrvBaseUtil.setCfgSrvBaseMap(cfgSrvBaseList);
            } catch (Exception e) {
                logger.warn("第一次获取服务配置数据异常!", e);
            }
        }

        CfgSrvBase retValue = CFG_SRV_BASE_MAP.get(serverId);
        if (retValue == null) {
            synchronized (CFG_SRV_BASE_MAP) {
                if (retValue == null) {
                    try {
                        CfgSrvBase cfgSrvBase = new CfgSrvBase();
                        cfgSrvBase.setSrvId("CfgSrv");
                        cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.osdi.ICfgSrvFSV");
                        ICfgSrvFSV cfgSrvFSV = (ICfgSrvFSV) DubboManage.getServer(cfgSrvBase);
                        CfgSrvBase srvBase = cfgSrvFSV.getServer(serverId);
                        CFG_SRV_BASE_MAP.put(srvBase.getSrvId(), srvBase);
                        retValue = srvBase;
                    } catch (Exception e) {
                        retValue = null;
                        logger.error("获取服务配置数据异常!", e);
                    }
                }
            }
        }
        return retValue;
    }

}
