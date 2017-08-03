package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessDefineEntity;

import java.util.List;
import java.util.Map;

@Dao(CfgProcessDefineEntity.class)
public interface ICfgProcessDefineDao extends CrudDao<CfgProcessDefineEntity,Long> {
    public List<CfgProcessDefineEntity> queryProcessInfo(Map params) throws Exception;
    public List<CfgProcessDefineEntity> queryValidProcessInfo(Map params) throws Exception;
}