package com.sandi.web.service.Impl;

import com.sandi.web.dao.IUserInfoDao;
import com.sandi.web.model.UserInfo;
import com.sandi.web.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 15049 on 2017-04-11.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private IUserInfoDao userInfoDao;
//    public IUserInfoDao getUserInfoDao() {
//        return userInfoDao;
//    }
//    public void setUserInfoDao(IUserInfoDao userInfoDao) {
//        this.userInfoDao = userInfoDao;
//    }
    @Override
    public UserInfo selectByUserId(int userId) {
        return userInfoDao.selectByUserId(userId);
    }
    @Override
    public int insertUserInfoByUserId(UserInfo userInfo) {
        return userInfoDao.insertUserInfoByUserId(userInfo);
    }
    @Override
    public int updateUserInfoByUserInfoId(UserInfo userInfo) {
        return userInfoDao.updateUserInfoByUserInfoId(userInfo);
    }
}
