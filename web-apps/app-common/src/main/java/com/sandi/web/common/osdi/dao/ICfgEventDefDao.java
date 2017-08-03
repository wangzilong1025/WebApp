package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgEventDefEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgEventDefEntity.class)
public interface ICfgEventDefDao extends CrudDao<CfgEventDefEntity,Long> {
}