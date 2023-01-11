package com.yudian.www.service.account.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 平台用户提取记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户提取记录")
@ExcelIgnoreUnannotated
public class AeAccountExtractParam {

    /**
     * 平台用户提取记录id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户提取记录id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户提取记录id")
    private Long accountExtractId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户id", required = true, hidden = true, position = 1)
//    @NotNull(message = "平台用户id参数必传")
    private Long accountId;

    /**
     * 提取金额
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "提取金额", required = true, position = 2)
    @NotNull(message = "提取金额参数必传")
    @ExcelProperty(value = "提取金额")
    @Min(value = 0, message = "最小为0")
    private BigDecimal extractAmount;

    /**
     * 审核状态：0=待审核；1=已通过；2=拒绝
     * int(1) 1
     */
    @ApiModelProperty(value = "审核状态：0=待审核；1=已通过；2=拒绝", required = false, hidden = true, position = 3)
    @ExcelProperty(value = "审核状态：0=待审核；1=已通过；2=拒绝")
    private Integer reviewStatus;

    /**
     * 审核备注
     * varchar(255) 255
     */
    @ApiModelProperty(value = "审核备注", required = false, hidden = true, position = 4)
    @Length(max = 255, message = "审核备注参数过长")
    @ExcelProperty(value = "审核备注")
    private String reviewRemark;

    private String targetZfbUsername;
    private String targetZfbQrCode;
    private BigDecimal sendMoney;

    public void initParam() {

    }
}