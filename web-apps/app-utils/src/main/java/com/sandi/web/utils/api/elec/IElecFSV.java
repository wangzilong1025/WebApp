/**
 * $Id: IElecFSV.java,v 1.0 2016/9/9 16:59 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.elec;

/**
 * @author zhangrp
 * @version $Id: IElecFSV.java,v 1.1 2016/9/9 16:59 zhangrp Exp $
 *          Created on 2016/9/9 16:59
 */
public interface IElecFSV {

    public String queryCfgElec(String param);

    /**
     * 查询电子资料实例信息
     * @param param
     * @return
     */
    public String queryElecIns(String param);

    /**
     * 获取新的fileInputId
     * @param param
     * @return
     */
    public String getNewFileInputId(String param);


    /**
     * 保存电子资料实例
     * @param param
     * @return
     */
    public String saveElecInst(String param);

    /**
     * 删除电子资料
     * @param param
     * @return
     */
    public String delElecInst(String param);
}
