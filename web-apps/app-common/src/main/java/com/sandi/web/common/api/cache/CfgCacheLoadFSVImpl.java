package com.sandi.web.common.api.cache;
/**
 * created by liuqin3 16/8/26
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.cache.CacheFactory;
import com.sandi.web.common.cache.entity.CfgCacheLoadEntity;
import com.sandi.web.common.cache.service.interfaces.ICfgCacheLoadSV;
import com.sandi.web.utils.api.cache.ICfgCacheLoadFSV;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SecManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service(protocol = {"dubbo"}, interfaceClass = ICfgCacheLoadFSV.class)
public class CfgCacheLoadFSVImpl implements ICfgCacheLoadFSV {

    private static final Logger logger = LoggerFactory.getLogger(CfgCacheLoadFSVImpl.class);
    @Autowired
    public ICfgCacheLoadSV cfgCacheLoadSV;

    @Override
    public String reloadCacheList(String requestJson) {

        Response response = new Response();
        Map<String, Object> responseMap = new HashMap();
        List<CfgCacheLoadEntity> cfgCacheLoadEntityList = new ArrayList<CfgCacheLoadEntity>();
        try {
            Map requestMap = JsonUtil.json2Map(requestJson);
            String cacheName = (String) requestMap.get("cacheName");
            if (cacheName == null || cacheName.equals("")) {
                cfgCacheLoadEntityList = cfgCacheLoadSV.getAllCfgCacheLoadEntity();
            } else {
                Pattern pattern = Pattern.compile("[\\w|\\d]*" + cacheName.toUpperCase() + "[\\w|\\d]*");
                List<CfgCacheLoadEntity> tempList = cfgCacheLoadSV.getAllCfgCacheLoadEntity();
                if (tempList != null && tempList.size() > 0) {
                    for (CfgCacheLoadEntity cfgCacheLoadEntity : tempList) {
                        Matcher matcher = pattern.matcher(cfgCacheLoadEntity.getCacheName().toUpperCase());
                        if (matcher.matches()) {
                            cfgCacheLoadEntityList.add(cfgCacheLoadEntity);
                        }
                    }
                }
            }
            if (cfgCacheLoadEntityList.size() <= 0) {
                responseMap.put("status", "0");
                responseMap.put("message", "cann't find your class");
                responseMap.put("count", 0);
            } else {
                responseMap.put("status", "1");
                responseMap.put("list", cfgCacheLoadEntityList);
                responseMap.put("count", cfgCacheLoadEntityList.size());
            }
            response.setData(responseMap);
            response.setCode(Response.SUCCESS);

        } catch (Exception e) {
            responseMap.put("status", "0");
            responseMap.put("list", "failure:" + e.getMessage());
            responseMap.put("count", 0);

            response.setData(responseMap);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }

    @Override
    public String reloadCache(String requestJson) {
        Response response = new Response();

        Map responseMap = new HashMap();
        String cacheName = "";
        try {
            Map<String, Object> requestMap = JsonUtil.json2Map(requestJson);
            cacheName = (String) requestMap.get("cacheName");
            if (cacheName != null) {
                CacheFactory.reloadCache(cacheName);
            }
            responseMap.put("status", "1");
            responseMap.put("message", "success");

            response.setData(responseMap);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            logger.error("reload" + cacheName + " error " + e.getMessage(), e);

            responseMap.put("status", "0");
            responseMap.put("message", "failure");

            response.setData(responseMap);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    public String reloadDetailData(String requestJson) {
        Response response = new Response();
        try {
            Map map = new HashMap();
            Map returnMap = new HashMap();
            map = JsonUtil.json2Map(requestJson);
            String cacheName = String.valueOf(map.get("cacheName"));
            String cacheKey = String.valueOf(map.get("cacheKey"));
            String key = String.valueOf(map.get("key"));
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            if (!cacheName.equals("null") && cacheName.equals(SecManage.OPER_CACHE_NAME) && !cacheKey.equals("null")) {
                if (JedisUtils.exists(SecManage.OPER_CACHE_NAME + "_" + SecManage.SEC_PRE + cacheKey)) {
                    Object object = JedisUtils.getObject(SecManage.OPER_CACHE_NAME + "_" + SecManage.SEC_PRE + cacheKey);
                    Map temp = new HashMap();
                    if (object != null) {
                        temp.put("key", cacheKey);
                        temp.put("value", object);
                    }
                    resultList.add(temp);
                }
            } else if (!cacheKey.equals("null") && !"".equals(cacheKey)) {
                Object object = CacheFactory.get(cacheName, cacheKey);
                Map temp = new HashMap();
                if (object != null) {
                    temp.put("key", cacheKey);
                    temp.put("value", object);
                }
                resultList.add(temp);
            } else if (!key.equals("null") && !"".equals(key)) {
                Map<String, Object> resultMap = CacheFactory.get(cacheName);
                if (resultMap != null) {
                    for (String str : resultMap.keySet()) {
                        if (str.indexOf(key) >= 0) {
                            Object object = resultMap.get(str);
                            Map tempMap = new HashMap();
                            if (object != null) {
                                tempMap.put("key", str);
                                tempMap.put("value", object);
                                resultList.add(tempMap);
                            }
                        }
                    }
                }
            } else {
                Map<String, Object> resultMap = CacheFactory.get(cacheName);
                if (resultMap != null) {
                    for (String str : resultMap.keySet()) {
                        Object object = resultMap.get(str);
                        Map tempMap = new HashMap();
                        if (object != null) {
                            tempMap.put("key", str);
                            tempMap.put("value", object);
                        }
                        resultList.add(tempMap);
                    }
                }
            }
            returnMap.put("cacheList", resultList);
            returnMap.put("count", resultList.size());
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功");
            response.setData(returnMap);

        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    public String deleteFromCache(String requestJson) {
        Response response = new Response();
        try {
            Map requestMap = new HashMap();
            requestMap = JsonUtil.json2Map(requestJson);
            String cacheName = String.valueOf(requestMap.get("cacheName"));
            String cacheKey = String.valueOf(requestMap.get("cacheKey"));
            boolean flag = false;
            if (cacheName.equals("null") || "".equals(cacheName)) {
                if (JedisUtils.exists(cacheKey)) {
                    JedisUtils.del(cacheKey);
                    flag = true;
                } else {
                    flag = false;
                }
            } else {
                flag = CacheFactory.deleteFromCache(cacheName, cacheKey);
            }
            if (flag) {
                response.setCode(Response.SUCCESS);
                response.setMessage("成功将" + cacheKey + "从缓存中删除");
            }
        } catch (Exception e) {
            logger.error("调用缓存方法出错:" + e.getMessage());
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    public String loadIdOrSysCacheData(String requestJson) {
        Response response = new Response();
        String userCachePre = SecManage.OPER_CACHE_NAME + "_" + SecManage.SEC_PRE;
        try {
            Map requestMap = new HashMap();
            requestMap = JsonUtil.json2Map(requestJson);
            Map returnMap = new HashMap();
            if (requestMap.get("cacheName") != null) {
                String cacheName = (String) requestMap.get("cacheName");
                String pattern = null;
                if (cacheName.startsWith("_ID_CACHE_FLAG_"))
                    pattern = "_ID_CACHE_FLAG_" + "*" + cacheName.substring("_ID_CACHE_FLAG_".length()).toUpperCase() + "*";
                else {
                    pattern = userCachePre + "*" + cacheName.substring(userCachePre.length()).toLowerCase() + "*";
                }
                List<Map<String, Object>> cacheList = new ArrayList<Map<String, Object>>();
                for (String str : JedisUtils.getKeys(pattern)) {
                    Map temp = new HashMap();
                    if (cacheName.startsWith(userCachePre) && JedisUtils.getObject(str) != null) {
                        temp.put("key", str.substring(userCachePre.length()));
                        temp.put("value", JedisUtils.getObject(str));
                    }
                    if (cacheName.startsWith("_ID_CACHE_FLAG_") && JedisUtils.getList(str) != null) {
                        temp.put("key", str.substring("_ID_CACHE_FLAG_".length()));
                        temp.put("value", JedisUtils.getList(str));
                    }
                    cacheList.add(temp);

                }
                returnMap.put("count", cacheList.size());
                returnMap.put("cacheList", cacheList);
                response.setCode(Response.SUCCESS);
                response.setData(returnMap);
            }
        } catch (Exception e) {
            logger.error("加载主键缓存出错", e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }
}