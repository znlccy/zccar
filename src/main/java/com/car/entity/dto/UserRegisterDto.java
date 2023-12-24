package com.car.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-22 12:27
 * @FileName: UserRegisterDto.java
 * @Desc    : 用户注册DTO对象
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户注册DTO对象")
public class UserRegisterDto implements Serializable {

    // 序列化
    private static final long serialVersionUID = -5421619784040815985L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度是6~32位")
    @ApiModelProperty(value = "密码")
    private String password;

    @Pattern(regexp = "^1(3|5|6|7|8|9)\\d{9}$", message = "手机格式不正确")
    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "电子邮箱")
    private String email;

}
