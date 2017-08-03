package com.sandi.web.common.process.dao;


import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.process.entity.CfgProcessInsEntity;

@Dao(CfgProcessInsEntity.class)
public interface ICfgProcessInsDao extends CrudDao<CfgProcessInsEntity,Long> {
}