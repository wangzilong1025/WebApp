package com.sandi.web.service;

import com.sandi.web.model.Admin;

import java.util.List;

public interface IAdminService {

    public Admin selectByAdminNameAndAdminPass(Admin admin);

    public Admin selectAdminLogin(Admin admin);

    public int insertAdminByAdminId(Admin admin);

    public Admin selectByAdminId(int adminId);

    public int updateAdminByAdminId(Admin admin);

    /**
     * 查询全部的管理员信息
     * @return
     */
    public List<Admin> queryAllAdminInfo();

    /**
     * 便利所有正在使用的管理员账户
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
