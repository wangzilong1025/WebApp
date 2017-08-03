package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgEventRelEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgEventRelEntity.class)
public interface ICfgEventRelDao extends CrudDao<CfgEventRelEntity,Long> {
}