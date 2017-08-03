package com.sandi.web.common.ftp.entity;


import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.Date;

/**
 * Created by lury on 2015/7/21.
 */
public class CfgFtpEntity extends BaseEntity {
    private String ftpCode;
    private String hostIp;
    private Integer port;
    private String username;
    private String password;
    private String remark;
    private Integer state;
    private Date doneDate;
    private Date createDate;

    public String getFtpCode() {
        return ftpCode;
    }

    public void setFtpCode(String ftpCode) {
        this.ftpCode = ftpCode;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
