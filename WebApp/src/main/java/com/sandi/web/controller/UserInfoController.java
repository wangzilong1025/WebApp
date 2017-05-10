package com.sandi.web.controller;

import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 15049 on 2017-04-11.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    private static final Logger log = Logger.getLogger(UserInfoController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("/selectPersonalCenter")
    public String selectPersonalCenter(ModelMap modelMap, HttpSession session){
        log.info(timeToken+"进入selectPersonalCenter方法");
        try{
            log.info(timeToken+"进入selectPersonalCenter的try方法");
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            modelMap.put("userInfo", userInfo);
            log.info(timeToken+"数据:"+userInfo);
            return "/jsp-front/user-information";
        }catch(Exception e){
            log.error(timeToken+"进入selectPersonalCenter的catch方法");
            return "/jsp-error/error-page";
        }
    }

    @RequestMapping("/updatePersonalUserInfo")
    public String updateUserInfo(ModelMap modelMap, UserInfo userInfo, HttpServletRequest request, HttpSession session, @RequestParam("file") MultipartFile file)throws IllegalStateException, IOException {
        log.info(timeToken+"进入updateUserInfo方法");
        try{
            log.info(timeToken+"进入updateUserInfo的try方法");
            UserLogin userLo = (UserLogin) session.getAttribute("user");
            UserInfo uI = new UserInfo();
            uI = userInfoService.selectByUserId(userLo.getUserId());
            userInfo.setUserId(uI.getUserId());
            //上传用户头像
            String path = request.getSession().getServletContext().getRealPath("/userInfoImage");
            File savedir = new File(path);
            // 如果目录不存在就创建
            if (!savedir.exists()) {
                savedir.mkdirs();
                System.out.println("已经创建目录!!!");
            }
            System.out.println("地址路径:"+path);
            if(!file.isEmpty()){
                String saveUserInfoImage = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                file.transferTo(new File(path + "/" + saveUserInfoImage));
                userInfo.setUserImage(saveUserInfoImage);
            }
            userInfoService.updateUserInfoByUserInfoId(userInfo);
            modelMap.put("userInfo", userInfo);
            return "redirect:/userInfo/selectPersonalCenter.do";

        }catch(Exception e){
            log.error("进入updateUserInfo的catch方法"+e);
            return "/jsp-error/error-page";
        }

    }
}
