package com.yudian.www.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("`sys_oper_log`")
public class SysOperLog {

    private static final long serialVersionUID = 1L;


    
    @TableId(value = "oper_id", type = IdType.AUTO)
    private Long operId;

    
    private String title;

    
    private Integer businessType;

    
    private String method;

    
    private String requestMethod;

    
    private Integer operatorType;

    
    private String operName;

    
    private String deptName;

    
    private String operUrl;

    
    private String operIp;

    
    private String operLocation;

    
    private String operParam;

    
    private String jsonResult;

    
    private Integer status;

    
    private String errorMsg;

    
    private LocalDateTime operTime;

}