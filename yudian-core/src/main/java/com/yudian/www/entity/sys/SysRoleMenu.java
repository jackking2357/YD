package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("`sys_role_menu`")
public class SysRoleMenu {

    private static final long serialVersionUID = 1L;


    
    @TableId
    private Long roleId;

    
    private Long menuId;

}