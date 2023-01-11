package com.yudian.www.service.robot.impl;

import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.mapper.robot.RobotAcceleratorMapper;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 机器人加速器 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class RobotAcceleratorServiceImpl extends ServiceImpl<RobotAcceleratorMapper, RobotAccelerator> implements IRobotAcceleratorService {

    @Resource
    private RobotAcceleratorMapper robotAcceleratorMapper;

}
