package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.mapper.account.AccountOutinMapper;
import com.yudian.www.service.account.IAccountOutinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户流水 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountOutinServiceImpl extends ServiceImpl<AccountOutinMapper, AccountOutin> implements IAccountOutinService {

    @Resource
    private AccountOutinMapper accountOutinMapper;

}
