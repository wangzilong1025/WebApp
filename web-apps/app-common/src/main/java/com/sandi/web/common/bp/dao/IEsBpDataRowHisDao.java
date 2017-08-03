package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.EsBpDataRowHisEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(EsBpDataRowHisEntity.class)
public interface IEsBpDataRowHisDao extends CrudDao<EsBpDataRowHisEntity, Long> {
}