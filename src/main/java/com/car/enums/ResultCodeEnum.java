package com.car.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.car.constants.TipMessage.*;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:47
 * @FileName: ResultCodeEnum.java
 * @Desc    : 统一结果状态码枚举类
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "统一结果状态码枚举类", description = "")
public enum ResultCodeEnum {

    // 成功
    SUCCESS(20000, SUCCESS_MESSAGE),

    // 失败
    FAILURE(40000, FAILURE_MESSAGE),

    // 用户已经存在
    USER_ALREADY_EXISTS(60001, USER_ALREADY_EXISTS_MESSAGE);

    // 状态码
    @ApiModelProperty(value = "状态码")
    private Integer code;

    // 提示信息
    @ApiModelProperty(value = "提示信息")
    private String message;
}
