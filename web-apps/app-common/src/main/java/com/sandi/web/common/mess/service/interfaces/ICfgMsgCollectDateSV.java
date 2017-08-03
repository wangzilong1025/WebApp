package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgCollectDateEntity;

public interface ICfgMsgCollectDateSV{
    /**
     * 根据消息编号获取消息最后发送的日期
     * */
    public CfgMsgCollectDateEntity getCfgMsgCollectDateEntity(Long busiMsgId) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgMsgCollectDateEntity entity) throws Exception;

    /**
     * 修改数据
     * */
    public void updateEntity(CfgMsgCollectDateEntity entity) throws Exception;
}