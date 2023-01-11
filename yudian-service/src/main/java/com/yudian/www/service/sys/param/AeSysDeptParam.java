package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门表
 *

 * @since 2022-02-12
 */
@Data
@ApiModel(description = "部门表")
@ExcelIgnoreUnannotated
public class AeSysDeptParam {

    /**
     * 部门id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "部门id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入部门id")
    private Long deptId;

    /**
     * 父部门id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "父部门id", required = false, position = 1)
//    @NotNull(message = "父部门id参数必传")
    private Long parentId;

    /**
     * 祖级列表
     * varchar(50) 50
     */
    @ApiModelProperty(value = "祖级列表", required = false, position = 2, hidden = true)
//    @Length(max = 50, message = "祖级列表参数过长")
//    @NotBlank(message = "祖级列表参数必传")
//    @ExcelProperty(value = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     * varchar(32) 32
     */
    @ApiModelProperty(value = "部门名称", required = true, position = 3)
    @Length(max = 32, message = "部门名称参数过长")
    @NotBlank(message = "部门名称参数必传")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 显示顺序
     * int(4) 4
     */
    @ApiModelProperty(value = "显示顺序", required = true, position = 4)
    @NotNull(message = "显示顺序参数必传")
    @ExcelProperty(value = "显示顺序")
    private Integer deptSort;

    /**
     * 负责人
     * varchar(20) 20
     */
    @ApiModelProperty(value = "负责人", required = false, position = 5)
    @Length(max = 20, message = "负责人参数过长")
//    @NotBlank(message = "负责人参数必传")
    @ExcelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     * varchar(11) 11
     */
    @ApiModelProperty(value = "联系电话", required = false, position = 6)
    @Length(max = 24, message = "联系电话参数过长")
//    @NotBlank(message = "联系电话参数必传")
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 部门状态：1=正常；0=停用；
     * bit(1) 1
     */
    @ApiModelProperty(value = "部门状态：1=正常；0=停用；", required = true, position = 7)
    @NotNull(message = "部门状态：1=正常；0=停用；参数必传")
    @ExcelProperty(value = "部门状态：1=正常；0=停用；")
    private Boolean deptStatus;

    public void initParam() {
        if (null == this.parentId) {
            this.parentId = 0L;
        }
    }
}