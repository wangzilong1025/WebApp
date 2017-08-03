package com.sandi.web.common.mess.service.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2017/2/13.
 */
public interface IMsgSendObjCollectSV {

    /**
     * 提取接受消息对象数据
     * */
    public List<Map> doCollect(Map inputParam) throws Exception;
}
