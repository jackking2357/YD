package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("`sys_dept`")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;


    private Long parentId;


    private String ancestors;


    private String deptName;


    private Integer deptSort;


    private String leader;


    private String phone;


    private Boolean deptStatus;


    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<SysDept>();
}