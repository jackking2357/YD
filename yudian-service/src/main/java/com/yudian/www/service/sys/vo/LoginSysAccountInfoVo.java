package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "系统用户/员工")
public class LoginSysAccountInfoVo {

    @ApiModelProperty(value = "token")
    private String authToken;

    /**
     * 账户id
     */
    @ApiModelProperty(value = "账户id")
    private Long accountId;

    /**
     * 账户手机号码
     */
    @ApiModelProperty(value = "账户手机号码")
    @ExcelProperty(value = "账户手机号码")
    private String phoneNumber;

    /**
     * 微信用户唯一标识
     */
    @ApiModelProperty(value = "微信用户唯一标识")
    @ExcelProperty(value = "微信用户唯一标识")
    private String wxUnionid;

    /**
     * 微信公众号openid
     */
    @ApiModelProperty(value = "微信公众号openid")
    @ExcelProperty(value = "微信公众号openid")
    private String wxGzhOpenid;

    /**
     * 微信小程序openid
     */
    @ApiModelProperty(value = "微信小程序openid")
    @ExcelProperty(value = "微信小程序openid")
    private String wxAppletsOpenid;

    /**
     * 微信用户信息授权
     */
    @ApiModelProperty(value = "微信用户信息授权")
    @ExcelProperty(value = "微信用户信息授权")
    private Boolean isWxUserInfo;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @ExcelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 性别：0=未知；1=男；2=女；
     */
    @ApiModelProperty(value = "性别：0=未知；1=男；2=女；")
    @ExcelProperty(value = "性别：0=未知；1=男；2=女；")
    private Integer gender;

    /**
     * 所在国家
     */
    @ApiModelProperty(value = "所在国家")
    @ExcelProperty(value = "所在国家")
    private String country;

    /**
     * 所在省
     */
    @ApiModelProperty(value = "所在省")
    @ExcelProperty(value = "所在省")
    private String province;

    /**
     * 所在市
     */
    @ApiModelProperty(value = "所在市")
    @ExcelProperty(value = "所在市")
    private String city;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "岗位身份")
    @ExcelProperty(value = "岗位身份")
    private String roleName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}