package com.sandi.web.common.bp.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class EsBpDataEntity extends BaseEntity {
    @Id
    private Long dataId;//数据编号

    private Long fileInputId;//文件编号

    private Integer priority;//优先级

    private Long templateId;//批量模板编号

    private Long headerTotal;//文件头行数

    private Long dataTotal;//数据总条数

    private Long dataSuccess;//数据处理成功条数

    private Long dataError;//数据处理失败条数

    private Integer state;//状态:1等待文件解析入库，2文件解析入库中，3文件解析入库成功等待业务处理，4文件解析入库失败，5业务处理中，6业务处理成功，7业务处理失败

    private String remark;//备注

    private Date createDate;//创建时间

    private Date doneDate;//操作时间

    private Long creator;//创建人

    private String busiParam;//业务处理参数json格式

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getFileInputId() {
        return fileInputId;
    }

    public void setFileInputId(Long fileInputId) {
        this.fileInputId = fileInputId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(Long dataTotal) {
        this.dataTotal = dataTotal;
    }

    public Long getDataSuccess() {
        return dataSuccess;
    }

    public void setDataSuccess(Long dataSuccess) {
        this.dataSuccess = dataSuccess;
    }

    public Long getDataError() {
        return dataError;
    }

    public void setDataError(Long dataError) {
        this.dataError = dataError;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getBusiParam() {
        return busiParam;
    }

    public void setBusiParam(String busiParam) {
        this.busiParam = busiParam;
    }

    public Long getHeaderTotal() {
        return headerTotal;
    }

    public void setHeaderTotal(Long headerTotal) {
        this.headerTotal = headerTotal;
    }
}