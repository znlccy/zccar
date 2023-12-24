package com.car.exception;

import com.car.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:31
 * @FileName: CarException.java
 * @Desc    : 自定义餐饮车异常类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarException extends RuntimeException {

    // 序列化
    private static final long serialVersionUID = 8089984521354862492L;

    // 状态码
    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    /**
     * 有参构造方法
     * @param resultCodeEnum
     */
    public CarException(ResultCodeEnum resultCodeEnum) {
        this.setMessage(resultCodeEnum.getMessage());
        this.setCode(resultCodeEnum.getCode());
    }

    public CarException(String message, Throwable cause) {
        super(message, cause);
    }
}
