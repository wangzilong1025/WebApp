package com.sandi.web.model;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单分类表
 * @author 15049
 *
 */
public class Menu implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 标题ID
     */
    private int topId;
    /**
     * 标题内容
     */
    private String topName;
    /**
     * 本级标题级别
     */
    private int topStatus;
    /**
     * 上级标题级别
     */
    private int upStatus;
    /**
     * 菜单list类型
     */
    private List<Menu> menulist;
    public List<Menu> getMenulist() {
        return menulist;
    }
    public void setMenulist(List<Menu> menulist) {
        this.menulist = menulist;
    }
    public int getTopId() {
        return topId;
    }
    public void setTopId(int topId) {
        this.topId = topId;
    }
    public String getTopName() {
        return topName;
    }
    public void setTopName(String topName) {
        this.topName = topName;
    }
    public int getTopStatus() {
        return topStatus;
    }
    public void setTopStatus(int topStatus) {
        this.topStatus = topStatus;
    }
    public int getUpStatus() {
        return upStatus;
    }
    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }
    public Menu(int topId, String topName, int topStatus, int upStatus) {
        super();
        this.topId = topId;
        this.topName = topName;
        this.topStatus = topStatus;
        this.upStatus = upStatus;
    }
    public Menu() {
        super();
    }
    @Override
    public String toString() {
        return "Menu [topId=" + topId + ", topName=" + topName + ", topStatus="
                + topStatus + ", upStatus=" + upStatus + "]";
    }

}
