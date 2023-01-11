package com.yudian.www.entity.account;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

/**
 * 平台用户证件
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
@TableName("`account_cert`")
public class AccountCert extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台用户证件id
     * bigint(20) 20
     */
    @TableId
    private Long accountCertId;

    /**
     * 平台用户id
     */
    private Long accountId;

    /**
     * 证件正面
     * varchar(255) 255
     */
    private String certPhotoFront;

    /**
     * 证件反面
     * varchar(255) 255
     */
    private String certPhotoReverse;

    /**
     * 审核状态：0=待审核；1=已通过；2=未通过
     * int(1) 1
     */
    private Integer reviewStatus;

    /**
     * 审核备注
     * varchar(255) 255
     */
    private String reviewRemark;

}