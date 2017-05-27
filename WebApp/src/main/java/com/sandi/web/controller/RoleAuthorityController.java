package com.sandi.web.controller;

import com.sandi.web.model.*;
import com.sandi.web.service.*;
import com.sandi.web.util.UtilStatic;
import com.sandi.web.vo.AdminAuthority;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王子龙 on 2017-05-23.
 */
@Controller
@RequestMapping("/roleAuthority")
public class RoleAuthorityController {

    private static final Logger log = Logger.getLogger(RoleAuthorityController.class);

    long timeToken = System.currentTimeMillis();

    @Autowired
    private IRoleAuthorityService roleAuthorityService;

    @Autowired
    private IAuthorityService authorityService;

    @Autowired
    private IAdminRoleService adminRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAdminService adminService;

    /**
     * 根据管理人员的ID去查询管理人员的相关信息和权限
     * 相关页面  =====================================================admin-authority.jsp
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/selectAdminAuthorityByRoleId")
    public String selectAdminAuthorityByRoleId(ModelMap modelMap, HttpSession session){
        log.info("进入selectAdminAuthorityByRoleId方法!");
        try{
            log.info("进入selectAdminAuthorityByRoleId的try方法!");
            Admin admin  = (Admin) session.getAttribute("admin");
            Admin adminInfo = adminService.selectByAdminId(admin.getAdminId());
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
            System.out.print("输出角色信息:"+adminRole);
            if((adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) ||(adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) ||(adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE)){
                Role role = roleService.selectRoleNameByRoleId(adminRole.getRoleId());
                List<RoleAuthority> roleAuthority = roleAuthorityService.sellectAuthorityByRoleId(adminRole.getRoleId());
                List<AdminAuthority> adminAuthorities = new ArrayList<AdminAuthority>();
                for(RoleAuthority list:roleAuthority){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority au = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityName(au.getAuthorityName());
                    adminAuthority.setAuthorityNote(au.getAuthorityNote());
                    adminAuthority.setAuthorityState(au.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorities.add(adminAuthority);
                }
                modelMap.put("adminRole",adminRole);
                modelMap.put("admin",adminInfo);
                modelMap.put("role",role);
                modelMap.put("adminAuthorities",adminAuthorities);
            }else{
                int roleId=adminRole.getRoleId()+1;
                Role role = roleService.selectRoleNameByRoleId(roleId);
                List<RoleAuthority> roleAuthority = roleAuthorityService.sellectAuthorityByRoleId(roleId);
                List<AdminAuthority> adminAuthorities = new ArrayList<AdminAuthority>();
                for(RoleAuthority list:roleAuthority){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority au = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityName(au.getAuthorityName());
                    adminAuthority.setAuthorityNote(au.getAuthorityNote());
                    adminAuthority.setAuthorityState(au.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorities.add(adminAuthority);
                }
                modelMap.put("adminRole",adminRole);
                modelMap.put("admin",adminInfo);
                modelMap.put("role",role);
                modelMap.put("adminAuthorities",adminAuthorities);
            }
            return "jsp-behind/admin-authority";
        }catch(Exception e){
            log.error("进入selectAdminAuthorityByRoleId的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }

    }
}
