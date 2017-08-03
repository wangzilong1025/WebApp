package com.sandi.web.common.elec.dao;

import com.sandi.web.common.elec.entity.ElecInstEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(ElecInstEntity.class)
public interface IElecInstDao extends CrudDao<ElecInstEntity, Long> {
}