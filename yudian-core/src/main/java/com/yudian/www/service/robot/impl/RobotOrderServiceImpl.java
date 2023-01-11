package com.yudian.www.service.robot.impl;

import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.mapper.robot.RobotOrderMapper;
import com.yudian.www.service.robot.IRobotOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 机器人订单 服务实现类
 *
 * @author yudian
 * @since 2023-01-06
 */
@Service
public class RobotOrderServiceImpl extends ServiceImpl<RobotOrderMapper, RobotOrder> implements IRobotOrderService {

    @Resource
    private RobotOrderMapper robotOrderMapper;

}
