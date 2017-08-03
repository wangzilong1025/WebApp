package com.sandi.web.common.id.service.interfaces;

import com.sandi.web.common.id.entity.IdGeneratorEntity;
import java.util.List;

/**
 * Created by dizl on 2015/6/4.
 */
public interface IIdGeneratorSV {

    /**
     * 获取所有的主键配置数据
     */
    public List<IdGeneratorEntity> getAllIdGeneratorEntity() throws Exception;

    /**
     * 根据主键获取数据
     */
    public IdGeneratorEntity getIdGeneratorEntityByTableName(String tableName) throws Exception;

    /**
     * 保存数据
     */
    public void updateEntity(IdGeneratorEntity entity) throws Exception;
}
