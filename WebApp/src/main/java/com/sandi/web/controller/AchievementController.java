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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sandi.web.model.Achievement;
import com.sandi.web.model.City;
import com.sandi.web.model.Menu;
import com.sandi.web.model.UserInfo;
import com.sandi.web.model.UserLogin;
import com.sandi.web.service.IAchievementService;
import com.sandi.web.service.ICityService;
import com.sandi.web.service.IMenuService;
import com.sandi.web.service.IUserInfoService;

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
    /**
     * 查询已经审核通过且发布的科研成果
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievement")
    public String queryAllAchievement(ModelMap modelMap,Achievement achievement,HttpServletRequest request,HttpSession session){
        log.info(timeToken+"进入queryAllAchievement方法");
        try{
            log.info(timeToken+"进入queryAllAchievement的try方法");
            Map<String,Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:"+userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //已发布科研成果状态
            int releaseState = 2;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for(Achievement list:achievementList){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:"+achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-show";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAchievement的catch方法"+e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 查询只是保存但未发布的科研成果
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return achievementList
     */
    @RequestMapping("/queryAllAchievementUnreleaseFront")
    public String queryAllAchievementUnreleaseFront(ModelMap modelMap,Achievement achievement,HttpServletRequest request,HttpSession session){
        log.info(timeToken+"进入queryAllAchievementUnreleaseFront方法");
        try{
            log.info(timeToken+"进入queryAllAchievementUnreleaseFront的try方法");
            Map<String,Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:"+userInfo.getUserNick());
            int userId = userLogin.getUserId();
            //0是科研成果保存未发布状态
            int releaseState = 0;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for(Achievement list:achievementList){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:"+achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-showunrelease";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAchievementUnreleaseFront的catch方法"+e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 前台用户根据用户ID去查询自己的待审核科研成果
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievementByCheckPendingFront")
    public String queryAllAchievementByCheckPendingFront(ModelMap modelMap,Achievement achievement,HttpServletRequest request,HttpSession session){
        log.info(timeToken+"进入queryAllAchievementByCheckPendingFront方法");
        try{
            log.info(timeToken+"进入queryAllAchievementByCheckPendingFront的try方法");
            Map<String,Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:"+userInfo.getUserNick());
            int userId = userLogin.getUserId();
            int releaseState = 1;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for(Achievement list:achievementList){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:"+achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-checkpending";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAchievementByCheckPendingFront的catch方法"+e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 根据用户的ID去查询当前用户的科研成果审核未通过的数据
     * @param modelMap
     * @param achievement
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/queryAllAchievementNotPass")
    public String queryAllAchievementNotPass(ModelMap modelMap,Achievement achievement,HttpServletRequest request,HttpSession session){
        log.info(timeToken+"进入queryAllAchievementNotPass方法");
        try{
            log.info(timeToken+"进入queryAllAchievementNotPass的try方法");
            Map<String,Integer> map = new HashMap<String, Integer>();
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            System.out.println("用户昵称:"+userInfo.getUserNick());
            int userId = userLogin.getUserId();
            int releaseState = 3;
            map.put("userId", userId);
            map.put("releaseState", releaseState);
            List<Achievement> achievementList = achievementService.queryAllAchievementByUserId(map);
            for(Achievement list:achievementList){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setTimeToString(sdf.format(list.getReleaseTime()));
            }
            System.out.println("list内容:"+achievementList);
            modelMap.put("achievementList", achievementList);
            return "jsp-front/achievement-notpass";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAchievementNotPass的catch方法"+e);
            return "jsp-error/error-page";
        }
    }

    /**
     * 添加科研成果
     * @param modelMap
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
    public String addAchievementByuserId(ModelMap modelMap,Achievement achievement,HttpSession session,HttpServletRequest request,
                                         @RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2)throws IllegalStateException, IOException{
        log.info(timeToken+"进入addAchievementByuserId方法");
        try{
            log.info(timeToken+"进入addAchievementByuserId的try方法");
            int releaseState = 0;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            System.out.println("时间转换"+time);
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            achievement.setUserId(userLogin.getUserId());
            achievement.setUserNick(userInfo.getUserNick());
            achievement.setReleaseTime(sdf.parse(time));
            //achievement.setLocationCity(localCity);
            String path = request.getSession().getServletContext().getRealPath("/achievementImage");
            File savedir = new File(path);
            // 如果目录不存在就创建
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            System.out.println("地址路径:"+path);
            String achievementImage = null;
            String achievementImage1 = null;
            String achievementImage2 = null;
            if(!file.isEmpty()){
                achievementImage = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                file.transferTo(new File(path + "/" + achievementImage));
            }
            if(!file1.isEmpty()){
                achievementImage1 = UUID.randomUUID().toString() + file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));
                file1.transferTo(new File(path + "/" + achievementImage1));
            }
            if(!file2.isEmpty()){
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
        }catch(Exception e){
            log.error(timeToken+"进入addAchievementByuserId的catch方法");
            return "jsp-error/error-page";
        }
    }

    @RequestMapping("/saveUpdateAchievementByAchievementId")
    public String saveUpdateAchievementByAchievementId(){
        log.info(timeToken+"进入saveUpdateAchievementByAchievementId方法!!!");
        try{
            log.info("进入saveUpdateAchievementByAchievementId的try方法!!!");

            return "";
        }catch(Exception e){
            log.error(timeToken+"进入saveUpdateAchievementByAchievementId的catch方法!!!");
            return "jsp-error/error-page";
        }

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
            List<Menu> selectOneMenu = menuService.findMenuLevels(1);
            List<City> selectOneCity = cityService.findCityLevel(1);
            int address = Integer.parseInt(achievement.getLocationCity());
            City city3 = cityService.selectCityNameBycityId(address);
            //所在地的第三个select框的值
            String addressThreeCity = city3.getCityName();
            //获得城市的一级ID
            int cityTwoId = city3.getCityUpLevel();
            City city2 = cityService.selectCityNameBycityId(cityTwoId);
            String addressTwoCity = city2.getCityName() ;

            System.out.println("一级城市:"+addressTwoCity+"二级城市"+addressThreeCity);
            //三级ID
            int menuUpStatusThree = Integer.parseInt(achievement.getAchievementType());
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
            int address = Integer.parseInt(achievement.getLocationCity());
            City city3 = cityService.selectCityNameBycityId(address);
            //所在地的第三个select框的值
            String addressThreeCity = city3.getCityName();
            //获得城市的一级ID
            int cityTwoId = city3.getCityUpLevel();
            City city2 = cityService.selectCityNameBycityId(cityTwoId);
            String addressTwoCity = city2.getCityName() ;
            System.out.println("一级城市:"+addressTwoCity+"二级城市"+addressThreeCity);
            //三级ID
            int menuUpStatusThree = Integer.parseInt(achievement.getAchievementType());
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
            achievementService.deleteAchievementByAchievementId(id);
            return "redirect:/achievement/queryAllAchievement.do";
        }catch(Exception e){
            log.error(timeToken+"进入deleteAchievementByAchievementId的catch方法!!!"+e);
            return "jsp-error/error-page";
        }
    }

    @RequestMapping("releaseAchievementByAchievementId")
    public String releaseAchievementByAchievementId(ModelMap modelMap,Achievement achievement,HttpSession session,HttpServletRequest request){
        log.info(timeToken+"进入releaseAchievementByAchievementId方法!!!");
        try{
            log.info("进入releaseAchievementByAchievementId的try方法!!!");
            //待审核的状态
            int status = 1;
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            UserInfo userInfo = userInfoService.selectByUserId(userLogin.getUserId());
            if(achievement.getUserNick().equals(userInfo.getUserNick())){
                log.info("判断achievement.getUserNick()的值与userInfo.getUserNick()的值是否相等:"+userInfo.getUserNick()+"========="+achievement.getUserNick());
            }
            achievement.setReleaseState(status);
            achievementService.updateAchievementByAchievementId(achievement);
            return "redirect:/achievement/queryAllAchievement.do";
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
            int resealState = 1;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(Integer.parseInt(list.getLocationCity()));
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(Integer.parseInt(list.getAchievementType()));
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
            //科研成果已通过状态
            int resealState = 2;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(Integer.parseInt(list.getLocationCity()));
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(Integer.parseInt(list.getAchievementType()));
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
            int resealState = 3;
            String achievementType = null;
            String unitName = null;
            List<Achievement> approveAchievementList = achievementService.queryAllApproveAchievement(resealState);
            for(Achievement list:approveAchievementList){
                list.getAchievementType();
                City city = cityService.selectCityNameBycityId(Integer.parseInt(list.getLocationCity()));
                list.setCityTypeName(city.getCityName());
                Menu menu = menuService.selectTopNameByTopId(Integer.parseInt(list.getAchievementType()));
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
            City city = cityService.selectCityNameBycityId(Integer.parseInt(achievement.getLocationCity()));
            achievement.setCityTypeName(city.getCityName());
            Menu menu = menuService.selectTopNameByTopId(Integer.parseInt(achievement.getAchievementType()));
            achievement.setAchievementTypeName(menu.getTopName());
            modelMap.put("achievement",achievement);
            return "jsp-behind/achievement-approve-detail";
        }catch(Exception e){
            log.error(timeToken+"进入achievementDetailApprove的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * 科研成果管理员审核通过（实际为灯芯方法，更新科研成果的状态）
     * @param modelMap
     * @param achievementId
     * @return
     */
    @RequestMapping("/achievementDetailReleased")
    public String achievementDetailReleased(ModelMap modelMap,@RequestParam("achievementId") int achievementId){
        log.info(timeToken+"进入achievementDetailApprove方法!!!");
        try{
            log.info(timeToken+"进入achievementDetailApprove的try方法,并且获取到成果详情的ID："+achievementId);
            Achievement achievement = achievementService.selectAchievementByAchievementId(achievementId);
            City city = cityService.selectCityNameBycityId(Integer.parseInt(achievement.getLocationCity()));
            achievement.setCityTypeName(city.getCityName());
            Menu menu = menuService.selectTopNameByTopId(Integer.parseInt(achievement.getAchievementType()));
            achievement.setAchievementTypeName(menu.getTopName());
            modelMap.put("achievement",achievement);
            return "jsp-behind/achievement-approve-detail";
        }catch(Exception e){
            log.error(timeToken+"进入achievementDetailApprove的catch方法!!!");
            return "jsp-error/error-page";
        }
    }
}
