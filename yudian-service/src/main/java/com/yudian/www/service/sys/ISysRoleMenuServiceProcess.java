package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysRoleMenuParam;
import com.yudian.www.service.sys.param.GetSysRoleMenuListParam;
import com.yudian.www.service.sys.vo.SysRoleMenuInfoVo;

import java.util.List;


public interface ISysRoleMenuServiceProcess {


    void sysRoleMenuAdd(AeSysRoleMenuParam aeSysRoleMenuParam);


    void sysRoleMenuAddBatch(List<AeSysRoleMenuParam> aeSysRoleMenuParamList);


    void sysRoleMenuEdit(AeSysRoleMenuParam aeSysRoleMenuParam);


    void sysRoleMenuRemove(Long[] roleIds);


    TableRecordVo<SysRoleMenuInfoVo> getSysRoleMenuList(GetSysRoleMenuListParam getSysRoleMenuListParam);


    SysRoleMenuInfoVo getSysRoleMenuDetail(Long roleId);
}
