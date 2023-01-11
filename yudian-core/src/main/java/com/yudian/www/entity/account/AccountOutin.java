package com.yudian.www.entity.account;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台用户流水
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
@TableName("`account_outin`")
public class AccountOutin extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户流水记录id
     * bigint(20) 20
     */
    @TableId
    private Long accountOutinId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    private Long accountId;

    /**
     * 流水类目
     * varchar(32) 32
     */
    private String outinName;

    /**
     * 流水金额
     * decimal(10,2) 10,2
     */
    private BigDecimal outinAmount;

    /**
     * 流水状态：0=无，1=审核中，2=已通过
     * int(1) 1
     */
    private Integer outinStatus;

    /**
     * 表
     */
    private String outinTable;

    /**
     * 表id，逗号间隔
     */
    private String outinTableId;


    private LocalDate outinDate;

}