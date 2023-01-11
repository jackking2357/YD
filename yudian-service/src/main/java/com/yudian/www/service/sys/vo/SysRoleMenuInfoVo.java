package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "角色和菜单关联表")
@ExcelIgnoreUnannotated
public class SysRoleMenuInfoVo {


    @ApiModelProperty(value = "角色ID")
    private Long roleId;


    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}