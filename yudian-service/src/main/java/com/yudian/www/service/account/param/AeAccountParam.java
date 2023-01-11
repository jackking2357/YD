package com.yudian.www.service.account.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.yudian.www.base.EditDomain;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台用户
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户")
@ExcelIgnoreUnannotated
public class AeAccountParam {

    /**
     * 平台用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户id")
    private Long accountId;

    /**
     * 邀请用户id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "邀请用户id", required = true, position = 1)
    @NotNull(message = "邀请用户id参数必传")
    private Long inviteAccountId;

    /**
     * 手机号码
     * char(24) 24
     */
    @ApiModelProperty(value = "手机号码", required = true, position = 2)
    @Length(max = 24, message = "手机号码参数过长")
    @NotBlank(message = "手机号码参数必传")
    @ExcelProperty(value = "手机号码")
    private String phoneNumber;

    /**
     * 登录密码
     * varchar(64) 64
     */
    @ApiModelProperty(value = "登录密码", required = true, position = 3)
    @Length(max = 64, message = "登录密码参数过长")
    @NotBlank(message = "登录密码参数必传")
    @ExcelProperty(value = "登录密码")
    private String loginPwd;

    /**
     * 微信用户唯一标识
     * char(32) 32
     */
    @ApiModelProperty(value = "微信用户唯一标识", required = true, position = 4)
    @Length(max = 32, message = "微信用户唯一标识参数过长")
    @NotBlank(message = "微信用户唯一标识参数必传")
    @ExcelProperty(value = "微信用户唯一标识")
    private String wxUnionid;

    /**
     * 微信公众号openid
     * char(32) 32
     */
    @ApiModelProperty(value = "微信公众号openid", required = true, position = 5)
    @Length(max = 32, message = "微信公众号openid参数过长")
    @NotBlank(message = "微信公众号openid参数必传")
    @ExcelProperty(value = "微信公众号openid")
    private String wxGzhOpenid;

    /**
     * 微信小程序openid
     * char(32) 32
     */
    @ApiModelProperty(value = "微信小程序openid", required = true, position = 6)
    @Length(max = 32, message = "微信小程序openid参数过长")
    @NotBlank(message = "微信小程序openid参数必传")
    @ExcelProperty(value = "微信小程序openid")
    private String wxAppletsOpenid;

    /**
     * 微信用户信息授权
     * bit(1) 1
     */
    @ApiModelProperty(value = "微信用户信息授权", required = true, position = 7)
    @NotNull(message = "微信用户信息授权参数必传")
    @ExcelProperty(value = "微信用户信息授权")
    private Boolean isWxUserInfo;

    /**
     * 昵称
     * varchar(128) 128
     */
    @ApiModelProperty(value = "昵称", required = true, position = 8)
    @Length(max = 128, message = "昵称参数过长")
    @NotBlank(message = "昵称参数必传")
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     * varchar(256) 256
     */
    @ApiModelProperty(value = "头像", required = true, position = 9)
    @Length(max = 256, message = "头像参数过长")
    @NotBlank(message = "头像参数必传")
    @ExcelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 性别：0=未知；1=男；2=女；
     * int(1) 1
     */
    @ApiModelProperty(value = "性别：0=未知；1=男；2=女；", required = true, position = 10)
    @NotNull(message = "性别：0=未知；1=男；2=女；参数必传")
    @ExcelProperty(value = "性别：0=未知；1=男；2=女；")
    private Integer gender;

    /**
     * 所在国家
     * varchar(128) 128
     */
    @ApiModelProperty(value = "所在国家", required = true, position = 11)
    @Length(max = 128, message = "所在国家参数过长")
    @NotBlank(message = "所在国家参数必传")
    @ExcelProperty(value = "所在国家")
    private String country;

    /**
     * 所在省
     * varchar(128) 128
     */
    @ApiModelProperty(value = "所在省", required = true, position = 12)
    @Length(max = 128, message = "所在省参数过长")
    @NotBlank(message = "所在省参数必传")
    @ExcelProperty(value = "所在省")
    private String province;

    /**
     * 所在市
     * varchar(128) 128
     */
    @ApiModelProperty(value = "所在市", required = true, position = 13)
    @Length(max = 128, message = "所在市参数过长")
    @NotBlank(message = "所在市参数必传")
    @ExcelProperty(value = "所在市")
    private String city;

    /**
     * 用户状态：1=正常；2=冻结
     * int(1) 1
     */
    @ApiModelProperty(value = "用户状态：1=正常；2=冻结", required = true, position = 14)
    @NotNull(message = "用户状态：1=正常；2=冻结参数必传")
    @ExcelProperty(value = "用户状态：1=正常；2=冻结")
    private Integer accountStatus;

    /**
     * 积分
     * int(9) 9
     */
    @ApiModelProperty(value = "积分", required = true, position = 15)
    @NotNull(message = "积分参数必传")
    @ExcelProperty(value = "积分")
    private BigDecimal score;

    /**
     * 支付宝收款码
     * varchar(255) 255
     */
    @ApiModelProperty(value = "支付宝收款码", required = true, position = 16)
    @Length(max = 255, message = "支付宝收款码参数过长")
    @NotBlank(message = "支付宝收款码参数必传")
    @ExcelProperty(value = "支付宝收款码")
    private String zfbQrCode;

    /**
     * 支付宝账号
     * varchar(64) 64
     */
    @ApiModelProperty(value = "支付宝账号", required = true, position = 17)
    @Length(max = 64, message = "支付宝账号参数过长")
    @NotBlank(message = "支付宝账号参数必传")
    @ExcelProperty(value = "支付宝账号")
    private String zfbUsername;

    /**
     * 账户邀请码
     */
    private String invitationCode;

    /**
     * 身份验证结果：0=待验证；1=成功；2=失败
     */
    private Integer certStatus;

    public void initParam() {

    }
}