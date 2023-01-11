package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysOperLog;
import com.yudian.www.mapper.sys.SysOperLogMapper;
import com.yudian.www.service.sys.ISysOperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    @Resource
    private SysOperLogMapper sysOperLogMapper;

}
