package com.sandi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandi.web.dao.IAdminDao;
import com.sandi.web.model.Admin;
import com.sandi.web.service.IAdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService{

    @Autowired
    private IAdminDao adminDao;

    public IAdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(IAdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin selectByAdminNameAndAdminPass(Admin admin) {
        return adminDao.selectByAdminNameAndAdminPass(admin);
    }

    @Override
    public Admin selectAdminLogin(Admin admin) {

        return adminDao.selectAdminLogin(admin);
    }

    @Override
    public int insertAdminByAdminId(Admin admin) {
        return adminDao.insertAdminByAdminId(admin);
    }

    @Override
    public Admin selectByAdminId(int adminId) {
        return adminDao.selectByAdminId(adminId);
    }

    @Override
    public int updateAdminByAdminId(Admin admin) {
        return adminDao.updateAdminByAdminId(admin);
    }

    /**
     * 查询全部的管理员信息
     * @return
     */
    @Override
    public List<Admin> queryAllAdminInfo() {
        return adminDao.queryAllAdminInfo();
    }

    /**
     * 便利所有的正在使用的管理员账号
     * @param adminStatus
     * @return
     */
    @Override
    public List<Admin> queryAllAdminInfoForApproval(int adminStatus) {
        return adminDao.queryAllAdminInfoForApproval(adminStatus);
    }

    @Override
    public Admin selectAdminAddByAdminName(String adminName) {
        return adminDao.selectAdminAddByAdminName(adminName);
    }

}
