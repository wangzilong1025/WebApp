package com.sandi.web.service.Impl;

import com.sandi.web.dao.IRoleAuthorityDao;
import com.sandi.web.model.RoleAuthority;
import com.sandi.web.service.IRoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Service
public class RoleAuthorityServiceImpl implements IRoleAuthorityService{

    @Autowired
    private IRoleAuthorityDao roleAuthorityDao;

    public IRoleAuthorityDao getRoleAuthorityDao() {
        return roleAuthorityDao;
    }

    public void setRoleAuthorityDao(IRoleAuthorityDao roleAuthorityDao) {
        this.roleAuthorityDao = roleAuthorityDao;
    }

    /**
     * 根据角色ID查询角色拥有的权限
     * @param roleId
     * @return
     */
    @Override
    public List<RoleAuthority> sellectAuthorityByRoleId(int roleId) {
        return roleAuthorityDao.sellectAuthorityByRoleId(roleId);
    }
}
