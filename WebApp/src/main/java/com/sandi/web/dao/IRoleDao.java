package com.sandi.web.dao;

import com.sandi.web.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Repository("roleDao")
public interface IRoleDao {
    /**
     * 便利所有的角色
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
     * 根据居然色编号查询角色名称
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
