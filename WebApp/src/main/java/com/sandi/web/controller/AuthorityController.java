package com.sandi.web.controller;

import com.sandi.web.model.Authority;
import com.sandi.web.service.IAuthorityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by 王子龙 on 2017-05-22.
 */
@Controller
@RequestMapping("/authority")
public class AuthorityController {

    private static final Logger log = Logger.getLogger(AuthorityController.class);

    long timeToken = System.currentTimeMillis();

    @Autowired
    private IAuthorityService authorityService;

    @RequestMapping("/queryAllAuthorityInfo")
    public String queryAllAuthorityInfo(ModelMap modelMap){
        log.info(timeToken+"进入queryAllAuthorityInfo方法!");
        try{
            log.info(timeToken+"进入queryAllAuthorityInfo的try方法!");
            List<Authority> authorityList = authorityService.queryAllAuthority();
            modelMap.put("authorityList",authorityList);
            return "jsp-behind/authority-list";
        }catch(Exception e){
            log.error(timeToken+"进入queryAllAuthorityInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 权限信息的添加
     * @param authority
     * @return
     */
    @RequestMapping("/addAuthorityInfo")
    public String addAuthorityInfo(Authority authority){
        log.info(timeToken+"进入addAuthorityInfo方法!");
        try{
            log.info(timeToken+"进入addAuthorityInfo的try方法,接收权限信息参数:"+authority);
            authorityService.addAuthority(authority);
            return "redirect:/authority/queryAllAuthorityInfo.do";
        }catch(Exception e){
            log.error(timeToken+"进入addAuthorityInfo的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }


    /**
     * 权限删除
     * @param authorityId
     * @return
     */
    @RequestMapping("/deleteAurhority")
    public String deleteAurhority(@RequestParam("authorityId") int authorityId){
        log.info(timeToken+"进入deleteAurhority方法,接收权限ID:"+authorityId);
        try{
            log.info(timeToken+"进入deleteAurhority的try方法!");
            authorityService.deleteAuthorityById(authorityId);
            return "redirect:/authority/queryAllAuthorityInfo.do";
        }catch(Exception e){
            log.error(timeToken+"进入deleteAurhority的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }
}
