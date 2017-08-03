package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgTaskInsEntity;

import java.util.List;
import java.util.Map;

@Dao(CfgTaskInsEntity.class)
public interface ICfgTaskInsDao extends CrudDao<CfgTaskInsEntity,Long> {
    public List<CfgTaskInsEntity> getExpireTask(Map params) throws Exception;

    public List<Map> queryUserTask(Map params) throws Exception;
}