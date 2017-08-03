package com.sandi.web.common.user.dao;

import com.sandi.web.common.persistence.annotation.Dao;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.user.entity.UserInfoEntity;

@Dao(UserInfoEntity.class)
public interface IUserInfoDao extends CrudDao<UserInfoEntity,Long>{
}