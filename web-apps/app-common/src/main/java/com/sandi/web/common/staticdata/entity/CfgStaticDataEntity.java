package com.sandi.web.common.staticdata.entity;

import com.sandi.web.common.persistence.entity.BaseEntity;

/**
 * Created by dizl on 2015/6/12.
 */
public class CfgStaticDataEntity extends BaseEntity {

    private String codeType;
    private String codeValue;
    private String codeName;
    private String codeDesc;
    private Integer sortId;
    private Integer state;
    private String extendCode;
    private String extendCode2;
    private String extendCode3;
    /**
     * 父枚举值，枚举分类
     */
    private String parentCode;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    public String getExtendCode2() {
        return extendCode2;
    }

    public void setExtendCode2(String extendCode2) {
        this.extendCode2 = extendCode2;
    }

    public String getExtendCode3() {
        return extendCode3;
    }

    public void setExtendCode3(String extendCode3) {
        this.extendCode3 = extendCode3;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }


    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
