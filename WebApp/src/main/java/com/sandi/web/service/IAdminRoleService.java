package com.sandi.web.service;

import com.sandi.web.model.AdminRole;

/**
 * Created by 王子龙 on 2017-04-13.
 */
public interface IAdminRoleService {
    /**
     * 管理员角色的添加
     * @param adminRole
     * @return
     */
    public int addAdminRoleInfo(AdminRole adminRole);

    /**
     * 根据管理员的ID查询管理员的角色
     * @param adminRole
     * @return
     */
    public AdminRole selectAdminRoleByAdminId(int adminRole);

    /**
     * 用户提交自交选择的管理员角色
     * @param adminRole
     * @return
     */
    public int updateAdminRoleByAdminId(AdminRole adminRole);
}
