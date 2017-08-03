package com.sandi.web.common.osdi.dao;

import com.sandi.web.common.osdi.entity.CfgBusiBaseEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.utils.http.entity.CfgBusiBase;
import com.sandi.web.utils.http.entity.CfgBusiEventRel;
import com.sandi.web.utils.http.entity.CfgBusiSrvRel;
import com.sandi.web.utils.http.entity.CfgSrvParamMapping;

import java.util.List;
import java.util.Map;

@Dao(CfgBusiBaseEntity.class)
public interface ICfgBusiBaseDao extends CrudDao<CfgBusiBaseEntity,Long> {
    List<CfgBusiBase> getBusiBaseEntity(Map param);
    List<CfgBusiSrvRel> getSrvListByBusiId(Map param);
    List<CfgBusiEventRel> getEventListByBusiId(Map param);
    List<CfgSrvParamMapping> getSrvParamMappingByBusiId(Map param);
}