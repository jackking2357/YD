package com.yudian.www.entity.robot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 机器人
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
@TableName("`robot`")
public class Robot extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 机器人id
     * bigint(20) 20
     */
    @TableId
    private Long robotId;

    /**
     * 机器人封面
     * varchar(512) 512
     */
    private String robotPhoto;

    /**
     * 机器人描述
     * varchar(1024) 1024
     */
    private String robotDesc;

    /**
     * 机器人价格
     * decimal(10,2) 10,2
     */
    private BigDecimal robotPrice;

    /**
     * 存在天数
     */
    private Integer existDay;
}