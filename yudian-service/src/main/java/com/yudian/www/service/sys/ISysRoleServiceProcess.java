package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysRoleParam;
import com.yudian.www.service.sys.param.GetSysRoleListParam;
import com.yudian.www.service.sys.vo.SysRoleInfoVo;

import java.util.List;


public interface ISysRoleServiceProcess {


    void sysRoleAdd(AeSysRoleParam aeSysRoleParam);


    void sysRoleAddBatch(List<AeSysRoleParam> aeSysRoleParamList);


    void sysRoleEdit(AeSysRoleParam aeSysRoleParam);


    void sysRoleRemove(Long[] roleIds);


    TableRecordVo<SysRoleInfoVo> getSysRoleList(GetSysRoleListParam getSysRoleListParam);


    SysRoleInfoVo getSysRoleDetail(Long roleId);


    void changeRoleStatus(Long roleId, Boolean roleStatus);


    void changeDataScope(Long roleId, String dataScope);
}
