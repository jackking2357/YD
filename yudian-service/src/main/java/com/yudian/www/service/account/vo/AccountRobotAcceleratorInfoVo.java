package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
public class AccountRobotAcceleratorInfoVo {

    /**
     * 平台用户机器人id
     */
    @ApiModelProperty(value = "平台用户机器人id", dataType="Long")
    private Long accountRobotAcceleratorId;

    /**
     * 机器人加速器id
     */
    @ApiModelProperty(value = "机器人加速器id", dataType="Long")
    private Long robotAcceleratorId;

    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType="Long")
    private Long accountId;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间", dataType="LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "购买时间")
    private LocalDateTime purchaseDatetime;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间", dataType="LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "过期时间")
    private LocalDateTime expirationTime;

}