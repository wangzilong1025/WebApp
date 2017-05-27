package com.sandi.web.dao;

import com.sandi.web.model.RoleAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 王子龙 on 2017-04-13.
 */
@Repository("roleAuthorityDao")
public interface IRoleAuthorityDao {
    /**
     * 根据角色ID查询角色拥有的权限
     * @param roleId
     * @return
     */
    public List<RoleAuthority> sellectAuthorityByRoleId(int roleId);
}
