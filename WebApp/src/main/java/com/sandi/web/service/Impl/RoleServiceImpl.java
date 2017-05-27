package com.sandi.web.service.Impl;

import com.sandi.web.dao.IRoleDao;
import com.sandi.web.model.Role;
import com.sandi.web.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 15049 on 2017-04-13.
 */
@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private IRoleDao roleDao;

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * 便利所有的角色
     * @return
     */
    @Override
    public List<Role> queryAllRole() {
        return roleDao.queryAllRole();
    }

    /**
     * 增加角色信息
     * @param role
     * @return
     */
    @Override
    public int addRoleInfo(Role role) {
        return roleDao.addRoleInfo(role);
    }

    /**
     * 根据角色编号查询角色名称
     * @param roleId
     * @return
     */
    @Override
    public Role selectRoleNameByRoleId(int roleId) {
        return roleDao.selectRoleNameByRoleId(roleId);
    }

    /**
     * 删除角色方法
     * @param roleId
     * @return
     */
    @Override
    public int deleteRoleByRoleId(int roleId) {
        return roleDao.deleteRoleByRoleId(roleId);
    }

    /**
     * 角色更新
     * @param role
     * @return
     */
    @Override
    public int updateRoleByRoleId(Role role) {
        return roleDao.updateRoleByRoleId(role);
    }
}
