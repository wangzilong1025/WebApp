package com.sandi.web.controller;

import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IUserInfoService;
import com.sandi.web.util.UtilStatic;
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
import java.util.Date;
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
            String birth = UtilStatic.shortTime.format(userInfo.getUserBirth());
            System.out.print("这是用户的生日:"+birth);
            if(birth != null && birth != ""){
                String[] birthList = birth.split("-");
                String YYYY = birthList[0];
                String MM = birthList[1];
                String DD = birthList[2];
                modelMap.put("YYYY",YYYY);
                modelMap.put("MM",MM);
                modelMap.put("DD",DD);
            }
            modelMap.put("userInfo", userInfo);
            log.info(timeToken+"数据:"+userInfo);
            return "jsp-front/user-information";
        }catch(Exception e){
            log.error(timeToken+"进入selectPersonalCenter的catch方法");
            return "jsp-error/error-page";
        }
    }

    @RequestMapping("/updatePersonalUserInfo")
    public String updateUserInfo(ModelMap modelMap, UserInfo userInfo, HttpServletRequest request, HttpSession session,@RequestParam("YYYY") String YYYY,@RequestParam("MM") String MM,@RequestParam("DD") String DD, @RequestParam("file") MultipartFile file)throws IllegalStateException, IOException {
        log.info(timeToken+"进入updateUserInfo方法");
        try{
            log.info(timeToken+"进入updateUserInfo的try方法");
            UserLogin userLo = (UserLogin) session.getAttribute("user");
            UserInfo uI = new UserInfo();
            uI = userInfoService.selectByUserId(userLo.getUserId());
            userInfo.setUserId(uI.getUserId());
            userInfo.setUserBirth(UtilStatic.shortTime.parse(YYYY+"-"+MM+"-"+DD));
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
            return "jsp-error/error-page";
        }

    }

    /**
     * 用户中心的用户信息查找方法
     * @param session
     * @param modelMap
     * @param userInfo
     * @return
     */
    @RequestMapping("/userCenterInfomation")
    public String userCenterInfomation(HttpSession session,ModelMap modelMap,UserInfo userInfo){
        log.info("进入userCenterInfomation方法!");
        try{
            log.info("进入userCenterInfomation的try方法!");
            //Date date = new Date();
            String shortTime = UtilStatic.shortTime.format(UtilStatic.NEW_DATE);
            String[] splitShortTime = shortTime.split("-");
            String year = splitShortTime[0];
            String month = splitShortTime[1];
            String day = splitShortTime[2];
            System.out.print("这是年:"+year+"========="+"这是月:"+month+"======="+"这是日:"+day);
            UserLogin userlog = (UserLogin) session.getAttribute("user");
            userInfo = userInfoService.selectByUserId(userlog.getUserId());
            modelMap.put("userInfo",userInfo);
            modelMap.put("day",day);
            modelMap.put("month",month);
            modelMap.put("year",year);
            return "jsp-front/user-center";
        }catch(Exception e){
            log.error("进入userCenterInfomation的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }
}
