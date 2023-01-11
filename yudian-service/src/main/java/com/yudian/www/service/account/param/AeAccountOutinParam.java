package com.yudian.www.service.account.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
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
 * 平台用户流水
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户流水")
@ExcelIgnoreUnannotated
public class AeAccountOutinParam {

    /**
     * 平台用户流水记录id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户流水记录id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户流水记录id")
    private Long accountOutinId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户id", required = true, position = 1)
    @NotNull(message = "平台用户id参数必传")
    private Long accountId;

    /**
     * 流水类目
     * varchar(32) 32
     */
    @ApiModelProperty(value = "流水类目", required = true, position = 2)
    @Length(max = 32, message = "流水类目参数过长")
    @NotBlank(message = "流水类目参数必传")
    @ExcelProperty(value = "流水类目")
    private String outinName;

    /**
     * 流水金额
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "流水金额", required = true, position = 3)
    @NotNull(message = "流水金额参数必传")
    @ExcelProperty(value = "流水金额")
    private BigDecimal outinAmount;

    /**
     * 流水状态：0=无，1=审核中，2=已通过
     * int(1) 1
     */
    @ApiModelProperty(value = "流水状态：0=无，1=审核中，2=已通过", required = true, position = 4)
    @NotNull(message = "流水状态：0=无，1=审核中，2=已通过参数必传")
    @ExcelProperty(value = "流水状态：0=无，1=审核中，2=已通过")
    private Integer outinStatus;

    public void initParam() {

    }
}