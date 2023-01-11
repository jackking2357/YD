package com.yudian.www.service.robot.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机器人加速器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "机器人加速器")
@ExcelIgnoreUnannotated
public class RobotAcceleratorInfoVo {

    /**
     * 机器人加速器id
     */
    @ApiModelProperty(value = "机器人加速器id", dataType="Long")
    private Long robotAcceleratorId;

    /**
     * 加速器封面
     */
    @ApiModelProperty(value = "加速器封面", dataType="String")
    @ExcelProperty(value = "加速器封面")
    private String acceleratorPhoto;

    /**
     * 加速器名称
     */
    @ApiModelProperty(value = "加速器名称", dataType="String")
    @ExcelProperty(value = "加速器名称")
    private String acceleratorName;

    /**
     * 加速器描述
     */
    @ApiModelProperty(value = "加速器描述", dataType="String")
    @ExcelProperty(value = "加速器描述")
    private String acceleratorDesc;

    /**
     * 加速器价格
     */
    @ApiModelProperty(value = "加速器价格", dataType="BigDecimal")
    @ExcelProperty(value = "加速器价格")
    private BigDecimal acceleratorPrice;

    /**
     * 加速比率
     */
    @ApiModelProperty(value = "加速比率", dataType="BigDecimal")
    @ExcelProperty(value = "加速比率")
    private BigDecimal acceleratorRate;

    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer showSort;
    @ApiModelProperty(value = "是否启用")
    @ExcelProperty(value = "是否启用")
    private Boolean isEnable;

    @ApiModelProperty(value = "存在天数")
    @ExcelProperty(value = "存在天数")
    private Integer existDay;
}