package com.sandi.web.common.log.dao;


import com.sandi.web.common.log.entity.SysBusiLogEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(SysBusiLogEntity.class)
public interface ISysBusiLogDao extends CrudDao<SysBusiLogEntity, Long> {
}