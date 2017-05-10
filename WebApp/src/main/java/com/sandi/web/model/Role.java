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
    private String rolrNote;

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

    public String getRolrNote() {
        return rolrNote;
    }

    public void setRolrNote(String rolrNote) {
        this.rolrNote = rolrNote;
    }

    public Role(int roleId, String roleName, String rolrNote) {
        super();
        this.roleId = roleId;
        this.roleName = roleName;
        this.rolrNote = rolrNote;
    }

    public Role() {
        super();
    }

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", roleName=" + roleName
                + ", rolrNote=" + rolrNote + "]";
    }

}
