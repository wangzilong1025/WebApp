package com.sandi.web.common.dync.dao;


import com.sandi.web.common.dync.entity.CfgDyncButtonEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import java.util.List;

@Dao(CfgDyncButtonEntity.class)
public interface ICfgDyncButtonDao extends CrudDao<CfgDyncButtonEntity,Long> {
    //查询按钮组包含的按钮
    public List<CfgDyncButtonEntity> getButtonByButtonsetId(long buttonsetId);
}