package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

@Data
@TableName("`sys_dict_data`")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId(value = "dict_data_id", type = IdType.AUTO)
    private Long dictDataId;

    
    private String dictKey;

    
    private Integer dictSort;

    
    private String dictLabel;

    
    private String dictValue;

    
    private Integer dictValueType;

    
    private String cssClass;

    
    private String listClass;

    
    private String isDefault;

    
    private Integer dictStatus;

    
    private String remark;

}