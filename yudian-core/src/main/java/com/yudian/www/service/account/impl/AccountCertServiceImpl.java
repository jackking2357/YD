package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountCert;
import com.yudian.www.mapper.account.AccountCertMapper;
import com.yudian.www.service.account.IAccountCertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户证件 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountCertServiceImpl extends ServiceImpl<AccountCertMapper, AccountCert> implements IAccountCertService {

    @Resource
    private AccountCertMapper accountCertMapper;

}
