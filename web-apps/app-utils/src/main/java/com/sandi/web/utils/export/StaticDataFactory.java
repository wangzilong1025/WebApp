package com.sandi.web.utils.export;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/4.
 * 静态数据处理类
 */
public class StaticDataFactory {
    private static final Logger log = Logger.getLogger(StaticDataFactory.class);
    private static Map<String, List<CfgStaticDataEntity>> cfgStaticDataCache = new HashMap<String, List<CfgStaticDataEntity>>();

    /**
     * 获取静态数据
     * */
    public  static  List<CfgStaticDataEntity> getCfgStaticData(String codeType) throws Exception{
        List<CfgStaticDataEntity> cfgStaticDataEntityList = new ArrayList<CfgStaticDataEntity>();
        Object obj = CacheFactory.getRedisCache("WebCfgStaticDataCache", codeType);
        if(obj!=null){
            cfgStaticDataEntityList = (List<CfgStaticDataEntity>)obj;
        }
        return cfgStaticDataEntityList;
    }



    public static CfgStaticDataEntity getCfgStaticDataByCon (String codeType , String codeValue) throws Exception{
        //判断是否使用缓存，如果使用则从缓存中获取数据
        CfgStaticDataEntity  cfgStaticDataEntity  =  new CfgStaticDataEntity () ;
            //修改成从reids中读取数据
        Object obj = CacheFactory.getRedisCache("WebCfgStaticDataCache", codeType+"^"+codeValue);
        if(obj!=null){
            cfgStaticDataEntity = (CfgStaticDataEntity) obj;
        }
        return cfgStaticDataEntity;
    }
}