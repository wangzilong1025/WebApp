package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgCollectResultEntity;

import java.util.List;

public interface ICfgMsgCollectResultSV{
    /**
     *根据消息编号获取消息内容提取结果
    */
    public List<CfgMsgCollectResultEntity> getCollectResultByBusiMsgId(Long msgBusiId) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgMsgCollectResultEntity entity) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgMsgCollectResultEntity> entityList) throws Exception;

    /**
     * 删除数据
     * */
    public void deleteEntity(Long resultId) throws Exception;

    /**
     * 删除数据
     * */
    public void deleteEntity(CfgMsgCollectResultEntity entity) throws Exception;

    /**
     * 删除数据
     * */
    public void deleteEntity(List<CfgMsgCollectResultEntity> entityList) throws Exception;
}