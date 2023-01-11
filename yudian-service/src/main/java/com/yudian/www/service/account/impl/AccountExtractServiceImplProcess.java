package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountExtract;
import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.mapper.account.AccountMapper;
import com.yudian.www.service.account.IAccountExtractService;
import com.yudian.www.service.account.IAccountExtractServiceProcess;
import com.yudian.www.service.account.IAccountOutinService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.account.param.AeAccountExtractParam;
import com.yudian.www.service.account.param.GetAccountExtractListParam;
import com.yudian.www.service.account.vo.AccountExtractInfoVo;
import com.yudian.www.service.account.vo.AccountInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 平台用户提取记录 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
@RequiredArgsConstructor
public class AccountExtractServiceImplProcess implements IAccountExtractServiceProcess {

    private final AccountMapper accountMapper;
    private final IAccountService accountService;
    private final IAccountOutinService accountOutinService;
    private final IAccountExtractService accountExtractService;

    /**
     * 添加
     *
     * @param aeAccountExtractParam
     */
    @Override
    public void accountExtractAdd(AeAccountExtractParam aeAccountExtractParam) {
        aeAccountExtractParam.initParam();
        this.checkParam(aeAccountExtractParam);
        AccountExtract accountExtract = new AccountExtract();
        BeanUtils.copyProperties(aeAccountExtractParam, accountExtract);
        accountExtract.setAccountExtractId(IdWorker.getId());

        Account oldAccount = accountService.getById(accountExtract.getAccountId());
        int count = accountMapper.subScore(accountExtract.getAccountId(), accountExtract.getExtractAmount().abs());
        if (0 == count) {
            throw new ServiceException("余额不足，无法提现");
        }
        Account newAccount = accountService.getById(accountExtract.getAccountId());

        AccountOutin accountOutin = new AccountOutin();
        accountOutin.setAccountId(accountExtract.getAccountId());
        accountOutin.setOutinName("余额提取");
        accountOutin.setOutinAmount(accountExtract.getExtractAmount().negate());
        accountOutin.setOutinStatus(1);
        accountOutin.setOutinTable("AccountExtract");
        accountOutin.setOutinTableId(accountExtract.getAccountExtractId() + "");
        accountOutin.setOutinDate(LocalDate.now());

        accountExtract.setOldScore(oldAccount.getScore());
        accountExtract.setNewScore(newAccount.getScore());
        accountExtractService.save(accountExtract);
        accountOutinService.save(accountOutin);
    }

    @Override
    public void accountExtractAddBatch(List<AeAccountExtractParam> aeAccountExtractParamList) {
        List<AccountExtract> accountExtractList = aeAccountExtractParamList.stream()
                .map(aeAccountExtractParam -> {
                    AccountExtract accountExtract = new AccountExtract();
                    BeanUtils.copyProperties(aeAccountExtractParam, accountExtract);
                    return accountExtract;
                }).collect(Collectors.toList());
        boolean save = accountExtractService.saveBatch(accountExtractList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountExtractParam
     */
    @Override
    public void accountExtractEdit(AeAccountExtractParam aeAccountExtractParam) {
        aeAccountExtractParam.initParam();
        this.checkParam(aeAccountExtractParam);
        AccountExtract accountExtract = accountExtractService.getById(aeAccountExtractParam.getAccountExtractId());
        if (null == accountExtract) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountExtractParam, accountExtract);
        boolean update = accountExtractService.updateById(accountExtract);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param accountExtractIds
     */
    @Override
    public void accountExtractRemove(Long[] accountExtractIds) {
        boolean remove = accountExtractService.removeByIds(Arrays.asList(accountExtractIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountExtractListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountExtractInfoVo> getAccountExtractList(GetAccountExtractListParam getAccountExtractListParam) {
        String phoneNumber = getAccountExtractListParam.getPhoneNumber();
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
        Integer reviewStatus = getAccountExtractListParam.getReviewStatus();

        LambdaQueryWrapper<AccountExtract> queryWrapper = new QueryWrapper<AccountExtract>().lambda();
        queryWrapper.in(null != accountIds && !accountIds.isEmpty(), AccountExtract::getAccountId, accountIds);
        queryWrapper.eq(null != reviewStatus, AccountExtract::getReviewStatus, reviewStatus);
        queryWrapper
                .ge(null != getAccountExtractListParam.getStartDateTime(), AccountExtract::getCreateDatetime, getAccountExtractListParam.getStartDateTime())
                .le(null != getAccountExtractListParam.getEndDateTime(), AccountExtract::getCreateDatetime, getAccountExtractListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountExtractListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountExtractListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountExtractListParam.getOrderBy());
        }

        IPage<AccountExtract> page = accountExtractService.page(new Page<>(getAccountExtractListParam.getPageNo(), getAccountExtractListParam.getPageSize()), queryWrapper);

        List<AccountExtract> records = page.getRecords();

        List<AccountExtractInfoVo> accountExtractInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountExtractInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountExtractId
     * @return
     */
    @Override
    public AccountExtractInfoVo getAccountExtractDetail(Long accountExtractId) {
        AccountExtract accountExtract = accountExtractService.getById(accountExtractId);
        if (null == accountExtract) {
            throw new ServiceException("记录不存在");
        }
        List<AccountExtractInfoVo> accountExtractInfoVos = this.entityToVo(Arrays.asList(accountExtract));
        return accountExtractInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountExtractInfoVo> entityToVo(List<AccountExtract> records) {
        Set<Long> accountIds = records.stream().map(AccountExtract::getAccountId).collect(Collectors.toSet());
        Map<Long, Account> accountMap = new HashMap<>();
        if (!accountIds.isEmpty()) {
            accountMap.putAll(accountService.listByIds(accountIds).stream().collect(Collectors.toMap(Account::getAccountId, account -> account)));
        }

        return records.stream()
                .map(accountExtract -> {
                    AccountExtractInfoVo accountExtractInfoVo = new AccountExtractInfoVo();
                    BeanUtils.copyProperties(accountExtract, accountExtractInfoVo);
                    if (accountMap.containsKey(accountExtract.getAccountId())) {
                        Account account = accountMap.get(accountExtract.getAccountId());
                        AccountInfoVo accountInfoVo = new AccountInfoVo();
                        BeanUtils.copyProperties(account, accountInfoVo);
                        accountExtractInfoVo.setAccountInfoVo(accountInfoVo);
                    }
                    return accountExtractInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountExtractParam
     */
    private void checkParam(AeAccountExtractParam aeAccountExtractParam) {
        BigDecimal extractAmount = aeAccountExtractParam.getExtractAmount();
        if (extractAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("提取余额错误");
        }
    }
}