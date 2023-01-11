package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysDictType;
import com.yudian.www.mapper.sys.SysDictTypeMapper;
import com.yudian.www.service.sys.ISysDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

}
