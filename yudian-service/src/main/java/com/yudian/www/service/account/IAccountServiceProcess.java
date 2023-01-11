package com.yudian.www.service.account;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.param.AeAccountParam;
import com.yudian.www.service.account.param.GetAccountListParam;
import com.yudian.www.service.account.vo.AccountInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 平台用户 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountServiceProcess {

    /**
     * 添加
     *
     * @param aeAccountParam
     */
    void accountAdd(AeAccountParam aeAccountParam);

    /**
     * 批量创建
     *
     * @param aeAccountParamList
     */
    void accountAddBatch(List<AeAccountParam> aeAccountParamList);

    /**
     * 编辑
     *
     * @param aeAccountParam
     */
    void accountEdit(AeAccountParam aeAccountParam);

    /**
     * 删除
     *
     * @param accountIds
     */
    void accountRemove(Long[] accountIds);

    /**
     * 分页查询
     *
     * @param getAccountListParam
     * @return
     */
    TableRecordVo<AccountInfoVo> getAccountList(GetAccountListParam getAccountListParam);

    /**
     * 明细
     *
     * @param accountId
     * @return
     */
    AccountInfoVo getAccountDetail(Long accountId);

    /**
     * app登录
     *
     * @param phoneNumber
     * @param loginPwd
     * @return
     */
    Map<String, Object> appLogin(String phoneNumber, String loginPwd);

    void editLoginPwd(String oldLoginPwd, String newLoginPwd);
}
