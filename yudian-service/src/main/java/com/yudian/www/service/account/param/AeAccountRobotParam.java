package com.yudian.www.service.account.param;

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
 * 平台用户机器人
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户机器人")
@ExcelIgnoreUnannotated
public class AeAccountRobotParam {

    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户机器人id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户机器人id")
    private Long accountRobotId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人id", required = true, position = 1)
    @NotNull(message = "机器人id参数必传")
    private Long robotId;

    /**
     * 购买时间
     * datetime datetim
     */
    @ApiModelProperty(value = "购买时间", required = true, position = 2)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "购买时间参数必传")
    @ExcelProperty(value = "购买时间")
    private LocalDateTime purchaseDatetime;

    /**
     * 累计收益
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "累计收益", required = true, position = 3)
    @NotNull(message = "累计收益参数必传")
    @ExcelProperty(value = "累计收益")
    private BigDecimal cumulativeIncome;

    public void initParam() {

    }
}