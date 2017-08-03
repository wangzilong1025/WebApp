package com.sandi.web.common.http.service.impl;

import com.sandi.web.common.http.dao.ICfgHttpClientDao;
import com.sandi.web.common.http.dao.ICfgHttpMappingDao;
import com.sandi.web.common.http.entity.CfgHttpClientEntity;
import com.sandi.web.common.http.entity.CfgHttpMappingEntity;
import com.sandi.web.common.http.service.interfaces.IHttpSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LIUQ on 2015/7/21.
 */
@Service
public class HttpSVImpl implements IHttpSV {
    public static final String DEFAULT = "DEFAULT";

    @Override
    public HashMap getHttpMapping(String code) throws Exception {
        HashMap hashMap = new HashMap();
        ICfgHttpMappingDao cfgHttpMappingDao = (ICfgHttpMappingDao) SpringContextHolder.getBean(ICfgHttpMappingDao.class);
        //DEFAULT
        CfgHttpMappingEntity cfgHttpDefaultMapping = new CfgHttpMappingEntity();
        cfgHttpDefaultMapping.setCfgHttpCode(DEFAULT);
        cfgHttpDefaultMapping.setState(CommConstants.State.STATE_NORMAL);
        List<CfgHttpMappingEntity> cfgHttpMappingDefaultList = cfgHttpMappingDao.findByEntity(cfgHttpDefaultMapping);
        if (cfgHttpMappingDefaultList.size() == 0 || cfgHttpMappingDefaultList == null) {
            return hashMap;
        }
        for (int i = 0; i < cfgHttpMappingDefaultList.size(); i++) {
            hashMap.put(cfgHttpMappingDefaultList.get(i).getMappingName().trim(), cfgHttpMappingDefaultList.get(i).getMappingValue().trim());
        }
        //CODE
        CfgHttpMappingEntity cfgHttpMapping = new CfgHttpMappingEntity();
        cfgHttpMapping.setCfgHttpCode(code);
        cfgHttpMapping.setState(CommConstants.State.STATE_NORMAL);
        List<CfgHttpMappingEntity> cfgHttpMappingList = cfgHttpMappingDao.findByEntity(cfgHttpMapping);
        if (cfgHttpMappingList.size() == 0 || cfgHttpMappingList == null){
            return hashMap;
        }
        Iterator<CfgHttpMappingEntity> iterator = cfgHttpMappingList.iterator();
        for (int i = 0; i < cfgHttpMappingList.size(); i++) {
            hashMap.put(cfgHttpMappingList.get(i).getMappingName().trim(), cfgHttpMappingList.get(i).getMappingValue().trim());
        }
        return hashMap;
    }

    @Override
    public CfgHttpClientEntity[] getAllCfgHttpClient() throws Exception {
        ICfgHttpClientDao cfgHttpClientDao = (ICfgHttpClientDao) SpringContextHolder.getBean(ICfgHttpClientDao.class);
        CfgHttpClientEntity cfgHttpClient = new CfgHttpClientEntity();
        cfgHttpClient.setState(CommConstants.State.STATE_NORMAL);
        List<CfgHttpClientEntity> cfgHttpClientList = cfgHttpClientDao.findByEntity(cfgHttpClient);
        if (cfgHttpClientList.size() == 0 || cfgHttpClientList == null) {
            return new CfgHttpClientEntity[0];
        }
        return (CfgHttpClientEntity[]) cfgHttpClientList.toArray(new CfgHttpClientEntity[0]);
    }
}
