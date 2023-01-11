package com.yudian.www.entity.robot;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 机器人订单
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
@TableName("`robot_order`")
public class RobotOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 机器人订单id
     * bigint(20) 20
     */
    @TableId
    private Long robotOrderId;

    /**
     * 订单编码
     * varchar(48) 48
     */
    private String orderNo;

    /**
     * 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
     * int(1) 1
     */
    private Integer orderStatus;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    private Long accountId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    private Long robotId;

    /**
     * 机器人加速器id
     */
    private Long robotAcceleratorId;

    /**
     * 总价
     * decimal(10,2) 10,2
     */
    private BigDecimal price;

    /**
     * 平台收款钱包配置id
     * bigint(20) 20
     */
    private Long platformWalletId;

    /**
     * 取消时间
     * datetime datetim
     */
    private LocalDateTime cancelDatetime;

    /**
     * 取消原因
     * text tex
     */
    private String cancelRemark;

    /**
     * 支付金额
     * decimal(10,2) 10,2
     */
    private BigDecimal payMoney;

    /**
     * 支付时间
     * datetime datetim
     */
    private LocalDateTime payDatetime;

    /**
     * 支付单号
     * varchar(64) 64
     */
    private String payNumber;

    /**
     * 支付类型：1=微信；2=支付宝；3=银行卡
     * int(10) 10
     */
    private Integer payType;

    /**
     * 支付方式
     * char(10) 10
     */
    private String payMethod;

    /**
     * 支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；
     * int(10) 10
     */
    private Integer payStatus;

    /**
     * 已收金额
     */
    private BigDecimal receiveMoney;

    private Integer payCount;

}