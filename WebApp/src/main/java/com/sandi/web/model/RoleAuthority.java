package com.sandi.web.model;

public class RoleAuthority {

    /**
     * 角色权限ID
     */
    private int roleAuthorityId;
    /**
     * 角色ID
     */
    private int roleId;
    /**
     * 权限ID
     */
    private int authorityId;
    /**
     * 角色权限备注
     */
    private String roleAuthorityNote;
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
    public int getAuthorityId() {
        return authorityId;
    }
    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }
    public String getRoleAuthorityNote() {
        return roleAuthorityNote;
    }
    public void setRoleAuthorityNote(String roleAuthorityNote) {
        this.roleAuthorityNote = roleAuthorityNote;
    }
    public RoleAuthority(int roleAuthorityId, int roleId, int authorityId,
                         String roleAuthorityNote) {
        super();
        this.roleAuthorityId = roleAuthorityId;
        this.roleId = roleId;
        this.authorityId = authorityId;
        this.roleAuthorityNote = roleAuthorityNote;
    }
    public RoleAuthority() {
        super();
    }
    @Override
    public String toString() {
        return "RoleAuthority [authorityId=" + authorityId
                + ", roleAuthorityId=" + roleAuthorityId
                + ", roleAuthorityNote=" + roleAuthorityNote + ", roleId="
                + roleId + "]";
    }

}
