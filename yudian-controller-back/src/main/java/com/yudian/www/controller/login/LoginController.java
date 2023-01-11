package com.yudian.www.controller.login;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.wf.captcha.ArithmeticCaptcha;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.Constants;
import com.yudian.common.entity.R;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.common.utils.AddressUtils;
import com.yudian.common.utils.ServletUtils;
import com.yudian.www.controller.monitor.vo.SysUserOnlineVo;
import com.yudian.www.entity.sys.SysUser;
import com.yudian.www.service.SysLoginService;
import com.yudian.www.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "系统登录")
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class LoginController {

    private final SysLoginService loginService;
    private final RedisUtils redisUtils;
    private final SaTokenConfig saTokenConfig;
    private final ISysUserService sysUserService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "生成验证码", notes = "生成验证码")
    @GetMapping("/captchaImage")
    public R getCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(160, 60);
        // 获取运算的公式
        captcha.getArithmeticString();
        // 获取运算的结果
        String text = captcha.text();

        // 缓存验证码至Redis
        String uuid = IdUtil.simpleUUID();
        redisUtils.set(Constants.CAPTCHA_CODE_KEY + uuid, text, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("uuid", uuid);
        resultMap.put("img", captcha.toBase64());
        return R.ok(resultMap);
    }

    @ApiOperationSupport(order = 2)
    @DynamicParameters(name = "sysUserDeleteParam", properties = {
            @DynamicParameter(name = "username", value = "账号", required = true),
            @DynamicParameter(name = "password", value = "密码", required = true),
            @DynamicParameter(name = "code", value = "验证码", required = true),
            @DynamicParameter(name = "uuid", value = "验证码对应标识", required = true)
    })
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public R login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String code = jsonObject.getString("code");
        String uuid = jsonObject.getString("uuid");
        // 生成令牌
        String token = loginService.login(username, password, code, uuid);
        Map<String, Object> resultMap = new HashMap<>(1);
        resultMap.put(Constants.TOKEN, token);

        // 系统账户登录
        UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = ServletUtils.getClientIP();
        String tokenValue = StpUtil.getTokenValue();
        SysUser user = sysUserService.getById(LoginUtils.getUserId());
        SysUserOnlineVo userOnlineDTO = new SysUserOnlineVo()
                .setIpaddr(ip)
                .setLoginLocation(AddressUtils.getRealAddressByIP(ip))
                .setBrowser(userAgent.getBrowser().getName())
                .setOs(userAgent.getOs().getName())
                .setLoginTime(System.currentTimeMillis())
                .setTokenId(tokenValue)
                .setUserName(user.getUserName());
        redisUtils.set(Constants.ONLINE_TOKEN_KEY + tokenValue, userOnlineDTO, saTokenConfig.getTimeout(), TimeUnit.SECONDS);
        return R.ok(resultMap);
    }

    @ApiOperation("登出方法")
    @PostMapping("/logout")
    public R logout() {
        try {
            StpUtil.logout();
        } catch (NotLoginException e) {
        }
        return R.ok(null, "退出成功");
    }
}
