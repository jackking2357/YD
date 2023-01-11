package com.yudian.www.service.work.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 任务
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "任务")
@ExcelIgnoreUnannotated
public class AeWorkTaskParam {

    /**
     * 工作任务记录id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "工作任务记录id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入工作任务记录id")
    private Long workTaskId;

    /**
     * 任务封面
     * varchar(255) 255
     */
    @ApiModelProperty(value = "任务封面", required = true, position = 1)
    @NotNull(message = "任务封面参数必传")
    @ExcelProperty(value = "任务封面")
    private List<String> taskIcon;

    /**
     * 任务名称
     * varchar(32) 32
     */
    @ApiModelProperty(value = "任务名称", required = true, position = 2)
    @Length(max = 32, message = "任务名称参数过长")
    @NotBlank(message = "任务名称参数必传")
    @ExcelProperty(value = "任务名称")
    private String taskName;

    /**
     * 任务描述
     * varchar(255) 255
     */
    @ApiModelProperty(value = "任务描述", required = true, position = 3)
    @Length(max = 255, message = "任务描述参数过长")
    @NotBlank(message = "任务描述参数必传")
    @ExcelProperty(value = "任务描述")
    private String taskDesc;

    @ApiModelProperty(value = "显示顺序", required = true, position = 3)
    private Integer showSort;

    @ApiModelProperty(value = "是否启用", required = true, position = 3)
    private Boolean isEnable;

    @ApiModelProperty(value = "奖励收益", required = true, position = 3)
    private BigDecimal awardIncome;


    public void initParam() {

    }
}