package com.sandi.web.dao;

import com.sandi.web.model.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by 15049 on 2017-04-11.
 */
@Repository("userInfoDao")
public interface IUserInfoDao {
    public UserInfo selectByUserId(int userId);

    public int insertUserInfoByUserId(UserInfo userInfo);

    public int updateUserInfoByUserInfoId(UserInfo userInfo);
}