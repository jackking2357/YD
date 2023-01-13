package com.yudian.www.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.service.robot.IRobotOrderService;
import com.yudian.www.service.robot.IRobotOrderServiceProcess;
import com.yudian.www.service.robot.param.GetRobotOrderListParam;
import com.yudian.www.service.robot.vo.RobotOrderInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * 机器人订单 前端控制器
 *
 * @author yudian
 * @since 2023-01-06
 */
@Api(tags = "机器人订单")
@RestController
@RequiredArgsConstructor
@RequestMapping("//robot-order")
public class RobotOrderController {

    private final IRobotOrderService robotOrderService;
    private final IRobotOrderServiceProcess robotOrderServiceProcess;

    @ApiOperation(value = "机器人订单(我已付款)", notes = "机器人订单(我已付款)", tags = {})
    @ApiOperationSupport(order = 1)
    @PutMapping("/robotOrderPaid/{robotOrderId}")
    public R robotOrderPaid(@ApiParam(name = "robotOrderId", value = "机器人订单id", required = true) @PathVariable Long robotOrderId) {
        RobotOrder robotOrder = robotOrderService.getById(robotOrderId);
        if (null == robotOrder) {
            return R.error(null, "订单不存在");
        }
        // 订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款
        Integer orderStatus = robotOrder.getOrderStatus();
        // 支付状态：0=待支付；1=已支付；2=退款中；3=已退款；4=已取消；
        Integer payStatus = robotOrder.getPayStatus();
        if (2 == orderStatus) {
            return R.error(null, "订单已付款");
        }
        if (3 == orderStatus) {
            return R.error(null, "订单已取消");
        }
        if (4 == orderStatus) {
            return R.error(null, "订单付款审核中");
        }
        if (1 == orderStatus) {
            robotOrderService.update(Wrappers.<RobotOrder>lambdaUpdate()
                    .set(RobotOrder::getOrderStatus, 4) // 订单变成付款审核
                    .set(RobotOrder::getPayStatus, 1) // 用户告知已支付
                    .set(RobotOrder::getPayDatetime, LocalDateTime.now())
                    .eq(RobotOrder::getRobotOrderId, robotOrderId));
            return R.ok(null, "操作成功，请等待管理审核");
        }
        return R.error(null, "订单状态错误");
    }

    @ApiOperation(value = "机器人订单(取消)", notes = "机器人订单(取消)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotOrderCancel/{robotOrderId}")
    public R robotOrderCancel(@ApiParam(name = "robotOrderId", value = "机器人订单id", required = true) @PathVariable Long robotOrderId) {
        RobotOrder ro = robotOrderService.getOne(Wrappers.<RobotOrder>lambdaQuery()
                .eq(RobotOrder::getAccountId, LoginUtils.getUserId())
                .eq(RobotOrder::getRobotOrderId, robotOrderId));
        if (null == ro) {
            return R.ok(null, "无法获取订单");
        }
        robotOrderServiceProcess.robotOrderCancel(new Long[]{robotOrderId});
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "机器人订单(列表)", notes = "机器人订单(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotOrderList")
    public R<TableRecordVo<RobotOrderInfoVo>> getRobotOrderList(@Valid @RequestBody GetRobotOrderListParam getRobotOrderListParam) {
        getRobotOrderListParam.setAccountId(LoginUtils.getUserId());
        TableRecordVo<RobotOrderInfoVo> tableRecordVo = robotOrderServiceProcess.getRobotOrderList(getRobotOrderListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

//    @ApiOperation(value = "机器人订单(明细)", notes = "机器人订单(明细)", tags = {})
//    @ApiOperationSupport(order = 5)
//    @GetMapping("/getRobotOrderDetail/{robotOrderId}")
//    public R<RobotOrderInfoVo> getRobotOrderDetail(@ApiParam(name = "robotOrderId", value = "机器人订单id", required = true) @PathVariable Long robotOrderId) {
//        RobotOrderInfoVo robotOrderInfoVo = robotOrderServiceProcess.getRobotOrderDetail(robotOrderId);
//        return R.ok(robotOrderInfoVo, "获取成功");
//    }
}
