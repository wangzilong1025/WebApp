package com.sandi.web.model;

import java.util.Date;

/**
 * Created by 王子龙 on 2017-05-14.
 */
public class FotoPlace {

    /**
     * 足记ID
     */
    private int fotoPlaceId;

    /**
     * 用户信息编号
     */
    private int userinfoId;

    /**
     * 科研成果ID
     */
    private int achievementId;

    /**
     * 足记浏览时间
     */
    private Date fotoPlaceDate;

    /**
     * 足记浏览时间Str类型
     */
    private String fotoPlaceDateStr;

    /**
     * 信息状态
     */
    private int state;

    public int getFotoPlaceId() {
        return fotoPlaceId;
    }

    public void setFotoPlaceId(int fotoPlaceId) {
        this.fotoPlaceId = fotoPlaceId;
    }

    public int getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(int userinfoId) {
        this.userinfoId = userinfoId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public Date getFotoPlaceDate() {
        return fotoPlaceDate;
    }

    public void setFotoPlaceDate(Date fotoPlaceDate) {
        this.fotoPlaceDate = fotoPlaceDate;
    }

    public String getFotoPlaceDateStr() {
        return fotoPlaceDateStr;
    }

    public void setFotoPlaceDateStr(String fotoPlaceDateStr) {
        this.fotoPlaceDateStr = fotoPlaceDateStr;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FotoPlace{" +
                "fotoPlaceId=" + fotoPlaceId +
                ", userinfoId=" + userinfoId +
                ", achievementId=" + achievementId +
                ", fotoPlaceDate=" + fotoPlaceDate +
                ", fotoPlaceDateStr='" + fotoPlaceDateStr + '\'' +
                ", state=" + state +
                '}';
    }
}
