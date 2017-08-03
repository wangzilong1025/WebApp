package com.sandi.web.common.staticdata.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.staticdata.entity.CfgStaticDataEntity;
import com.sandi.web.common.staticdata.service.interfaces.ICfgStaticDataSV;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/12.
 * 一类   此缓存中缓存codeType
 * 二类   此缓存缓存 codeType +^ + codeValue
 */
public class WebCfgStaticDataCache extends DefaultCache {
    public static final String CACHE_NAME = "WebCfgStaticDataCache";


    @Override
    protected Map getData() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        ICfgStaticDataSV cfgStaticDataSV = SpringContextHolder.getBean(ICfgStaticDataSV.class);
        List<CfgStaticDataEntity> cfgStaticDataEntityList = cfgStaticDataSV.getAllCfgStaticData();
        List<com.sandi.web.utils.export.CfgStaticDataEntity> webCfgStaticDataEntityList = new ArrayList<>();

        if (cfgStaticDataEntityList != null && cfgStaticDataEntityList.size() > 0) {
            for (CfgStaticDataEntity cfgStaticDataEntity : cfgStaticDataEntityList) {

                com.sandi.web.utils.export.CfgStaticDataEntity webEntity = new com.sandi.web.utils.export.CfgStaticDataEntity();
                webEntity.setState(cfgStaticDataEntity.getState());
                webEntity.setCodeDesc(cfgStaticDataEntity.getCodeDesc());
                webEntity.setCodeName(cfgStaticDataEntity.getCodeName());
                webEntity.setCodeType(cfgStaticDataEntity.getCodeType());
                webEntity.setCodeValue(cfgStaticDataEntity.getCodeValue());
                webEntity.setExtendCode(cfgStaticDataEntity.getExtendCode());
                webEntity.setExtendCode2(cfgStaticDataEntity.getExtendCode2());
                webEntity.setExtendCode3(cfgStaticDataEntity.getExtendCode3());
                webEntity.setParentCode(cfgStaticDataEntity.getParentCode());
                webEntity.setSortId(cfgStaticDataEntity.getSortId());
                webCfgStaticDataEntityList.add(webEntity);
            }

            for (com.sandi.web.utils.export.CfgStaticDataEntity cfgStaticDataEntity : webCfgStaticDataEntityList) {
                String codeType = cfgStaticDataEntity.getCodeType();
                String codeValue = cfgStaticDataEntity.getCodeValue();
                if (map.containsKey(codeType)) {
                    List tmpList = (List) map.get(codeType);
                    tmpList.add(cfgStaticDataEntity);
                } else {
                    List tmpList = new ArrayList();
                    tmpList.add(cfgStaticDataEntity);
                    map.put(codeType, tmpList);
                }
                map.put(codeType+"^"+codeValue, cfgStaticDataEntity);
            }
        }
        return map;
    }



}
