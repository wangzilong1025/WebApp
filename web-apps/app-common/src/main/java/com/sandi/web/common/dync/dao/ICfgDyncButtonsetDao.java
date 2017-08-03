package com.sandi.web.common.dync.dao;

import com.sandi.web.common.dync.entity.CfgDyncButtonsetEntity;
import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.persistence.entity.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Dao(CfgDyncButtonsetEntity.class)
public interface ICfgDyncButtonsetDao extends CrudDao<CfgDyncButtonsetEntity,Long> {
    public List<Map<String, Object>> queryCfgButtonsetData(@Param("param") Map<String, Object> param, @Param("page") Page page);
}