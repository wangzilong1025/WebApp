/**
 * $Id: CfgStaticDataFSVImpl.java,v 1.0 2016/9/6 10:46 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.staticdata;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.staticdata.StaticDataFactory;
import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;
import com.sandi.web.utils.api.staticdata.ICfgStaticDataFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: CfgStaticDataFSVImpl.java,v 1.1 2016/9/6 10:46 zhangrp Exp $
 *          Created on 2016/9/6 10:46
 */
@Service
public class CfgStaticDataFSVImpl implements ICfgStaticDataFSV {

     private static final   Logger logger = LoggerFactory.getLogger(CfgStaticDataFSVImpl.class);
    /**
     * 提供接口根据codeType调获取静态值列表
     * 传入的为具体的codeType
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public String getCfgStaticDataByCodeType(String requestJson) {
        Response response = new Response();
        String codeType="";
        try {
            Map<String,Object>   requestMap = JsonUtil.json2Map(requestJson);
            if (requestMap.containsKey("codeType") && requestMap.get("codeType")!=null) {
                 codeType = requestMap.get("codeType").toString();
            }

            //获取查询数据
            Map<String, List<CfgStaticDataEntity>> cfgStaticDataEntityMap = new HashMap<String, List<CfgStaticDataEntity>>();
            String codeTypeValue[] = codeType.split(",");
            if(codeTypeValue!=null && codeTypeValue.length>0){
                for (int i = 0; i < codeTypeValue.length; i++) {
                    List<CfgStaticDataEntity> lists = StaticDataFactory.getCfgStaticData(codeType);
                    cfgStaticDataEntityMap.put(codeTypeValue[i],lists);
                }
            }
            //将获取数据进行统一格式返回
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功！");
            response.setData(cfgStaticDataEntityMap);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }
}
