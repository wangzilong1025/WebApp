package com.sandi.web.common.user.entity;

import java.io.Serializable;

/**
 * Created by 15049 on 2017-07-21.
 */
public class UserInfoEntityEntity implements Serializable{

    private UserInfoEntity userInfoEntity;

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }
}
