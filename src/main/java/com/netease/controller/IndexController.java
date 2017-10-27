package com.netease.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.bean.*;
import com.netease.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 控制器
 */
@Controller
public class IndexController {

    @Autowired
    private DBDao dao;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        System.out.println("/login");
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        System.out.println("/logout");
        //session
        Person user = (Person) session.getAttribute("user");
        if(user!=null) {
            session.removeAttribute("user");
            session.setMaxInactiveInterval(0);
            session.invalidate();
        }
        return "login";
    }

    @RequestMapping(value="/api/login", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult index(@RequestParam String userName, @RequestParam String password,
                              ModelMap map, HttpSession session) {
        System.out.println("/api/login");
        System.out.println( userName+" "+password );

        List<Person> personList = dao.getPersons(userName);
        ReturnResult returnResult = new ReturnResult();
        if(personList.size()==1) {
            if(personList.get(0).getPassword().equals(password)) {
                map.put("user", personList.get(0));
                //System.out.println(personList.get(0));
                returnResult.setCode(200);
                returnResult.setMessage("登录成功");
                returnResult.setResult(true);
                session.setAttribute("user", personList.get(0));
            }else {
                returnResult.setCode(100);
                returnResult.setMessage("密码错误");
                returnResult.setResult(false);
            }
        }else {
            returnResult.setCode(100);
            returnResult.setMessage("用户名错误");
            returnResult.setResult(false);
        }
        System.out.println(returnResult);
        return returnResult;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView root(HttpSession session, HttpServletRequest request,
                            @RequestParam(value = "type", required = false) String listType) {
        System.out.println("/");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        Person user = (Person) session.getAttribute("user");
        if(user!=null) {
            if(user.getUsertype()== 0) {//buyer  已购买
                if(listType!=null) {
                    if(listType.equals("1")) { //未购买
                        System.out.println("un buy");
                        List<Content> contentList = dao.getUnBuyedContentsByPerson(user.getId());
                        System.out.println(contentList);
                        mv.addObject("productList", contentList);
                    }
                }else { //已购买
                    System.out.println("buyed");
                    List<Content> contentList = dao.getBuyedContentsByPerson(user.getId());
                    System.out.println(contentList);
                    mv.addObject("productList", contentList);
                }

            }else {//1 seller
                System.out.println("seller");
                List<Content> contentList = dao.getContentsBySeller();
                System.out.println(contentList);
                mv.addObject("productList", contentList);
            }

        }else {
            List<Content> contentList = dao.getContents();
            System.out.println(contentList.size());
            mv.addObject("productList", contentList);
        }

        return mv;
    }

    @RequestMapping(value="/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("show");
        Person person = (Person)request.getSession().getAttribute("user");
        if(person!=null) {
            if(person.getUsertype()== 0) {//买家
                Content content = dao.getBuyedContentByPersonAndContent(person.getId(), id);
                mv.addObject("product", content);
            }else {//卖家
                Content content = dao.getContentBySeller(id);
                mv.addObject("product", content);
            }
        }else {//未登录
            Content content = dao.getContentById2(id);
            mv.addObject("product", content);
        }
        return mv;
    }



}
