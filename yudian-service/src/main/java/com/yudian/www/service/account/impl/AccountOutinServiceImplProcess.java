package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.service.account.IAccountOutinService;
import com.yudian.www.service.account.IAccountOutinServiceProcess;
import com.yudian.www.service.account.param.AeAccountOutinParam;
import com.yudian.www.service.account.param.GetAccountOutinListParam;
import com.yudian.www.service.account.vo.AccountOutinInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台用户流水 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountOutinServiceImplProcess implements IAccountOutinServiceProcess {

    @Resource
    private IAccountOutinService accountOutinService;

    /**
     * 添加
     *
     * @param aeAccountOutinParam
     */
    @Override
    public void accountOutinAdd(AeAccountOutinParam aeAccountOutinParam) {
        aeAccountOutinParam.initParam();
        this.checkParam(aeAccountOutinParam);
        AccountOutin accountOutin = new AccountOutin();
        BeanUtils.copyProperties(aeAccountOutinParam, accountOutin);
        boolean save = accountOutinService.save(accountOutin);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountOutinAddBatch(List<AeAccountOutinParam> aeAccountOutinParamList) {
        List<AccountOutin> accountOutinList = aeAccountOutinParamList.stream()
                .map(aeAccountOutinParam -> {
                    AccountOutin accountOutin = new AccountOutin();
                    BeanUtils.copyProperties(aeAccountOutinParam, accountOutin);
                    return accountOutin;
                }).collect(Collectors.toList());
        boolean save = accountOutinService.saveBatch(accountOutinList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountOutinParam
     */
    @Override
    public void accountOutinEdit(AeAccountOutinParam aeAccountOutinParam) {
        aeAccountOutinParam.initParam();
        this.checkParam(aeAccountOutinParam);
        AccountOutin accountOutin = accountOutinService.getById(aeAccountOutinParam.getAccountOutinId());
        if (null == accountOutin) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountOutinParam, accountOutin);
        boolean update = accountOutinService.updateById(accountOutin);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param accountOutinIds
     */
    @Override
    public void accountOutinRemove(Long[] accountOutinIds) {
        boolean remove = accountOutinService.removeByIds(Arrays.asList(accountOutinIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountOutinListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountOutinInfoVo> getAccountOutinList(GetAccountOutinListParam getAccountOutinListParam) {
        Long accountId = getAccountOutinListParam.getAccountId();

        LambdaQueryWrapper<AccountOutin> queryWrapper = new QueryWrapper<AccountOutin>().lambda();
        queryWrapper.eq(null != accountId, AccountOutin::getAccountId, accountId);
        queryWrapper
                .ge(null != getAccountOutinListParam.getStartDateTime(), AccountOutin::getCreateDatetime, getAccountOutinListParam.getStartDateTime())
                .le(null != getAccountOutinListParam.getEndDateTime(), AccountOutin::getCreateDatetime, getAccountOutinListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountOutinListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountOutinListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountOutinListParam.getOrderBy());
        }

        IPage<AccountOutin> page = accountOutinService.page(new Page<>(getAccountOutinListParam.getPageNo(), getAccountOutinListParam.getPageSize()), queryWrapper);

        List<AccountOutin> records = page.getRecords();

        List<AccountOutinInfoVo> accountOutinInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountOutinInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountOutinId
     * @return
     */
    @Override
    public AccountOutinInfoVo getAccountOutinDetail(Long accountOutinId) {
        AccountOutin accountOutin = accountOutinService.getById(accountOutinId);
        if (null == accountOutin) {
            throw new ServiceException("记录不存在");
        }
        List<AccountOutinInfoVo> accountOutinInfoVos = this.entityToVo(Arrays.asList(accountOutin));
        return accountOutinInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountOutinInfoVo> entityToVo(List<AccountOutin> records) {
        return records.stream()
                .map(accountOutin -> {
                    AccountOutinInfoVo accountOutinInfoVo = new AccountOutinInfoVo();
                    BeanUtils.copyProperties(accountOutin, accountOutinInfoVo);
                    return accountOutinInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountOutinParam
     */
    private void checkParam(AeAccountOutinParam aeAccountOutinParam) {

    }
}