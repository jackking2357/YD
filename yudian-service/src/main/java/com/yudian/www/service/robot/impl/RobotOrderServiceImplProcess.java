package com.yudian.www.service.robot.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.platform.PlatformWallet;
import com.yudian.www.entity.robot.Robot;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.entity.robot.RobotOrder;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.account.vo.AccountInfoVo;
import com.yudian.www.service.platform.IPlatformWalletService;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.yudian.www.service.robot.IRobotOrderService;
import com.yudian.www.service.robot.IRobotOrderServiceProcess;
import com.yudian.www.service.robot.IRobotService;
import com.yudian.www.service.robot.param.AeRobotOrderParam;
import com.yudian.www.service.robot.param.GetRobotOrderListParam;
import com.yudian.www.service.robot.vo.RobotAcceleratorInfoVo;
import com.yudian.www.service.robot.vo.RobotInfoVo;
import com.yudian.www.service.robot.vo.RobotOrderInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机器人订单 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-06
 */
@Service
@RequiredArgsConstructor
public class RobotOrderServiceImplProcess implements IRobotOrderServiceProcess {

    private final IRobotService robotService;
    private final IAccountService accountService;
    private final IRobotOrderService robotOrderService;
    private final IPlatformWalletService platformWalletService;
    private final IRobotAcceleratorService robotAcceleratorService;

    /**
     * 添加
     *
     * @param aeRobotOrderParam
     */
    @Override
    public Long robotOrderAdd(AeRobotOrderParam aeRobotOrderParam) {
        aeRobotOrderParam.initParam();
        this.checkParam(aeRobotOrderParam);
        RobotOrder robotOrder = new RobotOrder();
        BeanUtils.copyProperties(aeRobotOrderParam, robotOrder);
        boolean save = robotOrderService.save(robotOrder);
        if (!save) {
            throw new ServiceException("保存失败");
        }
        return robotOrder.getRobotOrderId();
    }

