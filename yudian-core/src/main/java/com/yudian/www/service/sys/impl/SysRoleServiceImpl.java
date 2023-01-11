package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysRole;
import com.yudian.www.mapper.sys.SysRoleMapper;
import com.yudian.www.service.sys.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

}
