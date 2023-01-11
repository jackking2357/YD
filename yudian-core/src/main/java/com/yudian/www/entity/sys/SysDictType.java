package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.*;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

@Data
@TableName("`sys_dict_type`")
public class SysDictType extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    
    private String dictName;

    
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String dictKey;

    
    private Integer dictStatus;

    
    private String remark;

}