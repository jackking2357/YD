package com.yudian.www.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountRobotAccelerator;
import com.yudian.www.entity.platform.PlatformWallet;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.service.account.IAccountRobotAcceleratorService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.platform.IPlatformWalletService;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.yudian.www.service.robot.IRobotAcceleratorServiceProcess;
import com.yudian.www.service.robot.IRobotOrderService;
import com.yudian.www.service.robot.IRobotOrderServiceProcess;
import com.yudian.www.service.robot.param.AeRobotOrderParam;
import com.yudian.www.service.robot.param.GetRobotAcceleratorListParam;
import com.yudian.www.service.robot.vo.RobotAcceleratorInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 机器人加速器 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "机器人加速器")
@RestController
@RequiredArgsConstructor
@RequestMapping("//robot-accelerator")
public class RobotAcceleratorController {

    private final RedisUtils redisUtils;
    private final IAccountService accountService;
    private final IRobotOrderService robotOrderService;
    private final IPlatformWalletService platformWalletService;
    private final IRobotAcceleratorService robotAcceleratorService;
    private final IRobotOrderServiceProcess robotOrderServiceProcess;
    private final IAccountRobotAcceleratorService accountRobotAcceleratorService;
    private final IRobotAcceleratorServiceProcess robotAcceleratorServiceProcess;

    @ApiOperation(value = "机器人加速器(列表)", notes = "机器人加速器(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotAcceleratorList")
    public R<TableRecordVo<RobotAcceleratorInfoVo>> getRobotAcceleratorList(@Valid @RequestBody GetRobotAcceleratorListParam getRobotAcceleratorListParam) {
        TableRecordVo<RobotAcceleratorInfoVo> tableRecordVo = robotAcceleratorServiceProcess.getRobotAcceleratorList(getRobotAcceleratorListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "机器人加速器(明细)", notes = "机器人加速器(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getRobotAcceleratorDetail/{robotAcceleratorId}")
    public R<RobotAcceleratorInfoVo> getRobotAcceleratorDetail(@ApiParam(name = "robotAcceleratorId", value = "机器人加速器id", required = true) @PathVariable Long robotAcceleratorId) {
        RobotAcceleratorInfoVo robotAcceleratorInfoVo = robotAcceleratorServiceProcess.getRobotAcceleratorDetail(robotAcceleratorId);
        return R.ok(robotAcceleratorInfoVo, "获取成功");
    }

    @ApiOperation(value = "机器人加速器(购买)", notes = "机器人加速器(购买)", tags = {})
    @ApiOperationSupport(order = 20)
    @PostMapping("/robotAcceleratorBuy/{robotAcceleratorId}")
    public R<PlatformWalletInfoVo> robotAcceleratorBuy(@ApiParam(name = "robotAcceleratorId", value = "机器人加速器id", required = true) @PathVariable Long robotAcceleratorId) {
        RobotAccelerator robotAccelerator = robotAcceleratorService.getById(robotAcceleratorId);
        if (null == robotAccelerator) {
            return R.error(null, "未能获取机器人加速器");
        }

        Long userId = LoginUtils.getUserId();
        Account account = accountService.getById(LoginUtils.getUserId());
        if (account.getCertStatus() != 1) {
            return R.error(null, "实名认证未通过，无法购买");
        }

        // 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
        int count = robotOrderService.count(Wrappers.<RobotOrder>lambdaQuery()
                .in(RobotOrder::getOrderStatus, Arrays.asList(1, 4))
                .eq(RobotOrder::getAccountId, userId)
                .isNotNull(RobotOrder::getRobotAcceleratorId)
        );
        if (count != 0) {
            return R.error(null, "存在待付款或付款审核的订单，无法再次购买");
        }

        BigDecimal acceleratorPrice = robotAccelerator.getAcceleratorPrice();
        acceleratorPrice = acceleratorPrice.subtract(new BigDecimal("0." + (new Random().nextInt(100) + 1)));
        if (acceleratorPrice.compareTo(BigDecimal.ZERO) <= 0) {
            // 最低一分钱
            acceleratorPrice = new BigDecimal("0.01");
        }

        List<PlatformWallet> platformWallets = platformWalletService.list(Wrappers.<PlatformWallet>lambdaQuery()
                .eq(PlatformWallet::getIsEnable, true));
        List<Long> pWallteIds = platformWallets.stream().map(PlatformWallet::getPlatformWalletId).collect(Collectors.toList());
        if (pWallteIds.isEmpty()) {
            return R.error(null, "暂不支持购买");
        }

        AccountRobotAccelerator existAra = accountRobotAcceleratorService.getOne(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                .ge(AccountRobotAccelerator::getExpirationTime, LocalDateTime.now())
                .eq(AccountRobotAccelerator::getAccountId, userId));
        if (null != existAra) {
            Long existRobotAcceleratorId = existAra.getRobotAcceleratorId();
            if (!existRobotAcceleratorId.equals(robotAcceleratorId)) {
                RobotAccelerator existRobotAccelerator = robotAcceleratorService.getById(existRobotAcceleratorId);
                if (null == existRobotAccelerator) {
                    return R.error(null, "未能获取已拥有的机器人加速器");
                }
                if (existRobotAccelerator.getAcceleratorRate().compareTo(robotAccelerator.getAcceleratorRate()) > 0) {
                    return R.error(null, "已拥有更高等级加速器，无法购入低等级");
                }
            }
        }

        long incr = redisUtils.incr("wallet-current", 1);
        Long platformWalletId = pWallteIds.get(Math.toIntExact(incr % platformWallets.size()));

        Map<Long, PlatformWallet> platformWalletMap =
                platformWallets.stream().collect(Collectors.toMap(PlatformWallet::getPlatformWalletId, pw -> pw));
        PlatformWallet platformWallet = platformWalletMap.get(platformWalletId);

        AeRobotOrderParam aeRobotOrderParam = new AeRobotOrderParam();
        aeRobotOrderParam.setOrderNo(getPayNumber());
        aeRobotOrderParam.setOrderStatus(1);
        aeRobotOrderParam.setAccountId(userId);
        aeRobotOrderParam.setRobotAcceleratorId(robotAcceleratorId);
        aeRobotOrderParam.setPrice(acceleratorPrice);
        aeRobotOrderParam.setPlatformWalletId(platformWalletId);
        aeRobotOrderParam.setPayMoney(acceleratorPrice);
        aeRobotOrderParam.setPayDatetime(null);
        aeRobotOrderParam.setPayNumber(aeRobotOrderParam.getOrderNo());
        aeRobotOrderParam.setPayType(platformWallet.getQrType());
        aeRobotOrderParam.setPayMethod("扫码支付");
        aeRobotOrderParam.setPayStatus(0);
        Long orderId = robotOrderServiceProcess.robotOrderAdd(aeRobotOrderParam);

        PlatformWalletInfoVo platformWalletInfoVo = new PlatformWalletInfoVo();
        BeanUtils.copyProperties(platformWallet, platformWalletInfoVo);
        platformWalletInfoVo.setPayMoney(acceleratorPrice);
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
