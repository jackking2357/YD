package com.yudian.www.entity.work;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.math.BigDecimal;

/**
 * 任务
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
@TableName("`work_task`")
public class WorkTask extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 工作任务记录id
     * bigint(20) 20
     */
    @TableId
    private Long workTaskId;

    /**
     * 任务封面
     * varchar(255) 255
     */
    private String taskIcon;

    /**
     * 任务名称
     * varchar(32) 32
     */
    private String taskName;

    /**
     * 任务描述
     * varchar(255) 255
     */
    private String taskDesc;

    private Integer showSort;
    private Boolean isEnable;
    private BigDecimal awardIncome;

}