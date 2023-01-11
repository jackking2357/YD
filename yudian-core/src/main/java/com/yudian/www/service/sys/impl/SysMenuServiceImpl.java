package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.mapper.sys.SysMenuMapper;
import com.yudian.www.service.sys.ISysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

}
