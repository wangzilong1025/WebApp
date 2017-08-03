/**
 * $Id: CfgQuickSearch.java,v 1.0 2017/3/30 21:59 lijie Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

/**
 * @author lijie
 * @version $Id: CfgQuickSearch.java,v 1.1 2017/3/30 21:59 lijie Exp $
 * Created on 2017/3/30 21:59
 */
public class CfgQuickSearch implements java.io.Serializable{
    private String text;    //搜索框中展示的文本
    private String textFind;//输入关键字时过滤的文本
    private String name;    //打开页面时的标签
    private String url;     //页面地址
    private int openType;//打开类型，1-tab页面，2-弹框，3-不提醒，直接替换

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextFind() {
        return textFind;
    }

    public void setTextFind(String textFind) {
        this.textFind = textFind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }


}
