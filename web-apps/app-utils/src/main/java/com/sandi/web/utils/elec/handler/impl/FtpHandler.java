/**
 * $Id: FtpHandler.java,v 1.0 16/9/12 上午11:15 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.elec.handler.impl;

import com.sandi.web.utils.elec.entity.CfgElec;
import com.sandi.web.utils.elec.entity.ElecInst;
import com.sandi.web.utils.elec.handler.Handler;
import com.sandi.web.utils.ftp.FtpUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhangruiping
 * @version $Id: FtpHandler.java,v 1.1 16/9/12 上午11:15 zhangruiping Exp $
 *          Created on 16/9/12 上午11:15
 */
public class FtpHandler implements Handler {

    @Override
    public void upload(ElecInst elecInst, CfgElec cfgElec, InputStream inputStream) throws Exception {
        String address = cfgElec.getTransferAddress();
        if (address == null || "".equals(address)) {
            throw new Exception("传输地址不能为空，请确认CFG_ELEC表中配置是否正确！");
        }
        DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = dateformat.format(new Date()) + "_" + UUID.randomUUID().toString();
        FtpUtil.upload(address, newFileName, inputStream);
        elecInst.setFileSaveName(newFileName);
    }

    @Override
    public void download(ElecInst elecInst, CfgElec cfgElec, OutputStream outputStream) throws Exception {
        String address = cfgElec.getTransferAddress();
        if (address == null || "".equals(address)) {
            throw new Exception("传输地址不能为空，请确认CFG_ELEC表中配置是否正确！");
        }
        FtpUtil.download(address, elecInst.getFileSaveName(), outputStream);
    }


    /**
     * 删除文件
     *
     * @param elecInst
     * @param cfgElec
     * @throws Exception
     */
    @Override
    public void del(ElecInst elecInst, CfgElec cfgElec) throws Exception {
        String address = cfgElec.getTransferAddress();
        if (address == null || "".equals(address)) {
            throw new Exception("传输地址不能为空，请确认CFG_ELEC表中配置是否正确！");
        }
        FtpUtil.delete(address, elecInst.getFileSaveName());
    }
}