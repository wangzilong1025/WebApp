/**
 * $Id: ParamMappingEvent.java,v 1.0 2017/1/19 13:53 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.http.event;

import com.sandi.web.utils.api.osdi.IEvent;
import com.sandi.web.utils.http.entity.CfgBusiBase;
import com.sandi.web.utils.http.entity.CfgSrvParamMapping;
import com.sandi.web.utils.http.entity.SerParameter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijie
 * @version $Id: ParamMappingEvent.java,v 1.1 2017/1/19 13:53 lijie Exp $
 * Created on 2017/1/19 13:53
 */
public class ParamTransEvent implements IEvent {

    @Override
    public void init() throws Exception {

    }

    @Override
    public SerParameter doEvent(SerParameter serParameter, CfgBusiBase cfgBusiBase) {
        Object data = this.getData(serParameter);
        if (data != null && data instanceof Map) {
            Map dataMap = (Map) data;
            this.doMap(dataMap, serParameter, cfgBusiBase);
        } else if (data != null && data instanceof List) {
            //TODO
            //暂时只取第一个
            Map dataMap = (Map)((List) data).get(0);
            this.doMap(dataMap, serParameter, cfgBusiBase);
        }
        return serParameter;
    }

    private Object getData(SerParameter serParameter) {
        Object returnData = null;
        Object data1 = serParameter.getResponse().getData();
        if (data1 != null && data1 instanceof Map) {
            if (((Map) data1).containsKey("data")) {
                Object data2 = ((Map) data1).get("data");
                if (data2 != null) {
                    if (data2 instanceof Map) {
                        if (((Map) data2).containsKey("data")) {
                            Object data3 = ((Map) data2).get("data");
                            returnData = data3;
                        } else {
                            return data2;
                        }
                    } else if (data2 instanceof List) {
                        returnData = data2;
                    }
                } else {
                    returnData = data2;
                }
            } else {
                returnData = data1;
            }
        }
        return returnData;
    }

    private void doMap(Map dataMap, SerParameter serParameter, CfgBusiBase cfgBusiBase) {
        LinkedHashMap busiParams = serParameter.getRequest().getRequestInfo().getBusiParams();
        String key = serParameter.getBusiCode();
        List<CfgSrvParamMapping> paramMappings = cfgBusiBase.getParamMap().get(key);
        if (paramMappings != null && paramMappings.size() > 0) {
            for (CfgSrvParamMapping paramMapping : paramMappings) {
                //需要传递到下一服务时
                if (paramMapping.getTransitivity() == 1) {
                    String paramCode = paramMapping.getParamCode();
                    if (dataMap.containsKey(paramCode)) {
                        busiParams.put(paramMapping.getSrvParamCode(), dataMap.get(paramCode));
                    }
                }
            }
        }
    }
}
