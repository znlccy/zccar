package com.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.car.entity.model.Car;
import com.car.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-22 14:09
 * @FileName: CarService.java
 * @Desc    : 餐饮车服务接口类
 **/
public interface CarService extends IService<Car> {

    /**
     * 获取餐饮车信息
     * @param carId 餐饮车ID
     * @return 统一返回结果类
     */
    Result<?> info(Long carId);

    /**
     * 上传餐饮车信息
     * @param file
     * @return
     */
    Result<?> upload(MultipartFile file);

    /**
     * 同步车辆信息
     * @return
     */
    Result<?> sync();

    /**
     * 获取该餐饮车附近的餐饮车
     * @param carId
     * @return
     */
    Result<?> neighborhood(Long carId, Double distance, Integer size);

    /**
     * 餐饮车列表数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result<?> listData(Integer pageNo, Integer pageSize);
}
