package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("`sys_menu`")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId
    private Long menuId;

    
    private String menuName;

    
    private Long parentId;

    
    private String ancestors;

    
    private Integer menuSort;

    
    private String path;

    
    private String component;

    
    private String query;

    
    private Integer isFrame;

    
    private Integer isCache;

    
    private String menuType;

    
    private Integer menuStatus;

    
    private Integer visible;

    
    private String perms;

    
    private String icon;

    
    private String remark;

    
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}