package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.ListConverter;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@ApiModel(description = "用户信息表")
@ExcelIgnoreUnannotated
public class SysUserInfoVo {

    /**
     * 账户ID
     */
    @ApiModelProperty(value = "账户ID")
    private Long userId;

    /**
     * 账户昵称
     */
    @ApiModelProperty(value = "账户昵称")
    @ExcelProperty(value = "账户昵称")
    private String userNickname;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value = "账号")
    private String userName;

//    /**
//     * 账户密码
//     */
//    @ApiModelProperty(value = "账户密码")
//    @ExcelProperty(value = "账户密码")
//    private String password;
//
//    /**
//     * 账户类型；1=系统账号；2=商家管理
//     */
//    @ApiModelProperty(value = "账户类型；1=系统账号；2=商家管理")
//    @ExcelProperty(value = "账户类型；1=系统账号；2=商家管理")
//    private Integer userType;
//
//    /**
//     * 账户地址
//     * varchar(255) 255
//     */
//    @ApiModelProperty(value = "账户地址")
//    @ExcelProperty(value = "账户地址")
//    private String userAddress;
//
//    /**
//     * 账户邮箱
//     */
//    @ApiModelProperty(value = "账户邮箱")
//    @ExcelProperty(value = "账户邮箱")
//    private String userEmail;
//
//    /**
//     * 账户电话
//     */
//    @ApiModelProperty(value = "账户电话")
//    @ExcelProperty(value = "账户电话")
//    private String userTel;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "手机号")
    @ExcelProperty(value = "手机号")
    private String userPhone;

    /**
     * 账户头像地址
     */
    @ApiModelProperty(value = "账户头像地址")
    @ExcelProperty(value = "账户头像地址")
    private String userAvatar;

    /**
     * 帐号状态：1=正常；0=冻结
     */
    @ApiModelProperty(value = "帐号状态：1=正常；0=冻结")
    @ExcelProperty(value = "帐号状态：1=正常；0=冻结")
    private Boolean userStatus;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP")
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最后登录时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime loginDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最后更新时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime lastUpdateDatetime;

    @ApiModelProperty(value = "角色id列表")
    private Set<Long> roleIds;
    @ApiModelProperty(value = "角色名称列表")
    @ExcelProperty(value = "角色", converter = ListConverter.class)
    private List<String> roleNames;

    @ApiModelProperty(value = "部门id")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "岗位id列表")
    private Set<Long> postIds;
    @ApiModelProperty(value = "岗位名称列表")
    @ExcelProperty(value = "岗位", converter = ListConverter.class)
    private List<String> postNames;

    @ApiModelProperty(value = "公寓地址")
    private String apartmentAddress;

    @ApiModelProperty(value = "性别：0=未知；1=男；2=女")
    private Integer userSex;

    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;
}