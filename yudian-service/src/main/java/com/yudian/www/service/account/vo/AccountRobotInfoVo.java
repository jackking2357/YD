package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.yudian.www.service.robot.vo.RobotInfoVo;
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
public class AccountRobotInfoVo {

    /**
     * 平台用户机器人id
     */
    @ApiModelProperty(value = "平台用户机器人id", dataType="Long")
    private Long accountRobotId;

    /**
     * 机器人id
     */
    @ApiModelProperty(value = "机器人id", dataType="Long")
    private Long robotId;

    @ApiModelProperty(value = "机器人信息")
    private RobotInfoVo robotInfoVo;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间", dataType="LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "购买时间")
    private LocalDateTime purchaseDatetime;

    /**
     * 累计收益
     */
    @ApiModelProperty(value = "累计收益", dataType="BigDecimal")
    @ExcelProperty(value = "累计收益")
    private BigDecimal cumulativeIncome;

    @ApiModelProperty(value = "过期时间", dataType="LocalDateTime")
    private LocalDateTime expirationTime;

}