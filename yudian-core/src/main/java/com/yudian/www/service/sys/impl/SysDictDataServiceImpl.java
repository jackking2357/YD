package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yudian.www.entity.sys.SysDictData;
import com.yudian.www.mapper.sys.SysDictDataMapper;
import com.yudian.www.service.sys.ISysDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Resource
    private SysDictDataMapper sysDictDataMapper;

}
