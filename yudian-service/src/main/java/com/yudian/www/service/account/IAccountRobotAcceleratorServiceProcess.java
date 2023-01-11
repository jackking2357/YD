package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountRobotAcceleratorParam;
import com.yudian.www.service.account.param.GetAccountRobotAcceleratorListParam;
import com.yudian.www.service.account.vo.AccountRobotAcceleratorInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户机器人加速器 服务类-流程
 *
 * @author yudian
 * @since 2023-01-07
 */
public interface IAccountRobotAcceleratorServiceProcess {

    /**
     * 添加
     * @param aeAccountRobotAcceleratorParam
     */
    void accountRobotAcceleratorAdd(AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam);

    /**
     * 批量创建
     *
     * @param aeAccountRobotAcceleratorParamList
     */
    void accountRobotAcceleratorAddBatch(List<AeAccountRobotAcceleratorParam> aeAccountRobotAcceleratorParamList);

    /**
     * 编辑
     * @param aeAccountRobotAcceleratorParam
     */
    void accountRobotAcceleratorEdit(AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam);

    /**
     * 删除
     * @param accountRobotAcceleratorIds
     */
    void accountRobotAcceleratorRemove(Long[] accountRobotAcceleratorIds);

    /**
     * 分页查询
     * @param getAccountRobotAcceleratorListParam
     * @return
     */
    TableRecordVo<AccountRobotAcceleratorInfoVo> getAccountRobotAcceleratorList(GetAccountRobotAcceleratorListParam getAccountRobotAcceleratorListParam);

    /**
     * 明细
     * @param accountRobotAcceleratorId
     * @return
     */
    AccountRobotAcceleratorInfoVo getAccountRobotAcceleratorDetail(Long accountRobotAcceleratorId);
}
