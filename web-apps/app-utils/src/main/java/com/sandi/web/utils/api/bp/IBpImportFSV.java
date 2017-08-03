/**
 * $Id: ICfgDyncBusiFrameRelFSV.java,v 1.1 2016/8/30 14:41 haomeng Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.api.bp;

/**
 * @author haomeng
 * @version $Id: IBpImportFSV.java,v 1.1 2016/8/30 14:41 haomeng Exp $
 *          Created on 2016/8/30 14:41
 */
public interface IBpImportFSV {

    /**
     * 获取配置的BP数据
     * @param param
     * @return
     * @throws Exception
     */
    public String queryCfgBpTemplate(String param);

    /**
     * 文件上传成功后保存bp数据
     * @param param
     * @return
     */
    public String saveBpData(String param);

    /**
     * 查询bp数据
     * @param param
     * @return
     */
    public String queryEsBpData(String param);

    /**
     * 导出错误数据
     * @param param
     * @return
     */
    public String exportErrorData(String param);
}
