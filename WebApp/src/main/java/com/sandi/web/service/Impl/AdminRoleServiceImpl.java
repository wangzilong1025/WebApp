package com.sandi.web.service.Impl;

import com.sandi.web.dao.IAdminRoleDao;
import com.sandi.web.model.AdminRole;
import com.sandi.web.service.IAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 王子龙 on 2017-04-13.
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl implements IAdminRoleService{
    @Autowired
    private IAdminRoleDao adminRoleDao;

    public IAdminRoleDao getAdminRoleDao() {
        return adminRoleDao;
    }
    public void setAdminRoleDao(IAdminRoleDao adminRoleDao) {
        this.adminRoleDao = adminRoleDao;
    }

    /**
     * 管理员角色的添加
     * @param adminRole
     * @return
     */
    @Override
    public int addAdminRoleInfo(AdminRole adminRole) {
        return adminRoleDao.addAdminRoleInfo(adminRole);
    }

    /**
     * 根据管理员的ID，查询管理员的角色信息
     * @param adminRole
     * @return
     */
    @Override
    public AdminRole selectAdminRoleByAdminId(int adminRole) {
        return adminRoleDao.selectAdminRoleByAdminId(adminRole);
    }

    /**
     * 管理员提交自己选择的角色
     * @param adminRole
     * @return
     */
    @Override
    public int updateAdminRoleByAdminId(AdminRole adminRole) {
        return adminRoleDao.updateAdminRoleByAdminId(adminRole);
    }
}
