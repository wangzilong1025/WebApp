package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncAreaEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;

@Dao(CfgDyncAreaEntity.class)
public interface ICfgDyncAreaDao extends CrudDao<CfgDyncAreaEntity, Long> {
    public int deleteByPageId(@Param("pageId") Long pageId);
}