package com.yudian.www.project.task;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yudian.common.redis.utils.RedisUtils;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.entity.account.AccountRobot;
import com.yudian.www.entity.account.AccountRobotAccelerator;
import com.yudian.www.entity.robot.RobotAccelerator;
import com.yudian.www.entity.sys.SysDictData;
import com.yudian.www.entity.work.WorkTask;
import com.yudian.www.mapper.account.AccountMapper;
import com.yudian.www.mapper.account.AccountRobotMapper;
import com.yudian.www.service.account.IAccountOutinService;
import com.yudian.www.service.account.IAccountRobotAcceleratorService;
import com.yudian.www.service.account.IAccountRobotService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.robot.IRobotAcceleratorService;
import com.yudian.www.service.sys.ISysDictDataService;
import com.yudian.www.service.work.IWorkTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class MyTask {

    private final RedisUtils redisUtils;
    private final AccountMapper accountMapper;
    private final AccountRobotMapper accountRobotMapper;
    private final ISysDictDataService sysDictDataService;
    private final IAccountService accountService;
    private final IWorkTaskService workTaskService;
    private final IAccountOutinService accountOutinService;
    private final IAccountRobotService accountRobotService;
    private final IRobotAcceleratorService robotAcceleratorService;
    private final IAccountRobotAcceleratorService accountRobotAcceleratorService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void income() {
        LocalDateTime now = LocalDateTime.now();
        SysDictData sysDictData = sysDictDataService.getOne(Wrappers.<SysDictData>lambdaQuery()
                .eq(SysDictData::getDictStatus, true)
                .eq(SysDictData::getDictKey, "fen_xiao")
                .eq(SysDictData::getDictLabel, "一级分销收益")
        );

        Map<Long, RobotAccelerator> robotAcceleratorMap = robotAcceleratorService.list()
                .stream()
                .collect(Collectors.toMap(RobotAccelerator::getRobotAcceleratorId, robotAccelerator -> robotAccelerator));

        List<WorkTask> workTaskList = workTaskService.list(Wrappers.<WorkTask>lambdaQuery().eq(WorkTask::getIsEnable, true));
        BigDecimal successIncome = workTaskList.stream()
                .map(WorkTask::getAwardIncome)
                .filter(awardIncome -> awardIncome.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<AccountRobot> accountRobotList = accountRobotService.list(Wrappers.<AccountRobot>lambdaQuery().ge(AccountRobot::getExpirationTime, now));

        List<AccountRobotAccelerator> accountRobotAcceleratorList = accountRobotAcceleratorService.list(Wrappers.<AccountRobotAccelerator>lambdaQuery().ge(AccountRobotAccelerator::getExpirationTime, now));

        Map<Long, List<AccountRobot>> arListMap = accountRobotList.stream().collect(Collectors.groupingBy(AccountRobot::getAccountId));
        Map<Long, List<AccountRobotAccelerator>> araListMap = accountRobotAcceleratorList.stream().collect(Collectors.groupingBy(AccountRobotAccelerator::getAccountId));


        Map<Long, List<Long>> accountOutinMap = new HashMap<>();
        Map<Long, BigDecimal> accountScoreMap = new HashMap<>();
        Map<Long, BigDecimal> accountLScoreMap = new HashMap<>();
        Map<Long, BigDecimal> accountAScoreMap = new HashMap<>();
        Map<Long, BigDecimal> accountSScoreMap = new HashMap<>();
        Map<Long, List<Long>> accountRobotMap = new HashMap<>();
        List<AccountOutin> accountOutins = new ArrayList<>();
        arListMap.forEach((accountId, accountRoots) -> {
            BigDecimal accountSumIncome = new BigDecimal(accountRoots.size()).multiply(successIncome);

            AccountOutin accountOutin = new AccountOutin();
            accountOutin.setAccountOutinId(IdWorker.getId());
            accountOutin.setOutinTable("AccountRobot");
            accountOutin.setOutinTableId(accountRoots.stream().map(AccountRobot::getAccountRobotId).map(String::valueOf).collect(Collectors.joining(",")));
            accountOutin.setAccountId(accountId);
            accountOutin.setOutinName("任务收益");
            accountOutin.setOutinAmount(accountSumIncome);
            accountOutin.setOutinStatus(0);
            accountOutin.setOutinDate(now.toLocalDate());
            accountOutin.setCreateBy(-1L);
            accountOutin.setCreateDatetime(now);
            accountOutin.setLastUpdateBy(-1L);
            accountOutin.setLastUpdateDatetime(now);
            accountOutins.add(accountOutin);

            BigDecimal acceleratorsIncome = BigDecimal.ZERO;
            BigDecimal signAcceleratorsIncome = BigDecimal.ZERO;
            if (araListMap.containsKey(accountId)) {
                List<AccountRobotAccelerator> accountRobotAccelerators = araListMap.get(accountId);
                BigDecimal rate = accountRobotAccelerators.stream()
                        .map(accountRobotAccelerator -> robotAcceleratorMap.get(accountRobotAccelerator.getRobotAcceleratorId()))
                        .filter(Objects::nonNull)
                        .map(RobotAccelerator::getAcceleratorRate)
                        .filter(Objects::nonNull)
                        .filter(arate -> arate.compareTo(BigDecimal.ZERO) > 0)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal finalRate = rate.divide(BigDecimal.TEN.pow(2), 2, BigDecimal.ROUND_DOWN);
                acceleratorsIncome = accountSumIncome.multiply(finalRate).setScale(2, BigDecimal.ROUND_DOWN);
                signAcceleratorsIncome = successIncome.multiply(finalRate).setScale(2, BigDecimal.ROUND_DOWN);

                AccountOutin accountOutin2 = new AccountOutin();
                accountOutin2.setAccountOutinId(IdWorker.getId());
                accountOutin2.setAccountId(accountId);
                accountOutin2.setOutinTable("AccountRobotAccelerator");
                accountOutin2.setOutinTableId(accountRobotAccelerators.stream().map(AccountRobotAccelerator::getAccountRobotAcceleratorId).map(String::valueOf)
                        .collect(Collectors.joining(",")));
                accountOutin2.setOutinName("加速器收益");
                accountOutin2.setOutinAmount(acceleratorsIncome);
                accountOutin2.setOutinDate(now.toLocalDate());
                accountOutin2.setOutinStatus(0);
                accountOutin2.setCreateBy(-1L);
                accountOutin2.setCreateDatetime(now);
                accountOutin2.setLastUpdateBy(-1L);
                accountOutin2.setLastUpdateDatetime(now);
                accountOutins.add(accountOutin2);
            }

            accountScoreMap.put(accountId, accountSumIncome);
            accountAScoreMap.put(accountId, acceleratorsIncome);
            accountSScoreMap.put(accountId, successIncome.add(signAcceleratorsIncome));
            accountRobotMap.put(accountId, accountRoots.stream().map(AccountRobot::getAccountRobotId).collect(Collectors.toList()));
            accountOutinMap.put(accountId, Arrays.asList(accountOutin.getAccountOutinId()));
        });

        if (!accountOutins.isEmpty()) {
            if (null != sysDictData) {
                String dictValue = sysDictData.getDictValue();
                if (StringUtils.isNotBlank(dictValue)) {
                    List<Account> accounts = accountService.list();
                    Map<Long, List<Account>> inviteAccountMap = accounts.stream()
                            .filter(account -> account.getInviteAccountId() != null)
                            .collect(Collectors.groupingBy(Account::getInviteAccountId));
                    accountScoreMap.forEach((accountId, score) -> {
                        List<Account> childAccounts = inviteAccountMap.getOrDefault(accountId, new ArrayList<>());

                        BigDecimal levelIncome = BigDecimal.ZERO;
                        List<Long> outinIds = new ArrayList<>();
                        for (Account childAccount : childAccounts) {
                            if (!accountScoreMap.containsKey(childAccount.getAccountId())) {
                                continue;
                            }
                            BigDecimal aIncome = accountScoreMap.getOrDefault(childAccount.getAccountId(), BigDecimal.ZERO);
                            outinIds.addAll(accountOutinMap.getOrDefault(childAccount.getAccountId(), new ArrayList<>()));
                            levelIncome = levelIncome.add(aIncome.multiply(new BigDecimal(dictValue).divide(BigDecimal.TEN.pow(2), 2, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN));
                        }

                        if (levelIncome.compareTo(BigDecimal.ZERO) <= 0) {
                            return;
                        }

                        AccountOutin accountOutin = new AccountOutin();
                        accountOutin.setAccountOutinId(IdWorker.getId());
                        accountOutin.setOutinTable("AccountOutin");
                        accountOutin.setOutinTableId(outinIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
                        accountOutin.setAccountId(accountId);
                        accountOutin.setOutinName("一级分销收益");
                        accountOutin.setOutinAmount(levelIncome);
                        accountOutin.setOutinStatus(0);
                        accountOutin.setOutinDate(now.toLocalDate());
                        accountOutin.setCreateBy(-1L);
                        accountOutin.setCreateDatetime(now);
                        accountOutin.setLastUpdateBy(-1L);
                        accountOutin.setLastUpdateDatetime(now);
                        accountOutins.add(accountOutin);
                        accountLScoreMap.put(accountId, levelIncome);
                    });
                }
            }
            accountOutinService.saveBatch(accountOutins);
        }

        accountSScoreMap.forEach((accountId, score) -> {
            List<Long> accountRobotIds = accountRobotMap.get(accountId);
            if (!accountRobotIds.isEmpty()) {
                accountRobotMapper.addScore(accountRobotIds, score);
            }
        });

        accountScoreMap.forEach((accountId, score) -> accountMapper.addScore(accountId, score.add(accountAScoreMap.getOrDefault(accountId, BigDecimal.ZERO)).add(accountLScoreMap.getOrDefault(accountId, BigDecimal.ZERO))));
    }


}
