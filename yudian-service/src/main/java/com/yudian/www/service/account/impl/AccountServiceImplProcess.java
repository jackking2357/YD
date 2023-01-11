package com.yudian.www.service.account.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.auth.config.DeviceType;
import com.yudian.auth.entity.LoginUser;
import com.yudian.auth.entity.UserType;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.Account;
import com.yudian.www.entity.account.AccountCert;
import com.yudian.www.service.account.IAccountCertService;
import com.yudian.www.service.account.IAccountService;
import com.yudian.www.service.account.IAccountServiceProcess;
import com.yudian.www.service.account.param.AeAccountParam;
import com.yudian.www.service.account.param.GetAccountListParam;
import com.yudian.www.service.account.vo.AccountCertInfoVo;
import com.yudian.www.service.account.vo.AccountInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 平台用户 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImplProcess implements IAccountServiceProcess {

    private final IAccountService accountService;
    private final IAccountCertService accountCertService;

    /**
     * 添加
     *
     * @param aeAccountParam
     */
    @Override
    public void accountAdd(AeAccountParam aeAccountParam) {
        aeAccountParam.initParam();
        this.checkParam(aeAccountParam);
        Account account = new Account();
        BeanUtils.copyProperties(aeAccountParam, account);
        boolean save = accountService.save(account);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountAddBatch(List<AeAccountParam> aeAccountParamList) {
        List<Account> accountList = aeAccountParamList.stream()
                .map(aeAccountParam -> {
                    Account account = new Account();
                    BeanUtils.copyProperties(aeAccountParam, account);
                    return account;
                }).collect(Collectors.toList());
        boolean save = accountService.saveBatch(accountList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountParam
     */
    @Override
    public void accountEdit(AeAccountParam aeAccountParam) {
        aeAccountParam.initParam();
        this.checkParam(aeAccountParam);
        Account account = accountService.getById(aeAccountParam.getAccountId());
        if (null == account) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountParam, account);
        boolean update = accountService.updateById(account);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param accountIds
     */
    @Override
    public void accountRemove(Long[] accountIds) {
        boolean remove = accountService.removeByIds(Arrays.asList(accountIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountInfoVo> getAccountList(GetAccountListParam getAccountListParam) {
        String nickName = getAccountListParam.getNickName();
        String phoneNumber = getAccountListParam.getPhoneNumber();
        String phoneNumbers = getAccountListParam.getPhoneNumbers();
        Boolean showCert = getAccountListParam.getShowCert();
        Set<String> phones = new HashSet<>();
        if (StringUtils.isNotBlank(phoneNumbers)) {
            phones = Stream.of(phoneNumbers.split("_")).collect(Collectors.toSet());
        }

        LambdaQueryWrapper<Account> queryWrapper = new QueryWrapper<Account>().lambda();
        queryWrapper
                .in(!phones.isEmpty(), Account::getPhoneNumber, phones)
                .like(StringUtils.isNotBlank(nickName), Account::getNickName, nickName)
                .like(StringUtils.isNotBlank(phoneNumber), Account::getPhoneNumber, phoneNumber);
        queryWrapper
                .ge(null != getAccountListParam.getStartDateTime(), Account::getCreateDatetime, getAccountListParam.getStartDateTime())
                .le(null != getAccountListParam.getEndDateTime(), Account::getCreateDatetime, getAccountListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountListParam.getOrderBy());
        }
        IPage<Account> page = accountService.page(new Page<>(getAccountListParam.getPageNo(), getAccountListParam.getPageSize()), queryWrapper);

        List<Account> records = page.getRecords();

        List<AccountInfoVo> accountInfoVoList = this.entityToVo(records, showCert);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountId
     * @return
     */
    @Override
    public AccountInfoVo getAccountDetail(Long accountId) {
        Account account = accountService.getById(accountId);
        if (null == account) {
            throw new ServiceException("记录不存在");
        }
        List<AccountInfoVo> accountInfoVos = this.entityToVo(Arrays.asList(account), false);
        return accountInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void editLoginPwd(String oldLoginPwd, String newLoginPwd) {
        Account account = accountService.getById(LoginUtils.getUserId());
        if (null == account) {
            throw new ServiceException("账号不存在");
        }
        if (!account.getLoginPwd().equals(SecureUtil.md5(SecureUtil.sha1(oldLoginPwd)))) {
            throw new ServiceException("旧密码错误");
        }
        accountService.update(Wrappers.<Account>lambdaUpdate()
                .set(Account::getLoginPwd, SecureUtil.md5(SecureUtil.sha1(newLoginPwd)))
                .eq(Account::getAccountId, account.getAccountId()));
    }

    @Override
    public Map<String, Object> appLogin(String phoneNumber, String loginPwd) {
        Account account = accountService.getOne(Wrappers.<Account>lambdaQuery().eq(Account::getPhoneNumber, phoneNumber));
        if (null == account) {
            throw new ServiceException("账号不存在");
        }
        if (account.getAccountStatus() == 2) {
            throw new ServiceException("账号已被冻结");
        }
        if (!account.getLoginPwd().equals(SecureUtil.md5(SecureUtil.sha1(loginPwd)))) {
            throw new ServiceException("登录密码错误");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(account.getAccountId());
        loginUser.setUsername(account.getPhoneNumber());
        loginUser.setMenuPermission(new HashSet<>());
        loginUser.setRolePermission(new HashSet<>());
        // 生成token
        LoginUtils.loginByDevice(loginUser, UserType.SYS_ACCOUNT, DeviceType.APP);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", StpUtil.getTokenValue());
        resultMap.put("nickName", account.getNickName());
        resultMap.put("avatarUrl", account.getAvatarUrl());
        resultMap.put("accountId", account.getAccountId());
        return resultMap;
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountInfoVo> entityToVo(List<Account> records, Boolean showCert) {
        List<Long> accountIds = records.stream().map(Account::getAccountId).collect(Collectors.toList());
        Map<Long, AccountCertInfoVo> accountCertInfoVoMap = new HashMap<>();
        if (null != showCert && showCert && !accountIds.isEmpty()) {
            accountCertInfoVoMap.putAll(accountCertService.list(Wrappers.<AccountCert>lambdaQuery().in(AccountCert::getAccountId, accountIds))
                    .stream()
                    .collect(Collectors.toMap(AccountCert::getAccountId, accountCert -> {
                        AccountCertInfoVo accountCertInfoVo = new AccountCertInfoVo();
                        BeanUtils.copyProperties(accountCert, accountCertInfoVo);
                        return accountCertInfoVo;
                    })));

        }
        return records.stream()
                .map(account -> {
                    AccountInfoVo accountInfoVo = new AccountInfoVo();
                    BeanUtils.copyProperties(account, accountInfoVo);
                    if (null != showCert && showCert && accountCertInfoVoMap.containsKey(account.getAccountId())) {
                        AccountCertInfoVo accountCertInfoVo = accountCertInfoVoMap.get(account.getAccountId());
                        accountInfoVo.setAccountCertInfoVo(accountCertInfoVo);
                    }
                    return accountInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountParam
     */
    private void checkParam(AeAccountParam aeAccountParam) {

    }
}