    @Override
    public void robotOrderAddBatch(List<AeRobotOrderParam> aeRobotOrderParamList) {
        List<RobotOrder> robotOrderList = aeRobotOrderParamList.stream()
                .map(aeRobotOrderParam -> {
                    RobotOrder robotOrder = new RobotOrder();
                    BeanUtils.copyProperties(aeRobotOrderParam, robotOrder);
                    return robotOrder;
                }).collect(Collectors.toList());
        boolean save = robotOrderService.saveBatch(robotOrderList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeRobotOrderParam
     */
    @Override
    public void robotOrderEdit(AeRobotOrderParam aeRobotOrderParam) {
        aeRobotOrderParam.initParam();
        this.checkParam(aeRobotOrderParam);
        RobotOrder robotOrder = robotOrderService.getById(aeRobotOrderParam.getRobotOrderId());
        if (null == robotOrder) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeRobotOrderParam, robotOrder);
        boolean update = robotOrderService.updateById(robotOrder);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param robotOrderIds
     */
    @Override
    public void robotOrderRemove(Long[] robotOrderIds) {
        boolean remove = robotOrderService.removeByIds(Arrays.asList(robotOrderIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getRobotOrderListParam
     * @return
     */
    @Override
    public TableRecordVo<RobotOrderInfoVo> getRobotOrderList(GetRobotOrderListParam getRobotOrderListParam) {
        String phoneNumber = getRobotOrderListParam.getPhoneNumber();
        List<Long> accountIds = null;
        if (StringUtils.isNotBlank(phoneNumber)) {
            accountIds = accountService.list(Wrappers.<Account>lambdaQuery()
                    .select(Account::getAccountId)
                    .like(Account::getPhoneNumber, phoneNumber))
                    .stream()
                    .map(Account::getAccountId)
                    .collect(Collectors.toList());
            if (accountIds.isEmpty()) {
                return TableRecordVo.defaultValue();
            }
        }
        Integer orderStatus = getRobotOrderListParam.getOrderStatus();
        Long accountId = getRobotOrderListParam.getAccountId();

        LambdaQueryWrapper<RobotOrder> queryWrapper = new QueryWrapper<RobotOrder>().lambda();
        queryWrapper.eq(null != accountId, RobotOrder::getAccountId, accountId);
        queryWrapper.in(null != accountIds && !accountIds.isEmpty(), RobotOrder::getAccountId, accountIds);
        queryWrapper.eq(null != orderStatus, RobotOrder::getOrderStatus, orderStatus);
        queryWrapper
                .ge(null != getRobotOrderListParam.getStartDateTime(), RobotOrder::getCreateDatetime, getRobotOrderListParam.getStartDateTime())
                .le(null != getRobotOrderListParam.getEndDateTime(), RobotOrder::getCreateDatetime, getRobotOrderListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getRobotOrderListParam.getOrderByColumn()) && StringUtils.isNotBlank(getRobotOrderListParam.getIsAsc())) {
            queryWrapper.last("order by " + getRobotOrderListParam.getOrderBy());
        }

        IPage<RobotOrder> page = robotOrderService.page(new Page<>(getRobotOrderListParam.getPageNo(), getRobotOrderListParam.getPageSize()), queryWrapper);

        List<RobotOrder> records = page.getRecords();

        List<RobotOrderInfoVo> robotOrderInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(robotOrderInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param robotOrderId
     * @return
     */
    @Override
    public RobotOrderInfoVo getRobotOrderDetail(Long robotOrderId) {
        RobotOrder robotOrder = robotOrderService.getById(robotOrderId);
        if (null == robotOrder) {
            throw new ServiceException("记录不存在");
        }
        List<RobotOrderInfoVo> robotOrderInfoVos = this.entityToVo(Arrays.asList(robotOrder));
        return robotOrderInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void robotOrderCancel(Long[] robotOrderIds) {
        robotOrderService.update(Wrappers.<RobotOrder>lambdaUpdate()
                .set(RobotOrder::getOrderStatus, 3)
                .set(RobotOrder::getPayStatus, 4)
                .in(RobotOrder::getOrderStatus, Arrays.asList(1, 4))
                .in(RobotOrder::getRobotOrderId, Arrays.asList(robotOrderIds))
        );
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<RobotOrderInfoVo> entityToVo(List<RobotOrder> records) {
        List<Long> robotIds = records.stream().map(RobotOrder::getRobotId).filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> robotAIds = records.stream().map(RobotOrder::getRobotAcceleratorId).filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> accountIds = records.stream().map(RobotOrder::getAccountId).filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> pwIds = records.stream().map(RobotOrder::getPlatformWalletId).filter(Objects::nonNull).collect(Collectors.toList());
        Map<Long, RobotInfoVo> robotMap = new HashMap<>();
        if (!robotIds.isEmpty()) {
            robotMap.putAll(robotService.listByIds(robotIds)
                    .stream()
                    .collect(Collectors.toMap(Robot::getRobotId, robot -> {
                        RobotInfoVo robotInfoVo = new RobotInfoVo();
                        BeanUtils.copyProperties(robot, robotInfoVo);
                        return robotInfoVo;
                    })));
        }
        Map<Long, RobotAcceleratorInfoVo> robotAMap = new HashMap<>();
        if (!robotAIds.isEmpty()) {
            robotAMap.putAll(robotAcceleratorService.listByIds(robotAIds)
                    .stream()
                    .collect(Collectors.toMap(RobotAccelerator::getRobotAcceleratorId, robotAccelerator -> {
                        RobotAcceleratorInfoVo robotAcceleratorInfoVo = new RobotAcceleratorInfoVo();
                        BeanUtils.copyProperties(robotAccelerator, robotAcceleratorInfoVo);
                        return robotAcceleratorInfoVo;
                    })));
        }
        Map<Long, PlatformWalletInfoVo> platformWalletMap = new HashMap<>();
        if (!pwIds.isEmpty()) {
            platformWalletMap.putAll(platformWalletService.listByIds(pwIds)
                    .stream()
                    .collect(Collectors.toMap(PlatformWallet::getPlatformWalletId, platformWallet -> {
                        PlatformWalletInfoVo platformWalletInfoVo = new PlatformWalletInfoVo();
                        BeanUtils.copyProperties(platformWallet, platformWalletInfoVo);
                        return platformWalletInfoVo;
                    })));
        }
        Map<Long, AccountInfoVo> accountMap = new HashMap<>();
        if (!accountIds.isEmpty()) {
            accountMap.putAll(accountService.listByIds(accountIds)
                    .stream()
                    .collect(Collectors.toMap(Account::getAccountId, account -> {
                        AccountInfoVo accountInfoVo = new AccountInfoVo();
                        BeanUtils.copyProperties(account, accountInfoVo);
                        return accountInfoVo;
                    })));
        }

        return records.stream()
                .map(robotOrder -> {
                    RobotOrderInfoVo robotOrderInfoVo = new RobotOrderInfoVo();
                    BeanUtils.copyProperties(robotOrder, robotOrderInfoVo);
                    if (null != robotOrder.getRobotId() && robotMap.containsKey(robotOrder.getRobotId())) {
                        RobotInfoVo robotInfoVo = robotMap.get(robotOrder.getRobotId());
                        robotOrderInfoVo.setRobotInfoVo(robotInfoVo);
                    }

                    if (null != robotOrder.getRobotAcceleratorId() && robotAMap.containsKey(robotOrder.getRobotAcceleratorId())) {
                        RobotAcceleratorInfoVo robotAcceleratorInfoVo = robotAMap.get(robotOrder.getRobotAcceleratorId());
                        robotOrderInfoVo.setRobotAcceleratorInfoVo(robotAcceleratorInfoVo);
                    }

                    if (null != robotOrder.getPlatformWalletId() && platformWalletMap.containsKey(robotOrder.getPlatformWalletId())) {
                        PlatformWalletInfoVo platformWalletInfoVo = platformWalletMap.get(robotOrder.getPlatformWalletId());
                        robotOrderInfoVo.setPlatformWalletInfoVo(platformWalletInfoVo);
                    }
                    if (null != robotOrder.getAccountId() && accountMap.containsKey(robotOrder.getAccountId())) {
                        AccountInfoVo accountInfoVo = accountMap.get(robotOrder.getAccountId());
                        robotOrderInfoVo.setAccountInfoVo(accountInfoVo);
                    }
                    return robotOrderInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeRobotOrderParam
     */
    private void checkParam(AeRobotOrderParam aeRobotOrderParam) {

    }
}