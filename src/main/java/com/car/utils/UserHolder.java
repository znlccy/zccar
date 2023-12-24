package com.car.utils;

import com.car.entity.model.User;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 16:04
 * @FileName: UserHolder.java
 * @Desc    : UserHolder工具类
 **/
public class UserHolder {

    // 声明ThreadLocal变量
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    /**
     * 获取用户
     * @return
     */
    public static User getUser() {
        return threadLocal.get();
    }

    /**
     * 设置用户
     * @param user
     */
    public static void setUser(User user) {
        threadLocal.set(user);
    }

    /**
     * 删除用户
     */
    public static void removeUser() {
        threadLocal.remove();
    }
}
