package com.sandi.web.model;

import java.io.Serializable;

/**
 * 用户登录信息
 * @author 15049
 *
 */
public class UserLogin implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户状态(用户为2就是普通用户，用户为1就是管理员，用户为0就是超级管理员)
     */
    private int userStatus;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public int getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
    public UserLogin(int userId, String userName, String userPassword,
                     int userStatus) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
    }
    public UserLogin() {
        super();
    }
    @Override
    public String toString() {
        return "UserLogin [userId=" + userId + ", userName=" + userName
                + ", userPassword=" + userPassword + ", userStatus="
                + userStatus + "]";
    }

}
