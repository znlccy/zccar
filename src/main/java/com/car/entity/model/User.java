package com.car.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.car.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 15:09
 * @FileName: User.java
 * @Desc    : 用户实体类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户实体类")
@TableName(value = "tb_user")
public class User extends BaseEntity {

    // 序列化
    private static final long serialVersionUID = 3908125143491882439L;

    // 用户名
    @ApiModelProperty(value = "用户名", example = "demo")
    private String username;

    // 密码
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    // 手机号码
    @ApiModelProperty(value = "手机号码", example = "15379008689")
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "1235234@126.com")
    private String email;

    @ApiModelProperty(value = "是否可用", example = "1")
    private Integer enabled;
}
