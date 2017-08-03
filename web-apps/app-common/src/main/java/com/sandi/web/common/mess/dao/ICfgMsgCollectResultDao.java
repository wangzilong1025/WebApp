package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgCollectResultEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgCollectResultEntity.class)
public interface ICfgMsgCollectResultDao extends CrudDao<CfgMsgCollectResultEntity,Long> {
}