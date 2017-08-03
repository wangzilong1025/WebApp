package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncAttrEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.apache.ibatis.annotations.Param;

@Dao(CfgDyncAttrEntity.class)
public interface ICfgDyncAttrDao extends CrudDao<CfgDyncAttrEntity, Long> {
    public int deleteByPageId(@Param("pageId") Long pageId);
}