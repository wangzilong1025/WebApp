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
    public AdminRole(int adminRoleId, int adminId, int roleId,
                     String adminRoleNote) {
        super();
        this.adminRoleId = adminRoleId;
        this.adminId = adminId;
        this.roleId = roleId;
        this.adminRoleNote = adminRoleNote;
    }
    public AdminRole() {
        super();
    }
    @Override
    public String toString() {
        return "AdminRole [adminId=" + adminId + ", adminRoleId=" + adminRoleId
                + ", adminRoleNote=" + adminRoleNote + ", roleId=" + roleId
                + "]";
    }

}
