package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountCertParam;
import com.yudian.www.service.account.param.GetAccountCertListParam;
import com.yudian.www.service.account.vo.AccountCertInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户证件 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountCertServiceProcess {

    /**
     * 添加
     * @param aeAccountCertParam
     */
    void accountCertAdd(AeAccountCertParam aeAccountCertParam);

    /**
     * 批量创建
     *
     * @param aeAccountCertParamList
     */
    void accountCertAddBatch(List<AeAccountCertParam> aeAccountCertParamList);

    /**
     * 编辑
     * @param aeAccountCertParam
     */
    void accountCertEdit(AeAccountCertParam aeAccountCertParam);

    /**
     * 删除
     * @param accountCertIds
     */
    void accountCertRemove(Long[] accountCertIds);

    /**
     * 分页查询
     * @param getAccountCertListParam
     * @return
     */
    TableRecordVo<AccountCertInfoVo> getAccountCertList(GetAccountCertListParam getAccountCertListParam);

    /**
     * 明细
     * @param accountCertId
     * @return
     */
    AccountCertInfoVo getAccountCertDetail(Long accountCertId);
}
