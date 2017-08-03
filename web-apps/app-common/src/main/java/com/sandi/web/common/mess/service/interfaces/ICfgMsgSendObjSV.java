package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgSendObjEntity;

import java.util.List;

public interface ICfgMsgSendObjSV{
    /**
     * 根据消息编号获取发送对象配置数据
     * */
    public List<CfgMsgSendObjEntity> getSendObjEntityByBusiMsgId(Long busiMsgId) throws Exception;
}