package com.yudian.www.service.platform.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台收款钱包
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "平台收款钱包")
@ExcelIgnoreUnannotated
public class PlatformWalletInfoVo {

    /**
     * 平台收款钱包配置id
     */
    @ApiModelProperty(value = "平台收款钱包配置id", dataType = "Integer")
    private Long platformWalletId;

    /**
     * 收款码
     */
    @ApiModelProperty(value = "收款码", dataType = "String")
    @ExcelProperty(value = "收款码")
    private String qrCodePhoto;

    /**
     * 收款账户
     */
    @ApiModelProperty(value = "收款账户", dataType = "String")
    @ExcelProperty(value = "收款账户")
    private String qrAccount;

    /**
     * 类型：1=微信；2=支付宝；3=银行卡
     */
    @ApiModelProperty(value = "类型：1=微信；2=支付宝；3=银行卡", dataType = "Integer")
    @ExcelProperty(value = "类型：1=微信；2=支付宝；3=银行卡")
    private Integer qrType;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用", dataType = "Boolean")
    @ExcelProperty(value = "是否启用")
    private Boolean isEnable;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "订单id")
    private Long robotOrderId;
}