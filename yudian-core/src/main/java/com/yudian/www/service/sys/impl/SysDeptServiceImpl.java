package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysDept;
import com.yudian.www.mapper.sys.SysDeptMapper;
import com.yudian.www.service.sys.ISysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

}
