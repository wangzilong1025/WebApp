package com.sandi.web.common.persistence;

import com.alibaba.druid.pool.DruidDataSource;
import com.sandi.web.utils.security.K;

import java.sql.SQLException;

/**
 * Created by lijie9 on 2016/1/16.
 */

public class DruidDataSource4AI extends DruidDataSource {
    protected void initCheck() throws SQLException {
        super.initCheck();
        initPassword();
    }

    protected void initPassword() throws SQLException {
        try {
            String pwd = K.k_s(this.getPassword());
            this.setPassword(pwd);
        } catch (Exception e) {
            throw new SQLException("decrypt password error.");
        }
    }
}

