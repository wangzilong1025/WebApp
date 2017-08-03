package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.EsBpDataRowEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(EsBpDataRowEntity.class)
public interface IEsBpDataRowDao extends CrudDao<EsBpDataRowEntity, Long> {
}