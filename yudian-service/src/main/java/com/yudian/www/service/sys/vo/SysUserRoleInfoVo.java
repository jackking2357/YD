package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户和角色关联表")
@ExcelIgnoreUnannotated
public class SysUserRoleInfoVo {


    @ApiModelProperty(value = "用户ID")
    private Long userId;


    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}