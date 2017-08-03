package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.EsBpDataColHisEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(EsBpDataColHisEntity.class)
public interface IEsBpDataColHisDao extends CrudDao<EsBpDataColHisEntity, Long> {
}