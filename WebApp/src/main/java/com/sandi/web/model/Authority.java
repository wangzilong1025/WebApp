package com.sandi.web.model;

public class Authority {

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

    @Override
    public String toString() {
        return "Authority{" +
                "authorityId=" + authorityId +
                ", authorityName='" + authorityName + '\'' +
                ", authorityNote='" + authorityNote + '\'' +
                ", authorityState=" + authorityState +
                '}';
    }
}
