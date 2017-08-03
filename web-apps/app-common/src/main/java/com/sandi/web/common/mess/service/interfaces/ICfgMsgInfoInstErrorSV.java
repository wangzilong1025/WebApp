package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgInfoInstErrorEntity;

import java.util.List;

public interface ICfgMsgInfoInstErrorSV{
    /**
     * 保存数据
     * */
    public void saveEntity(List<CfgMsgInfoInstErrorEntity> msgInfoInstErrorEntityList) throws Exception;
}