package com.yudian.www.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.yudian.common.entity.R;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.platform.PlatformVersion;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.account.IAccountServiceProcess;
import com.yudian.www.service.platform.IPlatformProtocolServiceProcess;
import com.yudian.www.service.platform.IPlatformVersionService;
import com.yudian.www.service.platform.vo.PlatformProtocolInfoVo;
import com.yudian.www.service.platform.vo.PlatformVersionInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Slf4j
@Api(tags = "登录")
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class LoginApiController {

    private final IAccountService accountService;
    private final IAccountServiceProcess accountServiceProcess;
    private final IPlatformVersionService platformVersionService;
    private final IPlatformProtocolServiceProcess platformProtocolServiceProcess;
    public static Pattern pwd = Pattern.compile("[a-zA-Z0-9]{6,20}");
    public static Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]{9}$");


    @ApiOperationSupport(order = 1)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "phoneNumber", value = "手机号码", required = true),
            @DynamicParameter(name = "loginPwd", value = "登录密码", required = true),
            @DynamicParameter(name = "invitationCode", value = "邀请码", required = true),
    })
    @ApiOperation(value = "账户注册", notes = "账户注册")
    @PostMapping("/registered")
    public synchronized R<Map<String, Object>> registered(@RequestBody JSONObject jsonObject) {
        String phoneNumber = jsonObject.getString("phoneNumber");
        String loginPwd = jsonObject.getString("loginPwd");
        String invitationCode = jsonObject.getString("invitationCode");
        if (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(loginPwd)
                || StringUtils.isBlank(invitationCode)) {
            return R.error(null, "参数错误");
        }

        if (!pattern.matcher(phoneNumber).matches()) {
            return R.error(null, "请填写正确的手机号码");
        }
        if (!pwd.matcher(loginPwd).matches()) {
            return R.error(null, "登录密码只能输入数字跟字母且长度范围6-20位");
        }

        int count = accountService.count(Wrappers.<Account>lambdaQuery().eq(Account::getPhoneNumber, phoneNumber));
        if (0 != count) {
            return R.error(null, "账户已存在，请更换");
        }

        Account invitaAccount = accountService.getOne(Wrappers.<Account>lambdaQuery().eq(Account::getInvitationCode, invitationCode));
        if (null == invitaAccount) {
            return R.error(null, "邀请账户不存在，请更换");
        }

        loginPwd = loginPwd.trim();

        String shareCode = createShareCode(5);
        if (null == shareCode) {
            return R.error(null, "当前注册人数过多，请稍后注册");
        }

        Account account = new Account();
        account.setInviteAccountId(invitaAccount.getAccountId());
        account.setPhoneNumber(phoneNumber);
        account.setLoginPwd(SecureUtil.md5(SecureUtil.sha1(loginPwd)));
        account.setNickName(getRandomString(8));
        account.setAvatarUrl("http://yudianrobot.com/yudian/0.png");
        account.setAccountStatus(1);
        account.setScore(BigDecimal.ZERO);
        account.setInvitationCode(shareCode);
        account.setCertStatus(0);
        account.setCreateBy(-1L);
        accountService.save(account);
        return R.ok(null, "注册成功");
    }

    @ApiOperationSupport(order = 4)
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "token", value = "系统token", dataTypeClass = String.class),
            @DynamicParameter(name = "nickName", value = "昵称", dataTypeClass = String.class),
            @DynamicParameter(name = "avatarUrl", value = "头像", dataTypeClass = String.class),
            @DynamicParameter(name = "accountId", value = "用户id", dataTypeClass = String.class),
    })
    @DynamicParameters(properties = {
            @DynamicParameter(name = "phoneNumber", value = "手机号码", required = true),
            @DynamicParameter(name = "loginPwd", value = "登录密码", required = true),
    })
    @ApiOperation(value = "密码登录", notes = "密码登录")
    @PostMapping("/loginEqPwd")
    public R<Map<String, Object>> loginEqPwd(@RequestBody JSONObject jsonObject) {
        String phoneNumber = jsonObject.getString("phoneNumber");
        String loginPwd = jsonObject.getString("loginPwd");
        if (StringUtils.isBlank(loginPwd) || StringUtils.isBlank(phoneNumber)) {
            return R.error(null, "参数错误");
        }
        loginPwd = loginPwd.trim();
        Map<String, Object> resultMap = accountServiceProcess.appLogin(phoneNumber, loginPwd);
        return R.ok(resultMap, "登录成功");
    }


    @ApiOperation(value = "平台协议(明细)", notes = "平台协议(明细)：1=用户协议；2=隐私协议；", tags = {})
    @ApiOperationSupport(order = 6)
    @GetMapping("/getPlatformProtocolDetail/{platformProtocolId}")
    public R<PlatformProtocolInfoVo> getPlatformProtocolDetail(@ApiParam(name = "platformProtocolId", value = "平台协议id", required = true) @PathVariable Long platformProtocolId) {
        PlatformProtocolInfoVo platformProtocolInfoVo = platformProtocolServiceProcess.getPlatformProtocolDetail(platformProtocolId);
        return R.ok(platformProtocolInfoVo, "获取成功");
    }

    @ApiOperation(value = "应用版本最新获取", notes = "应用版本最新获取", tags = {})
    @ApiOperationSupport(order = 8)
    @GetMapping("/getPlatformVersion/{versionSystem}")
    public R<PlatformVersionInfoVo> getPlatformVersion(@ApiParam(name = "versionSystem", value = "版本系统：1=安卓；2=IOS", required = true) @PathVariable Long versionSystem) {
        if (1 != versionSystem && 2 != versionSystem) {
            throw new ServiceException("系统类型错误");
        }
        PlatformVersion platformVersion = platformVersionService.getOne(Wrappers.<PlatformVersion>lambdaQuery()
                .eq(PlatformVersion::getVersionSystem, versionSystem)
                .orderByDesc(PlatformVersion::getVersionCode)
                .last("limit 1"));
        if (null == platformVersion) {
            return R.ok(null, "获取成功");
        }
        PlatformVersionInfoVo platformVersionInfoVo = new PlatformVersionInfoVo();
        BeanUtils.copyProperties(platformVersion, platformVersionInfoVo);
        return R.ok(platformVersionInfoVo, "获取成功");
    }

    @ApiOperationSupport(order = 99)
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        try {
            StpUtil.logout();
        } catch (NotLoginException e) {
        }
        return R.ok("success");
    }

    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#_";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private String createShareCode(int count) {
        if (--count == 0) {
            return null;
        }
        // 生成邀请码
        String shareCode = createShareCode();
        Account account = accountService.getOne(Wrappers.<Account>lambdaQuery().eq(Account::getInvitationCode, shareCode));
        if (null == account) {
            return shareCode;
        }
        return createShareCode(count);
    }

    private static String createShareCode() {
        int i;
        int count = 0;
        char[] str = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        StringBuilder pwd = new StringBuilder();
        Random r = new Random();
        while (count < 6) {
            i = Math.abs(r.nextInt(str.length));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
