package com.withstars.controller;

import com.withstars.domain.Tab;
import com.withstars.service.impl.ReplyServiceImpl;
import com.withstars.service.impl.TabServiceImpl;
import com.withstars.service.impl.TopicServiceImpl;
import com.withstars.service.impl.UserServiceImpl;
import com.withstars.util.Captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 主控制类
 */
@Controller
public class MainController {

    @Autowired
    public TopicServiceImpl topicService;
    @Autowired
    public ReplyServiceImpl replyService;
    @Autowired
    public UserServiceImpl userService;
    @Autowired
    public TabServiceImpl tabService;

    /**
     * 进入登录页面.
     */
    @RequestMapping(value = {"/signin"})
    public ModelAndView signin(){
        ModelAndView signinPage=new ModelAndView("signin");

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();

        signinPage.addObject("topicsNum",topicsNum);
        signinPage.addObject("usersNum",usersNum);
        return  signinPage;
    }

    /**
     * 进入注册页面.
     */
    @RequestMapping("/signup")
    public ModelAndView signup(){
        ModelAndView signupPage=new ModelAndView("signup");

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();

        signupPage.addObject("topicsNum",topicsNum);
        signupPage.addObject("usersNum",usersNum);
        return  signupPage;
    }


    /**
     * 生成验证码
     * @throws IOException 
     */

    @RequestMapping("/checkcode")
    public void drawCheckcode(HttpServletResponse res, HttpSession session) throws IOException{
        res.setContentType("image/jpg");
        int width = 70;
        int height = 25;
        Captcha c = Captcha.getInstance();
        c.set(width, height);
        String checkcode = c.generateCheckcode();
        // 验证码放入session
        session.setAttribute("cCode", checkcode);
        OutputStream os = res.getOutputStream();
        ImageIO.write(c.generateCheckImg(checkcode), "jpg", os);
    }

    /**
     * 进入新建主题页面
     */
    @RequestMapping(value = {"/new"})
    public ModelAndView newTopic(){
        ModelAndView newTopicPage=new ModelAndView("new");
        List<Tab> tabs=tabService.getAllTabs();

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();

        newTopicPage.addObject("tabs",tabs);
        newTopicPage.addObject("topicsNum",topicsNum);
        newTopicPage.addObject("usersNum",usersNum);
        return  newTopicPage;
    }

    /**
     * 配置404页面
     */
    @RequestMapping("*")
    public String notFind(){
        return "404";
    }
}
