package com.yudian.www.entity.account;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台用户机器人加速器
 *
 * @author yudian
 * @since 2023-01-07
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
@TableName("`account_robot_accelerator`")
public class AccountRobotAccelerator extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    @TableId
    private Long accountRobotAcceleratorId;

    /**
     * 机器人加速器id
     * bigint(20) 20
     */
    private Long robotAcceleratorId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    private Long accountId;

    /**
     * 购买时间
     * datetime datetim
     */
    private LocalDateTime purchaseDatetime;

    /**
     * 过期时间
     * datetime datetim
     */
    private LocalDateTime expirationTime;

}