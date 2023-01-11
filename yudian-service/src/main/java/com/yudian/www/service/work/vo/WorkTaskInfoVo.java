package com.yudian.www.service.work.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 任务
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "任务")
@ExcelIgnoreUnannotated
public class WorkTaskInfoVo {

    /**
     * 工作任务记录id
     */
    @ApiModelProperty(value = "工作任务记录id", dataType = "Long")
    private Long workTaskId;

    /**
     * 任务封面
     */
    @ApiModelProperty(value = "任务封面")
    @ExcelProperty(value = "任务封面")
    private String taskIcon;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", dataType = "String")
    @ExcelProperty(value = "任务名称")
    private String taskName;

    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述", dataType = "String")
    @ExcelProperty(value = "任务描述")
    private String taskDesc;

    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer showSort;
    @ApiModelProperty(value = "是否启用")
    @ExcelProperty(value = "是否启用")
    private Boolean isEnable;
    @ApiModelProperty(value = "奖励收益")
    @ExcelProperty(value = "奖励收益")
    private BigDecimal awardIncome;
}