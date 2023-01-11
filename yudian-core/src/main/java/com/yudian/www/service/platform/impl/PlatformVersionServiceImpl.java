package com.yudian.www.service.platform.impl;

import com.yudian.www.entity.platform.PlatformVersion;
import com.yudian.www.mapper.platform.PlatformVersionMapper;
import com.yudian.www.service.platform.IPlatformVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台版本 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class PlatformVersionServiceImpl extends ServiceImpl<PlatformVersionMapper, PlatformVersion> implements IPlatformVersionService {

    @Resource
    private PlatformVersionMapper platformVersionMapper;

}
