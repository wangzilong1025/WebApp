package com.sandi.web.common.cache.entity;

import com.sandi.web.common.persistence.annotation.Id;
import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * Created by dizl on 2015/6/5.
 */
public class CfgCacheLoadEntity extends BaseEntity {
    @Id
    private String cacheName;//缓存名称
    private Integer cacheType;//缓存加载方式
    private String cacheImplClass;//缓存处理类
    private String moduleName;//模块编号
    private String cronExpression;//定时解析表达式
    private String saveType;//数据存储类型，默认为以map方式存储，2-每个key单独存储
    private Integer state;//状态

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Integer getCacheType() {
        return cacheType;
    }

    public void setCacheType(Integer cacheType) {
        this.cacheType = cacheType;
    }

    public String getCacheImplClass() {
        return cacheImplClass;
    }

    public void setCacheImplClass(String cacheImplClass) {
        this.cacheImplClass = cacheImplClass;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }
}
