package com.sandi.web.controller;

/**
 * Created by 王子龙 on 2017-05-12.
 */

import com.sandi.web.model.Achievement;
import com.sandi.web.model.AchievementCollect;
import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IAchievementCollectService;
import com.sandi.web.service.IAchievementService;
import com.sandi.web.service.IUserInfoService;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 科研成果的收藏
 */
@Controller
@RequestMapping("/achievementCollect")
public class AchievementCollectController {
    private static final Logger log = Logger.getLogger(AchievementCollectController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IAchievementCollectService achievementCollectService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAchievementService achievementService;

    @RequestMapping("/queryAllCollectionAchievement")
    public String queryAllCollectionAchievementByUserInfoId(ModelMap modelMap, HttpServletRequest request , HttpSession session, Achievement achievement, AchievementCollect achievementCollect){
        log.info(timeToken+"进入queryAllCollectionAchievementByUserInfoId方法!");
        try{
            log.info(timeToken+"进入queryAllCollectionAchievementByUserInfoId的try方法!");
            //代表科研成果已经发布的状态
            int releaseState = UtilStatic.STATIC_TWO;
            UserLogin user = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(user.getUserId());
            System.out.print("输出用户信息ID:"+userInfo.getUserinfoId());
            List<AchievementCollect> achieveCollect = achievementCollectService.achievementCollectByAchievementId(userInfo.getUserinfoId());
            for(AchievementCollect listcol:achieveCollect){
                listcol.setCollectionTimeStr(UtilStatic.sdf.format(listcol.getCollectionTime()));
            }
            List<Achievement> achieve = achievementService.queryAllAchievementForCollection(releaseState);
            modelMap.put("achieveCollect",achieveCollect);
            modelMap.put("achieve",achieve);
            return "jsp-front/user-collection";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllCollectionAchievementByUserInfoId的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";

        }
    }

    @RequestMapping("/deleteAchievementCollectionById")
    @ResponseBody
    public void deleteAchievementCollectionById(Integer pid,HttpServletRequest request,AchievementCollect achievementCollect){

        UserLogin user = (UserLogin) request.getSession().getAttribute("user");
        //pcollectService.deleteByPrimaryKey(pid);

    }

}
