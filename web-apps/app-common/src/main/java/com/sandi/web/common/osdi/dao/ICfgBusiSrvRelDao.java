package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgBusiSrvRelEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgBusiSrvRelEntity.class)
public interface ICfgBusiSrvRelDao extends CrudDao<CfgBusiSrvRelEntity,Long> {
}