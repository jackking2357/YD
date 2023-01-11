package com.yudian.www.service.platform.impl;

import com.yudian.www.entity.platform.PlatformProtocol;
import com.yudian.www.mapper.platform.PlatformProtocolMapper;
import com.yudian.www.service.platform.IPlatformProtocolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台协议 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class PlatformProtocolServiceImpl extends ServiceImpl<PlatformProtocolMapper, PlatformProtocol> implements IPlatformProtocolService {

    @Resource
    private PlatformProtocolMapper platformProtocolMapper;

}
