package com.sandi.web.common.mess.service.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2017/2/13.
 * 数据提取接口
 */
public interface IMsgDataCollectSV {
    /**
     * 提取待消息发送的数据
     * */
    public List<Map> doCollect(Map inputParam) throws Exception;
}
