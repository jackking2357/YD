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
 * ??????????????? ???????????????
 *
 * @author yudian
 * @since 2023-01-06
 */
@Api(tags = "???????????????")
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

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/robotOrderAdd")
    public R robotOrderAdd(@Valid @RequestBody AeRobotOrderParam aeRobotOrderParam) {
        robotOrderServiceProcess.robotOrderAdd(aeRobotOrderParam);
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotOrderEdit")
    public R robotOrderEditPaid(@Validated({EditDomain.class}) @RequestBody AeRobotOrderParam aeRobotOrderParam) {
        robotOrderServiceProcess.robotOrderEdit(aeRobotOrderParam);
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(?????????)", notes = "???????????????(?????????)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotOrderPaid")
    public R robotOrderEdit(@RequestBody JSONObject jsonObject) {
        Long robotOrderId = jsonObject.getLong("robotOrderId");
        BigDecimal receiveMoney = jsonObject.getBigDecimal("receiveMoney");

        RobotOrder robotOrder = robotOrderService.getById(robotOrderId);
        if (null == robotOrder) {
            return R.error(null, "???????????????");
        }

        // ???????????????1=????????????2=????????????3=???????????????????????????4=????????????
        Integer orderStatus = robotOrder.getOrderStatus();
        // ???????????????0=????????????1=????????????2=????????????3=????????????4=????????????
        Integer payStatus = robotOrder.getPayStatus();
        if (2 == orderStatus) {
            return R.error(null, "???????????????");
        }

        Long robotId = robotOrder.getRobotId();
        Long robotAcceleratorId = robotOrder.getRobotAcceleratorId();
        LocalDateTime robotTime = null;
        LocalDateTime robotATime = null;
        BigDecimal robotPrice = BigDecimal.ZERO;
        if (null != robotId) {
            Robot robot = robotService.getById(robotId);
            if (null == robot) {
                return R.error(null, "??????????????????");
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
                return R.error(null, "???????????????????????????");
            }
            if (robotAccelerator.getExistDay() == null) {
                robotAccelerator.setExistDay(36500);
            }
            robotATime = LocalDateTime.now().plusDays(robotAccelerator.getExistDay());
            currRate = robotAccelerator.getAcceleratorRate();
            existDay = robotAccelerator.getExistDay();
            robotAPrice = robotAccelerator.getAcceleratorPrice();
        }


        // ??????
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
                    .eq(SysDictData::getDictLabel, "???????????????????????????"));
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
                            accountOutin.setOutinName("???????????????????????????");
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
                    // ???????????????
                    RobotAccelerator existRobotAccelerator = robotAcceleratorService.getById(existAra.getRobotAcceleratorId());
                    if (null == existRobotAccelerator) {
                        return R.error(null, "??????????????????????????????????????????");
                    }
                    if (existRobotAccelerator.getAcceleratorRate().compareTo(currRate) > 0) {
                        return R.error(null, "??????????????????????????????????????????????????????");
                    }

                    // ??????????????????
                    accountRobotAcceleratorService.remove(Wrappers.<AccountRobotAccelerator>lambdaQuery()
                            .eq(AccountRobotAccelerator::getAccountId, accountId));

                    AccountRobotAccelerator accountRobotAccelerator = new AccountRobotAccelerator();
                    accountRobotAccelerator.setRobotAcceleratorId(robotAcceleratorId);
                    accountRobotAccelerator.setAccountId(accountId);
                    accountRobotAccelerator.setPurchaseDatetime(LocalDateTime.now());
                    accountRobotAccelerator.setExpirationTime(existAra.getExpirationTime().plusDays(existDay));
                    accountRobotAcceleratorService.save(accountRobotAccelerator);
                } else {
                    // ?????????????????????????????????
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
                    .eq(SysDictData::getDictLabel, "????????????????????????????????????"));

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
                            accountOutin.setOutinName("???????????????????????????");
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
        // ??????????????????
        robotOrderService.update(Wrappers.<RobotOrder>lambdaUpdate()
                .set(RobotOrder::getOrderStatus, 2)
                .set(RobotOrder::getReceiveMoney, receiveMoney)
                .set(RobotOrder::getPayStatus, 1)
                .eq(RobotOrder::getRobotOrderId, robotOrderId));
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotOrderRemove/{robotOrderIds}")
    public R robotOrderRemove(@ApiParam(name = "robotOrderIds", value = "???????????????id", required = true) @PathVariable Long[] robotOrderIds) {
        robotOrderServiceProcess.robotOrderRemove(robotOrderIds);
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotOrderCancel/{robotOrderIds}")
    public R robotOrderCancel(@ApiParam(name = "robotOrderIds", value = "???????????????id", required = true) @PathVariable Long[] robotOrderIds) {
        robotOrderServiceProcess.robotOrderCancel(robotOrderIds);
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotOrderList")
    public R<TableRecordVo<RobotOrderInfoVo>> getRobotOrderList(@Valid @RequestBody GetRobotOrderListParam getRobotOrderListParam) {
        TableRecordVo<RobotOrderInfoVo> tableRecordVo = robotOrderServiceProcess.getRobotOrderList(getRobotOrderListParam);
        return R.ok(tableRecordVo, "????????????");
    }

    @ApiOperation(value = "???????????????(??????)", notes = "???????????????(??????)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getRobotOrderDetail/{robotOrderId}")
    public R<RobotOrderInfoVo> getRobotOrderDetail(@ApiParam(name = "robotOrderId", value = "???????????????id", required = true) @PathVariable Long robotOrderId) {
        RobotOrderInfoVo robotOrderInfoVo = robotOrderServiceProcess.getRobotOrderDetail(robotOrderId);
        return R.ok(robotOrderInfoVo, "????????????");
    }

    @ApiOperation(value = "???????????????(excel??????)", notes = "???????????????(excel??????)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/robotOrderExport")
    public void robotOrderExport(HttpServletResponse response, @Valid @RequestBody GetRobotOrderListParam getRobotOrderListParam) throws IOException {
        TableRecordVo<RobotOrderInfoVo> list = robotOrderServiceProcess.getRobotOrderList(getRobotOrderListParam);
        List<EasyExcelEntity<RobotOrderInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<RobotOrderInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("???????????????");
        easyExcelEntity.setClazz(RobotOrderInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "?????????????????????", tList);
    }

    @ApiOperation(value = "???????????????(excel??????)", notes = "???????????????(excel??????)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/robotOrderImport")
    public R robotOrderImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeRobotOrderParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeRobotOrderParam.class).doReadSync();
        robotOrderServiceProcess.robotOrderAddBatch(dataList);
        return R.ok(null, "????????????");
    }

    @ApiOperation(value = "???????????????(vue????????????)", notes = "???????????????(vue????????????)", tags = {})
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
                // ???????????????
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
