package com.sandi.web.common.mess.service.interfaces;


import com.sandi.web.common.mess.entity.CfgMsgInfoEntity;

import java.util.List;

public interface ICfgMsgInfoSV{
    /**
     * 根据发送级别获取满足条件的数据
     * */
    public List<CfgMsgInfoEntity> getCfgMsgInfoBySendLevel(String sendLevel) throws Exception;
}