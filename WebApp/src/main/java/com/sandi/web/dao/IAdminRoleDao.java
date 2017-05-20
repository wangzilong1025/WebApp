package com.sandi.web.dao;

import com.sandi.web.model.AdminRole;
import org.springframework.stereotype.Repository;

/**
 * Created by 15049 on 2017-04-13.
 */
@Repository("adminRoleDao")
public interface IAdminRoleDao {
    /**
     * 管理员角色添加信息
     * @param adminRole
     * @return
     */
    public int addAdminRoleInfo(AdminRole adminRole);

    /**
     * 通过管理员的编号查询管理员的角色
     * @param adminId
     * @return
     */
    public AdminRole selectAdminRoleByAdminId(int adminId);

    /**
     * 管理员提交自己选择的角色
     * @param adminRole
     * @return
     */
    public int updateAdminRoleByAdminId(AdminRole adminRole);
}
