package com.car.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 15:58
 * @FileName: UserDto.java
 * @Desc    : 用户登录DTO类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@ApiModel(value = "用户登录DTO类'")
public class UserLoginDto implements Serializable {

    // 序列化版本
    private static final long serialVersionUID = -5727026378308982572L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @Size(min = 6, max = 32, message = "用户密码长度为6~32位")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

}
