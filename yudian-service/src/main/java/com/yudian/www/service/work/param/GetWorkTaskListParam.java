package com.yudian.www.service.work.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.BaseParam;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取任务列表参数")
public class GetWorkTaskListParam extends BaseParam {

    @ApiModelProperty(value = "任务id列表", required = false, position = 1)
    private Set<Long> workTaskIds;

    @ApiModelProperty(value = "是否启用")
    private Boolean isEnable;
}