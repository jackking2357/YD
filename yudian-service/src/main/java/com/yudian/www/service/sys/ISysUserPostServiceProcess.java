package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysUserPostParam;
import com.yudian.www.service.sys.param.GetSysUserPostListParam;
import com.yudian.www.service.sys.vo.SysUserPostInfoVo;

import java.util.List;

/**
 * 用户与岗位关联表 服务类-流程
 *
 * @since 2022-02-12
 */
public interface ISysUserPostServiceProcess {

    /**
     * 添加
     *
     * @param aeSysUserPostParam
     */
    void sysUserPostAdd(AeSysUserPostParam aeSysUserPostParam);

    /**
     * 批量创建
     *
     * @param aeSysUserPostParamList
     */
    void sysUserPostAddBatch(List<AeSysUserPostParam> aeSysUserPostParamList);

    /**
     * 编辑
     *
     * @param aeSysUserPostParam
     */
    void sysUserPostEdit(AeSysUserPostParam aeSysUserPostParam);

    /**
     * 删除
     *
     * @param userIds
     */
    void sysUserPostRemove(Long[] userIds);

    /**
     * 分页查询
     *
     * @param getSysUserPostListParam
     * @return
     */
    TableRecordVo<SysUserPostInfoVo> getSysUserPostList(GetSysUserPostListParam getSysUserPostListParam);

    /**
     * 明细
     *
     * @param userId
     * @return
     */
    SysUserPostInfoVo getSysUserPostDetail(Long userId);
}
