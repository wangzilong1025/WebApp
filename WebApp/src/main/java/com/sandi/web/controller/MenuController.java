package com.sandi.web.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandi.web.model.City;
import com.sandi.web.model.Menu;
import com.sandi.web.service.ICityService;
import com.sandi.web.service.IMenuService;
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
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/getMenuList")
    public String getMenuList(ModelMap modelMap, HttpServletRequest request){
        log.info(timeToken+"进入getMenuList方法");
        try{
            log.info(timeToken+"进入getMenuList的try方法");
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
        }catch(Exception e){
            log.error(timeToken+"进入getMenuList的catch方法");
        }
        return "/jsp-front/Main";
    }

    @RequestMapping("/selectMenuOne")
    public String selectMenuOne(ModelMap modelMap,HttpServletRequest request){
        log.info(timeToken+"进入selectMenuOne方法!!!");
        try{
            log.info(timeToken+"进入selectMenuOne的try方法!!!");
            List<Menu> selectOneMenu = menuService.findMenuLevels(1);
            List<City> selectOneCity = cityService.findCityLevel(1);
            modelMap.put("selectOneMenu", selectOneMenu);
            modelMap.put("selectOneCity", selectOneCity);
            return "/jsp-front/achievement-add";
        }catch(Exception e){
            log.error(timeToken+"进入selectMenuOne的catch方法!!!");
            return "/jsp-error/error-page";
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
}
