package com.yudian.www.service.robot.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.yudian.www.entity.account.Account;
import com.yudian.www.service.account.vo.AccountInfoVo;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 机器人订单
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "机器人订单")
@ExcelIgnoreUnannotated
public class RobotOrderInfoVo {

    /**
     * 机器人订单id
     */
    @ApiModelProperty(value = "机器人订单id", dataType = "Long")
    private Long robotOrderId;

    /**
     * 订单编码
     */
    @ApiModelProperty(value = "订单编码", dataType = "String")
    @ExcelProperty(value = "订单编码")
    private String orderNo;

    /**
     * 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
     */
    @ApiModelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款", dataType = "Integer")
    @ExcelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款")
    private Integer orderStatus;

    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType = "Long")
    private Long accountId;
    @ApiModelProperty(value = "平台用户明细")
    private AccountInfoVo accountInfoVo;

    /**
     * 机器人id
     */
    @ApiModelProperty(value = "机器人id", dataType = "Long")
    private Long robotId;

    /**
     * 机器人加速器id
     */
    @ApiModelProperty(value = "机器人加速器id", dataType = "Long")
    private Long robotAcceleratorId;

    /**
     * 总价
     */
    @ApiModelProperty(value = "总价", dataType = "BigDecimal")
    @ExcelProperty(value = "总价")
    private BigDecimal price;

    /**
     * 平台收款钱包配置id
     */
    @ApiModelProperty(value = "平台收款钱包配置id", dataType = "Long")
    private Long platformWalletId;

    /**
     * 取消时间
     */
    @ApiModelProperty(value = "取消时间", dataType = "LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "取消时间")
    private LocalDateTime cancelDatetime;

    /**
     * 取消原因
     */
    @ApiModelProperty(value = "取消原因", dataType = "String")
    @ExcelProperty(value = "取消原因")
    private String cancelRemark;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额", dataType = "BigDecimal")
    @ExcelProperty(value = "支付金额")
    private BigDecimal payMoney;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间", dataType = "LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间")
    private LocalDateTime payDatetime;

    /**
     * 支付单号
     */
    @ApiModelProperty(value = "支付单号", dataType = "String")
    @ExcelProperty(value = "支付单号")
    private String payNumber;

    /**
     * 支付类型：1=微信；2=支付宝；3=银行卡
     */
    @ApiModelProperty(value = "支付类型：1=微信；2=支付宝；3=银行卡", dataType = "Integer")
    @ExcelProperty(value = "支付类型：1=微信；2=支付宝；3=银行卡")
    private Integer payType;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式", dataType = "String")
    @ExcelProperty(value = "支付方式")
    private String payMethod;

    /**
     * 支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；
     */
    @ApiModelProperty(value = "支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；", dataType = "Integer")
    @ExcelProperty(value = "支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；")
    private Integer payStatus;

    private Integer payCount;

    @ApiModelProperty(value = "机器人明细")
    private RobotInfoVo robotInfoVo;
    @ApiModelProperty(value = "机器人加速器明细")
    private RobotAcceleratorInfoVo robotAcceleratorInfoVo;
    @ApiModelProperty(value = "平台收款钱")
    private PlatformWalletInfoVo platformWalletInfoVo;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}