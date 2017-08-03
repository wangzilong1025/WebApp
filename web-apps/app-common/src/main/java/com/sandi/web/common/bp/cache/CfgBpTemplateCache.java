/**
 * $Id: CfgBpTemplateCache.java,v 1.0 2016/9/26 14:50 Administrator Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.bp.cache;

import com.sandi.web.common.bp.entity.CfgBpTemplateEntity;
import com.sandi.web.common.bp.service.interfaces.ICfgBpTemplateSV;
import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haomeng
 * @version $Id: CfgBpTemplateCache.java,v 1.1 2016/9/26 14:50 haomeng Exp $
 *          Created on 2016/9/26 14:50
 */
public class CfgBpTemplateCache extends DefaultCache {

    @Override
    public Map getData() throws Exception {
        HashMap<String, CfgBpTemplateEntity> retMap = new HashMap<String, CfgBpTemplateEntity>();
        ICfgBpTemplateSV cfgFtpSV = SpringContextHolder.getBean(ICfgBpTemplateSV.class);
        List<CfgBpTemplateEntity> list = cfgFtpSV.queryCfgBpTemplate(new CfgBpTemplateEntity());
        if (list != null && list.size() > 0) {
            for (CfgBpTemplateEntity cfgBpTemplateEntity : list) {
                retMap.put("" + cfgBpTemplateEntity.getTemplateId(), cfgBpTemplateEntity);
            }
        }
        return retMap;
    }
}