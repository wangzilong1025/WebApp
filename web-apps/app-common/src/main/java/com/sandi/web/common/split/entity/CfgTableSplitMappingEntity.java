package com.sandi.web.common.split.entity;


import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.annotation.Table;
import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * Created by dizl on 2015/6/2.
 */
@Table("cfg_table_split_mapping")
public class CfgTableSplitMappingEntity extends BaseEntity {
    @Id
    private Long mappingId;
    private String tableName;
    private String columnName;
    private String columnConvertClass;
    private Integer state;

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnConvertClass() {
        return columnConvertClass;
    }

    public void setColumnConvertClass(String columnConvertClass) {
        this.columnConvertClass = columnConvertClass;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
