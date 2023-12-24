package com.car.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.IOException;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-21 14:34
 * @FileName: CaptchaController.java
 * @Desc    : 图形验证码控制器
 **/
@Api(tags = "图形验证码控制器")
@RestController
public class CaptchaController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取图形验证码
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "获取图形验证码")
    @GetMapping(value = "/captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {

        /*// 定义response输出类型为image/jpeg
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers
        response.setHeader("Cache-Controller", "no-store, no-cache, must-revalidate");
        // Set standard HTTP/1.1 no-cache headers (use AddHeader)
        response.setHeader("Cache-Controller", "post-check=0, pre-check=0");
        // set standard HTTP/1.0 n0-cache header
        response.setHeader("Prama", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");*/

        // 1.生成随机验证码
        RandomGenerator random = new RandomGenerator("0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ", 4);
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 80);
        circleCaptcha.setGenerator(random);
        Image image = circleCaptcha.createImage(circleCaptcha.getCode());

        // 2.生成好的验证码放入到Redis中
        // session.setAttribute("captcha", circleCaptcha.getCode());
        redisTemplate.opsForValue().set("captcha", circleCaptcha.getCode()) ;

        // 3.以图片方式输出
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            circleCaptcha.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
