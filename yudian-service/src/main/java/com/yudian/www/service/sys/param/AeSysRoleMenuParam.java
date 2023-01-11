package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色和菜单关联表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "角色和菜单关联表")
@ExcelIgnoreUnannotated
public class AeSysRoleMenuParam {

    /**
     * 角色ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "角色ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入角色ID")
    private Long roleId;

    /**
     * 菜单ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "菜单ID", required = true, position = 1)
    @NotNull(message = "菜单ID参数必传")
    private Long menuId;

    public void initParam() {

    }
}