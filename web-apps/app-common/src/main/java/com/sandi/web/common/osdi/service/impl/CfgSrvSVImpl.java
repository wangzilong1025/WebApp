/**
 * $Id: CfgSrvSVImpl.java,v 1.0 2015/7/17 10:02 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.service.impl;

import java.util.List;

import com.sandi.web.common.osdi.dao.ICfgSrvBaseDao;
import com.sandi.web.common.osdi.entity.CfgSrvBaseEntity;
import com.sandi.web.common.osdi.service.interfaces.ICfgSrvSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhangrp
 * @version $Id: CfgSrvSVImpl.java,v 1.1 2015/7/17 10:02 zhangrp Exp $
 *          Created on 2015/7/17 10:02
 */
@Service
public class CfgSrvSVImpl implements ICfgSrvSV {

    @Autowired
    private ICfgSrvBaseDao cfgSrvBeanDao;

    @Override
    @Transactional(readOnly = true)
    public CfgSrvBaseEntity getServer(String serverId) throws Exception {
        CfgSrvBaseEntity cfgSrvBase = new CfgSrvBaseEntity();
        cfgSrvBase.setSrvId(serverId);
        List<CfgSrvBaseEntity> cfgOsdiSrvBaseList = cfgSrvBeanDao.findByEntity(cfgSrvBase);

        if (cfgOsdiSrvBaseList.size() > 0) {
            return cfgOsdiSrvBaseList.get(0);
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CfgSrvBaseEntity> getAllServer() throws Exception {
        CfgSrvBaseEntity cfgSrvBase = new CfgSrvBaseEntity();
        List<CfgSrvBaseEntity> cfgSrvBaseList = cfgSrvBeanDao.findByEntity(cfgSrvBase);
        return cfgSrvBaseList;
    }

    @Override
    public void save(CfgSrvBaseEntity cfgSrvBase) throws Exception {
        CfgSrvBaseEntity cfgSrvBase_ = new CfgSrvBaseEntity();
        cfgSrvBase_.setSrvId(cfgSrvBase.getSrvId());
        List<CfgSrvBaseEntity> cfgSrvBaseList = cfgSrvBeanDao.findByEntity(cfgSrvBase_);
        if (cfgSrvBaseList.size() != 0) {
            throw new Exception("服务编码 [ " + cfgSrvBase.getSrvId() + " ] 不唯一！");
        }
        cfgSrvBase_ = new CfgSrvBaseEntity();
        cfgSrvBase_.setSrvMethod(cfgSrvBase.getSrvMethod());
        cfgSrvBase_.setSrvPackage(cfgSrvBase.getSrvPackage());
        cfgSrvBaseList = cfgSrvBeanDao.findByEntity(cfgSrvBase_);
        if (cfgSrvBaseList.size() != 0 && cfgSrvBaseList.get(0) != null) {
            throw new Exception("该服务已存在！请参考服务:" + cfgSrvBaseList.get(0).getSrvName() + "[" + cfgSrvBaseList.get(0).getSrvId() + "]。");
        }
        cfgSrvBeanDao.save(cfgSrvBase);
    }

    @Override
    public void delete(String serverId) throws Exception {
        CfgSrvBaseEntity cfgSrvBase = new CfgSrvBaseEntity();
        cfgSrvBase.setSrvId(serverId);
        cfgSrvBeanDao.deleteByEntity(cfgSrvBase);
    }

    @Override
    public void update(CfgSrvBaseEntity cfgSrvBase) throws Exception {
        cfgSrvBeanDao.updateById(cfgSrvBase);
    }

}
