package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.mapper.sys.SysUserMapper;
import com.yudian.www.service.sys.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

}
