package com.yudian.www.entity.platform;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台协议
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
@TableName("`platform_protocol`")
public class PlatformProtocol extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台协议配置id
     * bigint(20) 20
     */
    @TableId
    private Long platformProtocolId;

    /**
     * 协议内容
     * text tex
     */
    private String protocolContent;

    /**
     * 协议版本
     * int(10) 10
     */
    private Integer protocolVersion;

    /**
     * 协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告
     * int(1) 1
     */
    private Integer protocolType;

}