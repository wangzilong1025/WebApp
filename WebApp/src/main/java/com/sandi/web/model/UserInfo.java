package com.sandi.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 15049 on 2017-04-11.
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户信息ID
     */
    private int userinfoId;
    /**
     * 用户昵称
     */
    private String userNick;
    /**
     * 用户性别
     */
    private int userSex;
    /**
     * 用户行业
     */
    private String userProfession;
    /**
     * 用户学历
     */
    private String userAcademicdegree;
    /**
     * 用户生日
     */
    private Date userBirth;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户电话
     */
    private String userTelphone;
    /**
     * 用户头像
     */
    private String userImage;
    /**
     * 用户ID
     */
    private int userId;
    public int getUserinfoId() {
        return userinfoId;
    }
    public void setUserinfoId(int userinfoId) {
        this.userinfoId = userinfoId;
    }
    public String getUserNick() {
        return userNick;
    }
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    public int getUserSex() {
        return userSex;
    }
    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }
    public String getUserProfession() {
        return userProfession;
    }
    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }
    public String getUserAcademicdegree() {
        return userAcademicdegree;
    }
    public void setUserAcademicdegree(String userAcademicdegree) {
        this.userAcademicdegree = userAcademicdegree;
    }
    public Date getUserBirth() {
        return userBirth;
    }
    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserTelphone() {
        return userTelphone;
    }
    public void setUserTelphone(String userTelphone) {
        this.userTelphone = userTelphone;
    }
    public String getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfo(int userinfoId, String userNick, int userSex,
                    String userProfession, String userAcademicdegree, Date userBirth,
                    String userAddress, String userEmail, String userTelphone,
                    String userImage, int userId) {
        super();
        this.userinfoId = userinfoId;
        this.userNick = userNick;
        this.userSex = userSex;
        this.userProfession = userProfession;
        this.userAcademicdegree = userAcademicdegree;
        this.userBirth = userBirth;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userTelphone = userTelphone;
        this.userImage = userImage;
        this.userId = userId;
    }
    public UserInfo() {
        super();
    }
    @Override
    public String toString() {
        return "UserInfo [userAcademicdegree=" + userAcademicdegree
                + ", userAddress=" + userAddress + ", userBirth=" + userBirth
                + ", userEmail=" + userEmail + ", userId=" + userId
                + ", userImage=" + userImage + ", userNick=" + userNick
                + ", userProfession=" + userProfession + ", userSex=" + userSex
                + ", userTelphone=" + userTelphone + ", userinfoId="
                + userinfoId + "]";
    }

}
