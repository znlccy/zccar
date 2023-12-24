package com.car.handler;

import com.car.exception.CarException;
import com.car.result.Result;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Hashtable;
import java.util.Vector;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:39
 * @FileName: GlobalExceptionHandler.java
 * @Desc    : 全局异常处理类
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理统一异常
     * @param e 全局最大异常类
     * @return 统一返回json数据
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        // 打印异常堆栈异常信息
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    /**
     * 处理自定义异常
     * @param e 自定义异常类
     * @return 统一返回json数据
     */
    @ExceptionHandler(CarException.class)
    public Result<?> carExceptionHandler(CarException e) {
        // 打印异常堆栈信息
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    /**
     * 数据校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> validateExceptionHandler(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题:{},异常类型:{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Hashtable<String, Object> errorMessageMap = new Hashtable<>();
        bindingResult.getFieldErrors().forEach(item -> {
            // 获取错误信息
            String message = item.getDefaultMessage();
            // 获取错误的属性名字
            String field = item.getField();
            errorMessageMap.put(field, message);
        });
        return Result.error(errorMessageMap);
    }

    /**
     * Token过期异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public Result<?> expiredJwtExceptionHandler(ExpiredJwtException e) {
        e.printStackTrace();
        return Result.error("Token已过期，请重启登录获取!");
    }
}
