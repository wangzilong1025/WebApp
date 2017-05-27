package com.sandi.web.vo;

/**
 * Created by 王子龙 on 2017-05-23.
 */
public class AdminAuthority {

    /**
     * 权限编号
     */
    private int authorityId;
    /**
     * 权限名称
     */
    private String authorityName;
    /**
     * 权限备注
     */
    private String authorityNote;
    /**
     * 权限状态
     */
    private int authorityState;
    /**
     * 角色权限ID
     */
    private int roleAuthorityId;
    /**
     * 角色ID
     */
    private int roleId;
    /**
     * 角色权限备注
     */
    private String roleAuthorityNote;


    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityNote() {
        return authorityNote;
    }

    public void setAuthorityNote(String authorityNote) {
        this.authorityNote = authorityNote;
    }

    public int getAuthorityState() {
        return authorityState;
    }

    public void setAuthorityState(int authorityState) {
        this.authorityState = authorityState;
    }

    public int getRoleAuthorityId() {
        return roleAuthorityId;
    }

    public void setRoleAuthorityId(int roleAuthorityId) {
        this.roleAuthorityId = roleAuthorityId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleAuthorityNote() {
        return roleAuthorityNote;
    }

    public void setRoleAuthorityNote(String roleAuthorityNote) {
        this.roleAuthorityNote = roleAuthorityNote;
    }

    @Override
    public String toString() {
        return "AdminAuthority{" +
                "authorityId=" + authorityId +
                ", authorityName='" + authorityName + '\'' +
                ", authorityNote='" + authorityNote + '\'' +
                ", authorityState=" + authorityState +
                ", roleAuthorityId=" + roleAuthorityId +
                ", roleId=" + roleId +
                ", roleAuthorityNote='" + roleAuthorityNote + '\'' +
                '}';
    }
}
