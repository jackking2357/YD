package com.yudian.www.service.platform;

import com.yudian.www.service.platform.param.AePlatformVersionParam;
import com.yudian.www.service.platform.param.GetPlatformVersionListParam;
import com.yudian.www.service.platform.vo.PlatformVersionInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 平台版本 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface IPlatformVersionServiceProcess {

    /**
     * 添加
     * @param aePlatformVersionParam
     */
    void platformVersionAdd(AePlatformVersionParam aePlatformVersionParam);

    /**
     * 批量创建
     *
     * @param aePlatformVersionParamList
     */
    void platformVersionAddBatch(List<AePlatformVersionParam> aePlatformVersionParamList);

    /**
     * 编辑
     * @param aePlatformVersionParam
     */
    void platformVersionEdit(AePlatformVersionParam aePlatformVersionParam);

    /**
     * 删除
     * @param platformVersionIds
     */
    void platformVersionRemove(Integer[] platformVersionIds);

    /**
     * 分页查询
     * @param getPlatformVersionListParam
     * @return
     */
    TableRecordVo<PlatformVersionInfoVo> getPlatformVersionList(GetPlatformVersionListParam getPlatformVersionListParam);

    /**
     * 明细
     * @param platformVersionId
     * @return
     */
    PlatformVersionInfoVo getPlatformVersionDetail(Integer platformVersionId);
}
