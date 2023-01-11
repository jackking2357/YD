package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yudian.www.base.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("`sys_user`")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;


    
    @TableId
    private Long userId;

    
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long deptId;

    
    private String userNickname;

    
    private String userName;

    
    private String password;

    
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Integer userType;

    
    private String userAddress;

    
    private String userEmail;

    
    private String userTel;

    
    private String userAvatar;

    
    private Boolean userStatus;

    
    private String loginIp;

    
    private LocalDateTime loginDate;

    
    private String remark;

    
    private String userPhone;

    
    private Integer userSex;

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    
    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

}