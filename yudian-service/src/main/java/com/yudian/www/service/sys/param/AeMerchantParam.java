package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.yudian.common.utils.easyexcel.CustomBooleanConverter;
import com.yudian.www.base.AddDomain;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户信息表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "商家信息")
@ExcelIgnoreUnannotated
@ColumnWidth(30)
public class AeMerchantParam {

    /**
     * 商家ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "商家ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入商家ID")
    private Long userId;

    /**
     * 商家昵称
     * varchar(32) 32
     */
    @ApiModelProperty(value = "商家昵称", required = true, position = 1)
    @Length(max = 32, message = "商家昵称参数过长")
    @NotBlank(message = "商家昵称参数必传")
    @ExcelProperty(value = "*商家昵称")
    private String userNickname;

    /**
     * 账号
     * varchar(32) 32
     */
    @ApiModelProperty(value = "商家账号", required = true, position = 2)
    @Length(max = 32, message = "商家账号参数过长")
    @NotBlank(groups = {AddDomain.class}, message = "商家账号参数必传")
    @ExcelProperty(value = "*商家账号")
    private String userName;

    /**
     * 登录密码
     * varchar(128) 128
     */
    @ApiModelProperty(value = "登录密码", required = true, position = 3)
    @Length(max = 128, message = "登录密码参数过长")
    @NotBlank(groups = {AddDomain.class}, message = "登录密码参数必传")
    @ExcelProperty(value = "*登录密码")
    private String password;

    /**
     * 商家地址
     * varchar(255) 255
     */
    @ApiModelProperty(value = "商家地址", required = false, position = 5)
    @Length(max = 255, message = "商家地址参数过长")
    @ExcelProperty(value = "商家地址")
    private String userAddress;

    /**
     * 商家邮箱
     * varchar(48) 48
     */
    @ApiModelProperty(value = "商家邮箱", required = false, position = 5)
    @Length(max = 48, message = "商家邮箱参数过长")
    @ExcelProperty(value = "商家邮箱")
    private String userEmail;

    /**
     * 商家电话
     * varchar(24) 24
     */
    @ApiModelProperty(value = "商家电话", required = false, position = 6)
    @Length(max = 24, message = "商家电话参数过长")
    @ExcelProperty(value = "商家电话")
    private String userTel;

    /**
     * 商家头像地址
     * varchar(128) 128
     */
    @ApiModelProperty(value = "商家头像地址", required = false, position = 7)
    @Length(max = 128, message = "商家头像地址参数过长")
    private String userAvatar;

    /**
     * 帐号状态：1=正常；0=冻结
     * bit(1) 1
     */
    @ApiModelProperty(value = "帐号状态：1=正常；0=冻结", required = true, position = 8)
    @NotNull(message = "帐号状态：1=正常；0=冻结参数必传")
    @ExcelProperty(value = "*帐号状态：true=启用；false=停用", converter = CustomBooleanConverter.class)
    private Boolean userStatus;

    /**
     * 备注
     * varchar(128) 128
     */
    @ApiModelProperty(value = "备注", required = false, position = 11)
    @Length(max = 128, message = "备注参数过长")
//    @ExcelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色id列表", required = true, position = 12)
    @NotNull(message = "角色id列表参数必传")
    private Set<Long> roleIds;

    @ApiModelProperty(value = "excel导入字段-角色名称列表", hidden = true)
    @ExcelProperty(value = "角色名称")
    private String roleNames;

    public void initParam() {

    }
}