package com.sandi.web.service;

import com.sandi.web.model.Admin;

public interface IAdminService {

    public Admin selectByAdminNameAndAdminPass(Admin admin);

    public Admin selectAdminLogin(Admin admin);

    public int insertAdminByAdminId(Admin admin);

    public Admin selectByAdminId(int adminId);

    public int updateAdminByAdminId(Admin admin);

}
