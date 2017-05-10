package com.sandi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandi.web.dao.IAdminDao;
import com.sandi.web.model.Admin;
import com.sandi.web.service.IAdminService;
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

}
