package com.yudian.www.controller.login;

import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.service.SysPermissionService;
import com.yudian.www.service.sys.ISysMenuServiceProcess;
import com.yudian.www.service.sys.ISysUserService;
import com.yudian.www.service.sys.vo.RouterVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(tags = "登录后初始化")
@RestController
@RequestMapping("/login-next")
@RequiredArgsConstructor
public class LoginNextController {

    private final SysPermissionService sysPermissionService;
    private final ISysMenuServiceProcess sysMenuServiceProcess;
    private final ISysUserService sysUserService;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/getInfo")
    public R getInfo() {
        SysUser user = sysUserService.getById(LoginUtils.getUserId());
        user.setPassword(null);
        // 角色集合
        Set<String> roles = sysPermissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = sysPermissionService.getMenuPermission(user);
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("user", user);
        resultMap.put("roles", roles);
        resultMap.put("permissions", permissions);
        return R.ok(resultMap);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @ApiOperation("获取路由信息")
    @GetMapping("/getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginUtils.getUserId();
        List<SysMenu> menus = sysMenuServiceProcess.selectMenuTreeByUserId(userId);
        return R.ok(sysMenuServiceProcess.buildMenus(menus));
    }
}
