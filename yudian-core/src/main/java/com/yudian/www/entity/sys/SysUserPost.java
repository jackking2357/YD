package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("`sys_user_post`")
public class SysUserPost {

    private static final long serialVersionUID = 1L;


    
    @TableId
    private Long userId;

    
    private Long postId;

}