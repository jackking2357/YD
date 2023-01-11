package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysLogininfor;
import com.yudian.www.mapper.sys.SysLogininforMapper;
import com.yudian.www.service.sys.ISysLogininforService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService {

    @Resource
    private SysLogininforMapper sysLogininforMapper;

}
