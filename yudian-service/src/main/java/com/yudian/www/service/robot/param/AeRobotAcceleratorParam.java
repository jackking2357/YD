package com.yudian.www.service.robot.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.List;

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
 * 机器人加速器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "机器人加速器")
@ExcelIgnoreUnannotated
public class AeRobotAcceleratorParam {

    /**
     * 机器人加速器id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人加速器id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入机器人加速器id")
    private Long robotAcceleratorId;

    /**
     * 加速器封面
     * varchar(512) 512
     */
    @ApiModelProperty(value = "加速器封面", required = true, position = 1)
    @NotNull(message = "加速器封面参数必传")
    @ExcelProperty(value = "加速器封面")
    private List<String> acceleratorPhoto;

    /**
     * 加速器名称
     * varchar(64) 64
     */
    @ApiModelProperty(value = "加速器名称", required = true, position = 2)
    @Length(max = 64, message = "加速器名称参数过长")
    @NotBlank(message = "加速器名称参数必传")
    @ExcelProperty(value = "加速器名称")
    private String acceleratorName;

    /**
     * 加速器描述
     * varchar(255) 255
     */
    @ApiModelProperty(value = "加速器描述", required = true, position = 3)
    @Length(max = 255, message = "加速器描述参数过长")
    @NotBlank(message = "加速器描述参数必传")
    @ExcelProperty(value = "加速器描述")
    private String acceleratorDesc;

    /**
     * 加速器价格
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "加速器价格", required = true, position = 4)
    @NotNull(message = "加速器价格参数必传")
    @ExcelProperty(value = "加速器价格")
    private BigDecimal acceleratorPrice;

    /**
     * 加速比率
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "加速比率", required = true, position = 5)
    @NotNull(message = "加速比率参数必传")
    @ExcelProperty(value = "加速比率")
    private BigDecimal acceleratorRate;

    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer showSort;

    @ApiModelProperty(value = "是否启用")
    @ExcelProperty(value = "是否启用")
    private Boolean isEnable;

    /**
     * 存在天数
     */
    private Integer existDay;

    public void initParam() {

    }
}