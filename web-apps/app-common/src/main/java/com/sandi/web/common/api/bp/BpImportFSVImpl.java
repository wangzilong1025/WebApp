/**
 * $Id: BpImportFSVImpl.java,v 1.0 2016/9/27 16:56 Administrator Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.bp;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.bp.Constants;
import com.sandi.web.common.bp.entity.CfgBpTemplateEntity;
import com.sandi.web.common.bp.entity.EsBpDataEntity;
import com.sandi.web.common.bp.service.interfaces.ICfgBpTemplateSV;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataSV;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.utils.api.bp.IBpImportFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haomeng
 *         Created on 2016/9/27 16:56
 */
@Service
public class BpImportFSVImpl implements IBpImportFSV {
    private static final Logger logger = LoggerFactory.getLogger(BpImportFSVImpl.class);

    @Autowired
    private ICfgBpTemplateSV cfgBpTemplateSV;

    @Resource
    private IEsBpDataSV esBpDataSV;

    /**
     * 获取配置的所有BP业务
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public String queryCfgBpTemplate(String param) {
        Response response = new Response();
        try {
            CfgBpTemplateEntity cfgBpTemplateEntity;
            if (param != null && !"".equals(param)) {
                cfgBpTemplateEntity = JsonUtil.json2Object(param, CfgBpTemplateEntity.class);
            } else {
                cfgBpTemplateEntity = new CfgBpTemplateEntity();
            }
            response.setData(cfgBpTemplateSV.queryCfgBpTemplate(cfgBpTemplateEntity));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }


    /**
     * 文件上传成功后保存bp数据
     *
     * @param param
     * @return
     */
    @Override
    public String saveBpData(String param) {
        Response response = new Response();
        try {
            EsBpDataEntity esBpDataEntity = JsonUtil.json2Object(param, EsBpDataEntity.class);
            if (esBpDataEntity == null) {
                throw new Exception("参数不能为空！");
            }
            if (esBpDataEntity.getFileInputId() == null) {
                throw new Exception("文件上传的实例ID不能为空！");
            }
            if (esBpDataEntity.getTemplateId() == null) {
                throw new Exception("模板ID不能为空！");
            }
            if (esBpDataEntity.getPriority() == null) {
                throw new Exception("优先级不能为空！");
            }
            esBpDataEntity.setDataId(esBpDataEntity.newId());
            esBpDataEntity.setState(Constants.FileState.WAIT_ANALYSE);
            esBpDataSV.saveEsBpData(esBpDataEntity);
            response.setData("0");
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }

    @Override
    public String queryEsBpData(String param) {
        Response response = new Response();
        try {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

            Map<String, Object> paramMap = JsonUtil.json2Map(param);
            logger.info("--------------" + paramMap);
            Date startDate = null;
            if (paramMap.containsKey("startDate") && paramMap.get("startDate") != null) {
                String t = paramMap.get("startDate").toString();
                if (!"".equals(t)) {
                    startDate = dateformat.parse(t);
                }
            }
            Date endDate = null;
            if (paramMap.containsKey("endDate") && paramMap.get("endDate") != null) {
                String t = paramMap.get("endDate").toString();
                if (!"".equals(t)) {
                    endDate = dateformat.parse(t);
                }
            }
            Long templateId = null;
            if (paramMap.containsKey("templateId") && paramMap.get("templateId") != null) {
                templateId = Long.valueOf(paramMap.get("templateId").toString());
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
            queryMap.put("startDate", startDate);
            queryMap.put("endDate", endDate);
            queryMap.put("templateId", templateId);


            Map<String, Object> reMap = new HashMap<String, Object>();
            reMap.put("status", "0");
            reMap.put("list", esBpDataSV.queryEsBpData(queryMap, page));
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
     * 导出错误数据
     *
     * @param param
     * @return
     */
    @Override
    public String exportErrorData(String param) {
        Response response = new Response();
        try {
            Map<String, Object> paramMap = JsonUtil.json2Object(param, Map.class);
            if (paramMap == null) {
                throw new Exception("参数不能为空！");
            }
            if (paramMap.get("dataId") == null) {
                throw new Exception("参数不正确，数据ID不能为空！");
            }
            response.setData(esBpDataSV.queryErrorData(Long.valueOf("" + paramMap.get("dataId"))));
            response.setCode(Response.SUCCESS);
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);
        }
        return response.toString();
    }
}