package com.sandi.web.common.user.service.impl;

import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.persistence.service.CrudServiceImpl;
import com.sandi.web.common.user.dao.IUserInfoDao;
import com.sandi.web.common.user.dao.IUserInfoEntityDao;
import com.sandi.web.common.user.entity.UserInfoEntity;
import com.sandi.web.common.user.entity.UserInfoEntityEntity;
import com.sandi.web.common.user.service.interfaces.IUserInfoEntitySV;
import com.sandi.web.common.user.service.interfaces.IUserInfoSV;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 15049 on 2017-07-21.
 */
@Service
public class UserInfoEntitySVImpl extends CrudServiceImpl implements IUserInfoEntitySV{
    @Autowired
    private IUserInfoEntityDao entityDao;

    @Autowired
    private IUserInfoDao dao;

    @Autowired
    private IUserInfoSV userInfoSV;

    @Override
    public CrudDao getDao() {
        return dao;
    }

    /**
     * 获取所有有效操作员信息
     * */
    @Override
    public List<UserInfoInterface> getAllUserInfo() throws Exception {
        return entityDao.getAllUserInfo();
    }

    @Override
    public UserInfoEntityEntity getUserInfoByUserId(long userId) throws Exception {
        UserInfoEntity userInfoEntity = (UserInfoEntity) userInfoSV.findById(userId);
        UserInfoEntityEntity userInfoEntityEntity = getUserInfoByUserInfoEntity(userInfoEntity);
        return userInfoEntityEntity;
    }

    @Override
    public UserInfoEntityEntity getUserInfoByUserInfoEntity(UserInfoEntity userInfoEntity) throws Exception {
        UserInfoEntityEntity userInfoEntityEntity = null;
        if(userInfoEntity == null){
            return null;
        }
        userInfoEntityEntity = new UserInfoEntityEntity();
        long userId = userInfoEntity.getUserId();
        userInfoEntityEntity.setUserInfoEntity(userInfoEntity);

        return userInfoEntityEntity;
    }
}
