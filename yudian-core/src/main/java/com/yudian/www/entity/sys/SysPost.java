package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;


@Data
@TableName("`sys_post`")
public class SysPost extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    
    private String postCode;

    
    private String postName;

    
    private Integer postSort;

    
    private Boolean postStatus;

}