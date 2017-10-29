package com.netease.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.bean.Content;
import com.netease.bean.Person;
import com.netease.bean.ReturnResult;
import com.netease.bean.Trx;
import com.netease.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 控制器
 */
@Controller
public class BuyerController {

    @Autowired
    private DBDao dao;

    private Logger logger = Logger.getLogger("buyer");;

    /**
     * 购买post
     * @param jsonStr
     * @param request
     * @return
     */
    @RequestMapping(value="/api/buy", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ReturnResult buy(@RequestBody String jsonStr, HttpServletRequest request) {
        Person person = (Person)request.getSession().getAttribute("user");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = formatter.format(new Date());//一次购买多个商品，统一时间
        JSONArray arr = JSONArray.parseArray(jsonStr);
        boolean flag = true;
        for(int i=0;i<arr.size();i++) {//遍历每个商品
            int num = Integer.parseInt(JSONObject.parseObject(arr.get(i).toString()).get("number").toString());
            int id = Integer.parseInt(JSONObject.parseObject(arr.get(i).toString()).get("id").toString());
            Content content = dao.getContentById(id);
            Trx trx = new Trx( content, person, content.getPrice(), timeStr, num);
            logger.info(trx.toString());
            int insertNum = dao.insertTrx(trx);//新增1交易记录
            if(insertNum!=1) {
                flag=false;
            }
        }
        ReturnResult rr = new ReturnResult();
        if(flag) {
            rr.setCode(200);
            rr.setResult(flag);
        }

        return rr;
    }

    /**
     * 查看购物车GET
     * @return
     */
    @RequestMapping(value="/settleAccount", method = RequestMethod.GET)
    public ModelAndView settleAccount() {
        ModelAndView mv = new ModelAndView("settleAccount");
        return mv;
    }

    /**
     * 查看账务GET
     * @param request
     * @return
     */
    @RequestMapping(value="/account", method = RequestMethod.GET)
    public ModelAndView account(HttpServletRequest request) {
        Person persopn = (Person)request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView("account");
        List<Content> contentList = dao.getBuyedContentsByPerson(persopn.getId());
        logger.info(contentList.toString());
        mv.addObject("buyList", contentList);
        return mv;
    }

}
