/**
 * $Id: ElecUitl.java,v 1.0 16/9/12 上午10:42 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.elec;

import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.elec.entity.CfgElec;
import com.sandi.web.utils.elec.entity.ElecInst;
import com.sandi.web.utils.elec.handler.Handler;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangruiping
 * @version $Id: ElecUitl.java,v 1.1 16/9/12 上午10:42 zhangruiping Exp $
 *          Created on 16/9/12 上午10:42
 */
public class ElecUitl {
    public static final String ELEC_CACHE_NAME = "CfgElecCache";
    private static final Logger logger = LoggerFactory.getLogger(ElecUitl.class);


    public static void upload(ElecInst elecInst, InputStream inputStream) throws Exception{
        if(elecInst.getFileInputId() == null || elecInst.getFileTypeId() == null ){
            throw new Exception("上传失败！文件输入ID和文件类型ID都不能为空！");
        }
        //获取cfgElec
        CfgElec cfgElec = null;
        if(JedisUtils.existsObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId())){
            cfgElec = (CfgElec)JedisUtils.getObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId());
        }
        if(cfgElec == null){
            throw new Exception("根据文件类型ID，无法获取到正确的配置！");
        }

        String transferHandle = cfgElec.getTransferHandle();
        if(transferHandle == null || "".equals(transferHandle)){
            throw new Exception("文件处理类不能为空！");
        }

        if(cfgElec.getHasExpireDate() == null || cfgElec.getHasExpireDate() == 0){
            String[] parsePatterns = {"yyyy-MM-dd"};
            elecInst.setExpireDate(DateUtils.parseDate("2099-12-31", parsePatterns));
        }

        Handler handler = (Handler)Class.forName(transferHandle).newInstance();
        handler.upload(elecInst, cfgElec, inputStream);
    }


    public static void del(ElecInst elecInst) throws Exception{

        if(elecInst.getElecInstId() == null || elecInst.getFileTypeId() == null ){
            throw new Exception("删除失败！文件实例ID和文件类型ID都不能为空！");
        }
        //获取cfgElec
        CfgElec cfgElec = null;
        if(JedisUtils.existsObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId())){
            cfgElec = (CfgElec)JedisUtils.getObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId());
        }
        if(cfgElec == null){
            throw new Exception("根据文件类型ID，无法获取到正确的配置！");
        }


        String transferHandle = cfgElec.getTransferHandle();
        if(transferHandle == null || "".equals(transferHandle)){
            throw new Exception("文件处理类不能为空！");
        }

        Handler handler = (Handler)Class.forName(transferHandle).newInstance();
        handler.del(elecInst, cfgElec);
    }

    public static void download(ElecInst elecInst, OutputStream outputStream) throws Exception{

        if(elecInst.getFileSaveName() == null || elecInst.getFileTypeId() == null ){
            throw new Exception("下载失败！文件保存名和文件类型ID都不能为空！");
        }
        //获取cfgElec
        CfgElec cfgElec = null;
        if(JedisUtils.existsObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId())){
            cfgElec = (CfgElec)JedisUtils.getObject(ELEC_CACHE_NAME + "_" + elecInst.getFileTypeId());
        }
        if(cfgElec == null){
            throw new Exception("根据文件类型ID，无法获取到正确的配置！");
        }


        String transferHandle = cfgElec.getTransferHandle();
        if(transferHandle == null || "".equals(transferHandle)){
            throw new Exception("文件处理类不能为空！");
        }

        Handler handler = (Handler)Class.forName(transferHandle).newInstance();
        handler.download(elecInst, cfgElec, outputStream);
    }

}