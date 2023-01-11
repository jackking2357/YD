package com.yudian.www.service.robot.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.yudian.www.base.EditDomain;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机器人订单
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "机器人订单")
@ExcelIgnoreUnannotated
public class AeRobotOrderParam {

    /**
     * 机器人订单id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人订单id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入机器人订单id")
    private Long robotOrderId;

    /**
     * 订单编码
     * varchar(48) 48
     */
    @ApiModelProperty(value = "订单编码", required = true, position = 1)
    @Length(max = 48, message = "订单编码参数过长")
    @NotBlank(message = "订单编码参数必传")
    @ExcelProperty(value = "订单编码")
    private String orderNo;

    /**
     * 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
     * int(1) 1
     */
    @ApiModelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款", required = true, position = 2)
    @NotNull(message = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款参数必传")
    @ExcelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款")
    private Integer orderStatus;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户id", required = true, position = 3)
    @NotNull(message = "平台用户id参数必传")
    private Long accountId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人id", required = true, position = 4)
    @NotNull(message = "机器人id参数必传")
    private Long robotId;

    @ApiModelProperty(value = "机器人加速器id", required = true, position = 4)
    @NotNull(message = "机器人加速器id参数必传")
    private Long robotAcceleratorId;

    /**
     * 总价
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "总价", required = true, position = 5)
    @NotNull(message = "总价参数必传")
    @ExcelProperty(value = "总价")
    private BigDecimal price;

    /**
     * 平台收款钱包配置id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台收款钱包配置id", required = true, position = 6)
    @NotNull(message = "平台收款钱包配置id参数必传")
    private Long platformWalletId;

    /**
     * 取消时间
     * datetime datetim
     */
    @ApiModelProperty(value = "取消时间", required = true, position = 7)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "取消时间参数必传")
    @ExcelProperty(value = "取消时间")
    private LocalDateTime cancelDatetime;

    /**
     * 取消原因
     * text tex
     */
    @ApiModelProperty(value = "取消原因", required = true, position = 8)
    @NotBlank(message = "取消原因参数必传")
    @ExcelProperty(value = "取消原因")
    private String cancelRemark;

    /**
     * 支付金额
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "支付金额", required = true, position = 9)
    @NotNull(message = "支付金额参数必传")
    @ExcelProperty(value = "支付金额")
    private BigDecimal payMoney;

    /**
     * 支付时间
     * datetime datetim
     */
    @ApiModelProperty(value = "支付时间", required = true, position = 10)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "支付时间参数必传")
    @ExcelProperty(value = "支付时间")
    private LocalDateTime payDatetime;

    /**
     * 支付单号
     * varchar(64) 64
     */
    @ApiModelProperty(value = "支付单号", required = true, position = 11)
    @Length(max = 64, message = "支付单号参数过长")
    @NotBlank(message = "支付单号参数必传")
    @ExcelProperty(value = "支付单号")
    private String payNumber;

    /**
     * 支付类型：1=微信；2=支付宝；3=银行卡
     * int(10) 10
     */
    @ApiModelProperty(value = "支付类型：1=微信；2=支付宝；3=银行卡", required = true, position = 12)
    @NotNull(message = "支付类型：1=微信；2=支付宝；3=银行卡参数必传")
    @ExcelProperty(value = "支付类型：1=微信；2=支付宝；3=银行卡")
    private Integer payType;

    /**
     * 支付方式
     * char(10) 10
     */
    @ApiModelProperty(value = "支付方式", required = true, position = 13)
    @Length(max = 10, message = "支付方式参数过长")
    @NotBlank(message = "支付方式参数必传")
    @ExcelProperty(value = "支付方式")
    private String payMethod;

    /**
     * 支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；
     * int(10) 10
     */
    @ApiModelProperty(value = "支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；", required = true, position = 14)
    @NotNull(message = "支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；参数必传")
    @ExcelProperty(value = "支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；")
    private Integer payStatus;

    private Integer payCount;

    public void initParam() {

    }
}