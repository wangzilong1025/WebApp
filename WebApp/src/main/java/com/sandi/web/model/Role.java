package com.sandi.web.model;

public class Role {

    /**
     * 角色ID
     */
    private int roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色备注
     */
    private String roleNote;
    /**
     * 角色使用状态
     */
    private int roleState;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote;
    }

    public int getRoleState() {
        return roleState;
    }

    public void setRoleState(int roleState) {
        this.roleState = roleState;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleNote='" + roleNote + '\'' +
                ", roleState=" + roleState +
                '}';
    }
}
