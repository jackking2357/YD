package com.yudian.www.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class BaseEntity {

    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.DEFAULT, updateStrategy = FieldStrategy.NEVER)
    @JsonIgnore
    @JSONField(serialize = false)
    private LocalDateTime createDatetime;

    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.DEFAULT, updateStrategy = FieldStrategy.NEVER)
    @JsonIgnore
    @JSONField(serialize = false)
    private LocalDateTime lastUpdateDatetime;

    
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    
    @TableField(fill = FieldFill.UPDATE)
    private Long lastUpdateBy;
}
