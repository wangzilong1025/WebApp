package com.sandi.web.vo;

/**
 * Created by 15049 on 2017-05-22.
 */
public class AdminVo {

    private int adminId;

    private String adminName;

    private String adminPassword;

    private String adminImage;

    private String adminEmail;

    private String adminPhone;

    private String adminAddress;

    private int adminStatus;

    private String realName;

    private int adminRoleId;

    private int roleId;

    private int isNotApproval;

    private int applicationState;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(int adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getIsNotApproval() {
        return isNotApproval;
    }

    public void setIsNotApproval(int isNotApproval) {
        this.isNotApproval = isNotApproval;
    }

    public int getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(int applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public String toString() {
        return "AdminVo{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminImage='" + adminImage + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminPhone='" + adminPhone + '\'' +
                ", adminAddress='" + adminAddress + '\'' +
                ", adminStatus=" + adminStatus +
                ", realName='" + realName + '\'' +
                ", adminRoleId=" + adminRoleId +
                ", roleId=" + roleId +
                ", isNotApproval=" + isNotApproval +
                ", applicationState=" + applicationState +
                '}';
    }
}
