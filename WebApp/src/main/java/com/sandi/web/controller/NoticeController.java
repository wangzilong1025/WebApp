package com.sandi.web.controller;

import com.sandi.web.model.Admin;
import com.sandi.web.model.AdminRole;
import com.sandi.web.model.Notice;
import com.sandi.web.service.IAdminRoleService;
import com.sandi.web.service.IAdminService;
import com.sandi.web.service.INoticeService;
import com.sandi.web.util.UtilStatic;
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
 * Created by 王子龙 on 2017-04-16.
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
    @Autowired
    private IAdminRoleService adminRoleService;

    @RequestMapping("/queryAllNoticeByStatus")
    public String queryAllNoticeByStatus(ModelMap modelMap,Notice notice,HttpSession session){
        log.info(timeToken+"进入queryAllNoticeByStatus方法");
        int noticeStatus = UtilStatic.STATIC_ONE;
        try{
            log.info(timeToken+"进入queryAllNoticeByStatus的try方法!");
            Admin admin  = (Admin) session.getAttribute("admin");
            Admin adminInfo = adminService.selectByAdminId(admin.getAdminId());
            AdminRole adminRole = adminRoleService.selectAdminRoleByAdminId(adminInfo.getAdminId());
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
            log.error(timeToken+"进入queryAllNoticeByStatus的catch方法!!!");
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
            int status = UtilStatic.STATIC_ONE;
            notice.setCreateTime(date);
            notice.setNoticeReleaseTime(sdf.parse(notice.getNoticeReleaseTimeStr()));
            notice.setNoticeEndTime(sdf.parse(notice.getNoticeEndTimeStr()));
            notice.setNoticeStatus(status);
            noticeService.insertAdminByAdminId(notice);
            return "redirect:/notice/queryAllNoticeByStatus.do";
        }catch(Exception e){
            log.error(timeToken+"进入addNoticeByAdmin的catch方法!!!");
            return "jsp-error/error-page";

        }

    }
    @RequestMapping("/selectNoticeById")
    public String selectNoticeById(ModelMap modelMap, @RequestParam("noticeId") int noticeId){
        log.info(timeToken+"进入selectNoticeById方法!!!");
        try{
            log.info(timeToken+"进入selectNoticeById的try方法");
            Notice notice = noticeService.selectNoticeById(noticeId);
            String adminName = null;
            notice.setNoticeReleaseTimeStr(UtilStatic.sdf.format(notice.getNoticeReleaseTime()));
            notice.setNoticeEndTimeStr(UtilStatic.sdf.format(notice.getNoticeEndTime()));
            notice.setCreateTimeStr(UtilStatic.sdf.format(notice.getCreateTime()));
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
            log.error(timeToken+"进入selectNoticeById的catch方法");
            return "jsp-error/error-page";
        }

    }

    @RequestMapping("/selectNoticeByIdForUpdate")
    public String selectNoticeByIdForUpdate(ModelMap modelMap, @RequestParam("noticeId") int noticeId){
        log.info(timeToken+"进入selectNoticeById方法!!!");
        try{
            log.info(timeToken+"进入selectNoticeById的try方法");
            Notice notice = noticeService.selectNoticeById(noticeId);
            String adminName = null;
            notice.setNoticeReleaseTimeStr(UtilStatic.sdf.format(notice.getNoticeReleaseTime()));
            notice.setNoticeEndTimeStr(UtilStatic.sdf.format(notice.getNoticeEndTime()));
            notice.setCreateTimeStr(UtilStatic.sdf.format(notice.getCreateTime()));
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
            log.error(timeToken+"进入selectNoticeById的catch方法");
            return "jsp-error/error-page";
        }

    }
    @RequestMapping("/updateNoticeById")
    public String updateNoticeById(Notice notice){
        log.info(timeToken+"进入updateNoticeById方法!!!");
        try{
            log.info(timeToken+"进入updateNoticeById的try方法");
            int status = UtilStatic.STATIC_ONE;
            notice.setNoticeReleaseTime(UtilStatic.sdf.parse(notice.getNoticeReleaseTimeStr()));
            notice.setNoticeEndTime(UtilStatic.sdf.parse(notice.getNoticeEndTimeStr()));
            notice.setNoticeStatus(status);
            noticeService.updateAdminByAdminId(notice);
            return "redirect:/notice/queryAllNoticeByStatus.do";
        }catch (Exception e){
            log.error(timeToken+"进入updateNoticeById的catch方法"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

    /**
     * 删除公告（状态变成0）
     * @param noticeId
     * @return
     */
    @RequestMapping("/deleteNoticeByNoticeId")
    public String deleteNoticeByNoticeId(@RequestParam("noticeId") int noticeId){
        log.info(timeToken+"进入deleteNoticeByNoticeId方法1");
        try{
            log.info(timeToken+"进入deleteNoticeByNoticeId的try方法1");
            Notice notice = noticeService.selectNoticeById(noticeId);
            notice.setNoticeStatus(UtilStatic.STATIC_ZERO);
            noticeService.updateAdminByAdminId(notice);
            return "redirect:/notice/queryAllNoticeByStatus.do";
        }catch(Exception e){
            log.error(timeToken+"进入deleteNoticeByNoticeId的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }




}
