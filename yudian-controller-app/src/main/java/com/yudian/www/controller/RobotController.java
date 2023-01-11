package com.yudian.www.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountRobot;
import com.yudian.www.entity.platform.PlatformWallet;
import com.yudian.www.entity.robot.Robot;
import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.service.account.IAccountRobotService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.platform.IPlatformWalletService;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import com.yudian.www.service.robot.IRobotOrderService;
import com.yudian.www.service.robot.IRobotOrderServiceProcess;
import com.yudian.www.service.robot.IRobotService;
import com.yudian.www.service.robot.IRobotServiceProcess;
import com.yudian.www.service.robot.param.AeRobotOrderParam;
import com.yudian.www.service.robot.vo.RobotInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 机器人 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "机器人")
@RestController
@RequiredArgsConstructor
@RequestMapping("//robot")
public class RobotController {

    private final RedisUtils redisUtils;
    private final IRobotService robotService;
    private final IRobotOrderService robotOrderService;
    private final IAccountService accountService;
    private final IAccountRobotService accountRobotService;
    private final IRobotServiceProcess robotServiceProcess;
    private final IPlatformWalletService platformWalletService;
    private final IRobotOrderServiceProcess robotOrderServiceProcess;

    @ApiOperation(value = "机器人(明细)", notes = "机器人(明细)", tags = {})
    @ApiOperationSupport(order = 1)
    @GetMapping("/getRobotDetail/{robotId}")
    public R<RobotInfoVo> getRobotDetail(@ApiParam(name = "robotId", value = "机器人id", required = true) @PathVariable Long robotId) {
        RobotInfoVo robotInfoVo = robotServiceProcess.getRobotDetail(robotId);
        return R.ok(robotInfoVo, "获取成功");
    }

    @ApiOperation(value = "机器人(购买)", notes = "机器人(购买)", tags = {})
    @DynamicParameters(properties = {
            @DynamicParameter(name = "robotId", value = "机器人id", required = true),
            @DynamicParameter(name = "payCount", value = "购买数量", required = true),
    })
    @ApiOperationSupport(order = 10)
    @PostMapping("/robotBuy")
    public R<PlatformWalletInfoVo> robotBuy(@RequestBody JSONObject jsonObject) {
        Long robotId = jsonObject.getLong("robotId");
        Integer payCount = jsonObject.getInteger("payCount");
        if (null == robotId) {
            return R.error(null, "参数错误");
        }
        if (null == payCount || payCount == 0) {
            return R.error(null, "参数错误");
        }

        Account account = accountService.getById(LoginUtils.getUserId());
        if (account.getCertStatus() != 1) {
            return R.error(null, "实名认证未通过，无法购买");
        }


        Long userId = LoginUtils.getUserId();
        int count = accountRobotService.count(Wrappers.<AccountRobot>lambdaQuery()
                .eq(AccountRobot::getAccountId, userId));
        if (0 == count) {
            // 首次购买3台起, 上限10台
            if (payCount < 3 || payCount > 10) {
                return R.error(null, "首次购买3台起，上限10台");
            }
        }
        if (10 - count < payCount) {
            return R.error(null, "剩余可最大购买数量为：" + (10 - count));
        }

        // 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
        int waitCount = robotOrderService.count(Wrappers.<RobotOrder>lambdaQuery()
                .in(RobotOrder::getOrderStatus, Arrays.asList(1, 4))
                .eq(RobotOrder::getAccountId, userId)
                .isNotNull(RobotOrder::getRobotId)
        );
        if (waitCount != 0) {
            return R.error(null, "存在待付款或付款审核的订单，无法再次购买");
        }

        Robot robot = robotService.getById(robotId);
        if (null == robot) {
            return R.error(null, "未能获取机器人");
        }

        BigDecimal robotPrice = robot.getRobotPrice();
        robotPrice = robotPrice.multiply(new BigDecimal(payCount)).subtract(new BigDecimal("0." + (new Random().nextInt(100) + 1)));
        if (robotPrice.compareTo(BigDecimal.ZERO) <= 0) {
            // 最低一分钱
            robotPrice = new BigDecimal("0.01");
        }

        List<PlatformWallet> platformWallets = platformWalletService.list(Wrappers.<PlatformWallet>lambdaQuery()
                .eq(PlatformWallet::getIsEnable, true));
        List<Long> pWallteIds = platformWallets.stream().map(PlatformWallet::getPlatformWalletId).collect(Collectors.toList());
        if (pWallteIds.isEmpty()) {
            return R.error(null, "暂不支持购买");
        }

        long incr = redisUtils.incr("wallet-current", 1);
        Long platformWalletId = pWallteIds.get(Math.toIntExact(incr % platformWallets.size()));

        Map<Long, PlatformWallet> platformWalletMap = platformWallets.stream().collect(Collectors.toMap(PlatformWallet::getPlatformWalletId, pw -> pw));
        PlatformWallet platformWallet = platformWalletMap.get(platformWalletId);

        AeRobotOrderParam aeRobotOrderParam = new AeRobotOrderParam();
        aeRobotOrderParam.setOrderNo(getPayNumber());
        aeRobotOrderParam.setOrderStatus(1);
        aeRobotOrderParam.setAccountId(userId);
        aeRobotOrderParam.setRobotId(robotId);
        aeRobotOrderParam.setPrice(robotPrice);
        aeRobotOrderParam.setPlatformWalletId(platformWalletId);
        aeRobotOrderParam.setPayMoney(robotPrice);
        aeRobotOrderParam.setPayDatetime(null);
        aeRobotOrderParam.setPayNumber(aeRobotOrderParam.getOrderNo());
        aeRobotOrderParam.setPayType(platformWallet.getQrType());
        aeRobotOrderParam.setPayMethod("扫码支付");
        aeRobotOrderParam.setPayStatus(0);
        aeRobotOrderParam.setPayCount(payCount);
        Long orderId = robotOrderServiceProcess.robotOrderAdd(aeRobotOrderParam);

        PlatformWalletInfoVo platformWalletInfoVo = new PlatformWalletInfoVo();
        BeanUtils.copyProperties(platformWallet, platformWalletInfoVo);
        platformWalletInfoVo.setPayMoney(robotPrice);
        platformWalletInfoVo.setQrAccount(null);
        platformWalletInfoVo.setRobotOrderId(orderId);
        return R.ok(platformWalletInfoVo, "新增成功");
    }

    private String getPayNumber() {
        long orderNum = redisUtils.incr("order_num_inc:", 1);
        redisUtils.expire("order_num_inc:", 3);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + String.format("%0" + 2 + "d", (int) orderNum);
    }
}
