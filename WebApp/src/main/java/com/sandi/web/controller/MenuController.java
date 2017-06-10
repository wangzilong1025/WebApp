package com.sandi.web.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandi.web.model.*;
import com.sandi.web.service.IAchievementService;
import com.sandi.web.service.ICityService;
import com.sandi.web.service.IMenuService;
import com.sandi.web.service.INoticeService;
import com.sandi.web.util.UtilStatic;
import com.sandi.web.vo.NoticeVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 15049 on 2017-04-11.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    private static final Logger log = Logger.getLogger(MenuController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private IMenuService menuService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IAchievementService achievementService;

    @RequestMapping("/getMenuList")
    public String getMenuList(ModelMap modelMap, HttpServletRequest request){
        log.info(timeToken+"进入getMenuList方法");
        try{
            log.info(timeToken+"进入getMenuList的try方法");
            /*菜单的实现*/
            List<Menu> menuListAll = menuService.findAllMenuLevels();
            List<Menu> menuListOne = new ArrayList<Menu>();
            //List<NoticeVo> noticeVoList = new ArrayList<NoticeVo>();
            for(Menu menuOne:menuListAll){
                if(menuOne.getTopStatus()==1){
                    List<Menu> menuListTwo = new ArrayList<Menu>();
                    for(Menu menuTwo:menuListAll){
                        if(menuTwo.getTopStatus()==2&&menuTwo.getUpStatus()==menuOne.getTopId()){
                            List<Menu> menuListThree = new ArrayList<Menu>();
                            for(Menu menuThree:menuListAll){
                                if(menuThree.getTopStatus()==3&&menuThree.getUpStatus()==menuTwo.getTopId()){
                                    menuListThree.add(menuThree);
                                }
                            }
                            menuTwo.setMenulist(menuListThree);
                            menuListTwo.add(menuTwo);
                        }
                    }
                    menuOne.setMenulist(menuListTwo);
                    menuListOne.add(menuOne);
                }
            }
            modelMap.put("menuListOne", menuListOne);
            //公告便利功能
            int noticeStatus = UtilStatic.STATIC_ONE;
            Map<String,Integer> map = new HashMap<String, Integer>();
            map.put("noticeStatus",noticeStatus);
            List<Notice> noticeList = noticeService.queryAllNoticeByStatus(map);
            Date date = UtilStatic.NEW_DATE;
            for(Notice list:noticeList){
                list.setCreateTimeStr(UtilStatic.sdf.format(list.getCreateTime()));
                list.setNoticeReleaseTimeStr(UtilStatic.sdf.format(list.getNoticeReleaseTime()));
                list.setNoticeEndTimeStr(UtilStatic.sdf.format(list.getNoticeEndTime()));
            }
            modelMap.put("noticeList",noticeList);
            String shortTime = UtilStatic.shortTime.format(UtilStatic.NEW_DATE);
            String[] timeList = shortTime.split("-");
            String years = timeList[0];
            modelMap.put("years",years);
        }catch(Exception e){
            log.error(timeToken+"进入getMenuList的catch方法"+e.getMessage());
        }
        return "jsp-front/Main";
    }

    /**
     * 科研处成果添加的方法
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/selectMenuOne")
    public String selectMenuOne(ModelMap modelMap, HttpSession session,HttpServletRequest request){
        log.info(timeToken+"进入selectMenuOne方法!!!");
        try{
            log.info(timeToken+"进入selectMenuOne的try方法!!!");
            UserLogin userLogin = (UserLogin) session.getAttribute("user");
            if(userLogin ==null){
                request.setAttribute("testid","<script type=\"text/javascript\">\n" + "alert(请您登陆后在添加科研成果!!!);"+"\t\t</script>");
                return "jsp-front/user-login";
            }else{
                List<Menu> selectOneMenu = menuService.findMenuLevels(UtilStatic.STATIC_ONE);
                List<City> selectOneCity = cityService.findCityLevel(UtilStatic.STATIC_ONE);
                modelMap.put("selectOneMenu", selectOneMenu);
                modelMap.put("selectOneCity", selectOneCity);
                return "jsp-front/achievement-add";
            }
        }catch(Exception e){
            log.error(timeToken+"进入selectMenuOne的catch方法!!!");
            return "jsp-error/error-page";
        }

    }
    @RequestMapping(value="/selectMenuTwo")
    @ResponseBody
    public void selectMenuTwo(HttpServletRequest request,Integer topId,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException {
        String tId = request.getParameter("topId");
        List<Menu> selectTwoMenu = menuService.findUpStatus(Integer.parseInt(tId));
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(mapper.writeValueAsString(selectTwoMenu));
        out.print(mapper.writeValueAsString(selectTwoMenu));
    }

    @RequestMapping("/selectMenuThree")
    @ResponseBody
    public void selectMenuThree(HttpServletRequest request,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{
        String tId2 = request.getParameter("topId2");
        System.out.println("这是什么"+tId2);
        List<Menu> selectThreeMenu = menuService.findUpStatus(Integer.parseInt(tId2));
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(mapper.writeValueAsString(selectThreeMenu));
        out.print(mapper.writeValueAsString(selectThreeMenu));
    }


    /**
     * 用户搜索科研成果是的方法
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/selectMenuOneInSearch")
    public String selectMenuOneInSearch(@RequestParam("searchContent") String searchContent, ModelMap modelMap, HttpServletRequest request){
        log.info(timeToken+"进入selectMenuOneInSearch方法!!!");
        try{
            log.info(timeToken+"进入selectMenuOneInSearch的try方法!!!");
            //默认的页数
            int page = 1;
            //默认的每页的个数12条每页
            int pageSize = UtilStatic.STATIC_PAGESIZE;
            List<Menu> oneMenuInSearch = menuService.findMenuLevels(UtilStatic.STATIC_ONE);
            System.out.print("这是搜索内容:"+searchContent);
            //状态2代表科研成果经过审核通过的，能展示给用户查看的
            int releaseState = UtilStatic.STATIC_TWO;
            //String a = String.valueOf((page-1)*pageSize);
            Map map = new HashMap();
            map.put("searchContent",searchContent);
            map.put("releaseState",String.valueOf(releaseState));
            map.put("startCount",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            List<Achievement> searchList = achievementService.queryAchievementBySearchContent(map);
            //查询总数
            int count = achievementService.queryAchievementBySearchContentCount(map);
            System.out.print("科研成果总数:"+"==========================================="+count);
            if(count<=16){
                page = 1;
            }else{
                page = (count + pageSize -1)/pageSize;
                System.out.print("这是分页的总页数:"+page);
            }
            for(Achievement list:searchList){
                list.setTimeToString(UtilStatic.sdf.format(list.getReleaseTime()));
                Menu menu = menuService.selectTopNameByTopId(list.getAchievementType());
                list.setAchievementTypeName(menu.getTopName());
            }
            //遍历的科研成果菜单
            modelMap.put("oneMenuInSearch", oneMenuInSearch);
            //搜索到的科研成果
            modelMap.put("searchList",searchList);
            modelMap.put("searchContent",searchContent);
            modelMap.put("count",count);
            modelMap.put("page",page);
            return "jsp-front/user-search";
        }catch(Exception e){
            log.error(timeToken+"进入selectMenuOneInSearch的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * 管理员的科研成果搜索功能（后台）
     * @param searchContent
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/searchAchievementForBehind")
    public String searchAchievementForBehind(@RequestParam("searchContent") String searchContent, ModelMap modelMap, HttpServletRequest request){
        log.info(timeToken+"进入searchAchievementForBehind方法!!!");
        try{
            log.info(timeToken+"进入searchAchievementForBehind的try方法!!!");
            //默认的页数
            int page = 1;
            //默认的每页的个数12条每页
            int pageSize = UtilStatic.STATIC_PAGESIZE;
            List<Menu> oneMenuInSearch = menuService.findMenuLevels(UtilStatic.STATIC_ONE);
            System.out.print("这是搜索内容:"+searchContent);
            //状态2代表科研成果经过审核通过的，能展示给用户查看的
            int releaseState = UtilStatic.STATIC_TWO;
            //String a = String.valueOf((page-1)*pageSize);
            Map map = new HashMap();
            map.put("searchContent",searchContent);
            map.put("releaseState",String.valueOf(releaseState));
            map.put("startCount",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            List<Achievement> searchList = achievementService.queryAchievementBySearchContent(map);
            //查询总数
            int count = achievementService.queryAchievementBySearchContentCount(map);
            System.out.print("科研成果总数:"+"==========================================="+count);
            if(count<=16){
                page = 1;
            }else{
                page = (count + pageSize -1)/pageSize;
                System.out.print("这是分页的总页数:"+page);
            }
            for(Achievement list:searchList){
                list.setTimeToString(UtilStatic.sdf.format(list.getReleaseTime()));
                Menu menu = menuService.selectTopNameByTopId(list.getAchievementType());
                list.setAchievementTypeName(menu.getTopName());
                City city = cityService.selectCityNameBycityId(list.getLocationCity());
                list.setCityTypeName(city.getCityName());
            }
            //遍历的科研成果菜单
            modelMap.put("oneMenuInSearch", oneMenuInSearch);
            //搜索到的科研成果
            modelMap.put("searchList",searchList);
            modelMap.put("searchContent",searchContent);
            modelMap.put("count",count);
            modelMap.put("page",page);
            return "jsp-behind/achievement-search";
        }catch(Exception e){
            log.error(timeToken+"进入searchAchievementForBehind的catch方法!!!");
            return "jsp-error/error-page";
        }
    }


    /**
     * 用户搜索页面科研成果的 二级类查询方法
     * @param request
     * @param response
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping("/selectMenuTwoInSearch")
    @ResponseBody
    public void selectMenuTwoInSearch(HttpServletRequest request,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{
        log.info(timeToken+"进入selectMenuTwoInSearch方法!!!");
        try{
            log.info(timeToken+"进入selectMenuTwoInSearch的try方法");
            String topId1 = request.getParameter("topId1");
            List<Menu> twoMenuInSearch = menuService.findUpStatus(Integer.parseInt(topId1));
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            System.out.println(mapper.writeValueAsString(twoMenuInSearch));
            out.print(mapper.writeValueAsString(twoMenuInSearch));
        }catch(Exception e){
           log.error("进入selectMenuTwoInSearch的catch方法,异常信息:"+e.getMessage());
        }
    }



    @RequestMapping("/selectMenuThreeInSearch")
    @ResponseBody
    public void selectMenuThreeInSearch(HttpServletRequest request,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{
        log.info(timeToken+"进入selectMenuThreeInSearch方法!!!");
        try{
            log.info(timeToken+"进入selectMenuThreeInSearch的try方法");
            String topId1 = request.getParameter("topId2");
            List<Menu> twoMenuInSearch = menuService.findUpStatus(Integer.parseInt(topId1));
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            System.out.println(mapper.writeValueAsString(twoMenuInSearch));
            out.print(mapper.writeValueAsString(twoMenuInSearch));
        }catch(Exception e){
            log.error("进入selectMenuThreeInSearch的catch方法,异常信息:"+e.getMessage());
        }
    }

}
