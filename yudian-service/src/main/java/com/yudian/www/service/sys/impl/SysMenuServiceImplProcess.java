package com.yudian.www.service.sys.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yudian.common.entity.Constants;
import com.yudian.common.entity.UserConstants;
import com.yudian.common.utils.SecurityUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.entity.sys.SysRole;
import com.yudian.www.entity.sys.SysRoleMenu;
import com.yudian.www.entity.sys.SysUserRole;
import com.yudian.www.service.sys.*;
import com.yudian.www.service.sys.param.AeSysMenuParam;
import com.yudian.www.service.sys.param.GetSysMenuListParam;
import com.yudian.www.service.sys.vo.MetaVo;
import com.yudian.www.service.sys.vo.RouterVo;
import com.yudian.www.service.sys.vo.SysMenuInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 菜单权限表 服务实现类-流程
 *

 * @since 2022-01-06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImplProcess implements ISysMenuServiceProcess {

    private final ISysMenuService sysMenuService;
    private final ISysRoleService sysRoleService;
    private final ISysUserRoleService sysUserRoleService;
    private final ISysRoleMenuService sysRoleMenuService;

    /**
     * 添加
     *
     * @param aeSysMenuParam
     */
    @Override
    public void sysMenuAdd(AeSysMenuParam aeSysMenuParam) {
        String ancestors = "0";
        if (0 != aeSysMenuParam.getParentId()) {
            SysMenu parentSysMenu = sysMenuService.getById(aeSysMenuParam.getParentId());
            if (null == parentSysMenu) {
                throw new ServiceException("父级菜单不存在");
            }
            ancestors = parentSysMenu.getAncestors();
        }

        SysMenu sysMenu = new SysMenu();
        if ("M".equals(aeSysMenuParam.getMenuType())) {
            aeSysMenuParam.setParentId(0L);
        }
        BeanUtils.copyProperties(aeSysMenuParam, sysMenu);
        sysMenu.setAncestors(ancestors);
        if (0 != sysMenu.getParentId()) {
            sysMenu.setAncestors(ancestors + "," + sysMenu.getParentId());
        }
        boolean save = sysMenuService.save(sysMenu);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysMenuAddBatch(List<AeSysMenuParam> aeSysMenuParamList) {
        List<SysMenu> sysMenuList = aeSysMenuParamList.stream()
                .map(aeSysMenuParam -> {
                    SysMenu sysMenu = new SysMenu();
                    BeanUtils.copyProperties(aeSysMenuParam, sysMenu);
                    return sysMenu;
                }).collect(Collectors.toList());
        boolean save = sysMenuService.saveBatch(sysMenuList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysMenuParam
     */
    @Override
    public void sysMenuEdit(AeSysMenuParam aeSysMenuParam) {
        SysMenu sysMenu = sysMenuService.getById(aeSysMenuParam.getMenuId());
        if (null == sysMenu) {
            throw new ServiceException("记录不存在");
        }
        if (sysMenu.getMenuId().equals(aeSysMenuParam.getParentId())) {
            throw new ServiceException("修改菜单" + sysMenu.getMenuName() + "失败，上级菜单不能选择自己");
        }
        String ancestors = "0";
        if (0 != aeSysMenuParam.getParentId()) {
            SysMenu parentSysMenu = sysMenuService.getById(aeSysMenuParam.getParentId());
            if (null == parentSysMenu) {
                throw new ServiceException("父级菜单不存在");
            }
            ancestors = parentSysMenu.getAncestors();
        }

        BeanUtils.copyProperties(aeSysMenuParam, sysMenu);
        sysMenu.setAncestors(ancestors);
        if (0 != sysMenu.getParentId()) {
            sysMenu.setAncestors(ancestors + "," + sysMenu.getParentId());
        }

        boolean update = sysMenuService.updateById(sysMenu);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param menuId
     */
    @Override
    public void sysMenuRemove(Long menuId) {
        SysMenu sysMenu = sysMenuService.getById(menuId);
        if (null == sysMenu) {
            throw new ServiceException("记录不存在");
        }
        if (1 == sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, menuId).last(Constants.LIMIT1)).size()) {
            throw new ServiceException("存在下级菜单,不允许删除");
        }
        boolean remove = sysMenuService.removeById(menuId);
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysMenuListParam
     * @return
     */
    @Override
    public TableRecordVo<SysMenuInfoVo> getSysMenuList(GetSysMenuListParam getSysMenuListParam) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().lambda();
        queryWrapper.eq(null != getSysMenuListParam.getMenuStatus(), SysMenu::getMenuStatus, getSysMenuListParam.getMenuStatus())
                .like(StringUtils.isNotBlank(getSysMenuListParam.getMenuName()), SysMenu::getMenuName, getSysMenuListParam.getMenuName());

        List<SysMenu> sysMenuList = sysMenuService.list(queryWrapper);

        if (0 != sysMenuList.size()) {
            if (null != getSysMenuListParam.getMenuStatus() || StringUtils.isNotBlank(getSysMenuListParam.getMenuName())) {
                String ancestors = sysMenuList.stream().map(SysMenu::getAncestors).collect(Collectors.joining(","));
                // 所有上级id
                Set<Long> idSet = Stream.of(ancestors.split(",")).map(Long::parseLong).collect(Collectors.toSet());
                // 已经查到的id
                Set<Long> exitIdSet = sysMenuList.stream().map(SysMenu::getMenuId).collect(Collectors.toSet());
                // 排除已经查到的 = 未查出来的
                Set<Long> noExitIdSet = idSet.stream().filter(id -> !exitIdSet.contains(id)).collect(Collectors.toSet());
                if (0 != noExitIdSet.size()) {
                    sysMenuList.addAll(sysMenuService.listByIds(noExitIdSet));
                }
            }
        }
        List<SysMenuInfoVo> sysMenuInfoVoList = sysMenuList.stream()
                .map(sysMenu -> {
                    SysMenuInfoVo sysMenuInfoVo = new SysMenuInfoVo();
                    BeanUtils.copyProperties(sysMenu, sysMenuInfoVo);
                    return sysMenuInfoVo;
                })
                .sorted(Comparator.comparing(SysMenuInfoVo::getMenuSort))
                .collect(Collectors.toList());

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysMenuInfoVoList);
        tableRecordVo.setTotal(null);
        tableRecordVo.setPages(null);
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param menuId
     * @return
     */
    @Override
    public SysMenuInfoVo getSysMenuDetail(Long menuId) {
        SysMenu sysMenu = sysMenuService.getById(menuId);
        if (null == sysMenu) {
            throw new ServiceException("记录不存在");
        }
        List<SysMenuInfoVo> sysMenuInfoVos = this.entityToVo(Arrays.asList(sysMenu));
        return sysMenuInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                    .in(SysMenu::getMenuType, Arrays.asList("M", "C"))
                    .eq(SysMenu::getMenuStatus, 0)
                    .eq(SysMenu::getVisible, 0)
                    .orderByAsc(SysMenu::getParentId, SysMenu::getMenuSort));
        } else {
            List<SysUserRole> surList = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, userId));
            if (surList.isEmpty()) {
                return getChildPerms(menus, 0);
            }

            Set<Long> srIdSet = surList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            List<Long> roleIdList = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                    .in(SysRole::getRoleId, srIdSet)
                    .eq(SysRole::getRoleStatus, true))
                    .stream()
                    .map(SysRole::getRoleId)
                    .collect(Collectors.toList());
            if (roleIdList.isEmpty()) {
                return getChildPerms(menus, 0);
            }

            Set<Long> mIdSet = sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                    .in(SysRoleMenu::getRoleId, roleIdList))
                    .stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
            if (mIdSet.isEmpty()) {
                return getChildPerms(menus, 0);
            }
            menus = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                    .in(SysMenu::getMenuId, mIdSet)
                    .in(SysMenu::getMenuType, Arrays.asList("M", "C"))
                    .eq(SysMenu::getMenuStatus, 0)
                    .eq(SysMenu::getVisible, 0)
                    .orderByAsc(SysMenu::getParentId, SysMenu::getMenuSort));
        }
        return getChildPerms(menus, 0);
    }

    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
            List<SysMenu> cMenus = (List<SysMenu>) menu.getChildren();
            if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/inner");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().lambda();
        if (null != userId && 1 == userId) {
            // 说明是超级管理员
            return sysMenuService.list(queryWrapper);
        } else {
            List<SysUserRole> sysUserRoles = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, userId));
            if (sysUserRoles.isEmpty()) {
                return new ArrayList<>();
            }
            Set<Long> roleIdSet = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            Set<Long> menuIdSet = selectMenuListByRoleId(roleIdSet.stream().findFirst().orElse(null));
            if (menuIdSet.isEmpty()) {
                return new ArrayList<>();
            }
            return sysMenuService.listByIds(menuIdSet);
        }
    }

    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> sysMenuList) {
        if (sysMenuList.isEmpty()) {
            return new ArrayList<>();
        }
        List<SysMenu> menuTrees = buildTree(sysMenuList);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Set<Long> selectMenuListByRoleId(Long roleId) {
        if (null == roleId) {
            return new HashSet<>();
        }
        // 根据角色id获取菜单id列表
        return sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId))
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysMenuInfoVo> entityToVo(List<SysMenu> records) {
        return records.stream()
                .map(sysMenu -> {
                    SysMenuInfoVo sysMenuInfoVo = new SysMenuInfoVo();
                    BeanUtils.copyProperties(sysMenu, sysMenuInfoVo);
                    return sysMenuInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysMenuParam
     */
    private void checkParam(AeSysMenuParam aeSysMenuParam) {

    }


    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType()) && 1 == menu.getIsFrame()) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType()) && menu.getIsFrame() == 1;
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame() == 1 && Validator.isUrl(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        if (null == list) {
            return returnList;
        }
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<SysMenu> buildTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        Set<Long> tempSet = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toSet());

        for (SysMenu menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempSet.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        for (SysMenu n : list) {
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS},
                new String[]{"", ""});
    }
}