package com.sandi.web.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandi.web.model.City;
import com.sandi.web.model.Menu;
import com.sandi.web.model.Notice;
import com.sandi.web.service.ICityService;
import com.sandi.web.service.IMenuService;
import com.sandi.web.service.INoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/getMenuList")
    public String getMenuList(ModelMap modelMap, HttpServletRequest request){
        log.info(timeToken+"进入getMenuList方法");
        try{
            log.info(timeToken+"进入getMenuList的try方法");
            /*菜单的实现*/
            List<Menu> menuListAll = menuService.findAllMenuLevels();
            List<Menu> menuListOne = new ArrayList<Menu>();
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
            int noticeStatus = 1;
            Map<String,Integer> map = new HashMap<String, Integer>();
            map.put("noticeStatus",noticeStatus);
            List<Notice> noticeList = noticeService.queryAllNoticeByStatus(map);
            for(Notice list:noticeList){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.setCreateTimeStr(sdf.format(list.getCreateTime()));
                list.setNoticeReleaseTimeStr(sdf.format(list.getNoticeReleaseTime()));
                list.setNoticeEndTimeStr(sdf.format(list.getNoticeEndTime()));
            }
            modelMap.put("noticeList",noticeList);
        }catch(Exception e){
            log.error(timeToken+"进入getMenuList的catch方法");
        }
        return "jsp-front/Main";
    }

    /**
     * 科研处成果添加的方法
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/selectMenuOne")
    public String selectMenuOne(ModelMap modelMap,HttpServletRequest request){
        log.info(timeToken+"进入selectMenuOne方法!!!");
        try{
            log.info(timeToken+"进入selectMenuOne的try方法!!!");
            List<Menu> selectOneMenu = menuService.findMenuLevels(1);
            List<City> selectOneCity = cityService.findCityLevel(1);
            modelMap.put("selectOneMenu", selectOneMenu);
            modelMap.put("selectOneCity", selectOneCity);
            return "jsp-front/achievement-add";
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
    public String selectMenuOneInSearch(ModelMap modelMap,HttpServletRequest request){
        log.info(timeToken+"进入selectMenuOneInSearch方法!!!");
        try{
            log.info(timeToken+"进入selectMenuOneInSearch的try方法!!!");
            List<Menu> oneMenuInSearch = menuService.findMenuLevels(1);
            modelMap.put("oneMenuInSearch", oneMenuInSearch);
            return "jsp-front/user-search";
        }catch(Exception e){
            log.error(timeToken+"进入selectMenuOneInSearch的catch方法!!!");
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
