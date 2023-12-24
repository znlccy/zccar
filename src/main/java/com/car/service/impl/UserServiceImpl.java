package com.car.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.car.entity.dto.UserLoginDto;
import com.car.entity.dto.UserRegisterDto;
import com.car.entity.model.User;
import com.car.entity.vo.UserVo;
import com.car.enums.ResultCodeEnum;
import com.car.exception.CarException;
import com.car.mapper.UserMapper;
import com.car.result.Result;
import com.car.service.UserService;
import com.car.utils.JwtUtil;
import com.car.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.car.constants.CommonConstants.MD5_SALT;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:56
 * @FileName: UserServiceImpl.java
 * @Desc    : 用户服务实现类
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 依赖RedisTemplate
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @Override
    public Result<?> login(UserLoginDto userLoginDto, HttpSession session) {
        // 1.获取请求过来的参数
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        String code = userLoginDto.getCode();

        // 2.校验验证码是否有效(从Session中获取验证码)
        // String captcha = (String) session.getAttribute("captcha");
        String captcha = (String) redisTemplate.opsForValue().get("captcha");
        if (captcha == null || !captcha.equalsIgnoreCase(code)) {
            return Result.error("验证码错误，请重新获取验证码!");
        }

        // 3.判断数据库中是否有该用户(密码需要加密比较)
        User user = existsUsernameAndPassword(username, password);

        // 4.输出结果如果有，生成token返回给前端,并存放在Redis中
        if (ObjectUtils.isEmpty(user)) {
            return Result.error("用户不存在,请注册一个账号!");
        }
        String token = JwtUtil.generateToken(username, password);
        redisTemplate.opsForValue().set("user:token:" + username, token, 24*60l, TimeUnit.MINUTES);

        // 5.返回数据
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("userId", user.getId());
        data.put("token", token);
        return Result.ok(data).message("登录成功!");
    }

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        User user = lambdaQuery().eq(User::getUsername, username)
                .eq(User::getEnabled, true)
                .one();
        return user;
    }

    /**
     * 用户注册
     * @param userRegisterDto 用户注册DTO对象
     * @return
     */
    @Override
    public Result<?> register(UserRegisterDto userRegisterDto) {
        // 1.根据用户名查找数据库，看是否已经存在
        User user = lambdaQuery().eq(User::getUsername, userRegisterDto.getUsername()).one();
        if (!ObjectUtils.isEmpty(user)) {
            throw new CarException(ResultCodeEnum.USER_ALREADY_EXISTS);
        }

        // 2.保存用户信息到数据库(先从缓存中删除，然后再添加到数据库)
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        User userQuery = (User) hashOperations.get("user:" + userRegisterDto.getUsername(), "user");
        if (!ObjectUtils.isEmpty(userQuery)) {
            redisTemplate.opsForHash().delete("user:" + userRegisterDto.getUsername(), "user");
        }

        // 3.保存到数据库中
        User userSave = new User();
        BeanUtils.copyProperties(userRegisterDto,userSave);
        // 用户密码要加盐加密
        userSave.setPassword(SecureUtil.md5(MD5_SALT + userSave.getPassword()));
        save(userSave);

        return Result.ok();
    }

    /**
     * 用户信息
     * @return
     */
    @Override
    public Result<?> info() {
        // 1.从ThreadLocal中获取用户信息
        User user = UserHolder.getUser();
        if (ObjectUtils.isEmpty(user)) {
            return Result.error("用户未登录，请重新登录!");
        }

        // 2.用户敏感信息脱敏
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        // 3.返回数据
        return Result.ok(userVo);
    }

    /**
     * 判断用户是否存在
     * @param username
     * @param password
     * @return
     */
    private User existsUsernameAndPassword(String username, String password) {
        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, SecureUtil.md5(MD5_SALT + password))
                .ge(User::getEnabled, true).one();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user;
    }
}
