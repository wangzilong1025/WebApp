package com.sandi.web.common.bp.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;
import java.util.Date;

public class EsBpDataColHisEntity extends BaseEntity {
    @Id
    private Long colId;//列编号

    private Long rowId;//行编号

    private Long dataId;//数据编号

    private Integer colNum;//列号

    private String colKey;//列key值

    private String colValue;//列value值

    private Date createDate;//创建时间

    private Long ext1;

    private String ext2;

    private Date hisDate;


    public Long getColId() {
        return colId;
    }

    public void setColId(Long colId) {
        this.colId = colId;
    }

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

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public String getColKey() {
        return colKey;
    }

    public void setColKey(String colKey) {
        this.colKey = colKey;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getExt1() {
        return ext1;
    }

    public void setExt1(Long ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public Date getHisDate() {
        return hisDate;
    }

    public void setHisDate(Date hisDate) {
        this.hisDate = hisDate;
    }
}