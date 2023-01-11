package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.AccountRobot;
import com.yudian.www.entity.robot.Robot;
import com.yudian.www.service.account.IAccountRobotService;
import com.yudian.www.service.account.IAccountRobotServiceProcess;
import com.yudian.www.service.account.param.AeAccountRobotParam;
import com.yudian.www.service.account.param.GetAccountRobotListParam;
import com.yudian.www.service.account.vo.AccountRobotInfoVo;
import com.yudian.www.service.robot.IRobotService;
import com.yudian.www.service.robot.vo.RobotInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 平台用户机器人 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
@RequiredArgsConstructor
public class AccountRobotServiceImplProcess implements IAccountRobotServiceProcess {

    private final IRobotService robotService;
    private final IAccountRobotService accountRobotService;

    /**
     * 添加
     *
     * @param aeAccountRobotParam
     */
    @Override
    public void accountRobotAdd(AeAccountRobotParam aeAccountRobotParam) {
        aeAccountRobotParam.initParam();
        this.checkParam(aeAccountRobotParam);
        AccountRobot accountRobot = new AccountRobot();
        BeanUtils.copyProperties(aeAccountRobotParam, accountRobot);
        boolean save = accountRobotService.save(accountRobot);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountRobotAddBatch(List<AeAccountRobotParam> aeAccountRobotParamList) {
        List<AccountRobot> accountRobotList = aeAccountRobotParamList.stream()
                .map(aeAccountRobotParam -> {
                    AccountRobot accountRobot = new AccountRobot();
                    BeanUtils.copyProperties(aeAccountRobotParam, accountRobot);
                    return accountRobot;
                }).collect(Collectors.toList());
        boolean save = accountRobotService.saveBatch(accountRobotList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountRobotParam
     */
    @Override
    public void accountRobotEdit(AeAccountRobotParam aeAccountRobotParam) {
        aeAccountRobotParam.initParam();
        this.checkParam(aeAccountRobotParam);
        AccountRobot accountRobot = accountRobotService.getById(aeAccountRobotParam.getAccountRobotId());
        if (null == accountRobot) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountRobotParam, accountRobot);
        boolean update = accountRobotService.updateById(accountRobot);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param accountRobotIds
     */
    @Override
    public void accountRobotRemove(Long[] accountRobotIds) {
        boolean remove = accountRobotService.removeByIds(Arrays.asList(accountRobotIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountRobotListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountRobotInfoVo> getAccountRobotList(GetAccountRobotListParam getAccountRobotListParam) {
        Long accountId = getAccountRobotListParam.getAccountId();
        Boolean isEffective = getAccountRobotListParam.getIsEffective();

        LambdaQueryWrapper<AccountRobot> queryWrapper = new QueryWrapper<AccountRobot>().lambda();
        queryWrapper.ge(null != isEffective, AccountRobot::getExpirationTime, LocalDateTime.now());
        queryWrapper.eq(null != accountId, AccountRobot::getAccountId, accountId);
        queryWrapper
                .ge(null != getAccountRobotListParam.getStartDateTime(), AccountRobot::getCreateDatetime, getAccountRobotListParam.getStartDateTime())
                .le(null != getAccountRobotListParam.getEndDateTime(), AccountRobot::getCreateDatetime, getAccountRobotListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountRobotListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountRobotListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountRobotListParam.getOrderBy());
        }

        IPage<AccountRobot> page = accountRobotService.page(new Page<>(getAccountRobotListParam.getPageNo(), getAccountRobotListParam.getPageSize()), queryWrapper);

        List<AccountRobot> records = page.getRecords();

        List<AccountRobotInfoVo> accountRobotInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountRobotInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountRobotId
     * @return
     */
    @Override
    public AccountRobotInfoVo getAccountRobotDetail(Long accountRobotId) {
        AccountRobot accountRobot = accountRobotService.getById(accountRobotId);
        if (null == accountRobot) {
            throw new ServiceException("记录不存在");
        }
        List<AccountRobotInfoVo> accountRobotInfoVos = this.entityToVo(Arrays.asList(accountRobot));
        return accountRobotInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountRobotInfoVo> entityToVo(List<AccountRobot> records) {
        List<Long> robotIds = records.stream().map(AccountRobot::getRobotId).collect(Collectors.toList());
        Map<Long, RobotInfoVo> robotInfoVoMap = new HashMap<>();
        if (!robotIds.isEmpty()) {
            robotInfoVoMap.putAll(robotService.listByIds(robotIds)
                    .stream()
                    .collect(Collectors.toMap(Robot::getRobotId, robot -> {
                        RobotInfoVo robotInfoVo = new RobotInfoVo();
                        BeanUtils.copyProperties(robot, robotInfoVo);
                        return robotInfoVo;
                    })));
        }
        return records.stream()
                .map(accountRobot -> {
                    AccountRobotInfoVo accountRobotInfoVo = new AccountRobotInfoVo();
                    BeanUtils.copyProperties(accountRobot, accountRobotInfoVo);
                    if (robotInfoVoMap.containsKey(accountRobot.getRobotId())) {
                        RobotInfoVo robotInfoVo = robotInfoVoMap.get(accountRobot.getRobotId());
                        accountRobotInfoVo.setRobotInfoVo(robotInfoVo);
                    }
                    return accountRobotInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountRobotParam
     */
    private void checkParam(AeAccountRobotParam aeAccountRobotParam) {

    }
}