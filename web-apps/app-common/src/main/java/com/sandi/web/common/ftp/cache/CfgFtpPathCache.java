package com.sandi.web.common.ftp.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.ftp.entity.CfgFtpPathEntity;
import com.sandi.web.common.ftp.service.interfaces.ICfgFtpPathSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.ftp.entity.CfgFtpPath;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lury on 2015/7/21.
 */
public class CfgFtpPathCache extends DefaultCache {
    public HashMap getData() throws Exception {
        HashMap<String, CfgFtpPath> retMap = new HashMap<String, CfgFtpPath>();
        ICfgFtpPathSV cfgFtpPathSV = SpringContextHolder.getBean(ICfgFtpPathSV.class);
        List<CfgFtpPathEntity> list = cfgFtpPathSV.getAllCfgFtpPath();
        if (list != null && list.size() > 0) {
            for (CfgFtpPathEntity cfgFtpPathEntity : list) {
                CfgFtpPath cfgFtpPath = JsonUtil.json2Object(JsonUtil.object2Json(cfgFtpPathEntity), CfgFtpPath.class);
                retMap.put(cfgFtpPath.getFtpPathCode(), cfgFtpPath);
            }
        }
        return retMap;
    }
}
