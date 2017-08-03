package com.sandi.web.common.mess.dao;


import com.sandi.web.common.mess.entity.CfgMsgInfoEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgMsgInfoEntity.class)
public interface ICfgMsgInfoDao extends CrudDao<CfgMsgInfoEntity,Long> {
}