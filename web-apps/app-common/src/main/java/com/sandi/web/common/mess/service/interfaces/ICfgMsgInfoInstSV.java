package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgInfoInstEntity;

import java.util.List;

public interface ICfgMsgInfoInstSV{
    /**
     * 获取待发送的短信数据
     * */
    public List<CfgMsgInfoInstEntity> getSendMsg() throws Exception;

    /**
     * 获取当前操作员web提醒数据
     * */
    public List<CfgMsgInfoInstEntity> getWebInfoList() throws Exception;

    /**
     * 获取当前操作员web提醒数量
     * */
    public int getWebInfoCount() throws Exception;

    /**
     * 获取当前操作员app提醒数据
     * */
    public List<CfgMsgInfoInstEntity> getAppInfoList() throws Exception;

    /**
     * 获取当前操作员app提醒数量
     * */
    public int getAppInfoCount() throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgMsgInfoInstEntity> entityList) throws Exception;

    /**
     * 删除数据
     * */
    public void deleteEntity(List<CfgMsgInfoInstEntity> entityList) throws Exception;

    /**
     * 读取消息
     * */
    public void readMess(long msgInfoId) throws Exception;
}