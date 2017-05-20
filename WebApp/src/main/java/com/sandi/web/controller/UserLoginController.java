package com.sandi.web.controller;

import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IUserInfoService;
import com.sandi.web.service.IUserLoginService;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
                }
                else if(usLo.getUserStatus()==1){
                    session.setAttribute("user", usLo);
                    request.setAttribute("message", "欢迎您"+usLo.getUserName()+"管理员");
                    return "redirect:/menu/getMenuList.do";
                }
                else if(usLo.getUserStatus()==2){
                    session.setAttribute("user", usLo);
                    request.setAttribute("message", "欢迎您"+usLo.getUserName()+"会员");
                    return "redirect:/menu/getMenuList.do";
                }
            }else{
                request.setAttribute("message", "用户名或密码错误,请重新登陆!!!");
                return "jsp-front/user-login";
            }
        }catch(Exception e){
            log.error(timeToken+"进入login的catch语句!!!");
            session.setAttribute("message", "登录异常!!!");
            return "jsp-front/user-login";
        }
        request.setAttribute("message", "请输入用户名密码再登录哦!!!");
        return "jsp-front/user-login";
    }

    @RequestMapping("/addUserLogin")
    public String addUserLogin(UserLogin userLogin,HttpServletRequest request){
        log.info(timeToken+"进入用户注册方法addUserLogin()!!!");
        try{
            log.info(timeToken+"进入用户注册方法addUserLogin()的try方法!!!");
            userLogin.setUserStatus(UtilStatic.STATIC_TWO);
            userLoginService.addUserLogin(userLogin);
            UserInfo userInfo = new UserInfo();
            log.info("用户ID:"+userLogin.getUserId());
            userInfo.setUserId(userLogin.getUserId());
            userInfoService.insertUserInfoByUserId(userInfo);
            request.setAttribute("message", "注册成功,快去登录吧!!!");
            return "jsp-front/user-login";
        }catch(Exception e){
            log.error(timeToken+"进入用户注册方法addUserLogin()的catch方法!!!");
            request.setAttribute("message", "注册异常，请刷新页面重新注册!!!");
            return "jsp-front/user-regist";
        }
    }

    /**
     * 用户修改用户名和密码
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/userUpdateSafetyByUserId")
    public String userUpdateSafetyByUserId(ModelMap modelMap, HttpSession session){
        log.info(timeToken+"进入userUpdateSafetyByUserId方法!");
        try{
            log.info(timeToken+"进入userUpdateSafetyByUserId的try方法!");
            UserLogin userLog = (UserLogin) session.getAttribute("user");
            UserLogin user = userLoginService.selectUserLogin(userLog);
            UserInfo userInfo = userInfoService.selectByUserId(userLog.getUserId());
            modelMap.put("userInfo",userInfo);
            modelMap.put("user",user);
            return "jsp-front/user-safety";
        }catch(Exception e){
            log.error(timeToken+"进入userUpdateSafetyByUserId的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

    @RequestMapping("/userUpdatePasswordByUserLoginId")
    public String userUpdatePasswordByUserLoginId(UserLogin userLogin,HttpSession session,HttpServletRequest request){
        log.info("进入userUpdatePasswordByUserLoginId方法!");
        try{
            log.info("进入userUpdatePasswordByUserLoginId的try方法!");
            UserLogin user = (UserLogin) session.getAttribute("user");
            UserLogin userlog  = userLoginService.selectUserLogin(user);
            String newPass = request.getParameter("userNewPass");
            String oldPass = request.getParameter("oldpassword");

            if(userlog.getUserPassword().equals(oldPass)){
                log.info("进入userUpdatePasswordByUserLoginId的try中的if方法!");
                userLogin.setUserId(userlog.getUserId());
                userLogin.setUserName(userlog.getUserName());
                userLogin.setUserPassword(newPass);
                userLogin.setUserStatus(userlog.getUserStatus());
                userLogin.setSafeQuestion(userlog.getSafeQuestion());
                userLogin.setSafeAnswer(userlog.getSafeAnswer());
                userLoginService.updatePasswordByUserLoginId(userLogin);
                return "jsp-front/user-safety-updated";
            }else{
                log.info("进入userUpdatePasswordByUserLoginId的try中的else方法!");
                return "jsp-front/user-safety-pass";
            }
        }catch(Exception e){
            log.error("进入userUpdatePasswordByUserLoginId的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }

    }

    /**
     * 用户的安全问题的保存
     * @param
     * @param session
     * @return
     */
    @RequestMapping("/userSafetyQuestionInfo")
    public String userSafetyQuestionInfo(UserLogin userLogin,HttpSession session){
        log.info("进入userSafetyQuestionInfo方法!");
        try{
            log.info("进入userSafetyQuestionInfo的try方法!");
            System.out.print(userLogin);
            UserLogin user = (UserLogin) session.getAttribute("user");
            UserLogin userL = userLoginService.selectUserLogin(user);
            userLogin.setUserId(userL.getUserId());
            userLogin.setUserName(userL.getUserName());
            userLogin.setUserPassword(userL.getUserPassword());
            userLogin.setUserStatus(UtilStatic.STATIC_ONE);
            userLoginService.updatePasswordByUserLoginId(userLogin);
            return "redirect:/userLogin/userUpdateSafetyByUserId.do";
        }catch(Exception e){
            log.error("进入userSafetyQuestionInfo的catch方法!");
            return "jsp-error/error-page";
        }
    }
}
