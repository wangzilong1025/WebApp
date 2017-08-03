/**
 * $Id: Handler.java,v 1.0 16/9/12 上午11:09 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.elec.handler;

import com.sandi.web.utils.elec.entity.CfgElec;
import com.sandi.web.utils.elec.entity.ElecInst;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangruiping
 * @version $Id: Handler.java,v 1.1 16/9/12 上午11:09 zhangruiping Exp $
 *          Created on 16/9/12 上午11:09
 */
public interface Handler {

    /**
     * 上传文件
     * @param elecInst
     * @param cfgElec
     * @param inputStream
     * @throws Exception
     */
    public void upload(ElecInst elecInst, CfgElec cfgElec, InputStream inputStream) throws Exception;

    /**
     * 下载文件
     * @param elecInst
     * @param cfgElec
     * @return
     * @throws Exception
     */
    public void download(ElecInst elecInst, CfgElec cfgElec, OutputStream outputStream) throws Exception;

    /**
     * 删除文件
     * @param elecInst
     * @param cfgElec
     * @throws Exception
     */
    public void del(ElecInst elecInst, CfgElec cfgElec) throws Exception;

}