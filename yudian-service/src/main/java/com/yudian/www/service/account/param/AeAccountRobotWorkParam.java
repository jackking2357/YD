package com.yudian.www.service.account.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
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
 * 平台用户机器人工作记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户机器人工作记录")
@ExcelIgnoreUnannotated
public class AeAccountRobotWorkParam {

    /**
     * 平台用户机器人工作记录id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户机器人工作记录id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户机器人工作记录id")
    private Long accountRobotWorkId;

    /**
     * 平台用户机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户机器人id", required = true, position = 1)
    @NotNull(message = "平台用户机器人id参数必传")
    private Long accountRobotId;

    /**
     * 机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人id", required = true, position = 2)
    @NotNull(message = "机器人id参数必传")
    private Long robotId;

    /**
     * 工作日期
     * date dat
     */
    @ApiModelProperty(value = "工作日期", required = true, position = 3)
    @NotNull(message = "工作日期参数必传")
    @ExcelProperty(value = "工作日期")
    private LocalDate workDate;

    /**
     * 工作时间
     * datetime datetim
     */
    @ApiModelProperty(value = "工作时间", required = true, position = 4)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "工作时间参数必传")
    @ExcelProperty(value = "工作时间")
    private LocalDateTime workDatetime;

    /**
     * 总收益
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "总收益", required = true, position = 5)
    @NotNull(message = "总收益参数必传")
    @ExcelProperty(value = "总收益")
    private BigDecimal incomeSum;

    /**
     * 基础收益
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "基础收益", required = true, position = 6)
    @NotNull(message = "基础收益参数必传")
    @ExcelProperty(value = "基础收益")
    private BigDecimal incomeBase;

    /**
     * 加速收益
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "加速收益", required = true, position = 7)
    @NotNull(message = "加速收益参数必传")
    @ExcelProperty(value = "加速收益")
    private BigDecimal incomeAccelerator;

    /**
     * 加速比率
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "加速比率", required = true, position = 8)
    @NotNull(message = "加速比率参数必传")
    @ExcelProperty(value = "加速比率")
    private BigDecimal acceleratorRate;

    public void initParam() {

    }
}