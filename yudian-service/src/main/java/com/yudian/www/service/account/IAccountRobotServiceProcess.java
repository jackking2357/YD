package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountRobotParam;
import com.yudian.www.service.account.param.GetAccountRobotListParam;
import com.yudian.www.service.account.vo.AccountRobotInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户机器人 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountRobotServiceProcess {

    /**
     * 添加
     * @param aeAccountRobotParam
     */
    void accountRobotAdd(AeAccountRobotParam aeAccountRobotParam);

    /**
     * 批量创建
     *
     * @param aeAccountRobotParamList
     */
    void accountRobotAddBatch(List<AeAccountRobotParam> aeAccountRobotParamList);

    /**
     * 编辑
     * @param aeAccountRobotParam
     */
    void accountRobotEdit(AeAccountRobotParam aeAccountRobotParam);

    /**
     * 删除
     * @param accountRobotIds
     */
    void accountRobotRemove(Long[] accountRobotIds);

    /**
     * 分页查询
     * @param getAccountRobotListParam
     * @return
     */
    TableRecordVo<AccountRobotInfoVo> getAccountRobotList(GetAccountRobotListParam getAccountRobotListParam);

    /**
     * 明细
     * @param accountRobotId
     * @return
     */
    AccountRobotInfoVo getAccountRobotDetail(Long accountRobotId);
}
