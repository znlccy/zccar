package com.car.result;

import com.car.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:31
 * @FileName: Result.java
 * @Desc    : 自定义统一结果类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "统一结果类", description = "")
public class Result<T> {

    // 状态码
    @ApiModelProperty(value = "状态码")
    private Integer code;

    // 响应消息
    @ApiModelProperty(value = "响应消息")
    private String message;

    // 响应数据
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 构建结果类
     * @param data 响应数据
     * @param <T> 返回泛型数据
     * @return Result:统一结果类
     */
    public static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (null != data) {
            result.setData(data);
        }
        return result;
    }

    /**
     * 构建结果类
     * @param data 响应数据
     * @param resultCodeEnum 结果状态码枚举类
     * @param <T> data:响应数据
     * @return Result:统一结果类
     */
    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 成功返回结果
     * @param data 响应数据
     * @param <T> 泛型类
     * @return Result:统一结果类
     */
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 成功返回结果
     * @param <T> 泛型类
     * @return Result:统一结果类
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 错误返回结果
     * @param <T> 泛型类
     * @return Result:统一结果类
     */
    public static <T> Result<T> error() {
        return build(null, ResultCodeEnum.FAILURE);
    }

    /**
     * 错误返回结果
     * @param message 响应消息
     * @param <T> 泛型类
     * @return Result:统一结果类
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = build(null, ResultCodeEnum.FAILURE);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误返回结果
     * @param data 响应数据
     * @param <T> 泛型类
     * @return Result:统一返回结果
     */
    public static <T> Result<T> error(T data) {
        return build(data, ResultCodeEnum.FAILURE);
    }

    /**
     * 设置状态码
     * @param code 状态码
     * @return Result:统一结果类
     */
    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * 设置响应消息
     * @param message 响应消息
     * @return Result:统一结果类
     */
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置响应数据
     * @param data 响应数据
     * @return Result:统一结果类
     */
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}
