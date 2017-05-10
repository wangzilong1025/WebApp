package com.sandi.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sandi.web.model.City;
import com.sandi.web.service.ICityService;

@Controller
@RequestMapping("/city")
public class CityController {

    private static final Logger log = Logger.getLogger(MenuController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private ICityService cityService;

    @RequestMapping(value="/selectCityThree")
    @ResponseBody
    public void selectCityThree(HttpServletRequest request,Integer cityId,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{
        log.info(timeToken+"进入ajax的selectCityThree方法!!!");
        try{
            String cId = request.getParameter("cityId");
            List<City> selectThreeCity = cityService.findCityUpLevel(Integer.parseInt(cId));
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            System.out.println(mapper.writeValueAsString(selectThreeCity));
            out.print(mapper.writeValueAsString(selectThreeCity));
        }catch(Exception e){
            log.error(timeToken+"进入ajax的selectCityThree的catch方法!!!"+e);
        }

    }
}
