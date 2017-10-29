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
import java.util.logging.Logger;

/**
 * 控制器
 */
@Controller
public class IndexController {

    @Autowired
    private DBDao dao;
    
    private Logger logger = Logger.getLogger("index");

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        logger.info("/login");
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        logger.info("/logout");
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
        logger.info("/api/login");
        logger.info( userName+" "+password );

        List<Person> personList = dao.getPersons(userName);
        ReturnResult returnResult = new ReturnResult();
        if(personList.size()==1) {
            if(personList.get(0).getPassword().equals(password)) {
                map.put("user", personList.get(0));
                //logger.info(personList.get(0));
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
        logger.info(returnResult.toString());
        return returnResult;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView root(HttpSession session, HttpServletRequest request,
                            @RequestParam(value = "type", required = false) String listType) {
        logger.info("/");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        Person user = (Person) session.getAttribute("user");
        if(user!=null) {
            if(user.getUsertype()== 0) {//buyer  已购买
                if(listType!=null) {
                    if(listType.equals("1")) { //未购买
                        logger.info("un buy");
                        List<Content> contentList = dao.getUnBuyedContentsByPerson(user.getId());
                        logger.info(contentList!=null?contentList.toString():"contentList:null");
                        mv.addObject("productList", contentList);
                    }
                }else { //已购买+未购买
                    logger.info("buyed");
                    List<Content> contentList = dao.getBuyedContentsByPerson(user.getId());
                    List<Content> contentList2= dao.getUnBuyedContentsByPerson(user.getId());
                    contentList.addAll(contentList2);
                    logger.info(contentList!=null?contentList.toString():"contentList:null");
                    mv.addObject("productList", contentList);
                }

            }else {//1 seller
                logger.info("seller");
                List<Content> contentList = dao.getContentsBySeller();
                logger.info(contentList!=null?contentList.toString():"contentList:null");
                mv.addObject("productList", contentList);
            }

        }else {
            List<Content> contentList = dao.getContents();
            logger.info(String.valueOf(contentList.size()));
            mv.addObject("productList", contentList);
        }

        return mv;
    }

    @RequestMapping(value="/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam("id") int contentId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("show");
        Person person = (Person)request.getSession().getAttribute("user");
        if(person!=null) {
            if(person.getUsertype()== 0) {//买家
                Trx trx = dao.getTrxByContentId(contentId);
                if(trx!=null) {//已购买
                    Content content = dao.getBuyedContentByPersonAndContent(person.getId(), contentId);
                    logger.info("buyer login");
                    logger.info(content!=null?content.toString():"buyed content:null ->error");
                    mv.addObject("product", content);
                }else {//未购买
                    Content content = dao.getContentById2( contentId);
                    logger.info("buyer login");
                    logger.info(content!=null?content.toString():"un buyed content:null ->error");
                    mv.addObject("product", content);
                }


            }else {//卖家
                Trx trx = dao.getTrxByContentId(contentId);
                if(trx!=null) {//已卖出
                    Content content = dao.getContentBySeller(contentId);
                    logger.info("seller login");
                    logger.info(content!=null?content.toString():"content:null");
                    mv.addObject("product", content);
                }else {//未卖出
                    Content content = dao.getContentById2(contentId);
                    logger.info("seller login");
                    logger.info(content!=null?content.toString():"content:null");
                    mv.addObject("product", content);
                }

            }
        }else {//未登录
            Content content = dao.getContentById2(contentId);
            logger.info("not login");
            logger.info(content!=null?content.toString():"content:null");
            mv.addObject("product", content);
        }
        return mv;
    }



}
