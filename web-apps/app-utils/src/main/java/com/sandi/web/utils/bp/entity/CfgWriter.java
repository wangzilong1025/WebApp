/**
 * $Id: CfgWriter.java,v 1.0 17/2/23 下午2:41 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.bp.entity;

/**
 * @author zhangruiping
 * @version $Id: CfgWriter.java,v 1.1 17/2/23 下午2:41 zhangruiping Exp $
 *          Created on 17/2/23 下午2:41
 */
public class CfgWriter {
    private String colSplit;
    private String fileEncode;
    private Integer fileType;


    public CfgWriter() {

    }

    public CfgWriter(String colSplit, String fileEncode) {
        this.colSplit = colSplit;
        this.fileEncode = fileEncode;
    }

    public CfgWriter(Integer fileType) {
        this.fileType = fileType;
    }

    public String getColSplit() {
        return colSplit;
    }

    public void setColSplit(String colSplit) {
        this.colSplit = colSplit;
    }

    public String getFileEncode() {
        return fileEncode;
    }

    public void setFileEncode(String fileEncode) {
        this.fileEncode = fileEncode;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
}