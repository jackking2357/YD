package com.yudian.www.entity.suggestion;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 建议档案
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
@TableName("`suggestion`")
public class Suggestion extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 建议id
     * bigint(20) 20
     */
    @TableId
    private Long suggestionId;

    /**
     * 内容
     * varchar(512) 512
     */
    private String suggestionContent;

    /**
     * 照片（JSON数组）
     * varchar(512) 512
     */
    private String suggestionPhotos;

    /**
     * 处理状态：1=待处理；2=已处理；3=处理中
     * int(1) 1
     */
    private Integer suggestionStatus;

}