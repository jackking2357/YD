package com.yudian.www.service.work.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.work.WorkTask;
import com.yudian.www.service.work.IWorkTaskService;
import com.yudian.www.service.work.IWorkTaskServiceProcess;
import com.yudian.www.service.work.param.AeWorkTaskParam;
import com.yudian.www.service.work.param.GetWorkTaskListParam;
import com.yudian.www.service.work.vo.WorkTaskInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class WorkTaskServiceImplProcess implements IWorkTaskServiceProcess {

    @Resource
    private IWorkTaskService workTaskService;

    /**
     * 添加
     *
     * @param aeWorkTaskParam
     */
    @Override
    public void workTaskAdd(AeWorkTaskParam aeWorkTaskParam) {
        aeWorkTaskParam.initParam();
        this.checkParam(aeWorkTaskParam);
        WorkTask workTask = new WorkTask();
        BeanUtils.copyProperties(aeWorkTaskParam, workTask);
        workTask.setTaskIcon(JSON.toJSONString(aeWorkTaskParam.getTaskIcon()));
        boolean save = workTaskService.save(workTask);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void workTaskAddBatch(List<AeWorkTaskParam> aeWorkTaskParamList) {
        List<WorkTask> workTaskList = aeWorkTaskParamList.stream()
                .map(aeWorkTaskParam -> {
                    WorkTask workTask = new WorkTask();
                    BeanUtils.copyProperties(aeWorkTaskParam, workTask);
                    workTask.setTaskIcon(JSON.toJSONString(aeWorkTaskParam.getTaskIcon()));
                    return workTask;
                }).collect(Collectors.toList());
        boolean save = workTaskService.saveBatch(workTaskList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeWorkTaskParam
     */
    @Override
    public void workTaskEdit(AeWorkTaskParam aeWorkTaskParam) {
        aeWorkTaskParam.initParam();
        this.checkParam(aeWorkTaskParam);
        WorkTask workTask = workTaskService.getById(aeWorkTaskParam.getWorkTaskId());
        if (null == workTask) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeWorkTaskParam, workTask);
        workTask.setTaskIcon(JSON.toJSONString(aeWorkTaskParam.getTaskIcon()));
        boolean update = workTaskService.updateById(workTask);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param workTaskIds
     */
    @Override
    public void workTaskRemove(Long[] workTaskIds) {
        boolean remove = workTaskService.removeByIds(Arrays.asList(workTaskIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getWorkTaskListParam
     * @return
     */
    @Override
    public TableRecordVo<WorkTaskInfoVo> getWorkTaskList(GetWorkTaskListParam getWorkTaskListParam) {
        Boolean isEnable = getWorkTaskListParam.getIsEnable();

        LambdaQueryWrapper<WorkTask> queryWrapper = new QueryWrapper<WorkTask>().lambda();
        queryWrapper.eq(null != isEnable, WorkTask::getIsEnable, isEnable);
        queryWrapper
                .ge(null != getWorkTaskListParam.getStartDateTime(), WorkTask::getCreateDatetime, getWorkTaskListParam.getStartDateTime())
                .le(null != getWorkTaskListParam.getEndDateTime(), WorkTask::getCreateDatetime, getWorkTaskListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getWorkTaskListParam.getOrderByColumn()) && StringUtils.isNotBlank(getWorkTaskListParam.getIsAsc())) {
            queryWrapper.last("order by " + getWorkTaskListParam.getOrderBy());
        }

        IPage<WorkTask> page = workTaskService.page(new Page<>(getWorkTaskListParam.getPageNo(), getWorkTaskListParam.getPageSize()), queryWrapper);

        List<WorkTask> records = page.getRecords();

        List<WorkTaskInfoVo> workTaskInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(workTaskInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param workTaskId
     * @return
     */
    @Override
    public WorkTaskInfoVo getWorkTaskDetail(Long workTaskId) {
        WorkTask workTask = workTaskService.getById(workTaskId);
        if (null == workTask) {
            throw new ServiceException("记录不存在");
        }
        List<WorkTaskInfoVo> workTaskInfoVos = this.entityToVo(Arrays.asList(workTask));
        return workTaskInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<WorkTaskInfoVo> entityToVo(List<WorkTask> records) {
        return records.stream()
                .map(workTask -> {
                    WorkTaskInfoVo workTaskInfoVo = new WorkTaskInfoVo();
                    BeanUtils.copyProperties(workTask, workTaskInfoVo);
                    return workTaskInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeWorkTaskParam
     */
    private void checkParam(AeWorkTaskParam aeWorkTaskParam) {

    }
}