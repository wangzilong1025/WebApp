/**
 * $Id: Operator.java,v 1.0 2016/9/7 13:57 dizl Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.sec.entity;

import java.io.Serializable;

/**
 * @author dizl
 * @version $Id: Operator.java,v 1.1 2016/9/7 13:57 dizl Exp $
 * Created on 2016/9/7 13:57
 */
public class Operator implements Serializable {
    private Long operatorId;//操作员编号
    private Long staffId;//员工编号
    private String staffName;//员工名称
    private String code;//操作员账号
    private Long organizeId;//组织编号
    private String districtId;//属地
    private String billId;//手机号码
    private String email;//邮箱
    private String acctEffectDate;//账号生效时间
    private String acctExpireDate;//账号失效时间

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Long organizeId) {
        this.organizeId = organizeId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcctEffectDate() {
        return acctEffectDate;
    }

    public void setAcctEffectDate(String acctEffectDate) {
        this.acctEffectDate = acctEffectDate;
    }

    public String getAcctExpireDate() {
        return acctExpireDate;
    }

    public void setAcctExpireDate(String acctExpireDate) {
        this.acctExpireDate = acctExpireDate;
    }
}
