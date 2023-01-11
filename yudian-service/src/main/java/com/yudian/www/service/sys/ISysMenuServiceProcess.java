package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.service.sys.param.AeSysMenuParam;
import com.yudian.www.service.sys.param.GetSysMenuListParam;
import com.yudian.www.service.sys.vo.RouterVo;
import com.yudian.www.service.sys.vo.SysMenuInfoVo;

import java.util.List;
import java.util.Set;

public interface ISysMenuServiceProcess {


    void sysMenuAdd(AeSysMenuParam aeSysMenuParam);


    void sysMenuAddBatch(List<AeSysMenuParam> aeSysMenuParamList);


    void sysMenuEdit(AeSysMenuParam aeSysMenuParam);


    void sysMenuRemove(Long menuId);

    /**
     * 分页查询
     *
     * @param getSysMenuListParam
     * @return
     */
    TableRecordVo<SysMenuInfoVo> getSysMenuList(GetSysMenuListParam getSysMenuListParam);

    /**
     * 明细
     *
     * @param menuId
     * @return
     */
    SysMenuInfoVo getSysMenuDetail(Long menuId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * @param userId 用户id
     * @return
     */
    List<SysMenu> selectMenuList(Long userId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param sysMenuList 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> sysMenuList);

    /**
     * 角色id查询菜单id
     *
     * @param roleId
     * @return
     */
    Set<Long> selectMenuListByRoleId(Long roleId);
}
