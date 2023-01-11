package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountRobot;
import com.yudian.www.mapper.account.AccountRobotMapper;
import com.yudian.www.service.account.IAccountRobotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户机器人 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountRobotServiceImpl extends ServiceImpl<AccountRobotMapper, AccountRobot> implements IAccountRobotService {

    @Resource
    private AccountRobotMapper accountRobotMapper;

}
