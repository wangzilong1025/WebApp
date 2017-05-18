package com.sandi.web.controller;

/**
 * Created by 王子龙 on 2017-05-14.
 */

import com.sandi.web.model.Achievement;
import com.sandi.web.model.FotoPlace;
import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IAchievementService;
import com.sandi.web.service.IFotoPlaceService;
import com.sandi.web.service.IUserInfoService;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户足记浏览科研成果便利
 */
@Controller
@RequestMapping("/fotoPlace")
public class FotoPlaceController {

    private static final Logger log = Logger.getLogger(FotoPlaceController.class);
    long timeToken = System.currentTimeMillis();

    @Autowired
    private IFotoPlaceService fotoPlaceService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAchievementService achievementService;

    @RequestMapping("/queryAllFotoPlaceAchievementByUserInfoId")
    public String queryAllFotoPlaceAchievementByUserInfoId(ModelMap modelMap,HttpSession session){
        log.info(timeToken+"进入queryAllFotoPlaceAchievementByUserInfoId方法!");
        try{
            log.info(timeToken+"进入queryAllFotoPlaceAchievementByUserInfoId的try方法!");
            UserLogin user = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(user.getUserId());
            int userinfoId = userInfo.getUserinfoId();
            //足记浏览数据状态
            int state = UtilStatic.STATIC_ONE;
            //科研成果发布状态
            int releaseState = UtilStatic.STATIC_TWO;
            Map map = new HashMap();
            map.put("state",state);
            map.put("userinfoId",userinfoId);
            List<FotoPlace> fotoList = fotoPlaceService.findAllFotoPlaceByUserInfoId(map);
            for(FotoPlace list:fotoList){
                list.setFotoPlaceDateStr(UtilStatic.sdf.format(list.getFotoPlaceDate()));
            }

            List<Achievement> achieveList = achievementService.queryAllAchievementForCollection(releaseState);
            modelMap.put("achieveList",achieveList);
            modelMap.put("fotoList",fotoList);

            return "jsp-front/user-foot";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllFotoPlaceAchievementByUserInfoId的catch方法,错误信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }
}
