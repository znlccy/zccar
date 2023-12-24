package com.car.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-23 23:09
 * @FileName: CarQueryDto.java
 * @Desc    : 餐饮车查询对象
 **/
public class CarQueryDto implements Serializable {

    @ApiModelProperty(value = "设施类型")
    private String facilityType;

}
