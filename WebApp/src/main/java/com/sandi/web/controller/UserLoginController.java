package com.sandi.web.controller;

import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IUserInfoService;
import com.sandi.web.service.IUserLoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 15049 on 2017-04-11.
 */
@Controller
@RequestMapping("/userLogin")
public class UserLoginController {

    private static final Logger log = Logger.getLogger(UserLoginController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IUserLoginService userLoginService;
    @Autowired
    private IUserInfoService userInfoService;
    @RequestMapping("/login")
    public String login(UserLogin userLogin, HttpServletRequest request, HttpSession session){
        log.info(timeToken+"进入login方法!!!");
        UserLogin usLo = userLoginService.selectUserLogin(userLogin);
        try{
            log.info(timeToken+"进入login的try语句!!!");
            if(usLo!=null){
                log.info(timeToken+"用户状态:"+usLo.getUserStatus());
                if(usLo.getUserStatus()==0){
                    session.setAttribute("user", usLo);
                    request.setAttribute("message", "欢迎您"+usLo.getUserName()+"超级管理员");
                    return "redirect:/menu/getMenuList.do";
                    //return "/jsp-front/test1";
                }
                else if(usLo.getUserStatus()==1){
                    session.setAttribute("user", usLo);
                    request.setAttribute("message", "欢迎您"+usLo.getUserName()+"管理员");
                    return "redirect:/menu/getMenuList.do";
                    //return "/jsp-front/test1";
                }
                else if(usLo.getUserStatus()==2){
                    session.setAttribute("user", usLo);
                    request.setAttribute("message", "欢迎您"+usLo.getUserName()+"会员");
                    return "redirect:/menu/getMenuList.do";
                    //return "/jsp-front/test1";
                }
            }else{
                request.setAttribute("message", "用户名或密码错误,请重新登陆!!!");
                return "/jsp-front/user-login";
            }
        }catch(Exception e){
            log.error(timeToken+"进入login的catch语句!!!");
            session.setAttribute("message", "登录异常!!!");
            return "/jsp-front/user-login";
        }
        request.setAttribute("message", "请输入用户名密码再登录哦!!!");
        return "/jsp-front/user-login";
    }

    @RequestMapping("/addUserLogin")
    public String addUserLogin(UserLogin userLogin,HttpServletRequest request){
        log.info(timeToken+"进入用户注册方法addUserLogin()!!!");
        try{
            log.info(timeToken+"进入用户注册方法addUserLogin()的try方法!!!");
            userLogin.setUserStatus(2);
            userLoginService.addUserLogin(userLogin);
            UserInfo userInfo = new UserInfo();
            log.info("用户ID:"+userLogin.getUserId());
            userInfo.setUserId(userLogin.getUserId());
            userInfoService.insertUserInfoByUserId(userInfo);
            request.setAttribute("message", "注册成功,快去登录吧!!!");
            return "/jsp-front/user-login";
        }catch(Exception e){
            log.error(timeToken+"进入用户注册方法addUserLogin()的catch方法!!!");
            request.setAttribute("message", "注册异常，请刷新页面重新注册!!!");
            return "/jsp-front/user-regist";
        }
    }
}
