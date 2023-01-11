package com.yudian.www.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.work.IWorkTaskServiceProcess;
import com.yudian.www.service.work.param.GetWorkTaskListParam;
import com.yudian.www.service.work.vo.WorkTaskInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 任务 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "任务")
@RestController
@RequestMapping("/public/work-task")
public class WorkTaskController {

    @Resource
    private IWorkTaskServiceProcess workTaskServiceProcess;

    @ApiOperation(value = "任务(列表)", notes = "任务(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getWorkTaskList")
    public R<TableRecordVo<WorkTaskInfoVo>> getWorkTaskList(@Valid @RequestBody GetWorkTaskListParam getWorkTaskListParam) {
        getWorkTaskListParam.setIsEnable(true);
        getWorkTaskListParam.setOrderByColumn("showSort");
        getWorkTaskListParam.setIsAsc("asc");
        TableRecordVo<WorkTaskInfoVo> tableRecordVo = workTaskServiceProcess.getWorkTaskList(getWorkTaskListParam);
        return R.ok(tableRecordVo, "获取成功");
    }
}
