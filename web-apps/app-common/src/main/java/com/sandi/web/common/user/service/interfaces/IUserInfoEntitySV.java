package com.sandi.web.common.user.service.interfaces;

import com.sandi.web.common.persistence.service.CrudService;
import com.sandi.web.common.user.entity.UserInfoEntity;
import com.sandi.web.common.user.entity.UserInfoEntityEntity;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import java.util.List;

/**
 * Created by 15049 on 2017-07-21.
 */
public interface IUserInfoEntitySV extends CrudService {
    /**
     * 获取所有有效操作员信息
     * */
    public List<UserInfoInterface> getAllUserInfo() throws Exception;
    UserInfoEntityEntity getUserInfoByUserId(long userId)throws Exception;
    UserInfoEntityEntity getUserInfoByUserInfoEntity(UserInfoEntity userInfoEntity)throws Exception;
}
