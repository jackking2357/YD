package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色信息表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "获取角色信息表列表参数")
public class GetSysRoleListParam extends BaseParam {

    @ApiModelProperty(value = "权限字符", example = "10", required = false, position = 1)
    private String roleKey;

    @ApiModelProperty(value = "角色名称", example = "10", required = false, position = 2)
    private String roleName;

    @ApiModelProperty(value = "角色状态", example = "10", required = false, position = 2)
    private Boolean roleStatus;
}