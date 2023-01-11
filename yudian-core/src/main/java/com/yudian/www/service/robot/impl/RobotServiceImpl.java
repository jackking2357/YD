package com.yudian.www.service.robot.impl;

import com.yudian.www.entity.robot.Robot;
import com.yudian.www.mapper.robot.RobotMapper;
import com.yudian.www.service.robot.IRobotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 机器人 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class RobotServiceImpl extends ServiceImpl<RobotMapper, Robot> implements IRobotService {

    @Resource
    private RobotMapper robotMapper;

}
