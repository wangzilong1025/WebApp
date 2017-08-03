/**
 * $Id: CommonWebController.java,v 1.0 2015/9/15 19:52 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.export.CSVUtil;
import com.sandi.web.utils.export.CfgStaticDataEntity;
import com.sandi.web.utils.export.ExcelUtil;
import com.sandi.web.utils.export.StaticDataFactory;
import com.sandi.web.utils.response.Response;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zhangrp
 * @version $Id: CommonWebController.java,v 1.1 2015/9/15 19:52 zhangrp Exp $
 *          Created on 2015/9/15 19:52
 */
@Controller
@RequestMapping(value = "commonWebController")
public class CommonWebController {

    private static final Logger logger = LoggerFactory.getLogger(CommonWebController.class);

    private static final String fileCommomExportFSV = "IFILEBUSIFSV_FILEEXPORTCOMMONMETHOD";

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportToFile")
    @ResponseBody
    public Map<String,String> exportToFile(
            @RequestParam("param") String param_,
            @RequestParam("columns") String columns_,
            @RequestParam("formatData") String formatData_,
            @RequestParam("excelType") String excelType,
            @RequestParam("schemaData") String schemaData,
            @RequestParam("fileName") String fileName,
            @RequestParam("ftpPathCode") String ftpPathCode,
            HttpServletRequest request, HttpServletResponse response){
        Map<String, String> reMap = new HashMap<String, String>();
        try {
            Map<String,Object> totalMap = new HashedMap();
            Map<String,Object> requestInfoMap  = new HashedMap();
            Map<String,Object> curMap  = new HashedMap();
            String newParam_ = null;
             //解析参数
            {
                totalMap.put("param_",param_);
                totalMap.put("columns_",columns_);
                totalMap.put("excelType",excelType);
                totalMap.put("formatData_",formatData_);
                totalMap.put("schemaData",schemaData);
                totalMap.put("fileName",fileName);
                totalMap.put("ftpPathCode",ftpPathCode);

                requestInfoMap.put("busiParams",totalMap);
                requestInfoMap.put("busiCode",fileCommomExportFSV);
                curMap.put("requestInfo",requestInfoMap);
                curMap.put("pubInfo", null);

                newParam_ = JsonUtil.object2Json(curMap);
                logger.info(param_);
                logger.info(newParam_);
            }

            //发送请求
            String requestURL = null;
            //获取配置数据
            {
                CfgStaticDataEntity cfgStaticDataEntity = StaticDataFactory.getCfgStaticDataByCon("GRID_EXPORT_CFG", "url");
                requestURL = cfgStaticDataEntity.getCodeName();
            }
            if (requestURL == null) {
                throw new Exception("请确认数据导出配置是否正确！");
            }
            logger.debug(requestURL);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(requestURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("data", newParam_));
            postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            Enumeration<String> eee = request.getHeaderNames();
            while (eee.hasMoreElements()) {
                String name = eee.nextElement();
                String value = request.getHeader(name);
                if ("cookie".equals(name)) {
                    postMethod.setHeader(name, value);
                }
            }
            HttpResponse httpResponse = client.execute(postMethod);
            String reJson = EntityUtils.toString(httpResponse.getEntity());
            logger.info(reJson);
            Response response1 = JsonUtil.json2Object(reJson, Response.class);
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            if (response1.getErrorInfo() != null) {
                reMap.put("status", "0");
            }
        } catch (Exception e) {
            logger.error("", e);
            reMap.put("status", "1");
            reMap.put("message", e.getMessage());
        }
        return  reMap;
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportToExcel")
    @ResponseBody
    public Map<String, String> exportToExcel(
            @RequestParam("param") String param_,
            @RequestParam("columns") String columns_,
            @RequestParam("formatData") String formatData_,
            @RequestParam("excelType") String excelType,
            @RequestParam("schemaData") String schemaData,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> reMap = new HashMap<String, String>();

        try {
            List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
            Map<String, Object> formatData = new HashMap<String, Object>();
            String fileName = new Date().getTime() + "_" + UUID.randomUUID();
            //解析参数
            {
                param_ = URLDecoder.decode(URLDecoder.decode(param_, "UTF-8"), "UTF-8");
                columns_ = URLDecoder.decode(URLDecoder.decode(columns_, "UTF-8"), "UTF-8");
                excelType = URLDecoder.decode(URLDecoder.decode(excelType, "UTF-8"), "UTF-8");
                formatData_ = URLDecoder.decode(URLDecoder.decode(formatData_, "UTF-8"), "UTF-8");
                if (excelType == null || !excelType.trim().equals(ExcelUtil.XLSX)) {
                    excelType = ExcelUtil.XLS;
                }
                columns = JsonUtil.json2List(columns_);
                formatData = JsonUtil.json2Map(formatData_);
                logger.info(param_);
                logger.info(schemaData);
            }

            //解析枚举参数
            Map<String, Map<String, String>> formatDataMap = new HashMap<String, Map<String, String>>();
            for (Iterator<String> iterator = formatData.keySet().iterator(); iterator.hasNext(); ) {
                try {
                    String fieldId = iterator.next();
                    String codeType = formatData.get(fieldId) == null ? "" : formatData.get(fieldId).toString();
                    List<CfgStaticDataEntity> cfgStaticDataEntityList = StaticDataFactory.getCfgStaticData(codeType);
                    if (cfgStaticDataEntityList.size() > 0) {
                        Map<String, String> baseTypeMap = new HashMap<String, String>();
                        for (CfgStaticDataEntity cfgStaticDataEntity : cfgStaticDataEntityList) {
                            baseTypeMap.put(cfgStaticDataEntity.getCodeValue(), cfgStaticDataEntity.getCodeName());
                        }
                        formatDataMap.put(fieldId, baseTypeMap);
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }



            //发送请求
            String requestURL = null;
            //获取配置数据
            {
                CfgStaticDataEntity cfgStaticDataEntity = StaticDataFactory.getCfgStaticDataByCon("GRID_EXPORT_CFG", "url");
                requestURL = cfgStaticDataEntity.getCodeName();
            }
            if (requestURL == null) {
                throw new Exception("请确认数据导出配置是否正确！");
            }
            logger.debug(requestURL);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(requestURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("data", param_));
            postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            Enumeration<String> eee = request.getHeaderNames();
            while (eee.hasMoreElements()) {
                String name = eee.nextElement();
                String value = request.getHeader(name);
                if ("cookie".equals(name)) {
                    postMethod.setHeader(name, value);
                }
            }
            HttpResponse httpResponse = client.execute(postMethod);
            String reJson = EntityUtils.toString(httpResponse.getEntity());
            logger.info(reJson);
            Response response1 = JsonUtil.json2Object(reJson, Response.class);
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            if (response1.getErrorInfo() != null) {
                Map<String, Object> retInfo = (Map<String, Object>) response1.getData();
                if (!"".equals(schemaData)) {
                    String schema[] = schemaData.split("\\.");
                    if (schema.length > 1) {
                        Map<String, Object> reData = retInfo;
                        for (int i = 1; i < schema.length - 1; i++) {
                            if (retInfo.get(schema[i]) != null) {
                                reData = (Map<String, Object>) retInfo.get(schema[i]);
                            }
                        }
                        if (reData.get("data") != null) {
                            data = (List<Map<String, Object>>) reData.get("data");
                        }
                    }
                } else {
                    if (retInfo.get("list") != null || retInfo.get("data") != null) {
                        if (retInfo.get("list") == null) {
                            Map<String, Object> reData = (Map<String, Object>) retInfo.get("data");
                            if (reData.get("list") != null) {
                                data = (List<Map<String, Object>>) reData.get("list");
                            }
                        } else {
                            data = (List<Map<String, Object>>) retInfo.get("list");
                        }
                    }
                }
            }

            Map<String, Object> resultData = new HashMap<String, Object>();
            resultData.put("excelType", excelType);
            resultData.put("columns", columns);
            resultData.put("data", data);
            resultData.put("formatDataMap", formatDataMap);
            JedisUtils.setObject(fileName, resultData, 60);//放在redis中，超时60s
            logger.info("++++++++++++++++"+fileName);
            reMap.put("filePath", fileName);
            reMap.put("status", "0");
        } catch (Exception e) {
            logger.error("", e);
            reMap.put("status", "1");
            reMap.put("message", e.getMessage());
        }
        return reMap;
    }


    /**
     * 导出excel
     *
     * @param filePath
     * @param fileName
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadExcel")
    public void downloadExcel(
            @RequestParam("filePath") String filePath,
            @RequestParam("fileName") String fileName,
            @RequestParam("excelType") String excelType,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            filePath = URLDecoder.decode(URLDecoder.decode(filePath, "UTF-8"), "UTF-8");
            fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
            excelType = URLDecoder.decode(URLDecoder.decode(excelType, "UTF-8"), "UTF-8");

            if (excelType == null || "".equals(excelType)) {
                excelType = ExcelUtil.XLS;
            }
            if (excelType.equals(ExcelUtil.XLS)) {
                fileName = URLEncoder.encode(fileName, "UTF-8") + ".xls";
            } else if (excelType.equalsIgnoreCase(CSVUtil.CSV)){
                fileName = URLEncoder.encode(fileName, "UTF-8") + ".csv";
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8") + ".xlsx";
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error("", e1);
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("expires", "0");
        response.reset();
        response.resetBuffer();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String agent = request.getHeader("USER-AGENT");
        if (agent != null && agent.indexOf("Firefox") != -1) {
            response.addHeader("Content-Disposition", "attachment; filename*=\"utf8''" + fileName + "\"");
        } else {
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }
        try {
            OutputStream outputStream = response.getOutputStream();
            Map<String, Object> resultData = (Map<String, Object>) JedisUtils.getObject(filePath);
            //如果为csv格式
            if(excelType.equalsIgnoreCase(CSVUtil.CSV)){
                CSVUtil.exportCSVForDataGrid(resultData.get("excelType") == null ? "1" : resultData.get("excelType").toString(),
                        "sheet1",
                        resultData.get("columns") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>) resultData.get("columns"),
                        resultData.get("formatDataMap") == null ? new HashMap<String, Map<String, String>>() : (Map<String, Map<String, String>>) resultData.get("formatDataMap"),
                        resultData.get("data") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>) resultData.get("data"),
                        outputStream);
            } else {
                ExcelUtil.exportExcelForDataGrid(resultData.get("excelType") == null ? "1" : resultData.get("excelType").toString(),
                        "sheet1",
                        resultData.get("columns") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>) resultData.get("columns"),
                        resultData.get("formatDataMap") == null ? new HashMap<String, Map<String, String>>() : (Map<String, Map<String, String>>) resultData.get("formatDataMap"),
                        resultData.get("data") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>) resultData.get("data"),
                        outputStream);
            }
            JedisUtils.del(filePath);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    @RequestMapping(value = "/downloadFile")
    public void downloadFile(
            @RequestParam("dataRecordId") String dataRecordId,
            @RequestParam("fileName") String fileName,
            HttpServletRequest request, HttpServletResponse response) {

        try {
            fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error("", e1);
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("expires", "0");
        response.reset();
        response.resetBuffer();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String agent = request.getHeader("USER-AGENT");
        if (agent != null && agent.indexOf("Firefox") != -1) {
            response.addHeader("Content-Disposition", "attachment; filename*=\"utf8''" + fileName + "\"");
        } else {
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }
        try {
            OutputStream outputStream = response.getOutputStream();
            //如果为csv格式

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }

    }
}
