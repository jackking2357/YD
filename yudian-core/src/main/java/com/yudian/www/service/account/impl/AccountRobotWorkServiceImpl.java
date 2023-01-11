package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountRobotWork;
import com.yudian.www.mapper.account.AccountRobotWorkMapper;
import com.yudian.www.service.account.IAccountRobotWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户机器人工作记录 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountRobotWorkServiceImpl extends ServiceImpl<AccountRobotWorkMapper, AccountRobotWork> implements IAccountRobotWorkService {

    @Resource
    private AccountRobotWorkMapper accountRobotWorkMapper;

}
