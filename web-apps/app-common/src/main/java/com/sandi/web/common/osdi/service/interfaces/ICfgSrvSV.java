/**
 * $Id: ICfgSrvSV.java,v 1.0 2015/7/17 10:02 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.service.interfaces;

import com.sandi.web.common.osdi.entity.CfgSrvBaseEntity;
import java.util.List;


/**
 * @author zhangrp
 * @version $Id: ICfgSrvSV.java,v 1.1 2015/7/17 10:02 zhangrp Exp $
 *          Created on 2015/7/17 10:02
 */
public interface ICfgSrvSV {

    public CfgSrvBaseEntity getServer(String serverId) throws Exception;

    public List<CfgSrvBaseEntity> getAllServer() throws Exception;

    public void save(CfgSrvBaseEntity cfgSrvBase) throws Exception;

    public void delete(String serverId) throws Exception;

    public void update(CfgSrvBaseEntity cfgSrvBase) throws Exception;

}