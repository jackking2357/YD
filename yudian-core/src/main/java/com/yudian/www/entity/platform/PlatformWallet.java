package com.yudian.www.entity.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

/**
 * 平台收款钱包
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
@TableName("`platform_wallet`")
public class PlatformWallet extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台收款钱包配置id
     * int(11) 11
     */
    @TableId
    private Long platformWalletId;

    /**
     * 收款码
     * varchar(255) 255
     */
    private String qrCodePhoto;

    /**
     * 收款账户
     * varchar(128) 128
     */
    private String qrAccount;

    /**
     * 类型：1=微信；2=支付宝；3=银行卡
     * int(1) 1
     */
    private Integer qrType;

    /**
     * 是否启用
     * bit(1) 1
     */
    private Boolean isEnable;

}