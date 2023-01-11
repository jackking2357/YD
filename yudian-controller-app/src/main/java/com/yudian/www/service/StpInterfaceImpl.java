package com.yudian.www.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yudian.auth.entity.UserType;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.www.entity.sys.*;
import com.yudian.www.service.sys.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 * 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final ISysUserRoleService sysUserRoleService;
    private final ISysRoleService sysRoleService;
    private final ISysUserService sysUserService;
    private final ISysRoleMenuService sysRoleMenuService;
    private final ISysMenuService sysMenuService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 权限集合
        List<String> permissionList = new ArrayList<>();
        // 2. 遍历角色列表，查询拥有的权限码
        for (String roleKey : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleKey);
            List<String> list = roleSession.get("Permission_List", () -> {
                // 从数据库查询这个角色所拥有的权限列表
                SysRole sysRole = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery()
                        .eq(SysRole::getRoleKey, roleKey));
                if (null == sysRole) {
                    return new ArrayList<>();
                }

                // 根据角色id获取绑定的菜单id
                Set<Long> menuIdSet = sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                        .eq(SysRoleMenu::getRoleId, sysRole.getRoleId()))
                        .stream()
                        .map(SysRoleMenu::getMenuId)
                        .collect(Collectors.toSet());
                if (0 == menuIdSet.size()) {
                    return new ArrayList<>();
                }

                // 根据菜单id查询对应的菜单权限标识
                return sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                        .select(SysMenu::getPerms)
                        .in(SysMenu::getMenuId, menuIdSet)
                        .isNotNull(SysMenu::getPerms))
                        .stream()
                        .map(SysMenu::getPerms)
                        .collect(Collectors.toList());
            });
            permissionList.addAll(list);
        }
        log.info("权限集合：{}", JSON.toJSONString(permissionList));
        // 3. 返回权限码集合
        return permissionList;

    }

    /**
     * 返回一个账号所拥有的角色标识集合
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get("Role_List", () -> {
            SysUser sysUser = null;
            if (UserType.SYS_USER.getUserType().equals(LoginUtils.getUserType().getUserType())) {
                sysUser = sysUserService.getById((loginId + "").replaceAll(UserType.SYS_USER.getUserType(), ""));
            }
            if (null == sysUser) {
                return new ArrayList<>();
            }

            // 根据用户id查找绑定的用户角色id
            Set<String> roleIdSet = sysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getUserId, sysUser.getUserId()))
                    .stream()
                    .map(SysUserRole::getRoleId)
                    .map(String::valueOf)
                    .collect(Collectors.toSet());
            if (roleIdSet.isEmpty()) {
                return new ArrayList<>();
            }

            // 查询有开启的角色
            roleIdSet = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                    .select(SysRole::getRoleKey)
                    .in(SysRole::getRoleId, roleIdSet)
                    .eq(SysRole::getRoleStatus, true))
                    .stream()
                    .map(SysRole::getRoleKey)
                    .map(String::valueOf)
                    .collect(Collectors.toSet());
            if (roleIdSet.isEmpty()) {
                return new ArrayList<>();
            }
            log.info("角色集合：{}", JSON.toJSONString(roleIdSet));
            return new ArrayList<>(roleIdSet);
        });
    }

//    /**
//     * 返回一个账号所拥有的权限码集合
//     */
//    @Override
//    public List<String> getPermissionList(Object loginId, String loginType) {
//        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
//        List<String> list = new ArrayList<String>();
//        list.add("101");
//        list.add("user-add");
//        list.add("user-delete");
//        list.add("user-update");
//        list.add("user-get");
//        list.add("article-get");
//        return list;
//    }
//    /**
//     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
//     */
//    @Override
//    public List<String> getRoleList(Object loginId, String loginType) {
//        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
//        List<String> list = new ArrayList<String>();
//        list.add("admin");
//        list.add("super-admin");
//        return list;
//    }

}

