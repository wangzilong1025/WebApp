package com.sandi.web.common.bp.entity;

import com.sandi.web.common.persistence.annotation.Entity;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;
import java.util.List;

public class EsBpDataRowHisEntity extends BaseEntity {
    @Id
    private Long rowId;//行编号

    private Long dataId;//数据编号

    private Integer rowNum;//行号

    private Integer state;//状态：1未处理，2处理成功，3处理失败

    private String remark;//备注

    private Date createDate;//创建时间

    private Date doneDate;//操作时间

    private Integer rowType;//0：文件头，1：文件内容

    private Date hisDate;

    @Entity(clazz = EsBpDataColHisEntity.class, relAttr = "rowId", autoLoad = true)
    private List<EsBpDataColHisEntity> esBpDataColHisEntityList;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
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

    public List<EsBpDataColHisEntity> getEsBpDataColHisEntityList() {
        return esBpDataColHisEntityList;
    }

    public void setEsBpDataColHisEntityList(List<EsBpDataColHisEntity> esBpDataColHisEntityList) {
        this.esBpDataColHisEntityList = esBpDataColHisEntityList;
    }

    public Integer getRowType() {
        return rowType;
    }

    public void setRowType(Integer rowType) {
        this.rowType = rowType;
    }

    public Date getHisDate() {
        return hisDate;
    }

    public void setHisDate(Date hisDate) {
        this.hisDate = hisDate;
    }
}