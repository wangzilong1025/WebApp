package com.sandi.web.common.his.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * Created by dizl on 2015/7/17.
 */
public class CfgHisTableConfigEntity extends BaseEntity {
    @Id
    private String tableName;//表名
    private Integer deleteType;//处理方式
    private String columnName;//字段标识
    private String deleteFlag;//删除值标识
    private String upbHisTableName;//历史表表名
    private String delHisTableName;//竣工表表名
    private String hisIdFlag;//是否有历史表主键值
    private Integer state;//状态

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(Integer deleteType) {
        this.deleteType = deleteType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getHisIdFlag() {
        return hisIdFlag;
    }

    public void setHisIdFlag(String hisIdFlag) {
        this.hisIdFlag = hisIdFlag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUpbHisTableName() {
        return upbHisTableName;
    }

    public void setUpbHisTableName(String upbHisTableName) {
        this.upbHisTableName = upbHisTableName;
    }

    public String getDelHisTableName() {
        return delHisTableName;
    }

    public void setDelHisTableName(String delHisTableName) {
        this.delHisTableName = delHisTableName;
    }
}
