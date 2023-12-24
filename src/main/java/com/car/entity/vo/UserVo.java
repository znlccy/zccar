package com.car.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-22 13:49
 * @FileName: UserVo.java
 * @Desc    : 用户Vo类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户VO类")
public class UserVo implements Serializable {

    // 序列化
    private static final long serialVersionUID = -1222071538720787095L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;
}
