package com.yudian.www.entity.account;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台用户提取记录
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
@TableName("`account_extract`")
public class AccountExtract extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户提取记录id
     * bigint(20) 20
     */
    @TableId
    private Long accountExtractId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    private Long accountId;

    /**
     * 提取金额
     * decimal(10,2) 10,2
     */
    private BigDecimal extractAmount;

    /**
     * 审核状态：0=待审核；1=已通过；2=拒绝
     * int(1) 1
     */
    private Integer reviewStatus;

    /**
     * 审核备注
     * varchar(255) 255
     */
    private String reviewRemark;

    private BigDecimal oldScore;

    private BigDecimal newScore;


    private String targetZfbUsername;
    private String targetZfbQrCode;
    private BigDecimal sendMoney;
}