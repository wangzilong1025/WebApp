package com.sandi.web.common.osdi.dao;


import com.sandi.web.common.osdi.entity.CfgSrvParamMappingEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgSrvParamMappingEntity.class)
public interface ICfgSrvParamMappingDao extends CrudDao<CfgSrvParamMappingEntity,Long> {
}