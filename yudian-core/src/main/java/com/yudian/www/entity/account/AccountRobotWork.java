package com.yudian.www.entity.account;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台用户机器人工作记录
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
@TableName("`account_robot_work`")
public class AccountRobotWork extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户机器人工作记录id
     * bigint(20) 20
     */
    @TableId
    private Long accountRobotWorkId;

    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    private Long accountRobotId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    private Long robotId;

    /**
     * 工作日期
     * date dat
     */
    private LocalDate workDate;

    /**
     * 工作时间
     * datetime datetim
     */
    private LocalDateTime workDatetime;

    /**
     * 总收益
     * decimal(10,2) 10,2
     */
    private BigDecimal incomeSum;

    /**
     * 基础收益
     * decimal(10,2) 10,2
     */
    private BigDecimal incomeBase;

    /**
     * 加速收益
     * decimal(10,2) 10,2
     */
    private BigDecimal incomeAccelerator;

    /**
     * 加速比率
     * decimal(10,2) 10,2
     */
    private BigDecimal acceleratorRate;

}