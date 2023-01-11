package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "菜单权限表")
@ExcelIgnoreUnannotated
public class SysMenuInfoVo {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @ExcelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    /**
     * 祖级列表
     */
    @ApiModelProperty(value = "祖级列表")
    @ExcelProperty(value = "祖级列表")
    private String ancestors;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer menuSort;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    @ExcelProperty(value = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径")
    @ExcelProperty(value = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @ApiModelProperty(value = "路由参数")
    @ExcelProperty(value = "路由参数")
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty(value = "是否为外链（0是 1否）")
    @ExcelProperty(value = "是否为外链（0是 1否）")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）")
    @ExcelProperty(value = "是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    @ExcelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    @ExcelProperty(value = "菜单状态（0正常 1停用）")
    private Integer menuStatus;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "显示状态（0显示 1隐藏）")
    @ExcelProperty(value = "显示状态（0显示 1隐藏）")
    private Integer visible;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    @ExcelProperty(value = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    @ExcelProperty(value = "菜单图标")
    private String icon;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

}