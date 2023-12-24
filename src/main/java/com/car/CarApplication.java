package com.car;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:26
 * @FileName: CarApplication.java
 * @Desc    : 餐饮车启动类
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.car.mapper")
public class CarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }
}
