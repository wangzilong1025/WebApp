package com.sandi.web.common.split.entity;


import com.sandi.web.common.persistence.annotation.Entity;
import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.annotation.Table;
import com.sandi.web.common.persistence.entity.BaseEntity;

import java.util.List;

/**
 * Created by dizl on 2015/6/2.
 */
@Table("cfg_table_split")
public class CfgTableSplitEntity extends BaseEntity {
    @Id
    private String tableName;
    private String tableNameExpr;
    private Integer state;

    @Entity(clazz = CfgTableSplitMappingEntity.class, relAttr = "tableName", autoLoad = true)
    private List<CfgTableSplitMappingEntity> cfgTableSplitMappingEntitys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameExpr() {
        return tableNameExpr;
    }

    public void setTableNameExpr(String tableNameExpr) {
        this.tableNameExpr = tableNameExpr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<CfgTableSplitMappingEntity> getCfgTableSplitMappingEntitys() {
        return cfgTableSplitMappingEntitys;
    }

    public void setCfgTableSplitMappingEntitys(List<CfgTableSplitMappingEntity> cfgTableSplitMappingEntitys) {
        this.cfgTableSplitMappingEntitys = cfgTableSplitMappingEntitys;
    }
}
