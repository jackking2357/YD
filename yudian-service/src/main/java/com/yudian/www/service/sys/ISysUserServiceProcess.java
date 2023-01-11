package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.service.sys.param.AeSysUserParam;
import com.yudian.www.service.sys.param.GetSysUserListParam;

import java.util.List;

public interface ISysUserServiceProcess {

    /**
     * 添加
     *
     * @param aeSysUserParam
     */
    void sysUserAdd(AeSysUserParam aeSysUserParam);


    /**
     * 批量创建
     *
     * @param aeSysUserParamList
     */
    void sysUserAddBatch(List<AeSysUserParam> aeSysUserParamList);

    /**
     * 编辑
     *
     * @param aeSysUserParam
     */
    void sysUserEdit(AeSysUserParam aeSysUserParam);


    /**
     * 删除
     *
     * @param userIds
     */
    void sysUserRemove(Integer userType, Long[] userIds);

    /**
     * 分页查询
     *
     * @param getSysUserListParam
     * @return
     */
    TableRecordVo getSysUserList(Integer userType, GetSysUserListParam getSysUserListParam);

    /**
     * 实体类转vo
     *
     * @param userType
     * @param records
     * @return
     */
    List entityToVo(Integer userType, List<SysUser> records);

    /**
     * 明细
     *
     * @param userId
     * @return
     */
    Object getSysUserDetail(Integer userType, Long userId);

    /**
     * 重置登录密码
     *
     * @param userId
     * @param password
     */
    void resetUserPwd(Integer userType, Long userId, String encryptPassword);
}
