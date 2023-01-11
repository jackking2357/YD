package com.yudian.www.service.platform;

import com.yudian.www.service.platform.param.AePlatformWalletParam;
import com.yudian.www.service.platform.param.GetPlatformWalletListParam;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台收款钱包 服务类-流程
 *
 * @author yudian
 * @since 2023-01-06
 */
public interface IPlatformWalletServiceProcess {

    /**
     * 添加
     * @param aePlatformWalletParam
     */
    void platformWalletAdd(AePlatformWalletParam aePlatformWalletParam);

    /**
     * 批量创建
     *
     * @param aePlatformWalletParamList
     */
    void platformWalletAddBatch(List<AePlatformWalletParam> aePlatformWalletParamList);

    /**
     * 编辑
     * @param aePlatformWalletParam
     */
    void platformWalletEdit(AePlatformWalletParam aePlatformWalletParam);

    /**
     * 删除
     * @param platformWalletIds
     */
    void platformWalletRemove(Long[] platformWalletIds);

    /**
     * 分页查询
     * @param getPlatformWalletListParam
     * @return
     */
    TableRecordVo<PlatformWalletInfoVo> getPlatformWalletList(GetPlatformWalletListParam getPlatformWalletListParam);

    /**
     * 明细
     * @param platformWalletId
     * @return
     */
    PlatformWalletInfoVo getPlatformWalletDetail(Long platformWalletId);
}
