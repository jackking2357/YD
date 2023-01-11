package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysRoleMenu;
import com.yudian.www.mapper.sys.SysRoleMenuMapper;
import com.yudian.www.service.sys.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

}
