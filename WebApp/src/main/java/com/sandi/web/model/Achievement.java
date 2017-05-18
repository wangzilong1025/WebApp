package com.sandi.web.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 发布成果信息
 * @author 15049
 *
 */
public class Achievement implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 成果ID：achievementId
     */
    private int achievementId;
    /**
     * date类型转成String类型
     */
    private String timeToString;
    /**
     * 成果标题：achievementName
     */
    private String achievementName;
    /**
     * 所在部门名称 unitName
     */
    private String unitName;
    /**
     * 科研成果发布的类型
     */
    private int achievementType;
    /**
     * 所在地区
     */
    private int locationCity;
    /**
     * 成果内容：achievementContent
     */
    private String achievementContent;
    /**
     * 成果图片：achievementImages
     */
    private String achievementImages;
    /**
     * 成果第一张照片
     */
    private String achievementOneImage;
    /**
     * 成果的第二张图片
     */
    private String achievementTwoImage;
    /**
     * 发布时间：releaseTime
     */
    private Date releaseTime;
    /**
     * 用户ID：userId
     */
    private int userId;
    /**
     * 用户昵称
     * @return
     */
    private String userNick;

    /**
     * 科研成果的发布状态
     */
    private int releaseState;
    /*
    成果名称（根据成果ID去查询成果类型的名称）
     */
    private String achievementTypeName;
    /*
    城市名称（根据城市ID去查询城市的名称）
     */
    private String cityTypeName;

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getTimeToString() {
        return timeToString;
    }

    public void setTimeToString(String timeToString) {
        this.timeToString = timeToString;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(int achievementType) {
        this.achievementType = achievementType;
    }

    public int getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(int locationCity) {
        this.locationCity = locationCity;
    }

    public String getAchievementContent() {
        return achievementContent;
    }

    public void setAchievementContent(String achievementContent) {
        this.achievementContent = achievementContent;
    }

    public String getAchievementImages() {
        return achievementImages;
    }

    public void setAchievementImages(String achievementImages) {
        this.achievementImages = achievementImages;
    }

    public String getAchievementOneImage() {
        return achievementOneImage;
    }

    public void setAchievementOneImage(String achievementOneImage) {
        this.achievementOneImage = achievementOneImage;
    }

    public String getAchievementTwoImage() {
        return achievementTwoImage;
    }

    public void setAchievementTwoImage(String achievementTwoImage) {
        this.achievementTwoImage = achievementTwoImage;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(int releaseState) {
        this.releaseState = releaseState;
    }

    public String getAchievementTypeName() {
        return achievementTypeName;
    }

    public void setAchievementTypeName(String achievementTypeName) {
        this.achievementTypeName = achievementTypeName;
    }

    public String getCityTypeName() {
        return cityTypeName;
    }

    public void setCityTypeName(String cityTypeName) {
        this.cityTypeName = cityTypeName;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "achievementId=" + achievementId +
                ", timeToString='" + timeToString + '\'' +
                ", achievementName='" + achievementName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", achievementType=" + achievementType +
                ", locationCity=" + locationCity +
                ", achievementContent='" + achievementContent + '\'' +
                ", achievementImages='" + achievementImages + '\'' +
                ", achievementOneImage='" + achievementOneImage + '\'' +
                ", achievementTwoImage='" + achievementTwoImage + '\'' +
                ", releaseTime=" + releaseTime +
                ", userId=" + userId +
                ", userNick='" + userNick + '\'' +
                ", releaseState=" + releaseState +
                ", achievementTypeName='" + achievementTypeName + '\'' +
                ", cityTypeName='" + cityTypeName + '\'' +
                '}';
    }

}
