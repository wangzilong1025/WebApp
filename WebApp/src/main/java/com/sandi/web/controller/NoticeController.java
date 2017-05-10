package com.sandi.web.controller;

import com.sandi.web.model.Admin;
import com.sandi.web.model.Notice;
import com.sandi.web.service.IAdminService;
import com.sandi.web.service.INoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15049 on 2017-04-16.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    private static final Logger log = Logger.getLogger(NoticeController.class);
    long timeToken = System.currentTimeMillis();
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IAdminService adminService;

    @RequestMapping("/queryAllNoticeByStatus")
    public String queryAllNoticeByStatus(ModelMap modelMap,Notice notice){
        log.info(timeToken+"进入queryAllNoticeByStatus方法");
        int noticeStatus = 1;
        try{
            log.info("进入queryAllNoticeByStatus的try方法!!!");
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
            return "jsp-behind/notice-list";
        }catch(Exception e){
            log.error("进入queryAllNoticeByStatus的catch方法!!!");
            return "jsp-error/error-page";
        }

    }
    @RequestMapping("/addNoticeByAdmin")
    public String addNoticeByAdmin(Notice notice, ModelMap modelMap, HttpServletRequest request, HttpSession session){
        log.info(timeToken+"进入addNoticeByAdmin方法!!!");
        try{
            log.info(timeToken+"进入addNoticeByAdmin的try方法!!!");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            int status = 1;
            notice.setCreateTime(date);
            notice.setNoticeReleaseTime(sdf.parse(notice.getNoticeReleaseTimeStr()));
            notice.setNoticeEndTime(sdf.parse(notice.getNoticeEndTimeStr()));
            notice.setNoticeStatus(status);
            noticeService.insertAdminByAdminId(notice);
            return "redirect:/notice/queryAllNoticeByStatus";
        }catch(Exception e){
            log.error(timeToken+"进入addNoticeByAdmin的catch方法!!!");
            return "jsp-error/error-page";

        }

    }
    @RequestMapping("/selectNoticeById")
    public String selectNoticeById(ModelMap modelMap, @RequestParam("noticeId") int noticeId){
        log.info("进入selectNoticeById方法!!!");
        try{
            log.info("进入selectNoticeById的try方法");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Notice notice = noticeService.selectNoticeById(noticeId);
            //notice.getNoticeReleaseTime();
            String adminName = null;
            notice.setNoticeReleaseTimeStr(sdf.format(notice.getNoticeReleaseTime()));
            notice.setNoticeEndTimeStr(sdf.format(notice.getNoticeEndTime()));
            notice.setCreateTimeStr(sdf.format(notice.getCreateTime()));
            Admin admin = adminService.selectByAdminId(notice.getAdminId());
            adminName = admin.getAdminName();
            if(!"".equals(adminName)){
                adminName = admin.getAdminName();
                modelMap.put("adminName",adminName);
            }else{
                adminName = "无";
                modelMap.put("adminName",adminName);
            }
            modelMap.put("notice",notice);
            return "jsp-behind/notice-detail";
        }catch (Exception e){
            log.error("进入selectNoticeById的catch方法");
            return "jsp-error/error-page";
        }

    }

    @RequestMapping("/selectNoticeByIdForUpdate")
    public String selectNoticeByIdForUpdate(ModelMap modelMap, @RequestParam("noticeId") int noticeId){
        log.info("进入selectNoticeById方法!!!");
        try{
            log.info("进入selectNoticeById的try方法");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Notice notice = noticeService.selectNoticeById(noticeId);
            String adminName = null;
            notice.setNoticeReleaseTimeStr(sdf.format(notice.getNoticeReleaseTime()));
            notice.setNoticeEndTimeStr(sdf.format(notice.getNoticeEndTime()));
            notice.setCreateTimeStr(sdf.format(notice.getCreateTime()));
            Admin admin = adminService.selectByAdminId(notice.getAdminId());
            adminName = admin.getAdminName();
            if(!"".equals(adminName)){
                adminName = admin.getAdminName();
                modelMap.put("adminName",adminName);
            }else{
                adminName = "无";
                modelMap.put("adminName",adminName);
            }
            modelMap.put("notice",notice);
            return "jsp-behind/notice-update";
        }catch (Exception e){
            log.error("进入selectNoticeById的catch方法");
            return "jsp-error/error-page";
        }

    }
    @RequestMapping("/updateNoticeById")
    public String updateNoticeById(Notice notice){
        log.info("进入updateNoticeById方法!!!");
        try{
            log.info("进入updateNoticeById的try方法");
            int status = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            notice.setNoticeReleaseTime(sdf.parse(notice.getNoticeReleaseTimeStr()));
            notice.setNoticeEndTime(sdf.parse(notice.getNoticeEndTimeStr()));
            notice.setNoticeStatus(status);
            noticeService.updateAdminByAdminId(notice);
            return "redirect:/notice/queryAllNoticeByStatus";
        }catch (Exception e){
            log.error("进入updateNoticeById的catch方法"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

}
