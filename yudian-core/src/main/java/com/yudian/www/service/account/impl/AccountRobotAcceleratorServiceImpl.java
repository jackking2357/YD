package com.yudian.www.service.account.impl;

import com.yudian.www.entity.account.AccountRobotAccelerator;
import com.yudian.www.mapper.account.AccountRobotAcceleratorMapper;
import com.yudian.www.service.account.IAccountRobotAcceleratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 平台用户机器人加速器 服务实现类
 *
 * @author yudian
 * @since 2023-01-07
 */
@Service
public class AccountRobotAcceleratorServiceImpl extends ServiceImpl<AccountRobotAcceleratorMapper, AccountRobotAccelerator> implements IAccountRobotAcceleratorService {

    @Resource
    private AccountRobotAcceleratorMapper accountRobotAcceleratorMapper;

}
