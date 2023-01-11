package com.yudian.www.entity.account;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 平台用户机器人
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
@TableName("`account_robot`")
public class AccountRobot extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    @TableId
    private Long accountRobotId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    private Long robotId;

    /**
     * 用户id
     * bigint(20) 20
     */
    private Long accountId;

    /**
     * 购买时间
     * datetime datetim
     */
    private LocalDateTime purchaseDatetime;

    /**
     * 累计收益
     * decimal(10,2) 10,2
     */
    private BigDecimal cumulativeIncome;

    /**
     * 过期时间
     * datetime datetim
     */
    private LocalDateTime expirationTime;

}