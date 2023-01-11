package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.Account;
import com.yudian.www.mapper.account.AccountMapper;
import com.yudian.www.service.account.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

}
