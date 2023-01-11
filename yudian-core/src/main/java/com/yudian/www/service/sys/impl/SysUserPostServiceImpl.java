package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysUserPost;
import com.yudian.www.mapper.sys.SysUserPostMapper;
import com.yudian.www.service.sys.ISysUserPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

    @Resource
    private SysUserPostMapper sysUserPostMapper;

}
