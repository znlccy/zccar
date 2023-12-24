package com.car.controller;

import com.car.entity.dto.CarQueryDto;
import com.car.result.Result;
import com.car.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:30
 * @FileName: CarController.java
 * @Desc    : 餐饮车控制器
 **/
@Api(tags = "餐饮车控制器")
@RestController
@RequestMapping(value = "/car")
public class CarController {

    // 依赖CarService
    @Autowired
    private CarService carService;

    @ApiOperation(value = "餐饮车列表")
    @PostMapping(value = "/list/{pageNo}/{pageSize}")
    public Result<?> listData(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return carService.listData(pageNo, pageSize);
    }

    @ApiOperation(value = "餐饮车信息")
    @GetMapping(value = "/info/{carId}")
    public Result<?> info(@PathVariable Long carId) {
        return carService.info(carId);
    }

    @ApiOperation(value = "上传餐饮车信息")
    @PostMapping(value = "/upload")
    public Result<?> upload(@RequestParam("file")MultipartFile file) {
        return carService.upload(file);
    }

    @ApiOperation(value = "同步数据")
    @GetMapping(value = "/sync")
    public Result<?> syncCarData() {
        return carService.sync();
    }

    @ApiOperation(value = "获取附近的餐饮车")
    @PostMapping(value = "/neighborhood/{carId}/{distance}/{size}")
    public Result<?> getNeighborhoodCar(@PathVariable Long carId,
                                        @PathVariable Double distance,
                                        @PathVariable Integer size) {
        return carService.neighborhood(carId, distance, size);
    }

}
