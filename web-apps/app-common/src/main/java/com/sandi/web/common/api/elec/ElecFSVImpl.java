/**
 * $Id: ElecFSVImpl.java,v 1.0 2016/9/9 16:59 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.api.elec;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.elec.entity.CfgElecEntity;
import com.sandi.web.common.elec.entity.ElecInstEntity;
import com.sandi.web.common.elec.service.interfaces.ICfgElecSV;
import com.sandi.web.utils.api.elec.IElecFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: ElecFSVImpl.java,v 1.1 2016/9/9 16:59 zhangrp Exp $
 *          Created on 2016/9/9 16:59
 */
@Service
public class ElecFSVImpl implements IElecFSV {

    @Autowired
    private ICfgElecSV cfgElecSV;

    private static final Logger logger = LoggerFactory.getLogger(ElecFSVImpl.class);

    /**
     * 查询电子协议配置信息
     * 入参
     * fileTypeId: FILE_TYPE_ID
     * parentFileTypeId: PARENT_FILE_TYPE_ID
     *
     * @param param
     * @return
     */
    @Override
    public String queryCfgElec(String param) {
        Response response = new Response();
        try {
            CfgElecEntity cfgElecEntity = JsonUtil.json2Object(param, CfgElecEntity.class);
            if (cfgElecEntity.getFileTypeId() == null && cfgElecEntity.getParentFileTypeId() == null) {
                throw new Exception("入参不正确！fileTypeId和parentFileTypeId不能都为空！");
            }
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功！");
            response.setData(cfgElecSV.queryCfgElecEntity(cfgElecEntity));
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }


    /**
     * 查询电子资料实例信息
     *
     * @param param
     * @return
     */
    @Override
    public String queryElecIns(String param) {
        Response response = new Response();
        try {
            ElecInstEntity elecInstEntity = JsonUtil.json2Object(param, ElecInstEntity.class);
            elecInstEntity.setState(1);
            if (elecInstEntity.getFileInputId() == null && elecInstEntity.getElecInstId() == null) {
                throw new Exception("入参不正确！文件输入ID和电子资料实例ID不能都为空！");
            }
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功！");
            response.setData(cfgElecSV.queryElecInstEntity(elecInstEntity));
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }


    /**
     * 获取新的fileInputId
     *
     * @param param
     * @return
     */
    @Override
    public String getNewFileInputId(String param) {
        Response response = new Response();
        try {
            response.setCode(Response.SUCCESS);
            response.setMessage("调用成功！");
            response.setData(cfgElecSV.getNewFileInputId());
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }


    /**
     * 保存电子资料实例
     *
     * @param param
     * @return
     */
    @Override
    public String saveElecInst(String param) {
        Response response = new Response();
        try {
            ElecInstEntity elecInstEntity = JsonUtil.json2Object(param, ElecInstEntity.class);
            cfgElecSV.saveElecInst(elecInstEntity);
            response.setCode(Response.SUCCESS);
            response.setMessage("保存成功！");
            response.setData("1");
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }

    /**
     * 删除电子资料
     *
     * @param param
     * @return
     */
    @Override
    public String delElecInst(String param) {
        Response response = new Response();
        try {
            Map<String, Object> map = JsonUtil.json2Map(param);
            if(map.get("elecInstId") == null || "".equals(map.get("elecInstId").toString())){
                throw new Exception("文件实例ID不能为空！");
            }
            ElecInstEntity elecInstEntity = new ElecInstEntity();
            elecInstEntity.setElecInstId(Long.valueOf(map.get("elecInstId").toString()));
            cfgElecSV.delElecInst(elecInstEntity);
            response.setCode(Response.SUCCESS);
            response.setMessage("保存成功！");
            response.setData("1");
        } catch (Exception e) {
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
            logger.error("调用失败！", e);

        }
        return response.toString();
    }
}
