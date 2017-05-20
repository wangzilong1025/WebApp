package com.sandi.web.controller;

import com.sandi.web.model.Admin;
import com.sandi.web.model.AdminRole;
import com.sandi.web.service.IAdminRoleService;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 王子龙 on 2017-05-20.
 */
@RequestMapping("/adminRole")
@Controller
public class AdminRoleController {

    private static final Logger log = Logger.getLogger(AchievementController.class);

    long timeToken = System.currentTimeMillis();
    @Autowired
    private IAdminRoleService adminRoleService;

    @RequestMapping("/updateAdminRole")
    public String updateAdminRole(HttpServletRequest request, HttpSession session){
        log.info("进入updateAdminRole的方法!");
        try{
            log.info("进入updateAdminRole的try方法!");
            Admin admin = (Admin) session.getAttribute("admin");
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            if(roleId == 1){
                adminRole.setRoleId(roleId);
                adminRole.setAdminRoleNote("超级管理员");
                adminRole.setIsNotApproval(UtilStatic.STATIC_ZERO);
            }else if(roleId == 2){
                adminRole.setRoleId(roleId);
                adminRole.setIsNotApproval(UtilStatic.STATIC_ZERO);
                adminRole.setAdminRoleNote("管理员");
            }else if(roleId == 3){
                adminRole.setRoleId(roleId);
                adminRole.setAdminRoleNote("经理");
                adminRole.setIsNotApproval(UtilStatic.STATIC_ZERO);
            }
            adminRoleService.updateAdminRoleByAdminId(adminRole);
            return "jsp-behind/application-prompt";
        }catch(Exception e){
            log.error("进入updateAdminRole的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }
}
