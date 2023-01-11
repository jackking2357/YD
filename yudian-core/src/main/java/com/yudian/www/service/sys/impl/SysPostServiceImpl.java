package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysPost;
import com.yudian.www.mapper.sys.SysPostMapper;
import com.yudian.www.service.sys.ISysPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Resource
    private SysPostMapper sysPostMapper;

}
