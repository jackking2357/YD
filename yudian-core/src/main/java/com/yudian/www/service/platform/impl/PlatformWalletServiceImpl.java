package com.yudian.www.service.platform.impl;

import com.yudian.www.entity.platform.PlatformWallet;
import com.yudian.www.mapper.platform.PlatformWalletMapper;
import com.yudian.www.service.platform.IPlatformWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台收款钱包 服务实现类
 *
 * @author yudian
 * @since 2023-01-06
 */
@Service
public class PlatformWalletServiceImpl extends ServiceImpl<PlatformWalletMapper, PlatformWallet> implements IPlatformWalletService {

    @Resource
    private PlatformWalletMapper platformWalletMapper;

}
