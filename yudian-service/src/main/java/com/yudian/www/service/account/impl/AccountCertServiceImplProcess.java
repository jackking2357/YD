package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountCert;
import com.yudian.www.service.account.IAccountCertService;
import com.yudian.www.service.account.IAccountCertServiceProcess;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.account.param.AeAccountCertParam;
import com.yudian.www.service.account.param.GetAccountCertListParam;
import com.yudian.www.service.account.vo.AccountCertInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台用户证件 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
@RequiredArgsConstructor
public class AccountCertServiceImplProcess implements IAccountCertServiceProcess {

    private final IAccountService accountService;
    private final IAccountCertService accountCertService;

    /**
     * 添加
     *
     * @param aeAccountCertParam
     */
    @Override
    public void accountCertAdd(AeAccountCertParam aeAccountCertParam) {
        aeAccountCertParam.initParam();
        this.checkParam(aeAccountCertParam);
        AccountCert accountCert = new AccountCert();
        BeanUtils.copyProperties(aeAccountCertParam, accountCert);
        boolean save = accountCertService.save(accountCert);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountCertAddBatch(List<AeAccountCertParam> aeAccountCertParamList) {
        List<AccountCert> accountCertList = aeAccountCertParamList.stream()
                .map(aeAccountCertParam -> {
                    AccountCert accountCert = new AccountCert();
                    BeanUtils.copyProperties(aeAccountCertParam, accountCert);
                    return accountCert;
                }).collect(Collectors.toList());
        boolean save = accountCertService.saveBatch(accountCertList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountCertParam
     */
    @Override
    public void accountCertEdit(AeAccountCertParam aeAccountCertParam) {
        aeAccountCertParam.initParam();
        this.checkParam(aeAccountCertParam);
        AccountCert accountCert = accountCertService.getById(aeAccountCertParam.getAccountCertId());
        if (null == accountCert) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountCertParam, accountCert);
        boolean update = accountCertService.updateById(accountCert);
        if (!update) {
            throw new ServiceException("编辑失败");
        }

        accountService.update(Wrappers.<Account>lambdaUpdate()
                .set(Account::getCertStatus, aeAccountCertParam.getReviewStatus())
                .eq(Account::getAccountId, accountCert.getAccountId()));
    }

    /**
     * 删除
     *
     * @param accountCertIds
     */
    @Override
    public void accountCertRemove(Long[] accountCertIds) {
        boolean remove = accountCertService.removeByIds(Arrays.asList(accountCertIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountCertListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountCertInfoVo> getAccountCertList(GetAccountCertListParam getAccountCertListParam) {
        LambdaQueryWrapper<AccountCert> queryWrapper = new QueryWrapper<AccountCert>().lambda();
        queryWrapper
                .ge(null != getAccountCertListParam.getStartDateTime(), AccountCert::getCreateDatetime, getAccountCertListParam.getStartDateTime())
                .le(null != getAccountCertListParam.getEndDateTime(), AccountCert::getCreateDatetime, getAccountCertListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountCertListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountCertListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountCertListParam.getOrderBy());
        }

        IPage<AccountCert> page = accountCertService.page(new Page<>(getAccountCertListParam.getPageNo(), getAccountCertListParam.getPageSize()), queryWrapper);

        List<AccountCert> records = page.getRecords();

        List<AccountCertInfoVo> accountCertInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountCertInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountCertId
     * @return
     */
    @Override
    public AccountCertInfoVo getAccountCertDetail(Long accountCertId) {
        AccountCert accountCert = accountCertService.getById(accountCertId);
        if (null == accountCert) {
            throw new ServiceException("记录不存在");
        }
        List<AccountCertInfoVo> accountCertInfoVos = this.entityToVo(Arrays.asList(accountCert));
        return accountCertInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountCertInfoVo> entityToVo(List<AccountCert> records) {
        return records.stream()
                .map(accountCert -> {
                    AccountCertInfoVo accountCertInfoVo = new AccountCertInfoVo();
                    BeanUtils.copyProperties(accountCert, accountCertInfoVo);
                    return accountCertInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountCertParam
     */
    private void checkParam(AeAccountCertParam aeAccountCertParam) {

    }
}