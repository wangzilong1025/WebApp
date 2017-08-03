/**
 * $Id: Function.java,v 1.0 2016/9/7 13:57 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author dizl
 * @version $Id: Function.java,v 1.1 2016/9/7 13:57 dizl Exp $
 * Created on 2016/9/7 13:57
 */
public class Function implements Serializable, Comparable<Function> {
    private Long funcId;//菜单编号
    private Long entClassId;
    private String funcCode;
    private String name;//菜单名称
    private Long dominId;
    private Long parentId;//父节点
    private String viewName;//菜单路径
    private String funcImg;//菜单图标
    private Integer funSeq;//菜单排序
    private List<Function> childs;
    /**
     * 默认是按照新页签的方式打开
     * 1：新页签
     * 2：新页面
     */
    private Integer openType = 1;

    public List<Function> getChilds() {
        return childs;
    }

    public void setChilds(List<Function> childs) {
        this.childs = childs;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public Long getEntClassId() {
        return entClassId;
    }

    public void setEntClassId(Long entClassId) {
        this.entClassId = entClassId;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDominId() {
        return dominId;
    }

    public void setDominId(Long dominId) {
        this.dominId = dominId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getFuncImg() {
        return funcImg;
    }

    public void setFuncImg(String funcImg) {
        this.funcImg = funcImg;
    }

    public Integer getFunSeq() {
        return funSeq;
    }

    public void setFunSeq(Integer funSeq) {
        this.funSeq = funSeq;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    //界面排序
    @Override
    public int compareTo(Function o) {

        if (o != null) {
            if (this.getFunSeq() == null || o.getFunSeq() == null) {
                return 0;
            }
            return this.getFunSeq().compareTo(o.getFunSeq());
        }
        return 0;
    }
}
