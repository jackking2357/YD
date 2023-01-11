package com.yudian.www.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.entity.account.*;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.service.account.*;
import com.yudian.www.service.account.param.AeAccountExtractParam;
import com.yudian.www.service.account.vo.AccountCertInfoVo;
import com.yudian.www.service.account.vo.AccountInfoVo;
import com.yudian.www.service.account.vo.AccountTeamInfoVo;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Api(tags = "用户信息")
@RestController
@RequestMapping("/login-next")
@RequiredArgsConstructor
public class LoginNextController {

    private final IAccountService accountService;
    private final IAccountOutinService accountOutinService;
    private final IAccountExtractService accountExtractService;
    private final IAccountCertService accountCertService;
    private final IAccountRobotService accountRobotService;
    private final IAccountServiceProcess accountServiceProcess;
    private final IAccountExtractServiceProcess accountExtractServiceProcess;
    private final IAccountRobotAcceleratorService accountRobotAcceleratorService;
    private final IRobotAcceleratorService robotAcceleratorService;
    public static Pattern pwd = Pattern.compile("[a-zA-Z0-9]{6,20}");


    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "acceleratorCount", value = "加速器数量", dataTypeClass = Integer.class),
            @DynamicParameter(name = "acceleratorRate", value = "加速比率", dataTypeClass = String.class),
            @DynamicParameter(name = "acceleratorDate", value = "最大有效期日期", dataTypeClass = String.class),
            @DynamicParameter(name = "robotCount", value = "机器人数量", dataTypeClass = Integer.class),
    })
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "首页信息", notes = "首页信息")
    @GetMapping("/index")
    public R<Map<String, Object>> index() {
        Map<String, Object> resultMap = new HashMap<>();
        Long userId = LoginUtils.getUserId();
        List<AccountRobotAccelerator> accountRobotAccelerators = accountRobotAcceleratorService.list(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                .ge(AccountRobotAccelerator::getExpirationTime, LocalDateTime.now())
                .eq(AccountRobotAccelerator::getAccountId, userId));
        LocalDateTime localDateTime = accountRobotAccelerators.stream()
                .map(AccountRobotAccelerator::getExpirationTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        Map<Long, RobotAccelerator> robotAcceleratorMap = robotAcceleratorService.list()
                .stream()
                .collect(Collectors.toMap(RobotAccelerator::getRobotAcceleratorId, robotAccelerator -> robotAccelerator));

        BigDecimal acceleratorRate = accountRobotAccelerators.stream()
                .map(accountRobotAccelerator -> {
                    if (robotAcceleratorMap.containsKey(accountRobotAccelerator.getRobotAcceleratorId())) {
                        return robotAcceleratorMap.get(accountRobotAccelerator.getRobotAcceleratorId()).getAcceleratorRate();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int robotCount = accountRobotService.count(Wrappers.<AccountRobot>lambdaQuery()
                .ge(AccountRobot::getExpirationTime, LocalDateTime.now())
                .eq(AccountRobot::getAccountId, userId));
        resultMap.put("acceleratorCount", accountRobotAccelerators.size());
        resultMap.put("acceleratorRate", acceleratorRate.setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "%");
        resultMap.put("acceleratorDate", "????-??-??");
        if (null != localDateTime) {
            resultMap.put("acceleratorDate", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        resultMap.put("robotCount", robotCount);
        return R.ok(resultMap);
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/getInfo")
    public R<AccountInfoVo> getInfo() {
        Account account = accountService.getById(LoginUtils.getUserId());
        AccountInfoVo sysAccountInfoVo = new AccountInfoVo();
        BeanUtils.copyProperties(account, sysAccountInfoVo);

        List<AccountOutin> accountOutins = accountOutinService.list(Wrappers.<AccountOutin>lambdaQuery()
                .select(AccountOutin::getOutinDate, AccountOutin::getOutinAmount)
                .eq(AccountOutin::getAccountId, account.getAccountId()));

        BigDecimal todayIncome = accountOutins.stream()
                .filter(accountOutin -> accountOutin.getOutinAmount().compareTo(BigDecimal.ZERO) > 0)
                .filter(accountOutin -> accountOutin.getOutinDate().equals(LocalDate.now()))
                .map(AccountOutin::getOutinAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumIncome = accountOutins.stream()
                .filter(accountOutin -> accountOutin.getOutinAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(AccountOutin::getOutinAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sysAccountInfoVo.setTodayIncome(todayIncome);
        sysAccountInfoVo.setSumIncome(sumIncome);
        return R.ok(sysAccountInfoVo);
    }

    @ApiOperationSupport(order = 2)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "oldLoginPwd", value = "旧登录密码", required = true, dataTypeClass = String.class),
            @DynamicParameter(name = "newLoginPwd", value = "新登录密码", required = true, dataTypeClass = String.class),
    })
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/editLoginPwd")
    public R editLoginPwd(@RequestBody JSONObject jsonObject) {
        String oldLoginPwd = jsonObject.getString("oldLoginPwd");
        String newLoginPwd = jsonObject.getString("newLoginPwd");
        if (StringUtils.isBlank(oldLoginPwd)) {
            return R.error(null, "请输入旧密码");
        }
        if (StringUtils.isBlank(newLoginPwd) || newLoginPwd.length() < 6 || newLoginPwd.length() > 20) {
            return R.error(null, "登录密码长度范围6-20位");
        }
        if (!pwd.matcher(newLoginPwd).matches()) {
            return R.error(null, "登录密码只能输入数字跟字母且长度范围6-20位");
        }
        newLoginPwd = newLoginPwd.trim();
        accountServiceProcess.editLoginPwd(oldLoginPwd, newLoginPwd);
        return R.ok(null, "修改成功");
    }

    @ApiOperationSupport(order = 3)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "zfbQrCode", value = "支付宝二维码", required = true, dataTypeClass = String.class),
            @DynamicParameter(name = "zfbUsername", value = "支付宝账号", required = true, dataTypeClass = String.class),
    })
    @ApiOperation(value = "修改支付宝", notes = "修改支付宝")
    @PostMapping("/editZfb")
    public R editZfb(@RequestBody JSONObject jsonObject) {
        String zfbQrCode = jsonObject.getString("zfbQrCode");
        String zfbUsername = jsonObject.getString("zfbUsername");
        if (StringUtils.isBlank(zfbQrCode) || StringUtils.isBlank(zfbUsername)) {
            return R.error(null, "参数错误");
        }

        int count = accountExtractService.count(Wrappers.<AccountExtract>lambdaQuery()
                .eq(AccountExtract::getReviewStatus, 0)
                .eq(AccountExtract::getAccountId, LoginUtils.getUserId()));
        if (0 != count) {
            return R.error(null, "账户提取金额中，无法修改支付宝");
        }

        accountService.update(Wrappers.<Account>lambdaUpdate()
                .set(Account::getZfbQrCode, zfbQrCode)
                .set(Account::getZfbUsername, zfbUsername)
                .eq(Account::getAccountId, LoginUtils.getUserId())
        );
        return R.ok(null, "操作成功");
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "获取认证明细", notes = "获取认证明细")
    @GetMapping("/getCertDetail")
    public R<AccountCertInfoVo> getCertDetail() {
        Long userId = LoginUtils.getUserId();
        AccountCert accountCert = accountCertService.getOne(Wrappers.<AccountCert>lambdaQuery().eq(AccountCert::getAccountId, userId));
        if (null == accountCert) {
            return R.ok(null, "操作成功");
        }
        AccountCertInfoVo accountCertInfoVo = new AccountCertInfoVo();
        BeanUtils.copyProperties(accountCert, accountCertInfoVo);
        return R.ok(accountCertInfoVo, "操作成功");
    }

    @ApiOperationSupport(order = 5)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "certPhotoFront", value = "正面", required = true, dataTypeClass = String.class),
            @DynamicParameter(name = "certPhotoReverse", value = "反面", required = true, dataTypeClass = String.class),
    })
    @ApiOperation(value = "提交认证", notes = "提交认证")
    @PostMapping("/subCert")
    public R subCert(@RequestBody JSONObject jsonObject) {
        String certPhotoFront = jsonObject.getString("certPhotoFront");
        String certPhotoReverse = jsonObject.getString("certPhotoReverse");
        if (StringUtils.isBlank(certPhotoFront) || StringUtils.isBlank(certPhotoReverse)) {
            return R.error(null, "参数错误");
        }
        AccountCert accountCert = accountCertService.getOne(Wrappers.<AccountCert>lambdaQuery()
                .eq(AccountCert::getAccountId, LoginUtils.getUserId()));

        if (null == accountCert) {
            accountCert = new AccountCert();
            accountCert.setAccountId(LoginUtils.getUserId());
            accountCert.setCertPhotoFront(certPhotoFront);
            accountCert.setCertPhotoReverse(certPhotoReverse);
            accountCert.setReviewStatus(0);
            accountCert.setReviewRemark("");
            accountCertService.save(accountCert);
            return R.ok(null, "操作成功");
        }
        Integer reviewStatus = accountCert.getReviewStatus();
        if (2 != reviewStatus) {
            return R.ok(null, "非拒绝状态，无法再次提交认证");
        }
        accountCertService.update(Wrappers.<AccountCert>lambdaUpdate()
                .set(AccountCert::getCertPhotoFront, certPhotoFront)
                .set(AccountCert::getCertPhotoReverse, certPhotoReverse)
                .set(AccountCert::getReviewStatus, 0)
                .eq(AccountCert::getAccountCertId, accountCert.getAccountCertId())
        );

        accountService.update(Wrappers.<Account>lambdaUpdate()
                .set(Account::getCertStatus, 0)
                .eq(Account::getAccountId, accountCert.getAccountId())
        );
        return R.ok(null, "操作成功");
    }

    @ApiOperation(value = "金额提取", notes = "金额提取", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountExtractAdd")
    public R accountExtractAdd(@Valid @RequestBody AeAccountExtractParam aeAccountExtractParam) {
        Account account = accountService.getById(LoginUtils.getUserId());
        if (StringUtils.isBlank(account.getZfbUsername()) ||
                StringUtils.isBlank(account.getZfbQrCode())) {
            return R.error(null, "收款配置不全，无法提取");
        }

        if (account.getCertStatus() != 1) {
            return R.error(null, "实名认证未通过，无法提取");
        }

        aeAccountExtractParam.setAccountId(LoginUtils.getUserId());
        aeAccountExtractParam.setReviewStatus(0);
        aeAccountExtractParam.setReviewRemark(null);
        accountExtractServiceProcess.accountExtractAdd(aeAccountExtractParam);
        return R.ok(null, "新增成功");
    }


    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "teamRobotCount", value = "团队机器总人数", dataTypeClass = String.class),
            @DynamicParameter(name = "accountTeamInfoVo", value = "团队信息", dataTypeClass = AccountTeamInfoVo.class),
    })
    @ApiOperation(value = "我的团队", notes = "我的团队", tags = {})
    @ApiOperationSupport(order = 7)
    @GetMapping("/accountMyTeam")
    public R accountMyTeam() {
        List<Account> accounts = accountService.list(Wrappers.<Account>lambdaQuery()
                .select(Account::getAccountId, Account::getPhoneNumber, Account::getNickName, Account::getAvatarUrl, Account::getCreateDatetime)
                .eq(Account::getInviteAccountId, LoginUtils.getUserId()));
        List<Long> accountIds = accounts.stream().map(Account::getAccountId).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("teamRobotCount", 0);
        result.put("accountTeamInfoVos", new ArrayList<>());
        if (accountIds.isEmpty()) {
            return R.ok(result, "获取成功");
        }
        List<AccountRobot> accountRobotList = accountRobotService.list(Wrappers.<AccountRobot>lambdaQuery()
                .select(AccountRobot::getAccountId, AccountRobot::getAccountRobotId)
                .ge(AccountRobot::getExpirationTime, LocalDateTime.now())
                .in(AccountRobot::getAccountId, accountIds));

        Map<Long, List<AccountRobot>> arsMap = accountRobotList.stream()
                .collect(Collectors.groupingBy(AccountRobot::getAccountId));

        List<AccountTeamInfoVo> accountTeamInfoVos = accounts.stream()
                .map(account -> {
                    AccountTeamInfoVo accountTeamInfoVo = new AccountTeamInfoVo();
                    BeanUtils.copyProperties(account, accountTeamInfoVo);
                    accountTeamInfoVo.setAccountRobotCount(arsMap.getOrDefault(account.getAccountId(), new ArrayList<>()).size());
                    return accountTeamInfoVo;
                }).collect(Collectors.toList());

        result.put("teamRobotCount", accountRobotList.size());
        result.put("accountTeamInfoVos", accountTeamInfoVos);
        return R.ok(result, "新增成功");
    }
}
