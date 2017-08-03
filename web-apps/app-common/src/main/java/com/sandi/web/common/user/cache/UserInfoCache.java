/**
 * $Id: UserInfoCache.java,v 1.0 2016/8/26 13:29 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.user.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.user.entity.UserInfoEntityEntity;
import com.sandi.web.common.user.service.interfaces.IUserInfoEntitySV;
import com.sandi.web.utils.common.SpringContextHolder;
import com.sandi.web.utils.sec.SecManage;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: UserInfoCache.java,v 1.1 2016/8/26 13:29 zhangrp Exp $
 *          Created on 2016/8/26 13:29
 */
public class UserInfoCache extends DefaultCache {

    @Override
    protected Map getData() throws Exception {
        Map retMap = new HashMap();
        /**
         * 获取用户信息，将获取到的用户信息放进Redis中。
         */
        IUserInfoEntitySV userInfoSV = SpringContextHolder.getBean(IUserInfoEntitySV.class);
        List<UserInfoInterface> userInfos = userInfoSV.getAllUserInfo();
        if(userInfos!=null && userInfos.size()>0){
            for(UserInfoInterface userInfo : userInfos){
                retMap.put(SecManage.SEC_PRE+ userInfo.getUserName(),userInfo);
            }
        }
        return retMap;
    }

    private UserInfoInterface getUserInfoInterface(UserInfoEntityEntity userRedisInfo) throws Exception{
        UserInfoInterface userInfoInterface = new UserInfoInterface();
        if (userRedisInfo != null) {
            userInfoInterface.setUserId(userRedisInfo.getUserInfoEntity().getUserId());
            userInfoInterface.setUserName(userRedisInfo.getUserInfoEntity().getUserName());
            userInfoInterface.setUserPass( userRedisInfo.getUserInfoEntity().getUserPass());
            userInfoInterface.setUserAddress(userRedisInfo.getUserInfoEntity().getUserAddress());
            userInfoInterface.setUserAge(userRedisInfo.getUserInfoEntity().getUserAge());
            userInfoInterface.setPhoneNumber(userRedisInfo.getUserInfoEntity().getPhoneNumber());
            userInfoInterface.setUserBirth(userRedisInfo.getUserInfoEntity().getUserBirth());
            userInfoInterface.setUserEmail(userRedisInfo.getUserInfoEntity().getUserEmail());
            userInfoInterface.setUserHeight(userRedisInfo.getUserInfoEntity().getUserHeight());
            userInfoInterface.setUserInterest(userRedisInfo.getUserInfoEntity().getUserInterest());
            userInfoInterface.setUserSafeAnswer(userRedisInfo.getUserInfoEntity().getUserSafeAnswer());
            userInfoInterface.setUserSafeQuestion(userRedisInfo.getUserInfoEntity().getUserSafeQuestion());
            userInfoInterface.setState(userRedisInfo.getUserInfoEntity().getState());
            userInfoInterface.setUserSex(userRedisInfo.getUserInfoEntity().getUserSex());
            userInfoInterface.setUserImages(userRedisInfo.getUserInfoEntity().getUserImages());
            userInfoInterface.setUserNick(userRedisInfo.getUserInfoEntity().getUserNick());
            userInfoInterface.setUserWeight(userRedisInfo.getUserInfoEntity().getUserWeight());
        }

        return userInfoInterface;
    }
}
