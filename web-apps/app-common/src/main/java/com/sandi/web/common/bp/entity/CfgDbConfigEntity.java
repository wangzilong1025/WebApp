package com.sandi.web.common.bp.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class CfgDbConfigEntity extends BaseEntity {
	@Id
	private Long dbConfigId;//配置编号

	private String dbConfigName;//配置名称

	private String userName;//用户名

	private String password;//用户密码

	private String host;//主机地址

	private Long port;//端口号

	private String sid;//sid

	private Integer state;//状态:1有效0无效

	private String remark;//备注

	private Date createDate;//创建时间

	private Date doneDate;//操作时间

	private Long creator;//创建人

	private Long opId;//操作人

	public Long getDbConfigId() {return dbConfigId;}

	public void setDbConfigId(Long dbConfigId) {this.dbConfigId = dbConfigId;}

	public String getDbConfigName() {return dbConfigName;}

	public void setDbConfigName(String dbConfigName) {this.dbConfigName = dbConfigName;}

	public String getUserName() {return userName;}

	public void setUserName(String userName) {this.userName = userName;}

	public String getPassword() {return password;}

	public void setPassword(String password) {this.password = password;}

	public String getHost() {return host;}

	public void setHost(String host) {this.host = host;}

	public Long getPort() {return port;}

	public void setPort(Long port) {this.port = port;}

	public String getSid() {return sid;}

	public void setSid(String sid) {this.sid = sid;}

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public String getRemark() {return remark;}

	public void setRemark(String remark) {this.remark = remark;}

	public Date getCreateDate() {return createDate;}

	public void setCreateDate(Date createDate) {this.createDate = createDate;}

	public Date getDoneDate() {return doneDate;}

	public void setDoneDate(Date doneDate) {this.doneDate = doneDate;}

	public Long getCreator() {return creator;}

	public void setCreator(Long creator) {this.creator = creator;}

	public Long getOpId() {return opId;}

	public void setOpId(Long opId) {this.opId = opId;}

}