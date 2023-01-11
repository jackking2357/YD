package com.yudian.www.service.platform;

import com.yudian.www.service.platform.param.AePlatformProtocolParam;
import com.yudian.www.service.platform.param.GetPlatformProtocolListParam;
import com.yudian.www.service.platform.vo.PlatformProtocolInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台协议 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IPlatformProtocolServiceProcess {

    /**
     * 添加
     * @param aePlatformProtocolParam
     */
    void platformProtocolAdd(AePlatformProtocolParam aePlatformProtocolParam);

    /**
     * 批量创建
     *
     * @param aePlatformProtocolParamList
     */
    void platformProtocolAddBatch(List<AePlatformProtocolParam> aePlatformProtocolParamList);

    /**
     * 编辑
     * @param aePlatformProtocolParam
     */
    void platformProtocolEdit(AePlatformProtocolParam aePlatformProtocolParam);

    /**
     * 删除
     * @param platformProtocolIds
     */
    void platformProtocolRemove(Long[] platformProtocolIds);

    /**
     * 分页查询
     * @param getPlatformProtocolListParam
     * @return
     */
    TableRecordVo<PlatformProtocolInfoVo> getPlatformProtocolList(GetPlatformProtocolListParam getPlatformProtocolListParam);

    /**
     * 明细
     * @param platformProtocolId
     * @return
     */
    PlatformProtocolInfoVo getPlatformProtocolDetail(Long platformProtocolId);
}
