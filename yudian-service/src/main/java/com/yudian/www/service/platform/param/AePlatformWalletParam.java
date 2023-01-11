package com.yudian.www.service.platform.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.yudian.www.base.EditDomain;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 平台收款钱包
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "平台收款钱包")
@ExcelIgnoreUnannotated
public class AePlatformWalletParam {

    /**
     * 平台收款钱包配置id
     * int(11) 11
     */
    @ApiModelProperty(value = "平台收款钱包配置id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台收款钱包配置id")
    private Long platformWalletId;

    /**
     * 收款码
     * varchar(255) 255
     */
    @ApiModelProperty(value = "收款码", required = true, position = 1)
    @NotNull(message = "收款码参数必传")
    @ExcelProperty(value = "收款码")
    private List<String> qrCodePhoto;

    /**
     * 收款账户
     * varchar(128) 128
     */
    @ApiModelProperty(value = "收款账户", required = false, position = 2)
    @Length(max = 128, message = "收款账户参数过长")
//    @NotBlank(message = "收款账户参数必传")
    @ExcelProperty(value = "收款账户")
    private String qrAccount;

    /**
     * 类型：1=微信；2=支付宝；3=银行卡
     * int(1) 1
     */
    @ApiModelProperty(value = "类型：1=微信；2=支付宝；3=银行卡", required = true, position = 3)
    @NotNull(message = "类型：1=微信；2=支付宝；3=银行卡参数必传")
    @ExcelProperty(value = "类型：1=微信；2=支付宝；3=银行卡")
    private Integer qrType;

    /**
     * 是否启用
     * bit(1) 1
     */
    @ApiModelProperty(value = "是否启用", required = true, position = 4)
    @NotNull(message = "是否启用参数必传")
    @ExcelProperty(value = "是否启用")
    private Boolean isEnable;

    public void initParam() {

    }
}