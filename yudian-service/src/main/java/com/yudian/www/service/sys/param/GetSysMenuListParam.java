package com.yudian.www.service.sys.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单权限表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "获取菜单权限表列表参数")
public class GetSysMenuListParam {

    @ApiModelProperty(value = "菜单名称", example = "10", required = false, position = 1)
    private String menuName;

    @ApiModelProperty(value = "菜单状态", example = "10", required = false, position = 2)
    private Boolean menuStatus;
}