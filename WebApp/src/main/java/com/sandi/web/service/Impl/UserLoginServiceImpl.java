package com.sandi.web.service.Impl;

import com.sandi.web.dao.IUserLoginDao;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 15049 on 2017-04-11.
 */
@Service
public class UserLoginServiceImpl implements IUserLoginService {
    @Autowired
    private IUserLoginDao userLoginDao;

    /**
     * 用户登录Service方法
     */
    public UserLogin selectUserLogin(UserLogin userLogin) {
        return userLoginDao.selectUserLogin(userLogin);
    }
    /**
     * 用户注册Service层方法
     */
    public int addUserLogin(UserLogin userLogin) {
        return userLoginDao.addUserLogin(userLogin);
    }
    /**
     * 用户注册Service层方法
     */
    public UserLogin insertUserLogin(UserLogin userLogin) {
        return userLoginDao.insertUserLogin(userLogin);
    }

    @Override
    public int updatePasswordByUserLoginId(UserLogin userLogin) {
        return userLoginDao.updatePasswordByUserLoginId(userLogin);
    }
}
