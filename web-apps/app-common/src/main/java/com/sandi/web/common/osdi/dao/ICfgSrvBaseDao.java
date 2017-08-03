/**
 * $Id: ICfgSrvBaseDao.java,v 1.0 2015/7/16 14:21 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgSrvBaseEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

/**
 * @author zhangrp
 * @version $Id: ICfgSrvBaseDao.java,v 1.1 2015/7/16 14:21 zhangrp Exp $
 *          Created on 2015/7/16 14:21
 */
@Dao(CfgSrvBaseEntity.class)
public interface ICfgSrvBaseDao extends CrudDao<CfgSrvBaseEntity, String> {

}