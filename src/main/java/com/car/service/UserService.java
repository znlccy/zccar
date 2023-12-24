package com.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.car.entity.dto.UserLoginDto;
import com.car.entity.dto.UserRegisterDto;
import com.car.entity.model.User;
import com.car.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:55
 * @FileName: UserService.java
 * @Desc    : 用户服务接口类
 **/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    Result<?> login(UserLoginDto userLoginDto, HttpSession session);

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 用户注册
     * @param userRegisterDto
     * @return
     */
    Result<?> register(UserRegisterDto userRegisterDto);

    /**
     * 用户信息
     * @return
     */
    Result<?> info();
}
