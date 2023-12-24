package com.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.car.entity.model.Car;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-22 14:06
 * @FileName: CarMapper.java
 * @Desc    : 餐饮车Mapper类
 **/
@Mapper
public interface CarMapper extends BaseMapper<Car> {

}
