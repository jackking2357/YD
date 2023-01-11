package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysUserRole;
import com.yudian.www.mapper.sys.SysUserRoleMapper;
import com.yudian.www.service.sys.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

}
