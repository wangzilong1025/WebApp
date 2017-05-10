package com.sandi.web.dao;

import com.sandi.web.model.Admin;
import org.springframework.stereotype.Repository;

@Repository("adminDao")
public interface IAdminDao {

    public Admin selectByAdminNameAndAdminPass(Admin admin);

    //public int addAdminInfo(Admin admin);
    /*
    管理员登录方法
     */
    public Admin selectAdminLogin(Admin admin);
    /*
    管理员注册方法
     */
    public int insertAdminByAdminId(Admin admin);
    /*
    根据管理员ID查看信息
     */
    public Admin selectByAdminId(int adminId);
    /*
    更新管理员信息
     */
    public int updateAdminByAdminId(Admin admin);

}
