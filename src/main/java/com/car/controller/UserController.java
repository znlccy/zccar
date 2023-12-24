package com.car.controller;

import com.car.entity.dto.UserLoginDto;
import com.car.entity.dto.UserRegisterDto;
import com.car.result.Result;
import com.car.service.UserService;
import com.car.validate.SaveGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 16:06
 * @FileName: UserController.java
 * @Desc    : 用户控制器
 **/
@Api(tags = "用户控制器")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<?> login(@Validated @RequestBody UserLoginDto userLoginDto, HttpSession session) {
        return userService.login(userLoginDto, session);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Result<?> register(@Validated({SaveGroup.class}) @RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    @ApiOperation(value = "用户信息")
    @GetMapping(value = "/info")
    public Result<?> info() {
        return userService.info();
    }
}
