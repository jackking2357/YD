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
import java.util.Set;

/**
 * 角色信息表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "角色信息表")
@ExcelIgnoreUnannotated
public class AeSysRoleParam {

    /**
     * 角色ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "角色ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入角色ID")
    private Long roleId;

    /**
     * 角色名称
     * varchar(24) 24
     */
    @ApiModelProperty(value = "角色名称", required = true, position = 1)
    @Length(max = 24, message = "角色名称参数过长")
    @NotBlank(message = "角色名称参数必传")
    @ExcelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色权限字符串
     * varchar(128) 128
     */
    @ApiModelProperty(value = "角色权限字符串", required = true, position = 2)
    @Length(max = 128, message = "角色权限字符串参数过长")
    @NotBlank(message = "角色权限字符串参数必传")
    @ExcelProperty(value = "角色权限字符串")
    private String roleKey;

    /**
     * 显示顺序
     * int(4) 4
     */
    @ApiModelProperty(value = "显示顺序", required = true, position = 3)
    @NotNull(message = "显示顺序参数必传")
    @ExcelProperty(value = "显示顺序")
    private Integer roleSort;

    /**
     * 菜单树选择项是否关联显示
     * tinyint(1) 1
     */
    @ApiModelProperty(value = "菜单树选择项是否关联显示", required = true, position = 5)
    @NotNull(message = "菜单树选择项是否关联显示参数必传")
    @ExcelProperty(value = "菜单树选择项是否关联显示")
    private Boolean menuCheckStrictly;

    /**
     * 角色状态：1=正常；0=停用
     * bit(1) 1
     */
    @ApiModelProperty(value = "角色状态：1=正常；0=停用", required = true, position = 7)
    @NotNull(message = "角色状态：1=正常；0=停用参数必传")
    @ExcelProperty(value = "角色状态：1=正常；0=停用")
    private Boolean roleStatus;

    /**
     * 备注
     * varchar(128) 128
     */
    @ApiModelProperty(value = "备注", required = false, position = 8)
    @Length(max = 128, message = "备注参数过长")
    @ExcelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "菜单ids", required = true, position = 5)
    @NotNull(message = "菜单id参数必传")
    private Set<Long> menuIds;

    public void initParam() {

    }
}