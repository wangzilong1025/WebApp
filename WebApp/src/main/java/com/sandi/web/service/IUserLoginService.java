package com.sandi.web.service;

import com.sandi.web.model.UserLogin;

/**
 * Created by 15049 on 2017-04-11.
 */
public interface IUserLoginService {

    /**
     * 用户登录服务层接口selectUserLogin
     * @param userLogin
     * @return
     */
    public UserLogin selectUserLogin(UserLogin userLogin);
    /**
     * 用户添加服务层接口addUserLogin
     * @param userLogin
     * @return
     */
    public int addUserLogin(UserLogin userLogin);
    /**
     * 用户添加服务层接口insertUserLogin
     * @param userLogin
     * @return
     */
    public UserLogin insertUserLogin(UserLogin userLogin);

    /**
     * 用户更新密码方法
     * @param userLogin
     * @return
     */
    public int updatePasswordByUserLoginId(UserLogin userLogin);
}
