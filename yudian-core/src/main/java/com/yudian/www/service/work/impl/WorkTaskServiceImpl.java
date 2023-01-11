package com.yudian.www.service.work.impl;

import com.yudian.www.entity.work.WorkTask;
import com.yudian.www.mapper.work.WorkTaskMapper;
import com.yudian.www.service.work.IWorkTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 任务 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class WorkTaskServiceImpl extends ServiceImpl<WorkTaskMapper, WorkTask> implements IWorkTaskService {

    @Resource
    private WorkTaskMapper workTaskMapper;

}
