package com.yudian.www.service.robot;

import com.yudian.www.service.robot.param.AeRobotOrderParam;
import com.yudian.www.service.robot.param.GetRobotOrderListParam;
import com.yudian.www.service.robot.vo.RobotOrderInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 机器人订单 服务类-流程
 *
 * @author yudian
 * @since 2023-01-06
 */
public interface IRobotOrderServiceProcess {

    /**
     * 添加
     * @param aeRobotOrderParam
     */
    Long robotOrderAdd(AeRobotOrderParam aeRobotOrderParam);

    /**
     * 批量创建
     *
     * @param aeRobotOrderParamList
     */
    void robotOrderAddBatch(List<AeRobotOrderParam> aeRobotOrderParamList);

    /**
     * 编辑
     * @param aeRobotOrderParam
     */
    void robotOrderEdit(AeRobotOrderParam aeRobotOrderParam);

    /**
     * 删除
     * @param robotOrderIds
     */
    void robotOrderRemove(Long[] robotOrderIds);

    /**
     * 分页查询
     * @param getRobotOrderListParam
     * @return
     */
    TableRecordVo<RobotOrderInfoVo> getRobotOrderList(GetRobotOrderListParam getRobotOrderListParam);

    /**
     * 明细
     * @param robotOrderId
     * @return
     */
    RobotOrderInfoVo getRobotOrderDetail(Long robotOrderId);

    void robotOrderCancel(Long[] robotOrderIds);
}
