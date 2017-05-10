package com.sandi.web.model;

import java.util.Date;

public class Notice {

    /**
     * 公告ID
     */
    private int noticeId;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 公告创建时间
     */
    private Date createTime;
    /*
    创建公告的String类型
     */
    private String createTimeStr;
    /**
     * 创建员ID
     */
    private int adminId;
    /**
     * 公告发布时间
     */
    private Date noticeReleaseTime;
    /*
    公告发布时间的String类型
     */
    private String noticeReleaseTimeStr;
    /**
     * 公告结束时间
     */
    private Date noticeEndTime;
    /*
    公告结束时间String类型
     */
    private String noticeEndTimeStr;
    /**
     * 公告状态
     */
    private int noticeStatus;

    public int getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
    public String getNoticeContent() {
        return noticeContent;
    }
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCreateTimeStr() {
        return createTimeStr;
    }
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public Date getNoticeReleaseTime() {
        return noticeReleaseTime;
    }
    public void setNoticeReleaseTime(Date noticeReleaseTime) {
        this.noticeReleaseTime = noticeReleaseTime;
    }
    public String getNoticeReleaseTimeStr() {
        return noticeReleaseTimeStr;
    }
    public void setNoticeReleaseTimeStr(String noticeReleaseTimeStr) {
        this.noticeReleaseTimeStr = noticeReleaseTimeStr;
    }
    public Date getNoticeEndTime() {
        return noticeEndTime;
    }
    public void setNoticeEndTime(Date noticeEndTime) {
        this.noticeEndTime = noticeEndTime;
    }
    public String getNoticeEndTimeStr() {
        return noticeEndTimeStr;
    }
    public void setNoticeEndTimeStr(String noticeEndTimeStr) {
        this.noticeEndTimeStr = noticeEndTimeStr;
    }
    public int getNoticeStatus() {
        return noticeStatus;
    }
    public void setNoticeStatus(int noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", createTime=" + createTime +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", adminId=" + adminId +
                ", noticeReleaseTime=" + noticeReleaseTime +
                ", noticeReleaseTimeStr='" + noticeReleaseTimeStr + '\'' +
                ", noticeEndTime=" + noticeEndTime +
                ", noticeEndTimeStr='" + noticeEndTimeStr + '\'' +
                ", noticeStatus=" + noticeStatus +
                '}';
    }
}
