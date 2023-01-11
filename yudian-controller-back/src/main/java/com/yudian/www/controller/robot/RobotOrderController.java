package com.yudian.www.controller.robot;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.entity.account.AccountRobot;
import com.yudian.www.entity.account.AccountRobotAccelerator;
import com.yudian.www.entity.robot.Robot;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.entity.sys.SysDictData;
import com.yudian.www.mapper.account.AccountMapper;
import com.yudian.www.service.account.IAccountOutinService;
import com.yudian.www.service.account.IAccountRobotAcceleratorService;
import com.yudian.www.service.account.IAccountRobotService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.yudian.www.service.robot.IRobotOrderService;
import com.yudian.www.service.robot.IRobotOrderServiceProcess;
import com.yudian.www.service.robot.IRobotService;
import com.yudian.www.service.robot.param.AeRobotOrderParam;
import com.yudian.www.service.robot.param.GetRobotOrderListParam;
import com.yudian.www.service.robot.vo.RobotOrderInfoVo;
import com.yudian.www.service.sys.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private final IRobotService robotService;
    private final AccountMapper accountMapper;
    private final IAccountService accountService;
    private final IRobotOrderService robotOrderService;
    private final ISysDictDataService sysDictDataService;
    private final IAccountRobotService accountRobotService;
    private final IAccountOutinService accountOutinService;
    private final IRobotAcceleratorService robotAcceleratorService;
    private final IRobotOrderServiceProcess robotOrderServiceProcess;
    private final IAccountRobotAcceleratorService accountRobotAcceleratorService;

    @ApiOperation(value = "机器人订单(添加)", notes = "机器人订单(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/robotOrderAdd")
    public R robotOrderAdd(@Valid @RequestBody AeRobotOrderParam aeRobotOrderParam) {
        robotOrderServiceProcess.robotOrderAdd(aeRobotOrderParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "机器人订单(编辑)", notes = "机器人订单(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotOrderEdit")
    public R robotOrderEditPaid(@Validated({EditDomain.class}) @RequestBody AeRobotOrderParam aeRobotOrderParam) {
        robotOrderServiceProcess.robotOrderEdit(aeRobotOrderParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "机器人订单(已收款)", notes = "机器人订单(已收款)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotOrderPaid")
    public R robotOrderEdit(@RequestBody JSONObject jsonObject) {
        Long robotOrderId = jsonObject.getLong("robotOrderId");
        BigDecimal receiveMoney = jsonObject.getBigDecimal("receiveMoney");

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

        Long robotId = robotOrder.getRobotId();
        Long robotAcceleratorId = robotOrder.getRobotAcceleratorId();
        LocalDateTime robotTime = null;
        LocalDateTime robotATime = null;
        BigDecimal robotPrice = BigDecimal.ZERO;
        if (null != robotId) {
            Robot robot = robotService.getById(robotId);
            if (null == robot) {
                return R.error(null, "机器人不存在");
            }
            if (robot.getExistDay() == null) {
                robot.setExistDay(36500);
            }
            robotTime = LocalDateTime.now().plusDays(robot.getExistDay());
            robotPrice = robot.getRobotPrice();
        }

        BigDecimal currRate = new BigDecimal(0);
        BigDecimal robotAPrice = BigDecimal.ZERO;
        Integer existDay = 0;
        if (null != robotAcceleratorId) {
            RobotAccelerator robotAccelerator = robotAcceleratorService.getById(robotAcceleratorId);
            if (null == robotAccelerator) {
                return R.error(null, "机器人加速器不存在");
            }
            if (robotAccelerator.getExistDay() == null) {
                robotAccelerator.setExistDay(36500);
            }
            robotATime = LocalDateTime.now().plusDays(robotAccelerator.getExistDay());
            currRate = robotAccelerator.getAcceleratorRate();
            existDay = robotAccelerator.getExistDay();
            robotAPrice = robotAccelerator.getAcceleratorPrice();
        }


        // 发货
        Long accountId = robotOrder.getAccountId();
        Integer payCount = robotOrder.getPayCount();

        if (null != robotId) {
            List<AccountRobot> accountRobots = new ArrayList<>();
            for (int i = 0; i < payCount; i++) {
                AccountRobot accountRobot = new AccountRobot();
                accountRobot.setRobotId(robotId);
                accountRobot.setAccountId(accountId);
                accountRobot.setPurchaseDatetime(LocalDateTime.now());
                accountRobot.setCumulativeIncome(new BigDecimal("0"));
                accountRobot.setExpirationTime(robotTime);
                accountRobots.add(accountRobot);
            }
            accountRobotService.saveBatch(accountRobots);

            SysDictData sysDictData = sysDictDataService.getOne(Wrappers.<SysDictData>lambdaQuery()
                    .eq(SysDictData::getDictStatus, true)
                    .eq(SysDictData::getDictKey, "fen_xiao")
                    .eq(SysDictData::getDictLabel, "机器人购买分销收益"));
            if (null != sysDictData) {
                Account account = accountService.getById(accountId);
                if (null != account) {
                    Long inviteAccountId = account.getInviteAccountId();
                    if (null != inviteAccountId) {
                        String dictValue = sysDictData.getDictValue();
                        BigDecimal bigDecimal = receiveMoney.multiply(new BigDecimal(dictValue).divide(BigDecimal.TEN.pow(2), 2, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN);
                        if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                            AccountOutin accountOutin = new AccountOutin();
                            accountOutin.setAccountOutinId(IdWorker.getId());
                            accountOutin.setOutinTable("RobotOrder");
                            accountOutin.setOutinTableId(robotOrderId + "");
                            accountOutin.setAccountId(inviteAccountId);
                            accountOutin.setOutinName("团队机器人购买收益");
                            accountOutin.setOutinAmount(bigDecimal);
                            accountOutin.setOutinStatus(0);
                            accountOutin.setOutinDate(LocalDate.now());
                            accountOutinService.save(accountOutin);
                            accountMapper.addScore(inviteAccountId, bigDecimal);
                        }
                    }
                }
            }
        }
        if (null != robotAcceleratorId) {
            AccountRobotAccelerator existAra = accountRobotAcceleratorService.getOne(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                    .ge(AccountRobotAccelerator::getExpirationTime, LocalDateTime.now())
                    .eq(AccountRobotAccelerator::getAccountId, accountId));
            if (null != existAra) {
                Long existRobotAcceleratorId = existAra.getRobotAcceleratorId();
                if (!existRobotAcceleratorId.equals(robotAcceleratorId)) {
                    // 加速器不同
                    RobotAccelerator existRobotAccelerator = robotAcceleratorService.getById(existAra.getRobotAcceleratorId());
                    if (null == existRobotAccelerator) {
                        return R.error(null, "未能获取已拥有的机器人加速器");
                    }
                    if (existRobotAccelerator.getAcceleratorRate().compareTo(currRate) > 0) {
                        return R.error(null, "已拥有更高等级加速器，无法购入低等级");
                    }

                    // 计算剩余时间
                    accountRobotAcceleratorService.remove(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                            .eq(AccountRobotAccelerator::getAccountId, accountId));

                    AccountRobotAccelerator accountRobotAccelerator = new AccountRobotAccelerator();
                    accountRobotAccelerator.setRobotAcceleratorId(robotAcceleratorId);
                    accountRobotAccelerator.setAccountId(accountId);
                    accountRobotAccelerator.setPurchaseDatetime(LocalDateTime.now());
                    accountRobotAccelerator.setExpirationTime(existAra.getExpirationTime().plusDays(existDay));
                    accountRobotAcceleratorService.save(accountRobotAccelerator);
                } else {
                    // 相同加速器，则时间叠加
                    accountRobotAcceleratorService.update(Wrappers.<AccountRobotAccelerator>lambdaUpdate()
                            .set(AccountRobotAccelerator::getExpirationTime, existAra.getExpirationTime().plusDays(existDay))
                            .eq(AccountRobotAccelerator::getAccountRobotAcceleratorId, existAra.getAccountRobotAcceleratorId()));
                }
            } else {
                accountRobotAcceleratorService.remove(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                        .eq(AccountRobotAccelerator::getAccountId, accountId));
                AccountRobotAccelerator accountRobotAccelerator = new AccountRobotAccelerator();
                accountRobotAccelerator.setRobotAcceleratorId(robotAcceleratorId);
                accountRobotAccelerator.setAccountId(accountId);
                accountRobotAccelerator.setPurchaseDatetime(LocalDateTime.now());
                accountRobotAccelerator.setExpirationTime(robotATime);
                accountRobotAcceleratorService.save(accountRobotAccelerator);
            }

            SysDictData sysDictData = sysDictDataService.getOne(Wrappers.<SysDictData>lambdaQuery()
                    .eq(SysDictData::getDictStatus, true)
                    .eq(SysDictData::getDictKey, "fen_xiao")
                    .eq(SysDictData::getDictLabel, "机器人加速器购买分销收益"));

            if (null != sysDictData) {
                Account account = accountService.getById(accountId);
                if (null != account) {
                    Long inviteAccountId = account.getInviteAccountId();
                    if (null != inviteAccountId) {
                        String dictValue = sysDictData.getDictValue();
                        BigDecimal bigDecimal = receiveMoney.multiply(new BigDecimal(dictValue).divide(BigDecimal.TEN.pow(2), 2, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN);
                        if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                            AccountOutin accountOutin = new AccountOutin();
                            accountOutin.setAccountOutinId(IdWorker.getId());
                            accountOutin.setOutinTable("RobotOrder");
                            accountOutin.setOutinTableId(robotOrderId + "");
                            accountOutin.setAccountId(inviteAccountId);
                            accountOutin.setOutinName("团队加速器购买收益");
                            accountOutin.setOutinAmount(bigDecimal);
                            accountOutin.setOutinStatus(0);
                            accountOutin.setOutinDate(LocalDate.now());
                            accountOutinService.save(accountOutin);
                            accountMapper.addScore(inviteAccountId, bigDecimal);
                        }
                    }
                }
            }
        }
        // 修改订单状态
        robotOrderService.update(Wrappers.<RobotOrder>lambdaUpdate()
                .set(RobotOrder::getOrderStatus, 2)
                .set(RobotOrder::getReceiveMoney, receiveMoney)
                .set(RobotOrder::getPayStatus, 1)
                .eq(RobotOrder::getRobotOrderId, robotOrderId));
        return R.ok(null, "操作成功");
    }

    @ApiOperation(value = "机器人订单(删除)", notes = "机器人订单(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotOrderRemove/{robotOrderIds}")
    public R robotOrderRemove(@ApiParam(name = "robotOrderIds", value = "机器人订单id", required = true) @PathVariable Long[] robotOrderIds) {
        robotOrderServiceProcess.robotOrderRemove(robotOrderIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "机器人订单(取消)", notes = "机器人订单(取消)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotOrderCancel/{robotOrderIds}")
    public R robotOrderCancel(@ApiParam(name = "robotOrderIds", value = "机器人订单id", required = true) @PathVariable Long[] robotOrderIds) {
        robotOrderServiceProcess.robotOrderCancel(robotOrderIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "机器人订单(列表)", notes = "机器人订单(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotOrderList")
    public R<TableRecordVo<RobotOrderInfoVo>> getRobotOrderList(@Valid @RequestBody GetRobotOrderListParam getRobotOrderListParam) {
        TableRecordVo<RobotOrderInfoVo> tableRecordVo = robotOrderServiceProcess.getRobotOrderList(getRobotOrderListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "机器人订单(明细)", notes = "机器人订单(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getRobotOrderDetail/{robotOrderId}")
    public R<RobotOrderInfoVo> getRobotOrderDetail(@ApiParam(name = "robotOrderId", value = "机器人订单id", required = true) @PathVariable Long robotOrderId) {
        RobotOrderInfoVo robotOrderInfoVo = robotOrderServiceProcess.getRobotOrderDetail(robotOrderId);
        return R.ok(robotOrderInfoVo, "获取成功");
    }

    @ApiOperation(value = "机器人订单(excel导出)", notes = "机器人订单(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/robotOrderExport")
    public void robotOrderExport(HttpServletResponse response, @Valid @RequestBody GetRobotOrderListParam getRobotOrderListParam) throws IOException {
        TableRecordVo<RobotOrderInfoVo> list = robotOrderServiceProcess.getRobotOrderList(getRobotOrderListParam);
        List<EasyExcelEntity<RobotOrderInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<RobotOrderInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("机器人订单");
        easyExcelEntity.setClazz(RobotOrderInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "机器人订单导出", tList);
    }

    @ApiOperation(value = "机器人订单(excel导入)", notes = "机器人订单(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/robotOrderImport")
    public R robotOrderImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeRobotOrderParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeRobotOrderParam.class).doReadSync();
        robotOrderServiceProcess.robotOrderAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "机器人订单(vue资源下载)", notes = "机器人订单(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getRobotOrderVue", produces = "application/octet-stream")
    public void getRobotOrderVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("robot-order.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/robot/robot-order/*");
            for (org.springframework.core.io.Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                String filename = resource.getFilename();
                if (StringUtils.isBlank(filename)) {
                    continue;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                byte[] bytes = baos.toByteArray();
                // 设置文件名
                ArchiveEntry entry = new ZipArchiveEntry(filename);
                zaos.putArchiveEntry(entry);
                zaos.write(bytes);
                zaos.closeArchiveEntry();
                inputStream.close();
                baos.close();
            }
            zaos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
