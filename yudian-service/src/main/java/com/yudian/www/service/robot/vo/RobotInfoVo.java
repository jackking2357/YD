package com.yudian.www.service.robot.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 机器人
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "机器人")
@ExcelIgnoreUnannotated
public class RobotInfoVo {

    /**
     * 机器人id
     */
    @ApiModelProperty(value = "机器人id", dataType = "Long")
    private Long robotId;

    /**
     * 机器人封面
     */
    @ApiModelProperty(value = "机器人封面", dataType = "String")
    @ExcelProperty(value = "机器人封面")
    private String robotPhoto;

    /**
     * 机器人描述
     */
    @ApiModelProperty(value = "机器人描述", dataType = "String")
    @ExcelProperty(value = "机器人描述")
    private String robotDesc;

    /**
     * 机器人价格
     */
    @ApiModelProperty(value = "机器人价格", dataType = "BigDecimal")
    @ExcelProperty(value = "机器人价格")
    private BigDecimal robotPrice;

    /**
     * 存在天数
     */
    @ApiModelProperty(value = "存在天数")
    private Integer existDay;
}