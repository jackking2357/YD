package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel(description = "角色信息表")
@ExcelIgnoreUnannotated
public class SysRoleInfoVo {


    @ApiModelProperty(value = "角色ID")
    private Long roleId;


    @ApiModelProperty(value = "角色名称")
    @ExcelProperty(value = "角色名称")
    private String roleName;


    @ApiModelProperty(value = "角色权限字符串")
    @ExcelProperty(value = "角色权限字符串")
    private String roleKey;


    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer roleSort;


    @ApiModelProperty(value = "数据范围：1=全部数据权限；2=商家数据权限；")
    @ExcelProperty(value = "数据范围：1=全部数据权限；2=商家数据权限；")
    private String dataScope;


    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    @ExcelProperty(value = "菜单树选择项是否关联显示")
    private Boolean menuCheckStrictly;


    @ApiModelProperty(value = "部门树选择项是否关联显示")
    @ExcelProperty(value = "部门树选择项是否关联显示")
    private Boolean deptCheckStrictly;


    @ApiModelProperty(value = "角色状态：1=正常；0=停用")
    @ExcelProperty(value = "角色状态：1=正常；0=停用")
    private Boolean roleStatus;


    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}