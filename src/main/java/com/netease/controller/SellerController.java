package com.netease.controller;

import com.netease.bean.Content;
import com.netease.bean.Image;
import com.netease.bean.ReturnResult;
import com.netease.bean.UploadReturnResult;
import com.netease.dao.DBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * 控制器
 */
@Controller
public class SellerController {

    @Autowired
    private DBDao dao;

    private Logger logger = Logger.getLogger("seller");;

    /**
     * 进入发布页GET
     * @return
     */
    @RequestMapping(value="/public", method = RequestMethod.GET)
    public ModelAndView publish() {
        ModelAndView mv = new ModelAndView("public");
        return mv;
    }

    /**
     * 发布提交内容POST
     * @param title
     * @param summary
     * @param price
     * @param detail
     * @param image
     * @return
     */
    @RequestMapping(value="/publicSubmit", method = RequestMethod.POST)
    public ModelAndView publicSubmit(@RequestParam String title, @RequestParam String summary,
                                     @RequestParam int price, @RequestParam String detail,
                                     @RequestParam String image) {
        ModelAndView mv = new ModelAndView("publicSubmit");
        Content content = new Content();
        content.setTitle(title);
        content.setPrice(price);
        content.setSummary(summary);
        content.setDetail(detail);
        content.setImage(image);
        int num = dao.insertContent(content);
        if(num==1) {
            mv.addObject("product", content);
            logger.info("发布成功："+content);
        }
        return mv;
    }

    /**
     * 删除内容POST
     * @param id
     * @return
     */
    @RequestMapping(value="/api/delete", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult delete(@RequestParam int id) {
        ReturnResult rr = new ReturnResult();
        int num = dao.deleteContentById(id);
        logger.info("delete: " + num);
        if(num==1) {
            rr.setCode(200);
            rr.setResult(true);
        }
        return rr;
    }

    /**
     * 进入编辑页GET，编辑已发布的内容
     * @param id   content.id
     * @return
     */
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id) {
        ModelAndView mv = new ModelAndView("edit");
        Content content = dao.getContentById(id);
        mv.addObject("product", content);
        return mv;
    }

    /**
     * 编辑提交POST，修改内容
     * @param id
     * @param title
     * @param summary
     * @param price
     * @param detail
     * @param image  url
     * @return
     */
    @RequestMapping(value="/editSubmit", method = RequestMethod.POST)
    public ModelAndView editSubmit(@RequestParam int id,
                                   @RequestParam String title,
                                   @RequestParam String summary,
                                   @RequestParam int price,
                                   @RequestParam String detail,
                                   @RequestParam String image) {
        ModelAndView mv = new ModelAndView("editSubmit");
        Content content = new Content();
        content.setId(id);
        content.setTitle(title);
        content.setPrice(price);
        content.setSummary(summary);
        content.setDetail(detail);
        content.setImage(image);
        int num = dao.updateContent(content);
        if(num==1) {//成功更新一条内容
            mv.addObject("product", content);
            logger.info("提交成功："+content);
        }
        return mv;
    }

    /**
     * 上传图片POST
     * @param file
     * @return  200  图片url
     */
    @RequestMapping(value="/api/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadReturnResult upload(@RequestParam("file") CommonsMultipartFile file) {
        UploadReturnResult urr = new UploadReturnResult();
        Image image = new Image();
        image.setBytes(file.getBytes());
        image.setName(file.getFileItem().getFieldName());
        int num = dao.insertImage(image);
        if(num ==1) {
            urr.setCode(200);
            urr.setResult("/getImage?id="+image.getId());
            logger.info(urr.toString());
        }

        return urr;
    }

    /**
     * 获取图片GET
     * url = /getImage?id=1
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/getImage",method= RequestMethod.GET)
    public void getPhotoById (@RequestParam int id, final HttpServletResponse response) throws IOException {
        Image image = dao.getImageById(id);
        byte[] data = image.getBytes();
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }



}
