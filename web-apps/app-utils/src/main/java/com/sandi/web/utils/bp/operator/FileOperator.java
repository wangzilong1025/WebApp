/**
 * $Id: IFileAnalyse.java,v 1.0 17/2/16 下午12:49 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.bp.operator;

import com.sandi.web.utils.bp.entity.CfgBpTemplate;
import com.sandi.web.utils.bp.entity.CfgWriter;
import com.sandi.web.utils.bp.entity.EsBpData;
import com.sandi.web.utils.elec.entity.ElecInst;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zhangruiping
 * @version $Id: IFileAnalyse.java,v 1.1 17/2/16 下午12:49 zhangruiping Exp $
 *          Created on 17/2/16 下午12:49
 */
public abstract class FileOperator {
    /**
     * 进行文件解析
     * excel： sheet row col
     * txt     0     row col
     * cvs     0     row col
     *
     * @param cfgBpTemplateEntity
     * @param esBpDataEntity
     * @param elecInst
     * @return
     * @throws Exception
     */
    abstract public List<List<List<String>>> doAnalyse(CfgBpTemplate cfgBpTemplateEntity, EsBpData esBpDataEntity, ElecInst elecInst) throws Exception;


    /**
     * 数据生成文件
     *
     * @param headers
     * @param data
     * @param outputStream
     * @throws Exception
     */
    abstract public void doWrite(List<List<List<String>>> headers, List<List<List<String>>> data, OutputStream outputStream, CfgWriter cfgWriter) throws Exception;
}