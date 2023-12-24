package com.car.entity.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.car.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 15:15
 * @FileName: Car.java
 * @Desc    : 餐饮车实体类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "餐饮车实体类")
@TableName(value = "tb_car")
public class Car extends BaseEntity {

    // 序列化
    private static final long serialVersionUID = -6355657519959385974L;

    @Excel(name = "locationid", orderNum = "1")
    @ApiModelProperty(value = "位置ID")
    private String locationId;

    @Excel(name = "Applicant", orderNum = "2")
    @ApiModelProperty(value = "申请人")
    private String applicant;

    @Excel(name = "FacilityType", orderNum = "3")
    @ApiModelProperty(value = "设施类型")
    private String facilityType;

    @Excel(name = "cnn", orderNum = "4")
    @ApiModelProperty(value = "cnn")
    private String cnn;

    @Excel(name = "LocationDescription", orderNum = "5")
    @ApiModelProperty(value = "位置说明")
    private String locationDescription;

    @Excel(name = "Address", orderNum = "6")
    @ApiModelProperty(value = "详细地址")
    private String address;

    @Excel(name = "blocklot", orderNum = "7")
    @ApiModelProperty(value = "块地段")
    private String blockLot;

    @Excel(name = "block", orderNum = "8")
    @ApiModelProperty(value = "块")
    private String block;

    @Excel(name = "lot", orderNum = "9")
    @ApiModelProperty(value = "地段")
    private String lot;

    @Excel(name = "permit", orderNum = "10")
    @ApiModelProperty(value = "许可证")
    private String permit;

    @Excel(name = "Status", orderNum = "11")
    @ApiModelProperty(value = "状态")
    private String status;

    @Excel(name = "FoodItems", orderNum = "12")
    @ApiModelProperty(value = "食物列表")
    private String foodItems;

    @Excel(name = "X", orderNum = "13")
    @ApiModelProperty(value = "坐标x")
    private String x;

    @Excel(name = "Y", orderNum = "14")
    @ApiModelProperty(value = "坐标y")
    private String y;

    @Excel(name = "Latitude", orderNum = "15")
    @ApiModelProperty(value = "维度")
    private String latitude;

    @Excel(name = "Longitude", orderNum = "16")
    @ApiModelProperty(value = "经度")
    private String longitude;

    @Excel(name = "Schedule", orderNum = "17")
    @ApiModelProperty(value = "附表")
    private String schedule;

    @Excel(name = "dayshours", orderNum = "18")
    @ApiModelProperty(value = "小时/天")
    private String daysHours;

    @Excel(name = "NOISent", orderNum = "19")
    @ApiModelProperty(value = "noi发送")
    private String noiSent;

    @Excel(name = "Approved", orderNum = "20")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "批准时间")
    private LocalDateTime approved;

    @Excel(name = "Received", orderNum = "21")
    @JsonFormat(pattern = "yyyyMMdd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "接收时间")
    private LocalDateTime received;

    @Excel(name = "PriorPermit", orderNum = "22")
    @ApiModelProperty(value = "事先许可")
    private String priorPermit;

    @Excel(name = "ExpirationDate", orderNum = "23")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expirationDate;

    @Excel(name = "Location", orderNum = "24")
    @ApiModelProperty(value = "位置")
    private String location;

    @Excel(name = "Fire Prevention Districts", orderNum = "25")
    @ApiModelProperty(value = "消防区数量")
    private Integer firePreventionDistricts;

    @Excel(name = "Police Districts", orderNum = "26")
    @ApiModelProperty(value = "警区数量")
    private Integer policeDistricts;

    @Excel(name = "Supervisor Districts", orderNum = "27")
    @ApiModelProperty(value = "监视区数量")
    private Integer supervisorDistricts;

    @Excel(name = "Zip Codes", orderNum = "28")
    @ApiModelProperty(value = "邮政编码")
    private String zipCodes;

    @Excel(name = "Neighborhoods (old)", orderNum = "29")
    @ApiModelProperty(value = "社区数量")
    private Integer neighborhoods;
}
