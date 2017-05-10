package com.sandi.web.controller;

import com.sandi.web.model.Admin;
import com.sandi.web.service.IAdminService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = Logger.getLogger(AdminController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IAdminService adminService;

    @RequestMapping("/adminLogin")
    public String adminLogin(Admin admin, HttpServletRequest request, HttpSession session){
        log.info(timeToken+"进入adminLogin方法!!!");
        Admin adminlog = adminService.selectAdminLogin(admin);
        try{
            log.info(timeToken+"进入adminLogin的try方法!!!");
            if(adminlog!=null){
                log.info(timeToken+"管理员的状态:"+adminlog.getAdminStatus());
                if(adminlog.getAdminStatus()==0){
                    log.info(timeToken+"进入管理员的状态判断if条件语句!!!");
                    session.setAttribute("admin", adminlog);
                    request.setAttribute("message", "欢迎您"+adminlog.getAdminName());
                    return "jsp-behind/admin-index";
                }else{
                    log.info(timeToken+"进入管理员的状态判断else条件语句!!!");
                    request.setAttribute("message", "对不起您的状态不能登录,再去注册账号吧!!!");
                    return "jsp-behind/admin-regist";
                }
            }else{
                request.setAttribute("message","对不起，用户名密码不存在，请重新输入!!!");
                return "jsp-behind/admin-login";
            }
        }catch(Exception e){
            log.error(timeToken+"进入adminLogin的catch方法!!!"+e.getMessage());
            request.setAttribute("message", "对不起出错了!!!");
            return "jsp-error/error-page";
        }
    }

    @RequestMapping("/addAdmin")
    public String addAdmin(Admin admin,HttpServletRequest request){
        log.info(timeToken+"进入addAdmin方法!!!");
        try{
            log.info(timeToken+"进入addAdmin的try方法!!!");
            //管理员的状态是0代表管理首次注册不能操作任何东西
            //管理员的状态是1,2,3,4,5等代表拥有权限
            admin.setAdminStatus(0);
            adminService.insertAdminByAdminId(admin);
            return "jsp-behind/admin-login";
        }catch(Exception e){
            log.error(timeToken+"进入addAdmin的catch方法!!!"+e.getMessage());
            return "jsp-behind/admin-regist";
        }
    }
    @RequestMapping("/selectAdminInfo")
    public String selectAdminInfo(ModelMap modelMap, HttpSession session){
        log.info(timeToken+"进入selectAdminInfo的方法中!!!");
        try{
            log.info(timeToken+"进入selectAdminInfo的try方法!!!");
            Admin admin = (Admin) session.getAttribute("admin");
            Admin adminInfo = adminService.selectByAdminId(admin.getAdminId());
            modelMap.put("adminInfo",adminInfo);
            log.info(timeToken+"数据:"+adminInfo);
            return "jsp-behind/admin-information";
        }catch(Exception e){
            log.error(timeToken+"进入selectAdminInfo的catch方法!!!"+e.getMessage());
            return "jsp-error/error-page";
        }
    }
    @RequestMapping("/updateAdminInfo")
    public String updateAdminInfo(Admin admin,HttpSession session,ModelMap modelMap){
        log.info("进入updateAdminInfo方法!!!");
        try{
            log.info("进入updateAdminInfo的try方法!!!");
            Admin adminlog = (Admin) session.getAttribute("admin");
            admin.setAdminId(adminlog.getAdminId());
            System.out.print("这是传入的管理员信息:"+admin);
            adminService.updateAdminByAdminId(admin);
            modelMap.put("adminInfo",admin);
            return "redirect:/selectAdminInfo";
        }catch(Exception e){
            log.error("进入updateAdminInfo的catch方法!!!");
            return "jsp-error/error-page";
        }
    }
}
