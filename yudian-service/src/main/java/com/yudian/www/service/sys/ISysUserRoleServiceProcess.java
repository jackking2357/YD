package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysUserRoleParam;
import com.yudian.www.service.sys.param.GetSysUserRoleListParam;
import com.yudian.www.service.sys.vo.SysUserRoleInfoVo;

import java.util.List;

public interface ISysUserRoleServiceProcess {

    /**
     * 添加
     *
     * @param aeSysUserRoleParam
     */
    void sysUserRoleAdd(AeSysUserRoleParam aeSysUserRoleParam);

    /**
     * 批量创建
     *
     * @param aeSysUserRoleParamList
     */
    void sysUserRoleAddBatch(List<AeSysUserRoleParam> aeSysUserRoleParamList);

    /**
     * 编辑
     *
     * @param aeSysUserRoleParam
     */
    void sysUserRoleEdit(AeSysUserRoleParam aeSysUserRoleParam);

    /**
     * 删除
     *
     * @param userIds
     */
    void sysUserRoleRemove(Long[] userIds);

    /**
     * 分页查询
     *
     * @param getSysUserRoleListParam
     * @return
     */
    TableRecordVo<SysUserRoleInfoVo> getSysUserRoleList(GetSysUserRoleListParam getSysUserRoleListParam);

    /**
     * 明细
     *
     * @param userId
     * @return
     */
    SysUserRoleInfoVo getSysUserRoleDetail(Long userId);
}
