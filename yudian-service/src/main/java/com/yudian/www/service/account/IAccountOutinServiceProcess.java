package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountOutinParam;
import com.yudian.www.service.account.param.GetAccountOutinListParam;
import com.yudian.www.service.account.vo.AccountOutinInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户流水 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountOutinServiceProcess {

    /**
     * 添加
     * @param aeAccountOutinParam
     */
    void accountOutinAdd(AeAccountOutinParam aeAccountOutinParam);

    /**
     * 批量创建
     *
     * @param aeAccountOutinParamList
     */
    void accountOutinAddBatch(List<AeAccountOutinParam> aeAccountOutinParamList);

    /**
     * 编辑
     * @param aeAccountOutinParam
     */
    void accountOutinEdit(AeAccountOutinParam aeAccountOutinParam);

    /**
     * 删除
     * @param accountOutinIds
     */
    void accountOutinRemove(Long[] accountOutinIds);

    /**
     * 分页查询
     * @param getAccountOutinListParam
     * @return
     */
    TableRecordVo<AccountOutinInfoVo> getAccountOutinList(GetAccountOutinListParam getAccountOutinListParam);

    /**
     * 明细
     * @param accountOutinId
     * @return
     */
    AccountOutinInfoVo getAccountOutinDetail(Long accountOutinId);
}
