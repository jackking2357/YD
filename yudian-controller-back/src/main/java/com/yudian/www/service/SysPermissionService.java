package com.yudian.www.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yudian.www.entity.sys.*;
import com.yudian.www.service.sys.ISysMenuService;
import com.yudian.www.service.sys.ISysRoleMenuService;
import com.yudian.www.service.sys.ISysRoleService;
import com.yudian.www.service.sys.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysPermissionService {

    private final ISysRoleService sysRoleService;
    private final ISysMenuService sysMenuService;
    private final ISysUserRoleService sysUserRoleService;
    private final ISysRoleMenuService sysRoleMenuService;

    /**
     * 获取角色数据权限
     *
     * @param sysUser 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser sysUser) {
        Set<String> rolePermissions = new HashSet<String>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            rolePermissions.add("admin");
        } else {
            // 根据用户id查找绑定的用户角色id
            Set<Long> roleIdSet = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, sysUser.getUserId()))
                    .stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toSet());
            if (0 == roleIdSet.size()) {
                return rolePermissions;
            }

            // 查询有开启的角色
            roleIdSet = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                    .select(SysRole::getRoleId)
                    .in(SysRole::getRoleId, roleIdSet)
                    .eq(SysRole::getRoleStatus, true))
                    .stream()
                    .map(SysRole::getRoleId)
                    .collect(Collectors.toSet());
            if (0 == roleIdSet.size()) {
                return rolePermissions;
            }

            // 根据角色id获取绑定的菜单id
            Set<Long> menuIdSet = sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                    .in(SysRoleMenu::getRoleId, roleIdSet))
                    .stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
            if (0 == menuIdSet.size()) {
                return rolePermissions;
            }

            // 根据菜单id查询对应的菜单权限标识
            rolePermissions = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                    .select(SysMenu::getPerms)
                    .in(SysMenu::getMenuId, menuIdSet)
                    .ne(SysMenu::getMenuType, 3)
                    .isNotNull(SysMenu::getPerms))
                    .stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toSet());
        }
        return rolePermissions;
    }

    /**
     * 获取菜单数据权限
     *
     * @param sysUser 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser sysUser) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            perms.add("*:*:*");
        } else {
            // 根据用户id查找绑定的用户角色id
            Set<Long> roleIdSet = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, sysUser.getUserId()))
                    .stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toSet());
            if (0 == roleIdSet.size()) {
                return perms;
            }

            // 查询有开启的角色
            roleIdSet = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                    .select(SysRole::getRoleId)
                    .in(SysRole::getRoleId, roleIdSet)
                    .eq(SysRole::getRoleStatus, true))
                    .stream()
                    .map(SysRole::getRoleId)
                    .collect(Collectors.toSet());
            if (0 == roleIdSet.size()) {
                return perms;
            }

            // 根据角色id获取绑定的菜单id
            Set<Long> menuIdSet = sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                    .in(SysRoleMenu::getRoleId, roleIdSet))
                    .stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
            if (0 == menuIdSet.size()) {
                return perms;
            }

            // 根据菜单id查询对应的菜单权限标识
            perms = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                    .select(SysMenu::getPerms)
                    .in(SysMenu::getMenuId, menuIdSet)
                    .isNotNull(SysMenu::getPerms))
                    .stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toSet());
        }
        return perms;
    }
}
