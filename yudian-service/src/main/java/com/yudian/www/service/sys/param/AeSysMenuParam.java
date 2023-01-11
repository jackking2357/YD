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
 * 菜单权限表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "菜单权限表")
@ExcelIgnoreUnannotated
public class AeSysMenuParam {

    /**
     * 菜单ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "菜单ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     * varchar(50) 50
     */
    @ApiModelProperty(value = "菜单名称", required = true, position = 1)
    @Length(max = 50, message = "菜单名称参数过长")
    @NotBlank(message = "菜单名称参数必传")
    @ExcelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 父菜单ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "父菜单ID", required = true, position = 2)
    @NotNull(message = "父菜单ID参数必传")
    private Long parentId;

//    /**
//     * 祖级列表
//     * varchar(50) 50
//     */
//    @ApiModelProperty(value = "祖级列表", required = true, position = 3)
//    @Length(max = 50, message = "祖级列表参数过长")
//    @NotBlank(message = "祖级列表参数必传")
//    @ExcelProperty(value = "祖级列表")
//    private String ancestors;

    /**
     * 显示顺序
     * int(4) 4
     */
    @ApiModelProperty(value = "显示顺序", required = true, position = 4)
    @NotNull(message = "显示顺序参数必传")
    @ExcelProperty(value = "显示顺序")
    private Integer menuSort;

    /**
     * 路由地址
     * varchar(200) 200
     */
    @ApiModelProperty(value = "路由地址", required = false, position = 5)
    @Length(max = 200, message = "路由地址参数过长")
//    @NotBlank(message = "路由地址参数必传")
    @ExcelProperty(value = "路由地址")
    private String path;

    /**
     * 组件路径
     * varchar(255) 255
     */
    @ApiModelProperty(value = "组件路径", required = false, position = 6)
    @Length(max = 255, message = "组件路径参数过长")
//    @NotBlank(message = "组件路径参数必传")
    @ExcelProperty(value = "组件路径")
    private String component;

    /**
     * 路由参数
     * varchar(255) 255
     */
    @ApiModelProperty(value = "路由参数", required = false, position = 7)
    @Length(max = 255, message = "路由参数参数过长")
//    @NotBlank(message = "路由参数参数必传")
    @ExcelProperty(value = "路由参数")
    private String query;

    /**
     * 是否为外链（0是 1否）
     * int(1) 1
     */
    @ApiModelProperty(value = "是否为外链（0是 1否）", required = false, position = 8)
//    @NotNull(message = "是否为外链（0是 1否）参数必传")
    @ExcelProperty(value = "是否为外链（0是 1否）")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     * int(1) 1
     */
    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）", required = false, position = 9)
//    @NotNull(message = "是否缓存（0缓存 1不缓存）参数必传")
    @ExcelProperty(value = "是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     * char(1) 1
     */
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）", required = true, position = 10)
    @Length(max = 1, message = "菜单类型（M目录 C菜单 F按钮）参数过长")
    @NotBlank(message = "菜单类型（M目录 C菜单 F按钮）参数必传")
    @ExcelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 菜单状态（0正常 1停用）
     * int(1) 1
     */
    @ApiModelProperty(value = "菜单状态（0正常 1停用）", required = true, position = 11)
    @NotNull(message = "菜单状态（0正常 1停用）参数必传")
    @ExcelProperty(value = "菜单状态（0正常 1停用）")
    private Integer menuStatus;

    /**
     * 显示状态（0显示 1隐藏）
     * int(1) 1
     */
    @ApiModelProperty(value = "显示状态（0显示 1隐藏）", required = true, position = 12)
    @NotNull(message = "显示状态（0显示 1隐藏）参数必传")
    @ExcelProperty(value = "显示状态（0显示 1隐藏）")
    private Integer visible;

    /**
     * 权限标识
     * varchar(100) 100
     */
    @ApiModelProperty(value = "权限标识", required = false, position = 13)
    @Length(max = 100, message = "权限标识参数过长")
//    @NotBlank(message = "权限标识参数必传")
    @ExcelProperty(value = "权限标识")
    private String perms;

    /**
     * 菜单图标
     * varchar(100) 100
     */
    @ApiModelProperty(value = "菜单图标", required = false, position = 14)
    @Length(max = 100, message = "菜单图标参数过长")
//    @NotBlank(message = "菜单图标参数必传")
    @ExcelProperty(value = "菜单图标")
    private String icon;

    /**
     * 备注
     * varchar(500) 500
     */
    @ApiModelProperty(value = "备注", required = false, position = 15)
    @Length(max = 500, message = "备注参数过长")
//    @NotBlank(message = "备注参数必传")
    @ExcelProperty(value = "备注")
    private String remark;

    public void initParam() {

    }
}