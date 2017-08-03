/**
 * $Id: CfgDyncBusiFrameRelFSVImpl.java,v 1.0 2016/8/30 15:22 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.dync;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.dync.entity.*;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncCommonSV;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.utils.api.dync.ICfgDyncCommonFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haomeng
 * @version $Id: CfgDyncCommonFSVImpl.java,v 1.1 2016/8/30 15:22 haomeng Exp $
 *          Created on 2016/8/30 15:22
 */
@Service
public class CfgDyncCommonFSVImpl implements ICfgDyncCommonFSV {
    private static Logger logger = Logger.getLogger(CfgDyncCommonFSVImpl.class);

    @Autowired
    private ICfgDyncCommonSV cfgDyncCommonSV;

    /**
     * 获取根据条件获取请求的动态页面路径
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public String getRequestUrl(String param) {
        Response response = new Response();
        try {
            String data = cfgDyncCommonSV.getRequestUrl(param);
            response.setData(data);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 获取动态表单busiframe数据
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public String getBusiFrameEntity(String param) {
        Response response = new Response();
        try {
            String data = cfgDyncCommonSV.getBusiFrameEntity(param);
            response.setData(data);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 查询框架模板
     *
     * @param param
     * @return
     */
    @Override
    public String getCfgDyncPageTemplate(String param) {
        Response response = new Response();
        try {
            CfgDyncPageTemplateEntity cfgDyncPageTemplateEntity = new CfgDyncPageTemplateEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncPageTemplate(cfgDyncPageTemplateEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 查询规则集
     *
     * @param param
     * @return
     */
    @Override
    public String getCfgDyncRuleset(String param) {
        Response response = new Response();
        try {
            CfgDyncRulesetEntity cfgBpTemplateEntity = new CfgDyncRulesetEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncRuleset(cfgBpTemplateEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 查询按钮集
     *
     * @param param
     * @return
     */
    @Override
    public String getCfgDyncButtonset(String param) {
        Response response = new Response();
        try {
            CfgDyncButtonsetEntity cfgDyncButtonsetEntity = new CfgDyncButtonsetEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncButtonset(cfgDyncButtonsetEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    public String getCfgDyncButton(String param) {
        Response response = new Response();
        try {
            CfgDyncButtonEntity cfgDyncButtonEntity = new CfgDyncButtonEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncButton(cfgDyncButtonEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String getCfgDyncRule(String param) {
        Response response = new Response();
        try {
            CfgDyncRuleEntity cfgDyncRuleEntity = new CfgDyncRuleEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncRule(cfgDyncRuleEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 查询正则
     *
     * @param param
     * @return
     */
    @Override
    public String getCfgDyncRuleExp(String param) {
        Response response = new Response();
        try {
            CfgDyncRuleExpEntity cfgDyncRuleExpEntity = new CfgDyncRuleExpEntity();
            response.setData(cfgDyncCommonSV.getCfgDyncRuleExp(cfgDyncRuleExpEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 保存动态表单配置数据
     *
     * @param param
     * @return
     */
    @Override
    public String saveCfgDyncData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Object(param, Map.class);
            if (paramMap == null || paramMap.get("data") == null) {
                throw new Exception("未找到动态表单配置数据");
            }
            Map<String, Object> data = (Map<String, Object>) paramMap.get("data");
            response.setData(cfgDyncCommonSV.saveCfgDyncData(data));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 查询已配置的业务数据
     *
     * @param param
     * @return
     */
    @Override
    public String queryCfgDyncBusiData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            String remark = null;
            if (paramMap.containsKey("remark") && paramMap.get("remark") != null && !"".equals(paramMap.get("remark").toString())) {
                remark = paramMap.get("remark").toString();
            }
            Integer state = null;
            if (paramMap.containsKey("state") && paramMap.get("state") != null && !"".equals(paramMap.get("state").toString())) {
                state = Integer.valueOf(paramMap.get("state").toString());
            }
            Page page = new Page();
            if (paramMap.containsKey("page") && paramMap.get("page") != null) {
                page.setPageNo(Integer.valueOf(paramMap.get("page").toString()));
            } else {
                page.setPageNo(1);
            }
            if (paramMap.containsKey("pageSize") && paramMap.get("pageSize") != null) {
                page.setPageSize(Integer.valueOf(paramMap.get("pageSize").toString()));
            } else {
                page.setPageSize(10);
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("remark", remark);
            queryMap.put("state", state);


            Map<String, Object> reMap = new HashMap<String, Object>();
            reMap.put("status", "0");
            reMap.put("list", cfgDyncCommonSV.queryCfgDyncBusiData(queryMap, page));
            reMap.put("count", page.getCount());
            response.setData(reMap);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 启用业务
     *
     * @param param
     * @return
     */
    @Override
    public String startBusi(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            Long busiFrameId = null;
            if (paramMap.containsKey("busiFrameId") && paramMap.get("busiFrameId") != null) {
                busiFrameId = Long.valueOf(paramMap.get("busiFrameId").toString());
            }
            if (busiFrameId == null) {
                throw new Exception("未获取到正确的参数，框架业务ID不能为空！");
            }

            cfgDyncCommonSV.startBusi(busiFrameId);
            response.setData(1);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 锁定业务
     *
     * @param param
     * @return
     */
    @Override
    public String lockBusi(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            Long busiFrameId = null;
            if (paramMap.containsKey("busiFrameId") && paramMap.get("busiFrameId") != null) {
                busiFrameId = Long.valueOf(paramMap.get("busiFrameId").toString());
            }
            if (busiFrameId == null) {
                throw new Exception("未获取到正确的参数，框架业务ID不能为空！");
            }
            cfgDyncCommonSV.lockBusi(busiFrameId);
            response.setData(1);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 获取业务配置数据
     *
     * @param param
     * @return
     */
    @Override
    public String getCfgDyncData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            response.setData(cfgDyncCommonSV.getCfgDyncData(paramMap));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 获取页面和框架之间的复用关系
     *
     * @param param
     * @return
     */
    public String queryPageFrameRelation(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            response.setData(cfgDyncCommonSV.queryPageFrameRelation(paramMap));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }



    public String saveButtonset(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            cfgDyncCommonSV.saveButtonset(paramMap);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String saveRuleset(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            cfgDyncCommonSV.saveRuleset(paramMap);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String saveCfgDyncButton(String param) {
        Response response = new Response();
        try {
            CfgDyncButtonEntity cfgDyncButtonEntity = JsonUtil.json2Object(param, CfgDyncButtonEntity.class);
            cfgDyncCommonSV.saveButton(cfgDyncButtonEntity);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String saveCfgDyncRule(String param) {
        Response response = new Response();
        try {
            CfgDyncRuleEntity cfgDyncRuleEntity = JsonUtil.json2Object(param, CfgDyncRuleEntity.class);
            cfgDyncCommonSV.saveRule(cfgDyncRuleEntity);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    /**
     * 查询已配置的按钮集
     *
     * @param param
     * @return
     */

    public String queryCfgButtonsetData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            String buttonsetName = null;
            if (paramMap.containsKey("buttonsetName") && paramMap.get("buttonsetName") != null && !"".equals(paramMap.get("buttonsetName").toString())) {
                buttonsetName = paramMap.get("buttonsetName").toString();
            }
            Page page = new Page();
            if (paramMap.containsKey("page") && paramMap.get("page") != null) {
                page.setPageNo(Integer.valueOf(paramMap.get("page").toString()));
            } else {
                page.setPageNo(1);
            }
            if (paramMap.containsKey("pageSize") && paramMap.get("pageSize") != null) {
                page.setPageSize(Integer.valueOf(paramMap.get("pageSize").toString()));
            } else {
                page.setPageSize(10);
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("buttonsetName", buttonsetName);


            Map<String, Object> reMap = new HashMap<String, Object>();
            reMap.put("status", "0");
            reMap.put("list", cfgDyncCommonSV.queryCfgButtonsetData(queryMap, page));
            reMap.put("count", page.getCount());
            response.setData(reMap);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String queryCfgRulesetData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            String rulesetName = null;
            if (paramMap.containsKey("rulesetName") && paramMap.get("rulesetName") != null && !"".equals(paramMap.get("rulesetName").toString())) {
                rulesetName = paramMap.get("rulesetName").toString();
            }
            Page page = new Page();
            if (paramMap.containsKey("page") && paramMap.get("page") != null) {
                page.setPageNo(Integer.valueOf(paramMap.get("page").toString()));
            } else {
                page.setPageNo(1);
            }
            if (paramMap.containsKey("pageSize") && paramMap.get("pageSize") != null) {
                page.setPageSize(Integer.valueOf(paramMap.get("pageSize").toString()));
            } else {
                page.setPageSize(10);
            }

            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("rulesetName", rulesetName);


            Map<String, Object> reMap = new HashMap<String, Object>();
            reMap.put("status", "0");
            reMap.put("list", cfgDyncCommonSV.queryCfgRulesetData(queryMap, page));
            reMap.put("count", page.getCount());
            response.setData(reMap);
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String getNewButtonId(String param) {
        Response response = new Response();
        try {
            response.setData(new CfgDyncButtonEntity().newId());
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String getNewRuleId(String param) {
        Response response = new Response();
        try {
            response.setData(new CfgDyncRuleEntity().newId());
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String delCfgDyncButton(String param) {
        Response response = new Response();
        try {

            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            Long buttonId = null;
            if(paramMap.get("buttonId") == null || "".equals(paramMap.get("buttonId"))){
                throw new Exception("按钮ID不能为空！");
            }else{
                buttonId = Long.valueOf(paramMap.get("buttonId").toString());
            }

            cfgDyncCommonSV.delCfgDyncButton(buttonId);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String delButtonset(String param) {
        Response response = new Response();
        try {

            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            Long buttonsetId = null;
            if(paramMap.get("buttonsetId") == null || "".equals(paramMap.get("buttonsetId"))){
                throw new Exception("按钮集ID不能为空！");
            }else{
                buttonsetId = Long.valueOf(paramMap.get("buttonsetId").toString());
            }

            cfgDyncCommonSV.delButtonset(buttonsetId);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String delRuleset(String param) {
        Response response = new Response();
        try {

            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            Long rulesetId = null;
            if(paramMap.get("rulesetId") == null || "".equals(paramMap.get("rulesetId"))){
                throw new Exception("规则集ID不能为空！");
            }else{
                rulesetId = Long.valueOf(paramMap.get("rulesetId").toString());
            }

            cfgDyncCommonSV.delRuleset(rulesetId);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String delCfgDyncRule(String param) {
        Response response = new Response();
        try {

            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            Long ruleId = null;
            if(paramMap.get("ruleId") == null || "".equals(paramMap.get("ruleId"))){
                throw new Exception("按钮ID不能为空！");
            }else{
                ruleId = Long.valueOf(paramMap.get("ruleId").toString());
            }

            cfgDyncCommonSV.delCfgDyncRule(ruleId);
            response.setData("1");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }
}