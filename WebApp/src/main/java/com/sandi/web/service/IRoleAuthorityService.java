package com.sandi.web.service;

import com.sandi.web.model.RoleAuthority;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
public interface IRoleAuthorityService {

    /**
     * 根绝角色ID查询角色拥有的权限
     * @param roleId
     * @return
     */
    public List<RoleAuthority> sellectAuthorityByRoleId(int roleId);
}
