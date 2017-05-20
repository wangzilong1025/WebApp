package com.sandi.web.model;

public class AdminRole {

    /**
     * 管理员角色ID
     */
    private int adminRoleId;
    /**
     * 管理员ID
     */
    private int adminId;
    /**
     * 角色ID
     */
    private int roleId;
    /**
     * 管理员角色备注
     */
    private String adminRoleNote;
    /**
     * 审批人是否同意
     */
    private int isNotApproval;


    public int getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(int adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAdminRoleNote() {
        return adminRoleNote;
    }

    public void setAdminRoleNote(String adminRoleNote) {
        this.adminRoleNote = adminRoleNote;
    }

    public int getIsNotApproval() {
        return isNotApproval;
    }

    public void setIsNotApproval(int isNotApproval) {
        this.isNotApproval = isNotApproval;
    }

    @Override
    public String toString() {
        return "AdminRole{" +
                "adminRoleId=" + adminRoleId +
                ", adminId=" + adminId +
                ", roleId=" + roleId +
                ", adminRoleNote='" + adminRoleNote + '\'' +
                ", isNotApproval=" + isNotApproval +
                '}';
    }
}
