package com.sandi.web.dao;

import com.sandi.web.model.UserLogin;
import org.springframework.stereotype.Repository;

/**
 * Created by 15049 on 2017-04-11.
 */
@Repository("userLoginDao")
public interface IUserLoginDao {
    /**
     * 用户登录Dao层方法selectUserLogin
     * @param userLogin
     * @return UserLogin
     */
    public UserLogin selectUserLogin(UserLogin userLogin);
    /**
     * 用户添加Dao层方法addUserLogin
     * @param userLogin
     * @return userLogin
     */
    public int addUserLogin(UserLogin userLogin);
    /**
     * 用户添加Dao层方法insertUserLogin
     * @param userLogin
     * @return
     */
    public UserLogin insertUserLogin(UserLogin userLogin);
}
