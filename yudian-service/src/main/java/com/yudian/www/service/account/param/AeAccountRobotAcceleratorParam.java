package com.yudian.www.service.account.param;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 平台用户机器人加速器
 *
 * @author yudian
 * @since 2023-01-07
 */
@Data
@ApiModel(description = "平台用户机器人加速器")
@ExcelIgnoreUnannotated
public class AeAccountRobotAcceleratorParam {

    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户机器人id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户机器人id")
    private Long accountRobotAcceleratorId;

    /**
     * 机器人加速器id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人加速器id", required = true, position = 1)
    @NotNull(message = "机器人加速器id参数必传")
    private Long robotAcceleratorId;

    /**
     * 平台用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户id", required = true, position = 2)
    @NotNull(message = "平台用户id参数必传")
    private Long accountId;

    /**
     * 购买时间
     * datetime datetim
     */
    @ApiModelProperty(value = "购买时间", required = true, position = 3)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "购买时间参数必传")
    @ExcelProperty(value = "购买时间")
    private LocalDateTime purchaseDatetime;

    /**
     * 过期时间
     * datetime datetim
     */
    @ApiModelProperty(value = "过期时间", required = true, position = 4)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "过期时间参数必传")
    @ExcelProperty(value = "过期时间")
    private LocalDateTime expirationTime;

    public void initParam() {

    }
}