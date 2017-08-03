package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;

import java.util.List;

public interface ICfgMsgInfoInstHisSV{
    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgMsgInfoInstHisEntity> msgInfoInstHisEntityList) throws Exception;

    /**
     * 保存数据
     * */
    public void saveEntity(CfgMsgInfoInstHisEntity entity) throws Exception;
}