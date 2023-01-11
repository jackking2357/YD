package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 部门表
 *

 * @since 2022-02-12
 */
@Data
@ApiModel(description = "获取部门表列表参数")
public class GetSysDeptListParam extends BaseParam {

    @ApiModelProperty(value = "部门表id列表", required = false, position = 1)
    private Set<Long> deptIds;

    @ApiModelProperty(value = "部门名称", required = false, position = 1)
    private String deptName;

    @ApiModelProperty(value = "部门状态", required = false, position = 2)
    private Boolean deptStatus;

    @ApiModelProperty(value = "父部门id")
    private Long parentId;
}