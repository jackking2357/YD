package com.yudian.www.service.robot;

import com.yudian.www.service.robot.param.AeRobotParam;
import com.yudian.www.service.robot.param.GetRobotListParam;
import com.yudian.www.service.robot.vo.RobotInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 机器人 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IRobotServiceProcess {

    /**
     * 添加
     * @param aeRobotParam
     */
    void robotAdd(AeRobotParam aeRobotParam);

    /**
     * 批量创建
     *
     * @param aeRobotParamList
     */
    void robotAddBatch(List<AeRobotParam> aeRobotParamList);

    /**
     * 编辑
     * @param aeRobotParam
     */
    void robotEdit(AeRobotParam aeRobotParam);

    /**
     * 删除
     * @param robotIds
     */
    void robotRemove(Long[] robotIds);

    /**
     * 分页查询
     * @param getRobotListParam
     * @return
     */
    TableRecordVo<RobotInfoVo> getRobotList(GetRobotListParam getRobotListParam);

    /**
     * 明细
     * @param robotId
     * @return
     */
    RobotInfoVo getRobotDetail(Long robotId);
}
