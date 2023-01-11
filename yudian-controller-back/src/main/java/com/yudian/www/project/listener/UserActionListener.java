package com.yudian.www.project.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import com.yudian.common.entity.Constants;
import com.yudian.common.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户行为 侦听器的实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionListener implements SaTokenListener {

    private final RedisUtils redisUtils;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {

    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        redisUtils.del(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doLogout, useId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        redisUtils.del(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doLogoutByLoginId, useId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        redisUtils.del(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doReplaced, useId:{}, token:{}", loginId, tokenValue);
    }

    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }

    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }

    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }

    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
    }

    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }


}
