package com.yudian.auth.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ObjectUtil;
import com.yudian.auth.config.DeviceType;
import com.yudian.auth.entity.LoginUser;
import com.yudian.auth.entity.UserType;
import org.apache.commons.lang3.StringUtils;

public class LoginUtils {

    private final static String LOGIN_USER_KEY = "loginUser";

    
    public static void login(LoginUser loginUser, UserType userType) {
        StpUtil.login(userType.getUserType() + loginUser.getUserId());
        setLoginUser(loginUser);
    }

    
    public static void loginByDevice(LoginUser loginUser, UserType userType, DeviceType deviceType) {
        StpUtil.login(userType.getUserType() + loginUser.getUserId(), deviceType.getDevice());
        setLoginUser(loginUser);
    }

    
    public static LoginUser getLoginUser() {
        return (LoginUser) StpUtil.getTokenSession().get(LOGIN_USER_KEY);
    }

    
    public static void setLoginUser(LoginUser loginUser) {
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        if (ObjectUtil.isNull(loginUser)) {
            String loginId = StpUtil.getLoginIdAsString();
            String userId;
            String replace = "";
            if (StringUtils.contains(loginId, UserType.SYS_USER.getUserType())) {
                userId = StringUtils.replace(loginId, UserType.SYS_USER.getUserType(), replace);
            } else if (StringUtils.contains(loginId, UserType.SYS_ACCOUNT.getUserType())) {
                userId = StringUtils.replace(loginId, UserType.SYS_ACCOUNT.getUserType(), replace);
            } else {
                throw new UtilException("登录用户: LoginId异常 => " + loginId);
            }
            return Long.parseLong(userId);
        }
        return loginUser.getUserId();
    }








    
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    
    public static UserType getUserType() {
        String loginId = StpUtil.getLoginIdAsString();
        return getUserType(loginId);
    }

    public static UserType getUserType(Object loginId) {
        if (StringUtils.contains(loginId.toString(), UserType.SYS_USER.getUserType())) {
            return UserType.SYS_USER;
        } else if (StringUtils.contains(loginId.toString(), UserType.SYS_ACCOUNT.getUserType())) {
            return UserType.SYS_ACCOUNT;
        } else {
            throw new UtilException("登录用户: LoginId异常 => " + loginId);
        }
    }

}
