package com.sandi.web.common.his.service.interfaces;

import com.sandi.web.common.his.entity.CfgHisTableConfigEntity;
import java.util.List;

/**
 * Created by dizl on 2015/7/17.
 */
public interface ICfgHisTableConfigSV {
    /**
     * 查询所有数据
     */
    public List<CfgHisTableConfigEntity> getAllEntity() throws Exception;

    /**
     * 根据表名查询数据
     */
    public CfgHisTableConfigEntity getByTableName(String tableName) throws Exception;
}
