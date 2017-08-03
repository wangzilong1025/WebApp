package com.sandi.web.common.user.dao;

import com.sandi.web.common.persistence.dao.BaseDao;
import com.sandi.web.utils.sec.entity.UserInfoInterface;

import java.util.List;

/**
 * Created by 15049 on 2017-07-22.
 */

public interface IUserInfoEntityDao extends BaseDao{
    public List<UserInfoInterface> getAllUserInfo() throws Exception;


}
