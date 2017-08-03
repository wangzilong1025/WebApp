package com.sandi.web.common.elec.dao;

import com.sandi.web.common.elec.entity.CfgElecEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgElecEntity.class)
public interface ICfgElecDao extends CrudDao<CfgElecEntity, Long> {
}