package com.yudian.www.service.robot;

import com.yudian.www.service.robot.param.AeRobotAcceleratorParam;
import com.yudian.www.service.robot.param.GetRobotAcceleratorListParam;
import com.yudian.www.service.robot.vo.RobotAcceleratorInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 机器人加速器 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IRobotAcceleratorServiceProcess {

    /**
     * 添加
     * @param aeRobotAcceleratorParam
     */
    void robotAcceleratorAdd(AeRobotAcceleratorParam aeRobotAcceleratorParam);

    /**
     * 批量创建
     *
     * @param aeRobotAcceleratorParamList
     */
    void robotAcceleratorAddBatch(List<AeRobotAcceleratorParam> aeRobotAcceleratorParamList);

    /**
     * 编辑
     * @param aeRobotAcceleratorParam
     */
    void robotAcceleratorEdit(AeRobotAcceleratorParam aeRobotAcceleratorParam);

    /**
     * 删除
     * @param robotAcceleratorIds
     */
    void robotAcceleratorRemove(Long[] robotAcceleratorIds);

    /**
     * 分页查询
     * @param getRobotAcceleratorListParam
     * @return
     */
    TableRecordVo<RobotAcceleratorInfoVo> getRobotAcceleratorList(GetRobotAcceleratorListParam getRobotAcceleratorListParam);

    /**
     * 明细
     * @param robotAcceleratorId
     * @return
     */
    RobotAcceleratorInfoVo getRobotAcceleratorDetail(Long robotAcceleratorId);
}
