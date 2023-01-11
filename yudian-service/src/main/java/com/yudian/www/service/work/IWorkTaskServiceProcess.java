package com.yudian.www.service.work;

import com.yudian.www.service.work.param.AeWorkTaskParam;
import com.yudian.www.service.work.param.GetWorkTaskListParam;
import com.yudian.www.service.work.vo.WorkTaskInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 任务 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IWorkTaskServiceProcess {

    /**
     * 添加
     * @param aeWorkTaskParam
     */
    void workTaskAdd(AeWorkTaskParam aeWorkTaskParam);

    /**
     * 批量创建
     *
     * @param aeWorkTaskParamList
     */
    void workTaskAddBatch(List<AeWorkTaskParam> aeWorkTaskParamList);

    /**
     * 编辑
     * @param aeWorkTaskParam
     */
    void workTaskEdit(AeWorkTaskParam aeWorkTaskParam);

    /**
     * 删除
     * @param workTaskIds
     */
    void workTaskRemove(Long[] workTaskIds);

    /**
     * 分页查询
     * @param getWorkTaskListParam
     * @return
     */
    TableRecordVo<WorkTaskInfoVo> getWorkTaskList(GetWorkTaskListParam getWorkTaskListParam);

    /**
     * 明细
     * @param workTaskId
     * @return
     */
    WorkTaskInfoVo getWorkTaskDetail(Long workTaskId);
}
