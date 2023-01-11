package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("`sys_logininfor`")
public class SysLogininfor {

    private static final long serialVersionUID = 1L;


    
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;

    
    private String userName;

    
    private String ipaddr;

    
    private String loginLocation;

    
    private String browser;

    
    private String os;

    
    private Boolean loginStatus;

    
    private String msg;

    
    private LocalDateTime loginTime;

}