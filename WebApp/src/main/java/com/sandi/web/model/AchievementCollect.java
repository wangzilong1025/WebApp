package com.sandi.web.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 成果收藏表
 * @author 15049
 *
 */
public class AchievementCollect implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 成果收藏ID
     */
    private int achievementCollectId;
    /**
     * 成果标题ID
     */
    private int achievementId;
    /**
     * 用户信息ID
     */
    private int userinfoId;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 收藏时间
     */
    private Date collectionTime;
    /**
     * 用户收藏成果时间（String类型）
     */
    private String collectionTimeStr;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getAchievementCollectId() {
        return achievementCollectId;
    }

    public void setAchievementCollectId(int achievementCollectId) {
        this.achievementCollectId = achievementCollectId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public int getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(int userinfoId) {
        this.userinfoId = userinfoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionTimeStr() {
        return collectionTimeStr;
    }

    public void setCollectionTimeStr(String collectionTimeStr) {
        this.collectionTimeStr = collectionTimeStr;
    }



    /* public AchievementCollect(int achievementCollectId, int achievementId,
                              int userinfoId, int userId, Date collectionTime) {
        super();
        this.achievementCollectId = achievementCollectId;
        this.achievementId = achievementId;
        this.userinfoId = userinfoId;
        this.userId = userId;
        this.collectionTime = collectionTime;
    }
    public AchievementCollect() {
        super();
    }
    @Override
    public String toString() {
        return "AchievementCollect [achievementCollectId="
                + achievementCollectId + ", achievementId=" + achievementId
                + ", collectionTime=" + collectionTime + ", userId=" + userId
                + ", userinfoId=" + userinfoId + "]";
    }*/

}
