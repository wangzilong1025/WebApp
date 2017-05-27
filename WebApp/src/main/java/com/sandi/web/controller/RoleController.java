package com.sandi.web.controller;

import com.sandi.web.model.Role;
import com.sandi.web.service.IRoleService;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by 王子龙 on 2017-05-22.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger log = Logger.getLogger(RoleController.class);

    long timeToken = System.currentTimeMillis();

    @Autowired
    private IRoleService roleService;


    @RequestMapping("/queryAllAdminRole")
    public String queryAllAdminRole(ModelMap modelMap){
        log.info(timeToken+"进入queryAllAdminRole方法!");
        try{
            log.info(timeToken+"进入queryAllAdminRole的try方法!");
            List<Role> role = roleService.queryAllRole();
            modelMap.put("role",role);
            return "jsp-behind/role-list";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAdminRole的catch方法,接收错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    @RequestMapping("/addRoleInfo")
    public String addRoleInfo(Role role){
        log.info(timeToken+"进入addRoleInfo方法!");
        try{
            log.info(timeToken+"进入addRoleInfo的try方法!");
            roleService.addRoleInfo(role);
            return "redirect:/role/queryAllAdminRole.do";
        }catch(Exception e){
            log.error(timeToken+"进入addRoleInfo方法,接收错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    @RequestMapping("/deleteRoleInfo")
    public String deleteRoleInfo(@RequestParam("roleId") int roleId){
        log.info(timeToken+"进入deleteRoleInfo方法,接收角色ID:"+roleId);
        try{
            log.info(timeToken+"进入deleteRoleInfo的try方法!");
            roleService.deleteRoleByRoleId(roleId);
            return "redirect:/role/queryAllAdminRole.do";
        }catch(Exception e){
            log.error(timeToken+"进入deleteRoleInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 停止使用这个角色的方法
     * @param roleId
     * @return
     */
    @RequestMapping("/stopRoleUsed")
    public String stopRoleUsed(@RequestParam("roleId") int roleId){
        log.info("进入stopRoleUsed方法,接收角色ID:"+roleId);
        try{
            log.info("进入stopRoleUsed的try方法!");
            Role role = roleService.selectRoleNameByRoleId(roleId);
            role.setRoleState(UtilStatic.STATIC_ZERO);
            roleService.updateRoleByRoleId(role);
            return "redirect:/role/queryAllAdminRole.do";
        }catch(Exception e){
            log.error("进入stopRoleUsed的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 开始使用这个角色的方法
     * @param roleId
     * @return
     */
    @RequestMapping("/startRoleUsed")
    public String startRoleUsed(@RequestParam("roleId") int roleId){
        log.info("进入stopRoleUsed方法,接收角色ID:"+roleId);
        try{
            log.info("进入stopRoleUsed的try方法!");
            Role role = roleService.selectRoleNameByRoleId(roleId);
            role.setRoleState(UtilStatic.STATIC_ONE);
            roleService.updateRoleByRoleId(role);
            return "redirect:/role/queryAllAdminRole.do";
        }catch(Exception e){
            log.error("进入stopRoleUsed的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

}
