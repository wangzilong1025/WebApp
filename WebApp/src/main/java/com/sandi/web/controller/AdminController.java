package com.sandi.web.controller;

import com.sandi.web.model.*;
import com.sandi.web.service.*;
import com.sandi.web.util.UtilStatic;
import com.sandi.web.vo.AdminAuthority;
import com.sandi.web.vo.AdminVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    /**
     * 管理员状态：0代表该管理员已被禁用
     *            1代表超级管理员
     *            2代表管理员
     *            3代表部门经理
     */
    private static final Logger log = Logger.getLogger(AdminController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IAdminRoleService adminRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;
    @Autowired
    private IAuthorityService authorityService;

    @RequestMapping("/adminLogin")
    public String adminLogin(Admin admin, HttpServletRequest request, HttpSession session,ModelMap modelMap){
        log.info(timeToken+"进入adminLogin方法!!!");
        Admin adminlog = adminService.selectAdminLogin(admin);
        try{
            log.info(timeToken+"进入adminLogin的try方法!!!");
            if(adminlog!=null){
                log.info(timeToken+"管理员的状态:"+adminlog.getAdminStatus());
                if(adminlog.getAdminStatus() != UtilStatic.STATIC_ZERO){

                    log.info(timeToken+"进入超级管理员的状态判断if条件语句，职位判断：=================超级管理员");
                    session.setAttribute("admin", adminlog);
                    AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(adminlog.getAdminId());

                    if(adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE){
                        log.info(timeToken+"进入超级管理员的状态判断if条件语句，职位判断：=================超级管理员");
                        List<RoleAuthority> roleAuthoritys = roleAuthorityService.sellectAuthorityByRoleId(adminRole.getRoleId());
                        session.setAttribute("map",roleAuthoritys);
                        modelMap.put("adminRole",adminRole);
                        session.setAttribute("adminRoleId",UtilStatic.STATIC_ONE);
                        request.setAttribute("message", "欢迎您"+adminlog.getAdminName());
                        return "jsp-behind/super-admin-index";
                    }else if((adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                        log.info(timeToken+"进入管理员的状态判断if条件语句，职位判断：=================管理员");
                        List<RoleAuthority> roleAuthoritys = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_TWO);
                        session.setAttribute("map",roleAuthoritys);
                        modelMap.put("adminRole",adminRole);
                        session.setAttribute("adminRoleId",UtilStatic.STATIC_TWO);
                        request.setAttribute("message", "欢迎您"+adminlog.getAdminName());
                        return "jsp-behind/admin-index";
                    }else if((adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                        log.info(timeToken+"进入经理的状态判断if条件语句，职位判断：=================经理");
                        List<RoleAuthority> roleAuthoritys = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_THREE);
                        session.setAttribute("map",roleAuthoritys);
                        modelMap.put("adminRole",adminRole);
                        session.setAttribute("adminRoleId",UtilStatic.STATIC_THREE);
                        request.setAttribute("message", "欢迎您"+adminlog.getAdminName());
                        return "jsp-behind/manager-index";
                    }else if((adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                        log.info(timeToken+"进入无权人员管的状态判断if条件语句，职位判断：=================暂无权人员");
                        List<RoleAuthority> roleAuthoritys = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_FORE);
                        session.setAttribute("map",roleAuthoritys);
                        modelMap.put("adminRole",adminRole);
                        session.setAttribute("adminRoleId",UtilStatic.STATIC_FORE);
                        request.setAttribute("message", "欢迎您"+adminlog.getAdminName());
                        return "jsp-behind/manager-index";
                    }
                }else{
                    log.info(timeToken+"进入管理员的状态判断else条件语句!!!");
                    request.setAttribute("message", "对不起您的账号已被禁用，暂不能登录,再去注册账号吧!!!");
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
        return null;
    }

    @RequestMapping("/addAdmin")
    public String addAdmin(Admin admin,HttpServletRequest request){
        log.info(timeToken+"进入addAdmin方法!!!");
        Admin adminlog = adminService.selectAdminAddByAdminName(admin.getAdminName());
        try{
            log.info(timeToken + "进入addAdmin的try方法!!!");
            if(adminlog == null) {
                log.info(timeToken + "进入addAdmin的try中的if判断条件!!!");
                /**
                 * 管理员状态：0代表该管理员已被禁用
                 *            1代表超级管理员
                 *            2代表管理员
                 *            3代表部门经理
                 */
                admin.setAdminStatus(UtilStatic.STATIC_ONE);
                admin.setApplicationState(UtilStatic.STATIC_ZERO);
                adminService.insertAdminByAdminId(admin);

                Admin adm = adminService.selectAdminLogin(admin);
                log.info("admin的ID号为:" + adm.getAdminId());
                AdminRole adminRole = new AdminRole();
                int roleId = UtilStatic.STATIC_FORE;
                String roleNote = "暂无权限人员";
                adminRole.setAdminId(adm.getAdminId());
                adminRole.setRoleId(roleId);
                adminRole.setAdminRoleNote(roleNote);
                adminRole.setIsNotApproval(UtilStatic.STATIC_ONE);
                adminRoleService.addAdminRoleInfo(adminRole);
                return "jsp-behind/admin-login";
            }else{
                log.info(timeToken + "进入addAdmin的try的else判断条件!");
                request.setAttribute("message","该账用户名已经有人使用了，请再换一个");
                return "jsp-behind/admin-regist";
            }
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
            Admin adminInfo = adminService.selectAdminLogin(adminlog);
            admin.setAdminId(adminInfo.getAdminId());
            admin.setAdminPassword(adminInfo.getAdminPassword());
            admin.setAdminStatus(adminInfo.getAdminStatus());
            System.out.print("这是传入的管理员信息:"+admin);
            adminService.updateAdminByAdminId(admin);
            modelMap.put("adminInfo",admin);
            return "redirect:/selectAdminInfo";
        }catch(Exception e){
            log.error("进入updateAdminInfo的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * 用户申请权限查到信息发送给管理员
     * 相关页面  ========================================application-authority.jsp
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/adminApplicationAuthority")
    public String adminApplicationAuthority(ModelMap modelMap, HttpSession session){
        log.info(timeToken+"进入selectAdminInfo的方法中!!!");
        try{
            log.info(timeToken+"进入selectAdminInfo的try方法!!!");
            Admin admin = (Admin) session.getAttribute("admin");
            Admin adminInfo = adminService.selectByAdminId(admin.getAdminId());
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
            if((adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE)||(adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE)){
                Role role = roleService.selectRoleNameByRoleId(adminRole.getRoleId());
                modelMap.put("adminInfo",adminInfo);
                modelMap.put("adminRole",adminRole);
                modelMap.put("role",role);
                modelMap.put("realRole",adminRole.getRoleId());
                log.info(timeToken+"数据:"+adminInfo);
            }else{
                int realRole = adminRole.getRoleId()+1;

                Role role = roleService.selectRoleNameByRoleId(realRole);
                modelMap.put("adminInfo",adminInfo);
                modelMap.put("adminRole",adminRole);
                modelMap.put("role",role);
                modelMap.put("realRole",realRole);
                log.info(timeToken+"数据:"+adminInfo);
            }

            return "jsp-behind/application-authority";
        }catch(Exception e){
            log.error(timeToken+"进入selectAdminInfo的catch方法!!!"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 相关页面===========================================================================query-down-admin.jsp
     * 查找当前管理员的下级的所有管理员（如超级管理员的所有下级为：管理员，经理，暂无权限人员）
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/queryAllDownAdminInfo")
    public String queryAllDownAdminInfo(ModelMap modelMap,HttpSession session){
        log.info(timeToken+"进入queryAllDownAdminInfo方法!");
        Admin admin = (Admin) session.getAttribute("admin");
        AdminRole adminRo = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
        try{
            List<Admin> adminList = adminService.queryAllAdminInfo();
            List<AdminVo> adminVos = new ArrayList<AdminVo>();
            //超级管理员的下级人员便利
            if(adminRo.getRoleId()==UtilStatic.STATIC_ONE && adminRo.getIsNotApproval()==UtilStatic.STATIC_ONE){
                for(Admin list:adminList){
                    AdminVo ad = new AdminVo();
                    AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if(!(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE)){
                        ad.setAdminId(list.getAdminId());
                        ad.setAdminName(list.getAdminName());
                        ad.setAdminEmail(list.getAdminEmail());
                        ad.setAdminAddress(list.getAdminAddress());
                        ad.setAdminPhone(list.getAdminPhone());
                        ad.setAdminStatus(list.getAdminStatus());
                        ad.setRealName(list.getRealName());
                        if(adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE){
                            ad.setRoleId(UtilStatic.STATIC_ONE);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_TWO);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_THREE);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_FORE);
                        }
                        adminVos.add(ad);
                    }
                }
            }
            //管理员的下级人员便利
            else if((adminRo.getRoleId()==UtilStatic.STATIC_TWO && adminRo.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRo.getRoleId()==UtilStatic.STATIC_ONE && adminRo.getIsNotApproval()== UtilStatic.STATIC_ZERO)){
                for(Admin list:adminList){
                    AdminVo ad = new AdminVo();
                    AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO) || (adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()== UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO) ||(adminRole.getRoleId()==UtilStatic.STATIC_FORE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE)){
                        ad.setAdminId(list.getAdminId());
                        ad.setAdminName(list.getAdminName());
                        ad.setAdminEmail(list.getAdminEmail());
                        ad.setAdminAddress(list.getAdminAddress());
                        ad.setAdminPhone(list.getAdminPhone());
                        ad.setAdminStatus(list.getAdminStatus());
                        ad.setRealName(list.getRealName());
                        if(adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE){
                            ad.setRoleId(UtilStatic.STATIC_ONE);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_TWO);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_THREE);
                        }else if((adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) || (adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            ad.setRoleId(UtilStatic.STATIC_FORE);
                        }
                        adminVos.add(ad);
                    }
                }
            }
            log.info(timeToken+"进入queryAllDownAdminInfo的try方法!");

            modelMap.put("adminVos",adminVos);
            return "jsp-behind/query-down-admin";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllDownAdminInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }




    /**
     * 查询所有的管理者如经理申请管理员的职位，管理员申请超级管理员的职位，或者经理直接申请超级管理员的便利========
     * @return
     */
    @RequestMapping("/queryAllApplicationState")
    public String queryAllApplicationState(ModelMap modelMap,HttpSession session){
        log.info(timeToken+"进入queryAllApplicationState方法!");
        try{
            log.info(timeToken+"进入queryAllApplicationState的try方法!");
            Admin adminLog = (Admin) session.getAttribute("admin");
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(adminLog.getAdminId());
            List<AdminVo> adminVoList = new ArrayList<AdminVo>();
            //这时候判断管理员的状态为超级管理员
            if(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE){
                int roleCenter = UtilStatic.STATIC_ONE;
                //查询状态为1的账号可以使用的管理员
                List<Admin> adminList = adminService.queryAllAdminInfoForApproval(UtilStatic.STATIC_ONE);
                for(Admin list:adminList){
                    AdminRole adminRoleTest = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if((adminRoleTest.getRoleId()==UtilStatic.STATIC_ONE&&adminRoleTest.getIsNotApproval()==UtilStatic.STATIC_ZERO) || (adminRoleTest.getRoleId()==UtilStatic.STATIC_TWO && adminRoleTest.getIsNotApproval()==UtilStatic.STATIC_ZERO) || (adminRoleTest.getRoleId()==UtilStatic.STATIC_THREE && adminRoleTest.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                        AdminVo adminVo = new AdminVo();
                        adminVo.setAdminId(list.getAdminId());
                        adminVo.setAdminName(list.getAdminName());
                        adminVo.setRealName(list.getRealName());
                        adminVo.setAdminPhone(list.getAdminPhone());
                        adminVo.setAdminEmail(list.getAdminEmail());
                        adminVo.setAdminAddress(list.getAdminAddress());
                        adminVo.setRoleId(adminRoleTest.getRoleId());
                        adminVo.setIsNotApproval(adminRoleTest.getIsNotApproval());
                        adminVoList.add(adminVo);
                    }
                }

            }
            //这时管理员的角色为管理员（所以只能审批申请管理员和申请经理权限的人）
            else if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE)||(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<Admin> adminList = adminService.queryAllAdminInfoForApproval(UtilStatic.STATIC_ONE);
                for(Admin list:adminList){
                    AdminRole adminRoleTest = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if((adminRoleTest.getRoleId()==UtilStatic.STATIC_TWO && adminRoleTest.getIsNotApproval()==UtilStatic.STATIC_ZERO) || (adminRoleTest.getRoleId()==UtilStatic.STATIC_THREE && adminRoleTest.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                        AdminVo adminVo = new AdminVo();
                        adminVo.setAdminId(list.getAdminId());
                        adminVo.setAdminName(list.getAdminName());
                        adminVo.setRealName(list.getRealName());
                        adminVo.setAdminPhone(list.getAdminPhone());
                        adminVo.setAdminEmail(list.getAdminEmail());
                        adminVo.setAdminAddress(list.getAdminAddress());
                        adminVo.setRoleId(adminRoleTest.getRoleId());
                        adminVo.setIsNotApproval(adminRoleTest.getIsNotApproval());
                        adminVoList.add(adminVo);
                    }
                }
            }
            //application-position
            modelMap.put("adminShow",adminVoList);
            return "jsp-behind/manageing-authority";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllApplicationState的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

    /**
     * 这个方法没用了2017-05-25日
     * 超级管理员审核经理或者管理员的请求（申请管理员或者超级管理员）
     * @param
     * @param adminId
     * @param //applicationState
     * @return
     */
    @RequestMapping("/agreeAuthorityForAdmin")
    public String agreeAuthorityForAdmin(@RequestParam("adminId") int adminId){
        log.info(timeToken+"进入agreeAuthorityForAdmin方法!");
        try{
            log.info(timeToken+"进入agreeAuthorityForAdmin的try方法!");
            Admin admin = adminService.selectByAdminId(adminId);
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(adminId);
            if(adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO){
                adminRole.setIsNotApproval(UtilStatic.STATIC_ONE);
                adminRoleService.updateAdminRoleByAdminId(adminRole);
                return "jsp-behind/agree-success-prompt";
            }else{
                return "jsp-behind/agree-fail-prompt";
            }
        }catch(Exception e){
            log.info(timeToken+"进入agreeAuthorityForAdmin的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 相关页面 ===================================================managed-authority.jsp
     * 对已经授权的管理员进行便利（该登录的管理员的下级所有的人）
     * @return
     */
    @RequestMapping("/queryAllAlreadyAuthorizedAdminInfo")
    public String queryAllAlreadyAuthorizedAdminInfo(HttpSession session,ModelMap modelMap){
        log.info(timeToken+"进入queryAllAlreadyAuthorizedAdminInfo方法!");
        try{
            log.info(timeToken+"进入queryAllAlreadyAuthorizedAdminInfo的try方法!");
            Admin admin = (Admin) session.getAttribute("admin");
            Admin adminInfo = adminService.selectByAdminId(admin.getAdminId());
            AdminRole adminRol = adminRoleService.selectAdminRoleByAdminId(adminInfo.getAdminId());
            List<AdminVo> adminVos = new ArrayList<AdminVo>();
            if(adminRol.getRoleId()==UtilStatic.STATIC_ONE && adminRol.getIsNotApproval()==UtilStatic.STATIC_ONE){
                List<Admin> adminList = adminService.queryAllAdminInfoForApproval(UtilStatic.STATIC_ONE);
                for(Admin list:adminList){
                    AdminVo adminVoList = new AdminVo();
                    AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    //查找登录的管理员下级（可以对他们进行操作）
                    if(!((adminRole.getRoleId()==UtilStatic.STATIC_FORE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO) ||(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE))){
                        adminVoList.setAdminId(list.getAdminId());
                        adminVoList.setAdminName(list.getAdminName());
                        adminVoList.setRealName(list.getRealName());
                        adminVoList.setAdminStatus(list.getAdminStatus());
                        adminVoList.setAdminPhone(list.getAdminPhone());
                        adminVoList.setAdminAddress(list.getAdminAddress());
                        adminVoList.setAdminEmail(list.getAdminEmail());
                        if(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE){
                            adminVoList.setRoleId(UtilStatic.STATIC_ONE);
                        }else if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId() ==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            adminVoList.setRoleId(UtilStatic.STATIC_TWO);
                        }else if((adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                            adminVoList.setRoleId(UtilStatic.STATIC_THREE);
                        }
                        adminVos.add(adminVoList);
                    }
                }
            }else if((adminRol.getRoleId()==UtilStatic.STATIC_TWO&&adminRol.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRol.getRoleId()==UtilStatic.STATIC_ONE && adminRol.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<Admin> adminList = adminService.queryAllAdminInfoForApproval(UtilStatic.STATIC_ONE);
                for(Admin list:adminList){
                    AdminVo adminVoList = new AdminVo();
                    AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    //查找登录的管理员下级（可以对他们进行操作）
                    /**
                     * !((adminRole.getRoleId()==UtilStatic.STATIC_FORE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO) ||(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||
                     (adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO))
                     */
                    if((adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                        adminVoList.setAdminId(list.getAdminId());
                        adminVoList.setAdminName(list.getAdminName());
                        adminVoList.setRealName(list.getRealName());
                        adminVoList.setAdminStatus(list.getAdminStatus());
                        adminVoList.setAdminPhone(list.getAdminPhone());
                        adminVoList.setAdminAddress(list.getAdminAddress());
                        adminVoList.setAdminEmail(list.getAdminEmail());
                        if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId() ==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                            adminVoList.setRoleId(UtilStatic.STATIC_TWO);
                        }else if((adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                            adminVoList.setRoleId(UtilStatic.STATIC_THREE);
                        }
                        adminVos.add(adminVoList);
                    }
                }
            }
            modelMap.put("adminVos",adminVos);
            return "jsp-behind/managed-authority";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAlreadyAuthorizedAdminInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 根据管理人员编号查询管理人员的详细信息和管理权限
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectAdminInfoAndAuthorityAndRole")
    public String selectAdminInfoAndAuthorityAndRole(@RequestParam("adminId") int adminId,ModelMap modelMap){
        log.info(timeToken+"进入selectAdminInfoAndAuthorityAndRole方法1");
        try{
            log.info(timeToken+"进入selectAdminInfoAndAuthorityAndRole的try方法!");
            Admin admin = adminService.selectByAdminId(adminId);
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(adminId);
            List<AdminAuthority> adminAuthorityList = new ArrayList<AdminAuthority>();
            AdminVo adminVo = new AdminVo();
            adminVo.setAdminId(admin.getAdminId());
            adminVo.setAdminName(admin.getAdminName());
            adminVo.setRealName(admin.getRealName());
            adminVo.setAdminPhone(admin.getAdminPhone());
            adminVo.setAdminAddress(admin.getAdminAddress());
            adminVo.setAdminEmail(admin.getAdminEmail());
            adminVo.setAdminStatus(admin.getAdminStatus());
            if(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ONE){
                List<RoleAuthority> roleAuthorityList = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_ONE);
                for(RoleAuthority list:roleAuthorityList){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority authority = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(authority.getAuthorityId());
                    adminAuthority.setAuthorityName(authority.getAuthorityName());
                    adminAuthority.setAuthorityNote(authority.getAuthorityNote());
                    adminAuthority.setAuthorityState(authority.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorityList.add(adminAuthority);
                }
                adminVo.setRoleId(UtilStatic.STATIC_ONE);
                modelMap.put("adminVo",adminVo);
                modelMap.put("adminAuthorityList",adminAuthorityList);
            }else if((adminRole.getRoleId() == UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<RoleAuthority> roleAuthorityList = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_TWO);
                for(RoleAuthority list:roleAuthorityList){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority authority = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(authority.getAuthorityId());
                    adminAuthority.setAuthorityName(authority.getAuthorityName());
                    adminAuthority.setAuthorityNote(authority.getAuthorityNote());
                    adminAuthority.setAuthorityState(authority.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorityList.add(adminAuthority);
                }
                adminVo.setRoleId(UtilStatic.STATIC_TWO);
                modelMap.put("adminVo",adminVo);
                modelMap.put("adminAuthorityList",adminAuthorityList);
            }else if((adminRole.getRoleId() == UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<RoleAuthority> roleAuthorityList = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_THREE);
                for(RoleAuthority list:roleAuthorityList){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority authority = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(authority.getAuthorityId());
                    adminAuthority.setAuthorityName(authority.getAuthorityName());
                    adminAuthority.setAuthorityNote(authority.getAuthorityNote());
                    adminAuthority.setAuthorityState(authority.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorityList.add(adminAuthority);
                }
                adminVo.setRoleId(UtilStatic.STATIC_ONE);
                modelMap.put("adminVo",adminVo);
                modelMap.put("adminAuthorityList",adminAuthorityList);
            }else if((adminRole.getRoleId() == UtilStatic.STATIC_FORE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId()==UtilStatic.STATIC_THREE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<RoleAuthority> roleAuthorityList = roleAuthorityService.sellectAuthorityByRoleId(UtilStatic.STATIC_FORE);
                for(RoleAuthority list:roleAuthorityList){
                    AdminAuthority adminAuthority = new AdminAuthority();
                    Authority authority = authorityService.selectAuthorityByAuthorityId(list.getAuthorityId());
                    adminAuthority.setAuthorityId(authority.getAuthorityId());
                    adminAuthority.setAuthorityName(authority.getAuthorityName());
                    adminAuthority.setAuthorityNote(authority.getAuthorityNote());
                    adminAuthority.setAuthorityState(authority.getAuthorityState());
                    adminAuthority.setRoleAuthorityNote(list.getRoleAuthorityNote());
                    adminAuthorityList.add(adminAuthority);
                }
                adminVo.setRoleId(UtilStatic.STATIC_ONE);
                modelMap.put("adminVo",adminVo);
                modelMap.put("adminAuthorityList",adminAuthorityList);
            }
            return "jsp-behind/admin-detail-info";
        }catch(Exception e){
            log.error(timeToken+"进入selectAdminInfoAndAuthorityAndRole的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 相关页面==========================================================================query-same-admin.jsp
     * 查询同级的管理人员（管理员可以看见所有的管理员，超级管理员可以看见所有的超级管理员）
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("/queryAllTheSameGradeAdminInfo")
    public String queryAllTheSameGradeAdminInfo(HttpSession session,ModelMap modelMap){
        log.info(timeToken+"进入queryAllTheSameGradeAdminInfo方法!");
        try{
            log.info(timeToken+"进入queryAllTheSameGradeAdminInfo的try方法1");
            Admin admin = (Admin) session.getAttribute("admin");
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
            List<AdminVo> adminVoList = new ArrayList<AdminVo>();
            if(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE){
                List<Admin> adminList = adminService.queryAllAdminInfo();
                for(Admin list:adminList){
                    AdminRole adminRo = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if(adminRo.getRoleId()==UtilStatic.STATIC_ONE && adminRo.getIsNotApproval() == UtilStatic.STATIC_ONE){
                        AdminVo adminVo = new AdminVo();
                        adminVo.setAdminId(list.getAdminId());
                        adminVo.setAdminName(list.getAdminName());
                        adminVo.setAdminEmail(list.getAdminEmail());
                        adminVo.setAdminPhone(list.getAdminPhone());
                        adminVo.setAdminStatus(list.getAdminStatus());
                        adminVo.setRealName(list.getRealName());
                        adminVo.setRoleId(UtilStatic.STATIC_ONE);
                        adminVoList.add(adminVo);
                    }
                }
            }else if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                List<Admin> adminList = adminService.queryAllAdminInfo();
                for(Admin list:adminList){
                    AdminRole adminRo = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if((adminRo.getRoleId()==UtilStatic.STATIC_TWO && adminRo.getIsNotApproval()==UtilStatic.STATIC_ONE) || (adminRo.getRoleId()==UtilStatic.STATIC_ONE && adminRo.getIsNotApproval()==UtilStatic.STATIC_ZERO)){
                        AdminVo adminVo = new AdminVo();
                        adminVo.setAdminId(list.getAdminId());
                        adminVo.setAdminName(list.getAdminName());
                        adminVo.setAdminEmail(list.getAdminEmail());
                        adminVo.setAdminPhone(list.getAdminPhone());
                        adminVo.setAdminStatus(list.getAdminStatus());
                        adminVo.setRealName(list.getRealName());
                        adminVo.setRoleId(UtilStatic.STATIC_TWO);
                        adminVoList.add(adminVo);
                    }
                }
            }
            modelMap.put("adminVoList",adminVoList);
            return "jsp-behind/query-same-admin";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllTheSameGradeAdminInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 管理人员的下级所有人员的删除
     * 超级管理员禁用管理员账号，管理员可以禁用经理的账号（属于删除，禁用了以后不能登录账号）
     * @param adminId
     * @param //adminStatus
     * @return
     */
    @RequestMapping("/stopOrStartAdminUsed")
    public String stopOrStartAdminUsed(@RequestParam("adminId") int adminId){
        log.info(timeToken+"进入stopOrStartAdminUsed方法,接收管理人员编号:"+adminId);
        try{
            log.info(timeToken+"进入stopOrStartAdminUsed的try方法!");
            Admin admin  = adminService.selectByAdminId(adminId);
            if(admin.getAdminStatus()==UtilStatic.STATIC_ONE){
                log.info("进入if条件,管理人员的状态判断,当前状态为:"+admin.getAdminStatus());
                admin.setAdminStatus(UtilStatic.STATIC_ZERO);
            }else if(admin.getAdminStatus()==UtilStatic.STATIC_ZERO){
                log.info("进入else if条件,管理人员的状态判断,当前状态为:"+admin.getAdminStatus());
                admin.setAdminStatus(UtilStatic.STATIC_ONE);
            }
            adminService.updateAdminByAdminId(admin);
            return "redirect:/queryAllDownAdminInfo.do";
        }catch(Exception e){
            log.error(timeToken+"进入stopOrStartAdminUsed的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }





    /**
     * 已经授权的方法
     * 超级管理员禁用管理员账号，管理员可以禁用经理的账号（属于删除，禁用了以后不能登录账号）
     * @param adminId
     * @param //adminStatus
     * @return
     */
    @RequestMapping("/stopOrStartAdminBan")
    public String stopOrStartAdminBan(@RequestParam("adminId") int adminId){
        log.info(timeToken+"进入stopOrStartAdminBan方法,接收管理人员编号:"+adminId);
        try{
            log.info(timeToken+"进入stopOrStartAdminBan的try方法!");
            Admin admin  = adminService.selectByAdminId(adminId);
            if(admin.getAdminStatus()==UtilStatic.STATIC_ONE){
                log.info("进入if条件,管理人员的状态判断,当前状态为:"+admin.getAdminStatus());
                admin.setAdminStatus(UtilStatic.STATIC_ZERO);
            }else if(admin.getAdminStatus()==UtilStatic.STATIC_ZERO){
                log.info("进入else if条件,管理人员的状态判断,当前状态为:"+admin.getAdminStatus());
                admin.setAdminStatus(UtilStatic.STATIC_ONE);
            }
            adminService.updateAdminByAdminId(admin);
            return "redirect:/queryAllAlreadyAuthorizedAdminInfo.do";
        }catch(Exception e){
            log.error(timeToken+"进入stopOrStartAdminBan的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 查询管理员的上级（管理员的上级是超级管理员）
     * 关系页面 query-up-admin.jsp
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("/queryAllUpGradeAdminInfo")
    public String queryAllUpGradeAdminInfo(HttpSession session,ModelMap modelMap){
        log.info("进入queryAllUpGradeAdminInfo方法!");
        try{
            log.info("进入queryAllUpGradeAdminInfo的try方法!");
            Admin admin = (Admin) session.getAttribute("admin");
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(admin.getAdminId());
            List<AdminVo> adminVoList = new ArrayList<AdminVo>();
            if((adminRole.getRoleId()==UtilStatic.STATIC_TWO && adminRole.getIsNotApproval()== UtilStatic.STATIC_ONE) ||(adminRole.getRoleId()==UtilStatic.STATIC_ONE && adminRole.getIsNotApproval() == UtilStatic.STATIC_ZERO)){
                List<Admin> adminList = adminService.queryAllAdminInfo();
                for(Admin list:adminList){
                    AdminRole adminRo = adminRoleService.selectAdminRoleByAdminId(list.getAdminId());
                    if(adminRo.getRoleId()==UtilStatic.STATIC_ONE && adminRo.getIsNotApproval()==UtilStatic.STATIC_ONE){
                        AdminVo adminVo = new AdminVo();
                        adminVo.setAdminId(list.getAdminId());
                        adminVo.setAdminName(list.getAdminName());
                        adminVo.setRealName(list.getRealName());
                        adminVo.setAdminPhone(list.getAdminPhone());
                        adminVo.setAdminEmail(list.getAdminEmail());
                        adminVo.setAdminStatus(list.getAdminStatus());
                        adminVo.setRoleId(UtilStatic.STATIC_ONE);
                        adminVoList.add(adminVo);
                    }
                }
            }
            modelMap.put("adminVoList",adminVoList);
            return "jsp-behind/query-up-admin";
        }catch(Exception e){
            log.error("进入queryAllUpGradeAdminInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }



}
