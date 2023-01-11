package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountRobotWorkParam;
import com.yudian.www.service.account.param.GetAccountRobotWorkListParam;
import com.yudian.www.service.account.vo.AccountRobotWorkInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户机器人工作记录 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountRobotWorkServiceProcess {

    /**
     * 添加
     * @param aeAccountRobotWorkParam
     */
    void accountRobotWorkAdd(AeAccountRobotWorkParam aeAccountRobotWorkParam);

    /**
     * 批量创建
     *
     * @param aeAccountRobotWorkParamList
     */
    void accountRobotWorkAddBatch(List<AeAccountRobotWorkParam> aeAccountRobotWorkParamList);

    /**
     * 编辑
     * @param aeAccountRobotWorkParam
     */
    void accountRobotWorkEdit(AeAccountRobotWorkParam aeAccountRobotWorkParam);

    /**
     * 删除
     * @param accountRobotWorkIds
     */
    void accountRobotWorkRemove(Long[] accountRobotWorkIds);

    /**
     * 分页查询
     * @param getAccountRobotWorkListParam
     * @return
     */
    TableRecordVo<AccountRobotWorkInfoVo> getAccountRobotWorkList(GetAccountRobotWorkListParam getAccountRobotWorkListParam);

    /**
     * 明细
     * @param accountRobotWorkId
     * @return
     */
    AccountRobotWorkInfoVo getAccountRobotWorkDetail(Long accountRobotWorkId);
}
