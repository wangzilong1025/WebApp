/**
 * $Id: Col.java,v 1.0 2016/8/30 16:35 haomeng Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.dync.data;

/**
 * @author haomeng
 * @version $Id: Col.java,v 1.1 2016/3/8 11:35 haomeng Exp $
 * Created on 2016/8/30 16:35
 */
public class Col {
    private String fullId; //加了page和area拼在一起的id
    private String id;    //属性编码
    private String code;  //属性英文编码
    private String value; //属性值
    private String displayValue;  //展示值
    private String name;  //属性中文名称

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
