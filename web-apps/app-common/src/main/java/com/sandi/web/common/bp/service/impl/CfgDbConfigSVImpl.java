package com.sandi.web.common.bp.service.impl;

import com.sandi.web.common.bp.dao.ICfgDbConfigDao;
import com.sandi.web.common.bp.entity.CfgDbConfigEntity;
import com.sandi.web.common.bp.service.interfaces.ICfgDbConfigSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CfgDbConfigSVImpl implements ICfgDbConfigSV {
    @Autowired
    private ICfgDbConfigDao dao;

    /**
     * 获取所有的配置
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<CfgDbConfigEntity> getAllCfgDbConfig() throws Exception {
        CfgDbConfigEntity cfgDbConfigEntity = new CfgDbConfigEntity();
        cfgDbConfigEntity.setState(CommConstants.State.STATE_NORMAL);
        return dao.findByEntity(cfgDbConfigEntity);
    }

    /**
     * 根据条件查询配置
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public List<CfgDbConfigEntity> getCfgDbConfigByCon(String param) throws Exception {
        CfgDbConfigEntity cfgDbConfigEntity = new CfgDbConfigEntity();
        cfgDbConfigEntity.setState(CommConstants.State.STATE_NORMAL);
        Map inMap = JsonUtil.json2Map(param);
        if (inMap.containsKey("dbConfigId")) {
            cfgDbConfigEntity.setDbConfigId(Long.parseLong((String) inMap.get("dbConfigId")));
        }
        return dao.findByEntity(cfgDbConfigEntity);
    }
}