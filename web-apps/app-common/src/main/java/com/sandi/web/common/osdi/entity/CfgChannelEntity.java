package com.sandi.web.common.osdi.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

public class CfgChannelEntity extends BaseEntity {
    @Id
    private Long channelId;

    private String busiCode;

    private String interfaceId;

    private String interfaceType;

    private String defalutOpCode;

    private Long defalutOpId;

    private Long state;

    private String notes;

    private String ext1;

    private String ext2;

    private Long ext3;

    private Long ext4;

    private String checkRule;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getDefalutOpCode() {
        return defalutOpCode;
    }

    public void setDefalutOpCode(String defalutOpCode) {
        this.defalutOpCode = defalutOpCode;
    }

    public Long getDefalutOpId() {
        return defalutOpId;
    }

    public void setDefalutOpId(Long defalutOpId) {
        this.defalutOpId = defalutOpId;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public Long getExt3() {
        return ext3;
    }

    public void setExt3(Long ext3) {
        this.ext3 = ext3;
    }

    public Long getExt4() {
        return ext4;
    }

    public void setExt4(Long ext4) {
        this.ext4 = ext4;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

}