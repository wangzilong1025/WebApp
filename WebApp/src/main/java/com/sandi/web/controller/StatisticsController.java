package com.sandi.web.controller;

import com.sandi.web.model.City;
import com.sandi.web.service.ICityService;
import com.sandi.web.util.UtilStatic;
import com.sandi.web.vo.StatisticsVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 王子龙 on 2017-06-07.
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    private static final Logger log = Logger.getLogger(StatisticsController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private ICityService cityService;

    @RequestMapping("/statisticsAchievementCount")
    public String statisticsAchievementCount(ModelMap modelMap){
        log.info(timeToken+"进入statisticsAchievementCount方法!");
        try{
            log.info(timeToken+"进入statisticsAchievementCount的try方法!");
            List<City> cityList = cityService.findCityLevel(UtilStatic.STATIC_ONE);
            List<StatisticsVo> statisticsVoList = new ArrayList<StatisticsVo>();
            for(City list:cityList){
                StatisticsVo statisticsVo = new StatisticsVo();
                statisticsVo.setCityId(list.getCityId());
                statisticsVo.setCityName(list.getCityName());
                String[] monthList = new String[12];
                for(int i=0 ;i<12 ;i++){
                    int sum = (int)(Math.random()*500);
                    String num = String.valueOf(sum);
                    /**
                     * 虚拟随机数1到100，到生产去掉
                     */
                    monthList[i] = num;
                }
                System.out.println("数组值："+(Arrays.asList(monthList)).toString());
                statisticsVo.setYear_Ach_Count(String.valueOf(Arrays.asList(monthList)));
                statisticsVoList.add(statisticsVo);
            }

            modelMap.put("statisticsVoList",statisticsVoList);
            return "jsp-front/city-statistics";
        }catch(Exception e){
            log.error(timeToken+"进入statisticsAchievementCount的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    @RequestMapping("/selectCityByPie")
    public String selectCityByPie(ModelMap modelMap){
        log.info(timeToken+"进入selectCityByPie方法!");
        try{
            log.info(timeToken+"进入selectCityByPie的try方法!");
            List<City> cityList = cityService.findCityLevel(UtilStatic.STATIC_ONE);
            String []a = new String[34];
            int i = 0;
            for(City list:cityList){
                a[i] = "'"+list.getCityName()+"'";
                i++;
            }
            System.out.print("数组值为；"+Arrays.asList(a).toString());
            String city = (Arrays.asList(a)).toString();
            modelMap.put("city",city);


            int max=100000;
            int min=2000;
            String[] b = new String[34];
            int j = 0;
            for(City list:cityList){
                Random random = new Random();
                int s = random.nextInt(max)%(max-min+1) + min;
                String ss = String.valueOf(s);
                String str = "{value:"+ss+", name:'"+list.getCityName()+"'}";
                b[j] = str;
                j++;
            }
            System.out.print("数量加上城市名称:"+Arrays.asList(b).toString());
            String cityCount = Arrays.asList(b).toString();

            modelMap.put("cityCount",cityCount);
            return "jsp-front/city-pie";
        }catch(Exception e){
            log.error(timeToken+"进入selectCityByPie的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }



    @RequestMapping("/selectCityByPieForBehind")
    public String selectCityByPieForBehind(ModelMap modelMap){
        log.info(timeToken+"进入selectCityByPieForBehind方法!");
        try{
            log.info(timeToken+"进入selectCityByPieForBehind的try方法!");
            List<City> cityList = cityService.findCityLevel(UtilStatic.STATIC_ONE);
            String []a = new String[34];
            int i = 0;
            for(City list:cityList){
                a[i] = "'"+list.getCityName()+"'";
                i++;
            }
            System.out.print("数组值为；"+Arrays.asList(a).toString());
            String city = (Arrays.asList(a)).toString();
            modelMap.put("city",city);


            int max=100000;
            int min=2000;
            String[] b = new String[34];
            int j = 0;
            for(City list:cityList){
                Random random = new Random();
                int s = random.nextInt(max)%(max-min+1) + min;
                String ss = String.valueOf(s);
                String str = "{value:"+ss+", name:'"+list.getCityName()+"'}";
                b[j] = str;
                j++;
            }
            System.out.print("数量加上城市名称:"+Arrays.asList(b).toString());
            String cityCount = Arrays.asList(b).toString();

            modelMap.put("cityCount",cityCount);

            return "jsp-behind/admin-main";
        }catch (Exception e){
            log.error(timeToken+"进入selectCityByPieForBehind的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

}
