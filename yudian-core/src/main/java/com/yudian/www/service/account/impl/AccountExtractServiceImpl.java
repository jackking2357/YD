package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountExtract;
import com.yudian.www.mapper.account.AccountExtractMapper;
import com.yudian.www.service.account.IAccountExtractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户提取记录 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountExtractServiceImpl extends ServiceImpl<AccountExtractMapper, AccountExtract> implements IAccountExtractService {

    @Resource
    private AccountExtractMapper accountExtractMapper;

}
