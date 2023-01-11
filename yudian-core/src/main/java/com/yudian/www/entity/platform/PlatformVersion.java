package com.yudian.www.entity.platform;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 平台版本
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
@TableName("`platform_version`")
public class PlatformVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 平台版本id
     * int(11) 11
     */
    @TableId(value = "platform_version_id", type = IdType.AUTO)
    private Integer platformVersionId;

    /**
     * 版本名称
     * varchar(24) 24
     */
    private String versionName;

    /**
     * 版本编码
     * int(9) 9
     */
    private Integer versionCode;

    /**
     * 版本内容
     * text tex
     */
    private String versionContent;

    /**
     * 版本系统：1=安卓；2=IOS
     * int(1) 1
     */
    private Integer versionSystem;

    /**
     * 下载链接
     * varchar(255) 255
     */
    private String downloadUrl;

    /**
     * 是否强制更新
     * char(5) 5
     */
    private String isUpdate;

}