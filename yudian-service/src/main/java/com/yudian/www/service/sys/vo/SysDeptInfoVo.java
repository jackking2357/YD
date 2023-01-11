package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "部门表")
@ExcelIgnoreUnannotated
public class SysDeptInfoVo {

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @ApiModelProperty(value = "祖级列表")
    @ExcelProperty(value = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer deptSort;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @ExcelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 部门状态：1=正常；0=停用；
     */
    @ApiModelProperty(value = "部门状态：1=正常；0=停用；")
    @ExcelProperty(value = "部门状态：1=正常；0=停用；")
    private Boolean deptStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;

    @ApiModelProperty(value = "部门下的用户信息")
    private List<SysUserInfoVo> sysUserInfoVos;
}