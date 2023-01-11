package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台用户机器人工作记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户机器人工作记录")
@ExcelIgnoreUnannotated
public class AccountRobotWorkInfoVo {

    /**
     * 平台用户机器人工作记录id
     */
    @ApiModelProperty(value = "平台用户机器人工作记录id", dataType="Long")
    private Long accountRobotWorkId;

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

    /**
     * 工作日期
     */
    @ApiModelProperty(value = "工作日期", dataType="LocalDate")
    @ExcelProperty(value = "工作日期")
    private LocalDate workDate;

    /**
     * 工作时间
     */
    @ApiModelProperty(value = "工作时间", dataType="LocalDateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "工作时间")
    private LocalDateTime workDatetime;

    /**
     * 总收益
     */
    @ApiModelProperty(value = "总收益", dataType="BigDecimal")
    @ExcelProperty(value = "总收益")
    private BigDecimal incomeSum;

    /**
     * 基础收益
     */
    @ApiModelProperty(value = "基础收益", dataType="BigDecimal")
    @ExcelProperty(value = "基础收益")
    private BigDecimal incomeBase;

    /**
     * 加速收益
     */
    @ApiModelProperty(value = "加速收益", dataType="BigDecimal")
    @ExcelProperty(value = "加速收益")
    private BigDecimal incomeAccelerator;

    /**
     * 加速比率
     */
    @ApiModelProperty(value = "加速比率", dataType="BigDecimal")
    @ExcelProperty(value = "加速比率")
    private BigDecimal acceleratorRate;

}