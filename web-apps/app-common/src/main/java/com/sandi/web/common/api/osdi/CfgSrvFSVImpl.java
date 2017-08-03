/**
 * $Id: CfgSrvFSVImpl.java,v 1.0 2016/8/19 17:54 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.osdi;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.osdi.entity.CfgSrvBaseEntity;
import com.sandi.web.common.osdi.service.interfaces.ICfgSrvSV;
import com.sandi.web.utils.api.osdi.ICfgSrvFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrp
 * @version $Id: CfgSrvFSVImpl.java,v 1.1 2016/8/19 17:54 zhangrp Exp $
 *          Created on 2016/8/19 17:54
 */
@Service
public class CfgSrvFSVImpl implements ICfgSrvFSV {

    private static final Logger logger = LoggerFactory.getLogger(CfgSrvFSVImpl.class);

    @Autowired
    private ICfgSrvSV cfgSrvSV;

    @Override
    public CfgSrvBase getServer(String serverId) {
        CfgSrvBase cfgSrvBase = new CfgSrvBase();
        try {
            logger.info("入参：" + serverId);
            CfgSrvBaseEntity cfgSrvBaseEntity = cfgSrvSV.getServer(serverId);
            cfgSrvBase = JsonUtil.json2Object(JsonUtil.object2Json(cfgSrvBaseEntity), CfgSrvBase.class);
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return cfgSrvBase;
    }

    @Override
    public List<CfgSrvBase> getAllServer() {
        List<CfgSrvBase> cfgSrvBaseList = new ArrayList<CfgSrvBase>();
        try {
            List<CfgSrvBaseEntity> cfgSrvBaseEntityList = cfgSrvSV.getAllServer();
            for (CfgSrvBaseEntity cfgSrvBaseEntity : cfgSrvBaseEntityList) {
                CfgSrvBase cfgSrvBase = JsonUtil.json2Object(JsonUtil.object2Json(cfgSrvBaseEntity), CfgSrvBase.class);
                cfgSrvBaseList.add(cfgSrvBase);
            }
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return cfgSrvBaseList;
    }

    @Override
    public String save(CfgSrvBase cfgSrvBase) {
        String re = "ERROR";
        try {
            cfgSrvSV.save(JsonUtil.json2Object(JsonUtil.object2Json(cfgSrvBase), CfgSrvBaseEntity.class));
            re = "SUCCESS";
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return re;
    }

    @Override
    public String delete(String serverId) {
        String re = "ERROR";
        try {
            cfgSrvSV.delete(serverId);
            re = "SUCCESS";
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return re;
    }

    @Override
    public String update(CfgSrvBase cfgSrvBase) {
        String re = "ERROR";
        try {
            cfgSrvSV.update(JsonUtil.json2Object(JsonUtil.object2Json(cfgSrvBase), CfgSrvBaseEntity.class));
            re = "SUCCESS";
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return re;
    }
}
