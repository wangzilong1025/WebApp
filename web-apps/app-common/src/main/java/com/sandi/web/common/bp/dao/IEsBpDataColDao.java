package com.sandi.web.common.bp.dao;

import com.sandi.web.common.bp.entity.EsBpDataColEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;

@Dao(EsBpDataColEntity.class)
public interface IEsBpDataColDao extends CrudDao<EsBpDataColEntity, Long> {
}