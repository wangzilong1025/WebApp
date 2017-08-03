package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncFramePageEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(CfgDyncFramePageEntity.class)
public interface ICfgDyncFramePageDao extends CrudDao<CfgDyncFramePageEntity,Long> {
}