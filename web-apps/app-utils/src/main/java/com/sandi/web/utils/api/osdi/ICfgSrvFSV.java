/**
 * $Id: ICfgSrvFSV.java,v 1.0 2015/7/17 9:55 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.osdi;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import java.util.List;

/**
 * @author zhangrp
 * @version $Id: ICfgSrvFSV.java,v 1.1 2015/7/17 9:55 zhangrp Exp $
 *          Created on 2015/7/17 9:55
 */
public interface ICfgSrvFSV {

    public CfgSrvBase getServer(String serverId);

    public List<CfgSrvBase> getAllServer();

    public String save(CfgSrvBase cfgSrvBase);

    public String update(CfgSrvBase cfgSrvBase);

    public String delete(String serverId);
}