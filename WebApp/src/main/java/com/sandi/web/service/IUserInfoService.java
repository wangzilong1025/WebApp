package com.sandi.web.service;

import com.sandi.web.model.UserInfo;

/**
 * Created by 15049 on 2017-04-11.
 */
public interface IUserInfoService {

    /**
     * 根据用户ID去查询用户信息
     * @param userId
     * @return
     */
    public UserInfo selectByUserId(int userId);
    /**
     * 用户注册时将用户id放进用户信息表中
     * @param userInfo
     * @return
     */
    public int insertUserInfoByUserId(UserInfo userInfo);
    /**
     * 根据用户信息ID更新用户信息
     * @param userInfo
     * @return
     */
    public int updateUserInfoByUserInfoId(UserInfo userInfo);
}
