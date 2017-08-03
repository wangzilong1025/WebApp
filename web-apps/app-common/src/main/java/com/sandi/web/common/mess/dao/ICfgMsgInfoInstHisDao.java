package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgInfoInstHisEntity.class)
public interface ICfgMsgInfoInstHisDao extends CrudDao<CfgMsgInfoInstHisEntity,Long> {
}