package com.yudian.www.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yudian.auth.config.DeviceType;
import com.yudian.auth.entity.LoginUser;
import com.yudian.auth.entity.UserType;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.Constants;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.common.utils.SecurityUtils;
import com.yudian.common.utils.ServletUtils;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.service.sys.ISysLogininforServiceProcess;
import com.yudian.www.service.sys.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLoginService {

    private final ISysUserService userService;
    private final RedisUtils redisUtils;
    private final ISysLogininforServiceProcess sysLogininforServiceProcess;
    private final SysPermissionService permissionService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        HttpServletRequest request = ServletUtils.getRequest();
        // 验证码校验
        validateCaptcha(username, code, uuid, request);
        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = redisUtils.get(Constants.LOGIN_ERROR + username);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(Constants.LOGIN_ERROR_NUMBER)) {
            sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_FAIL, String.format("密码错误次数过多，帐户锁定{0}分钟", Constants.LOGIN_ERROR_LIMIT_TIME), request);
            throw new ServiceException(String.format("密码错误次数过多，帐户锁定{0}分钟", Constants.LOGIN_ERROR_LIMIT_TIME));
        }

        SysUser user = userService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUserName, username));
        if (null == user) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(username + "不存在");
        } else if (!user.getUserStatus()) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(username + "已被冻结");
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(Constants.LOGIN_ERROR_NUMBER)) {
                redisUtils.set(Constants.LOGIN_ERROR + username, errorNumber, Constants.LOGIN_ERROR_LIMIT_TIME, TimeUnit.MINUTES);
                sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_FAIL, String.format("密码错误次数过多，帐户锁定%d分钟", Constants.LOGIN_ERROR_LIMIT_TIME), request);
                throw new ServiceException(String.format("密码错误次数过多，帐户锁定%d分钟", Constants.LOGIN_ERROR_LIMIT_TIME));
            } else {
                // 未达到规定错误次数 则递增
                redisUtils.set(Constants.LOGIN_ERROR + username, errorNumber);
                sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_FAIL, String.format("密码输入错误%d次", errorNumber), request);
                throw new ServiceException(String.format("密码输入错误%d次", errorNumber));
            }
        }

        // 登录成功 清空错误次数
        redisUtils.del(Constants.LOGIN_ERROR + username);
        sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功", request);
        recordLoginInfo(user.getUserId());

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
//        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setMenuPermission(permissionService.getMenuPermission(user));
        loginUser.setRolePermission(permissionService.getRolePermission(user));

        // 生成token
        if (user.getUserType() == 1) {
            LoginUtils.loginByDevice(loginUser, UserType.SYS_USER, DeviceType.PC);
        }
        return StpUtil.getTokenValue();
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid, HttpServletRequest request) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisUtils.get(verifyKey);
        redisUtils.del(verifyKey);
        if (captcha == null) {
            sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码已过期", request);
            throw new ServiceException("验证码已过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            sysLogininforServiceProcess.recordLogininfor(username, Constants.LOGIN_FAIL, "验证码错误", request);
            throw new ServiceException("验证码错误");
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        userService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getLoginIp, ServletUtils.getClientIP())
                .set(SysUser::getLoginDate, LocalDateTime.now())
                .eq(SysUser::getUserId, userId));
    }
}
