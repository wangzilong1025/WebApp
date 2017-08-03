package com.sandi.web.common.bp.service.interfaces;

import com.sandi.web.common.bp.entity.CfgBpTemplateEntity;
import java.util.List;

public interface ICfgBpTemplateSV {

    /**
     * 查询BP配置数据
     *
     * @param cfgBpTemplateEntity
     * @return
     * @throws Exception
     */
    public List<CfgBpTemplateEntity> queryCfgBpTemplate(CfgBpTemplateEntity cfgBpTemplateEntity) throws Exception;
}