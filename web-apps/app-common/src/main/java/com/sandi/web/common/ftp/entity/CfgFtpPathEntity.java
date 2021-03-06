package com.sandi.web.common.ftp.entity;


import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

/**
 * Created by lury on 2015/7/21.
 */
public class CfgFtpPathEntity extends BaseEntity {
    private String ftpPathCode;
    private String ftpCode;
    private String remotePath;
    private String remotePathHis;
    private String localPath;
    private String localPathHis;
    private Integer state;
    private Date doneDate;
    private Long creator;
    private Date createDate;
    private String remark;
    private Integer hisFlag;


    public String getFtpPathCode() {
        return ftpPathCode;
    }

    public void setFtpPathCode(String ftpPathCode) {
        this.ftpPathCode = ftpPathCode;
    }

    public String getFtpCode() {
        return ftpCode;
    }

    public void setFtpCode(String ftpCode) {
        this.ftpCode = ftpCode;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getRemotePathHis() {
        return remotePathHis;
    }

    public void setRemotePathHis(String remotePathHis) {
        this.remotePathHis = remotePathHis;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalPathHis() {
        return localPathHis;
    }

    public void setLocalPathHis(String localPathHis) {
        this.localPathHis = localPathHis;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getHisFlag() {
        return hisFlag;
    }

    public void setHisFlag(Integer hisFlag) {
        this.hisFlag = hisFlag;
    }
}
