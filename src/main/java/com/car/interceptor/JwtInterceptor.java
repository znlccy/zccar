package com.car.interceptor;

import com.car.entity.model.User;
import com.car.exception.CarException;
import com.car.service.UserService;
import com.car.utils.JwtUtil;
import com.car.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-21 15:34
 * @FileName: JwtInterceptor.java
 * @Desc    : Jwt拦截器
 **/
public class JwtInterceptor implements HandlerInterceptor {

    // 依赖UserService
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        // 1.如果不映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 2.执行认证
        if (!StringUtils.hasText(token)) {
            throw new CarException(40001, "无Token，请重新登录");
        }

        // 3.验证token是否有效
        Boolean result = JwtUtil.validateToken(token);
        if (!result) {
            throw new CarException(40002, "Token无效，请重新登录");
        }

        // 4.获取用户信息
        String username = JwtUtil.getUsernameByToken(token);
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new CarException(40003, "用户不存在或被禁用，请重新登录或联系管理员!");
        }

        // 5.用户不为空的话放入ThreadLocal里面
        UserHolder.setUser(user);

        // 6.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
