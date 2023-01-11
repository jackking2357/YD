package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 平台用户
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户")
@ExcelIgnoreUnannotated
public class AccountInfoVo {

    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType = "Long")
    private Long accountId;

    /**
     * 邀请用户id
     */
    @ApiModelProperty(value = "邀请用户id", dataType = "Long")
    private Long inviteAccountId;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", dataType = "String")
    @ExcelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     * 微信用户唯一标识
     */
    @ApiModelProperty(value = "微信用户唯一标识", dataType = "String")
    @ExcelProperty(value = "微信用户唯一标识")
    private String wxUnionid;

    /**
     * 微信公众号openid
     */
    @ApiModelProperty(value = "微信公众号openid", dataType = "String")
    @ExcelProperty(value = "微信公众号openid")
    private String wxGzhOpenid;

    /**
     * 微信小程序openid
     */
    @ApiModelProperty(value = "微信小程序openid", dataType = "String")
    @ExcelProperty(value = "微信小程序openid")
    private String wxAppletsOpenid;

    /**
     * 微信用户信息授权
     */
    @ApiModelProperty(value = "微信用户信息授权", dataType = "Boolean")
    @ExcelProperty(value = "微信用户信息授权")
    private Boolean isWxUserInfo;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", dataType = "String")
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", dataType = "String")
    @ExcelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 性别：0=未知；1=男；2=女；
     */
    @ApiModelProperty(value = "性别：0=未知；1=男；2=女；", dataType = "Integer")
    @ExcelProperty(value = "性别：0=未知；1=男；2=女；")
    private Integer gender;

    /**
     * 所在国家
     */
    @ApiModelProperty(value = "所在国家", dataType = "String")
    @ExcelProperty(value = "所在国家")
    private String country;

    /**
     * 所在省
     */
    @ApiModelProperty(value = "所在省", dataType = "String")
    @ExcelProperty(value = "所在省")
    private String province;

    /**
     * 所在市
     */
    @ApiModelProperty(value = "所在市", dataType = "String")
    @ExcelProperty(value = "所在市")
    private String city;

    /**
     * 用户状态：1=正常；2=冻结
     */
    @ApiModelProperty(value = "用户状态：1=正常；2=冻结", dataType = "Integer")
    @ExcelProperty(value = "用户状态：1=正常；2=冻结")
    private Integer accountStatus;

    /**
     * 积分
     */
    @ApiModelProperty(value = "积分")
    @ExcelProperty(value = "积分")
    private BigDecimal score;

    /**
     * 支付宝收款码
     */
    @ApiModelProperty(value = "支付宝收款码", dataType = "String")
    @ExcelProperty(value = "支付宝收款码")
    private String zfbQrCode;

    /**
     * 支付宝账号
     */
    @ApiModelProperty(value = "支付宝账号", dataType = "String")
    @ExcelProperty(value = "支付宝账号")
    private String zfbUsername;

    /**
     * 账户邀请码
     */
    @ApiModelProperty(value = "账户邀请码", dataType = "String")
    @ExcelProperty(value = "账户邀请码")
    private String invitationCode;

    /**
     * 身份验证结果：0=待验证；1=成功；2=失败
     */
    @ApiModelProperty(value = "身份验证结果：0=待验证；1=成功；2=失败", dataType = "String")
    @ExcelProperty(value = "身份验证结果：0=待验证；1=成功；2=失败")
    private Integer certStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;

    @ApiModelProperty(value = "今日收益")
    private BigDecimal todayIncome;
    @ApiModelProperty(value = "总收益")
    private BigDecimal sumIncome;

    @ApiModelProperty(value = "证件信息", hidden = true)
    private AccountCertInfoVo accountCertInfoVo;
}