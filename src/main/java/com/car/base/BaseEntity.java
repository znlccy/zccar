package com.car.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:54
 * @FileName: BaseEntity.java
 * @Desc    : 基础实体类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "基础实体类", description = "")
public class BaseEntity implements Serializable {

    // 序列化
    private static final long serialVersionUID = 7707330221669575364L;

    // ID (使用MybatisPlus自动生成主键雪花算法)
    @ApiModelProperty(value = "ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 逻辑删除
    @ApiModelProperty(value = "逻辑删除(0:未删除 1:已删除)")
    @TableLogic
    private Integer isDeleted;

    // 创建时间(插入时自动更新)
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间(插入和更新时自动更新)
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
