package com.sandi.web.service;

import com.sandi.web.model.Role;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
public interface IRoleService {
    /**
     * 便利所有的管理员角色
     * @return
     */
    public List<Role> queryAllRole();

    /**
     * 增加角色信息
     * @param role
     * @return
     */
    public int addRoleInfo(Role role);

    /**
     * 根据角色编号查询角色名称
     * @param roleId
     * @return
     */
    public Role selectRoleNameByRoleId(int roleId);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public int deleteRoleByRoleId(int roleId);


    /**
     * 角色更新
     * @param role
     * @return
     */
    public int updateRoleByRoleId(Role role);
}
