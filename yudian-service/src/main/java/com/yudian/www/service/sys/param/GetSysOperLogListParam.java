package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "获取操作日志记录列表参数")
public class GetSysOperLogListParam extends BaseParam {

    @ApiModelProperty(value = "系统模块", required = false, position = 0)
    private String title;

    @ApiModelProperty(value = "操作人员", required = false, position = 0)
    private String operName;

    @ApiModelProperty(value = "操作类型", required = false, position = 0)
    private Integer businessType;

    @ApiModelProperty(value = "操作状态", required = false, position = 0)
    private Integer status;
}