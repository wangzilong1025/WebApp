package com.sandi.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sandi.web.model.*;
import com.sandi.web.service.*;
import com.sandi.web.util.UtilStatic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 状态分类：
 * 0：代表保存状态
 * 1：代表待审核状态
 * 2：审核通过状态
 * 3：代表审核未通过状态
 * 4：删除状态
 */
@Controller
@RequestMapping("/achievement")
public class AchievementController {

    private static final Logger log = Logger.getLogger(AchievementController.class);

    long timeToken = System.currentTimeMillis();

    @Autowired
    private IAchievementService achievementService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IFotoPlaceService fotoPlaceService;

    /**
     * 查询已经审核通过且发布的科研成果
     *
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievement")
    public String queryAllAchievement(ModelMap modelMap, Achievement achievement, HttpServletRequest request, HttpSession session) {
        log.info(timeToken + "进入queryAllAchievement方法");
        try {
            log.info(timeToken + "进入queryAllAchievement的try方法");
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:" + userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //已发布科研成果状态2
            int releaseState = UtilStatic.STATIC_TWO;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for (Achievement list : achievementList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:" + achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-show";
        } catch (Exception e) {
            log.error(timeToken + "进入queryAllAchievement的catch方法" + e.getMessage());
            return "jsp-error/error-page";
        }
    }

    /**
     * 查询只是保存但未发布的科研成果
     *
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return achievementList
     */
    @RequestMapping("/queryAllAchievementUnreleaseFront")
    public String queryAllAchievementUnreleaseFront(ModelMap modelMap, Achievement achievement, HttpServletRequest request, HttpSession session) {
        log.info(timeToken + "进入queryAllAchievementUnreleaseFront方法");
        try {
            log.info(timeToken + "进入queryAllAchievementUnreleaseFront的try方法");
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:" + userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //0是科研成果保存未发布状态0
            int releaseState = UtilStatic.STATIC_ZERO;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for (Achievement list : achievementList) {
                list.setTimeToString(UtilStatic.sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:" + achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-showunrelease";
        } catch (Exception e) {
            log.error(timeToken + "进入queryAllAchievementUnreleaseFront的catch方法" + e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 前台用户根据用户ID去查询自己的待审核科研成果
     *
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievementByCheckPendingFront")
    public String queryAllAchievementByCheckPendingFront(ModelMap modelMap, Achievement achievement, HttpServletRequest request, HttpSession session) {
        log.info(timeToken + "进入queryAllAchievementByCheckPendingFront方法");
        try {
            log.info(timeToken + "进入queryAllAchievementByCheckPendingFront的try方法");
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:" + userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //科研成果状态1
            int releaseState = UtilStatic.STATIC_ONE;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for (Achievement list : achievementList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:" + achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-checkpending";
        } catch (Exception e) {
            log.error(timeToken + "进入queryAllAchievementByCheckPendingFront的catch方法" + e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 根据用户的ID去查询当前用户的科研成果审核未通过的数据
     *
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievementNotPass")
    public String queryAllAchievementNotPass(ModelMap modelMap, Achievement achievement, HttpServletRequest request, HttpSession session) {
        log.info(timeToken + "进入queryAllAchievementNotPass方法");
        try {
            log.info(timeToken + "进入queryAllAchievementNotPass的try方法");
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:" + userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //科研成果状态未通过状态
            int releaseState = UtilStatic.STATIC_THREE;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for (Achievement list : achievementList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:" + achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-notpass";
        } catch (Exception e) {
            log.error(timeToken + "进入queryAllAchievementNotPass的catch方法" + e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 添加科研成果
     *
     * @param achievement
     * @param session
     * @param request
     * @param file
     * @param file1
     * @param file2
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("/addAchievement")
    public String addAchievementByuserId(Achievement achievement, HttpSession session, HttpServletRequest request,
                                         @RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws IllegalStateException, IOException {
        log.info(timeToken + "进入addAchievementByuserId方法");
        try {
            log.info(timeToken + "进入addAchievementByuserId的try方法");
            int releaseState = UtilStatic.STATIC_ZERO;
            String time = UtilStatic.sdf.format(UtilStatic.NEW_DATE);
            System.out.println("时间转换" + time);
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            achievement.setUserId(userLogin.getUserId());
            achievement.setUserNick(userInfo.getUserNick());
            achievement.setReleaseTime(UtilStatic.sdf.parse(time));
            String path = request.getSession().getServletContext().getRealPath("/achievementImage");
            File savedir = new File(path);
            // 如果目录不存在就创建
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            System.out.println("地址路径:" + path);
            String achievementImage = null;
            String achievementImage1 = null;
            String achievementImage2 = null;
            if (!file.isEmpty()) {
                achievementImage = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                file.transferTo(new File(path + "/" + achievementImage));
            }
            if (!file1.isEmpty()) {
                achievementImage1 = UUID.randomUUID().toString() + file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));
                file1.transferTo(new File(path + "/" + achievementImage1));
            }
            if (!file2.isEmpty()) {
                achievementImage2 = UUID.randomUUID().toString() + file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf("."));
                file2.transferTo(new File(path + "/" + achievementImage2));
            }
            achievement.setAchievementOneImage(achievementImage);
            achievement.setAchievementTwoImage(achievementImage1);
            achievement.setAchievementImages(achievementImage2);
            /**
             *科研成果状态为0时表示待审核状态
             *科研成果状态为1时表示审核成功
             *科研成果状态为2时表示审核失败
             */
            achievement.setReleaseState(releaseState);
            achievementService.addAchievementByuserId(achievement);
            return "redirect:/achievement/queryAllAchievementUnreleaseFront.do";
        } catch (Exception e) {
            log.error(timeToken + "进入addAchievementByuserId的catch方法");
            return "jsp-error/error-page";
        }
    }

    /**
     * 科研成果更新保存
     *
     * @return
     */
    @RequestMapping("/saveUpdateAchievementByAchievementId")
    public String saveUpdateAchievementByAchievementId(Achievement achievement, HttpSession session, HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws IllegalStateException, IOException {
        log.info(timeToken + "进入saveUpdateAchievementByAchievementId方法!!!");
        Achievement ach = achievementService.selectAchievementByAchievementId(achievement.getAchievementId());
        int releaseState = ach.getReleaseState();
        try {
            log.info("进入saveUpdateAchievementByAchievementId的try方法!!!");

            String time = UtilStatic.sdf.format(UtilStatic.NEW_DATE);
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            achievement.setUserId(userLogin.getUserId());
            achievement.setUserNick(userInfo.getUserNick());
            achievement.setReleaseTime(UtilStatic.sdf.parse(time));
            String path = request.getSession().getServletContext().getRealPath("/achievementImage");
            File savedir = new File(path);
            // 如果目录不存在就创建
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            System.out.println("地址路径:" + path);
            String achievementImage = null;
            String achievementImage1 = null;
            String achievementImage2 = null;
            if (!file.isEmpty()) {
                achievementImage = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                file.transferTo(new File(path + "/" + achievementImage));
            }
            if (!file1.isEmpty()) {
                achievementImage1 = UUID.randomUUID().toString() + file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));
                file1.transferTo(new File(path + "/" + achievementImage1));
            }
            if (!file2.isEmpty()) {
                achievementImage2 = UUID.randomUUID().toString() + file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf("."));
                file2.transferTo(new File(path + "/" + achievementImage2));
            }
            achievement.setAchievementOneImage(achievementImage);
            achievement.setAchievementTwoImage(achievementImage1);
            achievement.setAchievementImages(achievementImage2);
            /**
             *科研成果状态为0时表示待审核状态
             *科研成果状态为1时表示审核成功
             *科研成果状态为2时表示审核失败
             */

            achievement.setReleaseState(releaseState);
            achievementService.updateAchievementByAchievementId(achievement);
            if (releaseState == 0) {
                return "redirect:/achievement/queryAllAchievementUnreleaseFront.do";
            } else if (releaseState == 1) {
                return "redirect:/achievement/queryAllAchievementByCheckPendingFront.do";
            } else if (releaseState == 2) {
                return "redirect:/achievement/queryAllAchievement.do";
            } else if (releaseState == 3) {
                return "redirect:/achievement/queryAllAchievementNotPass.do";
            }else{

            }
        } catch (Exception e) {
            log.error(timeToken + "进入saveUpdateAchievementByAchievementId的catch方法!!!");
            return "jsp-error/error-page";
        }
        return null;
    }

    /**
     * 更新科研成果查询(查询到后更新)
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping("/updateAchievementByAchievementId")
    public String updateAchievementByAchievementId(ModelMap modelMap,@RequestParam("id") int id){
        log.info(timeToken+"进入updateAchievementByAchievementId方法");
        try{
            log.info(timeToken+"进入updateAchievementByAchievementId的try方法!!!");
            Achievement achievement = achievementService.selectAchievementByAchievementId(id);
            List<Menu> selectOneMenu = menuService.findMenuLevels(UtilStatic.STATIC_ONE);
            List<City> selectOneCity = cityService.findCityLevel(UtilStatic.STATIC_ONE);
            int address = achievement.getLocationCity();
            City city3 = cityService.selectCityNameBycityId(address);
            //所在地的第三个select框的值
            String addressThreeCity = city3.getCityName();
            //获得城市的一级ID
            int cityTwoId = city3.getCityUpLevel();
            City city2 = cityService.selectCityNameBycityId(cityTwoId);
            String addressTwoCity = city2.getCityName() ;

            System.out.println("一级城市:"+addressTwoCity+"二级城市"+addressThreeCity);
            //三级ID
            int menuUpStatusThree = achievement.getAchievementType();
            Menu menu = menuService.selectTopNameByTopId(menuUpStatusThree);
            //成果类型三级名称
            String menuNameThree = menu.getTopName();
            //二级ID
            int menuUpStatusTwo = menu.getUpStatus();
            Menu menu2 = menuService.selectTopNameByTopId(menuUpStatusTwo);
            //成果类型二级名称
            String menuNameTwo = menu2.getTopName();
            //一级ID
            int menuUpStatusOne = menu2.getUpStatus();
            Menu menu3 = menuService.selectTopNameByTopId(menuUpStatusOne);
            //成果类型一级名称
            String menuNameOne = menu3.getTopName();
            modelMap.put("selectOneMenu", selectOneMenu);
            modelMap.put("selectOneCity", selectOneCity);
            modelMap.put("addressTwo", addressTwoCity);
            modelMap.put("addressThree", addressThreeCity);
            modelMap.put("menuNameThree", menuNameThree);
            modelMap.put("menuNameTwo", menuNameTwo);
            modelMap.put("menuNameOne", menuNameOne);
            modelMap.put("achievement", achievement);
            return "jsp-front/achievement-update";
        }catch(Exception e){
            log.error(timeToken+"进入updateAchievementByAchievementId的catch方法!!!"+e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 根据ID去查找科研成果详细信息
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping("/selectAchievementByAchievementId")
    public String selectAchievementByAchievementId(ModelMap modelMap,@RequestParam("id") int id){
        log.info(timeToken+"进入selectAchievementByAchievementId方法!!!");
        try{
            log.info(timeToken+"进入selectAchievementByAchievementId的try方法!!!");
            Achievement achievement = achievementService.selectAchievementByAchievementId(id);
            int address = achievement.getLocationCity();
            City city3 = cityService.selectCityNameBycityId(address);
            //所在地的第三个select框的值
            String addressThreeCity = city3.getCityName();
            //获得城市的一级ID
            int cityTwoId = city3.getCityUpLevel();
            City city2 = cityService.selectCityNameBycityId(cityTwoId);
            String addressTwoCity = city2.getCityName() ;
            System.out.println("一级城市:"+addressTwoCity+"二级城市"+addressThreeCity);
            //三级ID
            int menuUpStatusThree = achievement.getAchievementType();
            Menu menu = menuService.selectTopNameByTopId(menuUpStatusThree);
            //成果类型三级名称
            String menuNameThree = menu.getTopName();
            //二级ID
            int menuUpStatusTwo = menu.getUpStatus();
            Menu menu2 = menuService.selectTopNameByTopId(menuUpStatusTwo);
            //成果类型二级名称
            String menuNameTwo = menu2.getTopName();
            //一级ID
            int menuUpStatusOne = menu2.getUpStatus();
            Menu menu3 = menuService.selectTopNameByTopId(menuUpStatusOne);
            //成果类型一级名称
            String menuNameOne = menu3.getTopName();
            System.out.println("查询出来的成果详情:"+achievement);
            System.out.println("三级类型名称:"+menuNameThree+"二级类型名称:"+menuNameTwo+"一级类型名称:"+menuNameOne);
            modelMap.put("achievement", achievement);
            modelMap.put("addressTwo", addressTwoCity);
            modelMap.put("addressThree", addressThreeCity);
            modelMap.put("menuNameThree", menuNameThree);
            modelMap.put("menuNameTwo", menuNameTwo);
            modelMap.put("menuNameOne", menuNameOne);
            return "jsp-front/achievement-detail";
        }catch(Exception e){
            log.error(timeToken+"进入selectAchievementByAchievementId的catch方法!!!"+e);
            return "jsp-error/error-page";
        }
    }
    /**
     * 科研成果公告删除
     * 状态为4（需要改进）
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping("/deleteAchievementByAchievementId")
    public String deleteAchievementByAchievementId(ModelMap modelMap,@RequestParam("id") int id){
        log.info(timeToken+"进入deleteAchievementByAchievementId方法!!!");
        try{
            log.info(timeToken+"进入deleteAchievementByAchievementId的try方法!!!");
            log.info(timeToken+"要删除的科研成果的ID为:"+id);

            Achievement achievement = achievementService.selectAchievementByAchievementId(id);

            if(achievement.getReleaseState() == 0){
                achievementService.deleteAchievementByAchievementId(id);
                return "redirect:/achievement/queryAllAchievementUnreleaseFront.do";
            }else if(achievement.getReleaseState() == 1){
                achievementService.deleteAchievementByAchievementId(id);
                return "redirect:/achievement/queryAllAchievementByCheckPendingFront.do";
            }else if(achievement.getReleaseState() == 2){
                achievementService.deleteAchievementByAchievementId(id);
                return "redirect:/achievement/queryAllAchievement.do";
            }else if(achievement.getReleaseState() == 3){
                achievementService.deleteAchievementByAchievementId(id);
                return "redirect:/achievement/queryAllAchievementNotPass.do";
            }
        }catch(Exception e){
            log.error(timeToken+"进入deleteAchievementByAchievementId的catch方法!!!"+e);
            return "jsp-error/error-page";
        }
        return null;
    }

    /**
     * 发布科研成果方法（从未发布成果，或者未通过科研成果，通过发布功能进入该方法，进行发布，跳转到待审核状态）
     * @param modelMap
     * @param session
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("releaseAchievementByAchievementId")
    public String releaseAchievementByAchievementId(ModelMap modelMap,HttpSession session,HttpServletRequest request,@RequestParam("id") int id){
        log.info(timeToken+"进入releaseAchievementByAchievementId方法!!!");
        try{
            log.info("进入releaseAchievementByAchievementId的try方法!!!");
            Achievement achievement = new Achievement();
            achievement = achievementService.selectAchievementByAchievementId(id);
            //待审核的状态
            int status = UtilStatic.STATIC_ONE;
            achievement.setReleaseState(status);
            achievementService.updateAchievementByAchievementId(achievement);
            return "redirect:/achievement/queryAllAchievementByCheckPendingFront.do";
        }catch(Exception e){
            log.error(timeToken+"进入releaseAchievementByAchievementId的catch方法!!!"+e);
            return "jsp-error/error-page";
        }

    }

    /**
     * 科研成果待审批后台方法
     * @param modelMap
     * @param achievement
     * @return
     */
    @RequestMapping("/queryAllApproveAchievement")
    public String queryAllApproveAchievement(ModelMap modelMap,Achievement achievement){
        log.info(timeToken+"进入queryAllApproveAchievement方法!!!");
        try{
            log.info(timeToken+"进入queryAllApproveAchievement的try方法!!!");
            //待审核状态
            int resealState = UtilStatic.STATIC_ONE;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(list.getLocationCity());
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(list.getAchievementType());
                list.setAchievementTypeName(menu.getTopName());
            }
            modelMap.put("approveAchievementList",approveAchievementList);
            return "jsp-behind/achievement-approve-list";
        }catch(Exception e){
            log.info(timeToken+"进入queryAllApproveAchievement的catch方法!!!");
            return "jsp-error/error-page";
        }
    }

    /**
     * 科研成果已通过状态
     * @param modelMap
     * @param achievement
     * @return
     */
    @RequestMapping("/queryAllReleasedAchievement")
    public String queryAllReleasedAchievement(ModelMap modelMap,Achievement achievement){
        log.info(timeToken+"进入queryAllReleasedAchievement方法!!!");
        try{
            log.info(timeToken+"进入queryAllReleasedAchievement的try方法!!!");
            //科研成果已通过状态2
            int resealState = UtilStatic.STATIC_TWO;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(list.getLocationCity());
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(list.getAchievementType());
                list.setAchievementTypeName(menu.getTopName());
            }
            modelMap.put("approveAchievementList",approveAchievementList);
            return "jsp-behind/achievement-released-list";
        }catch(Exception e){
            log.info(timeToken+"进入queryAllReleasedAchievement的catch方法!!!");
            return "jsp-error/error-page";
        }
    }

    /**
     * 科研成果未通过状态----后台
     * @param modelMap
     * @param achievement
     * @return
     */
    @RequestMapping("/queryAllUnreleasedAchievement")
    public String queryAllUnreleasedAchievement(ModelMap modelMap,Achievement achievement){
        log.info(timeToken+"进入queryAllUnreleasedAchievement方法!!!");
        try{
            log.info(timeToken+"进入queryAllUnreleasedAchievement的try方法!!!");
            //科研成果未通过状态
            int resealState = UtilStatic.STATIC_THREE;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(list.getLocationCity());
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(list.getAchievementType());
                list.setAchievementTypeName(menu.getTopName());
            }
            modelMap.put("approveAchievementList",approveAchievementList);
            return "jsp-behind/achievement-unreleased-list";
        }catch(Exception e){
            log.info(timeToken+"进入queryAllUnreleasedAchievement的catch方法!!!");
            return "jsp-error/error-page";
        }
    }

    /**
     * 待审批科研成果详情后台
     * @return
     */
    @RequestMapping("/achievementDetailApprove")
    public String achievementDetailApprove(ModelMap modelMap,@RequestParam("achievementId") int achievementId){
        log.info(timeToken+"进入achievementDetailApprove方法!!!");
        try{
            log.info(timeToken+"进入achievementDetailApprove的try方法,并且获取到成果详情的ID："+achievementId);
            Achievement achievement = achievementService.selectAchievementByAchievementId(achievementId);
            City city = cityService.selectCityNameBycityId(achievement.getLocationCity());
            achievement.setCityTypeName(city.getCityName());
            Menu menu = menuService.selectTopNameByTopId(achievement.getAchievementType());
            achievement.setAchievementTypeName(menu.getTopName());
            modelMap.put("achievement",achievement);
            return "jsp-behind/achievement-approve-detail";
        }catch(Exception e){
            log.error(timeToken+"进入achievementDetailApprove的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * 科研成果管理员审核通过（实际为更新方法，更新科研成果的状态）(审核通过后跳转到提示页面)
     * @param //modelMap
     * @param achievementId
     * @return
     */
    @RequestMapping("/achievementAgreementPass")
    public String achievementAgreementPass(@RequestParam("achievementId") int achievementId){
        log.info(timeToken+"进入achievementAgreementPass方法!!!");
        try{
            log.info(timeToken+"进入achievementAgreementPass的try方法,并且获取到成果详情的ID："+achievementId);
            Achievement achievement = achievementService.selectAchievementByAchievementId(achievementId);
            log.info(timeToken+"获得的科研成果的状态;"+achievement.getReleaseState());
            //set状态为2的书是代表审核通过
            achievement.setReleaseState(UtilStatic.STATIC_TWO);
            achievementService.updateAchievementByAchievementId(achievement);
            return "jsp-behind/achievement-pass-prompt";
        }catch(Exception e){
            log.error(timeToken+"进入achievementAgreementPass的catch方法!!!");
            return "jsp-error/error-page";
        }
    }



    @RequestMapping("/achievementAgreementNotPass")
    public String achievementAgreementNotPass(@RequestParam("achievementId") int achievementId){
        log.info(timeToken+"进入achievementAgreementNotPass方法!!!");
        try{
            log.info(timeToken+"进入achievementAgreementNotPass的try方法,并且获取到成果详情的ID："+achievementId);
            Achievement achievement = achievementService.selectAchievementByAchievementId(achievementId);
            log.info(timeToken+"获得的科研成果的状态;"+achievement.getReleaseState());
            //set状态为33333333333的书是代表审核不通过
            achievement.setReleaseState(UtilStatic.STATIC_THREE);
            achievementService.updateAchievementByAchievementId(achievement);
            return "jsp-behind/achievement-unpass-prompt";
        }catch(Exception e){
            log.error(timeToken+"进入achievementAgreementNotPass的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * Main主页里的菜单，通过点击类型查询到科研成果
     * @param modelMap
     * @param topId
     * @return
     */
    @RequestMapping("/selectAllAchievementByAchievementTypeForMenu")
    public String selectAllAchievementByAchievementTypeForMenu(ModelMap modelMap,@RequestParam("topId") int topId){
        log.info(timeToken+"进入selectAllAchievementByAchievementTypeForMenu方法!");
        try{
            log.info(timeToken+"进入selectAllAchievementByAchievementTypeForMenu的try方法,接收参数ID:"+topId);
            int topStatus = UtilStatic.STATIC_ONE;
            int page = 1;
            int pageSize = UtilStatic.STATIC_PAGESIZE;
            List<Menu> menuList = menuService.findMenuLevels(topStatus);
            //科研成果已经发布的状态
            Map map = new HashMap();
            int releaseState = UtilStatic.STATIC_TWO;
            map.put("startCount",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            map.put("releaseState",releaseState);
            map.put("topId",topId);
            int count = achievementService.selectAllAchievementByTypeCount(map);
            /*if(count !=0 && count>0 && count <=16){
                page = 1;
            }else if(count !=0 && count>0 && count >16){

            }*/
            List<Achievement> achieveList = achievementService.selectAllAchievementByType(map);

            System.out.print("这是科研成果数量:"+"===================================="+count);
            for(Achievement list:achieveList){
                list.setTimeToString(UtilStatic.sdf.format(list.getReleaseTime()));
            }
            modelMap.put("count",count);
            modelMap.put("menuList",menuList);
            modelMap.put("achieveList",achieveList);
            return "jsp-front/user-search-type";
        }catch(Exception e){
            log.error(timeToken+"进入selectAllAchievementByAchievementTypeForMenu的catch方法!");
            return "jsp-error/error-page";
        }
    }

    /**
     * 用户搜索到的科研成果详情查看页面
     * @return
     */
    @RequestMapping("/findAchievementDetailByAchievementId")
    public String findAchievementDetailByAchievementId(ModelMap modelMap,@RequestParam("achievementId") int achievementId ,HttpSession session){
        log.info(timeToken+"进入findAchievementDetailByAchievementId方法!");
        try{
            log.info(timeToken+"进入findAchievementDetailByAchievementId的try方法,科研成果编号:"+achievementId);

            UserLogin user = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(user.getUserId());

            Achievement achievement = achievementService.selectAchievementByAchievementId(achievementId);
            int address = achievement.getLocationCity();
            City city3 = cityService.selectCityNameBycityId(address);
            //所在地的第三个select框的值
            String addressThreeCity = city3.getCityName();
            //获得城市的一级ID
            int cityTwoId = city3.getCityUpLevel();
            City city2 = cityService.selectCityNameBycityId(cityTwoId);
            String addressTwoCity = city2.getCityName() ;
            System.out.println("一级城市:"+addressTwoCity+"二级城市"+addressThreeCity);
            //三级ID
            int menuUpStatusThree = achievement.getAchievementType();
            Menu menu = menuService.selectTopNameByTopId(menuUpStatusThree);
            //成果类型三级名称
            String menuNameThree = menu.getTopName();
            //二级ID
            int menuUpStatusTwo = menu.getUpStatus();
            Menu menu2 = menuService.selectTopNameByTopId(menuUpStatusTwo);
            //成果类型二级名称
            String menuNameTwo = menu2.getTopName();
            //一级ID
            int menuUpStatusOne = menu2.getUpStatus();
            Menu menu3 = menuService.selectTopNameByTopId(menuUpStatusOne);
            //成果类型一级名称
            String menuNameOne = menu3.getTopName();
            System.out.println("查询出来的成果详情:"+achievement);
            System.out.println("三级类型名称:"+menuNameThree+"二级类型名称:"+menuNameTwo+"一级类型名称:"+menuNameOne);
            modelMap.put("achievement", achievement);
            modelMap.put("addressTwo", addressTwoCity);
            modelMap.put("addressThree", addressThreeCity);
            modelMap.put("menuNameThree", menuNameThree);
            modelMap.put("menuNameTwo", menuNameTwo);
            modelMap.put("menuNameOne", menuNameOne);
            FotoPlace foto = new FotoPlace();
            foto.setUserinfoId(userInfo.getUserinfoId());
            foto.setAchievementId(achievementId);
            foto.setState(UtilStatic.STATIC_ONE);
            foto.setFotoPlaceDate(UtilStatic.NEW_DATE);
            fotoPlaceService.addFotoPlaceById(foto);
            //用户查看发布成功的科研成果详情
            return "jsp-front/user-achievement";
        }catch(Exception e){
            log.info(timeToken+"进入findAchievementDetailByAchievementId的catch方法!");
            return "jsp-error/error-page";
        }

    }
}
