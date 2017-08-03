package com.sandi.web.utils.sec.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInfoInterface implements Serializable {
    private String sessionId; //用户登录sessionId
    private Long userId;//登录用户Id
    private String userName;//姓名
    private String userPass;//密码
    private String userNick;//用户昵称
    private Integer recentPassTimes;//最近使用密码登录次数
    private String lockFlag;//账号锁定状态
    private Integer isAutoLockscreen;//是否自动锁屏
    private Integer lockscreenInteval;//锁屏时长
    private Integer isMultiLogin;//是否可重复登陆
    private String phoneNumber;//手机号码
    private String userEmail;//Email地址
    private Integer userSex;//用户性别
    private Date userBirth;//用户生日
    private Integer userAge;//用户年龄
    private String userAddress;//用户地址
    private String userImages;//用户头像
    private String userHeight;//用户身高
    private String userWeight;//用户体重
    private String userInterest;//用户兴趣爱好
    private String userOccupation;//用户职业
    private String userSafeQuestion;//用户安全问题
    private String userSafeAnswer;//用户安全答案
    private Integer state;//用户状态

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public Integer getRecentPassTimes() {
        return recentPassTimes;
    }

    public void setRecentPassTimes(Integer recentPassTimes) {
        this.recentPassTimes = recentPassTimes;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public Integer getIsAutoLockscreen() {
        return isAutoLockscreen;
    }

    public void setIsAutoLockscreen(Integer isAutoLockscreen) {
        this.isAutoLockscreen = isAutoLockscreen;
    }

    public Integer getLockscreenInteval() {
        return lockscreenInteval;
    }

    public void setLockscreenInteval(Integer lockscreenInteval) {
        this.lockscreenInteval = lockscreenInteval;
    }

    public Integer getIsMultiLogin() {
        return isMultiLogin;
    }

    public void setIsMultiLogin(Integer isMultiLogin) {
        this.isMultiLogin = isMultiLogin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(String userInterest) {
        this.userInterest = userInterest;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserSafeQuestion() {
        return userSafeQuestion;
    }

    public void setUserSafeQuestion(String userSafeQuestion) {
        this.userSafeQuestion = userSafeQuestion;
    }

    public String getUserSafeAnswer() {
        return userSafeAnswer;
    }

    public void setUserSafeAnswer(String userSafeAnswer) {
        this.userSafeAnswer = userSafeAnswer;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
