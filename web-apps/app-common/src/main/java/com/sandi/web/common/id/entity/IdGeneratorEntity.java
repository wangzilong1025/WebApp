package com.sandi.web.common.id.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.annotation.Table;
import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * Created by dizl on 2015/6/4.
 * 主键生成表
 */
@Table("cfg_id_generator")
public class IdGeneratorEntity extends BaseEntity {
    @Id
    private String tableName;//表名
    private Integer generatorType;//生成方式 1-系统控制 2-sequence
    private Long currValue;//当前值
    private Long minValue;//最小值
    private Long maxValue;//最大值
    private Integer stepBy;//步长
    private String cycleFlag;//循环标识 Y标识循环 N标识不循环
    private Integer cacheSize;//缓存大小
    private String sequenceName;//sequence名称
    private String sequenceCreateScript;//sequence创建脚本
    private String generatorClass;//主键处理类
    private Integer state;//状态

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(Integer generatorType) {
        this.generatorType = generatorType;
    }

    public Long getCurrValue() {
        return currValue;
    }

    public void setCurrValue(Long currValue) {
        this.currValue = currValue;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getStepBy() {
        return stepBy;
    }

    public void setStepBy(Integer stepBy) {
        this.stepBy = stepBy;
    }

    public String getCycleFlag() {
        return cycleFlag;
    }

    public void setCycleFlag(String cycleFlag) {
        this.cycleFlag = cycleFlag;
    }

    public Integer getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(Integer cacheSize) {
        this.cacheSize = cacheSize;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSequenceCreateScript() {
        return sequenceCreateScript;
    }

    public void setSequenceCreateScript(String sequenceCreateScript) {
        this.sequenceCreateScript = sequenceCreateScript;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGeneratorClass() {
        return generatorClass;
    }

    public void setGeneratorClass(String generatorClass) {
        this.generatorClass = generatorClass;
    }
}
