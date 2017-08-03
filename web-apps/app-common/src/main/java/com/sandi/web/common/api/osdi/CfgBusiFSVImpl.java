/**
 * $Id: CfgBusiFSVImpl.java,v 1.0 2017/1/16 10:10 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.osdi;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.osdi.service.interfaces.ICfgBusiBaseSV;
import com.sandi.web.utils.api.osdi.ICfgBusiFSV;
import com.sandi.web.utils.http.entity.CfgBusiBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijie
 * @version $Id: CfgBusiFSVImpl.java,v 1.1 2017/1/16 10:10 lijie Exp $
 * Created on 2017/1/16 10:10
 */
@Service
public class CfgBusiFSVImpl implements ICfgBusiFSV {

    private static final Logger logger = LoggerFactory.getLogger(CfgBusiFSVImpl.class);

    @Autowired
    private ICfgBusiBaseSV cfgBusiBaseSV;

    @Override
    public CfgBusiBase getBusi(String busiId) {
        CfgBusiBase cfgBusiBase = null;
        try {
            cfgBusiBase = cfgBusiBaseSV.getBusiBase(busiId);
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return cfgBusiBase;
    }

    @Override
    public List<CfgBusiBase> getAllBusi() {
        List<CfgBusiBase> cfgBusiBases = new ArrayList<CfgBusiBase>();
        try {
            //TODO
        } catch (Exception e) {
            logger.error("调用失败！", e);
        }
        return cfgBusiBases;
    }
}
