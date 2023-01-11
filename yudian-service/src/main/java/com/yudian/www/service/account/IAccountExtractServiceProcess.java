package com.yudian.www.service.account;

import com.yudian.www.service.account.param.AeAccountExtractParam;
import com.yudian.www.service.account.param.GetAccountExtractListParam;
import com.yudian.www.service.account.vo.AccountExtractInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台用户提取记录 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IAccountExtractServiceProcess {

    /**
     * 添加
     * @param aeAccountExtractParam
     */
    void accountExtractAdd(AeAccountExtractParam aeAccountExtractParam);

    /**
     * 批量创建
     *
     * @param aeAccountExtractParamList
     */
    void accountExtractAddBatch(List<AeAccountExtractParam> aeAccountExtractParamList);

    /**
     * 编辑
     * @param aeAccountExtractParam
     */
    void accountExtractEdit(AeAccountExtractParam aeAccountExtractParam);

    /**
     * 删除
     * @param accountExtractIds
     */
    void accountExtractRemove(Long[] accountExtractIds);

    /**
     * 分页查询
     * @param getAccountExtractListParam
     * @return
     */
    TableRecordVo<AccountExtractInfoVo> getAccountExtractList(GetAccountExtractListParam getAccountExtractListParam);

    /**
     * 明细
     * @param accountExtractId
     * @return
     */
    AccountExtractInfoVo getAccountExtractDetail(Long accountExtractId);
}
