package com.yudian.www.entity.account;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台用户
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
@TableName("`account`")
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户id
     * bigint(20) 20
     */
    @TableId
    private Long accountId;

    /**
     * 邀请用户id
     * bigint(20) 20
     */
    private Long inviteAccountId;

    /**
     * 手机号码
     * char(24) 24
     */
    private String phoneNumber;

    /**
     * 登录密码
     * varchar(64) 64
     */
    private String loginPwd;

    /**
     * 微信用户唯一标识
     * char(32) 32
     */
    private String wxUnionid;

    /**
     * 微信公众号openid
     * char(32) 32
     */
    private String wxGzhOpenid;

    /**
     * 微信小程序openid
     * char(32) 32
     */
    private String wxAppletsOpenid;

    /**
     * 微信用户信息授权
     * bit(1) 1
     */
    private Boolean isWxUserInfo;

    /**
     * 昵称
     * varchar(128) 128
     */
    private String nickName;

    /**
     * 头像
     * varchar(256) 256
     */
    private String avatarUrl;

    /**
     * 性别：0=未知；1=男；2=女；
     * int(1) 1
     */
    private Integer gender;

    /**
     * 所在国家
     * varchar(128) 128
     */
    private String country;

    /**
     * 所在省
     * varchar(128) 128
     */
    private String province;

    /**
     * 所在市
     * varchar(128) 128
     */
    private String city;

    /**
     * 用户状态：1=正常；2=冻结
     * int(1) 1
     */
    private Integer accountStatus;

    /**
     * 积分
     * int(9) 9
     */
    private BigDecimal score;

    /**
     * 支付宝收款码
     * varchar(255) 255
     */
    private String zfbQrCode;

    /**
     * 支付宝账号
     * varchar(64) 64
     */
    private String zfbUsername;

    /**
     * 账户邀请码
     */
    private String invitationCode;

    /**
     * 身份验证结果：0=待验证；1=成功；2=失败
     */
    private Integer certStatus;

}