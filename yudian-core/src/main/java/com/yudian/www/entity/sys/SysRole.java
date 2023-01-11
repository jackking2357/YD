package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;


@Data
@TableName("`sys_role`")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId
    private Long roleId;

    
    private String roleName;

    
    private String roleKey;

    
    private Integer roleSort;

    
    private Boolean menuCheckStrictly;

    
    private Boolean roleStatus;

    
    private String remark;

    
    private String dataScope;

}