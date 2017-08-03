package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgTemplateEntity;

import java.util.List;

public interface ICfgMsgTemplateSV{
    /**
     * 根据模板编号获取模板配置数据
     * */
    public List<CfgMsgTemplateEntity> getEntityByMsgId(Long cfgMsgId) throws Exception;

    /**
     * 获取所有模板
     * */
    public CfgMsgTemplateEntity getEntityByMsgIdAndMsgType(Long cfgMsgId, String msgType) throws Exception;
}