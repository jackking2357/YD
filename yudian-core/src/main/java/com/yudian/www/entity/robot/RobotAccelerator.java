package com.yudian.www.entity.robot;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 机器人加速器
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
@TableName("`robot_accelerator`")
public class RobotAccelerator extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 机器人加速器id
     * bigint(20) 20
     */
    @TableId
    private Long robotAcceleratorId;

    /**
     * 加速器封面
     * varchar(512) 512
     */
    private String acceleratorPhoto;

    /**
     * 加速器名称
     * varchar(64) 64
     */
    private String acceleratorName;

    /**
     * 加速器描述
     * varchar(255) 255
     */
    private String acceleratorDesc;

    /**
     * 加速器价格
     * decimal(10,2) 10,2
     */
    private BigDecimal acceleratorPrice;

    /**
     * 加速比率
     * decimal(10,2) 10,2
     */
    private BigDecimal acceleratorRate;

    /**
     * 存在天数
     */
    private Integer existDay;

    private Integer showSort;

    private Boolean isEnable;
}