package com.sandi.web.dao;

import com.sandi.web.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 查询全部的管理员信息
     * @return
     */
    public List<Admin> queryAllAdminInfo();

    /**
     * 便利所有正在使用的管理员
     * @return
     */
    public List<Admin> queryAllAdminInfoForApproval(int adminStatus);

    /**
     * 查询用户注册时是否有相同的名字已经存在，如果存在提示用户重新输入
     * @param adminName
     * @return
     */
    public Admin selectAdminAddByAdminName(String adminName);

}
