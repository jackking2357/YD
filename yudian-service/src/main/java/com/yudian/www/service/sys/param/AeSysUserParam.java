package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.yudian.www.base.AddDomain;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@Data
@ApiModel(description = "用户信息表")
@ExcelIgnoreUnannotated
@ColumnWidth(30)
public class AeSysUserParam {


    @ApiModelProperty(value = "账户ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入账户ID")
    private Long userId;


    @ApiModelProperty(value = "账户昵称", required = true, position = 1)
    @Length(max = 32, message = "账户昵称参数过长")
    @NotBlank(message = "账户昵称参数必传")
    @ExcelProperty(value = "账户昵称")
    private String userNickname;


    @ApiModelProperty(value = "账号", required = true, position = 2)
    @Length(max = 32, message = "账号参数过长")
    @NotBlank(groups = {AddDomain.class}, message = "账号参数必传")
    @ExcelProperty(value = "账号")
    private String userName;


    @ApiModelProperty(value = "登录密码", required = true, position = 3)
    @Length(max = 128, message = "登录密码参数过长")
    @NotBlank(groups = {AddDomain.class}, message = "登录密码参数必传")
    @ExcelProperty(value = "登录密码")
    private String password;




//     */
//    @ApiModelProperty(value = "账户类型；1=系统账号；2=商家管理", required = true, position = 4)
//    @NotNull(message = "账户类型；1=系统账号；2=商家管理参数必传")
//    @Range(min = 1, max = 2, message = "账户类型错误")
//    @ExcelProperty(value = "账户类型；1=系统账号；2=商家管理")
//    private Integer userType;
//
//    /**
//     * 账户地址
//     * varchar(255) 255
//     */
//    @ApiModelProperty(value = "账户地址", required = false, position = 5)
//    @Length(max = 255, message = "账户地址参数过长")
////    @NotBlank(message = "账户邮箱参数必传")
//    @ExcelProperty(value = "账户地址")
//    private String userAddress;
//
//    /**
//     * 账户邮箱
//     * varchar(48) 48
//     */
//    @ApiModelProperty(value = "账户邮箱", required = false, position = 5)
//    @Length(max = 48, message = "账户邮箱参数过长")
////    @NotBlank(message = "账户邮箱参数必传")
//    @ExcelProperty(value = "账户邮箱")
//    private String userEmail;
//
//    /**
//     * 账户电话
//     * varchar(24) 24
//     */
//    @ApiModelProperty(value = "账户电话", required = false, position = 6)
//    @Length(max = 24, message = "账户电话参数过长")
////    @NotBlank(message = "账户电话参数必传")
//    @ExcelProperty(value = "账户电话")
//    private String userTel;

//    @ApiModelProperty(value = "手机号", required = false, position = 6)
//    @Length(max = 24, message = "手机号参数过长")
////    @NotBlank(message = "手机号参数必传")
//    @ExcelProperty(value = "手机号")
//    private String userPhone;

    /**
     * 账户头像地址
     * varchar(128) 128
     */
    @ApiModelProperty(value = "账户头像地址", required = false, position = 7)
    @Length(max = 128, message = "账户头像地址参数过长")
//    @NotBlank(message = "账户头像地址参数必传")
    @ExcelProperty(value = "账户头像地址")
    private String userAvatar;

    /**
     * 帐号状态：1=正常；0=冻结
     * bit(1) 1
     */
    @ApiModelProperty(value = "帐号状态：1=正常；0=冻结", required = true, position = 8)
    @NotNull(message = "帐号状态：1=正常；0=冻结参数必传")
    @ExcelProperty(value = "帐号状态：1=正常；0=冻结")
    private Boolean userStatus;

    /**
     * 备注
     * varchar(128) 128
     */
    @ApiModelProperty(value = "备注", required = false, position = 11)
    @Length(max = 128, message = "备注参数过长")
//    @NotBlank(message = "备注参数必传")
    @ExcelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "角色id列表", required = true, position = 12)
    @NotNull(message = "角色id列表参数必传")
    private Set<Long> roleIds;

    @ApiModelProperty(value = "部门Id", required = false, position = 13)
    private Long deptId;
    @ApiModelProperty(value = "岗位ids", required = false, position = 14)
    private List<Long> postIds;

    @ApiModelProperty(value = "性别：0=未知；1=男；2=女", required = false, position = 15)
    @ExcelProperty(value = "性别：0=未知；1=男；2=女")
    private Integer userSex;

    @ApiModelProperty(value = "企业id", required = false, position = 15)
    private Long enterpriseId;

    public void initParam() {

    }
}