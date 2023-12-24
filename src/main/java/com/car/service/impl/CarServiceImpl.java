package com.car.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.car.entity.model.Car;
import com.car.exception.CarException;
import com.car.mapper.CarMapper;
import com.car.result.Result;
import com.car.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-22 14:33
 * @FileName: CarServiceImpl.java
 * @Desc    : 餐饮车服务实现类
 **/
@Slf4j
@Service
@EnableTransactionManagement
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    // 依赖RedisTemplate
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 查询餐饮车信息
     * @param carId 餐饮车ID
     * @return
     */
    @Override
    public Result<?> info(Long carId) {

        // 1.判断传输参数是否为空
        if (carId == null) {
            return Result.error("餐饮车ID不能为空");
        }

        // 2.查询数据库
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Car car = (Car) valueOperations.get("car:" + carId);

        // 3.缓存中没有，从数据库中获取，如果有直接返回
        if (car == null) {
            // 4.从数据库中查询
            car = lambdaQuery().eq(Car::getId, carId).one();

            // .5把数据更新到缓存中
            valueOperations.set("car:" + carId, JSONUtil.toJsonStr(car));
        }

        // 6.返回数据
        return Result.ok(car);
    }

    /**
     * 上传餐饮车信息
     * @param file
     * @return
     */
    @Transactional
    @Override
    public Result<?> upload(MultipartFile file) {
        if (!isExcelOrCsvFile(file)) {
            throw new CarException(5001, "导入的不是Excel文件或csv文件");
        }
        // 2.反射成Student对象
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            params.setHeadRows(1);
            List<Car> carList = ExcelImportUtil.importExcel(inputStream, Car.class, params);
            if (!CollectionUtils.isEmpty(carList)) {
                carList.forEach(car -> {
                    // 查找数据库中是否已经存在该餐饮车了
                    Car carQuery = lambdaQuery().eq(Car::getLocationId, car.getLocationId()).one();
                    if (ObjectUtils.isEmpty(carQuery)) {
                        // 保存车辆信息
                        save(car);
                    }
                });
            }
        } catch (Exception e) {
            log.error("导入excel或csv文件错误信息:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok();
    }

    /**
     * 同步车辆信息到Redis中
     * @return
     */
    @Override
    public Result<?> sync() {

        String carKey = "car:location";
        // 1.获取车辆信息
        List<Car> carList = list();

        // 2.同步到Redis
        carList.forEach(car -> {
            // 维度
            Double latitude = Double.valueOf(car.getLatitude());
            // 经度
            Double longitude = Double.valueOf(car.getLongitude());
            Long id = car.getId();
            redisTemplate.opsForGeo().add(carKey, new Point(longitude, latitude), id);
        });
        return Result.ok();
    }

    /**
     * 获取该餐饮车附近的餐饮车
     * @param carId
     * @return
     */
    @Override
    public Result<?> neighborhood(Long carId, Double distance, Integer size) {

        String carKey = "car:location";

        // 1.校验参数
        if (carId == null) {
            return Result.error("餐饮车ID不能为空");
        }

        // 2.查询餐饮车
        Car car = getById(carId);
        if (null == car) {
            return Result.error("该餐饮车不存在");
        }

        // 3.获取该餐饮车的经度和维度
        Double latitude = Double.valueOf(car.getLatitude());
        Double longitude = Double.valueOf(car.getLongitude());

        // 4.将该餐饮车加入到redis中
        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        geoOperations.add(carKey, new Point(longitude, latitude), carId);

        // 5.获取该餐饮车附近的餐饮车
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = geoOperations.radius(carKey, carId, new Distance(distance, Metrics.KILOMETERS));
        List<String> result = new ArrayList<>();
        geoResults.getContent().forEach(geoResult -> {
            result.add(String.valueOf(geoResult.getContent().getName()));
        });

        // 6.查询所有附近的餐车
        List<Car> cars = new ArrayList<>();
        result.forEach(queryCarId -> {
            long truckId = Long.parseLong(queryCarId);
            Car carQuery = getById(truckId);
            if (!ObjectUtils.isEmpty(carQuery)) {
                cars.add(carQuery);
            }
        });

        // 7.返回数据(分页查找)
        return Result.ok(cars.stream().limit(size).collect(Collectors.toList()));
    }

    /**
     * 餐饮车列表数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result<?> listData(Integer pageNo, Integer pageSize) {
        // 1.开始分页
        Page<Car> carPage = new Page<>(pageNo, pageSize);

        // 2.查询条件
        LambdaQueryWrapper<Car> queryWrapper = new LambdaQueryWrapper<>();

        // 3.排序(按照创建时间降序排列)
        queryWrapper.orderByDesc(Car::getCreateTime);

        // 4.获取餐饮分页之后的数据
        Page<Car> page = page(carPage, queryWrapper);

        // 3.返回数据
        return Result.ok(page);
    }

    /**
     * 导入文件格式校验
     * @param file
     * @return
     */
    private boolean isExcelOrCsvFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if (!(suffix.equals("xls") || suffix.equals("xlsx") || suffix.equals("csv"))) {
            return false;
        }
        return true;
    }
}
