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

/**
 * @author lijie
 * @version $Id: ParamMappingEvent.java,v 1.1 2017/1/19 13:53 lijie Exp $
 * Created on 2017/1/19 13:53
 */
public class ParamMappingEvent implements IEvent {

    @Override
    public void init() throws Exception {

    }

    @Override
    public SerParameter doEvent(SerParameter serParameter, CfgBusiBase cfgBusiBase) {
        LinkedHashMap busiParams = serParameter.getRequest().getRequestInfo().getBusiParams();
        String key = serParameter.getBusiCode();
        List<CfgSrvParamMapping> paramMappings = cfgBusiBase.getParamMap().get(key);
        if (paramMappings != null && paramMappings.size() > 0) {
            for (CfgSrvParamMapping paramMapping : paramMappings) {
                if (paramMapping.getTransitivity() == 0) {
                    String paramCode = paramMapping.getParamCode();
                    if (busiParams.containsKey(paramCode)) {
                        busiParams.put(paramMapping.getSrvParamCode(), busiParams.get(paramCode));
                    }
                }
            }
        }
        return serParameter;
    }
}
